package com.vega.resources;


import com.vega.entities.Status;
import com.vega.processing.Filter;
import com.vega.processing.Sorter;
import com.vega.service.StatusService;
import org.eclipse.microprofile.jwt.JsonWebToken;
import io.quarkus.security.identity.SecurityIdentity;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/statuses")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StatusResource {
    @Inject
    JsonWebToken jwt;
    @Inject
    StatusService service;
    Status.StatusKey key;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@QueryParam("sort") List<Sorter> sorts, List<Filter> filters,
                           @QueryParam("page") @DefaultValue("0") int pageIndex,
                           @QueryParam("size") @DefaultValue("20") int pageSize){
        if (checkJwt()) {
            return Response.ok(service.getAll(sorts, filters,pageIndex,pageSize)).build();
        }
        return Response.status(401).build();
    }

    @GET
    @Path("{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("name") String name){
        if (checkJwt()) {
            key.setNameStatus(name);
            key.setUserId(SecurityIdentity.USER_ATTRIBUTE);

            String userId = jwt.getClaim("sub");
            Status status = service.getByIdAndUserId(key, userId);
            if (status == null) {
                return Response.status(404).build();
            }
            return Response.ok(status).build();
        }
        return Response.status(401).build();
    }

    @Transactional
    @Path("{name}")
    @DELETE
    public Response deleteStatusByKey(@PathParam("name") String name) {
        if (checkJwt()) {
            String userId = jwt.getClaim("sub");
            key.setNameStatus(name);
            key.setUserId(SecurityIdentity.USER_ATTRIBUTE);
            if (!service.deleteWithUserId(key, userId)) {
                return Response.status(404).build();
            }
        }
        return Response.status(401).build();
    }

    @POST
    @Transactional
    public Response createStatus(Status statusToSave) {
        if (checkJwt()) {
            Status status = service.add(statusToSave);
            return Response.ok(status).build();
        }
        return Response.status(401).build();
    }

    @PUT
    @Path("{name}")
    @Transactional
    public Response edit(@PathParam("name") String name, Status statusToSave){
        if (checkJwt()) {
            key.setNameStatus(name);
            key.setUserId(SecurityIdentity.USER_ATTRIBUTE);
            String userId = jwt.getClaim("sub");
            if (service.get(key) == null) {
                return Response.status(204).build();
            }
            Status status = service.update(key, statusToSave);
            return Response.ok(status).build();
        }
        return Response.status(401).build();
    }

    private boolean checkJwt() {
        return jwt.containsClaim("sub") && jwt.getClaim("sub") != null;
    }
}

