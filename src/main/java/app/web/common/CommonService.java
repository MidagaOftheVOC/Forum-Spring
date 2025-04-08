package app.web.common;

import app.avatar.AvatarService;
import app.post.service.PostService;
import app.security.AuthenticationUserData;
import app.thread.service.ThreadService;
import app.user.model.User;
import app.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

@Service
@AllArgsConstructor
public class CommonService {

    final UserService theUserService;
    final ThreadService theThreadService;
    final PostService thePostService;
    final AvatarService theAvatarService;



    public ModelAndView getCommonHeaderMAV(AuthenticationUserData auth){
        ModelAndView mav = new ModelAndView();
        if(auth != null) {
            User authUser = theUserService.getUserById(auth.getUserUuid());
            mav.addObject("user", authUser);
            mav.addObject("loggedUserURL", theAvatarService.getAvatarUrl(authUser.getId()));
        }
        mav.addObject("totalUsers", theUserService.getUserCount());
        mav.addObject("totalThreads", theThreadService.getThreadCount());
        return mav;
    }

}
