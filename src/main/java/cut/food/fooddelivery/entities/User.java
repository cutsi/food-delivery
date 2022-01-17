package cut.food.fooddelivery.entities;

import cut.food.fooddelivery.utilities.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@Entity
@AllArgsConstructor
@Table(name="users")
public class User implements UserDetails {
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Long id;
    @Column(nullable = true)
    private String name;
    @Column(nullable = true)
    private String phone;
    private String email;
    private String password;
    @Column(nullable = true)
    private String address;
    private UserRole appUserRole;
    @Column(nullable = true)
    private Boolean isEnabled;

    public User(String name, String phone, String email, String password, String address){
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.address = address;
        this.appUserRole = UserRole.USER;
        this.isEnabled = false;
    }
    public User(String phone, String email, String password){
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.appUserRole = UserRole.USER;
        this.isEnabled = false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
