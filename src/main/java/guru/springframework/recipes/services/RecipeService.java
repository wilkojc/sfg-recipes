package guru.springframework.recipes.services;

import guru.springframework.recipes.commands.RecipeCommand;
import guru.springframework.recipes.domain.Recipe;

import java.util.Set;

public interface RecipeService {
   Set<Recipe> getRecipes();
   Recipe findById(Long l);
   RecipeCommand saveRecipeCommand(RecipeCommand command);
}
