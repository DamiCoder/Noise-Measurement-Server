package pl.dcwiek.noisemeasurementserver.domain.database.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "place")
public class PlaceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column
    private String name;

    @Column
    private String description;
}
