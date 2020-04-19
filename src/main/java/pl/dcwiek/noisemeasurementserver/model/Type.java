package pl.dcwiek.noisemeasurementserver.model;

import lombok.Data;
import pl.dcwiek.noisemeasurementserver.model.constants.StandardType;

import javax.persistence.*;

@Entity
@Data
@Table(name = "type")
public class Type {
    @Id
    @Column
    @GeneratedValue
    private Integer id;

    @Column
    @Enumerated(EnumType.STRING)
    private StandardType name;

}
