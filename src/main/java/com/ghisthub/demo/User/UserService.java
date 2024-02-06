package com.ghisthub.demo.User;

import com.ghisthub.demo.error.PasswordDoesNotMatchException;

public interface UserService {
    User createNewUser (User body) throws PasswordDoesNotMatchException;
    User findUserByEmail(String email) throws Exception;
}
