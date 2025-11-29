package com.university.forum.usermanagement.RabbitMQListeners;

import com.university.forum.forummanagement.shared.exceptions.ElementAlreadyExistsException;
import com.university.forum.forummanagement.shared.exceptions.ElementNotFoundException;
import com.university.forum.usermanagement.Dtos.Message.ProfessorMessage;
import com.university.forum.usermanagement.Dtos.Message.StudentMessage;
import com.university.forum.usermanagement.Services.ProfessorService;
import com.university.forum.usermanagement.Services.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class MemberManagementListener {
    private static final Logger logger = LoggerFactory.getLogger(MemberManagementListener.class);
    private final StudentService studentService;
    private final ProfessorService professorService;

    public MemberManagementListener(StudentService studentService, ProfessorService professorService) {
        this.studentService = studentService;
        this.professorService = professorService;
    }

    @RabbitListener(queues = "forum-member-professor-created-queue")
    public void handleProfessorCreated(ProfessorMessage professorMessage) {
        logger.info("👨‍🏫 Forum MS Received professor: {}", professorMessage.toString());
        try {
            professorService.createProfessor(professorMessage);
            logger.info("Professor Created successfully {}", professorMessage);
        }catch (IllegalArgumentException ex){
            logger.warn("❌ Student message is missing : {}", ex.getMessage());

        }
        catch (DataIntegrityViolationException e) {
            logger.warn("⚠️ Data integrity violation: {}", e.getMessage());
        }
        catch (ElementAlreadyExistsException e){
            logger.warn("❌ Student creation failed due to : {}",e.getMessage());
        }
    }

    @RabbitListener(queues = "forum-member-student-created-queue")
    public void handleStudentCreated(StudentMessage studentMessage) {
        logger.info("🎓 Forum MS Received student: {}", studentMessage.toString());
        try {
            studentService.createStudent(studentMessage);
            logger.info("Student Created successfully {}", studentMessage);
        }catch (IllegalArgumentException ex){
            logger.warn("❌ Student message is missing : {}", ex.getMessage());

        }
        catch (DataIntegrityViolationException e) {
            logger.warn("⚠️ Data integrity violation: {}", e.getMessage());
        }
        catch (ElementAlreadyExistsException e){
            logger.warn("❌ Student creation failed due to : {}",e.getMessage());
        }
    }
}
