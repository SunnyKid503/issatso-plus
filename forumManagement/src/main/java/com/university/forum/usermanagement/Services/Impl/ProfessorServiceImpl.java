package com.university.forum.usermanagement.Services.Impl;

import com.university.forum.forummanagement.membership.models.Professor;
import com.university.forum.forummanagement.membership.models.Role;
import com.university.forum.forummanagement.membership.models.Student;
import com.university.forum.forummanagement.membership.models.enums.MemberType;
import com.university.forum.forummanagement.membership.repositories.ProfessorRepository;
import com.university.forum.forummanagement.membership.repositories.RoleRepository;
import com.university.forum.forummanagement.shared.exceptions.ElementAlreadyExistsException;
import com.university.forum.forummanagement.structures.models.ClassGroup;
import com.university.forum.forummanagement.structures.repositories.ClassGroupRepository;
import com.university.forum.usermanagement.Dtos.Message.ProfessorMessage;
import com.university.forum.usermanagement.Dtos.Message.StudentMessage;
import com.university.forum.usermanagement.Services.ProfessorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProfessorServiceImpl implements ProfessorService {

    private static final Logger logger= LoggerFactory.getLogger(ProfessorServiceImpl.class);
    private final ProfessorRepository professorRepository;
    private final ClassGroupRepository classGroupRepository;
    private final RoleRepository roleRepository;

    public ProfessorServiceImpl(ProfessorRepository professorRepository, ClassGroupRepository classGroupRepository, RoleRepository roleRepository) {
        this.professorRepository = professorRepository;
        this.classGroupRepository = classGroupRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void createProfessor(ProfessorMessage professorMessage) throws ElementAlreadyExistsException {
        logger.info("Professor Service "+professorMessage.toString());
        validateMember(professorMessage);

        Professor professor = new Professor();
        professor.setId(professorMessage.getId());
        professor.setFirstName(professorMessage.getFirstName());
        professor.setLastName(professorMessage.getLastName());
        professor.setAddress(professorMessage.getAddress());
        professor.setAddressEmail(professorMessage.getAddressEmail());
        professor.setCin(professorMessage.getCin());
        professor.setDob(professorMessage.getDob());
        professor.setLinkedInProfileUrl(professorMessage.getLinkedInProfileUrl());
        professor.setProfileImageUrl(professorMessage.getProfileImageUrl());
        professor.setSex(professorMessage.getSex());
        professor.setMemberType(MemberType.STUDENT);
        professor.setPhoneNumber(professorMessage.getPhoneNumber());

        Set<Role> roles = roleRepository.findAllByIdIn(new ArrayList<>(professorMessage.getRoleIds()));
        professor.setRoles(roles);

        List<ClassGroup> classGroups =classGroupRepository.findAllById(professorMessage.getClassGroupIds());
        professor.setClassGroups(new HashSet<>(classGroups));

        professor=professorRepository.saveAndFlush(professor);
        logger.info("Professor created successfully {} ",professor);


    }

    public void validateMember(ProfessorMessage professorMessage) throws ElementAlreadyExistsException {
        logger.info("Validating member with email: {} and ID: {}", professorMessage.getAddressEmail(), professorMessage.getId());

        if (professorMessage.getId() == null) {
            throw new IllegalArgumentException("Professor Id is missing "+professorMessage);
        }
        if ((professorMessage.getAddressEmail() == null) || (professorMessage.getAddressEmail().isBlank())) {
            throw new IllegalArgumentException("Professor email is missing "+professorMessage);
        }
        Professor professor=professorRepository.findById(professorMessage.getId()).orElse(null);
        if (professor != null) {
            throw new ElementAlreadyExistsException("Professor with id "+professor.getId()+" already exists");
        }

    }
}
