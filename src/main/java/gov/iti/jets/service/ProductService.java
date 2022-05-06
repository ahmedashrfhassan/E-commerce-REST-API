package gov.iti.jets.service;

import gov.iti.jets.api.product.model.ProductModel;

import java.util.List;

public interface ProductService {
    List<ProductModel> findAll(int pageNumber, int recordsPerPage);

    List<ProductModel> findAllByCategoryId(int categoryId);

    List<ProductModel> findAllByCategoryId(int pageNumber, int recordPerPage, int categoryId);

    List<ProductModel> findAll();

    boolean save(ProductModel productModel);

    ProductModel findById(int id);

    boolean delete(int id);

    boolean put(ProductModel productModel);
}
