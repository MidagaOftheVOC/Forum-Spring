package app.post;

public class ForumPostNotFoundException extends RuntimeException{

    public ForumPostNotFoundException(String _msg){
        super(_msg);
    }

}
