package com.solution.ntq.common.validator;

import com.solution.ntq.common.constant.Level;
import com.solution.ntq.controller.request.ContentRequest;

import java.util.Date;


public class Validator {
    private Validator() {
    }


    public static boolean isValidContentRequest(ContentRequest contentRequest) {

        if (contentRequest.getStartDate().before(new Date())) {
            return false;
        }
        if (contentRequest.getEndDate().before(contentRequest.getStartDate())) {
            return false;
        }

        return !(!contentRequest.getLevel().equalsIgnoreCase(Level.BEGINNER.value()) && !contentRequest.getLevel().equalsIgnoreCase(Level.INTERMEDISE.value())
                && !contentRequest.getLevel().equalsIgnoreCase(Level.EXPERT.value()));

    }
}
