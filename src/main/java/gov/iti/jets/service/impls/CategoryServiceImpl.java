package gov.iti.jets.service.impls;

import gov.iti.jets.api.category.mapper.CategoryMapper;
import gov.iti.jets.api.category.model.CategoryModel;
import gov.iti.jets.api.exceptions.MyNotFoundException;
import gov.iti.jets.persistance.entity.Category;
import gov.iti.jets.persistance.repo.CategoryRepository;
import gov.iti.jets.persistance.repo.impl.CategoryRepositoryImpl;
import gov.iti.jets.service.CategoryService;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {
    private static  final  CategoryServiceImpl INSTANCE=new CategoryServiceImpl();
    private CategoryRepository categoryRepository = CategoryRepositoryImpl.getInstance();
    private CategoryServiceImpl(){

    }
    public static CategoryServiceImpl getInstance(){
        return INSTANCE;
    }
    @Override
    public CategoryModel findById(int id) {

        return CategoryMapper.INSTANCE.entityToModel(getCategory(id));
    }
    private Category getCategory(int id){
        Category category=categoryRepository.findById(id);
        if (category == null) {
            throw new MyNotFoundException("category not found");
        }
        return category;
    }

    @Override
    public List<CategoryModel> findAll() {
        List<Category> categories = categoryRepository.findAll();
        return CategoryMapper.INSTANCE.entitiesToModels(categories);
    }

    @Override
    public boolean save(CategoryModel categoryModel) {
        Category category=CategoryMapper.INSTANCE.modelToEntity(categoryModel);
        category.setId(0);
        category=categoryRepository.save(category);
        return true;
    }

    @Override
    public boolean delete(int id) {
        Category category=getCategory(id);
        return categoryRepository.delete(category);
    }

    @Override
    public boolean update() {
        return false;
    }
}
