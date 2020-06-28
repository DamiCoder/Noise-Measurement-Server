package pl.dcwiek.noisemeasurementserver.domain.database.model;


import lombok.Data;
import org.springframework.validation.annotation.Validated;
import pl.dcwiek.noisemeasurementserver.domain.database.model.validation.GeoPoints;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table
@Validated
public class ProbeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column
    @GeoPoints
    private String location;

    @ManyToOne(targetEntity = PlaceEntity.class)
    private PlaceEntity place;

    @ManyToOne(targetEntity = UserEntity.class)
    private UserEntity user;

    @ManyToOne(targetEntity = StandardEntity.class)
    private StandardEntity standard;

    @Column
    private Integer result;

    @Column
    private String comment;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

}
