package pl.dcwiek.noisemeasurementserver.database.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "app_user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column
    private String username;

    @Column
    private String password;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "first_log_in")
    private boolean firstLogIn;

    @ManyToMany(targetEntity = UserRoleEntity.class, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_to_user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "user_role_id"))
    private Set<UserRoleEntity> userRoles;

    public UserEntity withoutConfidentialData() {
        this.password = null;
        return this;
    }

    public UserEntity(Integer id, String username, String password, UserRoleEntity userRole, boolean firstLogIn) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.createdDate = LocalDateTime.now();
        Set<UserRoleEntity> userRoles = new HashSet<>();
        userRoles.add(userRole);
        this.userRoles = userRoles;
        this.firstLogIn = firstLogIn;
    }
}
