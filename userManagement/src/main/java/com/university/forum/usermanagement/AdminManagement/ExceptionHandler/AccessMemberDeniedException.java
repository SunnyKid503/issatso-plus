package com.university.forum.usermanagement.AdminManagement.ExceptionHandler;

public class AccessMemberDeniedException extends RuntimeException{
    public AccessMemberDeniedException(String msg){
        super(msg);
    }
}
