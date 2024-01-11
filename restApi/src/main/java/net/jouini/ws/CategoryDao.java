package net.jouini.ws;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDao {
    private static final String INSERT_CATEGORY_SQL = "INSERT INTO `category` (`id`, `label`) VALUES (?, ?)";
    private static final String SELECT_CATEGORY_BY_ID = "SELECT `id`, `label` FROM `category` WHERE id = ?";
    private static final String SELECT_ALL_CATEGORIES = "SELECT `id`, `label` FROM `category`";
    private static final String UPDATE_CATEGORY_SQL = "UPDATE `category` SET `label` = ? WHERE `category`.`id` = ?";
    private static final String DELETE_CATEGORY_SQL = "DELETE FROM `category` WHERE id = ?";

    Connection con = Singleton.seConnecter();

    public int add(Category category) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement(INSERT_CATEGORY_SQL, PreparedStatement.RETURN_GENERATED_KEYS);

            List<Category> list = listAllCategories();
            preparedStatement.setInt(1, list.size()+1);
            preparedStatement.setString(2, category.getLabel());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
            	 return -1;
            	
            }
            return 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return -1; // Return a value indicating failure
        }
    }

    public Category getCategoryById(int categoryId) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement(SELECT_CATEGORY_BY_ID);

            preparedStatement.setInt(1, categoryId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String label = resultSet.getString("label");
                return new Category(categoryId, label);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // Return null if no category with the specified id is found
    }

    public List<Category> listAllCategories() {
        List<Category> categoryList = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = con.prepareStatement(SELECT_ALL_CATEGORIES);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String label = resultSet.getString("label");
                Category category = new Category(id, label);
                categoryList.add(category);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categoryList;
    }

    public void updateCategory(Category category) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement(UPDATE_CATEGORY_SQL);

            preparedStatement.setString(1, category.getLabel());
            preparedStatement.setInt(2, category.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCategory(int categoryId) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement(DELETE_CATEGORY_SQL);

            preparedStatement.setInt(1, categoryId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
