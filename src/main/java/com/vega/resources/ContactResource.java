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
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

@Path("/contacts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ContactResource {
    @Inject
    JsonWebToken jwt;
    @Inject
    ContactService service;
/*
    @GET
    public Response getAll(@QueryParam("sort") String sorts, String filters,
                           @QueryParam("page") @DefaultValue("0") int pageIndex,
                           @QueryParam("size") @DefaultValue("20") int pageSize){
        if (checkJwt()) {
            return Response.ok(service.getAll(sorts, filters, pageIndex, pageSize)).build();
        }
        return Response.status(401).build();
    }*/

    @GET
    @Path("{id}")
    public Response getOne(@PathParam("id") UUID id) {
        if (checkJwt()) {
            String userId = jwt.getClaim("sub");
            Contact contact = service.getByIdAndUserId(id, userId);
            if (contact == null) {
                return Response.status(404).build();
            }
            return Response.ok(contact).build();
        }
        return Response.status(401).build();
    }

    @Transactional
    @DELETE
    @Path("{id}")
    public Response deleteContactById(@PathParam("id") UUID id) {
        if (checkJwt()) {
            String userId = jwt.getClaim("sub");
            if (!service.deleteWithUserId(id, userId)) {
                return Response.status(404).build();
            }
        }
        return Response.status(401).build();
    }

    @POST
    @Transactional
    public Response createContact(Contact contactToSave) {
        if (checkJwt()) {
            Contact contact = service.add(contactToSave);
            return Response.ok(contact).build();
        }
        return Response.status(401).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Response edit(@PathParam("id") UUID id, Contact contactToSave) {
        if (checkJwt()) {
            String userId = jwt.getClaim("sub");
            if (service.getByIdAndUserId(id, userId) == null) {
                return Response.status(204).build();
            }
            Contact contact = service.update(id, contactToSave);
            return Response.ok(contact).build();
        }
        return Response.status(401).build();
    }

    private boolean checkJwt() {
        return jwt.containsClaim("sub") && jwt.getClaim("sub") != null;
    }
}
