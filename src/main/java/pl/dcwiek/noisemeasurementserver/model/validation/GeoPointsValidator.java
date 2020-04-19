package pl.dcwiek.noisemeasurementserver.model.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class GeoPointsValidator implements
        ConstraintValidator<GeoPoints, String> {

    @Override
    public void initialize(GeoPoints contactNumber) {
    }


    //TODO: verify this regex
    @Override
    public boolean isValid(String locationField, ConstraintValidatorContext constraintValidatorContext) {
        return locationField != null && locationField.matches("\\d*(\\.?\\d*)*;\\d*(\\.\\d*)*\n");
    }

}