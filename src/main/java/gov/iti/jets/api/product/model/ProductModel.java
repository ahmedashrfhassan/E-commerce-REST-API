package gov.iti.jets.api.product.model;

import gov.iti.jets.api.category.model.CategoryModel;
import jakarta.ws.rs.core.Link;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ProductModel {

    private Integer id;
    private String name;
    private String description;
    private BigDecimal price;
    private List<CategoryModel> categories;
    private Integer quantity;
    private List<Link>links;


}
