package pl.dcwiek.noisemeasurementserver.database.model;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;
import pl.dcwiek.noisemeasurementserver.database.model.validation.GeoPoints;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Validated
@Entity(name = "probe")
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

    @Column
    private Integer result;

    @ManyToMany(targetEntity = StandardEntity.class, fetch = FetchType.EAGER)
    @JoinTable(
            name = "probe_to_standard",
            joinColumns = @JoinColumn(name = "probe_id"),
            inverseJoinColumns = @JoinColumn(name = "standard_id"))
    private Set<StandardEntity> standards;

    @Column
    private String comment;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "user_rating")
    private Integer userRating;

}
