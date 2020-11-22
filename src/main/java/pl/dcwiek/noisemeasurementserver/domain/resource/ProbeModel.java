package pl.dcwiek.noisemeasurementserver.domain.resource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import pl.dcwiek.noisemeasurementserver.infrastructure.model.validation.GeoPoints;
import pl.dcwiek.noisemeasurementserver.infrastructure.security.model.AppUser;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@ToString
public class ProbeModel {

    private final Integer id;
    @GeoPoints
    private final String location;
    private final PlaceModel place;
    private final AppUser appUser;
    private final Integer result;
    private final List<StandardModel> standards;
    private final String comment;
    private final Integer userRating;
    private final LocalDateTime createdDate;

}
