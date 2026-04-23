/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sensorhub;

import com.sensorhub.dao.MockDatabase;
import com.sensorhub.exception.LinkedResourceNotFoundException;
import com.sensorhub.model.Room;
import com.sensorhub.model.Sensor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Asus
 */

@Path("/sensors")
public class SensorResource {

    // GET all sensors
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Sensor> getAllSensors(@QueryParam("type") String type) {

        //if not filtered show all
        if (type == null || type.isEmpty()) {
            return MockDatabase.SENSORS.values();
        }

        List<Sensor> filtered = new ArrayList<>();

        for (Sensor sensor : MockDatabase.SENSORS.values()) {
            if (sensor.getType() != null
                    && sensor.getType().equalsIgnoreCase(type)) {
                filtered.add(sensor);
            }
        }

        return filtered;
    }

    // GET sensor by ID
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Sensor getSensorById(@PathParam("id") String id) {
        return MockDatabase.SENSORS.get(id);
    }

    // CREATE sensor
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Sensor createSensor(Sensor sensor) {

        // check if room exists
        Room room = MockDatabase.ROOMS.get(sensor.getRoomId());

        // ❗ if room does NOT exist → throw custom error
        if (room == null) {
            throw new LinkedResourceNotFoundException("Room does not exist");
        }

        // save sensor
        MockDatabase.SENSORS.put(sensor.getId(), sensor);

        // link sensor to room
        room.getSensorIds().add(sensor.getId());

        return sensor;
    }
    
    //sub-resource locator
    @Path("/{id}/readings")
    public SensorReadingResource getSensorReadingResource(@PathParam("id") String id) {
        
        //pass sensor ID to the next class
        return new SensorReadingResource(id);
    }

    
}
