package nikhil.springframework.recipeapp.controller;

import lombok.extern.slf4j.Slf4j;
import nikhil.springframework.recipeapp.commands.RecipeCommand;
import nikhil.springframework.recipeapp.services.ImageService;
import nikhil.springframework.recipeapp.services.RecipeService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;


@Slf4j
@Controller
public class ImageController {
    private final ImageService imageService;
    private final RecipeService recipeService;

    public ImageController(ImageService imageService, RecipeService recipeService) {
        this.imageService = imageService;
        this.recipeService = recipeService;
    }

    @GetMapping("recipe/{id}/image")
    public String showUploadForm(@PathVariable String id, Model model)
    {
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));

        return "recipe/imageUploadForm";
    }

    @PostMapping("recipe/{id}/image")
    public String handleImagePost(@PathVariable String id,
                                  @RequestParam("imagefile") MultipartFile file)
    {
          imageService.saveImageFile(Long.valueOf(id), file);

          return "redirect:/recipe/" + id + "/show";
    }

    @GetMapping("recipe/{recipeId}/recipeimage")
    public void renderImageFromDB(@PathVariable String recipeId, HttpServletResponse response) throws IOException
    {
        log.debug("Rendering image from the db");
        RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(recipeId));

        byte[] byteArray = new byte[recipeCommand.getImage().length];

        int i = 0;

        for(Byte wrappedByte : recipeCommand.getImage()){
            byteArray[i++] = wrappedByte;
        }

        response.setContentType("image/jpeg");
        InputStream is = new ByteArrayInputStream(byteArray);

        //convert the image to a byte array and copy the Input stream to the servlet response which MVC can later use
        IOUtils.copy(is,response.getOutputStream());
    }
}
