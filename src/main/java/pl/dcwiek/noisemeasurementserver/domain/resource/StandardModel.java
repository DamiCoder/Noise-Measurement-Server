package pl.dcwiek.noisemeasurementserver.domain.resource;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class StandardModel {

    private final Integer id;
    private final String title;
    private final String description;
    private final int minValue;
    private final int maxValue;
    private final RegulationModel regulation;
    private final PlaceModel place;
}
