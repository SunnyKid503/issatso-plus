package com.university.forum.usermanagement.Shared.Config;

import com.university.forum.usermanagement.ClassGroupManagement.Models.*;
import com.university.forum.usermanagement.ClassGroupManagement.Repositories.*;
import com.university.forum.usermanagement.MemberManagement.Models.Role;
import com.university.forum.usermanagement.MemberManagement.Models.Student;
import com.university.forum.usermanagement.MemberManagement.Repositories.ProfessorRepository;
import com.university.forum.usermanagement.MemberManagement.Repositories.RoleRepository;
import com.university.forum.usermanagement.MemberManagement.Repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;

@Configuration
public class SeedDatabase {
    @Autowired
    ClassGroupRepository classGroupRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    SpecialityRepository specialityRepository;
    @Autowired
    LevelOfStudyRepository levelOfStudyRepository;
    @Autowired
    BranchRepository branchRepository;
    @Autowired
    RoleRepository roleRepository;

    @Bean
    public CommandLineRunner SeedDb() {
        return arg -> {
            Timestamp now = new Timestamp(System.currentTimeMillis());

            // 1. Check if roles exist, if not, create them
            if (roleRepository.count() == 0) {
                Role professorRole = new Role();
                professorRole.setName("STUDENT");

                roleRepository.save(professorRole);
            }

            Set<Role> studentRoles = new HashSet<>(List.of(roleRepository.findAll().get(0)));

            // 2. Check if department exists, if not, create it
            if (departmentRepository.count() == 0) {
                Department computerScienceDept = new Department("Computer Science", "CS");
                departmentRepository.save(computerScienceDept);
            }



            // 3. Check if branch exists, if not, create it
//            if (branchRepository.count() == 0) {
//                Branch softwareEngineeringBranch = new Branch(1, "Software Engineering", "SE", new ArrayList<>(), departmentRepository.findAll().get(0));
//                branchRepository.save(softwareEngineeringBranch);
//            }
//
//            // 4. Check if speciality exists, if not, create it
//            if (specialityRepository.count() == 0) {
//                Speciality webDevelopmentSpeciality = new Speciality(2, "Web Development", "WD", new ArrayList<>(), branchRepository.findAll().get(0));
//                specialityRepository.save(webDevelopmentSpeciality);
//            }
//
//            // 5. Check if level of study exists, if not, create it
//            if (levelOfStudyRepository.count() == 0) {
//                LevelOfStudy bachelorLevel = new LevelOfStudy(3, "Bachelor", "B", new ArrayList<>(), specialityRepository.findAll().get(0));
//                levelOfStudyRepository.save(bachelorLevel);
//            }
//
//            // 6. Check if class group exists, if not, create it
//            if (classGroupRepository.count() == 0) {
//                ClassGroup groupA = new ClassGroup(4, "Group A", "G-A", new ArrayList<>(), new ArrayList<>(), levelOfStudyRepository.findAll().get(0));
//                classGroupRepository.save(groupA);
//            }

            // 7. Check if students exist, if not, create them
            if (studentRepository.count() == 0) {
                Student student1 = new Student();

                //student1.setId(UUID.randomUUID());
                student1.setFirstName("John");
                student1.setLastName("Doe");
                student1.setAddressEmail("john.doe@email.com");
                student1.setCin("11111111");
                student1.setAddress("123 Main St");
                student1.setLinkedInProfileUrl("linkedin.com/johndoe");
                student1.setPhoneNumber("14500222");
                student1.setSex(true);
                student1.setDob(LocalDate.of(2000, 5, 15));
                student1.setCreationDate(now);
                student1.setUpdateDate(now);
                student1.setStudentNumber("NUMB1");


                studentRepository.save(student1);
            }
        };
    }
}
