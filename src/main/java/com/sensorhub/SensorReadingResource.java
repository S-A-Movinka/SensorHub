/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sensorhub;

import com.sensorhub.dao.MockDatabase;
import com.sensorhub.exception.SensorNotFoundException;
import com.sensorhub.exception.SensorUnavailableException;
import com.sensorhub.model.Sensor;
import com.sensorhub.model.SensorReading;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Asus
 */
public class SensorReadingResource {

    private String sensorId;

    // constructor receives sensor ID from URL
    public SensorReadingResource(String sensorId) {
        this.sensorId = sensorId;
    }

    // GET all readings for this sensor
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<SensorReading> getReadings() {

        // check if sensor exists
        if (!MockDatabase.SENSORS.containsKey(sensorId)) {
            throw new SensorNotFoundException("Sensor not found");
        }

        // return readings or empty list
        return MockDatabase.READINGS.getOrDefault(sensorId, new ArrayList<>());
    }

    // ADD new reading
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public SensorReading addReading(SensorReading reading) {

        // check sensor exists
        if (!MockDatabase.SENSORS.containsKey(sensorId)) {
            throw new SensorNotFoundException("Sensor not found");
        }

        Sensor sensor = MockDatabase.SENSORS.get(sensorId);

        //cannot add reading if sensor in maintenance
        if ("MAINTENANCE".equalsIgnoreCase(sensor.getStatus())) {
            throw new SensorUnavailableException("Sensor in maintenance");
        }

        // add reading to history
        MockDatabase.READINGS
                .computeIfAbsent(sensorId, k -> new ArrayList<>())
                .add(reading);

        // update current value on parent sensor
        sensor.setCurrentValue(reading.getValue());

        return reading;
    }
}
