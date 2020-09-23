package guru.springframework.recipes.bootstrap;

import guru.springframework.recipes.domain.*;
import guru.springframework.recipes.repositories.CategoryRepository;
import guru.springframework.recipes.repositories.RecipeRepository;
import guru.springframework.recipes.repositories.UnitOfMeasureRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@Component
public class DataLoader implements ApplicationListener<ApplicationReadyEvent> {

    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent applicationReadyEvent) {
        recipeRepository.saveAll(getRecipes());
        System.out.println("Loaded recipes.");
    }

    public DataLoader(RecipeRepository recipeRepository, CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    private ArrayList<Recipe> getRecipes(){
        //load categories
        Optional<Category> americanCategoryOptional = categoryRepository.findByDescription("American");
        Optional<Category> mexicanCategoryOptional = categoryRepository.findByDescription("Mexican");
        Optional<Category> italianCategoryOptional = categoryRepository.findByDescription("Italian");
        Optional<Category> otherCategoryOptional = categoryRepository.findByDescription("Other");

        Set<Optional> categoriesOptionals = new HashSet<>();
        categoriesOptionals.add(americanCategoryOptional);
        categoriesOptionals.add(mexicanCategoryOptional);
        categoriesOptionals.add(italianCategoryOptional);
        categoriesOptionals.add(otherCategoryOptional);

        categoriesOptionals.forEach(x -> {
            if(!x.isPresent())
                throw new RuntimeException("Given category does not exist.");
        });

        Category americanCategory = americanCategoryOptional.get();
        Category mexicanCategory = mexicanCategoryOptional.get();
        Category italianCategory = italianCategoryOptional.get();
        Category otherCategory = otherCategoryOptional.get();

        //load units
        Optional<UnitOfMeasure> teaspoonUomOptional = unitOfMeasureRepository.findByDescription("teaspoon");
        Optional<UnitOfMeasure> tablespoonUomOptional = unitOfMeasureRepository.findByDescription("tablespoon");
        Optional<UnitOfMeasure> dashUomOptional = unitOfMeasureRepository.findByDescription("dash");
        Optional<UnitOfMeasure> cupUomOptional = unitOfMeasureRepository.findByDescription("cup");
        Optional<UnitOfMeasure> pieceUomOptional = unitOfMeasureRepository.findByDescription("");

        Set<Optional> uomsOptionals = new HashSet<>();
        uomsOptionals.add(teaspoonUomOptional);
        uomsOptionals.add(tablespoonUomOptional);
        uomsOptionals.add(dashUomOptional);
        uomsOptionals.add(cupUomOptional);
        uomsOptionals.add(pieceUomOptional);

        uomsOptionals.forEach(x -> {
            if(!x.isPresent())
                throw new RuntimeException("Given unit of measure does not exist: " + x.get().toString());
        });

        UnitOfMeasure teaspoonUom = teaspoonUomOptional.get();
        UnitOfMeasure tablespoonUom = tablespoonUomOptional.get();
        UnitOfMeasure dashUom = dashUomOptional.get();
        UnitOfMeasure cupUom = cupUomOptional.get();
        UnitOfMeasure pieceUom = pieceUomOptional.get();
        //todo rest of the units

        ArrayList<Recipe> recipes = new ArrayList<>();

        Recipe guac = new Recipe();
        guac.setDescription("Perfect Guacamole");
        guac.setDifficulty(Difficulty.EASY);
        guac.setPrepTime(15);
        guac.setCookTime(60);
        guac.setServings(4);
        guac.setSource("www.simplerecipes.com");
        guac.setUrl("www.google.pl");
        guac.setDirections("Take the avocado and eat it raw.");
        guac.getCategories().add(otherCategory);
        guac.getIngredients().add(new Ingredient(new BigDecimal(2), pieceUom, "ripe avocados", guac));
        guac.getIngredients().add(new Ingredient(new BigDecimal(1), tablespoonUom, "fresh lime juice", guac));

        Notes guacNotes = new Notes(guac, "Guacamole notes. Oh how perfect it is.");
        guac.setNotes(guacNotes);


        recipes.add(guac);

        Recipe taco = new Recipe();
        taco.setDescription("Spicy Chicken Taco");
        taco.setDifficulty(Difficulty.MODERATE);
        taco.setPrepTime(25);
        taco.setCookTime(25);
        taco.setServings(2);
        taco.setSource("www.simplerecipes.com");
        taco.setUrl("www.chicken.com");
        taco.setDirections("Spicy directions.");
        taco.getCategories().add(mexicanCategory);
        taco.getIngredients().add(new Ingredient(new BigDecimal(2), pieceUom, "boneless, skinless chicken breast", taco));
        taco.getIngredients().add(new Ingredient(new BigDecimal(1), dashUom, "peter parker goulash", taco));

        taco.getNotes().setContent("Chicken tacos are awesome. Especially when they're spicy.");
        taco.getNotes().setRecipe(taco);

        recipes.add(taco);
        return recipes;
    }
}