package app.post.repository;

import app.post.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    /**
     * Take all posts in a thread and order them in descending order by creation date.
     * @param threadId ID of the thread whose posts we're querying for.
     * @return A list of said posts.
     */
    List<Post> findAllByThreadWherePosted_IdOrderByCreationDateAsc(int threadId);

}
