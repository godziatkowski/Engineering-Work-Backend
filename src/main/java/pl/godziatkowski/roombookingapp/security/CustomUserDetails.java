package pl.godziatkowski.roombookingapp.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import pl.godziatkowski.roombookingapp.domain.user.dto.UserSnapshot;

public class CustomUserDetails
    implements UserDetails {

    private static final long serialVersionUID = 1L;

    private final Long id;
    private final String password;
    private final String login;
    private final Set<GrantedAuthority> authorities;

    public CustomUserDetails(UserSnapshot userSnapshot) {
        this.id = userSnapshot.getId();
        this.login = userSnapshot.getLogin();
        this.password = userSnapshot.getPassword();
        this.authorities = userSnapshot.getUserRoles()
            .stream()
            .map(userRole -> {
                return new SimpleGrantedAuthority(userRole.toString());
            })
            .collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
