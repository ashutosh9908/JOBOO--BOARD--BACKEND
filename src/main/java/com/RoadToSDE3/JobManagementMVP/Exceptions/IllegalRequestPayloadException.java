package com.RoadToSDE3.JobManagementMVP.Exceptions;

public class IllegalRequestPayloadException extends RuntimeException{

    public IllegalRequestPayloadException(String message){
        super(message);
    }
}
