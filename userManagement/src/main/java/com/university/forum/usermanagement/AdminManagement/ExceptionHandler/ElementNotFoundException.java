package com.university.forum.usermanagement.AdminManagement.ExceptionHandler;


public class ElementNotFoundException extends RuntimeException {
        public ElementNotFoundException(String msg) {
            super(msg);
        }
}