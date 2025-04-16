package app.web;


import app.avatar.AvatarService;
import app.security.AuthenticationUserData;
import app.user.UserNotAuthorised;
import app.user.model.User;
import app.user.service.UserService;
import app.web.common.CommonService;
import app.web.dto.UserEditingRequest;
import app.web.dto.UserWithAvatar;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

        // here "user" actually means the user whose page we're viewing
        ModelAndView mav = new ModelAndView();
        User rawTypeOfUserWeAreLookingAt = theUserService.getUserById(userId);
        UserWithAvatar userWeAreLookingAt = new UserWithAvatar(
                rawTypeOfUserWeAreLookingAt,
                theAvatarService.getAvatarUrl(userId)
        );

        mav.addObject("user", userWeAreLookingAt);

        //  and "loggedInUser" is what we're accessing with
        if(theCommonService.verifyAuthPrinciple(auth, false))
            mav.addObject("loggedInUser", theUserService.getUserById(auth.getUserUuid()));

        mav.setViewName("user/view");
        return mav;
    }

    @GetMapping("/user/edit/{userId}")
    public ModelAndView getEditPage(
            @AuthenticationPrincipal AuthenticationUserData auth,
            @PathVariable("userId") UUID userId,
            Model model
    ) {
        theCommonService.ensureAuthUserMatchesId(auth, userId, "Опит за редакция на привилигировани данни.");
        User u = theUserService.getUserByAuthenticationData(auth);
        ModelAndView mav = theCommonService.getCommonHeaderMAV(u);
        mav.addObject("userEditingRequest", new UserEditingRequest());

        String test = theAvatarService.getAvatarUrl(u.getId());
        mav.addObject("avatarUrl", test);

        mav.addAllObjects(model.asMap());

        mav.setViewName("user/edit");
        return mav;
    }

    @PostMapping("/user/edit/{userId}")
    public ModelAndView processEditRequest(
            @AuthenticationPrincipal AuthenticationUserData auth,
            @PathVariable("userId") UUID userId,
            @Valid UserEditingRequest req,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        theCommonService.ensureAuthUserMatchesId(auth, userId, "Опит за редакция на чужди данни.");
        ModelAndView mav = new ModelAndView();

        if(result.hasErrors()){
            redirectAttributes.addFlashAttribute("userEditingRequest", req);
            return new ModelAndView(("redirect:/user/edit" + userId));
        }



        mav.setViewName("user/" + userId);
        return mav;
    }

    @GetMapping("/admin/edit/{userId}")
    public ModelAndView getEditPageForAdmins(
            @AuthenticationPrincipal AuthenticationUserData auth,
            @PathVariable("userId") UUID userId
    ) {
        theCommonService.verifyAuthPrinciple(auth, true);
        User user = theUserService.getUserByAuthenticationData(auth);
        if(!user.isAdmin()){
            throw new UserNotAuthorised("Опит за достъп към страници, предназначени само за администратори.");
        }

        ModelAndView mav = theCommonService.getCommonHeaderMAV(theUserService.getUserById(userId));
        mav.addObject("loggedInUser", theUserService.getUserByAuthenticationData(auth));

        mav.setViewName("user/admin_edit");
        return mav;
    }

}
