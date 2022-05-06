package gov.iti.jets.service.impls;

import gov.iti.jets.api.category.model.CategoryModel;
import gov.iti.jets.api.exceptions.MyNotFoundException;
import gov.iti.jets.api.product.mapper.ProductMapper;
import gov.iti.jets.api.product.model.ProductModel;
import gov.iti.jets.persistance.entity.Category;
import gov.iti.jets.persistance.entity.Product;
import gov.iti.jets.persistance.entity.enums.ProductState;
import gov.iti.jets.persistance.repo.ProductRepository;
import gov.iti.jets.persistance.repo.impl.CategoryRepositoryImpl;
import gov.iti.jets.persistance.repo.impl.ProductRepositoryImpl;
import gov.iti.jets.service.ProductService;

import java.util.ArrayList;
import java.util.List;

public class ProductServiceImpl implements ProductService {
    private static final ProductServiceImpl INSTANCE = new ProductServiceImpl();

    private final ProductRepository productRepository = ProductRepositoryImpl.getInstance();

    private ProductServiceImpl() {
    }

    public static ProductServiceImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public List<ProductModel> findAll() {
        List<Product> products = productRepository.findAll();
        return ProductMapper.INSTANCE.entitiesToModels(products);
    }

    @Override
    public List<ProductModel> findAll(int pageNumber, int recordsPerPage) {
        List<Product> products = productRepository.getSinglePageContent(pageNumber, recordsPerPage);
        return ProductMapper.INSTANCE.entitiesToModels(products);
    }

    @Override
    public List<ProductModel> findAllByCategoryId(int categoryId) {
        List<Product> products = productRepository.findAllByCategoryId(categoryId);
        return ProductMapper.INSTANCE.entitiesToModels(products);
    }

    @Override
    public List<ProductModel> findAllByCategoryId(int pageNumber, int recordPerPage, int categoryId) {
        List<Product> products = productRepository.getSinglePageContent(pageNumber,recordPerPage,categoryId);
        return ProductMapper.INSTANCE.entitiesToModels(products);
    }



    @Override
    public boolean save(ProductModel productModel) {
        Product product = ProductMapper.INSTANCE.modelToEntity(productModel);
        product.setId(0);
        List<Category> categories = getCategories(productModel);
        product.setCategories(categories);
        product.setState(ProductState.NEW);
        productRepository.save(product);
        return true;
    }

    private List<Category> getCategories(ProductModel productModel) {
        List<Category> categories = new ArrayList<>();
        for (CategoryModel categoryModel : productModel.getCategories()) {
            if (categoryModel.getId() == null || categoryModel.getId() <= 0) {
                throw new MyNotFoundException("category not found");
            }
            Category category = CategoryRepositoryImpl.getInstance().findById(categoryModel.getId());
            if (category == null) {
                throw new MyNotFoundException("category not found");
            }
            categories.add(category);

        }
        return categories;
    }

    @Override
    public ProductModel findById(int id) {
        Product product = getProduct(id);
        return ProductMapper.INSTANCE.entitiesToModels(product);
    }

    @Override
    public boolean delete(int id) {
        Product product = getProduct(id);
        productRepository.delete(product);
        return true;
    }

    @Override
    public boolean put(ProductModel productModel) {
        if (productModel.getId() == null) {
            return save(productModel);
        } else {
            getProduct(productModel.getId());
            Product product = ProductMapper.INSTANCE.modelToEntity(productModel);
            product.setCategories(getCategories(productModel));
            productRepository.update(product);
            return true;
        }
    }

    private Product getProduct(int id) {
        if (id <= 0) {
            throw new MyNotFoundException("product not found");
        }
        Product product = productRepository.findById(id);
        if (product == null) {
            throw new MyNotFoundException("product not found");
        }
        return product;
    }

}
