package com.vega.resources;


import com.vega.entities.Status;
import com.vega.repositories.StatusRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

public class StatusResource {


    @Inject
    StatusRepository statusRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllStatuses(@QueryParam("sort") List<String> sortQuery,
                                 @QueryParam("page") @DefaultValue("0") int pageIndex,
                                 @QueryParam("size") @DefaultValue("20") int pageSize){
        Page page = Page.of(pageIndex, pageSize);
        //  Sort sort = getSortFromQuery(sortQuery);
        List<Status> statuses = statusRepository.findAll().page(page).list();
        return Response.ok(statuses).build();

    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOne(@PathParam("id")Status.StatusKey key){
        Status status = statusRepository.getStatus(key);
        if (status == null) {
            throw new WebApplicationException(404);
        }
        return  Response.ok(status).build();
      //  return status;
    }

    @Transactional
    @DELETE
    @Path("{id}")
    public void deleteStatusByKey(Status.StatusKey key) {
        if (!statusRepository.deleteStatus(key)) {
            throw new WebApplicationException(404);
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response createStatus(Status statusToSave){
        Status status = statusRepository.addStatus(statusToSave);
        //return  Response.status(401).build();
        return Response.ok(status).build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response edit(Status.StatusKey key, Status statusToSave){
        if(statusRepository.getStatus(key)==null)
        {
            return Response.status(204).build();
        }
        Status status = statusRepository.editStatus(key, statusToSave);
        //return  Response.status(401).build();
        return Response.ok(status).build();

    }
}
