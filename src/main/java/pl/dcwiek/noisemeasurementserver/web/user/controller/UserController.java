package pl.dcwiek.noisemeasurementserver.web.user.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.dcwiek.noisemeasurementserver.application.resource.service.ServiceException;
import pl.dcwiek.noisemeasurementserver.application.resource.user.creation.CreateUserCommand;
import pl.dcwiek.noisemeasurementserver.application.resource.user.creation.CreateUserService;
import pl.dcwiek.noisemeasurementserver.security.model.AppUser;
import pl.dcwiek.noisemeasurementserver.web.user.model.UserRegistrationForm;

@Controller
@RequestMapping(value = "/api")
public class UserController {

    private final CreateUserService createUserService;
    private final Validator userRegistrationFormValidator;

    @Autowired
    UserController(CreateUserService createUserService,
                   @Qualifier(value = "userRegistrationFormValidator") Validator userRegistrationFormValidator) {
        this.createUserService = createUserService;
        this.userRegistrationFormValidator = userRegistrationFormValidator;
    }

    @PostMapping("/public/user/login")
    public ResponseEntity<Object> loginUser(Authentication authentication) {
        AppUser user = (AppUser) authentication.getPrincipal();
        return ResponseEntity.ok(user);
    }

    @PostMapping("/public/user/register")
    public ResponseEntity<Object> registerUser(@RequestBody UserRegistrationForm userRegistrationForm, BindingResult bindingResult) throws BindException, ServiceException {
        ValidationUtils.invokeValidator(userRegistrationFormValidator, userRegistrationForm, bindingResult);
        if(bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        CreateUserCommand command = new CreateUserCommand(userRegistrationForm.getUsername(), userRegistrationForm.getPassword());
        AppUser user = createUserService.createUser(command);
        return ResponseEntity.ok(user);
    }

}
