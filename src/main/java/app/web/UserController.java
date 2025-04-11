package app.web;


import app.avatar.AvatarService;
import app.security.AuthenticationUserData;
import app.user.service.UserService;
import app.web.common.CommonService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;


@AllArgsConstructor
@Controller
public class UserController {

    private final UserService theUserService;
    private final CommonService theCommonService;
    private final AvatarService theAvatarService;

    @GetMapping("/user/{userId}")
    public ModelAndView getProfilePage(
            @AuthenticationPrincipal AuthenticationUserData auth,
            @PathVariable("userId") UUID userId
            ){
        if(auth == null || !auth.getUserUuid().equals(userId)){
            return new ModelAndView("redirect:/main");
        }

        ModelAndView mav = theCommonService.getCommonHeaderMAV(theUserService.getUserById(userId));

        mav.addObject("avatarUrl",theAvatarService.getAvatarUrl(userId));
        mav.setViewName("user/view");
        return mav;
    }

    //public ModelAndView

}
