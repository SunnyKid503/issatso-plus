package com.university.forum.notificationManagement.Exceptions;

public class ElementAlreadyExistsException extends RuntimeException{
    public ElementAlreadyExistsException(String  msg){
        super(msg);
    }
}
