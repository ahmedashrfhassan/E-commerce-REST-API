package gov.iti.jets.service;

import gov.iti.jets.api.category.model.CategoryModel;
import gov.iti.jets.persistance.entity.Category;

import java.util.List;

public interface CategoryService {
    CategoryModel findById(int id);
    List<CategoryModel>findAll();

    boolean save(CategoryModel categoryModel);

    boolean delete(int id);

    boolean update();

}
