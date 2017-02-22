package com.au.personal.safety.database;

import java.net.URISyntaxException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import javax.servlet.*;

public class backgroundDBConnect implements ServletContextListener  {
	private static final int MAXIMUM_CONCURRENT = 1;
    private static final String INIT_PARAMETER = "backgroundTasks";
     
    private ScheduledThreadPoolExecutor executor = null;
    private Connection connection;
 
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        DatabaseConnectionSingleton databaseConnection = null;
		try {
			databaseConnection = DatabaseConnectionSingleton.getInstance();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        databaseConnection.openConnection();
        connection = databaseConnection.getConnection();
        
        ServletContext servletContext = sce.getServletContext();
        setServletContextAttributes(servletContext);    
                
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
        Map<String, String> gamertagWatchList = new HashMap<>();
        servletContext.setAttribute("gamertagWatchList", gamertagWatchList);
        servletContext.setAttribute("DBConnection", connection);
    }

}
