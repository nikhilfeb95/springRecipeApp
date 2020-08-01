package nikhil.springframework.recipeapp.services;

import nikhil.springframework.recipeapp.commands.IngredientCommand;
import nikhil.springframework.recipeapp.commands.UnitOfMeasureCommand;
import nikhil.springframework.recipeapp.converters.IngredientCommandToIngredient;
import nikhil.springframework.recipeapp.converters.IngredientToIngredientCommand;
import nikhil.springframework.recipeapp.domain.Ingredient;
import nikhil.springframework.recipeapp.domain.Recipe;
import nikhil.springframework.recipeapp.domain.UnitOfMeasure;
import nikhil.springframework.recipeapp.repositories.RecipeRepository;
import nikhil.springframework.recipeapp.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IngredientServiceImplTest {

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Mock
    IngredientToIngredientCommand ingredientToIngredientCommand;

    @Mock
    IngredientCommandToIngredient ingredientCommandToIngredient;

    @InjectMocks
    IngredientServiceImpl ingredientService;

    Recipe recipe;
    Ingredient ingredient;
    @BeforeEach
    public void setUp()
    {
        recipe = new Recipe();
        recipe.setId(1L);
        ingredient = new Ingredient();
        ingredient.setId(2L);
    }
    @Test
    void findByRecipeIdAndIngredientId() throws Exception{
        //prep for test
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(2L);
        ingredientCommand.setRecipeId(1L);
        recipe.getIngredients().add(ingredient);

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
        when(ingredientToIngredientCommand.convert(any())).thenReturn(ingredientCommand);
        //then

         IngredientCommand returnedCommand = ingredientService.findByRecipeIdAndIngredientId(1L,2L);

         assertEquals(Long.valueOf(2L), returnedCommand.getId());
         assertEquals(Long.valueOf(1L), returnedCommand.getRecipeId());
         verify(recipeRepository, times(1)).findById(anyLong());
    }

    @Test
    void testSaveIngredientCommand()
    {
        //Prep
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(2L);
        ingredientCommand.setRecipeId(1L);
        recipe.getIngredients().add(ingredient);

        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(1L);
        //Adding unit of measure command
        UnitOfMeasureCommand uomCommand = new UnitOfMeasureCommand();
        uomCommand.setId(1L);
        ingredientCommand.setUnitOfMeasure(uomCommand);

        //Mocks
        when(recipeRepository.save(any())).thenReturn(recipe);
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
        when(ingredientToIngredientCommand.convert(any())).thenReturn(ingredientCommand);
        when(unitOfMeasureRepository.findById(anyLong())).thenReturn(Optional.of(uom));

        //then
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(ingredientCommand);

        assertEquals(2L, savedCommand.getId());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
        verify(recipeRepository,times(1)).findById(anyLong());
    }

    @Test
    void testDeleteById()
    {
        recipe.getIngredients().add(ingredient);

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
        when(recipeRepository.save(any())).thenReturn(recipe);

        //when
        ingredientService.DeleteById(1L, 2L);

        //then
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any());
    }
}