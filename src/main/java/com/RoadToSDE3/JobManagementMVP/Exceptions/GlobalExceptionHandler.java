package com.RoadToSDE3.JobManagementMVP.Exceptions;


import com.RoadToSDE3.JobManagementMVP.Models.DTOs.ErrResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrResponse> handleResourceNotFoundException(ResourceNotFoundException e){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        ErrResponse
                                .builder()
                                .message(e.getMessage())
                                .time(LocalDate.now())
                                .httpStatus(HttpStatus.NOT_FOUND)
                                .build()
                );
    }

    @ExceptionHandler(IllegalRequestPayloadException.class)
    public ResponseEntity<ErrResponse> handleIllegalRequestPayloadException(IllegalRequestPayloadException e){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        ErrResponse
                                .builder()
                                .message(e.getMessage())
                                .time(LocalDate.now())
                                .httpStatus(HttpStatus.NOT_FOUND)
                                .build()
                );
    }

    @ExceptionHandler(EncodeDecodeException.class)
    public ResponseEntity<ErrResponse> handleEncodeDecodeException(EncodeDecodeException e){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        ErrResponse
                                .builder()
                                .message(e.getMessage())
                                .time(LocalDate.now())
                                .httpStatus(HttpStatus.NOT_FOUND)
                                .build()
                );
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrResponse> handleGeneralException(Exception e){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        ErrResponse
                                .builder()
                                .message("Internal Exception Occured")
                                .time(LocalDate.now())
                                .httpStatus(HttpStatus.NOT_FOUND)
                                .build()
                );
    }
}
