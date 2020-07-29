package nikhil.springframework.recipeapp.services;

import lombok.extern.slf4j.Slf4j;
import nikhil.springframework.recipeapp.domain.Recipe;
import nikhil.springframework.recipeapp.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService{

    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
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
            throw new RuntimeException("Recipe not Found");
        return optionalRecipe.get();
    }
}
