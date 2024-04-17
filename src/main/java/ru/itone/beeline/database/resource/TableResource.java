package ru.itone.beeline.database.resource;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.itone.beeline.database.entity.AppResponse;
import ru.itone.beeline.database.entity.ReqParam;
import ru.itone.beeline.database.service.DatabaseService;
import ru.itone.beeline.database.service.TableService;

@Path("/table")
public class TableResource {

    private static Logger log = LoggerFactory.getLogger(TableResource.class);

    private static final int DELETE_BATCH_SIZE = 1000;
    
    private static DatabaseService databaseService = new DatabaseService();
    private static TableService service = new TableService(databaseService);

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(ReqParam param) {
        log.info("DELETE request processing: {}", param);
        AppResponse result;
        try {
            Integer deletedRows = service.deleteRows(
                param.fromAsDateTime(), param.toAsDateTime(), DELETE_BATCH_SIZE);
            result = AppResponse.builder()
                .affectedRows(deletedRows)
                .build();
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            result = AppResponse.builder()
                .error(e.getMessage())
                .code(Integer.toString(e.getErrorCode()))
                .build();
        }
        log.info("DELETE request result: {}", result);
        return Response.ok().entity(result).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response put(ReqParam param) {
        log.info("PUT request processing: {}", param);
        AppResponse result;
        try {
            Integer insertedRows = service.insertRandomRows(param.getCount());
            result = AppResponse.builder()
                .affectedRows(insertedRows)
                .build();
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            result = AppResponse.builder()
                .error(e.getMessage())
                .code(Integer.toString(e.getErrorCode()))
                .build();
        }
        log.info("PUT request result: {}", result);
        return Response.ok().entity(result).build();
    }

}
