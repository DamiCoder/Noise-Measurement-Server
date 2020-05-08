package pl.dcwiek.noisemeasurementserver.database.model;

import lombok.Data;
import pl.dcwiek.noisemeasurementserver.database.model.constants.ApplicationUserRole;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user_role")
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private ApplicationUserRole applicationUserRole;
}
