/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sensorhub.mapper;

import com.sensorhub.exception.RoomNotFoundException;
import com.sensorhub.model.ErrorResponse;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Asus
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class RoomNotFoundExceptionMapper implements ExceptionMapper<RoomNotFoundException> {

    @Override
    public Response toResponse(RoomNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(ex.getMessage(), 404);

        return Response.status(Response.Status.NOT_FOUND)
                .entity(error)
                .build();
    }
}
