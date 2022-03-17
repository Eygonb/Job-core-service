package com.vega.resources;

import com.vega.entities.Contact;
import com.vega.entities.Vacancy;
import com.vega.processing.Filter;
import com.vega.processing.Sorter;
import com.vega.repositories.ContactRepository;
import com.vega.service.ContactService;
import com.vega.service.VacancyService;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import io.quarkus.security.identity.SecurityIdentity;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public class ContactResource {

    @Inject
    ContactService service;

    @GET()
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@QueryParam("sort") List<Sorter> sorts, List<Filter> filters,
                           @QueryParam("page") @DefaultValue("0") int pageIndex,
                           @QueryParam("size") @DefaultValue("20") int pageSize){
        return Response.ok(service.getAll(sorts, filters,pageIndex,pageSize)).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") UUID id){
        return Response.ok(service.get(id)).build();
    }

    @Transactional
    @DELETE
    public void delete(UUID id) {
        service.delete(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response create(Contact contact){
        return Response.ok(service.add(contact)).build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response edit(@PathParam("id") UUID id, Contact contact){
        return Response.ok(service.update(id,contact)).build();
    }
}
