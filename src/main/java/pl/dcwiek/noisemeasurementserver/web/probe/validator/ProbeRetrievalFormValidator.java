package pl.dcwiek.noisemeasurementserver.web.probe.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import pl.dcwiek.noisemeasurementserver.web.probe.model.ProbeRetrievalForm;

@Component(value = "probeRetrievalFormValidator")
public class ProbeRetrievalFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return ProbeRetrievalForm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        ProbeRetrievalForm probeRetrievalForm = (ProbeRetrievalForm) o;

        if(probeRetrievalForm.getPageSize() != null) {
            if(probeRetrievalForm.getPageSize() <= 0) {
                errors.rejectValue("pageSize", "page_size.not.provided");
            }
        }
    }
}