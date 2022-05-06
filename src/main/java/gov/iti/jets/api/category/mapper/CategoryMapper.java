package gov.iti.jets.api.category.mapper;

import gov.iti.jets.api.category.model.CategoryModel;
import gov.iti.jets.persistance.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryModel entityToModel(Category category);

    List<CategoryModel> entitiesToModels(List<Category> categories);

    Category modelToEntity(CategoryModel categoryModel);

    List<Category> modelsToEntities(List<CategoryModel> categoryModels);
}
