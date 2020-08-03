package nikhil.springframework.recipeapp.services;

import lombok.extern.slf4j.Slf4j;
import nikhil.springframework.recipeapp.commands.RecipeCommand;
import nikhil.springframework.recipeapp.converters.RecipeCommandToRecipe;
import nikhil.springframework.recipeapp.converters.RecipeToRecipeCommand;
import nikhil.springframework.recipeapp.domain.Recipe;
import nikhil.springframework.recipeapp.exceptions.NotFoundException;
import nikhil.springframework.recipeapp.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService{

    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Set<Recipe> getRecipes() {
        log.debug("In the recipe Service");
        Set<Recipe> recipeSet = new HashSet<>();
        //::(double colon) operator acts as a lambda-> refers the class method directly
        recipeRepository.findAll().iterator().forEachRemaining(recipeSet::add);
        return recipeSet;
    }

    @Override
    public Recipe findById(Long l) {
        Optional<Recipe> optionalRecipe= recipeRepository.findById(l);

        if(!optionalRecipe.isPresent())
            throw new NotFoundException("Recipe not Found with id:" + l);
        return optionalRecipe.get();
    }

    @Override
    public RecipeCommand findCommandById(Long id) {
        Recipe detached = recipeRepository.findById(id).orElse(null);
        if(detached == null)
            throw new RuntimeException("Recipe with Id doesn't exist");
        return recipeToRecipeCommand.convert(detached);
    }

    @Override
    public RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand) {
        Recipe detachedRecipe = recipeCommandToRecipe.convert(recipeCommand);

        Recipe savedRecipe = recipeRepository.save(detachedRecipe);
        log.debug("Saved recipe id :"+ savedRecipe.getId());
        return recipeToRecipeCommand.convert(savedRecipe);
    }

    @Override
    public void deleteById(Long id) {
        recipeRepository.deleteById(id);
    }
}
