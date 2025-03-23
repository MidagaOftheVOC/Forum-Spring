package app.post.service;


import app.post.repository.PostRepository;
import app.thread.repository.ThreadRepository;
import app.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PostService {

    private final PostRepository      thePostRepository;
    private final UserRepository      theUserRepository;
    private final ThreadRepository    theThreadRepository;

    public PostService(
        PostRepository _post_repo,
        UserRepository _user_repo,
        ThreadRepository _thread_repo
    )
    {
        thePostRepository = _post_repo;
        theUserRepository = _user_repo;
        theThreadRepository = _thread_repo;
    }



    public void redactPost(int postId){



    }

    /*
    * What can be done with a post?
    *
    * -creation
    * -deletion
    * -redaction
    * -maintain rating, i.e. in-/decrement an integer //    Off-loaded to Rating
    *
    * */




}
