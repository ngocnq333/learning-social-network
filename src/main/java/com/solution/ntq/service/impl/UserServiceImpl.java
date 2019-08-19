package com.solution.ntq.service.impl;

import com.solution.ntq.common.exception.InvalidRequestException;
import com.solution.ntq.common.utils.ConvertObject;
import com.solution.ntq.controller.request.UserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solution.ntq.controller.response.UserResponse;
import com.solution.ntq.repository.entities.User;
import com.solution.ntq.repository.base.UserRepository;
import com.solution.ntq.service.base.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    /**
     *Get information of User by id
     */
    @Override
    public UserResponse getUserResponseById(String userId) {
        User user = userRepository.findById(userId);

        if (user == null) {
            return null;
        }
        return convertUserToUserResponse(user);
    }

    /**
     *Update information of User
     */
    @Override
    public void updateUser(String tokenId, UserRequest userRequest, String userId) {
        User user = userRepository.findUserByTokenIdToken(tokenId);

        if (user == null) {
            throw new InvalidRequestException("Account does not exits");
        }

        if (!user.getId().equals(userId) ) {
            throw new InvalidRequestException("Invalid information !");
        }
        user = convertUserRequestToUser(userRequest, user);
        userRepository.save(user);
    }

    private User convertUserRequestToUser(UserRequest userRequest, User userOld) {
        ObjectMapper mapper = ConvertObject.mapper();
        User newUser = mapper.convertValue(userRequest,User.class);
        newUser.setId(userOld.getId());
        newUser.setEmail(userOld.getEmail());
        newUser.setJoinDate(userOld.getJoinDate());
        newUser.setHd(userOld.getHd());
        newUser.setToken(userOld.getToken());
        newUser.setJoinEvents(userOld.getJoinEvents());
        newUser.setClazzMembers(userOld.getClazzMembers());
        newUser.setAttendances(userOld.getAttendances());
        return newUser;
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
    public List<UserResponse> findByEmailContains(String email) {
        List<User> users = userRepository.findByEmailContains(email);
        List<UserResponse> userResponses = new ArrayList<>();
        users.forEach(user -> userResponses.add(convertUserToUserResponse(user)));
        return userResponses;
    }
    private UserResponse convertUserToUserResponse(User user){
        ObjectMapper mapper = ConvertObject.mapper();
        return mapper.convertValue(user,UserResponse.class);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
    @Override
    public boolean existsUser(String userId) {
        return userRepository.existsById(userId);
    }
}
