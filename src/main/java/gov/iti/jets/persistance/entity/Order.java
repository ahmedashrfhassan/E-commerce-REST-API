package gov.iti.jets.persistance.entity;

import jakarta.persistence.*;

import java.util.List;

import gov.iti.jets.persistance.entity.enums.*;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue
    private int id;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<LineItem> lineItems;

    @ManyToOne
    @JoinColumn(name = "maker_id")
    private User maker;

    public Order() {
    }

    public Order(List<LineItem> lineItems, User maker) {
        this.lineItems = lineItems;
        this.maker = maker;
    }

    public User getMaker() {
        return maker;
    }

    public int getId() {
        return id;
    }

    public List<LineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

    public void setMaker(User maker) {
        this.maker = maker;
    }

}
