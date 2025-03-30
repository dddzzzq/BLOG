package com.example.databases.exception;

public class ServiceException extends RuntimeException {
    private  String code;
    public  ServiceException(String msg){
        super(msg);
    }
    public  ServiceException(String code,String msg){
        super(msg);
        this.code=code;
    }

    public String getCode() {
        return code;
    }
}
