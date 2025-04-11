package app.thread;

public class ThreadSendingPostsToLockedThread extends RuntimeException {
    public ThreadSendingPostsToLockedThread(String message) {
        super(message);
    }
}
