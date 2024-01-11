package net.jouini.ws;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {
	private static final String INSERT_PRODUIT_SQL = "INSERT INTO `product` (`id`, `name`, `price`, `idCategory`) VALUES (NULL, ?, ?, ?)";
	private static final String SELECT_PRODUIT_BY_ID = "SELECT `id`,`name`,`price`,`idCategory` FROM `product` where id =?";
	private static final String SELECT_ALL_PRODUITS = "SELECT `id`,`name`,`price`,`idCategory` FROM `product`";
	private static final String DELETE_PRODUITS_SQL = "delete from `product` where id = ?;";
	private static final String UPDATE_PRODUITS_SQL = "UPDATE `product` SET `name` = ?, `price` = ? , `idCategory` = ? WHERE `product`.`id` = ?;";
	Connection con = Singleton.seConnecter();	
	
	 public List<Product> listAll() {
	        List<Product> productList = new ArrayList<>();
	        try {
	        		PreparedStatement preparedStatement = con.prepareStatement(SELECT_ALL_PRODUITS); 
	            	ResultSet resultSet = preparedStatement.executeQuery();
	            	while (resultSet.next()) {
	            		int id = resultSet.getInt("id");
	            		String name = resultSet.getString("name");
	            		float prix = resultSet.getFloat("price");
	            		Product product = new Product(id, name, prix);
	            		productList.add(product);
	            	}

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return productList;
	    }

	    public int add(Product p) {
	        try {
	        	PreparedStatement preparedStatement = con.prepareStatement(INSERT_PRODUIT_SQL, PreparedStatement.RETURN_GENERATED_KEYS) ;

	            preparedStatement.setString(1, p.getName());
	            preparedStatement.setDouble(2, p.getPrice());
	            // Assuming you have a method in the Product class to get idCategorie
	            preparedStatement.setInt(3, p.getCat());

	            int affectedRows = preparedStatement.executeUpdate();

	            if (affectedRows == 0) {
	                throw new SQLException("Creating product failed, no rows affected.");
	            }

	           ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
	                if (generatedKeys.next()) {
	                    return generatedKeys.getInt(1);
	                } else {
	                    throw new SQLException("Creating product failed, no ID obtained.");
	                }
	            

	        } catch (SQLException e) {
	            e.printStackTrace();
	            return -1; // Return a value indicating failure
	        }
	    }

	    public Product get(int id) {
	        try {
	             PreparedStatement preparedStatement = con.prepareStatement(SELECT_PRODUIT_BY_ID);

	            preparedStatement.setInt(1, id);
	            ResultSet resultSet = preparedStatement.executeQuery();

	            if (resultSet.next()) {
	                String name = resultSet.getString("name");
	                float price= resultSet.getFloat("price");
	                // Assuming  you have a constructor in the Product class
	                return new Product(id, name, price);
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return null; // Return null if no product with the specified id is found
	    }

	    public void update(Product p) {
	        try {
	             PreparedStatement preparedStatement = con.prepareStatement(UPDATE_PRODUITS_SQL) ;

	            preparedStatement.setString(1, p.getName());
	            preparedStatement.setFloat(2, p.getPrice());
	            // Assuming you have a method in the Product class to get idCategorie
	            preparedStatement.setInt(3, p.getCat());
	            preparedStatement.setInt(4, p.getId());

	            preparedStatement.executeUpdate();

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    public void delete(Product p) {
	        try {
	             PreparedStatement preparedStatement = con.prepareStatement(DELETE_PRODUITS_SQL);

	            preparedStatement.setInt(1, p.getId());
	            preparedStatement.executeUpdate();

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    public List<Product> listProductsByCategory(int categoryId) {
	        List<Product> productList = new ArrayList<>();

	        try {
	            String query = "SELECT `id`, `name`, `price` FROM `product` WHERE `idCategory` = ?";
	            try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
	                preparedStatement.setInt(1, categoryId);
	                ResultSet resultSet = preparedStatement.executeQuery();

	                while (resultSet.next()) {
	                    int id = resultSet.getInt("id");
	                    String description = resultSet.getString("name");
	                    float prix = resultSet.getFloat("price");

	                    // Assuming you have a constructor in the Product class
	                    Product product = new Product(id, description, prix);
	                    productList.add(product);
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace(); // Consider logging the exception
	        }

	        return productList;
	    }
	    
	    
	}

