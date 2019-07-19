
package com.solution.ntq.service.impl;

import com.solution.ntq.common.GoogleUtils;
import com.solution.ntq.model.User;
import com.solution.ntq.service.IGoogleService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@NoArgsConstructor
@AllArgsConstructor
@Service
public class GoogleServiceImpl implements IGoogleService {
    private static final String NTQ_EMAIL_FORM = "ntq-solution.com.vn";
    @Autowired
    private GoogleUtils googleUtils;

    @Override
    public boolean activeLoginToEmail(String code) {
        if (isCode(code)){
            return false;
        } else {
            String accessToken = getAccessTokenFormGoogle(code);
            User user = getUserFormGoogle(accessToken); // can phai kiem tra su ton tai cua user trong bo nho token va dababase
            return verifyUserNTQ(user);
        }
    }

    /**
     * Verify email of ntq
     */

    @Override
    public boolean verifyUserNTQ(User user) {
        try {
            String suffixEmail = user.getHd();
            return (isNTQMail(suffixEmail));
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public String getAccessTokenFormGoogle(String code) {
        try {
            return googleUtils.getToken(code);
        } catch (IOException ex) {
            return null;
        }
    }

    /**
     * Get user information form google
     */

    @Override
    public User getUserFormGoogle(String accessToken) {
        try {
            return googleUtils.getUserInfo(accessToken);
        } catch (IOException ex) {
            return null;
        }
    }

    /**
     * Verify mail ntq with mail gmail
     */

    public boolean isNTQMail(String userEmail) {
        return (NTQ_EMAIL_FORM.equalsIgnoreCase(userEmail));
    }

    /**
     * Check code recovery code form google
     */

    public boolean isCode(String code) {
        return (code == null || code.isEmpty());
    }
}

