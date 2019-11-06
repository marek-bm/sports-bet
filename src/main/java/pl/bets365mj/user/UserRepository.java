package pl.bets365mj.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository <User, Integer> {
    List<User> findAll();
    User findById(int id);
    User findByMail(String mail);
    User findByUsername(String username);
}
