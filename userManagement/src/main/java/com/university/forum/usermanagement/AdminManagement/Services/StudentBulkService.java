package com.university.forum.usermanagement.AdminManagement.Services;

import com.university.forum.usermanagement.AdminManagement.Dtos.BulkUploadResponse;
import com.university.forum.usermanagement.ClassGroupManagement.Models.ClassGroup;
import com.university.forum.usermanagement.ClassGroupManagement.Repositories.ClassGroupRepository;
import com.university.forum.usermanagement.MemberManagement.Models.Role;
import com.university.forum.usermanagement.MemberManagement.Models.Student;
import com.university.forum.usermanagement.MemberManagement.Repositories.RoleRepository;
import com.university.forum.usermanagement.MemberManagement.Repositories.StudentRepository;
import com.university.forum.usermanagement.Shared.Dtos.Messages.NewPasswordMessage;
import com.university.forum.usermanagement.Shared.Services.MessageProducer;
import com.university.forum.usermanagement.Shared.Services.NotificationSender;
import com.university.forum.usermanagement.Shared.Services.PasswordService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentBulkService {

    private static final Logger logger = LoggerFactory.getLogger(StudentBulkService.class);
    private static final List<String> EXPECTED_HEADERS = Arrays.asList(
            "firstName", "lastName", "addressEmail", "phoneNumber", "address",
            "linkedInProfileUrl", "profileImageUrl", "studentNumber", "cin",
            "sex", "rolesIds", "dob", "classGroupId");

    private final StudentRepository studentRepository;
    private final ClassGroupRepository classGroupRepository;
    private final RoleRepository roleRepository;
    private final PasswordService passwordService;
    private final NotificationSender notificationSender;
    private final MessageProducer messageProducer;

    public StudentBulkService(StudentRepository studentRepository,
                              ClassGroupRepository classGroupRepository,
                              RoleRepository roleRepository, PasswordService passwordService, NotificationSender notificationSender, MessageProducer messageProducer) {
        this.studentRepository = studentRepository;
        this.classGroupRepository = classGroupRepository;
        this.roleRepository = roleRepository;
        this.passwordService = passwordService;
        this.notificationSender = notificationSender;
        this.messageProducer = messageProducer;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public BulkUploadResponse processExcel(MultipartFile file) {
        List<Student> studentsToSave = new ArrayList<>();
        List<String> errors = new ArrayList<>();
        Map<Integer, String> rowErrors = new HashMap<>();
        int totalRows = 0;

        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);

            // Validate headers
            Row headerRow = sheet.getRow(0);
            if (!validateHeaders(headerRow, errors)) {
                return new BulkUploadResponse(0, errors, 0, errors.size());
            }

            List<Role> allRoles = roleRepository.findAll();
            Set<Integer> validRoleIds = allRoles.stream()
                    .map(Role::getId)
                    .collect(Collectors.toSet());

            Iterator<Row> rowIterator = sheet.rowIterator();
            rowIterator.next();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                totalRows++;

                try {
                    Student student = mapRowToStudent(row, validRoleIds);
                    validateStudent(student);
                    checkDuplicates(student, studentsToSave);
                    studentsToSave.add(student);
                } catch (Exception e) {
                    String errorMessage = "Row " + (row.getRowNum()+1) + ": " + e.getMessage();
                    rowErrors.put(row.getRowNum(), errorMessage);
                    errors.add(errorMessage);
                    logger.error(errorMessage, e);
                }
            }

        } catch (IOException e) {
            errors.add("File processing error: " + e.getMessage());
            logger.error("Error processing Excel file", e);
            return new BulkUploadResponse(totalRows, errors, 0, errors.size());
        }

        int successCount = 0;
        for (Student student : studentsToSave) {
            try {
                String passwordGenerated=passwordService.generateRandomPassword();
                student.setPassword(passwordService.hashPassword(passwordGenerated));
                student=studentRepository.save(student);
                notificationSender.sendPasswordResetEmail(new NewPasswordMessage(student.getId(),student.getAddressEmail(),passwordGenerated));
                messageProducer.sendMemberCreatedMessage(student,"student");

                successCount++;
            } catch (Exception e) {
                String errorMessage = "Error saving student " + student.getFirstName() + " " + student.getLastName() +
                        " (" + student.getAddressEmail() + "): " + getRootCauseMessage(e);
                errors.add(errorMessage);
                logger.error(errorMessage, e);
            }
        }

        return new BulkUploadResponse(
                totalRows,
                errors,
                successCount,
                errors.size()
        );
    }


    private String getRootCauseMessage(Throwable throwable) {
        Throwable rootCause = throwable;
        while (rootCause.getCause() != null && rootCause.getCause() != rootCause) {
            rootCause = rootCause.getCause();
        }
        return rootCause.getMessage();
    }


    private boolean validateHeaders(Row headerRow, List<String> errors) {
        List<String> foundHeaders = new ArrayList<>();
        for (int i = 0; i < headerRow.getLastCellNum(); i++) {
            Cell cell = headerRow.getCell(i);
            if (cell != null) {
                foundHeaders.add(cell.getStringCellValue().trim());
            }
        }

        List<String> missingHeaders = EXPECTED_HEADERS.stream()
                .filter(header -> !foundHeaders.contains(header))
                .collect(Collectors.toList());

        if (!missingHeaders.isEmpty()) {
            errors.add("Missing required headers: " + String.join(", ", missingHeaders));
            return false;
        }

        return true;
    }


    private Student mapRowToStudent(Row row, Set<Integer> validRoleIds) {
        Student student = new Student();

        student.setFirstName(getStringCell(row, 0));
        student.setLastName(getStringCell(row, 1));
        student.setAddressEmail(getStringCell(row, 2));

        String phoneNumber = getStringCell(row, 3);
        if (phoneNumber != null && !phoneNumber.isBlank()) {
            phoneNumber = phoneNumber.replaceAll("\\D", "");

            if (phoneNumber.length() != 8) {
                throw new RuntimeException("Phone number must be exactly 8 digits: " + phoneNumber);
            }
        }
        student.setPhoneNumber(phoneNumber);

        student.setAddress(getStringCell(row, 4));
        student.setLinkedInProfileUrl(getStringCell(row, 5));
        student.setProfileImageUrl(getStringCell(row, 6));
        student.setStudentNumber(getStringCell(row, 7));
        student.setCin(getStringCell(row, 8));

        String sexValue = getStringCell(row, 9);
        student.setSex(sexValue.equalsIgnoreCase("TRUE") || sexValue.equalsIgnoreCase("1"));

        List<Role> roles = parseAndFetchRoles(getStringCell(row, 10), validRoleIds);
        student.setRoles(new HashSet<>(roles));

        student.setDob(parseDate(getStringCell(row, 11)));

        int classGroupId;
        try {
            classGroupId = (int) getNumericCell(row, 12);
        } catch (Exception e) {
            String classGroupIdStr = getStringCell(row, 12);
            try {
                classGroupId = Integer.parseInt(classGroupIdStr);
            } catch (NumberFormatException ex) {
                throw new RuntimeException("Invalid classGroupId format: " + classGroupIdStr);
            }
        }

        int finalClassGroupId = classGroupId;
        ClassGroup classGroup = classGroupRepository.findById(classGroupId)
                .orElseThrow(() -> new RuntimeException("ClassGroup with ID " + finalClassGroupId + " not found"));
        student.setClassGroup(classGroup);

        return student;
    }

    /**
     * Parse and fetch roles from the database based on comma-separated IDs
     * Now takes a set of valid role IDs to check against
     */
    private List<Role> parseAndFetchRoles(String rolesString, Set<Integer> validRoleIds) {
        if (rolesString == null || rolesString.trim().isEmpty()) {
            // Default role (ID 4 - student)
            Role defaultRole = roleRepository.findById(4)
                    .orElseThrow(() -> new RuntimeException("Default student role (ID 4) not found"));
            return Collections.singletonList(defaultRole);
        }

        List<Integer> providedRoleIds = Arrays.stream(rolesString.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        if (providedRoleIds.isEmpty()) {
            // Default role (ID 4 - student)
            Role defaultRole = roleRepository.findById(4)
                    .orElseThrow(() -> new RuntimeException("Default student role (ID 4) not found"));
            return Collections.singletonList(defaultRole);
        }

        List<Integer> invalidRoleIds = providedRoleIds.stream()
                .filter(id -> !validRoleIds.contains(id))
                .collect(Collectors.toList());

        if (!invalidRoleIds.isEmpty()) {
            throw new RuntimeException("Invalid role IDs: " + String.join(", ",
                    invalidRoleIds.stream().map(String::valueOf).collect(Collectors.toList())));
        }

        List<Role> roles = roleRepository.findAllByIdIn(providedRoleIds);

        if (roles.isEmpty()) {
            Role defaultRole = roleRepository.findById(4)
                    .orElseThrow(() -> new RuntimeException("Default student role (ID 4) not found"));
            return Collections.singletonList(defaultRole);
        }

        return roles;
    }

    /**
     * Parse date string into LocalDate with multiple format support
     */
    private LocalDate parseDate(String dateString) {
        if (dateString == null || dateString.trim().isEmpty() ||
                dateString.equalsIgnoreCase("INVALID_DOB")) {
            throw new RuntimeException("Invalid or missing date of birth");
        }

        List<DateTimeFormatter> formatters = Arrays.asList(
                DateTimeFormatter.ISO_LOCAL_DATE,             // YYYY-MM-DD
                DateTimeFormatter.ofPattern("M/d/yyyy"),      // Excel US format
                DateTimeFormatter.ofPattern("d/M/yyyy"),      // DD/MM/YYYY
                DateTimeFormatter.ofPattern("dd-MM-yyyy"),    // DD-MM-YYYY
                DateTimeFormatter.ofPattern("MM-dd-yyyy")     // MM-DD-YYYY
        );

        for (DateTimeFormatter formatter : formatters) {
            try {
                return LocalDate.parse(dateString, formatter);
            } catch (DateTimeParseException e) {
                // Try next format
            }
        }

        throw new RuntimeException("Unsupported date format: " + dateString + ". Use YYYY-MM-DD format.");
    }

    /**
     * Validate student entity
     */
    private void validateStudent(Student student) {
        List<String> validationErrors = new ArrayList<>();

        // Validate required fields
        if (student.getFirstName() == null || student.getFirstName().isBlank()) {
            validationErrors.add("First name is required");
        }

        if (student.getLastName() == null || student.getLastName().isBlank()) {
            validationErrors.add("Last name is required");
        }

        if (student.getAddressEmail() == null || student.getAddressEmail().isBlank()) {
            validationErrors.add("Email is required");
        } else if (!student.getAddressEmail().matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            validationErrors.add("Invalid email format: " + student.getAddressEmail());
        }

        if (student.getCin() == null || !student.getCin().matches("^\\d{8}$")) {
            validationErrors.add("CIN must be exactly 8 digits");
        }

        if (student.getStudentNumber() == null || !student.getStudentNumber().matches("^\\d{8}$")) {
            validationErrors.add("Student Number must be exactly 8 digits");
        }

        if (student.getPhoneNumber() == null || !student.getPhoneNumber().matches("^\\d{8}$")) {
            validationErrors.add("Phone number must be exactly 8 digits");
        }

        if (student.getDob() == null) {
            validationErrors.add("Date of birth is required");
        } else {
            LocalDate minAgeDate = LocalDate.now().minusYears(17);
            if (student.getDob().isAfter(minAgeDate)) {
                validationErrors.add("Student must be at least 17 years old");
            }
        }

        if (student.getClassGroup() == null) {
            validationErrors.add("Class group is required");
        }

        if (!validationErrors.isEmpty()) {
            throw new RuntimeException(String.join("; ", validationErrors));
        }
    }


    private void checkDuplicates(Student student, List<Student> currentBatch) {
        if (studentRepository.findByAddressEmail(student.getAddressEmail()).isPresent()) {
            throw new RuntimeException("Email already exists in database: " + student.getAddressEmail());
        }

        boolean duplicateInBatch = currentBatch.stream()
                .anyMatch(s -> s.getAddressEmail().equalsIgnoreCase(student.getAddressEmail()));

        if (duplicateInBatch) {
            throw new RuntimeException("Duplicate email in current upload batch: " + student.getAddressEmail());
        }
    }
    private String getStringCell(Row row, int cellIndex) {
        Cell cell = row.getCell(cellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getLocalDateTimeCellValue().toLocalDate().toString();
                } else {
                    // Handle numeric cells without decimal places
                    double numValue = cell.getNumericCellValue();
                    if (numValue == Math.floor(numValue)) {
                        return String.valueOf((long) numValue);
                    } else {
                        return String.valueOf(numValue);
                    }
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                FormulaEvaluator evaluator = cell.getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator();
                CellValue cellValue = evaluator.evaluate(cell);

                switch (cellValue.getCellType()) {
                    case STRING:
                        return cellValue.getStringValue().trim();
                    case NUMERIC:
                        if (DateUtil.isCellDateFormatted(cell)) {
                            return cell.getLocalDateTimeCellValue().toLocalDate().toString();
                        } else {
                            double value = cellValue.getNumberValue();
                            if (value == Math.floor(value)) {
                                return String.valueOf((long) value);
                            } else {
                                return String.valueOf(value);
                            }
                        }
                    case BOOLEAN:
                        return String.valueOf(cellValue.getBooleanValue());
                    default:
                        return "";
                }
            default:
                return "";
        }
    }


    private double getNumericCell(Row row, int cellIndex) {
        Cell cell = row.getCell(cellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

        switch (cell.getCellType()) {
            case NUMERIC:
                return cell.getNumericCellValue();
            case STRING:
                try {
                    return Double.parseDouble(cell.getStringCellValue().trim());
                } catch (NumberFormatException e) {
                    throw new RuntimeException("Invalid numeric value: " + cell.getStringCellValue());
                }
            case FORMULA:
                FormulaEvaluator evaluator = cell.getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator();
                CellValue cellValue = evaluator.evaluate(cell);

                if (cellValue.getCellType() == CellType.NUMERIC) {
                    return cellValue.getNumberValue();
                } else if (cellValue.getCellType() == CellType.STRING) {
                    try {
                        return Double.parseDouble(cellValue.getStringValue().trim());
                    } catch (NumberFormatException e) {
                        throw new RuntimeException("Invalid numeric value in formula: " + cellValue.getStringValue());
                    }
                }
            default:
                throw new RuntimeException("Cell is not numeric at column index " + cellIndex);
        }
    }
}