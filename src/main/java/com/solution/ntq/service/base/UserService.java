package com.solution.ntq.service.base;
import com.solution.ntq.controller.request.UserRequest;
import com.solution.ntq.controller.response.UserResponse;
import com.solution.ntq.repository.entities.User;
import java.util.List;

/**
 * @author Nam_Phuong
 * Delear user service
 * Date update 24/7/2019
 */
public interface UserService {
    User getUserById(String id);

    UserResponse getUserResponseById(String userId);

    User getUserByTokenId(String id);

    List<User> findAll();

    boolean existsUser(String userId);

    List<UserResponse> findByEmailContains(String email);

    void updateUser(String tokenId, UserRequest userRequest, String userId);
}
