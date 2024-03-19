package com.practice.DbinheritanceDemo.exceptions;

import com.practice.DbinheritanceDemo.payloads.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RestControllerAdvice
public class GlobalControllerAdvice {

   @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> HandleResourceNotFoundException(ResourceNotFoundException ex){
        String message=ex.getMessage();
        ApiResponse apiResponse = new ApiResponse(message, false);
       return new ResponseEntity<>(apiResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
   public ResponseEntity<Map<String,String>> handleMethodArgsNotValidException(MethodArgumentNotValidException ex){
       Map<String,String> resp=new HashMap<>();
       ex.getBindingResult().getAllErrors().forEach((error)->{
           String fieldName =((FieldError)error).getField();
           String message =error.getDefaultMessage();
           resp.put(fieldName,message);
       });
       return new ResponseEntity<>(resp,HttpStatus.BAD_REQUEST);
   }
   @ExceptionHandler(ConstraintViolationException.class)
   public ResponseEntity<Set<ApiResponse>> handleConstraintViolationException(ConstraintViolationException ex){
       Set<ApiResponse> resp = new HashSet<>();

       ex.getConstraintViolations().forEach((error)->{
           ApiResponse r1= new ApiResponse(error.getMessage(), false);
           resp.add(r1);

       });
       return  new ResponseEntity<>(resp,HttpStatus.BAD_REQUEST);
   }
}
