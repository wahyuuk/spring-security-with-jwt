package co.id.wahyu.auth.jwt.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AppUserDetail implements UserDetails {

    private final User user;

    public AppUserDetail(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_"+ role.getName().toUpperCase()));

            role.getPrivileges().forEach(privilege -> {
                authorities.add(new SimpleGrantedAuthority(privilege.getName().toUpperCase()));
            });
        });
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.isAccountStatus();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.isAccountStatus();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.isAccountStatus();
    }

    @Override
    public boolean isEnabled() {
        return user.isAccountStatus();
    }
}
