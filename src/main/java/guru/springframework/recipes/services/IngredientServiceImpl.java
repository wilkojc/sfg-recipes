package guru.springframework.recipes.services;

import guru.springframework.recipes.commands.IngredientCommand;
import guru.springframework.recipes.converters.IngredientCommandToIngredient;
import guru.springframework.recipes.converters.IngredientToIngredientCommand;
import guru.springframework.recipes.domain.Ingredient;
import guru.springframework.recipes.domain.Recipe;
import guru.springframework.recipes.repositories.RecipeRepository;
import guru.springframework.recipes.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand,
                                 IngredientCommandToIngredient ingredientCommandToIngredient,
                                 RecipeRepository recipeRepository,
                                 UnitOfMeasureRepository unitOfMeasureRepository) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {

        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (!recipeOptional.isPresent()) {
            //todo impl error handling
            log.error("recipe id not found. Id: " + recipeId);
        }

        Recipe recipe = recipeOptional.get();

        Optional<IngredientCommand> ingredientCommandOptional =
                recipe.getIngredients().stream().filter(ingredient -> ingredient.getId().equals(ingredientId))
                        .map(ingredientToIngredientCommand::convert).findFirst();

        if (!ingredientCommandOptional.isPresent()) {
            //todo impl error handling
            log.error("Ingredient id not found: " + ingredientId);
        }

        return ingredientCommandOptional.get();
    }

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand command) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipeId());

        if (!recipeOptional.isPresent()) {
            log.error("Recipe not found for id " + command.getRecipeId());
            return new IngredientCommand();
        } else {
            Recipe recipeFound = recipeOptional.get();

            Optional<Ingredient> ingredientOptional =
                    recipeFound.getIngredients().stream().filter(ingredient -> ingredient.getId()
                            .equals(command.getId())).findFirst();

            if (ingredientOptional.isPresent()) {
                Ingredient ingredientFound = ingredientOptional.get();

                ingredientFound.setId(command.getId());
                ingredientFound.setDescription(command.getDescription());
                ingredientFound.setAmount(command.getAmount());
                ingredientFound.setUom(unitOfMeasureRepository
                        .findById(command.getUom().getId())
                        .orElseThrow(() -> new RuntimeException("Unit of measure not found for id "
                                + command.getUom().getId())));
            } else {
                Ingredient ingredient = ingredientCommandToIngredient.convert(command);
                ingredient.setRecipe(recipeFound);
                recipeFound.addIngredient(ingredient);
            }

            Recipe savedRecipe = recipeRepository.save(recipeFound);

            Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getId().equals(command.getId())).findFirst();

            if(!savedIngredientOptional.isPresent()) {
                savedIngredientOptional =
                        savedRecipe.getIngredients().stream()
                                .filter(recipe -> recipe.getDescription().equalsIgnoreCase(command.getDescription()))
                                .filter(recipe -> recipe.getAmount().equals(command.getAmount()))
                                .filter(recipe -> recipe.getUom().getId().equals(command.getUom().getId()))
                                .findFirst();
            }

            return ingredientToIngredientCommand.convert(savedIngredientOptional.get());
}
}}
