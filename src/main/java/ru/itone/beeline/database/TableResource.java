package ru.itone.beeline.database;

import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/table")
public class TableResource {

    private static Logger log = LoggerFactory.getLogger(TableResource.class);
    private static TableService service = new TableService();

    @DELETE
    public Response delete() {
        log.info("DELETE request processing");
        service.deleteRows(null, null, null);
        return Response.ok().entity("OK").build();
    }

    @PUT
    public Response put() {
        log.info("PUT request processing");
        service.insertRows(null);
        return Response.ok().entity("OK").build();
    }

    @POST
    public Response post() {
        log.info("POST request processing");
        service.updateRows(null);
        return Response.ok().entity("OK").build();
    }

}
