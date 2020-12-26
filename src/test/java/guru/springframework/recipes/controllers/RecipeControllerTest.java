package guru.springframework.recipes.controllers;

import guru.springframework.recipes.domain.Recipe;
import guru.springframework.recipes.repositories.RecipeRepository;
import guru.springframework.recipes.services.RecipeService;
import guru.springframework.recipes.services.RecipeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.Mockito.*;
class RecipeControllerTest {

    @Mock
    RecipeRepository recipeRepository;

    RecipeController recipeController;
    RecipeService recipeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository);
        recipeController = new RecipeController(recipeService);
    }

    @Test
    void testGetRecipe() throws Exception{
        Recipe recipe = new Recipe();
        recipe.builder().id(1l).build();

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of((recipe)));

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1"));
    }
}