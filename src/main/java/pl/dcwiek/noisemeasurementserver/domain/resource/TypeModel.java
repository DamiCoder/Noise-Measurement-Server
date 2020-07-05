package pl.dcwiek.noisemeasurementserver.domain.resource;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class TypeModel {

    private final Integer id;
    private final String name;
}
