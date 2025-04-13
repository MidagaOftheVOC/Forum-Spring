package app.user;

public class BannedUserAttemptsAction extends RuntimeException {
    public BannedUserAttemptsAction(String message) {
        super(message);
    }
}
