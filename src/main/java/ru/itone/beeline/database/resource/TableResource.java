package ru.itone.beeline.database.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.itone.beeline.database.entity.ReqParam;
import ru.itone.beeline.database.service.TableService;

@Path("/table")
public class TableResource {

    private static Logger log = LoggerFactory.getLogger(TableResource.class);
    private static TableService service = new TableService();

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(ReqParam param) {
        log.info("DELETE request processing: {}", param);
        service.deleteRows(null, null, null);
        return Response.ok().entity("OK").build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response put(ReqParam param) {
        log.info("PUT request processing: {}", param);
        service.insertRows(null);
        return Response.ok().entity("OK").build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response post(ReqParam param) {
        log.info("POST request processing: {}", param);
        service.updateRows(null);
        return Response.ok().entity("OK").build();
    }

}
