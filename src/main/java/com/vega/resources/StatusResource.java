package com.vega.resources;


import com.vega.entities.Contact;
import com.vega.entities.Status;
import com.vega.entities.Vacancy;
import com.vega.processing.Filter;
import com.vega.processing.Sorter;
import com.vega.repositories.StatusRepository;
import com.vega.service.StatusService;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import io.quarkus.security.identity.SecurityIdentity;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public class StatusResource {


    @Inject
    StatusService service;
    Status.StatusKey key;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@QueryParam("sort") List<Sorter> sorts, List<Filter> filters,
                           @QueryParam("page") @DefaultValue("0") int pageIndex,
                           @QueryParam("size") @DefaultValue("20") int pageSize){
        return Response.ok(service.getAll(sorts, filters,pageIndex,pageSize)).build();
    }

    @GET
    @Path("{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("name") String name){
        key.setNameStatus(name);
        key.setUserId(SecurityIdentity.USER_ATTRIBUTE);
        return  Response.ok(service.get(key)).build();
    }

    @Transactional
    @DELETE
    public void deleteStatusByKey(String name) {
        key.setNameStatus(name);
        key.setUserId(SecurityIdentity.USER_ATTRIBUTE);
        if (!service.delete(key)) {
            throw new WebApplicationException(404);
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response createStatus(Status status){
        return Response.ok(service.add(status)).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response edit(String name, Status status){
        key.setNameStatus(name);
        key.setUserId(SecurityIdentity.USER_ATTRIBUTE);
        return Response.ok(service.update(key,status)).build();

    }
}
