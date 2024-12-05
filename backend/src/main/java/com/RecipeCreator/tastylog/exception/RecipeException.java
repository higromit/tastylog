package com.RecipeCreator.tastylog.exception;

public class RecipeException extends RuntimeException{
    private final String errorCode;

    public RecipeException(String errorCode, String message){
        super(message);
        this.errorCode=errorCode;
    }
    public String getErrorCode(){
        return errorCode;
    }
}
