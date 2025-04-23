package app.category.repository;

import app.category.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    List<Category> findAll();

    Optional<Category> findByCategoryName(String categoryName);

    Optional<Category> findById(int id);

    List<Category> findByThreads(Thread thread);
}
