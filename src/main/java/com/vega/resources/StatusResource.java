package com.vega.resources;

import com.vega.entities.Status;
import com.vega.service.StatusService;
import org.eclipse.microprofile.jwt.JsonWebToken;

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

    //@GET
    //public Response getAllStatuses(@QueryParam("sort") List<String> sortQuery,
    //                               @QueryParam("page") @DefaultValue("0") int pageIndex,
    //                               @QueryParam("size") @DefaultValue("20") int pageSize) {
    //    if (checkJwt()) {
    //        List<Status> statuses = service.getAll(sortQuery, pageIndex, pageSize);
    //        return Response.ok(statuses).build();
    //    }
    //    return Response.status(401).build();
    //}
//
    //@GET
    //@Path("{id}")
    //public Response getOne(@PathParam("id") Status.StatusKey id) {
    //    if (checkJwt()) {
    //        String userId = jwt.getClaim("sub");
    //        Status status = service.getByIdAndUserId(id, userId);
    //        if (status == null) {
    //            return Response.status(404).build();
    //        }
    //        return Response.ok(status).build();
    //    }
    //    return Response.status(401).build();
    //}
//
    //@Transactional
    //@DELETE
    //@Path("{id}")
    //public Response deleteStatusById(Status.StatusKey id) {
    //    if (checkJwt()) {
    //        String userId = jwt.getClaim("sub");
    //        if (!service.deleteWithUserId(id, userId)) {
    //            return Response.status(404).build();
    //        }
    //    }
    //    return Response.status(401).build();
    //}
//
    @POST
    @Transactional
    public Response createStatus(Status statusToSave) {
        if (checkJwt()) {
            Status status = service.add(statusToSave);
            return Response.ok(status).build();
        }
        return Response.status(401).build();
    }
//
    //@PUT
    //@Path("{id}")
    //@Transactional
    //public Response edit(Status.StatusKey id, Status statusToSave) {
    //    if (checkJwt()) {
    //        String userId = jwt.getClaim("sub");
    //        if (service.getByIdAndUserId(id, userId) == null) {
    //            return Response.status(204).build();
    //        }
    //        Status status = service.update(id, statusToSave);
    //        return Response.ok(status).build();
    //    }
    //    return Response.status(401).build();
    //}
//
    private boolean checkJwt() {
        return jwt.containsClaim("sub") && jwt.getClaim("sub") != null;
    }
}
