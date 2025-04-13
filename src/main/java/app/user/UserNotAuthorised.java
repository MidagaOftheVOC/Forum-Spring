package app.user;

/**
 * This exception is used when a LOGGED IN user attempts to access something
 * that is off-limits.
 */
public class UserNotAuthorised extends RuntimeException {
    public UserNotAuthorised(String message) {
        super(message);
    }
}
