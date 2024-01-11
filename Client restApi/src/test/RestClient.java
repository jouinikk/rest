package test;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import entites.Category;
import entites.Product;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Scanner;

public class RestClient {
    private static final String BASE_URL = "http://localhost:8080/restApi/rest";
    private static final Scanner scanner = new Scanner(System.in);
    private ArrayList<Category> categories= new ArrayList();
    private ArrayList<Product> products= new ArrayList();
    public static void main(String[] args) {
    	
        while (true) {
            printMenu();
            int choice = getUserChoice();

            switch (choice) {
                case 1:
                    listAllProducts();
                    break;
                case 2:
                    listProductsByCategory();
                    break;
                case 3:
                    addProduct();
                    break;
                case 4:
                    updateProduct();
                    break;
                case 5:
                    deleteProduct();
                    break;
                case 6:
                    listAllCategories();
                    break;
                case 7:
                    addCategory();
                    break;
                case 0:
                    System.out.println("Exiting program. Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            scanner.next();
        }
    }

    private static void printMenu() {
        System.out.println("\nMenu:");
        System.out.println("1. List All Products");
        System.out.println("2. List Products by Category");
        System.out.println("3. Add Product");
        System.out.println("4. Update Product");
        System.out.println("5. Delete Product");
        System.out.println("6. List All Categories");
        System.out.println("7. Add Category");
        System.out.println("0. Exit");
    }

    private static int getUserChoice() {
        System.out.print("Enter your choice: ");
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void listAllProducts() {
        Client client = Client.create();
        URI uri = UriBuilder.fromUri(BASE_URL).path("/products").build();

        WebResource webResource = client.resource(uri);
        ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        
        handleResponse(response);
    }

    private static void listProductsByCategory() {
        System.out.print("Enter Category ID: ");
        int categoryId = Integer.parseInt(scanner.nextLine());

        Client client = Client.create();
        URI uri = UriBuilder.fromUri(BASE_URL).path("/products/category/" + categoryId).build();

        WebResource webResource = client.resource(uri);
        ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);

        handleResponse(response);
    }

    private static void addProduct() {
        System.out.println("Enter product name: ");
        String name = scanner.nextLine();

        System.out.println("Enter product price: ");
        float price = Float.parseFloat(scanner.nextLine());

        System.out.println("Select category's id: ");
        listAllCategories();
        int categoryId = Integer.parseInt(scanner.nextLine());

        try {
			Process process = Runtime.getRuntime().exec("curl -X POST -H \"Content-Type: application/json\" -d \"{\\\"name\\\":\\\""+name+"\\\",\\\"price\\\":"+price+",\\\"cat\\\":"+categoryId+"}\" http://localhost:8080/restApi/rest/products");
            System.out.println("success");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error adding product");
        }
    }


    private static void updateProduct() {

        System.out.println("Enter product's id: ");
        String id = scanner.nextLine();
    	
    	System.out.println("Enter product name: ");
        String name = scanner.nextLine();
        
        System.out.println("Enter product price: ");
        float price = Float.parseFloat(scanner.nextLine());
        
        System.out.println("Select category's id: ");
        listAllCategories();
        int categoryId = Integer.parseInt(scanner.nextLine());
        
        try{
			Process process = Runtime.getRuntime().exec("curl -X PUT -H \"Content-Type: application/json\" -d \"{\\\"id\\\":\\\""+id+"\\\",\\\"name\\\":\\\""+name+"\\\",\\\"price\\\":"+price+",\\\"cat\\\":"+categoryId+"}\" http://localhost:8080/restApi/rest/products/"+id);
            System.out.println("success");
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error adding product");
        }
    }

    private static void deleteProduct() {
        System.out.print("Enter Product ID to delete: ");
        String productId = scanner.nextLine();
        
        try {
			Runtime.getRuntime().exec("curl -X DELETE http://localhost:8080/restApi/rest/products/"+productId);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    private static void listAllCategories() {
        Client client = Client.create();
        URI uri = UriBuilder.fromUri(BASE_URL).path("/categories").build();

        WebResource webResource = client.resource(uri);
        ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);

        handleResponse(response);
    }

    private static void addCategory() {
    	System.out.println("category name: ");
    	String label = scanner.nextLine();
    	
    	try {
			Process process = Runtime.getRuntime().exec("curl -X POST -H \"Content-Type: application/json\" -d \"{\\\"label\\\":\\\""+label+"\\\"}\" http://localhost:8080/restApi/rest/categories");
            System.out.println("success");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error adding product");
        }
    }


    private static void handleResponse(ClientResponse response) {
        if (response.getStatus() == 200) {
            String responseBody = response.getEntity(String.class);
            System.out.println(responseBody);
        } else {
            System.out.println("Error: " + response.getStatus());
            String errorMessage = response.getEntity(String.class);
            System.out.println(errorMessage);
        }
    }
}