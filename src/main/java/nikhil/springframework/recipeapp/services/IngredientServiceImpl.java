package nikhil.springframework.recipeapp.services;

import lombok.extern.slf4j.Slf4j;
import nikhil.springframework.recipeapp.commands.IngredientCommand;
import nikhil.springframework.recipeapp.converters.IngredientCommandToIngredient;
import nikhil.springframework.recipeapp.converters.IngredientToIngredientCommand;
import nikhil.springframework.recipeapp.domain.Ingredient;
import nikhil.springframework.recipeapp.domain.Recipe;
import nikhil.springframework.recipeapp.repositories.RecipeRepository;
import nikhil.springframework.recipeapp.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService{

    RecipeRepository recipeRepository;
    IngredientToIngredientCommand ingredientToIngredientCommand;
    IngredientCommandToIngredient ingredientCommandToIngredient;
    UnitOfMeasureRepository unitOfMeasureRepository;

    public IngredientServiceImpl(RecipeRepository recipeRepository,
                                 IngredientToIngredientCommand ingredientToIngredientCommand,
                                 IngredientCommandToIngredient ingredientCommandToIngredient,
                                 UnitOfMeasureRepository unitOfMeasureRepository) {

        this.recipeRepository = recipeRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {

        Recipe recipe = recipeRepository.findById(recipeId).orElse(null);

        if(recipe == null)
        {
            log.error("recipe not found for Id" + recipeId);
            //redirect to an error page --> a runtime exception will jeopardise the whole thing
        }
        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredient -> ingredientToIngredientCommand.convert(ingredient)).findFirst();

        if(!ingredientCommandOptional.isPresent())
        {
            //implement error handling
            log.error("no ingredient with id : " + ingredientId);
        }
        return ingredientCommandOptional.get();
    }

    //Persist the updated Ingredient in the DB
    /*command -> this is the binded & updated object passed by the form --> persist this to the dB
    i.e save it in the repository (hibernate updates the DB as well)*/
    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand command) {

        Optional<Recipe> optionalRecipe = recipeRepository.findById(command.getRecipeId());

        if(!optionalRecipe.isPresent()){
            log.error("Recipe doesn't exist with Id : " + command.getRecipeId());
            return  new IngredientCommand(); //will use a better strategy later
        }

        Recipe recipe = optionalRecipe.get();

        Optional<Ingredient> ingredientOptional = recipe
                .getIngredients()
                .stream()
                .filter(ingredient -> ingredient.getId().equals(command.getId()))
                .findFirst();

        if(ingredientOptional.isPresent()){
            Ingredient ingredientFound = ingredientOptional.get();
            ingredientFound.setDescription(command.getDescription());
            ingredientFound.setAmount(command.getAmount());
            //check whether the updated UOM is present in the DB or not(Defensive programming stuff)
            ingredientFound.setUnitOfMeasure(unitOfMeasureRepository
                .findById(command.getUnitOfMeasure().getId())
            .orElseThrow(() -> new RuntimeException("UOM does not exist")));
        }
        else{
            //save the ingredient if not present --> for handling save
            Ingredient ingredient = ingredientCommandToIngredient.convert(command);
            ingredient.setRecipe(recipe);
            recipe.addIngredient(ingredient);
        }

        Recipe savedRecipe = recipeRepository.save(recipe);

        Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(command.getId())).findFirst();

        if(!savedIngredientOptional.isPresent()){
            //if not found by id then use description, amount etc --> can be an issue as ingredients aren't unique
            savedIngredientOptional = savedRecipe.getIngredients().stream()
                    .filter(recipeIngredients -> recipeIngredients.getDescription().equals(command.getDescription()))
                    .filter(recipeIngredients -> recipeIngredients.getAmount().equals(command.getAmount()))
                    .filter(recipeIngredients -> recipeIngredients.getUnitOfMeasure().getId()
                    .equals(command.getUnitOfMeasure().getId()))
                    .findFirst();
        }
        //to do check for fail
        return ingredientToIngredientCommand.convert(savedIngredientOptional.get());
    }


}
