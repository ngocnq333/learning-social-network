package com.solution.ntq.service.base;

import com.solution.ntq.model.User;

/**
 * @author Nam_Phuong
 * Delear user service
 * Date update 24/7/2019
 */
public interface UserService {
    User getUserById(String id);
    User getUserByTokenId(String id);
}
