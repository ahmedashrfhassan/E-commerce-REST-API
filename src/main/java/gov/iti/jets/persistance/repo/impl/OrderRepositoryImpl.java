package gov.iti.jets.persistance.repo.impl;

import gov.iti.jets.persistance.entity.Order;
import gov.iti.jets.persistance.entity.User;
import gov.iti.jets.persistance.repo.Connector;
import gov.iti.jets.persistance.repo.OrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class OrderRepositoryImpl extends RepositoryImpl<Order,Integer> implements OrderRepository {
    protected final EntityManager entityManager = Connector.getInstance().getEntityManager();

    @Override
    public List<Order> findUserOrders(int id) {
        Query query = entityManager.createQuery("select u.orders from " + User.class.getSimpleName() + " u where u.id = " + id);
        List<Order> orders = query.getResultList();
        return orders;    }
}
