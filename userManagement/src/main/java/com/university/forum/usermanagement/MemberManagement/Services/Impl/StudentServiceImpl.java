package com.university.forum.usermanagement.MemberManagement.Services.Impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.university.forum.usermanagement.AdminManagement.ExceptionHandler.ElementNotFoundException;
import com.university.forum.usermanagement.ClassGroupManagement.Dtos.Response.DepartmentResponseDto;
import com.university.forum.usermanagement.ClassGroupManagement.Models.*;
import com.university.forum.usermanagement.ClassGroupManagement.Repositories.*;
import com.university.forum.usermanagement.MemberManagement.Dtos.Request.StudentRequestDto;
import com.university.forum.usermanagement.MemberManagement.Dtos.Request.StudentUpdateRequestDto;
import com.university.forum.usermanagement.MemberManagement.Dtos.Response.StudentResponseDto;
import com.university.forum.usermanagement.MemberManagement.Models.Classes.ProfileImage;
import com.university.forum.usermanagement.MemberManagement.Models.Role;
import com.university.forum.usermanagement.MemberManagement.Models.Student;
import com.university.forum.usermanagement.MemberManagement.Repositories.RoleRepository;
import com.university.forum.usermanagement.MemberManagement.Repositories.StudentRepository;
import com.university.forum.usermanagement.MemberManagement.Services.StudentService;
import com.university.forum.usermanagement.Shared.Dtos.Messages.NewPasswordMessage;
import com.university.forum.usermanagement.Shared.Services.MessageProducer;
import com.university.forum.usermanagement.Shared.Services.NotificationSender;
import com.university.forum.usermanagement.Shared.Services.PasswordService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final RoleRepository roleRepository;
    private final ClassGroupRepository classGroupRepository;
    private final PasswordService passwordService;
    private final MessageProducer messageProducer;
    private final NotificationSender notificationSender;
    private final LevelOfStudyRepository levelOfStudyRepository;
    private final SpecialityRepository specialityRepository;
    private final BranchRepository branchRepository;
    private final DepartmentRepository departmentRepository;


    public StudentServiceImpl(StudentRepository studentRepository, RoleRepository roleRepository, ClassGroupRepository classGroupRepository, PasswordService passwordService, MessageProducer messageProducer, NotificationSender notificationSender, LevelOfStudyRepository levelOfStudyRepository, SpecialityRepository specialityRepository, BranchRepository branchRepository, DepartmentRepository departmentRepository) {
        this.studentRepository = studentRepository;
        this.roleRepository = roleRepository;
        this.classGroupRepository = classGroupRepository;
        this.passwordService = passwordService;
        this.messageProducer = messageProducer;
        this.notificationSender = notificationSender;
        this.levelOfStudyRepository = levelOfStudyRepository;
        this.specialityRepository = specialityRepository;
        this.branchRepository = branchRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public StudentResponseDto createStudent(StudentRequestDto studentRequestDto) {

        Set<Role> roles =getRolesByIds(studentRequestDto.getRolesIds());

        ClassGroup classGroup= classGroupRepository.findById(studentRequestDto.getClassGroupId()).orElseThrow(()->new ElementNotFoundException("ClassGroup not found"));

        Student oldStudent=studentRepository.findByAddressEmail(studentRequestDto.getAddressEmail()).orElse(null);
        /*if(oldStudent!=null) {
            throw new UsernameNotFoundException("Student already exists");
        }*/
        String passwordGenerated=passwordService.generateRandomPassword();
        System.out.println("Generated password is : "+passwordGenerated);
        Student student=new Student();
        student.setRoles(roles);
        student.setFirstName(studentRequestDto.getFirstName());
        student.setLastName(studentRequestDto.getLastName());
        student.setCin(studentRequestDto.getCin());
        student.setAddress(studentRequestDto.getAddress());
        student.setClassGroup(classGroup);
        student.setStudentNumber(studentRequestDto.getStudentNumber());
        student.setPassword(passwordService.hashPassword(passwordGenerated));
        student.setAddressEmail(studentRequestDto.getAddressEmail());
        student.setLinkedInProfileUrl(studentRequestDto.getLinkedInProfileUrl());
        student.setPhoneNumber(studentRequestDto.getPhoneNumber());
        student.setDob(studentRequestDto.getDob());
        student.setSex(studentRequestDto.getSex()!=null?studentRequestDto.getSex():true);
        student.setProfileImageUrl(studentRequestDto.getProfileImageUrl());
        classGroup.getStudents().add(student);

        student=studentRepository.save(student);
        messageProducer.sendMemberCreatedMessage(student,"student");
        // send the new password to the notif MS ,so he can notify by email the student
        notificationSender.sendPasswordResetEmail(new NewPasswordMessage(student.getId(),student.getAddressEmail(),passwordGenerated));
        return StudentResponseDto.convertToStudentResponseDto(student);
    }

    @Override
    public List<StudentResponseDto> getAll() {

        List<Student> students=studentRepository.findAll();
        List<StudentResponseDto> studentResponseDtos=new ArrayList<>();
        for(Student student:students) {
            StudentResponseDto studentResponseDto;
            ClassGroup group=student.getClassGroup();
            LevelOfStudy levelOfStudy=group.getLevelOfStudy();
            Speciality speciality=levelOfStudy.getSpeciality();
            Branch branch =speciality.getBranch();
            Department department=branch.getDepartment();
            studentResponseDto=StudentResponseDto.convertToStudentResponseDtoFull(
                    student,
                    new DepartmentResponseDto(department.getId(), department.getName(), department.getReference()),
                    new DepartmentResponseDto(branch.getId(), branch.getName(), branch.getReference()),
                    new DepartmentResponseDto(speciality.getId(), speciality.getName(), speciality.getReference()),
                    new DepartmentResponseDto(levelOfStudy.getId(), levelOfStudy.getName(), levelOfStudy.getReference()),
                    new DepartmentResponseDto(group.getId(),group.getName(), group.getReference())
            );
            studentResponseDtos.add(studentResponseDto);

        }
        return  studentResponseDtos;
    }

    @Override
    public void updateStudent(UUID id, StudentUpdateRequestDto studentRequestDto) {
        Student student =studentRepository.findById(id).orElseThrow(
                ()->new    UsernameNotFoundException("User not found")
        );

        if (studentRequestDto.getFirstName() != null && !studentRequestDto.getFirstName().isBlank()) student.setFirstName(studentRequestDto.getFirstName());
        if (studentRequestDto.getLastName() != null  && !studentRequestDto.getLastName().isBlank()) student.setLastName(studentRequestDto.getLastName());
        if (studentRequestDto.getAddressEmail() != null && !studentRequestDto.getAddressEmail().isBlank()) student.setAddressEmail(studentRequestDto.getAddressEmail());
        if (studentRequestDto.getPhoneNumber() != null && !studentRequestDto.getPhoneNumber().isBlank()) student.setPhoneNumber(studentRequestDto.getPhoneNumber());
        if (studentRequestDto.getAddress() != null && !studentRequestDto.getAddress().isBlank()) student.setAddress(studentRequestDto.getAddress());
        if (studentRequestDto.getLinkedInProfileUrl() != null  && !studentRequestDto.getLinkedInProfileUrl().isBlank()) student.setLinkedInProfileUrl(studentRequestDto.getLinkedInProfileUrl());
        if (studentRequestDto.getProfileImageUrl() != null && !studentRequestDto.getProfileImageUrl().isBlank()) student.setProfileImageUrl(studentRequestDto.getProfileImageUrl());
        if (studentRequestDto.getSex() != null ) student.setSex(studentRequestDto.getSex());
        if (studentRequestDto.getDob() != null ) student.setDob(studentRequestDto.getDob());
        if (studentRequestDto.getClassGroupId() != null && studentRequestDto.getClassGroupId()>0) {
            ClassGroup newGroup=classGroupRepository.findById(studentRequestDto.getClassGroupId()).orElseThrow(()->new ElementNotFoundException("ClassGroup not found"));
           student.setClassGroup(newGroup);
        }

        studentRepository.save(student);

    }

    @Override
    public StudentResponseDto getStudentById(UUID id) {
        Student student=studentRepository.findById(id).orElseThrow(()->new UsernameNotFoundException("Student not found"));

        return StudentResponseDto.convertToStudentResponseDto(student);

    }

    private Set<Role> getRolesByIds(Set<Integer> roleIds) {
        Set<Role> roles = new HashSet<>();
        for (Integer roleId : roleIds) {
            Role role = roleRepository.findById(roleId)
                    .orElseThrow(() -> new ElementNotFoundException("Role not found"));
            roles.add(role);
        }
        if (roles.isEmpty()) {
            throw new ElementNotFoundException("There are no existing roles associated with this student");
        }
        return roles;
    }



}
