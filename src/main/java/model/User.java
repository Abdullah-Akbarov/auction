package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private boolean isAdmin;
    private String phoneNumber;

    public User(Integer id, String username, String firstName, String lastName, boolean isAdmin, String phoneNumber) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isAdmin = isAdmin;
        this.phoneNumber = phoneNumber;
    }
}
