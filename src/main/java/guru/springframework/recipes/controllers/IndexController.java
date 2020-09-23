package guru.springframework.recipes.controllers;

import guru.springframework.recipes.domain.Category;
import guru.springframework.recipes.domain.UnitOfMeasure;
import guru.springframework.recipes.repositories.CategoryRepository;
import guru.springframework.recipes.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class IndexController {

    private CategoryRepository categoryRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;

    public IndexController(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @RequestMapping({"/","","/index.html","/index"})
    public String getIndexPage(){

        Optional<Category> categoryOptional = categoryRepository.findByDescription("Mexican");
        Optional<UnitOfMeasure> unitOfMeasureOptional = unitOfMeasureRepository.findByDescription("Teaspoon");

        System.out.println("Category id: " + categoryOptional.get().getId());
        System.out.println("Unit of measure desc: " + unitOfMeasureOptional.get().getDescription());
        return "index";
    }
}
