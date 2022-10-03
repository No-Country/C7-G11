package com.gimnasiolomas.ar.error;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ErrorHandler implements ErrorController {
    @Autowired
    private ErrorAttributes errorAttributes;

    @RequestMapping("/error")
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleError(WebRequest webRequest){
        Map<String, Object> attributes =errorAttributes.getErrorAttributes(webRequest, ErrorAttributeOptions.of(ErrorAttributeOptions.Include.MESSAGE, ErrorAttributeOptions.Include.BINDING_ERRORS));
        String message = (String)attributes.get("message");
        String path = (String) attributes.get("path");
        int status =(int) attributes.get("status");
        ApiError apiError = new ApiError(status, message, path);
        if(attributes.containsKey("errors")){
            @SuppressWarnings("unchecked")
            List<FieldError> fieldErrors = (List<FieldError>) attributes.get("errors");
            Map<String, String> validatioErrors = new HashMap<>();
            for(FieldError fieldError : fieldErrors){
                validatioErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            apiError.setValidationErrors(validatioErrors);
        }
        return apiError;
    }
}
