package app.web.common;

import app.avatar.AvatarService;
import app.post.service.PostService;
import app.security.AuthenticationUserData;
import app.thread.service.ThreadService;
import app.user.UserNotAuthorised;
import app.user.UserNotLoggedIn;
import app.user.model.User;
import app.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Service
@AllArgsConstructor
public class CommonService {

    final UserService theUserService;
    final ThreadService theThreadService;
    final PostService thePostService;
    final AvatarService theAvatarService;

    public boolean verifyAuthPrinciple(AuthenticationUserData auth, boolean true_if_throwing_exception){
        if(auth == null) {
            if(true_if_throwing_exception)
                throw new UserNotLoggedIn("Няма влязъл потребител, вероятно трябва да има данни за връзката на госта от БД");
            System.out.println("@AuthenticationPrincipal e null");
            return false;
        }
        return true;
    }

    public void ensureAuthUserMatchesId(AuthenticationUserData auth, UUID targetUserId, String message){
        verifyAuthPrinciple(auth, true);    //  ensuring matching user IDs requires by default a logged in user, i.e. that's exception-worthy
        if(auth.getUserUuid().equals(targetUserId)) throw new UserNotAuthorised(message);
    }

    public ModelAndView getCommonHeaderMAV(AuthenticationUserData auth){
        ModelAndView mav = new ModelAndView();
        if(verifyAuthPrinciple(auth, false)) {
            User authUser = theUserService.getUserById(auth.getUserUuid());
            mav.addObject("user", authUser);
            mav.addObject("loggedUserURL", theAvatarService.getAvatarUrl(authUser.getId()));
        }
        mav.addObject("totalUsers", theUserService.getUserCount());
        mav.addObject("totalThreads", theThreadService.getThreadCount());
        return mav;
    }

    public ModelAndView getCommonHeaderMAV(User user){
        ModelAndView mav = new ModelAndView();
        User authUser = theUserService.getUserById(user.getId());
        mav.addObject("user", authUser);
        mav.addObject("loggedUserURL", theAvatarService.getAvatarUrl(authUser.getId()));
        mav.addObject("totalUsers", theUserService.getUserCount());
        mav.addObject("totalThreads", theThreadService.getThreadCount());
        return mav;
    }

}
