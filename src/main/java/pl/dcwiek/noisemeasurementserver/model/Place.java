package pl.dcwiek.noisemeasurementserver.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "place")
public class Place {

    @Id
    @GeneratedValue
    @Column
    private Integer id;

    @Column
    private String name;

    @Column
    private String description;
}
