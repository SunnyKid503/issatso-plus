package com.university.forum.forummanagement.forums.config;

import com.university.forum.forummanagement.forums.models.*;
import com.university.forum.forummanagement.forums.repositories.CommentRepository;
import com.university.forum.forummanagement.forums.repositories.InteractionRepository;
import com.university.forum.forummanagement.forums.repositories.PostRepository;
import com.university.forum.forummanagement.membership.models.Professor;
import com.university.forum.forummanagement.membership.models.Role;
import com.university.forum.forummanagement.membership.models.Student;
import com.university.forum.forummanagement.membership.models.enums.MemberType;
import com.university.forum.forummanagement.membership.repositories.ProfessorRepository;
import com.university.forum.forummanagement.membership.repositories.RoleRepository;
import com.university.forum.forummanagement.membership.repositories.StudentRepository;
import com.university.forum.forummanagement.structures.models.*;
import com.university.forum.forummanagement.structures.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;
@Configuration
public class SeedDatabase {
    @Autowired
    ClassGroupRepository classGroupRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    ProfessorRepository professorRepository;
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
    @Autowired
    PostRepository postRepository;
    @Autowired
    InteractionRepository interactionRepository;
    @Autowired
    CommentRepository commentRepository;

    @Bean
    public CommandLineRunner SeedDb() {
        return arg -> {
            Timestamp now = new Timestamp(System.currentTimeMillis());

            // 1. Check if roles exist, if not, create them
            if (roleRepository.count() == 0) {
                Role adminRole = new Role();
                adminRole.setName("ADMINISTRATOR");
                Role studentRole = new Role();
                studentRole.setName("STUDENT");
                Role professorRole = new Role();
                professorRole.setName("PROFESSOR");

                roleRepository.saveAll(List.of(adminRole, studentRole, professorRole));
            }

            Set<Role> studentRoles = new HashSet<>(List.of(roleRepository.findByName("STUDENT")));
            Set<Role> professorRoles = new HashSet<>(List.of(roleRepository.findByName("PROFESSOR")));
            Set<Role> adminRoles = new HashSet<>(List.of(roleRepository.findByName("ADMINISTRATOR")));

            // 2. Check if department exists, if not, create it
            if (departmentRepository.count() == 0) {
                Department computerScienceDept = new Department("Computer Science", "CS");
                departmentRepository.save(computerScienceDept);
            }



            // 3. Check if branch exists, if not, create it
            if (branchRepository.count() == 0) {
                Branch softwareEngineeringBranch = new Branch(1, "Software Engineering", "SE", new ArrayList<>(), departmentRepository.findAll().get(0));
                branchRepository.save(softwareEngineeringBranch);
            }

            // 4. Check if speciality exists, if not, create it
            if (specialityRepository.count() == 0) {
                Speciality webDevelopmentSpeciality = new Speciality(2, "Web Development", "WD", new ArrayList<>(), branchRepository.findAll().get(0));
                specialityRepository.save(webDevelopmentSpeciality);
            }

            // 5. Check if level of study exists, if not, create it
            if (levelOfStudyRepository.count() == 0) {
                LevelOfStudy bachelorLevel = new LevelOfStudy(3, "Bachelor", "B", new ArrayList<>(), specialityRepository.findAll().get(0));
                levelOfStudyRepository.save(bachelorLevel);
            }

            // 6. Check if class group exists, if not, create it
            if (classGroupRepository.count() == 0) {
                ClassGroup groupA = new ClassGroup(4, "Group A", "G-A", new ArrayList<>(), new ArrayList<>(), levelOfStudyRepository.findAll().get(0));
                classGroupRepository.save(groupA);
            }

            // 7. Check if students exist, if not, create them
            if (studentRepository.count() == 0) {
                Student student1 = new Student(
                        UUID.randomUUID(), "John", "Doe", "john.doe@email.com",
                        "11111111", "123 Main St", "linkedin.com/johndoe",
                        "image.url/johndoe", "14500222", true,
                        LocalDate.of(2000, 5, 15), now, now, MemberType.STUDENT,
                        null, "STNUM001", null
                );

                Student student2 = new Student(
                        UUID.randomUUID(), "Jane", "Smith", "jane.smith@email.com",
                        "22222222", "456 Oak Ave", "linkedin.com/janesmith",
                        "image.url/janesmith", "14025566", false,
                        LocalDate.of(1999, 8, 22), now, now, MemberType.STUDENT,
                        null, "STNUM002", null
                );
                studentRepository.saveAll(List.of(student1, student2));
            }

            // 8. Check if professors exist, if not, create them
            if (professorRepository.count() == 0) {
                Professor professor1 = new Professor(
                        UUID.randomUUID(), "Robert", "Johnson", "robert.johnson@email.com",
                        "55512345", "789 University Blvd", "linkedin.com/robertjohnson",
                        "image.url/robertjohnson", "14022003", true,
                        LocalDate.of(1975, 3, 10), now, now, MemberType.PROFESSOR,
                        null, null
                );

                Professor professor2 = new Professor(
                        UUID.randomUUID(), "Emily", "Williams", "emily.williams@email.com",
                        "55598765", "321 College St", "linkedin.com/emilywilliams",
                        "image.url/emilywilliams", "14055004", false,
                        LocalDate.of(1980, 11, 5), now, now, MemberType.PROFESSOR,
                        null, null
                );
                professorRepository.saveAll(List.of(professor1, professor2));
            }

            if (postRepository.count() <20) {
                for (int i = 1; i <= 20; i++) {
                    Post p = new Post(0, 0, 0, 0,0,
                            "This is post number " + i,
                            "Title " + i,
                            studentRepository.findAll().get(0),
                            null, now, now,
                            null, null, null, PostType.POST);

                postRepository.save(p);

                   // UserPostInteraction interaction = new UserPostInteraction(
                    //  new UserPostInteractionId(studentRepository.findAll().get(0).getId(), p.getId()),
                    //  true, false, true, false, null, now);

                    // interactionRepository.save(interaction);
                }

            }
        };
    }
}
