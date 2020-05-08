package pl.dcwiek.noisemeasurementserver.database.model;

import lombok.Data;
import pl.dcwiek.noisemeasurementserver.database.model.constants.StandardType;

import javax.persistence.*;

@Entity
@Data
@Table(name = "type")
public class Type {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @Enumerated(EnumType.STRING)
    private StandardType name;

}
