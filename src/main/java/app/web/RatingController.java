package app.web;

import app.rating.model.RatingKeypair;
import app.rating.service.RatingService;
import app.security.AuthenticationUserData;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@AllArgsConstructor
@Controller
public class RatingController {

    final private RatingService theRatingService;

    @PostMapping("/rate_post")
    public void likePost(
            @AuthenticationPrincipal AuthenticationUserData auth,
            @RequestParam("action") String action,
            @RequestParam("postId") int postId
            ){
        switch (action){
            case "like" ->{
                theRatingService.likePostByUserId(auth.getUserUuid(), postId);
            }
            case "unrate" -> {
                theRatingService.unratePostByUserId(auth.getUserUuid(), postId);
            }
            case "dislike" -> {
                theRatingService.dislikePostByUserId(auth.getUserUuid(), postId);
            }
            default -> {
            }
        }
    }

}
