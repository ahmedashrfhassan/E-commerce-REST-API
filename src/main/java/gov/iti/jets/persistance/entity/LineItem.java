package gov.iti.jets.persistance.entity;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
public class LineItem implements Serializable {

    @Id
    @GeneratedValue
    private int id;

    private String productName;

    private BigDecimal productPrice;

    private int quantity;

    @JsonbTransient
    @ManyToOne
    @JoinColumn( name = "order_id" )
    private Order order;

    public Order getOrder() {
        return order;
    }

    public LineItem() {
    }

    public LineItem( String productName, BigDecimal productPrice, int quantity ) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity( int quantity ) {
        this.quantity = quantity;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }
}