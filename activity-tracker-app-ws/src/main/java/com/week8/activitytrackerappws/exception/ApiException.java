package com.week8.activitytrackerappws.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class ApiException{
       private  String message;
       private HttpStatus httpStatus;
       private ZonedDateTime zonedDateTime;
}
