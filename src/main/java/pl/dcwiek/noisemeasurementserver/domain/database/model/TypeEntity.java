package pl.dcwiek.noisemeasurementserver.domain.database.model;

import lombok.Data;
import pl.dcwiek.noisemeasurementserver.domain.database.model.constants.Standard;

import javax.persistence.*;

@Entity
@Data
@Table(name = "type")
public class TypeEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @Enumerated(EnumType.STRING)
    private Standard standard;

}
