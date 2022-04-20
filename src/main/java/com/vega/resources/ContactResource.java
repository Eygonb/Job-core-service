package com.vega.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vega.entities.Contact;
import com.vega.processing.Filter;
import com.vega.processing.Sorter;
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
    @Inject
    ObjectMapper objectMapper;

    @GET
    public Response getAll(@QueryParam("sort") @DefaultValue("[]") String sortParam,
                           @QueryParam("filter") @DefaultValue("[]") String filterParam,
                           @QueryParam("page") @DefaultValue("0") int pageIndex,
                           @QueryParam("size") @DefaultValue("20") int pageSize) throws JsonProcessingException {
      //  if (checkJwt()) {
         //   String userId = jwt.getClaim("sub");
            String userId = "quarkus.user";
            List<Sorter> sorts = objectMapper.readValue(sortParam, new TypeReference<>() {});
            List<Filter> filters = objectMapper.readValue(filterParam, new TypeReference<>() {});
            List<Contact> countList = service.getAll(sorts, filters,pageIndex,pageSize,userId);
            Long countVacancy = service.count(filters,userId);
            return Response.ok(countList).
                    header("X-Total-Count", countVacancy).build();
     //   }
      //  return Response.status(401).build();
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
       // if (checkJwt()) {
            //String userId = jwt.getClaim("sub");
            String userId = "quarkus.user";
            Contact contact = service.add(contactToSave, userId);
            return Response.ok(contact).build();
      //  }
      //  return Response.status(401).build();
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
