package app.web;


import app.avatar.AvatarService;
import app.post.model.Post;
import app.post.service.PostService;
import app.security.AuthenticationUserData;
import app.thread.model.Thread;
import app.thread.service.ThreadService;
import app.user.model.User;
import app.user.service.UserService;
import app.web.common.CommonService;
import app.web.dto.LoginRequest;
import app.web.dto.RegistrationRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@AllArgsConstructor
@Controller
public class WebpageController {

    final private UserService theUserService;
    final private ThreadService theThreadService;
    final private AvatarService theAvatarService;
    final private PostService thePostService;

    final private CommonService theCommonService;

    @GetMapping("/")
    public String emptyPageRedirectoToMain(){
        return "redirect:/main";
    }

    @GetMapping("/main")
    public ModelAndView showMainPage(
            @RequestParam(defaultValue = "recent") String sort,
            @AuthenticationPrincipal AuthenticationUserData auth){
        ModelAndView mav = theCommonService.getCommonHeaderMAV(auth); // includes common header data

        List<Thread> sortedThreadList = theThreadService.getThreadListBySortingMethod(sort);

        mav.addObject("threads", sortedThreadList);
        return mav;
    }

    @GetMapping("/avatar")
    public ModelAndView showAvatarManagementPage(@AuthenticationPrincipal AuthenticationUserData auth) {
        ModelAndView mav = new ModelAndView();

        mav.addObject("user", theUserService.getUserById(auth.getUserUuid()));

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
