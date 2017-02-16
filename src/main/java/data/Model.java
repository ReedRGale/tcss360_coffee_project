/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import objects.Shop;
import objects.User;

/**
 *
 * @author wlloyd
 */
public class Model {
    static final Logger logger = Logger.getLogger(Model.class.getName());
    private static Model instance;
    private Connection conn;
    
    public static Model singleton() throws Exception {
        if (instance == null) {
            instance = new Model();
        }
        return instance;
    }
    
    Model() throws Exception
    {
        Class.forName("org.postgresql.Driver");
        String dbUrl = System.getenv("JDBC_DATABASE_URL");
        if ((dbUrl == null) || (dbUrl.length() < 1))
            dbUrl = System.getProperties().getProperty("DBCONN");
        logger.log(Level.INFO, "dbUrl=" + dbUrl);  
        logger.log(Level.INFO, "attempting db connection");
        conn = DriverManager.getConnection(dbUrl);
        logger.log(Level.INFO, "db connection ok.");
    }
    
    private Connection getConnection()
    {
        return conn;
    }
    
    private Statement createStatement() throws SQLException
    {
        Connection conn = getConnection();
        if ((conn != null) && (!conn.isClosed()))
        {
            logger.log(Level.INFO, "attempting statement create");
            Statement st = conn.createStatement();
            logger.log(Level.INFO, "statement created");
            return st;
        }
        else
        {
            // Handle connection failure
        }
        return null;
    }
    
    private PreparedStatement createPreparedStatement(String sql) throws SQLException
    {
        Connection conn = getConnection();
        if ((conn != null) && (!conn.isClosed()))
        {
            logger.log(Level.INFO, "attempting statement create");
            PreparedStatement pst = conn.prepareStatement(sql);
            logger.log(Level.INFO, "prepared statement created");
            return pst;
        }
        else
        {
            // Handle connection failure
        }
        return null;
    }
    
    public User[] getUsers() throws SQLException
    {
        LinkedList<User> ll = new LinkedList<User>();
        String sqlQuery ="select * from users;";
        Statement st = createStatement();
        ResultSet rows = st.executeQuery(sqlQuery);
        while (rows.next())
        {
            logger.log(Level.INFO, "Reading row...");
            User usr = new User();
            usr.setUsername(rows.getString("username"));
            usr.setPassword(rows.getString("password"));
            usr.setEmail(rows.getString("email"));
            logger.log(Level.INFO, "\nAdding user to list with username=" + usr.getUsername());
            ll.add(usr);
        }
        return ll.toArray(new User[ll.size()]);
    }
    
    public String newUser(User usr) throws SQLException
    {
        // Design the SQL statement that we're going to evaulate.
        // This statement creates a new User and sets it into a folder called:
        // ..."users"...
        // Unless the @path is changed at the top of UserService.
        String sqlInsert= "insert into users (username, password, email) values ('" 
                + usr.getUsername() + "','" 
                + usr.getPassword() + "','" 
                + usr.getEmail() + "');";
        
        // Evaluate the SQL statement...
        Statement s = createStatement();
        logger.log(Level.INFO, "attempting statement execute");
        s.execute(sqlInsert,Statement.RETURN_GENERATED_KEYS);
        logger.log(Level.INFO, "statement executed.  atempting get generated keys");
        ResultSet rs = s.getGeneratedKeys();
        logger.log(Level.INFO, "retrieved keys from statement");
        
        while (rs.next()); 
        
        return usr.getUsername();
    }
    
    public boolean updateUser(User usr) throws SQLException
    {
        // Build our SQL query...
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append("update users ");
        sqlQuery.append("set email='" + usr.getEmail() + "', ");
        sqlQuery.append("password='" + usr.getPassword() + "' ");
        sqlQuery.append("where username='" + usr.getUsername() + "';");
        
        // Execute the query...
        Statement st = createStatement();
        logger.log(Level.INFO, "UPDATE SQL=" + sqlQuery.toString());
        return st.execute(sqlQuery.toString());
    }
    
    public void deleteUser(String username) throws SQLException
    {
        // Prepare the SQL statement...
        String sqlDelete="delete from users where username=?";
        PreparedStatement pst = createPreparedStatement(sqlDelete);
        pst.setString(1, username);
        
        // Attempt user destruction...
        pst.execute();
    }
    
    public Shop[] getShops() throws SQLException
    {
        LinkedList<Shop> ll = new LinkedList<Shop>();
        String sqlQuery ="select * from shops;";
        Statement st = createStatement();
        ResultSet rows = st.executeQuery(sqlQuery);
        while (rows.next())
        {
            logger.log(Level.INFO, "Reading row...");
            
            // Read the row of information.
            Shop shp = new Shop();
            shp.setShopid(rows.getInt("shopid"));
            shp.setName(rows.getString("name"));
            shp.setCity(rows.getString("city"));
            shp.setState(rows.getString("state"));
            shp.setZip(rows.getInt("zip"));
            shp.setPhone(rows.getInt("zip"));
            shp.setOpentime(rows.getInt("opentime"));
            shp.setClosetime(rows.getInt("closetime"));
            
            logger.log(Level.INFO, "\nAdding shop to list with ID: " + shp.getShopid());
            ll.add(shp);
        }
        return ll.toArray(new Shop[ll.size()]);
    }
    
    public int newShop(Shop shp) throws SQLException
    {
        // Design the SQL statement that we're going to evaulate.
        // This statement creates a new Shop and sets it into a folder called:
        // ..."shops"...
        // Unless the @path is changed at the top of ShopService.
        String sqlInsert= "insert into shops "
                + "(shopid, name, city, state, zip, phone, opentime, closetime, description)"
                + " values (" 
                + shp.getShopid() + ","
                + shp.getName() + "','"
                + shp.getCity() + "','"
                + shp.getState() + "',"
                + shp.getZip() + ","
                + shp.getPhone() + ","
                + shp.getOpentime() + ","
                + shp.getClosetime() + ",'"
                + shp.getDescription() + "');";
        
        // Evaluate the SQL statement...
        Statement s = createStatement();
        logger.log(Level.INFO, "attempting statement execute");
        s.execute(sqlInsert,Statement.RETURN_GENERATED_KEYS);
        logger.log(Level.INFO, "statement executed.  atempting get generated keys");
        ResultSet rs = s.getGeneratedKeys();
        logger.log(Level.INFO, "retrieved keys from statement");
        
        int shpid = -1;
        while (rs.next())
            shpid = rs.getInt(1);   // assuming 1st column is shopid 
        
        return shpid;
    }
    
    public boolean updateShop(Shop shp) throws SQLException
    {
        // Build our SQL query...
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append("update shops ");
        sqlQuery.append("set name='" + shp.getName() + "', ");
        sqlQuery.append("city='" + shp.getCity() + "', ");
        sqlQuery.append("state='" + shp.getState() + "', ");
        sqlQuery.append("zip=" + shp.getZip() + ", ");
        sqlQuery.append("phone=" + shp.getPhone() + ", ");
        sqlQuery.append("opentime=" + shp.getOpentime() + ", ");
        sqlQuery.append("closetime=" + shp.getClosetime() + ", ");
        sqlQuery.append("description='" + shp.getDescription() + "' ");
        sqlQuery.append("where shopid=" + shp.getShopid() + ";");
        
        // Execute the query...
        Statement st = createStatement();
        logger.log(Level.INFO, "UPDATE SQL=" + sqlQuery.toString());
        return st.execute(sqlQuery.toString());
    }
    
    public void deleteShop(int shopid) throws SQLException
    {
        // Prepare the SQL statement...
        String sqlDelete="delete from shops where shopid=?";
        PreparedStatement pst = createPreparedStatement(sqlDelete);
        pst.setInt(1, shopid);
        
        // Attempt user destruction...
        pst.execute();
    }
}
