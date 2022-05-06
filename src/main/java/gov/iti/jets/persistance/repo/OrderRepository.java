package gov.iti.jets.persistance.repo;

import gov.iti.jets.persistance.entity.Order;

import java.util.List;

public interface OrderRepository extends Repository<Order, Integer> {

    List<Order> findUserOrders(int id);
}
