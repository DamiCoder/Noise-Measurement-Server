package pl.dcwiek.noisemeasurementserver.web.probe.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import pl.dcwiek.noisemeasurementserver.domain.database.model.constants.Standard;
import pl.dcwiek.noisemeasurementserver.web.probe.model.ProbeModelForm;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class ProbeModelFormValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return ProbeModelForm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        ProbeModelForm probeModelForm = (ProbeModelForm) o;
        if (StringUtils.isBlank(probeModelForm.getPlace())) {
            errors.rejectValue("place", "place.not.provided");
        }

        if (probeModelForm.getCreatedDate() == 0) {
            errors.rejectValue("createdDate", "createddate.not.provided");
        } else {
            LocalDateTime createdDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(probeModelForm.getCreatedDate()), ZoneId.of("UTC"));
            LocalDateTime now = LocalDateTime.now();
            if (now.isBefore(createdDate)) {
                errors.rejectValue("createdDate", "createddate.refers.future");
            }
        }

        if(StringUtils.isBlank(probeModelForm.getStandard())) {
            errors.rejectValue("standard", "standard.not.provided");
        } else if (Standard.contains(probeModelForm.getStandard())) {
            errors.rejectValue("standard", "standard.value.not.recognized");
        }

        if(probeModelForm.getResult() == 0) {
            errors.rejectValue("result", "result.not.provided");
        }

    }
}
