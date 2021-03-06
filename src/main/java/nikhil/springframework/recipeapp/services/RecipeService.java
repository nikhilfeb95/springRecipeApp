package nikhil.springframework.recipeapp.services;

import nikhil.springframework.recipeapp.commands.RecipeCommand;
import nikhil.springframework.recipeapp.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecipes();

    Recipe findById(Long l);

    RecipeCommand findCommandById(Long id);

    RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand);

    void deleteById(Long id);
}
