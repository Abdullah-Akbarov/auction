package dao.impl;

import dao.PostgresConnection;
import dao.UserDao;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserDaoImpl implements UserDao {
    private final Connection con = PostgresConnection.getInstance();

    private static UserDao userDao;

    public static UserDao getUserDao() {
        if (userDao == null) {
            userDao = new UserDaoImpl();
        }
        return userDao;
    }

    @Override
    public Optional<User> findByUsernameAndPassword(String username, String password) {
        PreparedStatement statement = null;
        try {
            statement = con.prepareStatement("select * from users where username = ? and password = ?");
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Integer id = resultSet.getInt(1);
                String firstName = resultSet.getString(4);
                String lastName = resultSet.getString(5);
                boolean isAdmin = resultSet.getBoolean(6);
                String phoneNumber = resultSet.getString(7);
                return Optional.of(new User(id, username, lastName, firstName, isAdmin, phoneNumber));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }
}
