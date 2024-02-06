package com.ghisthub.demo.User;

import com.ghisthub.demo.error.PasswordDoesNotMatchException;

public class UserServiceImpl implements UserService {
    @Override
    public User createNewUser(User body) throws PasswordDoesNotMatchException {
        return null;
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        return null;
    }
}
