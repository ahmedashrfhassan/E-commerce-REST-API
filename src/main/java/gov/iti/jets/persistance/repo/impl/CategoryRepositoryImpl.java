package gov.iti.jets.persistance.repo.impl;

import gov.iti.jets.persistance.entity.Category;
import gov.iti.jets.persistance.repo.CategoryRepository;

public class CategoryRepositoryImpl extends RepositoryImpl<Category, Integer> implements CategoryRepository {
    private static final CategoryRepositoryImpl INSTANCE = new CategoryRepositoryImpl();

    private CategoryRepositoryImpl() {

    }

    public static CategoryRepositoryImpl getInstance() {
        return INSTANCE;
    }

}
