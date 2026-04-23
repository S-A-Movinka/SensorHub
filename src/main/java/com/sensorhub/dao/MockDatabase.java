/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sensorhub.dao;

import com.sensorhub.model.Room;
import com.sensorhub.model.Sensor;
import com.sensorhub.model.SensorReading;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Asus
 */
public class MockDatabase {
    
    public static final Map<String, Room> ROOMS=new HashMap<>();
    
    public static final Map<String, Sensor>SENSORS=new HashMap<>();
    
    public static final Map<String, List<SensorReading>>READINGS=new HashMap<>();

    static{
        ROOMS.put("R1", new Room("R1","Library",100));
        ROOMS.put("R2", new Room("R2","Staff Room",40));
        SENSORS.put("S1",new Sensor("S1","CO2","ACTIVE",400.0,"R1"));
        ROOMS.get("R1").getSensorIds().add("S1");
    }

    private MockDatabase(){
        
    }
}
