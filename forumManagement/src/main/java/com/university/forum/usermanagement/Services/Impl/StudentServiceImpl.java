package com.university.forum.usermanagement.Services.Impl;

import com.university.forum.forummanagement.membership.models.Member;
import com.university.forum.forummanagement.membership.models.Role;
import com.university.forum.forummanagement.membership.models.Student;
import com.university.forum.forummanagement.membership.models.enums.MemberType;
import com.university.forum.forummanagement.membership.repositories.RoleRepository;
import com.university.forum.forummanagement.membership.repositories.StudentRepository;
import com.university.forum.forummanagement.shared.exceptions.ElementAlreadyExistsException;
import com.university.forum.forummanagement.shared.exceptions.ElementNotFoundException;
import com.university.forum.forummanagement.structures.models.ClassGroup;
import com.university.forum.forummanagement.structures.repositories.ClassGroupRepository;
import com.university.forum.usermanagement.Dtos.Message.StudentMessage;
import com.university.forum.usermanagement.Services.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerFactoryFriend;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class StudentServiceImpl implements StudentService {

    private static final Logger logger= LoggerFactory.getLogger(StudentServiceImpl.class);
    private final StudentRepository studentRepository;
    private final ClassGroupRepository classGroupRepository;
    private final RoleRepository roleRepository;


    public StudentServiceImpl(StudentRepository studentRepository, ClassGroupRepository classGroupRepository, RoleRepository roleRepository) {
        this.studentRepository = studentRepository;
        this.classGroupRepository = classGroupRepository;
        this.roleRepository = roleRepository;
    }


    @Override
    public void createStudent(StudentMessage studentMessage) throws ElementAlreadyExistsException {
            logger.info("Student Service "+studentMessage.toString());
            validateMember(studentMessage);
            Student student = new Student();
            student.setId(studentMessage.getId());
            student.setFirstName(studentMessage.getFirstName());
            student.setLastName(studentMessage.getLastName());
            student.setStudentNumber(studentMessage.getStudentNumber());
            student.setAddress(studentMessage.getAddress());
            student.setAddressEmail(studentMessage.getAddressEmail());
            student.setCin(studentMessage.getCin());
            student.setDob(studentMessage.getDob());
            student.setLinkedInProfileUrl(studentMessage.getLinkedInProfileUrl());
            student.setProfileImageUrl(studentMessage.getProfileImageUrl());
            student.setSex(studentMessage.getSex());
            student.setMemberType(MemberType.STUDENT);
            student.setPhoneNumber(studentMessage.getPhoneNumber());

            classGroupRepository.findById(studentMessage.getClassGroupId()).ifPresent(student::setClassGroup);

            Set<Role> roles = roleRepository.findAllByIdIn(new ArrayList<>(studentMessage.getRoleIds()));
            student.setRoles(roles);

            student=studentRepository.saveAndFlush(student);
            logger.info("Student Created successfully {} ",student);


    }

    public void validateMember(StudentMessage studentMessage) throws ElementAlreadyExistsException {
        logger.info("Validating member with email: {} and ID: {}", studentMessage.getAddressEmail(), studentMessage.getId());

        if (studentMessage.getId() == null) {
            throw new IllegalArgumentException("Student Id is missing "+studentMessage);
        }
        if ((studentMessage.getAddressEmail() == null) || (studentMessage.getAddressEmail().isBlank())) {
            throw new IllegalArgumentException("Student email is missing "+studentMessage);
        }
        Student student=studentRepository.findById(studentMessage.getId()).orElse(null);
        if (student != null) {
            throw new ElementAlreadyExistsException("Student with id "+student.getId()+" already exists");
        }

    }

}
