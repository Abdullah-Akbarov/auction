package service;

import model.Message;

public interface UserService {
    Message KeyGenerator(String username, String password);
}
