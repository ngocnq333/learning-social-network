package com.solution.ntq.service;

import com.solution.ntq.model.User;

public interface IGoogleService {
    String getAccessTokenFormGoogle(String code);
    User getUserFormGoogle(String accessToken);
    boolean verifyUser(String code);

}
