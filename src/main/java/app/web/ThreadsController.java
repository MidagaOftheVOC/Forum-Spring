package app.web;

import app.avatar.AvatarService;
import app.post.model.Post;
import app.post.service.PostService;
import app.rating.service.RatingService;
import app.security.AuthenticationUserData;
import app.thread.model.Thread;
import app.thread.service.ThreadService;
import app.user.model.User;
import app.user.service.UserService;
import app.web.common.CommonService;
import app.web.dto.PostCreationRequest;
import app.web.dto.PostWithAvatar;
import app.web.dto.ThreadCreationObject;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Controller
@RequestMapping("/thread")
public class ThreadsController {

    final private ThreadService theThreadService;
    final private UserService theUserService;
    final private PostService thePostService;
    final private RatingService theRatingService;
    final private AvatarService theAvatarService;

    final private CommonService theCommonService;


    @GetMapping("/{threadId}")
    public ModelAndView getThreadView(
            @AuthenticationPrincipal AuthenticationUserData auth,
            @PathVariable("threadId") int threadId
            ){
        ModelAndView mav = theCommonService.getCommonHeaderMAV(auth);

        if(auth != null){
            mav.addObject("postCreationRequest", new PostCreationRequest());
        }

        Thread thread = theThreadService.openThread(threadId); // on fail we go to /thread/not_found
        List<Post> sortedPostList = thePostService.getSortedPostsFromThread(threadId);
        List<PostWithAvatar> finalisedPostListForRendering = new ArrayList<>();

        for(int i = 0; i < sortedPostList.size(); i++){
            PostWithAvatar post = new PostWithAvatar(
                    sortedPostList.get(i),
                    theAvatarService.getAvatarUrl(sortedPostList.get(i).getOriginalPoster().getId())
            );
            finalisedPostListForRendering.add(post);
        }

        mav.addObject("thread", thread);
        mav.addObject("posts", finalisedPostListForRendering);
        mav.setViewName("thread/view"); // !!!
        return mav;
    }

    @GetMapping("/create")
    public ModelAndView getThreadCreatePage(
            @AuthenticationPrincipal AuthenticationUserData auth
            ){
        ModelAndView mav = theCommonService.getCommonHeaderMAV(auth);
        mav.addObject("threadCreationRequest", new ThreadCreationObject());
        mav.setViewName("thread/create");
        return mav;
    }

    @PostMapping("/create")
    public ModelAndView createThread(
            @AuthenticationPrincipal AuthenticationUserData auth,
            @Valid ThreadCreationObject threadCreationRequest,
            BindingResult result
            ){
        // Get control already ensures a user is logged in to use this.

        if(result.hasErrors()){
            ModelAndView errMav = theCommonService.getCommonHeaderMAV(auth);

            if(result.hasFieldErrors("title")){
                errMav.addObject("thrCreationErrmsg", "Заглавието е неподходящо");
                return errMav;
            }
            else {
                errMav.addObject("thrCreationErrmsg", "Тялото на нишката е празно или по-дълго от 1024 символа.");
                return errMav;
            }
        }

        int newThreadId = theThreadService.createThread(auth, threadCreationRequest);
        return new ModelAndView("redirect:/thread/view/" + newThreadId);
    }


}
