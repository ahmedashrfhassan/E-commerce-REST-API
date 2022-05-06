package gov.iti.jets.api.order.models;

import gov.iti.jets.persistance.entity.LineItem;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class OrderModel implements Serializable {
    private int userId;
    private String userName;
//    private String userName;
    List<LineItem> lineItemList;

    public OrderModel() {
    }

    public OrderModel(int userId, List<LineItem> lineItemList) {
        this.userId = userId;
        this.lineItemList = lineItemList;
    }
    public OrderModel(int userId,String userName, List<LineItem> lineItemList) {
        this.userName = userName;
        this.userId = userId;
        this.lineItemList = lineItemList;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<LineItem> getLineItemList() {
        return lineItemList;
    }

    public void setLineItemList(List<LineItem> lineItemList) {
        this.lineItemList = lineItemList;
    }
}
