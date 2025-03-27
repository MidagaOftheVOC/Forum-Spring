package app.web;


import app.user.model.User;
import app.user.repository.UserRepository;
import app.user.service.UserService;
import app.web.dto.LoginRequest;
import app.web.dto.RegistrationRequest;
import ch.qos.logback.core.model.Model;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WebpageController {

    private final UserService theUserService;

    public WebpageController(
            UserService _userService
    ){
        theUserService = _userService;
    }

    @GetMapping("/login")
    public ModelAndView showLoginPage(@RequestParam(value = "error", required = false) String errorParameter){

        ModelAndView mav = new ModelAndView();

        mav.setViewName("login");
        mav.addObject("loginRequest", new LoginRequest());

        if(errorParameter != null){
            mav.addObject("errorMsg", "This means you fucked the login.");
        }

        return mav;
    }

    @GetMapping("/register")
    public ModelAndView showRegistrationPage(){
        ModelAndView mav = new ModelAndView();

        mav.setViewName("register");
        mav.addObject("registrationRequest", new RegistrationRequest());

        return mav;
    }

    @PostMapping("/register")
    public ModelAndView registerUser(@Valid RegistrationRequest registrationRequest,
                                     BindingResult validationResult){

        if(validationResult.hasErrors()){
            return new ModelAndView("register");
        }

        theUserService.register(registrationRequest);

        return new ModelAndView("redirect:/login");
    }



}
