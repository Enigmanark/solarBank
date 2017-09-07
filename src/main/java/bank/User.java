package bank;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

public class User implements UserDetails {
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private List<GrantedAuthority> authorities;
    private boolean enabled = true;
    private boolean locked = false;
    private boolean credentials;
    private boolean expired;

    @Override
    public String toString() {
        return username + ", " + firstName + ", " + lastName;
    }

    public User() {
        authorities = new ArrayList<GrantedAuthority>();
    }

    public void makeUser() {
        authorities.clear();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
    }

    public void makeAdmin() {
        authorities.clear();
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    public User(String username, String firstName, String lastName) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        authorities = new ArrayList<GrantedAuthority>();
    }

    public void setUsername(String un) {
        username = un;
    }

    public void setFirstName(String fn) {
        firstName = fn;
    }

    public void setLastName(String ln) {
        lastName = ln;
    }

    public void setPassword(String pass) {
        password = pass;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public List<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return expired;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentials;
    }

    @Override
    public boolean isAccountNonLocked() {
        return locked;
    }

}
