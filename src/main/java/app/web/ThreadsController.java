package app.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
public class ThreadsController {

    @GetMapping("/main")
    public ModelAndView getFrontPageThreads(){
        ModelAndView mav = new ModelAndView();




        return mav;
    }



}
