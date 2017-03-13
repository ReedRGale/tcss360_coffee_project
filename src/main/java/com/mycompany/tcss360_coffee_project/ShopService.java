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
        
        StringBuilder sb = new StringBuilder();
        
        try {
            Model db = Model.singleton();
            Shop[] shps = db.getShops(0);
            sb.append("<html>"
                    + "<head>"
                    + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">"
                    + "<script src=\"//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js\"></script>  "
                   
                    + "<script language=\"javascript\">"
                    + "$(document).ready(function () {");
            
            // Designate all buttons a function.
            for (int i = 0; i < shps.length; i++) 
            {
                sb.append(
                "        \n$('#delete-shop-" + shps[i].getShopid() + "').click(function ()\n" +
                "        {\n" +                                         
                "           var shop = { 'shopid': " + shps[i].getShopid() + "};   \n" +     
                "           var url='shops';\n" +
                "           if (confirm('Are you sure')) {\n" +
                "             $.ajax({\n" +
                "               type: 'DELETE',\n" +
                "               url: url,\n" +
                "               datatype: 'json',\n" +
                "               data: JSON.stringify(shop),\n" +
                "               contentType: 'application/json',\n" +
                "               success: render_delete\n" +
                "             }); \n" +
                "           } \n" +         
                "        });"
                );
            }
            
            sb.append(
            "        \n$('#add-shop').click(function ()\n" +
            "        {\n" +                                         
            "            if (document.getElementById(\"id-field\").disabled)\n" +
            "            {\n" +
            "                var name = document.getElementById(\"name-field\");\n" +
            "                var street = document.getElementById(\"street-field\");\n" +
            "                var city = document.getElementById(\"city-field\");\n" +
            "                var state = document.getElementById(\"state-field\");\n" +
            "                var zip = document.getElementById(\"zip-field\");\n" +
            "                var phone = document.getElementById(\"phone-field\");\n" +
            "                var opentime = document.getElementById(\"opentime-field\");\n" +
            "                var closetime = document.getElementById(\"closetime-field\");\n" +
            "                var description = document.getElementById(\"description-field\");\n" +
                    
            "                var obj = { \"name\": name.value, " +
            "                            \"street\": street.value,            \n" +
            "                            \"city\": city.value,           \n" +
            "                            \"state\": state.value,           \n" +
            "                            \"zip\": zip.value,            \n" +
            "                            \"phone\": phone.value,            \n" +
            "                            \"opentime\": opentime.value,            \n" +
            "                            \"closetime\": closetime.value,            \n" +
            "                            \"description\": description.value };           \n" +
                    
            "                var url='shops';\n" +
            "                if (confirm('Are you sure you wanna add this?')) {\n" +
            "                  $.ajax({\n" +
            "                    type: 'POST',\n" +
            "                    url: url,\n" +
            "                    datatype: 'json',\n" +
            "                    data: JSON.stringify(obj),\n" +
            "                    contentType: 'application/json',\n" +
            "                    success: render_newuser\n" +
            "                  }); \n" +
            "                }                 \n" +
            "            }\n" +
            "            else\n" +
            "            {\n" +
            "                var shopid = document.getElementById(\"id-field\");\n" +
            "                var name = document.getElementById(\"name-field\");\n" +
            "                var street = document.getElementById(\"street-field\");\n" +
            "                var city = document.getElementById(\"city-field\");\n" +
            "                var state = document.getElementById(\"state-field\");\n" +
            "                var zip = document.getElementById(\"zip-field\");\n" +
            "                var phone = document.getElementById(\"phone-field\");\n" +
            "                var opentime = document.getElementById(\"opentime-field\");\n" +
            "                var closetime = document.getElementById(\"closetime-field\");\n" +
            "                var description = document.getElementById(\"description-field\");\n" +
                    
            "                var obj = { \"shopid\": shopid.value, " +
            "                            \"name\": name.value,            \n" +
            "                            \"street\": street.value,            \n" +
            "                            \"city\": city.value,           \n" +
            "                            \"state\": state.value,           \n" +
            "                            \"zip\": zip.value,            \n" +
            "                            \"phone\": phone.value,            \n" +
            "                            \"opentime\": opentime.value,            \n" +
            "                            \"closetime\": closetime.value,            \n" +
            "                            \"description\": description.value };           \n" +
                    
            "                var url='shops';\n" +
            "                if (confirm('Are you sure you wanna update this?')) {\n" +
            "                  $.ajax({\n" +
            "                    type: 'POST',\n" +
            "                    url: url,\n" +
            "                    datatype: 'json',\n" +
            "                    data: JSON.stringify(obj),\n" +
            "                    contentType: 'application/json',\n" +
            "                    success: render_newuser\n" +
            "                  }); \n" +
            "                }                 \n" +
            "            }" +         
            "        });"
            );
            
            sb.append(
            "        $('#new-shop').click(function ()\n" +
            "        {\n" +
            "           console.log('Inside');" +
            "           document.getElementById(\"id-field\").disabled = true;\n" +       
            "        }\n);"
            );
            
            // Delete shop functionality.
            sb.append(
            "    function render_delete(data)\n" +
            "    {\n" +            
            "        console.log(data);" +
            "    }"
            );
            
            // Add shop functionality.
            sb.append(
            "    function render_newuser(data)\n" +
            "    {\n" +            
            "        document.getElementById(\"id-field\").disabled = false;" +
            "        console.log(data);" +
            "    }"
            );

            sb.append("});"
                    + "</script>"
                    + "</head>"
                    
                    + "<input type=\"button\" value=\"New Shop\" onclick=\"\" id=\"new-shop\"/>"
                    + "<input type=\"button\" value=\"Add/Update Shop\" onclick=\"\" id=\"add-shop\"/>" +
                    
                    "   <tr>\n" +
                    "       <td>Enter Shop ID:  </td>\n" +
                    "       <td><input type=\"text\" id=\"id-field\" size=\"10\"/>  \n" +
                    "   </tr>" +
                    
                    "   <tr>\n" +
                    "       <td>Enter Name:  </td>\n" +
                    "       <td><input type=\"text\" id=\"name-field\" size=\"10\"/>  \n" +
                    "   </tr>" +
                   
                    "   <tr>\n" +
                    "       <td>Enter Street:  </td>\n" +
                    "       <td><input type=\"text\" id=\"street-field\" size=\"10\"/>  \n" +
                    "   </tr>" +
                    
                    "   <tr>\n" +
                    "       <td>Enter City:  </td>\n" +
                    "       <td><input type=\"text\" id=\"city-field\" size=\"10\"/>  \n" +
                    "   </tr>" +
                    
                    "   <tr>\n" +
                    "       <td>Enter State:  </td>\n" +
                    "       <td><input type=\"text\" id=\"state-field\" size=\"10\"/>  \n" +
                    "   </tr>" +
                    
                    "   <tr>\n" +
                    "       <td>Enter Zip:  </td>\n" +
                    "       <td><input type=\"text\" id=\"zip-field\" size=\"10\"/>  \n" +
                    "   </tr>" +
                    
                    "   <tr>\n" +
                    "       <td>Enter Phone Number:  </td>\n" +
                    "       <td><input type=\"text\" id=\"phone-field\" size=\"10\"/>  \n" +
                    "   </tr>" +
                    
                    "   <tr>\n" +
                    "       <td>Enter Opentime (HHMM):  </td>\n" +
                    "       <td><input type=\"text\" id=\"opentime-field\" size=\"10\"/>  \n" +
                    "   </tr>" +
                    
                    "   <tr>\n" +
                    "       <td>Enter Closetime (HHMM):  </td>\n" +
                    "       <td><input type=\"text\" id=\"closetime-field\" size=\"10\"/>  \n" +
                    "   </tr>" +
                    
                    "   <tr>\n" +
                    "       <td>Enter Description:  </td>\n" +
                    "       <td><input type=\"text\" id=\"description-field\" size=\"100\"/>  \n" +
                    "   </tr>" 
                    
                    + "<body><style>table, th, td "
                    + "{font-family:Arial,Verdana,sans-serif;font-size:16px;padding: "
                    + "0px;border-spacing: 0px;}a {color: yellowgreen;-webkit-transition: "
                    + "all .35s;-moz-transition: all .35s;transition: all "
                    + ".35s;}a:hover,a:focus {color: forestgreen;}ul {list-style-type: "
                    + "none;margin: 0;padding: 0;overflow: hidden;"
                    + "background-color: #f1f1f1;}li {float: left;}li a {display: block;"
                    + "text-align: center;padding: 14px 16px;text-decoration: none;"
                    + "}li a:hover {background-color: yellowgreen;}"
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
                    + ".nav > li>a,.navbar-default .nav > li > a:focus {"
                    + "color: rgba(255,255,255,.7);}.navbar-default "
                    + ".nav > li>a:hover,.navbar-default .nav > li > a:focus:hover {"
                    + "color: #fff;}.navbar-default.affix {border-color: #fff;"
                    + "background-color: #fff;box-shadow: 0px 7px 20px 0px rgba(0,0,0,0.1);}"
                    + ".navbar-default.affix .nav > li>a,.navbar-default.affix "
                    + ".nav>li>a:focus:hover {color: yellowgreen;}}</style>"
                    + "<nav id=\"siteNav\" class=\"navbar navbar-default navbar-fixed-top\" "
                    + "role=\"navigation\"><div class=\"container\"><div "
                    + "class=\"navbar-header\"></a>"
                    + "</div></div><div class=\"collapse navbar-collapse\" id=\"navbar\">"
                    + "<ul class=\"collapse navbar-collapse\" id=\"navbar\"><li "
                    + "class=\"active\"><a href=\"https://shrouded-shore-30021.herokuapp.com\">"
                    + "Home</a></li><li><a href=\"https://shrouded-shore-30021.herokuapp.com/home/shops\">"
                    + "Shops</a></li><li><a href=\"https://shrouded-shore-30021.herokuapp.com/home/reviews\">"
                    + "Reviews</a></li><li><a href=\"https://shrouded-shore-30021.herokuapp.com/home/users\">"
                    + "Users</a></li></ul></div></nav>\n" 
                    + "<b>SHOP LIST:</b>"
                    + "<br><br>"
                    + "<div id=\"googleMap\" style=\"width:100%;height:300px;\"></div>" 
                    + "<table cellpadding=10 border=1 id=\"shopTable\"><tr>"
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
                    + "<td>Capacity</td>"
                    + "<td>Wifi</td>"
                    + "<td>Volume</td>"
                    + "<td>Coffee Ranking</td>"
                    + "<td>Food Ranking</td>"
                    + "<td>Expense Ranking</td>"
                    + "</tr> "
        );
            
            for (int i = 0; i < shps.length; i++) {
                sb.append("<tr><td>"
                        + shps[i].getShopid() + "</td><td><a href=\"#\">"
                        + shps[i].getName() + "</a></td><td>"
                        + shps[i].getStreet() + "</td><td>"
                        + shps[i].getCity() + "</td><td>"
                        + shps[i].getState() + "</td><td>"
                        + shps[i].getZip() + "</td><td>"
                        + shps[i].getPhone() + "</td><td>"
                        + shps[i].getOpentime() + "</td><td>"
                        + shps[i].getClosetime() + "</td><td>"
                        + shps[i].getDescription() + "</td><td>"
                        + shps[i].getCapacity() + "</td><td>"
                        + shps[i].getWifi() + "</td><td>"
                        + shps[i].getVolume() + "</td><td>"
                        + shps[i].getCoffeeRank() + "</td><td>"
                        + shps[i].getFoodRank() + "</td><td>"
                        + shps[i].getExpenseRank() + "</td>"
                        + "<td><input type=\"button\" value=\"Delete " + shps[i].getName() + "\" onclick=\"\" id=\"delete-shop-" + shps[i].getShopid() + "\"/></td></tr>");
            }
            
        } catch (Exception e) {
            sb.append("</table><br>Error getting shops: " + e.toString() + "<br>");
        }
        
        
        sb.append("</table>");
        
        try {
            Model db = Model.singleton();
            Shop[] shps = db.getShops(0);
            
            
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
        Shop[] shp = mapper.readValue(jobj, Shop[].class);

        try {
            Model db = Model.singleton();
            int id = db.newShop(shp);
            logger.log(Level.INFO, "Shop persisted to db with ID: " + id);
        } catch (SQLException sqle) {
            String errText = "Error persisting shop after db connection made:\n" + sqle.getMessage() + " --- " + sqle.getSQLState() + "\n";
            logger.log(Level.SEVERE, errText);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error connecting to db.");
        }

        return "Completed";
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
    public String deleteShop(String jobj) throws IOException {
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
