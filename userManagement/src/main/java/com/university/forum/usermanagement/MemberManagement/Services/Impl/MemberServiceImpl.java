package com.university.forum.usermanagement.MemberManagement.Services.Impl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.university.forum.usermanagement.ClassGroupManagement.Models.*;
import com.university.forum.usermanagement.ClassGroupManagement.Repositories.*;
import com.university.forum.usermanagement.MemberManagement.Dtos.Message.ReportMessage;
import com.university.forum.usermanagement.MemberManagement.Dtos.Request.ReportRequest;
import com.university.forum.usermanagement.MemberManagement.Dtos.Response.MemberResponseDto;
import com.university.forum.usermanagement.MemberManagement.Models.*;
import com.university.forum.usermanagement.MemberManagement.Models.Classes.ProfileImage;
import com.university.forum.usermanagement.MemberManagement.Models.Enums.MemberType;
import com.university.forum.usermanagement.MemberManagement.Repositories.MemberRepository;
import com.university.forum.usermanagement.MemberManagement.Repositories.ProfessorRepository;
import com.university.forum.usermanagement.MemberManagement.Repositories.StudentRepository;
import com.university.forum.usermanagement.MemberManagement.Services.MemberService;
import com.university.forum.usermanagement.Shared.Services.CloudinaryService;
import com.university.forum.usermanagement.Shared.Services.ReportProducer;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final StudentRepository studentRepository;
    private final ProfessorRepository professorRepository;
    private final CloudinaryService cloudinaryService;
    private final ClassGroupRepository classGroupRepository;
    private final LevelOfStudyRepository levelOfStudyRepository;
    private final SpecialityRepository specialityRepository;
    private final BranchRepository branchRepository;
    private final DepartmentRepository departmentRepository;
    private final ReportProducer reportProducer;

    public MemberServiceImpl(MemberRepository memberRepository, StudentRepository studentRepository, ProfessorRepository professorRepository, CloudinaryService cloudinaryService, ClassGroupRepository classGroupRepository, LevelOfStudyRepository levelOfStudyRepository, SpecialityRepository specialityRepository, BranchRepository branchRepository, DepartmentRepository departmentRepository, ReportProducer reportProducer) {
        this.memberRepository = memberRepository;
        this.studentRepository = studentRepository;
        this.professorRepository = professorRepository;
        this.cloudinaryService = cloudinaryService;
        this.classGroupRepository = classGroupRepository;
        this.levelOfStudyRepository = levelOfStudyRepository;
        this.specialityRepository = specialityRepository;
        this.branchRepository = branchRepository;
        this.departmentRepository = departmentRepository;
        this.reportProducer = reportProducer;
    }

    @Override
    public MemberResponseDto getMemberMe(UUID memberId) {
        Member member=memberRepository.findById(memberId).orElseThrow(
                ()-> new UsernameNotFoundException("Member not found")
        );

        System.out.println("hhhhhhhhhhhhhhhhhhhhhhhh");
        MemberResponseDto memberResponseDto=convertMemberMeToDto(member);

        if(member instanceof  Student ){
            Student student=studentRepository.getReferenceById(memberId);
            memberResponseDto.setClassGroupId(student.getClassGroup().getId());
            memberResponseDto.setStudentNumber(student.getStudentNumber());
            ClassGroup classGroup=classGroupRepository.findById(student.getClassGroup().getId()).orElseThrow(() -> new UsernameNotFoundException("Class group not found"));
            LevelOfStudy levelOfStudy= levelOfStudyRepository.findById(classGroup.getLevelOfStudy().getId()).orElseThrow(() -> new UsernameNotFoundException("Level of study not found"));
            Speciality speciality=specialityRepository.findById(levelOfStudy.getSpeciality().getId()).orElseThrow(() -> new UsernameNotFoundException("Speciality not found"));
            Branch branch =branchRepository.findById(speciality.getBranch().getId()).orElseThrow(()->new UsernameNotFoundException("Branch not found"));
            Department department=departmentRepository.findById(branch.getDepartment().getId()).orElseThrow(()->new UsernameNotFoundException("Department not found"));
            memberResponseDto.setClassGroupName(classGroup.getName());
            memberResponseDto.setLevelOfStudyName(levelOfStudy.getName());
            memberResponseDto.setSpecialityName(speciality.getName());
            memberResponseDto.setBranchName(branch.getName());
            memberResponseDto.setDepartmentName(department.getName());
        }
        if(member instanceof  Professor){
        Professor professor=professorRepository.getReferenceById(memberId);
        memberResponseDto.setGroupIds(professor.getClassGroups().stream().map(ClassGroup::getId).collect(Collectors.toSet()));

        }

        return memberResponseDto;

    }

    @Override
    public MemberResponseDto getMemberById(UUID memberId) {
        Member member=memberRepository.findById(memberId).orElseThrow(
                ()-> new UsernameNotFoundException("Member not found")
        );

        MemberResponseDto memberResponseDto=convertMemberToDto(member);

        if(member instanceof  Student){
            memberResponseDto.setStudentNumber(((Student) member).getStudentNumber());
            Student student=studentRepository.getReferenceById(memberId);
            memberResponseDto.setClassGroupId(Optional.ofNullable(
                    student.getClassGroup())
                    .map(ClassGroup::getId)
                            .orElse(null)
                    );
            ClassGroup classGroup=classGroupRepository.findById(student.getClassGroup().getId()).orElseThrow(() -> new UsernameNotFoundException("Class group not found"));
            LevelOfStudy levelOfStudy= levelOfStudyRepository.findById(classGroup.getLevelOfStudy().getId()).orElseThrow(() -> new UsernameNotFoundException("Level of study not found"));
            Speciality speciality=specialityRepository.findById(levelOfStudy.getSpeciality().getId()).orElseThrow(() -> new UsernameNotFoundException("Speciality not found"));
            Branch branch =branchRepository.findById(speciality.getBranch().getId()).orElseThrow(()->new UsernameNotFoundException("Branch not found"));
            Department department=departmentRepository.findById(branch.getDepartment().getId()).orElseThrow(()->new UsernameNotFoundException("Department not found"));
            memberResponseDto.setClassGroupName(classGroup.getName());
            memberResponseDto.setLevelOfStudyName(levelOfStudy.getName());
            memberResponseDto.setSpecialityName(speciality.getName());
            memberResponseDto.setBranchName(branch.getName());
            memberResponseDto.setDepartmentName(department.getName());
        }
        if(member instanceof Professor){
            Professor professor=professorRepository.getReferenceById(memberId);
            memberResponseDto.setGroupIds(professor.getClassGroups().stream().map(ClassGroup::getId).collect(Collectors.toSet()));

        }

        return memberResponseDto;

    }

    @Override
    public String updateProfileImage(UUID memberId, MultipartFile image) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new UsernameNotFoundException("Member not found"));


        String oldImagePublicId = member.getProfileImagePublic_Id();
        System.out.println("Old public id is "+oldImagePublicId);

        try {
            ProfileImage newProfileImage = cloudinaryService.uploadFileToFolder(image,
                    "profileImages/" + getMemberType(member) + "/" + member.getId());

            System.out.println("New Photo URL: " + newProfileImage.getImageUrl()+"  -  "+newProfileImage.getPublicId());

            member.setProfileImageUrl(newProfileImage.getImageUrl());
            member.setProfileImagePublic_Id(newProfileImage.getPublicId());
            memberRepository.save(member);

            if (oldImagePublicId != null && !oldImagePublicId.isBlank()) {
                String resultOfDelete = cloudinaryService.deleteFile(oldImagePublicId);
                System.out.println("Result of deleting old image: " + resultOfDelete);
            }

            return newProfileImage.getImageUrl();

        } catch (IOException e) {
            throw new RuntimeException("Error processing profile image upload", e);
        }
    }

    @Override
    public void reportMember(UUID memberId, ReportRequest reportRequest) {
        if (!memberRepository.existsById(memberId)) throw new UsernameNotFoundException("Member not found");
        Member reportedMember=memberRepository.findById(reportRequest.getTargetId()).orElseThrow(()->new UsernameNotFoundException("Member reported not found"));

        ReportMessage reportMessage=new ReportMessage(
                memberId,reportRequest.getTargetId().toString(),
                reportRequest.getTargetType(),
                "Member reported full name is "+reportedMember.getFirstName()+" "+reportedMember.getLastName()+"\n and email "+reportedMember.getAddressEmail(),
                reportRequest.getReason()
                );
            reportProducer.sendReport(reportMessage);
    }

    public MemberResponseDto convertMemberMeToDto(Member member) {
        MemberResponseDto memberResponseDto=new MemberResponseDto();
        memberResponseDto.setId(member.getId());
        memberResponseDto.setFirstName(member.getFirstName());
        memberResponseDto.setLastName(member.getLastName());
        memberResponseDto.setAddressEmail(member.getAddressEmail());
        memberResponseDto.setAddress(member.getAddress());
        memberResponseDto.setCin(member.getCin());
        memberResponseDto.setDob(member.getDob());
        memberResponseDto.setSex(member.getSex());
        memberResponseDto.setPhoneNumber(member.getPhoneNumber());
        memberResponseDto.setLinkedInProfileUrl(member.getLinkedInProfileUrl());
        memberResponseDto.setProfileImageUrl(member.getProfileImageUrl());
        memberResponseDto.setMemberType(getMemberType(member));
        return memberResponseDto;
    }

    public MemberResponseDto convertMemberToDto(Member member) {
        MemberResponseDto memberResponseDto=new MemberResponseDto();
        memberResponseDto.setId(member.getId());
        memberResponseDto.setFirstName(member.getFirstName());
        memberResponseDto.setLastName(member.getLastName());
        memberResponseDto.setAddressEmail(member.getAddressEmail());
        memberResponseDto.setAddress(member.getAddress());
        memberResponseDto.setDob(member.getDob());
        memberResponseDto.setSex(member.getSex());
        memberResponseDto.setLinkedInProfileUrl(member.getLinkedInProfileUrl());
        memberResponseDto.setProfileImageUrl(member.getProfileImageUrl());
        memberResponseDto.setMemberType(getMemberType(member));
        memberResponseDto.setPhoneNumber(member.getPhoneNumber());
        memberResponseDto.setCin(member.getCin());
        return memberResponseDto;
    }

    public static MemberType getMemberType(Member member) {
        if (member instanceof Student) {
            return MemberType.STUDENT;
        } else if (member instanceof Professor) {
            return MemberType.PROFESSOR;
        } else if (member instanceof Administrator) {
            return MemberType.ADMINISTRATOR;
        } else if (member instanceof Association) {
            return MemberType.ASSOCIATION;
        } else {
            throw new IllegalArgumentException("Unknown member type");
        }
    }

}
