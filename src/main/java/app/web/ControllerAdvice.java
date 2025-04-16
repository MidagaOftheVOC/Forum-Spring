package app.web;


import app.security.AuthenticationUserData;
import app.user.UserNotAuthorised;
import app.user.UserNotLoggedIn;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

//    /**
//     * This is a terrible idea. Better not.
//     */
//    @ExceptionHandler(
//            //NullPointerException.class
//    )
//    public ModelAndView MissingAuthenticationPrincipal(
//            NullPointerException ex
//    ) {
//        return new ModelAndView("redirect:/main");
//    }

    @ExceptionHandler(
            UserNotLoggedIn.class
    )
    public ModelAndView UserNotLoggedIn(){
        return new ModelAndView("redirect:/login");
    }

    @ExceptionHandler(
            UserNotAuthorised.class
    )
    public ModelAndView UserLackingPrivileges(
            @AuthenticationPrincipal AuthenticationUserData auth,
            UserNotAuthorised ex
            ){
        return new ModelAndView("redirect:/user/" + auth.getUserUuid())
                .addObject("redirectRequest_ErrorMessage", "Непозволен достъп: " + ex.getMessage());
    }

}
