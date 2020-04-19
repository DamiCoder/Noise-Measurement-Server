package pl.dcwiek.noisemeasurementserver.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@Table
public class User {

    @Id
    @GeneratedValue
    @Column
    private Integer id;

    @Column
    private String username;

    @Column
    private String password;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @ManyToMany(targetEntity = UserRole.class, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_to_user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "user_role_id"))
    private Set<UserRole> userRoles;
}
