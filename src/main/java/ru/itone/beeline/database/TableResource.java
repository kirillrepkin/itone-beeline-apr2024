package ru.itone.beeline.database;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/table")
public class TableResource {

    @GET
    @Path("/list")
    public Response list() {
        return Response.ok().entity("OK").build();
    }

}
