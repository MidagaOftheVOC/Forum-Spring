package app.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController("")
@RequestMapping("")
public class ThreadsController {

    @PostMapping("/main")
    void helloWorld(@RequestBody String body){
        log.info("HELLO WORLD! Here's the HTML body: ");

        if(body != null)
            log.info(body);
        else
            log.debug("POST body is empty @/main");
    }

}
