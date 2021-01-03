package guru.springframework.recipes.bootstrap;

import guru.springframework.recipes.domain.*;
import guru.springframework.recipes.repositories.CategoryRepository;
import guru.springframework.recipes.repositories.RecipeRepository;
import guru.springframework.recipes.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Slf4j
@Component
public class DataLoader implements ApplicationListener<ApplicationReadyEvent> {

    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public DataLoader(RecipeRepository recipeRepository, CategoryRepository categoryRepository,
                      UnitOfMeasureRepository unitOfMeasureRepository) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(final ApplicationReadyEvent applicationReadyEvent) {
        recipeRepository.saveAll(getRecipes());
        log.debug("Loading bootstrap data...");
    }

    private ArrayList<Recipe> getRecipes() {
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
            if (!x.isPresent()) throw new RuntimeException("Given category does not exist.");
        });

        Category americanCategory = americanCategoryOptional.get();
        Category mexicanCategory = mexicanCategoryOptional.get();
        Category italianCategory = italianCategoryOptional.get();
        Category otherCategory = otherCategoryOptional.get();

        log.debug("Loaded categories...");
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
            if (!x.isPresent())
                throw new RuntimeException("Given unit of measure does not exist: " + x.get().toString());
        });

        UnitOfMeasure teaspoonUom = teaspoonUomOptional.get();
        UnitOfMeasure tablespoonUom = tablespoonUomOptional.get();
        UnitOfMeasure dashUom = dashUomOptional.get();
        UnitOfMeasure cupUom = cupUomOptional.get();
        UnitOfMeasure pieceUom = pieceUomOptional.get();
        //todo rest of the units

        log.debug("Loaded units of measure...");
        ArrayList<Recipe> recipes = new ArrayList<>();

        Recipe guac = new Recipe();
        guac.setDescription("Perfect Guacamole");
        guac.setDifficulty(Difficulty.EASY);
        guac.setPrepTime(15);
        guac.setCookTime(60);
        guac.setServings(4);
        guac.setSource("www.simplerecipes.com");
        guac.setUrl("www.google.pl");
        guac.setDirections("1 Cut the avocado, remove flesh: Cut the avocados in half. Remove the pit. Score the " +
                "inside of the avocado with a blunt knife and scoop out the flesh with a spoon. (See How to Cut and " + "Peel an Avocado.) Place in a bowl.\n" + "2 Mash with a fork: Using a fork, " + "roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)\n" + "3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. " + "The acid in the lime juice will provide some balance to the richness of the avocado and will help " + "delay the avocados from turning brown.\n" + "Add the chopped onion, cilantro, black pepper, " + "and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili " + "pepper and add to the guacamole to your desired degree of hotness.\n" + "Remember that much " + "of this is done to taste because of the variability in the fresh ingredients. Start with this recipe" + " and adjust to your taste.\n" + "Chilling tomatoes hurts their flavor, so if you want to add " + "chopped tomato to your guacamole, add it just before serving.\n" + "4 Serve: Serve " + "immediately, or if making a few hours ahead, place plastic wrap on the surface of the guacamole and " + "press down to cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which" + " will turn the guacamole brown.) Refrigerate until ready to serve.");
        guac.getCategories().add(otherCategory);
        guac.getCategories().add(italianCategory);
        guac.addIngredient(new Ingredient(new BigDecimal(2), pieceUom, "ripe avocados"));
        guac.addIngredient(new Ingredient(new BigDecimal(1), tablespoonUom, "fresh lime juice"));

        guac.setNotes(new Notes("Be careful handling chiles if using. Wash your hands thoroughly after handling and " + "do not touch your eyes or the area near your eyes with your hands for several hours."));

        recipes.add(guac);

        Recipe taco = new Recipe();
        taco.setDescription("Spicy Chicken Taco");
        taco.setDifficulty(Difficulty.MODERATE);
        taco.setPrepTime(25);
        taco.setCookTime(25);
        taco.setServings(2);
        taco.setSource("www.simplerecipes.com");
        taco.setUrl("www.chicken.com");
        taco.setDirections("1 Prepare a gas or charcoal grill for medium-high, direct heat.\n" + "2 Make the " +
                "marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, " +
                "sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. " +
                "Add the chicken to the bowl and toss to coat all over.\n" + "Set aside to marinate while the " +
                "grill heats and you prepare the rest of the toppings.\n" + "Spicy Grilled Chicken Tacos\n" +
                "3 Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer " +
                "inserted into the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 " +
                "minutes.\n" + "\n" + "4 Warm the tortillas: Place each tortilla on the grill or on a hot, dry " +
                "skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the " +
                "tortilla, turn it with tongs and heat for a few seconds on the other side.\n" + "Wrap warmed " +
                "tortillas in a tea towel to keep them warm until serving.\n" + "5 Assemble the tacos: Slice " +
                "the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken " +
                "slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. " +
                "Serve with lime wedges.");
        taco.getCategories().add(mexicanCategory);
        taco.addIngredient(new Ingredient(new BigDecimal(2), pieceUom, "boneless, skinless chicken breast"));
        taco.addIngredient(new Ingredient(new BigDecimal(1), dashUom, "peter parker goulash"));

        taco.setNotes(new Notes("Look for ancho chile powder with the Mexican ingredients at your grocery store, on " +
                "buy it online. (If you can't find ancho chili powder, you replace the ancho chili, the oregano, and " +
                "the cumin with 2 1/2 tablespoons regular chili powder, though the flavor won't be quite the same.)"));

        recipes.add(taco);
        return recipes;
    }
}
