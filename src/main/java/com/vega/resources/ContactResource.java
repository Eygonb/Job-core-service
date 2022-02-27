package com.vega.resources;

import com.vega.entities.Contact;
import com.vega.repositories.ContactRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

public class ContactResource {

    @Inject
    ContactRepository contactRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllContacts(@QueryParam("sort") List<String> sortQuery,
                                  @QueryParam("page") @DefaultValue("0") int pageIndex,
                                  @QueryParam("size") @DefaultValue("20") int pageSize){
        Page page = Page.of(pageIndex, pageSize);
        //  Sort sort = getSortFromQuery(sortQuery);
        List<Contact> contacts = contactRepository.findAll().page(page).list();
        return Response.ok(contacts).build();

    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOne(@PathParam("id") UUID id){
        Contact contact = contactRepository.getContact(id);
        if (contact == null) {
            throw new WebApplicationException(404);
        }
        return Response.ok(contact).build();
    }

    @Transactional
    @DELETE
    @Path("{id}")
    public void deleteContactById(UUID id) {
        if (!contactRepository.deleteContact(id)) {
            throw new WebApplicationException(404);
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response createContact(Contact contactToSave){
        Contact contact = contactRepository.addContact(contactToSave);
        //return  Response.status(401).build();
        return Response.ok(contact).build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response edit(UUID id, Contact contactToSave){
        if(contactRepository.getContact(id)==null)
        {
            return Response.status(204).build();
        }
        Contact contact = contactRepository.editContact(id,contactToSave);
        //return  Response.status(401).build();
        return Response.ok(contact).build();

    }
}
