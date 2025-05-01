package app.category.service;

import app.GCV;
import app.category.CategoryAlreadyExists;
import app.category.model.Category;
import app.category.repository.CategoryRepository;
import app.thread.model.Thread;
import app.thread.service.ThreadService;
import app.web.dto.CategoryCreationRequest;
import lombok.AllArgsConstructor;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.stereotype.Service;

import java.util.HexFormat;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.random.RandomGenerator;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository theCategoryRepo;
    private final ThreadService theThreadService;

    /**
     *
     * @param ccr
     * @return At this point in time, the name has been checked for errors automatically by the DTO.
     */
    public boolean handleNewCreationRequest(
            CategoryCreationRequest ccr
    ) {

        ccr.setColour(validateColourValue(ccr.getColour()));

        if(isCategoryNameTaken(ccr.getName())){
            return false;
        }

        Category category = new Category();

        category.setCategoryColour(ccr.getColour());
        category.setCategoryName(ccr.getName());
        theCategoryRepo.save(category);
        return true;
    }


    private String validateColourValue(
            String colour
    ) {
        if(colour == null || colour.isEmpty()){
            return generateNewColourValue();
        }
        boolean flag_ReplacementNeeded = false;

        if(colour.length() != 6){
            flag_ReplacementNeeded = true;
        }

        for(int i = 0; i < 6; i++){
            if( !HexFormat.isHexDigit(colour.charAt(i)) ){
                flag_ReplacementNeeded = true;
                break;
            }
        }


        if(flag_ReplacementNeeded){
            return generateNewColourValue();
        }
        else
            return colour;
    }

    private boolean isCategoryNameTaken(
            String name
    ) {
        return theCategoryRepo.findByCategoryName(name).isPresent();
    }

    private String generateNewColourValue(){
        Random random = new Random();
        int r = 100 + random.nextInt(156); // [100, 255]
        int g = 100 + random.nextInt(156);
        int b = 100 + random.nextInt(156);

        return String.format("%02x%02x%02x", r, g, b).toUpperCase();
    }

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
