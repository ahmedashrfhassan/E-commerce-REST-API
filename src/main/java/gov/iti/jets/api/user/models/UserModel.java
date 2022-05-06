package gov.iti.jets.api.user.models;

import gov.iti.jets.persistance.entity.Order;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.ws.rs.core.Link;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "user")
public class UserModel {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    @JsonbTransient
    private List<Order> orders = new ArrayList<>();


    @XmlJavaTypeAdapter(Link.JaxbAdapter.class)
    private List<Link> linksList = new ArrayList<>();

    public UserModel() {
    }

    public UserModel(int id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Link> getLinksList() {
        return linksList;
    }

    public void setLinksList(List<Link> linksList) {
        this.linksList = linksList;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", linksList=" + linksList +
                '}';
    }
}
