package cut.food.fooddelivery.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cut.food.fooddelivery.utilities.UserRole;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
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
    @Enumerated(EnumType.STRING)
    private UserRole appUserRole;
    @Column(nullable = true)
    private Boolean isEnabled;
    @Column(nullable = true)
    private Boolean locked = false;
    @Column(name = "verification_code", length = 64)
    private String verificationCode;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = {
                    @JoinColumn(name = "user_id",referencedColumnName = "id",
                            nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "role_id",referencedColumnName = "id",
                            nullable = false, updatable = false)})
    private Set<Role> roles = new HashSet<>();

    public User(String name, String phone, String email, String password, String address){
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.address = address;
        this.isEnabled = false;
        this.appUserRole = UserRole.USER;
    }
    public User(String email, String phone, String password,String name){
        this.phone = phone;
        this.email = email;
        this.name = name;
        this.password = password;
        this.isEnabled = false;
        this.appUserRole = UserRole.USER;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {//daje rolu korisniku, za spring security da mozemo odlucit koja rola ima pristup cemu
        SimpleGrantedAuthority authority =
                new SimpleGrantedAuthority(appUserRole.name());
        return Collections.singleton(authority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
