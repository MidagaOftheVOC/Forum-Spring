package app.category.service;

import app.GCV;
import app.category.CategoryAlreadyExists;
import app.category.model.Category;
import app.category.repository.CategoryRepository;
import app.thread.model.Thread;
import app.thread.service.ThreadService;
import app.web.dto.CategoryCreationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository theCategoryRepo;
    private final ThreadService theThreadService;

    public List<Category> getAllCategories(){
        return theCategoryRepo.findAll();
    }

    public List<Thread> getAllThreadsByCategoryId(int id){
        return theThreadService.getAllThreadsByCategoryId(id);
    }

    public void attachCategoryToThread(
            int threadId,
            int categoryId
    ) {
        Category category = getCategory(categoryId);
        Thread thread = theThreadService.getThread(threadId);

        if(
            theThreadService.attachCategory(
                thread,
                category
            )
        ) { // on success
            category.getThreads().add(thread);
            theCategoryRepo.save(category);
        }
        else{ // on fail
            if(GCV.isDebugging())
                System.out.println("Attaching category to thread[%s] failed.".formatted(thread.getId()));
        }
    }

    public void processCreationRequest(
            CategoryCreationRequest ccr
    ) {
        if(categoryWithNameExists(ccr.getName())){
            throw new CategoryAlreadyExists("Category with name [%s] already exists!".formatted(ccr.getName()));
        }

        Category category = new Category();

        category.setCategoryName(ccr.getName());
        category.setCategoryColour(ccr.getColour());
        theCategoryRepo.save(category);
    }


    private Category getCategory(int id){
        Optional<Category> c = theCategoryRepo.findById(id);

        if(c.isPresent()){
            return c.get();
        }
        else
            return null;
    }

    private boolean categoryWithNameExists(String name){
        Optional<Category> c = theCategoryRepo.findByCategoryName(name);

        return c.isPresent();
    }

}
