package pl.dcwiek.noisemeasurementserver.model;


import lombok.Data;
import org.springframework.validation.annotation.Validated;
import pl.dcwiek.noisemeasurementserver.model.validation.GeoPoints;

import javax.persistence.*;

@Data
@Entity
@Table
@Validated
public class Probe {

    @Id
    @GeneratedValue
    @Column
    private Integer id;

    @Column
    @GeoPoints
    private String location;

    @ManyToOne(targetEntity = Place.class)
    private Place place;

    @ManyToOne(targetEntity = User.class)
    private User user;

    @ManyToOne(targetEntity = Standard.class)
    private Standard standard;

    @Column
    private Integer result;

    @Column
    private String comment;

}
