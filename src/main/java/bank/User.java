package bank;

import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

public class User implements UserDetails {
    @Id
    public String id;

    private float balance;
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private List<GrantedAuthority> authorities;
    private boolean enabled = true;
    private boolean notLocked = true;
    private boolean notCredsExpired = true;
    private boolean notExpired = true;

    public float deposit(float d) {
        balance += d;
        return balance;
    }

    public float withdraw(float w) {
        float difference = balance - w;
        if( difference >= 0 ) {
            balance = difference;
            return balance;
        } else {
            return -1;
        }

    }


    public float getBalance() {
        return balance;
    }

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
        return notExpired;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return notCredsExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return notLocked;
    }

}
