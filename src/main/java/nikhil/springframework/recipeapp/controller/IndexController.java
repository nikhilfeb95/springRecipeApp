package nikhil.springframework.recipeapp.controller;

import lombok.extern.slf4j.Slf4j;
import nikhil.springframework.recipeapp.domain.Category;
import nikhil.springframework.recipeapp.domain.UnitOfMeasure;
import nikhil.springframework.recipeapp.repositories.CategoryRepository;
import nikhil.springframework.recipeapp.repositories.RecipeRepository;
import nikhil.springframework.recipeapp.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Slf4j
@Controller
public class IndexController {
    private final CategoryRepository  categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final RecipeRepository recipeRepository;
    public IndexController(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository, RecipeRepository recipeRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.recipeRepository = recipeRepository;
    }

    @RequestMapping({"", "/index", "/"})
    public  String getIndexPage(Model model){
        log.debug("Loading index page");
        model.addAttribute("recipes", recipeRepository.findAll());
        return "index";
    }
}
