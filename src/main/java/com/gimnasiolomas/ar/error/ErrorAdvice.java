package com.gimnasiolomas.ar.error;

import com.twilio.exception.ApiException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestControllerAdvice
public class ErrorAdvice {


    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFoundException(NotFoundException e, HttpServletRequest request){
        return new ApiError(404, e.getMessage(), request.getServletPath());
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNullPointerException(NullPointerException e, HttpServletRequest request){
        return new ApiError(404, "No encontrado", request.getServletPath());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleDataIntegrityViolationException(DataIntegrityViolationException e, HttpServletRequest request){
        return new ApiError(400, e.getMessage(), request.getServletPath());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleConstraintViolationException(ConstraintViolationException e, HttpServletRequest request){
        ApiError apiError = new ApiError(400, e.getMessage(), request.getServletPath());
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        Map<String, String> validationErrors = new HashMap<>();
        for(ConstraintViolation<?> error: constraintViolations){
            validationErrors.put(error.getPropertyPath().toString(), error.getMessage());
        }
        apiError.setValidationErrors(validationErrors);
        return apiError;
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleBadCredentialsException(BadCredentialsException e, HttpServletRequest request){
        return new ApiError(400, "Username or Password incorrect", request.getServletPath());
//        return new ApiError(400, e.getMessage(), request.getServletPath());
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidationError(ValidationException e, HttpServletRequest request){
        return new ApiError(400, e.getMessage(), request.getServletPath());
    }
    @ExceptionHandler(UnderLegalAgeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleUnderLegalAgeException(UnderLegalAgeException e, HttpServletRequest request){
        return new ApiError(400, e.getMessage(), request.getServletPath());
    }
    @ExceptionHandler(PlanNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handlePlanNotFoundException(PlanNotFoundException e, HttpServletRequest request){
        return new ApiError(404, e.getMessage(), request.getServletPath());
    }
    @ExceptionHandler(ActivePlanException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleActivePlanException(ActivePlanException e, HttpServletRequest request){
        return new ApiError(400, e.getMessage(), request.getServletPath());
    }
    @ExceptionHandler(ActivityAlreadyScheduledException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleActivityAlreadyScheduledException(ActivityAlreadyScheduledException e, HttpServletRequest request){
        return new ApiError(400, e.getMessage(), request.getServletPath());
    }
    @ExceptionHandler(ActivityAlreadyExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleActivityAlreadyExistException(ActivityAlreadyExistException e, HttpServletRequest request){
        return new ApiError(400, e.getMessage(), request.getServletPath());
    }
    @ExceptionHandler(NoActivityFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNoActivityFoundException(NoActivityFoundException e, HttpServletRequest request){
        return new ApiError(404, e.getMessage(), request.getServletPath());
    }
    @ExceptionHandler(NoGymClassesLeftException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleNoGymClassesLeftException(NoGymClassesLeftException e, HttpServletRequest request){
        return new ApiError(400, e.getMessage(), request.getServletPath());
    }
    @ExceptionHandler(HolidayException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleHolidayException(HolidayException e, HttpServletRequest request){
        return new ApiError(400, e.getMessage(), request.getServletPath());
    }
    @ExceptionHandler(CancelActivityScheduleException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleCancelActivityScheduleException (CancelActivityScheduleException e, HttpServletRequest request){
        return new ApiError(400, e.getMessage(), request.getServletPath());
    }
    @ExceptionHandler(CancelInscriptionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleCancelInscriptionException (CancelInscriptionException e, HttpServletRequest request){
        return new ApiError(400, e.getMessage(), request.getServletPath());
    }
    @ExceptionHandler(MaxNumberOfMembersReachedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleMaxNumberOfMembersReachedException(MaxNumberOfMembersReachedException e, HttpServletRequest request){
        return new ApiError(400, e.getMessage(), request.getServletPath());
    }
    @ExceptionHandler(ApiException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleApiException(ApiException e, HttpServletRequest request){
        return new ApiError(400, e.getMessage(), request.getServletPath());
    }
}