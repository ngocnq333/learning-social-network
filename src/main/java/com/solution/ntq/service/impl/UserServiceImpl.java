package com.solution.ntq.service.impl;

import com.solution.ntq.repository.entities.User;
import com.solution.ntq.repository.base.UserRepository;
import com.solution.ntq.service.base.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Nam_Phuong
 * Delear user service
 * Date update 24/7/2019
 */

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

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
