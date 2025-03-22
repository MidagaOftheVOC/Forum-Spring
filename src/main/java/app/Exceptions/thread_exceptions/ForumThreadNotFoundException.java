package app.Exceptions.thread_exceptions;

import app.Exceptions.user_exceptions.ForumUserException;

public class ForumThreadNotFoundException extends ForumUserException {

    public ForumThreadNotFoundException(String _msg){
        super(_msg);
    }

}
