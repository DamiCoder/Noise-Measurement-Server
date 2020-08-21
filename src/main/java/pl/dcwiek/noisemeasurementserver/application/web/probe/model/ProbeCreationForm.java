package pl.dcwiek.noisemeasurementserver.application.web.probe.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.dcwiek.noisemeasurementserver.infrastructure.model.validation.GeoPoints;

import java.util.List;

@Getter
@AllArgsConstructor
public class ProbeCreationForm {
    @GeoPoints
    private final String location;

    private final Integer placeId;

    private final Integer regulationId;

    private final List<Integer> standardIds;

    private final Integer result;

    private final String comment;

    private final Integer userRating;
}
