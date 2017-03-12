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
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import objects.Shop;
import objects.User;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONObject;

/**
 * REST Web Service
 *
 * @author Jared Lowery
 * @author lloyd
 */
@Path("allshops")
public class PublicShops{

    static final Logger logger = Logger.getLogger(ShopService.class.getName());

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of the shop.
     */
    public PublicShops() {

    }

    /**
     * GET function that retrieves all shops in a table.
     *
     * @return A string of HTML that represents the shops.
     */
    @GET
    @Path("{shopid}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Shop> getMessages(@PathParam("shopid") String id) {
        LinkedList<Shop> messageList = new LinkedList<Shop>();
     
        try
        {
            int messageid = Integer.parseInt(id);
            Model db = Model.singleton();
            Shop[] shops = db.getMessages(messageid);
            if (messageid == 0)
                for (int i=0;i<shops.length;i++)
                    messageList.add(shops[i]);
            else
                messageList.add(shops[0]);
            logger.log(Level.INFO, "Received request to fetch user id=" + messageid);
            return messageList;
        }
        catch (Exception e)
        {
            JSONObject obj = new JSONObject();
                logger.log(Level.WARNING, "Error getting users:" + e.toString());
                return null;
        }

    }
}

