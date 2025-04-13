package app.user.model;


import app.post.model.Post;
import app.thread.model.Thread;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.*;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"threadList", "postList"})
@Entity
@Table(name = "users")
public class User {

    @Id  @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_uuid", nullable = false, unique = true)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = false)
    private String userhash;

    @Column(name = "shown_username")
    private String shownUsername;

    @Column
    private String quote;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_status", nullable = false)
    private UserStatus userStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", nullable = false)
    private UserType userType;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "total_posts")
    private int totalPosts;

    @Column(name = "date_creation")
    private LocalDateTime creationDate;

    @Column(name = "date_last_account_redaction")
    private LocalDateTime lastRedactDate;

    @Column(name = "date_last_activity")
    private LocalDateTime lastActiveDate;

    @OneToMany(mappedBy = "originalPoster", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Thread> threadList;

    @OneToMany(mappedBy = "originalPoster", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Post> postList;


    public String whichUsernameToShow(){
        return (shownUsername == null) ? username : shownUsername;
    }

    public String bulgarianType(){
        if(userStatus == UserStatus.BANNED){
            return "Баннат";
        }
        switch (userType){
            case ADMIN -> {
                return "Админ";
            }
            default -> {
                return "Стандартен";
            }
        }
    }

    public boolean isAdmin(){
        return UserType.ADMIN == userType;
    }

    public boolean isActive(){
        return UserStatus.ACTIVE == userStatus;
    }

    public boolean isBanned(){
        return UserStatus.BANNED == userStatus;
    }

    public String debugString(){
        return "User object -> { id = [%s], username = [%s], shown_username = [%s], userhash = [%s] }"
                .formatted(id.toString(), username, shownUsername, userhash);
    }

    /*
    * Here go the relations, i.e. Lists for threads and posts
    * */

}
