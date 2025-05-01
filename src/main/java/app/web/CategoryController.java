package app.web;


import app.category.service.CategoryService;
import app.security.AuthenticationUserData;
import app.thread.service.ThreadService;
import app.web.common.CommonService;
import app.web.dto.CategoryCreationRequest;
import feign.Param;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@AllArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService theCategoryService;

    private final CommonService theCommonService;

    @GetMapping("/browse")
    public ModelAndView getListPage(
            @AuthenticationPrincipal AuthenticationUserData auth
            )
    {
        ModelAndView mav = theCommonService.getCommonHeaderMAV(auth);

        // "categoriesList"

        mav.addObject("categoriesList", theCategoryService.getAllCategories());

        // mav.setViewName("category/browse");
        return mav;
    }

    @GetMapping("/search/{catId}")
    public ModelAndView getSearchByCategoryPage(
            @AuthenticationPrincipal AuthenticationUserData auth,
            @PathVariable("catId") int categoryId
    ) {
        ModelAndView mav = theCommonService.getCommonHeaderMAV(auth);

        // "threadsFound"
        mav.addObject("threadsFound", theCategoryService.getAllThreadsByCategoryId(categoryId));

        mav.setViewName("category/search");
        return mav;
    }

    @GetMapping("/creation")
    public ModelAndView getCategorySearch(
        @AuthenticationPrincipal AuthenticationUserData auth
    ) {
        ModelAndView mav = theCommonService.getCommonHeaderMAV(auth);

        mav.addObject("categoryCreationRequest", new CategoryCreationRequest());


        return mav;
    }

    @PostMapping("/create")
    public ModelAndView categoryCreation(
            @AuthenticationPrincipal AuthenticationUserData auth,
            @Valid CategoryCreationRequest ccr,
            BindingResult result
    ) {
        ModelAndView mav = theCommonService.getCommonHeaderMAV(auth);
        if(result.hasErrors()){
            mav.addObject("categoryCreationRequest", ccr);
            mav.setViewName("category/creation");
            return mav;
        }

        // the other function for creation requests is abandoned
        theCategoryService.handleNewCreationRequest(ccr);

        mav.setViewName("redirect:/main");
        return mav;
    }

}
