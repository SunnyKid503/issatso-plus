package com.university.forum.usermanagement.AdminManagement.ExceptionHandler;

public class MemberBannedException extends RuntimeException{
    public MemberBannedException(String msg){
        super(msg);
    }
}
