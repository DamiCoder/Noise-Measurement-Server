package pl.dcwiek.noisemeasurementserver.domain.database.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "standard")
public class StandardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column(name = "min_value")
    private Integer minValue;

    @Column(name = "max_value")
    private Integer maxValue;

    @Column
    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    private TypeEntity type;
}
