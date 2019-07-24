package com.solution.ntq.service.validator;

import com.solution.ntq.common.constant.ResponseCode;
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
        if (content.getStartDate() == null || content.getStartDate().after(new Date())) {
            errors.rejectValue("date", "Date not null must be ");
        }


    }

    public static boolean isValidContentRequest(BindingResult bindingResult, ContentRequest contentRequest, Response<ContentRequest> contentRequestResponse) {

      new ContentValidator().validate(contentRequest, bindingResult);
        if (bindingResult.hasFieldErrors()) {
            contentRequestResponse.setCodeStatus(ResponseCode.BAD_REQUEST.value());
            return false;
        }

        contentRequestResponse.setCodeStatus(ResponseCode.OK.value());
        contentRequestResponse.setData(contentRequest);
        return true;

    }
}
