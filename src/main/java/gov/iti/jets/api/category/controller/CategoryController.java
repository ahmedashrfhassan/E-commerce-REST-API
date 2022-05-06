package gov.iti.jets.api.category.controller;

import gov.iti.jets.api.category.model.CategoryModel;
import gov.iti.jets.api.exceptions.MyForbiddenException;
import gov.iti.jets.api.product.model.ProductModel;
import gov.iti.jets.service.CategoryService;
import gov.iti.jets.service.ProductService;
import gov.iti.jets.service.impls.CategoryServiceImpl;
import gov.iti.jets.service.impls.ProductServiceImpl;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.List;

@Path("categories")
public class CategoryController {
    @Context
    private UriInfo uriInfo;

    CategoryService categoryService = CategoryServiceImpl.getInstance();
    ProductService productService = ProductServiceImpl.getInstance();

    @GET
    public Response getAllCategories() {
        UriBuilder selfUriBuilder = uriInfo.getAbsolutePathBuilder();
        UriBuilder productsUriBuilder = uriInfo.getAbsolutePathBuilder();

        Link self = Link.fromUri(selfUriBuilder.build()).rel("self").build();
        List<CategoryModel> categories = categoryService.findAll();
        categories.forEach(c -> c.setLinks(List.of(self, Link.fromUri(productsUriBuilder.path(c.getId().toString()).path("products").build()).rel("categoryProducts").build())));
        return Response.ok().entity(categories).build();

    }

    @GET
    @Path("/{uid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCategory(@PathParam("uid") int id) {
        UriBuilder selfUriBuilder = uriInfo.getAbsolutePathBuilder();
        Link self = Link.fromUri(selfUriBuilder.build()).rel("self").build();
        CategoryModel categoryModel = categoryService.findById(id);
        categoryModel.setLinks(List.of(self));
        return Response.ok().entity(categoryModel).build();
    }

    @GET
    @Path("/{uid}/products")
    public Response getProductCategories(@DefaultValue("1") @QueryParam("page") int pageNumber, @DefaultValue("20") @QueryParam("rpp") int recordsPerPage, @PathParam("uid") int id) {
        UriBuilder selfUriBuilder = uriInfo.getAbsolutePathBuilder().queryParam("page", pageNumber).queryParam("rpp", recordsPerPage);
        UriBuilder nextUriBuilder = uriInfo.getAbsolutePathBuilder().queryParam("page", pageNumber + 1).queryParam("rpp", recordsPerPage);
        Link next = Link.fromUri(nextUriBuilder.build()).rel("next").build();
        Link self = Link.fromUri(selfUriBuilder.build()).rel("self").build();
        List<ProductModel> products = productService.findAllByCategoryId(pageNumber, recordsPerPage, id);
        return Response.ok().links(next, self).entity(products).build();

    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addCategory(@Context SecurityContext sec, CategoryModel categoryModel) {
        authorize(sec);
        if (categoryService.save(categoryModel)) {
            return Response.ok().entity("Added Successfully").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Couldn't add").build();
        }
    }

    /*@DELETE
    @Path("/{uid}")
    public Response archiveCategory(@Context SecurityContext sec, @PathParam("uid") int id) {
        authorize(sec);
        if (categoryService.delete(id)) {
            return Response.ok().entity("deleted Successfully").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Couldn't delete").build();
        }
    }*/

    private void authorize(SecurityContext sec) {
        if (sec.isSecure() && !sec.isUserInRole("CLERK")) {
            throw new MyForbiddenException("this resource is accessed via CLERK");
        }
    }
}
