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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor
public class PostController {

    final private ThreadService theThreadService;
    final private PostService thePostService;
    final private UserService theUserService;
    final private RatingService theRatingService;
    final private CommonService theCommonService;

    @PostMapping("/post/create")
    public String createPost(
            @AuthenticationPrincipal AuthenticationUserData auth,
            @Valid PostCreationRequest pcr,
            @RequestParam("threadId") int threadId,
            BindingResult result
            ){

        if(result.hasErrors()){
            //"redirect:/thread/view/" + threadId +
            return "redirect:/thread/view/" + threadId;
        }

        thePostService.registerPost(auth, pcr, threadId);

        return "redirect:/thread/view/" + threadId;
    }

}
