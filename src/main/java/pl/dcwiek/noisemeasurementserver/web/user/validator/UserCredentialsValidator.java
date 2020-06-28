package pl.dcwiek.noisemeasurementserver.web.user.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import pl.dcwiek.noisemeasurementserver.commons.util.EmailUtils;
import pl.dcwiek.noisemeasurementserver.web.user.model.UserCredentials;

import javax.annotation.Resource;


@Component(value = "userCredentialsValidator")
public class UserCredentialsValidator implements Validator {

    @Resource
    private EmailUtils emailUtils;

    @Override
    public boolean supports(Class<?> aClass) {
        return UserCredentials.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserCredentials userCredentials = (UserCredentials) o;

        if(StringUtils.isBlank(userCredentials.getPassword())) {
            errors.rejectValue("password", "password.not.provided");
        }

        if(StringUtils.isBlank(userCredentials.getUsername())) {
            errors.rejectValue("username", "username.not.provided");
        } else {
            if(!emailUtils.isEmail(userCredentials.getUsername())) {
                errors.rejectValue("username", "username.not.email");
            }
        }
    }
}
