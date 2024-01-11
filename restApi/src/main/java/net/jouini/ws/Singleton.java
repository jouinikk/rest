package net.jouini.ws;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Singleton {
	 public static Connection con;
	    private static String pass="";
	    private static String uselog="root";
	    
	    public static Connection seConnecter (){
	        if( con == null ){
	            try {
	                Class.forName("com.mysql.jdbc.Driver");
	                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/rest", uselog, pass);
	                return con;
	            } catch (ClassNotFoundException ex) {
	                System.out.println("Driver Non Trouvée"+ex.getMessage());
	            } catch (SQLException ex) {
	            	System.out.println("SQL incorrect " +ex.getMessage());
	            }
	        }
	        return con;
	    }

	    public static String getUselog() {
	        return uselog;
	    }

	    public static void setCon(Connection con) {
	        Singleton.con = con;
	    }

	    public static void setPass(String pass) {
	    	Singleton.pass = pass;
	    }

	    public static void setUselog(String uselog) {
	    	Singleton.uselog = uselog;
	    }

	
		public static void main(String [] args){
			Singleton.setPass("");
			Singleton.setUselog("root");
			    
		    if(Singleton.seConnecter() != null){
		            System.out.println("Connection Etatblie");
		    }else{
		            System.out.println("Connection non Etablie");
		    } 
		}
}
