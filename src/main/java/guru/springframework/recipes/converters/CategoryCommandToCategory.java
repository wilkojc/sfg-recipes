package guru.springframework.recipes.converters;

import com.sun.istack.Nullable;
import guru.springframework.recipes.commands.CategoryCommand;
import guru.springframework.recipes.domain.Category;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CategoryCommandToCategory implements Converter<CategoryCommand, Category> {

    @Override
    @Synchronized
    @Nullable
    public Category convert(CategoryCommand source) {

        if (source == null) {
            return null;
        }

        Category category = new Category();
        category.setId(source.getId());
        category.setDescription(source.getDescription());
        return category;
    }
}
