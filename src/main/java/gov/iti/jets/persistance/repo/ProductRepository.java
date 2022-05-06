package gov.iti.jets.persistance.repo;



import java.math.BigDecimal;
import java.util.List;

import gov.iti.jets.persistance.entity.Product;

public interface ProductRepository extends Repository<Product,Integer> {

    List<Product> getLast10();
    List<Product> findAllByCategoryId(int id);
    List<Product> getSinglePageContent(int pageNumber, int recordsPerPage,int categoryId);
//    List<Product> getSinglePageProducts(int PageNumber , int recordsPerPage);
    List<Product> findProductByName(String productName);
    List<Product> findProductByPrice(BigDecimal productPrice);
    List<Product> findProductByPriceAndCategoryId(BigDecimal productPrice ,int id);
//    int findAllProductsNumber();
    List<Product> getFilteredProducts(int pageNumber, int recordsPerPage, List<String> categoriesIds);
    long countProductsOfCertainCategories(List<String> categoriesIds);
    List<Product> relatedProducts(int id);

//    relatedProducts

}
