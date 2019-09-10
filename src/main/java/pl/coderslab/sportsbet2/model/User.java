package pl.coderslab.sportsbet2.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private Integer id;

    @Column(nullable = false, unique = true)
//    @UsernameUnique
    @Getter
    @Setter
    private String username;

    @NotNull
    @Getter
    @Setter
    private String password;

    @NotNull
    @Getter
    @Setter
    private String firstName;

    @NotNull
    @Getter
    @Setter
    private String lastName;

    //    @NotNull
    @Getter
    @Setter
    private boolean adult;

    //    @EmailUnique
    @Email
    @Column(nullable = false, unique = true)
    @Getter
    @Setter
    private String mail;

    @OneToOne(cascade = CascadeType.ALL)
    @Getter
    @Setter
    private Wallet wallet;

    @OneToMany(mappedBy = "user")
    @Getter
    @Setter
    private List<Coupon> coupons = new ArrayList<>();

    @Getter
    @Setter
    private boolean enabled = false;

    @ManyToMany(fetch = FetchType.EAGER)
    @Getter
    @Setter
    private Set<Role> roles;

    //    @NotNull
    @Getter
    @Setter
    private String street;

    //    @NotNull
    @Getter
    @Setter
    private String city;

    //    @NotNull
    @Getter
    @Setter
    private String zip;

    @OneToOne
    @Getter
    @Setter
    private Country country;

    //    @NotNull
    @Getter
    @Setter
    private boolean dataProcessingAcknowledgement = false;

    //    @NotNull
    @Getter
    @Setter
    private boolean active = true;

    public User(String username, String password, Collection<? extends GrantedAuthority> authorities) {
    }
}