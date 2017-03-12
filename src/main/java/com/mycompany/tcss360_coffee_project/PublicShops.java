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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
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
    @Produces(MediaType.APPLICATION_JSON)
    public LinkedList<Shop> getShops() {
        //TODO return proper representation object
        LinkedList<Shop> shopList = new LinkedList<Shop>();
     
        try
        {
            Model db = Model.singleton();
            Shop[] shops = db.getShops();
            
                for (int i=0;i<shops.length;i++) {
                    shopList.add(shops[i]);
                    logger.log(shops[i].toString);
                }
                
            logger.log(Level.INFO, "Received request to fetch user id=" );
            return shopList;
        }
        catch (Exception e)
        {
            JSONObject obj = new JSONObject();
                logger.log(Level.WARNING, "Error getting users:" + e.toString());
                return null;
        }
    }
}

