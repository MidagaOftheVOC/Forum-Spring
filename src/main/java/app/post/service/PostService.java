package app.post.service;


import app.post.PostMalformedCreationRequest;
import app.post.model.Post;
import app.post.repository.PostRepository;
import app.thread.repository.ThreadRepository;
import app.thread.service.ThreadService;
import app.user.model.User;
import app.user.repository.UserRepository;
import app.user.service.UserService;
import app.web.dto.PostCreationRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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
        if(pcr.getContent().isEmpty() || pcr.getContent().isBlank()){
            throw new PostMalformedCreationRequest("PostCreationRequest object with empty 'content' field.");
        }
        if(pcr.getContent().length() >= POST_CHAR_LIMIT){
            throw new PostMalformedCreationRequest("PostCreationRequest.content.length() is longer than the POST_CHAR_LIMIT");
        }
    }

    public void createPost(
            PostCreationRequest pcr,
            User authenticatedUser
    ){
        validatePostCreationRequest(pcr);

        Post newPost = new Post();

        newPost.setContent(pcr.getContent());
        newPost.setThreadWherePosted(
                theThreadService.getThread(pcr.getThreadIdWherePosted())
        );

        newPost.setCreationDate(LocalDateTime.now());
        newPost.setOriginalPoster(authenticatedUser);
        newPost.setPoints(0);
    }




}
