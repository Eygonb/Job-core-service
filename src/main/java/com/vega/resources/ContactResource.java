package com.vega.resources;

import com.vega.entities.Contact;
import com.vega.service.ContactService;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

@Path("/contacts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ContactResource {
    @Inject
    JsonWebToken jwt;
    @Inject
    ContactService service;

    @GET
    public Response getAllContacts(@QueryParam("sort") List<String> sortQuery,
                                   @QueryParam("page") @DefaultValue("0") int pageIndex,
                                   @QueryParam("size") @DefaultValue("20") int pageSize) {
        if (checkJwt()) {
            List<Contact> contacts = service.getAll(sortQuery, pageIndex, pageSize);
            return Response.ok(contacts).build();
        }
        return Response.status(401).build();
    }

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
    public Response deleteContactById(UUID id) {
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
    public Response edit(UUID id, Contact contactToSave) {
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
