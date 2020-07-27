package nikhil.springframework.recipeapp.controller;

import lombok.extern.slf4j.Slf4j;
import nikhil.springframework.recipeapp.domain.Category;
import nikhil.springframework.recipeapp.domain.UnitOfMeasure;
import nikhil.springframework.recipeapp.repositories.CategoryRepository;
import nikhil.springframework.recipeapp.repositories.RecipeRepository;
import nikhil.springframework.recipeapp.repositories.UnitOfMeasureRepository;
import nikhil.springframework.recipeapp.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Slf4j
@Controller
public class IndexController {

    private  final RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"", "/index", "/"})
    public  String getIndexPage(Model model){
        log.debug("Loading index page");
        model.addAttribute("recipes", recipeService.getRecipes());
        return "index";
    }
}
