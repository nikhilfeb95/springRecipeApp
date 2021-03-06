package nikhil.springframework.recipeapp.services;

import lombok.extern.slf4j.Slf4j;
import nikhil.springframework.recipeapp.domain.Recipe;
import nikhil.springframework.recipeapp.repositories.RecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService{

    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void saveImageFile(Long id, MultipartFile file) {
        try{
            //we need to store the image as a bytearray
            Optional<Recipe> optionalRecipe = recipeRepository.findById(id);
            if(!optionalRecipe.isPresent())
            {
                log.error("Value with id not present in the repository");
            }
            Recipe recipe = optionalRecipe.get();
            Byte[] byteObjects = new Byte[file.getBytes().length];
            int i = 0;
            for(byte b : file.getBytes()) {
                byteObjects[i++] = b;
            }

            recipe.setImage(byteObjects);
            recipeRepository.save(recipe);
        }
        catch (IOException e){
            log.error("Error while uploading an image");
            e.printStackTrace();
        }
    }
}
