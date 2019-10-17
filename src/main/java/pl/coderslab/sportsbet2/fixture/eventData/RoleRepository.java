package pl.coderslab.sportsbet2.fixture.eventData;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.sportsbet2.users.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(String name);
}
