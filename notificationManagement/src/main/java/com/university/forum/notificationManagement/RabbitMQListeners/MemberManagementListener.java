package com.university.forum.notificationManagement.RabbitMQListeners;


import com.university.forum.notificationManagement.Dtos.Messages.ProfessorMessage;
import com.university.forum.notificationManagement.Dtos.Messages.StudentMessage;
import com.university.forum.notificationManagement.Exceptions.ElementAlreadyExistsException;
import com.university.forum.notificationManagement.Services.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

@Service
public class MemberManagementListener {
    private static final Logger logger = LoggerFactory.getLogger(MemberManagementListener.class);
    private final MemberService memberService;

    public MemberManagementListener(MemberService memberService) {
        this.memberService = memberService;
    }

    @RabbitListener(queues = "member-professor-created-queue")
    public void handleProfessorCreated(ProfessorMessage professorMessage) {
        logger.info("👨‍🏫 Received professor: {}" , professorMessage.toString());
        try {
            logger.info("🎓 Received professor: {} ", professorMessage);
            memberService.createMember(professorMessage);
        } catch (ElementAlreadyExistsException e) {
            logger.warn("⚠️ Professor already exists: {}", e.getMessage());
        }
        catch (BadCredentialsException e){
            logger.warn("❌ Professor message is bad: {}", e.getMessage());
        }
        catch (DataIntegrityViolationException e) {
            logger.warn("⚠️ Data integrity violation: {}", e.getMessage());
        }
        catch (Exception e) {
            logger.error("❌ Unexpected error while creating student member", e);
            throw e;
        }

    }

    @RabbitListener(queues = "member-student-created-queue")
    public void handleStudentCreated(StudentMessage studentMessage) {
       logger.info("🎓 Received student: {} " , studentMessage.toString());
        try {
            logger.info("🎓 Received student: {} ", studentMessage);
            memberService.createMember(studentMessage);
        } catch (ElementAlreadyExistsException e) {
            logger.warn("⚠️ Student already exists: {}", e.getMessage());
        }
        catch (BadCredentialsException e){
            logger.warn("❌ Student message is bad: {}", e.getMessage());
        }
        catch (DataIntegrityViolationException e) {
            logger.warn("⚠️ Data integrity violation: {}", e.getMessage());
        }catch (Exception e) {
            logger.error("❌ Unexpected error while creating student member", e);
            throw e;
        }
    }

}
