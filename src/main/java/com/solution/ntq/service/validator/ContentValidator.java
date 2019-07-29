package com.solution.ntq.service.validator;

import com.solution.ntq.controller.request.ContentRequest;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;



public class ContentValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return ContentRequest.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ContentRequest content = (ContentRequest) o;
        if (content.getContent() == null) {
            errors.rejectValue("content", "content not null");
        }



    }
}
