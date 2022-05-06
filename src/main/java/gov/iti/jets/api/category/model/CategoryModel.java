package gov.iti.jets.api.category.model;

import gov.iti.jets.api.product.model.ProductModel;
import gov.iti.jets.persistance.entity.Product;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.ws.rs.core.Link;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CategoryModel {

    private Integer id;
    private String name;
    private List<Link>links;
}
