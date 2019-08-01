package com.solution.ntq.common.validator;

import com.solution.ntq.common.constant.Level;
import com.solution.ntq.controller.request.ContentRequest;
import com.solution.ntq.controller.response.Response;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Date;


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
        if (content.getStartDate() == null || content.getStartDate().before(new Date())) {
            errors.rejectValue("startDate", "Date not null  ");
        }
        if (content.getEndDate() == null || content.getEndDate().before(content.getStartDate())) {
            errors.rejectValue("endDate", "Date not null  ");
        }
        if(content.getTitle()==null || content.getTitle().length()>65)
        {
            errors.rejectValue("title","tile not null");
        }
        if (!content.getLevel().equalsIgnoreCase(Level.BEGINNER.value()) && !content.getLevel().equalsIgnoreCase(Level.INTERMEDISE.value())
                && !content.getLevel().equalsIgnoreCase(Level.EXPERT.value())) {
            errors.rejectValue("level", "level not match");
        }

    }

    public static boolean isValidContentRequest(BindingResult bindingResult, ContentRequest contentRequest, Response<ContentRequest> contentRequestResponse) {

      new ContentValidator().validate(contentRequest, bindingResult);
        if (bindingResult.hasFieldErrors()) {

            return false;
        }


        return true;

    }
}
