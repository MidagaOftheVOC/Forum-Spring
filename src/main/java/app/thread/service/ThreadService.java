package app.thread.service;


import app.thread.ThreadSendingPostsToLockedThread;
import app.post.model.Post;
import app.security.AuthenticationUserData;
import app.thread.ForumThreadNotFoundException;
import app.GCV;
import app.thread.model.Thread;
import app.thread.repository.ThreadRepository;
import app.user.service.UserService;
import app.web.dto.ThreadCreationObject;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Slf4j  @AllArgsConstructor
@Service
public class ThreadService {

    private final ThreadRepository theThreadRepository;

    private final UserService theUserService;

    public void incrementPostCount(int threadId){
        Thread t = getThread(threadId);
        t.setPosts(t.getPosts() + 1);
        theThreadRepository.save(t);
    }

    public boolean canTakePosts(int threadId){
        Thread t = getThread(threadId);

        if(t.isLocked()){
            throw new ThreadSendingPostsToLockedThread(
                    "Attempting to post to locked thread with ID [%s]"
                            .formatted(threadId)
            );
        }

        return true;
    }

    public int createThread(AuthenticationUserData auth, ThreadCreationObject tco){

        Thread self = new Thread();

        self.setOriginalPoster(theUserService.getUserById(auth.getUserUuid()));
        self.setThreadTitle(tco.getTitle());
        self.setThreadBody(tco.getBody());
        self.setCreationDate(LocalDateTime.now());

        self.setLocked(false);
        self.setPinned(false);
        self.setViews(0);
        self.setPosts(0);

        theThreadRepository.save(self);

        return self.getId();
    }

    /**
     * Use this function when accessing it through thread/view.
     * It performs *on view* actions, like increaisng the view count and
     * returns the proper thread object.
     * @param threadId
     * @return
     */
    public Thread openThread(int threadId){
        Thread thr = getThread(threadId);
        thr.setViews(thr.getViews() + 1);
        theThreadRepository.save(thr);
        return thr;
    }

    public Thread getThread(int threadId){
        Optional<Thread> t = theThreadRepository.findById(threadId);

        if(t.isPresent()){
            return t.get();
        }
        else{
            throw new  ForumThreadNotFoundException("Thread with ID [%d] not found in repository.".formatted(threadId));
        }
    }

    public List<Thread> getThreadListBySortingMethod(String sort){
        switch(sort){
            case "views"    -> {
                return theThreadRepository.findTop10ByOrderByViewsDesc();
            }
            case "posts"    -> {
                return theThreadRepository.findTop10ByOrderByPostsDesc();
            }
            default         -> {    // recent by default, would also cover malicious strange query inputs
                return theThreadRepository.findTop10ByOrderByCreationDateDesc();
            }
        }
    }

    // we're not expecting more than 4 billion threads
    public int getThreadCount(){
        return (int)theThreadRepository.count();
    }

    // TODO: fix convention
    public void changeThreadLockStatus(int _target_thread_id)
        throws ForumThreadNotFoundException {

        Thread thread = getThread(_target_thread_id);

        log.info("Thread with ID[%d] LOCK status changed to " + !thread.isLocked());
        thread.setLocked(!thread.isLocked());
    }

    public void changeThreadPinStatus(int _target_thread_id)
    throws ForumThreadNotFoundException {

        Thread thread = theThreadRepository.findById(_target_thread_id).orElseThrow(() -> new ForumThreadNotFoundException(
                ("Thread with ID[%d] not found." + (GCV.isDebugging() ? "@changeThreadPinStatus" : "")).formatted(_target_thread_id)
        ));

        log.info("Thread with ID[%d] PIN status changed to " + !thread.isPinned());
        thread.setPinned(!thread.isPinned());
    }
}
