package pl.dcwiek.noisemeasurementserver.application.web.user.controller;


import lombok.extern.slf4j.Slf4j;
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
import pl.dcwiek.noisemeasurementserver.application.resource.user.creation.CreateUserCommand;
import pl.dcwiek.noisemeasurementserver.application.resource.user.creation.CreateUserService;
import pl.dcwiek.noisemeasurementserver.application.web.user.model.UserRegistrationForm;
import pl.dcwiek.noisemeasurementserver.domain.service.ServiceException;
import pl.dcwiek.noisemeasurementserver.domain.service.UserService;
import pl.dcwiek.noisemeasurementserver.infrastructure.security.model.AppUser;

@Controller
@RequestMapping(value = "/public/api/user")
@Slf4j
public class UserController {

    private final CreateUserService createUserService;
    private final UserService userService;
    private final Validator userRegistrationFormValidator;

    @Autowired
    UserController(CreateUserService createUserService,
                   UserService userService,
                   @Qualifier(value = "userRegistrationFormValidator") Validator userRegistrationFormValidator) {
        this.createUserService = createUserService;
        this.userService = userService;
        this.userRegistrationFormValidator = userRegistrationFormValidator;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> logIn(Authentication authentication) throws ServiceException {
        AppUser user = (AppUser) authentication.getPrincipal();
        log.info("UserController.loginUser method invoked: User logged in: {}", user.toString());
        if(user.isFirstLogIn()) {
            userService.updateUserLogInInfo(user);
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody UserRegistrationForm userRegistrationForm, BindingResult bindingResult)
            throws BindException, ServiceException {
        ValidationUtils.invokeValidator(userRegistrationFormValidator, userRegistrationForm, bindingResult);
        if(bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        CreateUserCommand command = new CreateUserCommand(userRegistrationForm.getUsername(), userRegistrationForm.getPassword());
        AppUser user = createUserService.createUser(command);
        return ResponseEntity.ok(user);
    }
}
