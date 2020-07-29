package nikhil.springframework.recipeapp.converters;

import com.sun.istack.Nullable;
import lombok.Synchronized;
import nikhil.springframework.recipeapp.commands.CategoryCommand;
import nikhil.springframework.recipeapp.domain.Category;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

//Convert the domain object to the ones into binded form --> which is used by the SpringMVC
@Component
public class CategoryToCategoryCommand implements Converter<Category, CategoryCommand> {

    @Synchronized
    @Nullable
    @Override
    public CategoryCommand convert(Category source) {
        if(source == null)
            return null;

        CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setId(source.getId());
        categoryCommand.setDescription(source.getDescription());

        return categoryCommand;
    }
}
