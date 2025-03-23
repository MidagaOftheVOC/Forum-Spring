package app.thread;

import app.user.ForumUserException;

public class ForumThreadNotFoundException extends ForumUserException {

    public ForumThreadNotFoundException(String _msg){
        super(_msg);
    }

}
