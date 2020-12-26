package guru.springframework.recipes.controllers;

import guru.springframework.recipes.domain.Recipe;
import guru.springframework.recipes.repositories.RecipeRepository;
import guru.springframework.recipes.services.RecipeService;
import guru.springframework.recipes.services.RecipeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class IndexControllerTest {

    IndexController indexController;
    RecipeService recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    Model model;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository);
        indexController = new IndexController(recipeService);
    }

    @Test
    void getIndexPage() {
        //given
        Set<Recipe> recipesData = new HashSet<>();
        recipesData.add(new Recipe().builder().id(1l).build());
        recipesData.add(new Recipe().builder().id(2l).build());

        when(recipeRepository.findAll()).thenReturn(recipesData);

        ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);


        //then
        assertEquals("index", indexController.getIndexPage(model));
        verify(model, (times(1))).addAttribute(eq("recipes"), argumentCaptor.capture());
        verify(recipeRepository, times(1)).findAll();
        assertEquals(2, argumentCaptor.getValue().size());
    }

}