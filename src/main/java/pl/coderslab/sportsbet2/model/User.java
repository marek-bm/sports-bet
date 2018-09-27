package pl.coderslab.sportsbet2.model;

import org.hibernate.FetchMode;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import pl.coderslab.sportsbet2.validators.EmailUnique;
import pl.coderslab.sportsbet2.validators.UsernameUnique;
import pl.coderslab.sportsbet2.validators.UsernameUnique;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false, unique = true)
    @UsernameUnique
    private String username;

    @NotEmpty
    private String password;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @NotEmpty
    private boolean adult;

    @EmailUnique
    @Email
    @Column(nullable = false, unique = true)
    private String mail;

    @OneToOne (cascade = CascadeType.ALL)
    private Wallet wallet;

    @OneToMany (mappedBy = "user")
    private List<BetCoupon> coupons = new ArrayList<>();

    private boolean enabled=false;

    @ManyToMany (fetch = FetchType.EAGER)
    private Set<Role> roles;

    @NotNull
    private String street;

    @NotNull
    private String city;

    @NotNull
    private String zip;

    @OneToOne
    private Country country;

    @NotNull
    private boolean dataProcessingAcknowledgement = false;

    @NotNull
    private boolean active = true;

    public User(String username, String password, Collection<? extends GrantedAuthority> authorities) {
    }


    //getters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean getAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public User() {
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public List<BetCoupon> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<BetCoupon> coupons) {
        this.coupons = coupons;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public boolean getDataProcessingAcknowledgement() {
        return dataProcessingAcknowledgement;
    }

    public void setDataProcessingAcknowledgement(boolean dataProcessingAcknowledgement) {
        this.dataProcessingAcknowledgement = dataProcessingAcknowledgement;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}