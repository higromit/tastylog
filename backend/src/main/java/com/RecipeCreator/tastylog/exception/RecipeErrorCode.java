package com.RecipeCreator.tastylog.exception;

public enum RecipeErrorCode {
    MEMBER_NOT_FOUND("MEMBER_NOT_FOUND", "멤버를 찾을 수 없습니다."),
    RECIPE_NOT_FOUND("RECIPE_NOT_FOUND", "레시피를 찾을 수 없습니다"),
    INVALID_URL("INVALID_URL","잘못된 웹사이트 URL입니다."),
    API_RESPONSE_ERROR("API_RESPONSE_ERROR","API 응답 오류가 발생했습니다."),
    CATEGORY_NOT_FOUND("CATEGORY_NOT_FOUND", "카테고리를 찾을 수 없습니다");


    private final String code;
    private final String message;

    RecipeErrorCode(String code, String message){
        this.code= code;
        this.message = message;
    }
    public String getCode(){
        return code;
    }

    public String getMessage(){
        return message;
    }
}
