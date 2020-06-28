package pl.dcwiek.noisemeasurementserver.application.resource.probe.retrieval;

import lombok.Getter;
import pl.dcwiek.noisemeasurementserver.domain.database.model.validation.GeoPoints;

@Getter
public class ProbeModel {

    @GeoPoints
    private String location;

    private String place;

    private String standard;

    private Integer result;

    private String comment;

    private long createdDate;

}
