package nikhil.springframework.recipeapp.controller;

import lombok.extern.slf4j.Slf4j;
import nikhil.springframework.recipeapp.commands.IngredientCommand;
import nikhil.springframework.recipeapp.commands.RecipeCommand;
import nikhil.springframework.recipeapp.commands.UnitOfMeasureCommand;
import nikhil.springframework.recipeapp.domain.Recipe;
import nikhil.springframework.recipeapp.domain.UnitOfMeasure;
import nikhil.springframework.recipeapp.services.IngredientService;
import nikhil.springframework.recipeapp.services.RecipeService;
import nikhil.springframework.recipeapp.services.UnitOfMeasureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class IngredientController {

    RecipeService recipeService;
    IngredientService ingredientService;
    UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredients")
    public String getIngredients(@PathVariable String recipeId, Model model){
        log.debug("Fetching the ingredients for the id" + recipeId);
        model.addAttribute("recipe",recipeService.findCommandById(Long.valueOf(recipeId)));
        return "recipe/ingredient/list";
    }
    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredients/{ingredientId}/show")
    public String showIngredients(@PathVariable String recipeId,
                                  @PathVariable String ingredientId, Model model){

        model.addAttribute("ingredient", ingredientService.
                findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(ingredientId)));
        return "recipe/ingredient/show";
    }

    //Get the Ingredient form
    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredients/{ingredientId}/update")
    public String updateIngredient(@PathVariable String recipeId,
                                   @PathVariable String ingredientId, Model model)
    {
        model.addAttribute("ingredient",
                ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(ingredientId)));
        model.addAttribute("uomList", unitOfMeasureService.getUnitOfMeasures());
        return "recipe/ingredient/ingredientForm";
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredient/new")
    public String newRecipe(@PathVariable String recipeId, Model model){
        //Make sure we have a proper id value
        RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(recipeId));
        //Raise exception if null
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(Long.valueOf(recipeId));
        model.addAttribute("ingredient", ingredientCommand); //data from the form will be binded here

        ingredientCommand.setUnitOfMeasure(new UnitOfMeasureCommand());

        model.addAttribute("uomList", unitOfMeasureService.getUnitOfMeasures());

        return "recipe/ingredient/ingredientForm";
    }


    //Save the binded object here --> this route is called on form action
    //the form returns a IngredientCommand
    @PostMapping("recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientCommand command){
        if(command.getUnitOfMeasure() == null){
            log.error("Null pointer exception UOM not captured");
        }
        command.setUnitOfMeasure(unitOfMeasureService.getUnitOfMeasures()
        .stream().filter(unitOfMeasureCommand -> unitOfMeasureCommand.getId().equals(command.getUnitOfMeasure().getId()))
        .findFirst().orElse(null));

        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);

        log.debug("saved recipe id:" + savedCommand.getRecipeId());
        log.debug("saved ingredient id:" + savedCommand.getId());

        return "redirect:/recipe/" + savedCommand.getRecipeId() + "/ingredients/" + savedCommand.getId() + "/show";

    }
}
