/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tcss360_coffee_project;

import data.Model;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import objects.User;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * REST Web Service
 *
 * @author Jared Lowery
 * @author lloyd
 */
@Path("users")
public class UserService {

    static final Logger logger = Logger.getLogger(UserService.class.getName());
    
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of the user.
     */
    public UserService() {
        
    }

    /**
     * GET function that retrieves all users and returns them in a table.
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getUsers() {
        //TODO return proper representation object
        StringBuilder sb = new StringBuilder();
        sb.append("<html><body><style>table, th, td {font-family:Arial,Verdana,sans-serif;"
                + "font-size:16px;padding: 0px;border-spacing: 0px;}a {color: yellowgreen;"
                + "-webkit-transition: all .35s;-moz-transition: all .35s;"
                + "transition: all .35s;}a:hover,a:focus {color: forestgreen;"
                + "}ul {list-style-type: none;margin: 0;padding: 0;"
                + "overflow: hidden;background-color: #f1f1f1;}li {float: left;"
                + "}li a {display: block;text-align: center;padding: 14px 16px;"
                + "text-decoration: none;}li a:hover {background-color: yellowgreen;}"
                + ".navbar-default {border-color: rgba(34, 34, 34, .05);"
                + "background-color: #ffff;-webkit-transition: all .35s;"
                + "-moz-transition: all .35s;transition: all .35s;}.navbar-default "
                + ".navbar-header .navbar-brand {color: yellowgreen;}.navbar-default "
                + ".nav > li>a,.navbar-default .nav>li>a:focus {color: #222;}"
                + ".navbar-default .nav > li.active>a:hover,.navbar-default "
                + ".nav>li.active>a:focus {color: yeallowgreen!important;"
                + "background-color: transparent;}@media(min-width:768px) {"
                + ".navbar-default {border-color: rgba(255,255,255,.7);"
                + "background-color: transparent;}.navbar-default .navbar-header "
                + ".navbar-brand {color: rgba(255, 255, 255, .7);letter-spacing: 0.5em}"
                + ".navbar-default .navbar-header .navbar-brand:hover,.navbar-default "
                + ".navbar-header .navbar-brand:focus {color: #fff;}.navbar-default "
                + ".nav > li>a,.navbar-default "
                + ".nav > li > a:focus {color: rgba(255,255,255,.7);}.navbar-default "
                + ".nav > li>a:hover,.navbar-default .nav > li > a:focus:hover {"
                + "color: #fff;}.navbar-default.affix {border-color: #fff;"
                + "background-color: #fff;box-shadow: 0px 7px 20px 0px rgba(0,0,0,0.1);}"
                + ".navbar-default.affix .nav > li>a,.navbar-default.affix "
                + ".nav>li>a:focus:hover {color: yellowgreen;}}"
                + "</style><nav id=\"siteNav\" class=\"navbar navbar-default "
                + "navbar-fixed-top\" role=\"navigation\"><div class=\"container\">"
                + "<div class=\"navbar-header\"></div></div><div class=\"collapse "
                + "navbar-collapse\" id=\"navbar\"><ul class=\"collapse navbar-collapse\" "
                + "id=\"navbar\"><li class=\"active\"><a "
                + "href=\"https://shrouded-shore-30021.herokuapp.com\">Home</a>"
                + "</li><li>"
                + "<a href=\"https://shrouded-shore-30021.herokuapp.com/home/shops\">"
                + "Shops</a></li><li><a "
                + "href=\"https://shrouded-shore-30021.herokuapp.com/home/reviews\">"
                + "Reviews</a></li><li><a "
                + "href=\"https://shrouded-shore-30021.herokuapp.com/home/users\">" +
                "   <tr>\n" +
                "       <td>Enter User ID:  </td>\n" +
                "       <td><input type=\"text\" id=\"id-field\" size=\"10\"/>  \n" +
                "   </tr>" +

                "   <tr>\n" +
                "       <td>Enter Name:  </td>\n" +
                "       <td><input type=\"text\" id=\"name-field\" size=\"10\"/>  \n" +
                "   </tr>" +

                "   <tr>\n" +
                "       <td>Enter Password:  </td>\n" +
                "       <td><input type=\"text\" id=\"password-field\" size=\"10\"/>  \n" +
                "   </tr>" +

                "   <tr>\n" +
                "       <td>Enter Email:  </td>\n" +
                "       <td><input type=\"text\" id=\"email-field\" size=\"10\"/>  \n" +
                "   </tr>" 
                + "Users</a></li></ul></div></nav><b>USERS LIST:</b><br><br><table "
                + "cellpadding=10 border=1><tr>"
                + "<td>ID</td>"
                + "<td>Username</td>"
                + "<td>Password</td>"
                + "<td>Email</td></tr>");
        
        
        try
        {
            Model db = Model.singleton();
            User[] users = db.getUsers();
            for (int i=0;i<users.length;i++)
                sb.append("<tr><td>" + 
                        users[i].getUserid() + "</td><td>" +
                        users[i].getUsername() + "</td><td>" + 
                        users[i].getPassword() + "</td><td>" + 
                        users[i].getEmail() + "</td></tr>");
        }
        catch (Exception e)
        {
            sb.append("</table><br>Error getting users: " + e.toString() + "<br>");
        }
        sb.append("</table></body></html>");
        return sb.toString();
    }
    
    /**
     * POST method for updating or creating an instance of a User
     * @param jobj: A JSON object to format into a User object.
     */
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String createUser(String jobj) throws IOException {
        
        // Turn the JSON into a User Object.
        ObjectMapper mapper = new ObjectMapper();
        User usr = mapper.readValue(jobj, User.class);
        
        // String to let us know what's happening.
        StringBuilder text = new StringBuilder();
        text.append("\nThe JSON obj:" + jobj + "\n");
        text.append("Created " + usr.getUsername() + "...\n");
        
        try {
            Model db = Model.singleton();
            int id = db.newUser(usr);
            logger.log(Level.INFO, "user persisted to db as username=" + id);
            text.append("User id persisted with id=" + id);
        }
        catch (SQLException sqle)
        {
            String errText = "Error persisting user after db connection made:\n" + sqle.getMessage() + " --- " + sqle.getSQLState() + "\n";
            logger.log(Level.SEVERE, errText);
            text.append(errText);
        }
        catch (Exception e)
        {
            logger.log(Level.SEVERE, "Error connecting to db.");
        }
        
        return text.toString();
    }

    /**
     * PUT method for updating or creating an instance of a User
     * @param jobj: A JSON object to format into a User object.
     */
    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateUser(String jobj) throws IOException
    {
        // Convert JSON to User object.
        ObjectMapper mapper = new ObjectMapper();
        User usr = mapper.readValue(jobj, User.class);
        
        // Update the user.
        StringBuilder text = new StringBuilder();
        try {
            Model db = Model.singleton();
            int userid = usr.getUserid();
            db.updateUser(usr);
            logger.log(Level.INFO, "update user with id: " + userid);
            text.append("User updated with id: " + userid + "\n");
        }
        catch (SQLException sqle)
        {
            String errText = "Error updating user after db connection made:\n" + sqle.getMessage() + " --- " + sqle.getSQLState() + "\n";
            logger.log(Level.SEVERE, errText);
            text.append(errText);
        }
        catch (Exception e)
        {
            logger.log(Level.SEVERE, "Error connecting to db.");
            text.append("Error connecting to db.");
        }
        return text.toString();
    }
    
    /**
     * DELETE method for destroying an instance of a User.
     * The function deletes a user with a certain username.
     * @param jobj: A JSON object to format into a User object.
     */
    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String deleteUser(String jobj) throws IOException
    {
        // Convert JSON to a User object...
        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.readValue(jobj.toString(), User.class);
        
        // Attempt user destruction...
        StringBuilder text = new StringBuilder();
        try {
            Model db = Model.singleton();
            int userid = user.getUserid();
            db.deleteUser(userid);
            logger.log(Level.INFO, "user deleted from db=" + userid);
            text.append("User deleted with username=" + userid);
        }
        catch (SQLException sqle)
        {
            String errText = "Error deleteing user after db connection made:\n" + sqle.getMessage() + " --- " + sqle.getSQLState() + "\n";
            logger.log(Level.SEVERE, errText);
            text.append(errText);
        }
        catch (Exception e)
        {
            logger.log(Level.SEVERE, "Error connecting to db.");
            text.append("Error connecting to db.");
        }
        return text.toString();
    }
    
    
}

