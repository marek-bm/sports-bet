package pl.bets365mj.user;

import java.util.List;

public interface UserService {

    void saveUser(User user);
    List<User> findAll();
    User findByMail(String emial);
    User findByUsername(String username);
    User findById(int id);
    boolean isOldPasswordValid(User user, String oldPassword);
    void changePassword(User user, String password);
}
