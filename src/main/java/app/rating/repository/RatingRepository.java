package app.rating.repository;

import app.rating.model.Rating;
import app.rating.model.RatingKeypair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

/**
 */

@Repository
public interface RatingRepository extends JpaRepository<Rating, RatingKeypair> {


    Optional<Rating> findByKeypairUserIdAndKeypairPostId(UUID userId, Integer postId);



}
