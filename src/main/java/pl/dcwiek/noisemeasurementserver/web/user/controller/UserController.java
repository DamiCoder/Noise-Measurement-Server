package pl.dcwiek.noisemeasurementserver.web.user.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.dcwiek.noisemeasurementserver.database.model.User;
import pl.dcwiek.noisemeasurementserver.security.model.UserCredentials;
import pl.dcwiek.noisemeasurementserver.web.user.UserCredentialsException;
import pl.dcwiek.noisemeasurementserver.web.user.UsernameAlreadyExistsException;
import pl.dcwiek.noisemeasurementserver.web.user.model.UserRegistrationForm;
import pl.dcwiek.noisemeasurementserver.web.user.service.ServiceException;
import pl.dcwiek.noisemeasurementserver.web.user.service.UserService;

@Controller
@RequestMapping(value = "/api")
public class UserController {

    private final UserService userService;

    private final Validator userCredentialsValidator;
    private final Validator userRegistrationFormValidator;

    @Autowired
    UserController(UserService userService,
                   @Qualifier(value = "userCredentialsValidator") Validator userCredentialsValidator,
                   @Qualifier(value = "userRegistrationFormValidator") Validator userRegistrationFormValidator) {
        this.userService = userService;
        this.userCredentialsValidator = userCredentialsValidator;
        this.userRegistrationFormValidator = userRegistrationFormValidator;
    }

    @PostMapping("/public/user/login")
    public ResponseEntity<Object> loginUser(@RequestBody UserCredentials userCredentials, BindingResult bindingResult)
            throws ServiceException, BindException, UserCredentialsException {
        ValidationUtils.invokeValidator(userCredentialsValidator, userCredentials, bindingResult);
        if(bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
            User user = userService.getUserWithoutConfidentialData(userCredentials);
            return ResponseEntity.ok(user);
    }

    @PostMapping("/public/user/register")
    public ResponseEntity<Object> registerUser(@RequestBody UserRegistrationForm userRegistrationForm,
                                               BindingResult bindingResult)
            throws BindException, ServiceException, UsernameAlreadyExistsException {
        ValidationUtils.invokeValidator(userRegistrationFormValidator, userRegistrationForm, bindingResult);
        if(bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        User user = userService.createUser(userRegistrationForm);
        return ResponseEntity.ok(user);
    }


}
