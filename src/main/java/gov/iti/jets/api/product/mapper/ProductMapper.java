package gov.iti.jets.api.product.mapper;

import gov.iti.jets.api.product.model.ProductModel;
import gov.iti.jets.persistance.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE= Mappers.getMapper(ProductMapper.class);
    ProductModel entitiesToModels(Product product);
    Product modelToEntity(ProductModel productModel);
    List<ProductModel> entitiesToModels(List<Product>products);
}
