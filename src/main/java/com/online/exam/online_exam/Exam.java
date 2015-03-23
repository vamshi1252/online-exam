package com.online.exam.online_exam;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;


@Path("/start")
@Service
@Configurable
public class Exam {
	
    @GET
    public Response responseMsg() {
       String output = "Pinged";

        return Response.status(200).entity(output).build();
    }
	

}
