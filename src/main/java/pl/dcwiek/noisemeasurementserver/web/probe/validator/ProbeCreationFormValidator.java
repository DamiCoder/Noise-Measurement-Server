package pl.dcwiek.noisemeasurementserver.web.probe.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import pl.dcwiek.noisemeasurementserver.domain.database.model.constants.Standard;
import pl.dcwiek.noisemeasurementserver.web.probe.model.ProbeCreationForm;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Component(value = "probeCreationFormValidator")
public class ProbeCreationFormValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return ProbeCreationForm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        ProbeCreationForm probeCreationForm = (ProbeCreationForm) o;
        if (StringUtils.isBlank(probeCreationForm.getPlace())) {
            errors.rejectValue("place", "place.not.provided");
        }

        if (probeCreationForm.getCreatedDate() == 0) {
            errors.rejectValue("createdDate", "createddate.not.provided");
        } else {
            LocalDateTime createdDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(probeCreationForm.getCreatedDate()), ZoneId.of("UTC"));
            LocalDateTime now = LocalDateTime.now();
            if (now.isBefore(createdDate)) {
                errors.rejectValue("createdDate", "createddate.refers.future");
            }
        }

        if(StringUtils.isBlank(probeCreationForm.getStandard())) {
            errors.rejectValue("standard", "standard.not.provided");
        } else if (Standard.contains(probeCreationForm.getStandard())) {
            errors.rejectValue("standard", "standard.value.not.recognized");
        }

        if(probeCreationForm.getResult() == 0) {
            errors.rejectValue("result", "result.not.provided");
        }

    }
}
