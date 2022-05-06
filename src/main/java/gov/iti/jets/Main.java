package gov.iti.jets;

import gov.iti.jets.api.order.models.OrderModel;
import gov.iti.jets.api.user.models.UserModel;
import gov.iti.jets.persistance.repo.Connector;
import gov.iti.jets.service.impls.OrderServiceImpl;
import gov.iti.jets.service.impls.UserServiceImpl;
import jakarta.persistence.EntityManager;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManager entityManager = Connector.getInstance().getEntityManager();

        UserModel userModel = UserServiceImpl.getInstance().getUserById(7);
        System.out.println(userModel);



    }

}
