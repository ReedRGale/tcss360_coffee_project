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

    /**
     * POST method for updating or creating an instance of a Shop
     *
     * @param jobj: A JSON object to format into a Shop object.
     */
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String createShop(String jobj) throws IOException {
        // Turn the JSON into a Shop Object.
        ObjectMapper mapper = new ObjectMapper();
        Shop shp = mapper.readValue(jobj, Shop.class);

        // String to let us know what's happening.
        StringBuilder text = new StringBuilder();
        text.append("\nThe JSON obj:" + jobj + "\n");
        text.append("Created " + shp.getShopid() + "...\n");
        text.append("Name: " + shp.getName() + "...\n");
        text.append("Street: " + shp.getStreet() + "...\n");
        text.append("City: " + shp.getCity() + "...\n");
        text.append("State: " + shp.getState() + "...\n");
        text.append("Zip: " + shp.getZip() + "...\n");
        text.append("Phone: " + shp.getPhone() + "...\n");
        text.append("Opentime: " + shp.getOpentime() + "...\n");
        text.append("Closetime: " + shp.getClosetime() + "...\n");
        text.append("Description: " + shp.getDescription() + "...\n");
        text.append("Capacity: " + shp.getCapacity() + "...\n");
        text.append("Wifi: " + shp.getWifi() + "...\n");
        text.append("Volume: " + shp.getVolume() + "...\n");

        try {
            Model db = Model.singleton();
            int id = db.newShop(shp);
            logger.log(Level.INFO, "Shop persisted to db with ID: " + id);
            text.append("Shop ID persisted with ID: " + id);
        } catch (SQLException sqle) {
            String errText = "Error persisting shop after db connection made:\n" + sqle.getMessage() + " --- " + sqle.getSQLState() + "\n";
            logger.log(Level.SEVERE, errText);
            text.append(errText);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error connecting to db.");
        }

        return text.toString();
    }

    /**
     * PUT method for updating or creating an instance of a Shop
     *
     * @param jobj: A JSON object to format into a Shop object.
     */
    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateShop(String jobj) throws IOException {
        // Convert JSON to User object.
        ObjectMapper mapper = new ObjectMapper();
        Shop shp = mapper.readValue(jobj, Shop.class);

        // Update the user.
        StringBuilder text = new StringBuilder();
        try {
            Model db = Model.singleton();
            int id = shp.getShopid();
            db.updateShop(shp);
            logger.log(Level.INFO, "Update shop with ID: " + id);
            text.append("Shop updated with ID: " + id + "\n");
        } catch (SQLException sqle) {
            String errText = "Error updating shop after db connection made:\n" + sqle.getMessage() + " --- " + sqle.getSQLState() + "\n";
            logger.log(Level.SEVERE, errText);
            text.append(errText);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error connecting to db.");
            text.append("Error connecting to db.");
        }
        return text.toString();
    }

    /**
     * DELETE method for destroying an instance of a Shop. The function deletes
     * a shop with a certain ID.
     *
     * @param jobj: A JSON object to format into a User object.
     */
    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String deleteUser(String jobj) throws IOException {
        // Convert JSON to a User object...
        ObjectMapper mapper = new ObjectMapper();
        Shop shp = mapper.readValue(jobj, Shop.class);

        // Attempt user destruction...
        StringBuilder text = new StringBuilder();
        try {
            Model db = Model.singleton();
            int id = shp.getShopid();
            db.deleteShop(id);
            logger.log(Level.INFO, "Shop deleted from db: " + id);
            text.append("Shop deleted with ID: " + id);
        } catch (SQLException sqle) {
            String errText = "Error deleting shop after db connection made:\n" + sqle.getMessage() + " --- " + sqle.getSQLState() + "\n";
            logger.log(Level.SEVERE, errText);
            text.append(errText);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error connecting to db.");
            text.append("Error connecting to db.");
        }
        return text.toString();
    }
}

