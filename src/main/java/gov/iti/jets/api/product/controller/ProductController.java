package gov.iti.jets.api.product.controller;


import gov.iti.jets.api.exceptions.MyForbiddenException;
import gov.iti.jets.service.ProductService;
import gov.iti.jets.service.impls.ProductServiceImpl;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.*;

import gov.iti.jets.api.product.model.ProductModel;


@Path("products")
public class ProductController {
    @Context
    private UriInfo uriInfo;

    ProductService productService=ProductServiceImpl.getInstance();

    @GET
    public Response getAllProducts(@DefaultValue("1") @QueryParam("page") int pageNumber,@DefaultValue("5") @QueryParam("rpp") int recordsPerPage) {
        UriBuilder selfUriBuilder = uriInfo.getAbsolutePathBuilder().queryParam("page", pageNumber).queryParam("rpp", recordsPerPage);
        UriBuilder nextUriBuilder = uriInfo.getAbsolutePathBuilder().queryParam("page", pageNumber + 1).queryParam("rpp", recordsPerPage);

        Link next = Link.fromUri(nextUriBuilder.build()).rel("next").build();
        Link self = Link.fromUri(selfUriBuilder.build()).rel("self").build();

            List<ProductModel> products = productService.findAll(pageNumber, recordsPerPage);
            return Response.ok().links(next, self).entity(products).build();

    }

    @GET
    @Path("/{uid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProduct(@PathParam("uid") int id) {
        ProductModel productModel = productService.findById(id);
        Link self = Link.fromUriBuilder(uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(id))
        ).rel("self").build();
        productModel.setLinks(List.of(self));
        return Response.ok().entity(productModel).build();
    }

    @GET
    @Path("/{uid}/categories")
    public Response getProductCategories(@PathParam("uid") int id) {
        ProductModel productModel = productService.findById(id);
        UriBuilder selfUriBuilder = uriInfo.getAbsolutePathBuilder();
        Link self = Link.fromUri(selfUriBuilder.build()).rel("self").build();

        return Response.ok().links(self).entity(productModel.getCategories()).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addProduct(@Context SecurityContext sec, ProductModel productModel) {
        authorize(sec);
        if (productService.save(productModel)) {
            return Response.ok().entity("Added Successfully").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Couldn't add").build();
        }
    }

    @DELETE
    @Path("/{uid}")
    public Response archiveProduct(@Context SecurityContext sec, @PathParam("uid") int id) {
        authorize(sec);
        if (productService.delete(id)) {
            return Response.ok().entity("deleted Successfully").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Couldn't delete").build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putProduct(@Context SecurityContext sec, ProductModel productModel) {
        authorize(sec);
        if (productService.put(productModel)) {
            return Response.ok().entity("Put Successfully").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Couldn't put").build();
        }
    }


    private void authorize(SecurityContext sec) {
        if (sec.isSecure() && !sec.isUserInRole("CLERK")) {
            throw new MyForbiddenException("this resource is accessed via CLERK");
        }
    }
}
