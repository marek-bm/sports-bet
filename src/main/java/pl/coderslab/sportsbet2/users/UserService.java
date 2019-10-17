package pl.coderslab.sportsbet2.users;

import pl.coderslab.sportsbet2.users.User;

import java.util.List;

public interface UserService {

    User findByUserName(String name);
    User findByMail(String email);
    void saveUser(User user);
    List<User> findAll();
    User findUserByMail(String emial);
    User findUsersByUsername(String username);
    User getByUsername(String username);
    User findById(int id);
    boolean checkIfValidOldPassword(User user, String oldPassword);
    void changeUserPassword(User user, String password);
}
