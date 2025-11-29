package com.university.forum.usermanagement.MemberManagement.Services.Impl;

import com.university.forum.usermanagement.AdminManagement.ExceptionHandler.ElementNotFoundException;
import com.university.forum.usermanagement.ClassGroupManagement.Dtos.Response.DepartmentResponseDto;
import com.university.forum.usermanagement.ClassGroupManagement.Models.ClassGroup;
import com.university.forum.usermanagement.ClassGroupManagement.Models.Department;
import com.university.forum.usermanagement.ClassGroupManagement.Repositories.ClassGroupRepository;
import com.university.forum.usermanagement.ClassGroupManagement.Repositories.DepartmentRepository;
import com.university.forum.usermanagement.MemberManagement.Dtos.Request.ProfessorRequestDto;
import com.university.forum.usermanagement.MemberManagement.Dtos.Request.ProfessorUpdateRequestDto;
import com.university.forum.usermanagement.MemberManagement.Dtos.Response.ProfessorResponseDto;
import com.university.forum.usermanagement.MemberManagement.Models.Professor;
import com.university.forum.usermanagement.MemberManagement.Models.Role;
import com.university.forum.usermanagement.MemberManagement.Repositories.ProfessorRepository;
import com.university.forum.usermanagement.MemberManagement.Repositories.RoleRepository;
import com.university.forum.usermanagement.MemberManagement.Services.ProfessorService;
import com.university.forum.usermanagement.Shared.Dtos.Messages.NewPasswordMessage;
import com.university.forum.usermanagement.Shared.Services.MessageProducer;
import com.university.forum.usermanagement.Shared.Services.NotificationSender;
import com.university.forum.usermanagement.Shared.Services.PasswordService;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class ProfessorServiceImpl implements ProfessorService {
    private final ProfessorRepository professorRepository;
    private final RoleRepository roleRepository;
    private final ClassGroupRepository classGroupRepository;
    private final PasswordService passwordService;
    private final MessageProducer messageProducer;
    private final DepartmentRepository departmentRepository;
    private final NotificationSender notificationSender;

    public ProfessorServiceImpl(ProfessorRepository professorRepository, RoleRepository roleRepository, ClassGroupRepository classGroupRepository, PasswordService passwordService, MessageProducer messageProducer, DepartmentRepository departmentRepository, NotificationSender notificationSender) {
        this.professorRepository = professorRepository;
        this.roleRepository = roleRepository;
        this.classGroupRepository = classGroupRepository;
        this.passwordService = passwordService;
        this.messageProducer = messageProducer;
        this.departmentRepository = departmentRepository;
        this.notificationSender = notificationSender;
    }

    @Override
    @Transactional
    public ProfessorResponseDto createProfessor(ProfessorRequestDto professorRequestDto) {

        Professor oldProf=professorRepository.findByAddressEmail(professorRequestDto.getAddressEmail()).orElse(null);
        if(oldProf!=null){
            throw new UsernameNotFoundException("Professor already exists");
        }

        Set<Role> roles = getRolesByIds(professorRequestDto.getRolesIds());

        String passwordGenerated=passwordService.generateRandomPassword();
        System.out.println("Genretaed password is : "+passwordGenerated);
       Professor professor= new Professor();
       professor.setFirstName(professorRequestDto.getFirstName());
       professor.setLastName(professorRequestDto.getLastName());
       professor.setRoles(roles);
       professor.setAddress(professorRequestDto.getAddress());
       professor.setCin(professorRequestDto.getCin());
       professor.setAddressEmail(professorRequestDto.getAddressEmail());
       professor.setDob(professorRequestDto.getDob());
       professor.setLinkedInProfileUrl(professorRequestDto.getLinkedInProfileUrl());
       professor.setProfileImageUrl(professorRequestDto.getProfileImageUrl());
       professor.setPhoneNumber(professorRequestDto.getPhoneNumber());
       professor.setSex(professorRequestDto.getSex());
       professor.setPassword(passwordService.hashPassword(passwordGenerated));
        if(professorRequestDto.getClassGroupIds()!=null && !professorRequestDto.getClassGroupIds().isEmpty()){
            Set<ClassGroup> classGroups=getClassGroupsByIds(professorRequestDto.getClassGroupIds());
            professor.setClassGroups(classGroups);
        }

        if (professorRequestDto.getDepartmentId()!=null &&professorRequestDto.getDepartmentId()>0) {
            Department department=departmentRepository.findById(professorRequestDto.getDepartmentId()).orElse(null);
            if(department!=null)professor.setBelongedDepartment(department);

        }
         professor=professorRepository.save(professor);
        messageProducer.sendMemberCreatedMessage(professor,"professor");
        notificationSender.sendPasswordResetEmail(new NewPasswordMessage(professor.getId(),professor.getAddressEmail(),passwordGenerated));
        return convertToProfessorResponseDto(professor);
    }

    @Override
    public List<ProfessorResponseDto> getAll() {
        return professorRepository.findAll().stream().map(this::convertToProfessorResponseDto).toList();
    }

    @Override
    public void updateStudent(UUID id, ProfessorUpdateRequestDto professorDto) {
        Professor professor=professorRepository.findById(id).orElseThrow(
                ()->new UsernameNotFoundException("Professor not found")
        );
        if (professorDto.getFirstName() != null && !professorDto.getFirstName().isBlank()) professor.setFirstName(professorDto.getFirstName());
        if (professorDto.getLastName() != null  && !professorDto.getLastName().isBlank()) professor.setLastName(professorDto.getLastName());
        if (professorDto.getAddressEmail() != null && !professorDto.getAddressEmail().isBlank()) professor.setAddressEmail(professorDto.getAddressEmail());
        if (professorDto.getPhoneNumber() != null && !professorDto.getPhoneNumber().isBlank()) professor.setPhoneNumber(professorDto.getPhoneNumber());
        if (professorDto.getAddress() != null && !professorDto.getAddress().isBlank()) professor.setAddress(professorDto.getAddress());
        if (professorDto.getLinkedInProfileUrl() != null  && !professorDto.getLinkedInProfileUrl().isBlank()) professor.setLinkedInProfileUrl(professorDto.getLinkedInProfileUrl());
        if (professorDto.getProfileImageUrl() != null && !professorDto.getProfileImageUrl().isBlank()) professor.setProfileImageUrl(professorDto.getProfileImageUrl());
        if (professorDto.getSex() != null ) professor.setSex(professorDto.getSex());
        if (professorDto.getDob() != null ) professor.setDob(professorDto.getDob());
        if (professorDto.getDepartmentId() != null ) {
            departmentRepository.findById(professorDto.getDepartmentId()).ifPresent(professor::setBelongedDepartment);
        }
        if(professorDto.getClassGroupIds()!=null ){
            Set<ClassGroup> classGroups=getClassGroupsByIds(professorDto.getClassGroupIds());
            professor.setClassGroups(classGroups);
        }
        professorRepository.save(professor);

    }


    private Set<Role> getRolesByIds(Set<Integer> roleIds) {
        Set<Role> roles = new HashSet<>();
        for (Integer roleId : roleIds) {
            Role role = roleRepository.findById(roleId)
                    .orElseThrow(() -> new ElementNotFoundException("Role not found"));
            roles.add(role);
        }
        if (roles.isEmpty()) {
            throw new ElementNotFoundException("There are no existing roles associated with this professor");
        }
        return roles;
    }

    private Set<ClassGroup> getClassGroupsByIds(Set<Integer> classGroupIds) {
        Set<ClassGroup> classGroups = new HashSet<>();
        for (Integer classGroupId : classGroupIds) {
            ClassGroup classGroup = classGroupRepository.findById(classGroupId)
                    .orElseThrow(() -> new ElementNotFoundException("ClassGroup not found with id : "+classGroupId));
            classGroups.add(classGroup);
        }
        if (classGroups.isEmpty()) {
            throw new ElementNotFoundException("There are no existing classGroups associated with this professor");
        }
        return classGroups;
    }
    public ProfessorResponseDto convertToProfessorResponseDto(Professor professor) {
        ProfessorResponseDto responseDto = new ProfessorResponseDto();
        responseDto.setId(professor.getId());
        responseDto.setFirstName(professor.getFirstName());
        responseDto.setLastName(professor.getLastName());
        responseDto.setAddressEmail(professor.getAddressEmail());
        responseDto.setPhoneNumber(professor.getPhoneNumber());
        responseDto.setAddress(professor.getAddress());
        responseDto.setLinkedInProfileUrl(professor.getLinkedInProfileUrl());
        responseDto.setCin(professor.getCin());
        responseDto.setDob(professor.getDob());
        responseDto.setSex(professor.getSex());
        if(professor.getBelongedDepartment()!=null)responseDto.setBelongedDepartment(new DepartmentResponseDto(professor.getBelongedDepartment().getId(),professor.getBelongedDepartment().getName(),professor.getBelongedDepartment().getReference()));
        responseDto.setProfileImageUrl(professor.getProfileImageUrl());

        for(ClassGroup classGroup : professor.getClassGroups()) {
            responseDto.getClassGroups().add(new DepartmentResponseDto(classGroup.getId(), classGroup.getName(), classGroup.getReference()));
        }

        return responseDto;
    }


}
