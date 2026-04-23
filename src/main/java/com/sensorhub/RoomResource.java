/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sensorhub;

import com.sensorhub.dao.MockDatabase;
import com.sensorhub.exception.RoomNotEmptyException;
import com.sensorhub.exception.RoomNotFoundException;
import com.sensorhub.model.Room;
import java.util.Collection;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Asus
 */

//@Path tells the server WHEN to use this class
@Path("/rooms")
public class RoomResource {

    //Get all rooms
    @GET
    //returns the result as json
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Room> getAllRooms() {
        return MockDatabase.ROOMS.values();
    }

    //GET room by ID
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Room getRoom(@PathParam("id")String id){
        return MockDatabase.ROOMS.get(id);
    }
    
    
    //CREATE  a new room
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Room createRoom(Room room) {
        MockDatabase.ROOMS.put(room.getId(), room);
        return room;
    }
    
    //DELETE a room
    @DELETE
    @Path("/{id}")
    public void deleteRoom(@PathParam("id")String id){
        
        //get the room form DB
        Room room=MockDatabase.ROOMS.get(id);
        
       //if room does not exist throw and error
       if (room==null){
           throw new RoomNotFoundException("Room not found");
       }
        
       //check if room has sensors
       if(room.getSensorIds()!=null && !room.getSensorIds().isEmpty()){
        
        throw new RoomNotEmptyException("Room has sensors, cannot delete");
       }
    
       
      //delete the room  
     MockDatabase.ROOMS.remove(id);
    } 
 }

