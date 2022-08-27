package service.impl;

import dao.UserDao;
import dao.impl.UserDaoImpl;
import model.Message;
import model.User;
import service.UserService;

import java.sql.SQLException;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static UserService userService;
    private final UserDao userDao = UserDaoImpl.getUserDao();

    public static UserService getUserService(){
        if (userService==null){
            userService = new UserServiceImpl();
        }
        return userService;
    }
    @Override
    public Message KeyGenerator(String username, String password) {
        Message message ;
        Optional<User> userOptional = userDao.findByUsernameAndPassword(username, password);
        if (userOptional.isPresent()){
            String key = Base64.getEncoder().encodeToString((username + "&" + password).getBytes());
            message = new Message(200,"ok",key);
        }else {
            message = new Message(404,"not found",null);
        }
        return message;
    }
}
