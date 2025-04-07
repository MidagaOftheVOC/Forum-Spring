package app.thread.model;


import app.category.model.Category;
import app.post.model.Post;
import app.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "threads")
public class Thread {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "original_poster_uuid", referencedColumnName = "user_uuid")
    private User originalPoster;

    @OneToMany(mappedBy = "threadWherePosted", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Post> postList;

    @Column(name = "content_title", nullable = false)
    private String threadTitle;

    @Column(name = "content_body", nullable = false)
    private String threadBody;

    @Column(name = "is_locked")
    private boolean isLocked;

    @Column(name = "is_pinned")
    private boolean isPinned;

    @Column(name = "view_count")
    private int views;

    @Column(name = "posts_count")
    private int posts;

    @Column(name = "date_creation")
    private LocalDateTime creationDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "category_thread_helper",
        joinColumns = @JoinColumn(name = "thread_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;

}
