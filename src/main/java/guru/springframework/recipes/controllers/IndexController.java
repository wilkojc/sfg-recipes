package guru.springframework.recipes.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping({"/","","/index.html","/index"})
    public String getIndexPage(){
            System.out.println("Grabbing index view...");
        return "index";
    }
}
