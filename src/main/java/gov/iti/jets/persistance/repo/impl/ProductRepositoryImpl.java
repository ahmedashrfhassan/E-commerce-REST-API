package gov.iti.jets.persistance.repo.impl;

import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.math.BigDecimal;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

import gov.iti.jets.persistance.entity.Product;
import gov.iti.jets.persistance.entity.enums.ProductState;
import gov.iti.jets.persistance.repo.ProductRepository;

public class ProductRepositoryImpl extends RepositoryImpl<Product, Integer> implements ProductRepository {
    private static final ProductRepositoryImpl INSTANCE = new ProductRepositoryImpl();

    private ProductRepositoryImpl() {

    }

    public static ProductRepositoryImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Product> getLast10() {
        return (List<Product>) entityManager.createNamedQuery("newArrivals").setMaxResults(10).getResultList();
    }

    @Override
    public List<Product> findAllByCategoryId(int id) {

        TypedQuery<Product> query = entityManager.createNamedQuery("findAllByCategoryId", Product.class);
        query.setParameter("category_id", id);
        return query.getResultList();
    }

    @Override
    public List<Product> getSinglePageContent(int pageNumber, int recordsPerPage, int categoryId) {
        TypedQuery<Product> query = entityManager.createNamedQuery("findAllByCategoryId",Product.class);
        query.setParameter("category_id", categoryId);
        query.setFirstResult((pageNumber - 1) * recordsPerPage);
        query.setMaxResults(recordsPerPage);
        return query.getResultList();
    }

    @Override
    public List<Product> findProductByName(String productName) {
        TypedQuery<Product> query = entityManager.createNamedQuery("findProductByName", Product.class);
        query.setParameter("product_name", productName);
        return query.getResultList();
    }

    @Override
    public List<Product> findProductByPrice(BigDecimal productPrice) {
        TypedQuery<Product> query = entityManager.createNamedQuery("findProductByPrice", Product.class);
        query.setParameter("product_price", productPrice);
        return query.getResultList();

    }

    @Override
    public List<Product> findProductByPriceAndCategoryId(BigDecimal productPrice, int id) {
        TypedQuery<Product> query = entityManager.createNamedQuery("findProductByPriceAndCategoryId",
                Product.class);
        query.setParameter("product_price", productPrice);
        query.setParameter("category_id", id);
        return query.getResultList();
    }

    /*@Override
    public boolean delete(Product entity) {
        entity.setState(ProductState.ARCHIVED);
        entityManager.getTransaction().begin();
        entity = entityManager.merge(entity);
        entityManager.getTransaction().commit();
        return true;
    }*/

    // @Override
    // public int findAllProductsNumber() {
    // Query queryTotal = entityManager.createQuery// ("Select count(p.id) from
    // Product p");
    // long countResult = (long)queryTotal.getSingleResult();
    // return (int)countResult;
    // }

    // @Override
    // public List<Product> getSinglePageProducts(int pageNumber, int
    // recordsPerPage) {
    // Query query = entityManager.createQuery("FROM Product");
    // query.setFirstResult((pageNumber-1) * recordsPerPage);
    // query.setMaxResults(recordsPerPage);
    // List <Product> productBeansPerSinglePage = query.getResultList();
    // return productBeansPerSinglePage;
    // }

    public List<Product> getFilteredProducts(int pageNumber, int recordsPerPage, List<String> categoriesIds) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = cb.createQuery(Product.class);
        Root<Product> root = criteriaQuery.from(Product.class);
        criteriaQuery.select(root);
        List<Predicate> predicatesList = new ArrayList<>();
        for (String categoryId : categoriesIds) {
            Predicate predicate = cb.equal(root.get("category").get("categoryId"), categoryId);
            predicatesList.add(predicate);
        }
        Predicate[] finalPredicates = new Predicate[predicatesList.size()];
        Predicate predicate = cb.or(predicatesList.toArray(finalPredicates));
        criteriaQuery.where(predicate);
        criteriaQuery.orderBy(cb.desc(root.get("creationDate")));

        List<Product> result = entityManager
                .createQuery(criteriaQuery)
                .setMaxResults(recordsPerPage)
                .setFirstResult((pageNumber - 1) * recordsPerPage)
                .getResultList();

        return result;
    }

    public long countProductsOfCertainCategories(List<String> categoriesIds) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
        Root<Product> root = criteriaQuery.from(Product.class);
        criteriaQuery.select(cb.count(root));
        List<Predicate> predicatesList = new ArrayList<>();
        for (String categoryId : categoriesIds) {
            Predicate predicate = cb.equal(root.get("category").get("categoryId"), categoryId);
            predicatesList.add(predicate);
        }
        Predicate[] finalPredicates = new Predicate[predicatesList.size()];
        Predicate predicate = cb.or(predicatesList.toArray(finalPredicates));
        criteriaQuery.where(predicate);

        long result = entityManager.createQuery(criteriaQuery).getSingleResult();
        return result;
    }

    @Override
    public List<Product> relatedProducts(int id) {
        TypedQuery<Product> query = entityManager.createNamedQuery("relatedProducts", Product.class);
        query.setParameter("category_id", id);
        return query.setMaxResults(4).getResultList();
    }
}
