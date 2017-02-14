/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.sample_maven_web_app;

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
        sb.append("<html><body><style>table, th, td {font-family:Arial,Verdana,sans-serif;font-size:16px;padding: 0px;border-spacing: 0px;}</style><b>USERS LIST:</b><br><br><table cellpadding=10 border=1><tr><td>Username</td><td>Password</td><td>Email</td></tr>");
        try
        {
            Model db = Model.singleton();
            User[] users = db.getUsers();
            for (int i=0;i<users.length;i++)
                sb.append("<tr><td>" + users[i].getUsername() + "</td><td>" + users[i].getPassword() + "</td><td>" + users[i].getEmail() + "</td></tr>");
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
            String username = db.newUser(usr);
            logger.log(Level.INFO, "user persisted to db as username=" + username);
            text.append("User id persisted with id=" + username);
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
            String username = usr.getUsername();
            db.updateUser(usr);
            logger.log(Level.INFO, "update user with username=" + username);
            text.append("User updated with username=" + username + "\n");
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
            String username = user.getUsername();
            db.deleteUser(username);
            logger.log(Level.INFO, "user deleted from db=" + username);
            text.append("User deleted with username=" + username);
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

