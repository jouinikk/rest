package net.jouini.ws;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;

@Path("/products")
public class ProductResourse {
    private ProductDao dao = new ProductDao();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> list() {
        return dao.listAll();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public int add(Product product) {
        return dao.add(product);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Product get(@PathParam("id") int id) {
        return dao.get(id);
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(@PathParam("id") int id, Product product) {
        Product existingProduct = dao.get(id);
        if (existingProduct != null) {
            product.setId(id);
            dao.update(product);
        }
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") int id) {
        Product product = dao.get(id);
        if (product != null) {
            dao.delete(product);
        }
    }
    

    @GET
    @Path("/category/{categoryId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> listProductsByCategory(@PathParam("categoryId") int categoryId) {
        // Implement logic to get products by category from the DAO
        List<Product> products = dao.listProductsByCategory(categoryId);

        if (products.isEmpty()) {
            throw new WebApplicationException("No products found for category ID: " + categoryId, Response.Status.NOT_FOUND);
        }

        return products;
    }
}
