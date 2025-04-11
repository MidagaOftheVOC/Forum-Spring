package app.post.service;


import app.post.ForumPostNotFoundException;
import app.post.model.Post;
import app.post.repository.PostRepository;
import app.security.AuthenticationUserData;
import app.thread.model.Thread;
import app.thread.service.ThreadService;
import app.user.model.User;
import app.user.service.UserService;
import app.web.dto.PostCreationRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class PostService {

    private final PostRepository thePostRepository;
    private final UserService theUserService;
    private final ThreadService theThreadService;

    final int POST_CHAR_LIMIT = 1024;

    public List<Post> getSortedPostsFromThread(int threadId){
        return thePostRepository.findAllByThreadWherePosted_IdOrderByCreationDateAsc(threadId);
    }

    public void validatePostCreationRequest(PostCreationRequest pcr){

    }

    public void modifyVoteCount(
            int postId,
            int change
    ) {
        Post p = getPost(postId);
        p.setPoints(
                p.getPoints() + change
        );
        thePostRepository.save(p);
    }

    public Post getPost(int postId){
        Optional<Post> p = thePostRepository.findById(postId);

        if(!p.isPresent()){
            throw new ForumPostNotFoundException(
                    "Post with ID [%s] not found."
                            .formatted(postId)
            );
        }

        return p.get();
    }

    public void registerPost(
            AuthenticationUserData auth,
            PostCreationRequest pcr,
            int threadId
    ){
        validatePostCreationRequest(pcr);
        Post self = new Post();


        // Thread interraction
        Thread currentThread = theThreadService.getThread(threadId);
        theThreadService.canTakePosts(threadId);

        // User interraction
        User originalPoster = theUserService.getUserById(auth.getUserUuid());
        theUserService.isAbleToPost(auth.getUserUuid());

        self.setOriginalPoster(originalPoster);
        self.setThreadWherePosted(currentThread);
        self.setContent(pcr.getContent());
        self.setCreationDate(LocalDateTime.now());

        thePostRepository.save(self);
        theThreadService.incrementPostCount(threadId);
        theUserService.registerPost(auth.getUserUuid());
    }



}
