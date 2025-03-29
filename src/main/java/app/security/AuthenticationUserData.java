package app.security;

import app.user.model.UserStatus;
import app.user.model.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Data
@Getter
@AllArgsConstructor
public class AuthenticationUserData implements UserDetails {

    //  TODO:   figure out how to remove this ugliness
    //  And don't change this class anymore(28.03.)
    private static final long serialVersionUID = 574375817029383998L;


    private UUID userUuid;
    private String username;
    private String userhash;
    private String shownUsername;
    private UserStatus userStatus;
    private UserType userType;  // or user role


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        SimpleGrantedAuthority sga = new SimpleGrantedAuthority(
                "ROLE_%s".formatted(userType.name())    // String::name() for correct constant name, ::toString() otherwise
        );

        return List.of(sga);
    }


    //  Dodgy :)
    @Override
    public String getPassword() {
        return userhash;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !(this.userStatus == UserStatus.INACTIVE);
    }

    /**
     * Check if the account is *ACTIVE* only!
     * If it's not inactive or banned, returns true.
     */
    @Override
    public boolean isAccountNonLocked() {
        return this.userStatus == UserStatus.ACTIVE;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !(this.userStatus == UserStatus.INACTIVE);
    }

    @Override
    public boolean isEnabled() {
        return !(this.userStatus == UserStatus.INACTIVE);
    }
}
