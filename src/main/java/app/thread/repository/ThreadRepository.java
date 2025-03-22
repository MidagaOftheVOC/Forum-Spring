package app.thread.repository;

import app.thread.model.Thread;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface ThreadRepository extends JpaRepository<Thread, Integer> {

    List<Thread> findByOriginalPoster_Id(UUID userID);

    Optional<Thread> findByThreadTitle(String threadTitle);

    List<Thread> findByIsPinned(boolean isPinned);

    List<Thread> findByIsLocked(boolean isLocked);

    Optional<Thread> findById(int id);

}
