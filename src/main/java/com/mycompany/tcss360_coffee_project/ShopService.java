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
import objects.Shop;
import objects.User;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * REST Web Service
 *
 * @author Jared Lowery
 * @author lloyd
 */
@Path("shops")
public class ShopService {

    static final Logger logger = Logger.getLogger(ShopService.class.getName());

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of the shop.
     */
    public ShopService() {

    }

    /**
     * GET function that retrieves all shops in a table.
     *
     * @return A string of HTML that represents the shops.
     */
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getShops() {
        //TODO return proper representation object
        StringBuilder sb = new StringBuilder();
        sb.append("<html><body><style>table, th, td "
                + "{font-family:Arial,Verdana,sans-serif;font-size:16px;padding: "
                + "0px;border-spacing: 0px;}</style>"
                + "<b>SHOP LIST:</b>"
                + "<br><br>"
                + "<div id=\"googleMap\" style=\"float:right;width:60%;height:300px;\"></div>"
                + "<table cellpadding=10 border=1><tr>"
                + "<td>ID</td>"
                + "<td>Name</td>"
                + "<td>Street</td>"
                + "<td>City</td>"
                + "<td>State</td>"
                + "<td>Zip</td>"
                + "<td>Phone</td>"
                + "<td>Open Time</td>"
                + "<td>Close Time</td>"
                + "<td>Description</td>"
                + "</tr>");
        try {
            Model db = Model.singleton();
            Shop[] shps = db.getShops();
            for (int i = 0; i < shps.length; i++) {
                sb.append("<tr><td>"
                        + shps[i].getShopid() + "</td><td>"
                        + shps[i].getName() + "</td><td>"
                        + shps[i].getStreet() + "</td><td>"
                        + shps[i].getCity() + "</td><td>"
                        + shps[i].getState() + "</td><td>"
                        + shps[i].getZip() + "</td><td>"
                        + shps[i].getPhone() + "</td><td>"
                        + shps[i].getOpentime() + "</td><td>"
                        + shps[i].getClosetime() + "</td><td>"
                        + shps[i].getDescription() + "</td></tr>");
            }
        } catch (Exception e) {
            sb.append("</table><br>Error getting shops: " + e.toString() + "<br>");
        }
        
        
        sb.append("</table>");
        
        try {
            Model db = Model.singleton();
            Shop[] shps = db.getShops();
            
            
            sb.append("<script type=\"text/javascript\">\n"
                + "var map;\n"
                + "var infoWindow;\n"
                + "var geocoder;\n"
                + "var labels = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';\n"
                + "var labelIndex = 0;\n"
                + "var markers = new Array();\n"
                //                + "$(document).on('ready', myMap);\n"

                + "var shops = [\n");
            
            for (int i = 0; i < shps.length; i++) {
                sb.append("['" + shps[i].getName() + "', '" +
                          shps[i].getStreet() + " " + 
                          shps[i].getCity() + ", " + 
                          shps[i].getState() + "'],\n");
            }
            sb.append("];\n");
            sb.append("var infoWindowContent = [\n");
            for (int i = 0; i < shps.length; i++) {
                sb.append("['<div><h3>" + shps[i].getName() + "</h3>"
                        + "<p>" + shps[i].getDescription() + "</p></div>'],");

            }
                sb.append("];\n"
                //initializer of the map
                + "function myMap() {\n"
                + "var  map = new google.maps.Map(document.getElementById(\"googleMap\"), {\n"
                + "    zoom: 8,\n"
                + "    center: {\n"
                + "      lat: -34.397,\n"
                + "      lng: 150.644\n"
                + "    }\n"
                + "  });\n"
                + "  var infoWindow = new google.maps.InfoWindow(), marker, i;\n"

                + "  var geocoder = new google.maps.Geocoder();\n"
                + "for( i = 0; i < shops.length; i++ ) {\n"
                + "  geocodeAddress(shops[i][1], i, shops[i][0], geocoder, map, infoWindow);\n"
                + "}\n"
                //                + "  geocodeAddress(address2, geocoder, map);\n"
                //                + "  geocodeAddress(address3, geocoder, map);\n"
                + "}\n"

                + "function geocodeAddress(address, i, title, geocoder, resultsMap, infoWindow) {\n"
                + "  geocoder.geocode({\n"
                + "    'address': address\n"
                + "  }, function(results, status) {\n"

                + "    if (status === google.maps.GeocoderStatus.OK) {\n"
                + "      resultsMap.setCenter(results[0].geometry.location);\n"
                + "      var marker = new google.maps.Marker({\n"
                + "        map: resultsMap,\n"
                + "        label: labels[labelIndex++ % labels.length],\n"
                + "        position: results[0].geometry.location,\n"
                + "        title: title\n"// <---Coffee Shop Name will go here
                + "      });\n"

                + "        google.maps.event.addListener(marker, 'click', (function(marker, i) {\n"
                + "console.log(marker, i);"
                + "            return function() {\n"
                + "                infoWindow.setContent(infoWindowContent[i][0]);\n"
                + "                infoWindow.open(map, marker);\n"
                + "            }\n"
                + "        })(marker, i));\n"
                + "      markers.push(marker);\n"
                + "      updateZoom(resultsMap);\n"
                + "    } else {\n"
                + "      alert('Geocode was not successful for the following reason: ' + status);\n"
                + "    }\n"
                + "  });\n"
                + "}\n"
                //fits all the markers within the map view 
                + "function updateZoom(resultsMap) {\n"
                + "  var bounds = new google.maps.LatLngBounds();\n"
                + "  for (i = 0; i < markers.length; i++) {\n"
                + "    bounds.extend(markers[i].getPosition());\n"
                + "  }\n"
                + "  resultsMap.fitBounds(bounds);\n"
                + "}\n"
                + "</script>\n"
                + "<script src=\"https://maps.googleapis.com/maps/api/js?key=AIzaSyAtTXi_yuz0tnAri_Xd_XZenxYBRTqzqYE&callback=myMap\"></script></body></html>");
            
        } catch (Exception e) {
                sb.append("</table><br>Error getting shops: " + e.toString() + "<br>");
        }
        
        sb.append("</body></html>");
        return sb.toString();
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
