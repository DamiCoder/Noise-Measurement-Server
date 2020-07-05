package pl.dcwiek.noisemeasurementserver.web.probe.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import pl.dcwiek.noisemeasurementserver.web.probe.model.ProbeCreationForm;

@Component(value = "probeCreationFormValidator")
public class ProbeCreationFormValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return ProbeCreationForm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        ProbeCreationForm probeCreationForm = (ProbeCreationForm) o;

        if(probeCreationForm.getResult() == 0) {
            errors.rejectValue("result", "result.not.provided");
        }

    }
}
