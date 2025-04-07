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

}
