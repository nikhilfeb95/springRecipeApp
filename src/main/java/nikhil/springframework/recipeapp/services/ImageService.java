package nikhil.springframework.recipeapp.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface ImageService {
    void saveImageFile(Long id, MultipartFile file);
}
