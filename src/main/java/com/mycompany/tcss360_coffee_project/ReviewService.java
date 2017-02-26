/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tcss360_coffee_project;

import data.Model;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import objects.Review;
import objects.Shop;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author Reed R Gale
 */
@Path("reviews")
public class ReviewService 
{
    static final Logger logger = Logger.getLogger(ShopService.class.getName());
    
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of the review.
     */
    public ReviewService() 
    {
        
    }

    /**
     * GET function that retrieves all reviews in a table.
     * @return A string of HTML that represents the shops.
     */
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getReviews() {
        //TODO return proper representation object
        StringBuilder sb = new StringBuilder();
        sb.append("<html><body><style>table, th, td "
                + "{font-family:Arial,Verdana,sans-serif;font-size:16px;padding: "
                + "0px;border-spacing: 0px;}</style>"
                + "<b>ALL REVIEWS LIST:</b>"
                + "<br><br>"
                + "<table cellpadding=10 border=1><tr>"
                + "<td>ID</td>"
                + "<td>Food Rank</td>"
                + "<td>Expense</td>"
                + "<td>Coffee Rank</td>"
                + "<td>Added On</td>"
                + "<td>Owner ID</td>"
                + "<td>Shop ID</td>"
                + "<td>Comment</td>"
                + "</tr>");
        try
        {
            Model db = Model.singleton();
            Review[] shps = db.getReviews();
            for (int i=0;i<shps.length;i++)
                sb.append("<tr><td>" 
                        + shps[i].getReviewid() + "</td><td>"
                        + shps[i].getFoodrank() + "</td><td>"
                        + shps[i].getExpenserank() + "</td><td>"
                        + shps[i].getCoffeerank() + "</td><td>"
                        + shps[i].getDateadded() + "</td><td>"
                        + shps[i].getOwner() + "</td><td>"
                        + shps[i].getShop() + "</td><td>"
                        + shps[i].getComment() + "</td><td>"   );
        }
        catch (Exception e)
        {
            sb.append("</table><br>Error getting shops: " + e.toString() + "<br>");
        }
        
        return sb.toString();
    }
    
    /**
     * POST method for updating or creating an instance of a Shop
     * @param jobj: A JSON object to format into a Shop object.
     */
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String createReview(String jobj) throws IOException 
    {
        // Turn the JSON into a Shop Object.
        ObjectMapper mapper = new ObjectMapper();
        Review rvw = mapper.readValue(jobj, Review.class);
        
        // String to let us know what's happening.
        StringBuilder text = new StringBuilder();
        text.append("\nThe JSON obj:" + jobj + "\n");
        text.append("Created " + rvw.getReviewid() + "...\n");
        text.append("Food Rank: " + rvw.getFoodrank() + "...\n" );
        text.append("Expense: " + rvw.getExpenserank() + "...\n" );
        text.append("Coffee Rank: " + rvw.getCoffeerank() + "...\n" );
        text.append("Owner: " + rvw.getOwner() + "...\n" );
        text.append("Shop: " + rvw.getShop() + "...\n" );
        text.append("Comment: " + rvw.getComment() + "...\n" );
        
        try {
            Model db = Model.singleton();
            int id = db.newReview(rvw);
            logger.log(Level.INFO, "Review persisted to db with ID: " + id);
            text.append("Review persisted with ID: " + id);
        }
        catch (SQLException sqle)
        {
            String errText = "Error persisting shop after db connection made:\n" + sqle.getMessage() + " --- " + sqle.getSQLState() + "\n";
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
     * PUT method for updating or creating an instance of a Shop
     * @param jobj: A JSON object to format into a Shop object.
     */
    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateReview(String jobj) throws IOException
    {
        // Convert JSON to User object.
        ObjectMapper mapper = new ObjectMapper();
        Review rvw = mapper.readValue(jobj, Review.class);
        
        // Update the user.
        StringBuilder text = new StringBuilder();
        try {
            Model db = Model.singleton();
            int id = rvw.getReviewid();
            db.updateReview(rvw);
            logger.log(Level.INFO, "Update review with ID: " + id);
            text.append("Review updated with ID: " + id + "\n");
        }
        catch (SQLException sqle)
        {
            String errText = "Error updating shop after db connection made:\n" + sqle.getMessage() + " --- " + sqle.getSQLState() + "\n";
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
     * DELETE method for destroying an instance of a Shop.
     * The function deletes a shop with a certain ID.
     * @param jobj: A JSON object to format into a User object.
     */
    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String deleteReview(String jobj) throws IOException
    {
        // Convert JSON to a User object...
        ObjectMapper mapper = new ObjectMapper();
        Review shp = mapper.readValue(jobj, Review.class);
        
        // Attempt user destruction...
        StringBuilder text = new StringBuilder();
        try {
            Model db = Model.singleton();
            int id = shp.getReviewid();
            db.deleteShop(id);
            logger.log(Level.INFO, "Review deleted from db: " + id);
            text.append("Review deleted with ID: " + id);
        }
        catch (SQLException sqle)
        {
            String errText = "Error deleting shop after db connection made:\n" + sqle.getMessage() + " --- " + sqle.getSQLState() + "\n";
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
