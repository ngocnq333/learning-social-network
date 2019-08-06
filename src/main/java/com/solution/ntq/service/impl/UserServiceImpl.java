package com.solution.ntq.service.impl;

import com.solution.ntq.repository.ClazzMemberRepository;
import com.solution.ntq.repository.entities.User;
import com.solution.ntq.repository.UserRepository;
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
    private ClazzMemberRepository clazzMemberRepository;
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


    /**
     * Get an user with id
     */
    public User getUserByTokenId(String id) {
        try {
            return userRepository.findUserByTokenIdToken(id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean existsUser(String userId) {
        return userRepository.existsById(userId);
    }
}
