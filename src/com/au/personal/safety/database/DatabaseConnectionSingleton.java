package com.au.personal.safety.database;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;
//import java.util.Map;
import java.sql.DriverManager;

public class DatabaseConnectionSingleton {
	private static DatabaseConnectionSingleton instance = null;
    
    private Connection connection;
    private String username;
    private String password;
    private String port;
    private String jdbUrl;
   
    
    private DatabaseConnectionSingleton() throws URISyntaxException {
        //Map<String, String> env = System.getenv(); 
        URI jdbUri = new URI(System.getenv("JAWSDB_URL"));
        username = jdbUri.getUserInfo().split(":")[0];
	    password = jdbUri.getUserInfo().split(":")[1];
	    port = String.valueOf(jdbUri.getPort());
	    jdbUrl = "jdbc:mysql://" + jdbUri.getHost() + ":" + port + jdbUri.getPath();
    }
    
    public static DatabaseConnectionSingleton getInstance() throws URISyntaxException {
        if(instance == null) {
            instance = new DatabaseConnectionSingleton();
        }
        return instance;
    }
    
    public Connection getConnection() {
        return connection;
    }
    
    public void openConnection() {
        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbUrl, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }
    
    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
