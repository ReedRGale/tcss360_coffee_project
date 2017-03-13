/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import objects.Review;
import objects.Shop;
import objects.User;
import java.sql.Date;
import java.util.Calendar;

/**
 *
 * @author wlloyd
 */
public class Model {
    private static final int REVIEW_DECAY_RATE = -4;
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
            usr.setUserid(rows.getInt("userid"));
            usr.setUsername(rows.getString("username"));
            usr.setPassword(rows.getString("password"));
            usr.setEmail(rows.getString("email"));
            logger.log(Level.INFO, "\nAdding user to list with username=" + usr.getUsername());
            ll.add(usr);
        }
        return ll.toArray(new User[ll.size()]);
    }
    
    public int newUser(User usr) throws SQLException
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
        
        int shpid = -1;
        while (rs.next())
            shpid = rs.getInt(1);   // assuming 1st column is shopid 
        
        return shpid;
    }
    
    public boolean updateUser(User usr) throws SQLException
    {
        // Build our SQL query...
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append("update users ");
        sqlQuery.append("set email='" + usr.getEmail() + "', ");
        sqlQuery.append("password='" + usr.getPassword() + "', ");
        sqlQuery.append("username='" + usr.getUsername() + "' ");
        sqlQuery.append("where userid=" + usr.getUserid() + ";");
        
        // Execute the query...
        Statement st = createStatement();
        logger.log(Level.INFO, "UPDATE SQL=" + sqlQuery.toString());
        return st.execute(sqlQuery.toString());
    }
    
    public void deleteUser(int userid) throws SQLException
    {
        // Prepare the SQL statement...
        String sqlDelete="delete from users where userid=?";
        PreparedStatement pst = createPreparedStatement(sqlDelete);
        pst.setInt(1, userid);
        
        // Attempt user destruction...
        pst.execute();
    }
    
// **** **** **** **** **** **** **** **** **** **** **** **** **** **** //
    
    public Shop[] getShops(int query) throws SQLException
    {
        LinkedList<Shop> ll = new LinkedList<Shop>();
        String sqlQuery = null;
        
        if (query == 0)
        {
            sqlQuery = "select * from shops orderby name;";
        }
        else
        {
            sqlQuery = "select * from shops where shopid=" + query + "orderby shopid;";
        }
        
        Statement st = createStatement();
        ResultSet rows = st.executeQuery(sqlQuery);
        while (rows.next())
        {
            logger.log(Level.INFO, "Reading row...");
            
            // Read the row of information.
            Shop shp = new Shop();
            shp.setShopid(rows.getInt("shopid"));
            shp.setName(rows.getString("name"));
            shp.setStreet(rows.getString("street"));
            shp.setCity(rows.getString("city"));
            shp.setState(rows.getString("state"));
            shp.setZip(rows.getInt("zip"));
            shp.setPhone(rows.getString("phone"));
            shp.setOpentime(rows.getInt("opentime"));
            shp.setClosetime(rows.getInt("closetime"));
            shp.setDescription(rows.getString("description"));
            shp.setCapacity(rows.getInt("capacity"));
            shp.setWifi(rows.getInt("wifi"));
            shp.setVolume(rows.getInt("volume"));
            
            double coffeerank = calculateRank(shp.getShopid(), 1);
            double foodrank = calculateRank(shp.getShopid(), 2);
            double expenserank = calculateRank(shp.getShopid(), 3);
            shp.setCoffeeRank(coffeerank);
            shp.setFoodRank(foodrank);
            shp.setExpenseRank(expenserank);
            
            
            logger.log(Level.INFO, "\nAdding shop to list with ID: " + shp.getShopid());
            ll.add(shp);
        }
        return ll.toArray(new Shop[ll.size()]);
    }
    
    private double calculateRank(int id, int choice) throws SQLException {
     
        Review[] rvws = getReviews(id);
        double[] weights = new double[rvws.length];
	
        int outOf = 0; 
	int age;
	// get percentage of original weight to keep (out of 100; ex: a review that is 
        //2 months old will keep 92% (100 + (2 * -4)) of its original weight of 100)
	for (int i = 0; i < rvws.length; i++) {
		age = calculateAgeInMonths(rvws[i].getDateadded()); 
		weights[i] = 100 + (age * REVIEW_DECAY_RATE);
		outOf += weights[i];
	}
	
	// calculate percentage to use for weighted average (sum of all weights after this loop should add up to 100)
	for (int i = 0; i < weights.length; i++) {
		weights[i] = weights[i]/outOf;
	}
	
	// calculate overall rank (sum of each review's rank * weight)
	double rank = 0;
        
        switch (choice) {
            case 1: //coffee ranking 
                for (int i = 0; i < rvws.length; i++) {
                    rank += rvws[i].getCoffeerank() * weights[i];
                }   
                break;
            case 2: //food ranking
                for (int i = 0; i < rvws.length; i++) {
                    rank += rvws[i].getFoodrank() * weights[i];
                }  
                break;
            case 3: //expense ranking
                for (int i = 0; i < rvws.length; i++) {
                    rank += rvws[i].getExpenserank() * weights[i];
                }
                break;
        }	
	return rank;
    }
    
    private int calculateAgeInMonths(Date date) {
        Calendar now = Calendar.getInstance();
        Calendar d = Calendar.getInstance();
        d.setTime(date);
        int diff = (now.get(Calendar.YEAR) - d.get(Calendar.YEAR)) * 12 + now.get(Calendar.MONTH) - d.get(Calendar.MONTH);
        return diff;
    }
    
    
    public int newShop(Shop shp) throws SQLException
    {
        // Design the SQL statement that we're going to evaulate.
        // This statement creates a new Shop and sets it into a folder called:
        // ..."shops"...
        // Unless the @path is changed at the top of ShopService.
        String sqlInsert= "insert into shops "
                + "(name, street, city, state, zip, phone, opentime, closetime, description, capacity, wifi, volume)"
                + " values ('" 
                + shp.getName() + "','"
                + shp.getStreet() + "','"
                + shp.getCity() + "','"
                + shp.getState() + "',"
                + shp.getZip() + ","
                + shp.getPhone() + ","
                + shp.getOpentime() + ","
                + shp.getClosetime() + ",'"
                + shp.getDescription() + "',"
                + shp.getCapacity() + ","
                + shp.getWifi() + ","
                + shp.getVolume() + ");";
        
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
        sqlQuery.append("street='" + shp.getStreet() + "', ");
        sqlQuery.append("city='" + shp.getCity() + "', ");
        sqlQuery.append("state='" + shp.getState() + "', ");
        sqlQuery.append("zip=" + shp.getZip() + ", ");
        sqlQuery.append("phone=" + shp.getPhone() + ", ");
        sqlQuery.append("opentime=" + shp.getOpentime() + ", ");
        sqlQuery.append("closetime=" + shp.getClosetime() + ", ");
        sqlQuery.append("description='" + shp.getDescription() + "', ");
        sqlQuery.append("capacity=" + shp.getCapacity() + ", ");
        sqlQuery.append("volume=" + shp.getVolume() + ", ");
        sqlQuery.append("wifi=" + shp.getWifi() + " ");
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
    
// **** **** **** **** **** **** **** **** **** **** **** **** **** **** //
    
    public Review[] getReviews() throws SQLException
    {
        LinkedList<Review> ll = new LinkedList<Review>();
        String sqlQuery ="select * from reviews;";
        Statement st = createStatement();
        ResultSet rows = st.executeQuery(sqlQuery);
        while (rows.next())
        {
            logger.log(Level.INFO, "Reading row...");
            Review rvw = new Review();
            rvw.setReviewid(rows.getInt("reviewid"));
            rvw.setFoodrank(rows.getInt("foodrank"));
            rvw.setExpenserank(rows.getInt("expenserank"));
            rvw.setCoffeerank(rows.getInt("coffeerank"));
            rvw.setDateadded(rows.getDate("dateadded"));
            rvw.setOwner(rows.getInt("owner"));
            rvw.setShop(rows.getInt("shop"));
            rvw.setComment(rows.getString("comment"));
            logger.log(Level.INFO, "Adding user to list with id=" + rvw.getReviewid());
            ll.add(rvw);
        }
        return ll.toArray(new Review[ll.size()]);
    }
    
    // returns array of reviews connected to specified coffee shop sortecd from newest to oldest
    private Review[] getReviews(int shopid) throws SQLException
    {
        LinkedList<Review> ll = new LinkedList<Review>();
        String sqlQuery ="select * from reviews where shop=" + shopid + " order by dateadded desc;";
        Statement st = createStatement();
        ResultSet rows = st.executeQuery(sqlQuery);
        while (rows.next())
        {
            logger.log(Level.INFO, "Reading row...");
            Review rvw = new Review();
            rvw.setReviewid(rows.getInt("reviewid"));
            rvw.setFoodrank(rows.getInt("foodrank"));
            rvw.setExpenserank(rows.getInt("expenserank"));
            rvw.setCoffeerank(rows.getInt("coffeerank"));
            rvw.setDateadded(rows.getDate("dateadded"));
            rvw.setOwner(rows.getInt("owner"));
            rvw.setShop(rows.getInt("shop"));
            rvw.setComment(rows.getString("comment"));
            logger.log(Level.INFO, "Adding user to list with id=" + rvw.getReviewid());
            ll.add(rvw);
        }
        return ll.toArray(new Review[ll.size()]);
    }
    
    
    
    public int newReview(Review rvw) throws SQLException
    {  
        // Define the SQL insert we're inserting.
        String sqlInsert="insert into reviews "
                + "(foodrank, expenserank, coffeerank, "
                + "dateadded, owner, shop, comment) values (" 
                + rvw.getFoodrank() + ","
                + rvw.getExpenserank() + ","
                + rvw.getCoffeerank() + ","
                + "now()" + ","
                + rvw.getOwner() + ","
                + rvw.getShop() + ","
                + "'" + rvw.getComment() + "');";
        
        // Create the object that will execute our SQL insert
        // Implicitly defines the connection to the server.
        Statement s = createStatement();
        logger.log(Level.INFO, "attempting statement execute");
        s.execute(sqlInsert,Statement.RETURN_GENERATED_KEYS);
        logger.log(Level.INFO, "statement executed.  atempting get generated keys");
        ResultSet rs = s.getGeneratedKeys();
        logger.log(Level.INFO, "retrieved keys from statement");
        
        // Generate message ID.
        int rvwId = -1;
        while (rs.next())
            rvwId = rs.getInt(1);   // assuming 1st column is msgid
        logger.log(Level.INFO, "The new review id: " + rvwId);
        return rvwId;
    }
    
    public boolean updateReview(Review msg) throws SQLException
    {
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append("update reviews ");
        sqlQuery.append("set foodrank=" + msg.getFoodrank() + ", ");
        sqlQuery.append("expenserank=" + msg.getExpenserank() + ", ");
        sqlQuery.append("coffeerank=" + msg.getCoffeerank() + ", ");
        sqlQuery.append("comment=" + msg.getComment() + " ");
        sqlQuery.append("where reviewid=" + msg.getReviewid() + ";");
        
        Statement st = createStatement();
        logger.log(Level.INFO, "UPDATE SQL=" + sqlQuery.toString());
        return st.execute(sqlQuery.toString());
    }
    
    public void deleteReview(int reviewid) throws SQLException
    {
        String sqlDelete="delete from reviews where reviewid=?";
        PreparedStatement pst = createPreparedStatement(sqlDelete);
        pst.setInt(1, reviewid);
        pst.execute();
    }
    
    public Shop[] getMessages(int messageId) throws SQLException
    {
        LinkedList<Shop> ll = new LinkedList<Shop>();
        String sqlQuery ="select * from shops";
        sqlQuery += (messageId > 0) ? " where shopid=" + messageId + " order by shopid;" : " order by shopid;";
        Statement st = createStatement();
        ResultSet rows = st.executeQuery(sqlQuery);
        while (rows.next())
        {
            logger.log(Level.INFO, "Reading row...");
            Shop shp = new Shop();
            shp.setShopid(rows.getInt("shopid"));
            shp.setName(rows.getString("name"));
            shp.setStreet(rows.getString("street"));
            shp.setCity(rows.getString("city"));
            shp.setState(rows.getString("city"));
            shp.setZip(rows.getInt("zip"));
            shp.setPhone(rows.getString("phone"));
            shp.setOpentime(rows.getInt("opentime"));
            shp.setClosetime(rows.getInt("closetime"));
            shp.setDescription(rows.getString("description"));
            shp.setCapacity(rows.getInt("capacity"));
            shp.setVolume(rows.getInt("volume"));
            shp.setWifi(rows.getInt("wifi"));
     
         
            logger.log(Level.INFO, "Adding user to list with id=" + shp.getShopid());
            ll.add(shp);
        }
        return ll.toArray(new Shop[ll.size()]);
    }
}
