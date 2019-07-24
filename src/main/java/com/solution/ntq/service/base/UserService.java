package com.solution.ntq.service.base;

import com.solution.ntq.repository.entities.User;

/**
 * @author Nam_Phuong
 * Delear user service
 * Date update 24/7/2019
 */
public interface UserService {
    User getUserById(String id);
}
