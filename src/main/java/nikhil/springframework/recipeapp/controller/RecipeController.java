package nikhil.springframework.recipeapp.controller;

import lombok.extern.slf4j.Slf4j;
import nikhil.springframework.recipeapp.commands.RecipeCommand;
import nikhil.springframework.recipeapp.exceptions.NotFoundException;
import nikhil.springframework.recipeapp.services.RecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Slf4j
@Controller
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }


    @RequestMapping("/recipe/{id}/show")
    public String showById(@PathVariable String id, Model model){
        model.addAttribute("recipe", recipeService.findById(Long.parseLong(id)));

        return "recipe/show";
    }


    @RequestMapping("recipe/new")
    public String newRecipe(Model model){
        model.addAttribute("recipe", new RecipeCommand());

        return "recipe/recipeform";
    }

    @PostMapping("recipe")
    public String saveOrUpdate(@ModelAttribute RecipeCommand command){
        RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);

        return "redirect:/recipe/" + savedCommand.getId() + "/show";
    }

    @RequestMapping("recipe/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model){
        RecipeCommand saveCommand = recipeService.findCommandById(Long.valueOf(id));
        model.addAttribute("recipe", saveCommand);

        return "recipe/recipeform";
    }

    @RequestMapping("/recipe/{id}/delete")
    public String deleteById(@PathVariable String id, Model model){

        log.debug("Deleting recipe with id "+ id);

        recipeService.deleteById(Long.valueOf(id));
        return "redirect:/";
    }

    //Route handles all 404 errors
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception ex){
        log.error("Handling not found exception");
        log.error(ex.getMessage());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("404error");
        modelAndView.addObject("exception", ex);

        return modelAndView;
    }
}
