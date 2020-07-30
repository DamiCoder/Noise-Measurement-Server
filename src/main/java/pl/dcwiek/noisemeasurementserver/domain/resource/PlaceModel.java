package pl.dcwiek.noisemeasurementserver.domain.resource;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class PlaceModel {

    private final int id;
    private final String name;
    private final String description;
    private final String type;
}
