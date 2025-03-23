package app.rating.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.UUID;

@Data @NoArgsConstructor @AllArgsConstructor
@Embeddable
public class RatingKeypair {

    @Column(name = "user_uuid")
    private UUID userId;

    @Column(name = "post_id")
    private Integer postId;

    @Override   //important for composite keys
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        RatingKeypair other = (RatingKeypair) o;
        return this.userId.equals(other.userId) && Objects.equals(this.postId, other.postId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, postId);
    }
}
