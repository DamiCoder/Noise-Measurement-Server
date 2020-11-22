package pl.dcwiek.noisemeasurementserver.infrastructure.model.validation;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class GeoPointsValidator implements
        ConstraintValidator<GeoPoints, String> {

    @Override
    public void initialize(GeoPoints location) {
    }

    @Override
    public boolean isValid(String locationField, ConstraintValidatorContext constraintValidatorContext) {
        if(StringUtils.isBlank(locationField)) {
            return true;
        }

        if(locationField.matches("\\d*(\\.?\\d*)*;\\d*(\\.\\d*)*\n")) {
            String[] coordinates = locationField.split(";");
            double longitude = Double.parseDouble(coordinates[0]);
            double latitude = Double.parseDouble(coordinates[1]);

            if(longitude > 180 || longitude < -180) {
                return false;
            }

            return !(latitude > 90) && !(latitude < -90);

        } else return false;
    }
}