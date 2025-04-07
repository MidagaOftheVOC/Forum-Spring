package app.rating.service;


import app.rating.repository.RatingRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RatingService {

    final private RatingRepository theRatingRepository;

    public RatingService(
            RatingRepository ratingRepository
    )
    {
        theRatingRepository = ratingRepository;
    }


    public void likePostByUserId(UUID userId, int postId){

    }

    public void dislikePostByUserId(UUID userId, int postId){

    }

    public void unratePostByUserId(UUID userId, int postId){

    }

}
