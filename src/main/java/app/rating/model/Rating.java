package app.rating.model;


import app.post.model.Post;
import app.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "post_ratings")
public class Rating {

    @EmbeddedId
    private RatingKeypair keypair;

    @Column
    private int rating;

//    @ManyToOne
//    @JoinColumn(name = "user_uuid", insertable = false, updatable = false)
//    private User user;

//    @ManyToOne
//    @JoinColumn(name = "post_id", insertable = false, updatable = false)
//    private Post post;

}
