package gov.iti.jets.service;

import gov.iti.jets.api.order.models.OrderModel;
import gov.iti.jets.api.order.models.CartItemModel;
import gov.iti.jets.persistance.entity.Order;

import java.util.List;

public interface OrderService {
    public Order createOrder(List<CartItemModel> cartItemModels, int userId);
    List<OrderModel> viewUserOrders(int id);
    List<OrderModel> getAllOrders();

}
