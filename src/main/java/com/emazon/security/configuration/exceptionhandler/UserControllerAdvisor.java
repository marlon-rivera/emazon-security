package com.emazon.security.configuration.exceptionhandler;

import com.emazon.security.domain.exception.*;
import com.emazon.security.utils.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class UserControllerAdvisor {

    @ExceptionHandler(UserEmailAlreadyUsedException.class)
    public ResponseEntity<ExceptionResponse> handleUserEmailAlreadyUsedException(UserEmailAlreadyUsedException ex) {
        return ResponseEntity.badRequest().body(
                new ExceptionResponse(Constants.USER_EMAIL_ALREADY_USED, HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now())
        );
    }

    @ExceptionHandler(UserEmailNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleUserEmailNotValidException(UserEmailNotValidException ex) {
        return ResponseEntity.badRequest().body(
                new ExceptionResponse(Constants.EMAIL_NOT_VALID, HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now())
        );
    }

    @ExceptionHandler(UserIdAlreadyUsedException.class)
    public ResponseEntity<ExceptionResponse> handleUserIdAlreadyUsedException(UserIdAlreadyUsedException ex) {
        return ResponseEntity.badRequest().body(
                new ExceptionResponse(Constants.USER_ID_ALREADY_USER, HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now())
        );
    }

    @ExceptionHandler(UserIdNotValidOnlyNumericException.class)
    public ResponseEntity<ExceptionResponse> handleUserIdNotValidOnlyNumericException(UserIdNotValidOnlyNumericException ex) {
        return ResponseEntity.badRequest().body(
                new ExceptionResponse(Constants.USER_ID_NOT_VALID_ONLY_NUMERIC, HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now())
        );
    }

    @ExceptionHandler(UserNotLegalAgeException.class)
    public ResponseEntity<ExceptionResponse> handleUserNotLegalAgeException(UserNotLegalAgeException ex) {
        return ResponseEntity.badRequest().body(
                new ExceptionResponse(Constants.USER_NOT_LEGAL, HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now())
        );
    }

    @ExceptionHandler(UserPhoneNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleUserPhoneNotValidException(UserPhoneNotValidException ex) {
        return ResponseEntity.badRequest().body(
                new ExceptionResponse(Constants.USER_PHONE_NOT_VALID, HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now())
        );
    }

    @ExceptionHandler(UserIncorrectPasswordException.class)
    public ResponseEntity<ExceptionResponse> handleUserIncorrectPasswordException(UserIncorrectPasswordException ex) {
        return ResponseEntity.badRequest().body(
                new ExceptionResponse(Constants.USER_EMAIL_OR_PASSWORD_INCORRECT, HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now())
        );
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.badRequest().body(
                new ExceptionResponse(Constants.USER_EMAIL_OR_PASSWORD_INCORRECT, HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now())
        );
    }

}
