package pl.dcwiek.noisemeasurementserver.model.validation;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = GeoPointsValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface GeoPoints {
    String message() default "Invalid geo coordinates";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
