package com.vega.resources;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vega.entities.Status;
import com.vega.processing.Filter;
import com.vega.processing.Sorter;
import com.vega.service.StatusService;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

@Path("/statuses")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StatusResource {
    @Inject
    JsonWebToken jwt;
    @Inject
    StatusService service;
    @Inject
    ObjectMapper objectMapper;

    @GET
    public Response getAll(@QueryParam("sort") @DefaultValue("[]") String sortParam,
                           @QueryParam("filter") @DefaultValue("[]") String filterParam,
                           @QueryParam("page") @DefaultValue("0") int pageIndex,
                           @QueryParam("size") @DefaultValue("20") int pageSize) throws JsonProcessingException {
        if (checkJwt()) {
            String userId = jwt.getClaim("sub");
            List<Sorter> sorts = objectMapper.readValue(sortParam, new TypeReference<>() {});
            List<Filter> filters = objectMapper.readValue(filterParam, new TypeReference<>() {});
            List<Status> statusList = service.getAll(sorts, filters, pageIndex, pageSize, userId);
            Long countVacancy = service.count(filters, userId);
            return Response.ok(statusList).
                    header("X-Total-Count", countVacancy).build();
        }
        return Response.status(401).build();
    }


    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") UUID id) {
        if (checkJwt()) {
            String userId = jwt.getClaim("sub");
            Status status = service.getByIdAndUserId(id, userId);
            if (status == null) {
                return Response.status(404).build();
            }
            return Response.ok(status).build();
        }
        return Response.status(401).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") UUID id) {
        if (checkJwt()) {
            String userId = jwt.getClaim("sub");
            if (!service.deleteWithUserId(id, userId)) {
                return Response.status(404).build();
            }
            return Response.status(200).build();
        }
        return Response.status(401).build();
    }

    @POST
    @Transactional
    public Response createStatus(Status statusToSave) {
        if (checkJwt()) {
            String userId = jwt.getClaim("sub");

            Status savedStatus = service.add(statusToSave, userId);
            return Response.ok(savedStatus).build();
        }
        return Response.status(401).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response edit(@PathParam("id") UUID id, Status statusToSave) {
        if (checkJwt()) {
            String userId = jwt.getClaim("sub");
            statusToSave.setUserId(userId);
            if (service.get(id) == null) {
                return Response.status(204).build();
            }
            Status status = service.update(id, statusToSave);
            return Response.ok(status).build();
        }
        return Response.status(401).build();
    }

    @PUT
    @Transactional
    public Response editAll(List<Status> statuses) {
        if (checkJwt()) {
            String userId = jwt.getClaim("sub");
            List<Status> changedStatus = service.updateAll(statuses, userId);
            return Response.ok(changedStatus).build();
        }
        return Response.status(401).build();
    }

    private boolean checkJwt() {
        return jwt.containsClaim("sub") && jwt.getClaim("sub") != null;
    }
}

