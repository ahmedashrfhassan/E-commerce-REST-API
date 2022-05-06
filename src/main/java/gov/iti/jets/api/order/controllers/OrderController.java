package gov.iti.jets.api.order.controllers;

import gov.iti.jets.api.exceptions.MyForbiddenException;
import gov.iti.jets.api.order.models.CartItemModel;
import gov.iti.jets.api.order.models.OrderModel;
import gov.iti.jets.persistance.entity.Order;
import gov.iti.jets.service.OrderService;
import gov.iti.jets.service.impls.OrderServiceImpl;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.List;

@Path("orders")
public class OrderController {
    @Context
    SecurityContext securityContext;
    @GET
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public Response getAllOrders() {
        boolean authorized = securityContext.isUserInRole("ADMIN");
        if(!authorized) throw new MyForbiddenException("you are not allowed to visit this page :)");
        OrderService orderService = new OrderServiceImpl();
        List<OrderModel> orderModels = orderService.getAllOrders();
        GenericEntity<List<OrderModel>> entity = new GenericEntity<List<OrderModel>>(orderModels){};
        return Response.ok().entity(entity).build();
    }

    @GET
    @Path("/{uid}")
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public Response getUserOrders(@PathParam("uid") int id) {
        boolean authorized = securityContext.isUserInRole("ADMIN");
        if(!authorized) throw new MyForbiddenException("you are not allowed to visit this page :)");
        OrderService orderService = new OrderServiceImpl();
        List<OrderModel> orderModels = orderService.viewUserOrders(id);
        GenericEntity<List<OrderModel>> entity = new GenericEntity<List<OrderModel>>(orderModels){};
        return Response.ok().entity(entity).build();
    }

    @POST
    @Path("/{uid}")
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public String addUserOrder(List<CartItemModel> cartItemModels, @PathParam("uid") int id) {
        OrderService orderService = new OrderServiceImpl();
        Order order = orderService.createOrder(cartItemModels,id);
        return order != null ? "order created":"order wasn't created";
    }
}
