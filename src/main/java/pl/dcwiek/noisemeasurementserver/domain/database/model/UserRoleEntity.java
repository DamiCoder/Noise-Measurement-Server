package pl.dcwiek.noisemeasurementserver.domain.database.model;

import lombok.Data;
import pl.dcwiek.noisemeasurementserver.domain.database.model.constants.UserRole;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user_role")
public class UserRoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
}
