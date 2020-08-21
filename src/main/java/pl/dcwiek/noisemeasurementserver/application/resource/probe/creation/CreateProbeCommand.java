package pl.dcwiek.noisemeasurementserver.application.resource.probe.creation;

import lombok.Data;
import pl.dcwiek.noisemeasurementserver.infrastructure.model.validation.GeoPoints;

import java.util.List;

@Data
public class CreateProbeCommand {

    private final int userId;
    private final Integer result;
    private final Integer placeId;
    private final int regulationId;
    private final List<Integer> standardIds;
    @GeoPoints
    private final String location;
    private final String comment;
    private final Integer userRating;

}
