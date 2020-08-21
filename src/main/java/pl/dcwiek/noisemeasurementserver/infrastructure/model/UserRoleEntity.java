package pl.dcwiek.noisemeasurementserver.infrastructure.model;

import lombok.Data;
import pl.dcwiek.noisemeasurementserver.infrastructure.model.constants.UserRole;

import javax.persistence.*;

@Data
@Entity(name = "user_role")
public class UserRoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
}
