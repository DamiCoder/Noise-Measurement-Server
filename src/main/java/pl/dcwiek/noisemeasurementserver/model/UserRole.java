package pl.dcwiek.noisemeasurementserver.model;

import lombok.Data;
import pl.dcwiek.noisemeasurementserver.model.constants.ApplicationUserRole;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user_role")
public class UserRole {
    @Id
    @GeneratedValue
    @Column
    private Integer id;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private ApplicationUserRole applicationUserRole;
}
