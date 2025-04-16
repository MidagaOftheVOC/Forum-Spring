package app.web;

import app.post.service.PostService;
import app.rating.service.RatingService;
import app.security.AuthenticationUserData;
import app.thread.service.ThreadService;
import app.user.service.UserService;
import app.web.common.CommonService;
import app.web.dto.PostCreationRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@AllArgsConstructor
public class PostController {

    final private ThreadService theThreadService;
    final private PostService thePostService;
    final private UserService theUserService;
    final private RatingService theRatingService;
    final private CommonService theCommonService;

    @PostMapping("/post/create")
    public ModelAndView createPost(
            @AuthenticationPrincipal AuthenticationUserData auth,
            @RequestParam("threadId") int threadId,
            RedirectAttributes redirectAttributes,
            @Valid PostCreationRequest pcr,
            BindingResult result
            ){

        redirectAttributes.addFlashAttribute("scrollDown", true);

        if(result.hasErrors()){
            redirectAttributes.addFlashAttribute("errMsg_postCreation", "Празни постове са забранени.");
            return new ModelAndView("redirect:/thread/" + threadId);
        }

        thePostService.registerPost(auth, pcr, threadId);
        return new ModelAndView("redirect:/thread/" + threadId);
    }

}
