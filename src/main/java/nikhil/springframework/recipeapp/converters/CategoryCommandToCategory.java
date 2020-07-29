package nikhil.springframework.recipeapp.converters;

import com.sun.istack.Nullable;
import lombok.Synchronized;
import nikhil.springframework.recipeapp.commands.CategoryCommand;
import nikhil.springframework.recipeapp.domain.Category;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

//Converter class converts the binded objects to the ones in domain
@Component
public class CategoryCommandToCategory implements Converter<CategoryCommand, Category> {
    @Synchronized
    @Nullable
    @Override
    public Category convert(CategoryCommand source) {
        if(source == null)
            return null;
        final Category category = new Category();
        category.setId(source.getId());
        category.setDescription(source.getDescription());
        return category;
    }
}
