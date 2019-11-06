package pl.bets365mj.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository <User, Integer> {
    User findByUsername(String username);
    List<User> findAll();
    User findByMail(String emial);
    User findUserByMail(String emial);
    User findUsersByUsername(String username);
    User findById(int id);
}
