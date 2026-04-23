/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sensorhub;

/**
 *
 * @author Asus
 */

import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


//@Path tells the server WHEN to use this class
@Path ("/")

public class DiscoveryResource {
    
    //this method runs when someone sends a GET request
    @GET
    
    //returns the result as json
    @Produces(MediaType.APPLICATION_JSON)
    
    
    public Map<String, Object> getApiInfo(){
        
        Map<String, Object> response = new HashMap<>();
        
        
        response.put("name", "Sensor Hub");
        response.put("version", "v1");
        response.put("contact", "amovinka@gmail.com");
        
        
        //putting data into the response
         Map<String, String> resources = new HashMap<>();
         
         resources.put("rooms", "/api/v1/rooms");
         resources.put("sensors" , "/api/v1/sensors");
         resources.put("sensorReadings" , "api/v1/sensors/{id}/readings");
         
         
         response.put("resources", resources);
         
        return response;
    
    }    
}
