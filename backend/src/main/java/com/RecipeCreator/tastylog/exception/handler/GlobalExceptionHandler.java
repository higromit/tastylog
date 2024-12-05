package com.RecipeCreator.tastylog.exception.handler;

import com.RecipeCreator.tastylog.exception.RecipeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 유효성 검증 실패 처리
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleValidationExceiptions(MethodArgumentNotValidException ex){
        Map<String,String> errors = new HashMap<>();
        for(FieldError error: ex.getBindingResult().getFieldErrors()){
            errors.put(error.getField(), error.getDefaultMessage());

        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errors);
    }

    /**
     * 레시피 크롤링 exception 처리
     * @param ex
     * @return
     */
    @ExceptionHandler(RecipeException.class)
    public ResponseEntity<Map<String,String>> handleRecipeCrawlingException(RecipeException ex){
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("errorCode",ex.getErrorCode());
        errorResponse.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * 기타 예외 처리
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다: " + ex.getMessage());
    }
}
