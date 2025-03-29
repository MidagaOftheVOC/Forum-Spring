package app.web;


import app.GCV;
import app.security.AuthenticationUserData;
import app.thread.service.ThreadService;
import app.user.ForumUserNotFound;
import app.user.model.User;
import app.user.model.UserStatus;
import app.user.model.UserType;
import app.user.repository.UserRepository;
import app.user.service.UserService;
import app.web.dto.LoginRequest;
import app.web.dto.RegistrationRequest;
import ch.qos.logback.core.model.Model;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import static com.mysql.cj.conf.PropertyKey.logger;

@Controller
public class WebpageController {

    private final UserService theUserService;
    private final ThreadService theThreadService;


    public WebpageController(
            UserService _userService,
            ThreadService _threadService
    ){
        theUserService = _userService;
        theThreadService = _threadService;
    }

    @GetMapping("/")
    public String emptyPageRedirectoToMain(){
        return "redirect:/main";
    }

    @GetMapping("/main")
    public ModelAndView showMainPage(@AuthenticationPrincipal AuthenticationUserData auth){
        ModelAndView mav = new ModelAndView();

        //  COMMON HEADER DATA
        mav.addObject("totalUsers", theUserService.getUserCount());
        mav.addObject("totalThreads", theThreadService.getThreadCount());

        if(auth != null){   // LOGGED USER-SPECIFIC DATA
            mav.addObject("user", theUserService.getUserById(auth.getUserUuid()));
        }

        return mav;
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
