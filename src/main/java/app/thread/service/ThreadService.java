package app.thread.service;


import app.thread.ForumThreadNotFoundException;
import app.GCV;
import app.thread.model.Thread;
import app.thread.repository.ThreadRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class ThreadService {

    private final ThreadRepository theThreadRepository;

    public ThreadService(
            ThreadRepository _thread_repo
    )
    {
        theThreadRepository = _thread_repo;
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

    public void changeThreadLockStatus(int _target_thread_id)
        throws ForumThreadNotFoundException {

        Thread thread = theThreadRepository.findById(_target_thread_id).orElseThrow(() -> new ForumThreadNotFoundException(
                ("Thread with ID[%d] not found." + (GCV.isDebugging() ? "@changeThreadLockStatus" : "")).formatted(_target_thread_id)
        ));

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
