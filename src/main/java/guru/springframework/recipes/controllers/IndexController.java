package guru.springframework.recipes.controllers;

import guru.springframework.recipes.repositories.CategoryRepository;
import guru.springframework.recipes.repositories.RecipeRepository;
import guru.springframework.recipes.repositories.UnitOfMeasureRepository;
import guru.springframework.recipes.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    private final RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"/","","/index.html","/index"})
    public String getIndexPage(Model model){

        model.addAttribute("recipes", recipeService.getRecipes());

        return "index";
    }
}
