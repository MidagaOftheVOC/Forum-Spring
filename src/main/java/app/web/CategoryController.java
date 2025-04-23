package app.web;


import app.category.service.CategoryService;
import app.security.AuthenticationUserData;
import app.thread.service.ThreadService;
import app.web.common.CommonService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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




        return mav;
    }

    @GetMapping("/search/{catId}")
    public ModelAndView getSearchByCategoryPage(
            @AuthenticationPrincipal AuthenticationUserData auth,
            @PathVariable("catId") int categoryId
    ) {
        ModelAndView mav = theCommonService.getCommonHeaderMAV(auth);


        return mav;
    }

}
