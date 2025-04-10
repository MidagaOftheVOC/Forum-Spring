package app.post.model;


import app.thread.model.Thread;
import app.user.model.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString(exclude = {"threadWherePosted", "originalPoster"})
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "thread_id_where_posted", referencedColumnName = "id")
    private Thread threadWherePosted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "original_poster_uuid", referencedColumnName = "user_uuid")
    private User originalPoster;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int points;

    @Column(name = "date_creation")
    private LocalDateTime creationDate;

    @Column(name = "date_redaction")
    private LocalDateTime redactionDate;

}
