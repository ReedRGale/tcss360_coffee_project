/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.sample_maven_web_app;

import static com.mycompany.sample_maven_web_app.UserService.logger;
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
import objects.Message;
import objects.User;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * REST Web Service
 *
 * @author Jared Lowery
 */
@Path("messages")
public class MessageService {

    // What is this for?
    // Changed to be our MessageService class
    static final Logger logger = Logger.getLogger(MessageService.class.getName());
    
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of the MessageService
     */
    public MessageService() 
    {
        
    }

    /**
     * Retrieves representation of an instance of services.GenericResource
     * @return an instance of java.lang.String
     */
    @GET // I think this tells REST this is the get function.
    @Produces(MediaType.TEXT_HTML) // This specifies the mediatype returns to the client.
    public String getMessages() 
    {
        //TODO return proper representation object
        StringBuilder sb = new StringBuilder();
        sb.append("<html><body><style>table, th, td {font-family:Arial,Verdana,sans-serif;font-size:16px;padding: 0px;border-spacing: 0px;}</style><b>MESSAGE LIST:</b><br><br><table cellpadding=10 border=1><tr><td>User ID</td><td>Message</td><td>Date</td><td>Message ID</td></tr>");
        try
        {
            Model db = Model.singleton();
            Message[] messages = db.getMessages();
            for (int i=0;i<messages.length;i++)
                sb.append("<tr><td>" + messages[i].getUserId() + "</td><td>" + messages[i].getMessage() + "</td><td>" + messages[i].getDateadded() + "</td><td>" + messages[i].getMessageId() + "</td></tr>");
        }
        catch (Exception e)
        {
            sb.append("</table><br>Error getting users: " + e.toString() + "<br>");
        }
        sb.append("</table></body></html>");
        return sb.toString();
    }
//elegiggl
    /**
     * PUT method for updating or creating an instance of GenericResource
     * @param content representation for the resource
     */
    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateMessage(String jobj) throws IOException
    {
        ObjectMapper mapper = new ObjectMapper();
        Message msg = mapper.readValue(jobj.toString(), Message.class);
        StringBuilder text = new StringBuilder();
        try {
            Model db = Model.singleton();
            int messageId = msg.getMessageId();
            db.updateMessage(msg);
            logger.log(Level.INFO, "update user with messageId=" + messageId);
            text.append("Message id updated with messageId=" + messageId + "\n");
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
    
    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String deleteMessage(String jobj) throws IOException
    {
        ObjectMapper mapper = new ObjectMapper();
        Message msg = mapper.readValue(jobj.toString(), Message.class);
        StringBuilder text = new StringBuilder();
        try {
            Model db = Model.singleton();
            int messageId = msg.getMessageId();
            db.deleteMessage(messageId);
            logger.log(Level.INFO, "message deleted from db=" + messageId);
            text.append("Message deleted with id=" + messageId);
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
    
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String createMessage(String jobj) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Message msg = mapper.readValue(jobj.toString(), Message.class);
        
        StringBuilder text = new StringBuilder();
        text.append("The JSON obj:" + jobj.toString() + "\n");
        text.append("MsgID: " + msg.getMessageId() + "\n");
        text.append("UserID: " + msg.getUserId() + "\n");
        text.append("Message: " + msg.getMessage() + "\n");
        text.append("Date Added: " + msg.getDateadded() + "\n");
        
        try {
            Model db = Model.singleton();
            int msgid = db.newMessage(msg);
            logger.log(Level.INFO, "message persisted to db as userid=" + msgid);
            text.append("message id persisted with id=" + msgid);
        }
        catch (SQLException sqle)
        {
            String errText = "Error persisting message after db connection made:\n" + sqle.getMessage() + " --- " + sqle.getSQLState() + "\n";
            logger.log(Level.SEVERE, errText);
            text.append(errText);
        }
        catch (Exception e)
        {
            logger.log(Level.SEVERE, "Error connecting to db.");
        }
        
        
        
        return text.toString();
    }
}

