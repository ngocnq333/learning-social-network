package com.solution.ntq.service.impl;

import com.solution.ntq.model.User;
import com.solution.ntq.repository.IUserRepository;
import com.solution.ntq.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Nam_Phuong
 * Delear user service
 * Date update 24/7/2019
 */

@Service
@AllArgsConstructor
public class UserServiceImpl implements IUserService {
    private IUserRepository userRepository;

    /**
     * Get an user with id
     */
    public User getUserById(String id) {
        try {
            return userRepository.findById(id);
        } catch (Exception e) {
            return null;
        }
    }
}
