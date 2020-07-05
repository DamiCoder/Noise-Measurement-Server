package pl.dcwiek.noisemeasurementserver.domain.resource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import pl.dcwiek.noisemeasurementserver.database.model.validation.GeoPoints;
import pl.dcwiek.noisemeasurementserver.security.model.AppUser;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@ToString
public class ProbeModel {

    private final Integer id;

    @GeoPoints
    private final String location;

    private final PlaceModel place;

    private final StandardModel standard;

    private final AppUser appUser;

    private final Integer result;

    private final String comment;

    private final LocalDateTime createdDate;

}
