package gov.iti.jets.api.user.controllers;

import java.util.ArrayList;

import gov.iti.jets.api.order.models.OrderModel;
import gov.iti.jets.api.order.models.CartItemModel;
import gov.iti.jets.persistance.entity.Order;
import gov.iti.jets.service.OrderService;
import gov.iti.jets.service.UserService;
import gov.iti.jets.service.impls.OrderServiceImpl;
import gov.iti.jets.service.impls.UserServiceImpl;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.*;

import gov.iti.jets.api.exceptions.*;
import gov.iti.jets.api.user.models.*;

@Path("users")
public class UserController {
    @Context SecurityContext securityContext;
    @GET
    public Response getAllUsers(@DefaultValue("1") @QueryParam("page") int pageNumber,@DefaultValue("5") @QueryParam("rpp") int recordsPerPage, @Context UriInfo uriInfo,@DefaultValue("customer") @QueryParam("role") String role) {
        boolean authorized = securityContext.isUserInRole("ADMIN");
        UserService userService = UserServiceImpl.getInstance();
        List<UserModel> users = new ArrayList<>();
        if (role.equalsIgnoreCase("customer")){
            users = userService.getCertainUsers(pageNumber,recordsPerPage, role);
        }else if(authorized){
            users = userService.getCertainUsers(pageNumber,recordsPerPage,role);
        }else throw new MyForbiddenException("Not authorized to view these roles, or may be there's a typo");

        setLinksForModels(users, pageNumber,recordsPerPage,uriInfo,role);

        GenericEntity<List<UserModel>> entity = new GenericEntity<List<UserModel>>(users){};
        return Response.ok().entity(entity).build();
    }

    private void setLinksForModels(List<UserModel> users, int pageNumber, int recordsPerPage, UriInfo uriInfo, String role) {
        for(UserModel userModel: users){
            Link self = Link.fromUriBuilder(uriInfo.getAbsolutePathBuilder()
                    .queryParam("pageNumber",pageNumber)
                    .queryParam("rpp",recordsPerPage)
                    .queryParam("role",role)).rel("self").build();
            Link nextPage = Link.fromUriBuilder(uriInfo.getAbsolutePathBuilder()
                    .queryParam("pageNumber",pageNumber+1)
                    .queryParam("rpp",recordsPerPage)
                    .queryParam("role",role)).rel("nextPage").build();
            userModel.setLinksList(Arrays.asList(new Link[] {self,nextPage}));
        }
    }

    @GET
    @Path("{uid}")
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public Response getUser(@PathParam("uid") int id,@Context UriInfo uriInfo) {
        UserModel userModel = UserServiceImpl.getInstance().getUserById(id);
        Link self = Link.fromUriBuilder(uriInfo.getAbsolutePathBuilder()).rel("self").build();
        userModel.setLinksList(Arrays.asList(self));
        return Response.ok().entity(userModel).build();
    }
    @GET
    @Path("/{uid}/orders")
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public Response getUserOrders(@PathParam("uid") int id) {
        OrderService orderService = new OrderServiceImpl();
        List<OrderModel> orderModels = orderService.viewUserOrders(id);
        GenericEntity<List<OrderModel>> entity = new GenericEntity<List<OrderModel>>(orderModels){};
        return Response.ok().entity(entity).build();
    }


    @POST
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public Response addUser(UserModel userModel) {
        UserServiceImpl.getInstance().insertUser(userModel);
        return Response.ok().entity("created successfully").build();
    }

    @POST
    @Path("/{uid}/orders")
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public String addUserOrder(List<CartItemModel> cartItemModels,@PathParam("uid") int id) {
        OrderService orderService = new OrderServiceImpl();
        Order order = orderService.createOrder(cartItemModels,id);
        return order != null ? "order created":"order wasn't created";
    }


    @PUT
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public Response updateUser(UserModel userModel) {
        UserServiceImpl.getInstance().updateUser(userModel);
        return Response.ok().entity("updated successfully").build();
    }

    @DELETE
    @Path("{uid}")
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public Response deleteUser(@PathParam("uid") int id) {
        UserServiceImpl.getInstance().deleteUser(id);
        return Response.ok().entity("deleted successfully").build();
    }

}
