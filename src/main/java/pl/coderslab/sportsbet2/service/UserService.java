package pl.coderslab.sportsbet2.service;

import pl.coderslab.sportsbet2.model.User;

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
}
