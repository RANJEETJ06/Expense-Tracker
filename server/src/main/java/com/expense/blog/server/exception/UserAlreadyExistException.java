package com.expense.blog.server.exception;

public class UserAlreadyExistException extends RuntimeException{
    String Field;
    String FieldValue;
    public UserAlreadyExistException(String Field,String FieldValue){
        super(String.format("User is Already Exist with %s:%s",FieldValue,Field));
        this.Field=Field;
        this.FieldValue=FieldValue;
    }
}
