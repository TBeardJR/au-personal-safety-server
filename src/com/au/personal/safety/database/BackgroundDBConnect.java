package com.au.personal.safety.database;

import java.net.URISyntaxException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import javax.servlet.*;

public class BackgroundDBConnect implements ServletContextListener  {
     
    private ScheduledThreadPoolExecutor executor = null;
    private Connection connection;
 
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        /*DatabaseConnectionSingleton databaseConnection = null;
		try {
			databaseConnection = DatabaseConnectionSingleton.getInstance();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        databaseConnection.openConnection();
        connection = databaseConnection.getConnection();
        
        ServletContext servletContext = sce.getServletContext();
        setServletContextAttributes(servletContext);   */ 
                
    }
 
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
			DatabaseConnectionSingleton.getInstance().closeConnection();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        executor.shutdown();
    }
    
    private void setServletContextAttributes(ServletContext servletContext) {
        servletContext.setAttribute("DBConnection", connection);
    }

}
