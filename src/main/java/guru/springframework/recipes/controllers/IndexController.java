package guru.springframework.recipes.controllers;

import guru.springframework.recipes.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class IndexController {

    private final RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"/","","/index.html","/index"})
    public String getIndexPage(Model model){

        model.addAttribute("recipes", recipeService.getRecipes());
        log.debug("Added recipes to model.");
        return "index";
    }
}
