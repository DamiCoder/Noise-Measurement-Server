package pl.dcwiek.noisemeasurementserver.web.user.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import pl.dcwiek.noisemeasurementserver.commons.util.EmailUtils;
import pl.dcwiek.noisemeasurementserver.web.user.model.UserRegistrationForm;

import javax.annotation.Resource;

@Component(value = "userRegistrationFormValidator")
public class UserRegistrationFormValidator implements Validator {

    @Resource
    private EmailUtils emailUtils;

    @Override
    public boolean supports(Class<?> aClass) {
        return UserRegistrationForm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserRegistrationForm userRegistrationForm = (UserRegistrationForm) o;

        if(StringUtils.isBlank(userRegistrationForm.getPassword())) {
            errors.rejectValue("password", "password.not.provided");
        }

        if(StringUtils.isBlank(userRegistrationForm.getUsername())) {
            errors.rejectValue("username", "username.not.provided");
        } else {
            if(!emailUtils.isEmail(userRegistrationForm.getUsername())) {
                errors.rejectValue("username", "username.not.email");
            }
        }
    }
}
