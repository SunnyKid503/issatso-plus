package com.university.forum.usermanagement.Services;

import com.university.forum.forummanagement.shared.exceptions.ElementAlreadyExistsException;
import com.university.forum.usermanagement.Dtos.Message.ProfessorMessage;

public interface ProfessorService {
    void createProfessor(ProfessorMessage professorMessage)throws ElementAlreadyExistsException;
}
