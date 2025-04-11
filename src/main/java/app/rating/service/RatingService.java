package app.rating.service;

import app.post.service.PostService;
import app.rating.model.Rating;
import app.rating.model.RatingKeypair;
import app.rating.repository.RatingRepository;
import app.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class RatingService {

    final private RatingRepository theRatingRepository;
    final private PostService thePostService;
    final private UserService theUserService;

    public void likePostByUserId(UUID userId, int postId) {
        changeVote(userId, postId, 1);
    }

    public void dislikePostByUserId(UUID userId, int postId) {
        changeVote(userId, postId, -1);
    }

    public void unratePostByUserId(UUID userId, int postId) {
        Optional<Rating> rating = checkForPreviousRecords(userId, postId);
        if (rating.isPresent()) {
            int previousRating = rating.get().getRating();
            theRatingRepository.delete(rating.get());
            thePostService.modifyVoteCount(postId, -previousRating);
        }
    }

    private void changeVote(UUID userId, int postId, int newRating) {
        Optional<Rating> existingRating = checkForPreviousRecords(userId, postId);

        if (existingRating.isPresent()) {
            int previousRating = existingRating.get().getRating();
            if (previousRating != newRating) {
                int voteChange = newRating - previousRating;
                existingRating.get().setRating(newRating);
                theRatingRepository.save(existingRating.get());
                thePostService.modifyVoteCount(postId, voteChange);
            }
        } else {
            Rating newRatingRecord = new Rating();
            newRatingRecord.setRating(newRating);
            newRatingRecord.setKeypair(generateKeypair(userId, postId));
            theRatingRepository.save(newRatingRecord);
            thePostService.modifyVoteCount(postId, newRating);
        }
    }

    private RatingKeypair generateKeypair(UUID userId, int postId) {
        RatingKeypair key = new RatingKeypair();
        key.setUserId(userId);
        key.setPostId(postId);
        return key;
    }

    private Optional<Rating> checkForPreviousRecords(UUID userId, int postId) {
        return theRatingRepository.findByKeypairUserIdAndKeypairPostId(userId, postId);
    }
}
