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
import pl.dcwiek.noisemeasurementserver.application.resource.user.UserModel;
import pl.dcwiek.noisemeasurementserver.application.resource.user.creation.CreateUserCommand;
import pl.dcwiek.noisemeasurementserver.application.resource.user.creation.CreateUserService;
import pl.dcwiek.noisemeasurementserver.application.resource.user.retrieval.GetUserService;
import pl.dcwiek.noisemeasurementserver.application.resource.user.retrieval.LoginCommand;
import pl.dcwiek.noisemeasurementserver.domain.ServiceException;
import pl.dcwiek.noisemeasurementserver.web.user.model.UserCredentials;
import pl.dcwiek.noisemeasurementserver.web.user.model.UserRegistrationForm;

@Controller
@RequestMapping(value = "/api")
public class UserController {

    private final GetUserService getUserService;
    private final CreateUserService createUserService;

    private final Validator userCredentialsValidator;
    private final Validator userRegistrationFormValidator;

    @Autowired
    UserController(GetUserService getUserService,
                   CreateUserService createUserService, @Qualifier(value = "userCredentialsValidator") Validator userCredentialsValidator,
                   @Qualifier(value = "userRegistrationFormValidator") Validator userRegistrationFormValidator) {
        this.getUserService = getUserService;
        this.createUserService = createUserService;
        this.userCredentialsValidator = userCredentialsValidator;
        this.userRegistrationFormValidator = userRegistrationFormValidator;
    }

    @PostMapping("/public/user/login")
    public ResponseEntity<Object> loginUser(@RequestBody UserCredentials userCredentials, BindingResult bindingResult) throws ServiceException, BindException {
        ValidationUtils.invokeValidator(userCredentialsValidator, userCredentials, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        LoginCommand command = new LoginCommand(userCredentials.getUsername(), userCredentials.getPassword());
        UserModel user = getUserService.getUserWithoutCredentials(command);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/public/user/register")
    public ResponseEntity<Object> registerUser(@RequestBody UserRegistrationForm userRegistrationForm, BindingResult bindingResult) throws BindException, ServiceException {
        ValidationUtils.invokeValidator(userRegistrationFormValidator, userRegistrationForm, bindingResult);
        if(bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        CreateUserCommand command = new CreateUserCommand(userRegistrationForm.getUsername(), userRegistrationForm.getPassword());
        UserModel user = createUserService.createUser(command);
        return ResponseEntity.ok(user);
    }


}
