package pl.coderslab.sportsbet2.users;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import pl.coderslab.sportsbet2.fixture.eventData.Country;
import pl.coderslab.sportsbet2.betting.coupon.Coupon;
import pl.coderslab.sportsbet2.wallet.Wallet;
import pl.coderslab.sportsbet2.users.validators.UsernameUnique;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false, unique = true)
    @UsernameUnique
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

        @NotNull
    private boolean adult;

    //    @EmailUnique
    @Email
    @Column(nullable = false, unique = true)
    private String mail;

    @OneToOne(cascade = CascadeType.ALL)
    private Wallet wallet;

    @OneToMany(mappedBy = "user")
    private List<Coupon> coupons = new ArrayList<>();

    private boolean enabled = false;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    //    @NotNull
    private String street;

    //    @NotNull
    private String city;

    //    @NotNull
    private String zip;

    @OneToOne
    private Country country;

    //    @NotNull
    private boolean dataProcessingAcknowledgement = false;

    //    @NotNull
    private boolean active = true;

    public User(String username, String password, Collection<? extends GrantedAuthority> authorities) {
    }
}