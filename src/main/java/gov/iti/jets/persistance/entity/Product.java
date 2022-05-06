package gov.iti.jets.persistance.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import gov.iti.jets.persistance.entity.enums.ProductState;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@NamedQueries(
        @NamedQuery(name = "findAllByCategoryId",query = "select  p from Product p inner join  p.categories c where c.id =: category_id")
)
public class Product {

    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String description;
    private BigDecimal price;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Category> categories;

    private int quantity;
    @Enumerated(EnumType.STRING)
    private ProductState state;

    public Product() {
    }

    public Product(String name, String description, BigDecimal price) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.categories = new ArrayList<>();
    }
}
