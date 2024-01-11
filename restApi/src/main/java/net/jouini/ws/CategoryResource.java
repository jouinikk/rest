package net.jouini.ws;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/categories")
public class CategoryResource {
    private CategoryDao dao = new CategoryDao();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Category> list() {
        return dao.listAllCategories();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public int add(Category category) {
        return dao.add(category);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Category get(@PathParam("id") int id) {
        return dao.getCategoryById(id);
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(@PathParam("id") int id, Category category) {
        Category existingCategory = dao.getCategoryById(id);
        if (existingCategory != null) {
            category.setId(id);
            dao.updateCategory(category);
        }
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") int id) {
        Category category = dao.getCategoryById(id);
        if (category != null) {
            dao.deleteCategory(id);
        }
    }
}
