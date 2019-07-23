package com.solution.ntq.service.impl;

import com.solution.ntq.model.User;
import com.solution.ntq.repository.IUserRepository;
import com.solution.ntq.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService implements IUserService {
    private IUserRepository userRepository;

    /** Get an user with id*/
    public User getUserById(String id) {
        try {
            return userRepository.findById(id);
        }catch (Exception e){
            return null;
        }
    }
}
