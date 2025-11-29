package com.university.forum.usermanagement.Services;

import com.university.forum.forummanagement.membership.models.Student;
import com.university.forum.forummanagement.shared.exceptions.ElementAlreadyExistsException;
import com.university.forum.usermanagement.Dtos.Message.StudentMessage;

public interface StudentService {

    void createStudent(StudentMessage studentMessage) throws ElementAlreadyExistsException;
}
