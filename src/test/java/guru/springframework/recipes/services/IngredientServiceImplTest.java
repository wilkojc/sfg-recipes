package guru.springframework.recipes.services;

import guru.springframework.recipes.commands.IngredientCommand;
import guru.springframework.recipes.commands.RecipeCommand;
import guru.springframework.recipes.converters.IngredientCommandToIngredient;
import guru.springframework.recipes.converters.IngredientToIngredientCommand;
import guru.springframework.recipes.converters.UnitOfMeasureCommandToUnitOfMeasure;
import guru.springframework.recipes.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.recipes.domain.Ingredient;
import guru.springframework.recipes.domain.Recipe;
import guru.springframework.recipes.repositories.RecipeRepository;
import guru.springframework.recipes.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class IngredientServiceImplTest {

    IngredientService ingredientService;

    private final IngredientToIngredientCommand ingredientToIngredientCommand =
            new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());

    private final IngredientCommandToIngredient ingredientCommandToIngredient =
            new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ingredientService = new IngredientServiceImpl(ingredientToIngredientCommand, ingredientCommandToIngredient, recipeRepository,
                unitOfMeasureRepository);
    }

    @Test
    void findByRecipeIdAndIngredientId() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        Ingredient ingredient1 = new Ingredient();
        Ingredient ingredient2 = new Ingredient();
        Ingredient ingredient3 = new Ingredient();
        ingredient1.setId(1L);
        ingredient2.setId(2L);
        ingredient3.setId(3L);

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));

        IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(1L, 2L);

        assertEquals(ingredientCommand.getId(), Long.valueOf(2));
        assertEquals(ingredientCommand.getRecipeId(), Long.valueOf(1));

        verify(recipeRepository, times(1)).findById(anyLong());
    }

    @Test
    void testFindByRecipeIdAndIngredientId() throws Exception {
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(3L);
        ingredientCommand.setRecipeId(2L);

        Optional<Recipe> recipeOptional = Optional.of(new Recipe());

        Recipe savedRecipe = new Recipe();
        Ingredient ingredient = new Ingredient();
        ingredient.setId(3L);
        savedRecipe.addIngredient(ingredient);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        when(recipeRepository.save(any())).thenReturn(savedRecipe);

        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(ingredientCommand);

        assertEquals(Long.valueOf(3L), savedCommand.getId());
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }

    @Test
    public void testDeleteIngredientByRecipeIdAndIngredientId() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Ingredient ingredient = new Ingredient();
        ingredient.setId(3L);
        recipe.addIngredient(ingredient);
        ingredient.setRecipe(recipe);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        ingredientService.deleteById(1L, 3L);

        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any(Recipe.class));

    }
}