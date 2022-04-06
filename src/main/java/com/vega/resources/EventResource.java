package com.vega.resources;

import com.vega.entities.Event;
import com.vega.service.EventService;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

@Path("/events")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EventResource {
    @Inject
    JsonWebToken jwt;
    @Inject
    EventService service;

    @GET
    public Response getAllEvents(@QueryParam("sort") List<String> sortQuery,
                                 @QueryParam("page") @DefaultValue("0") int pageIndex,
                                 @QueryParam("size") @DefaultValue("20") int pageSize) {
        if (checkJwt()) {
            List<Event> events = service.getAll(sortQuery, pageIndex, pageSize);
            return Response.ok(events).build();
        }
        return Response.status(401).build();
    }

    @GET
    @Path("{id}")
    public Response getOne(@PathParam("id") UUID id) {
        if (checkJwt()) {
            String userId = jwt.getClaim("sub");
            Event event = service.getByIdAndUserId(id, userId);
            if (event == null) {
                return Response.status(404).build();
            }
            return Response.ok(event).build();
        }
        return Response.status(401).build();
    }

    @Transactional
    @DELETE
    @Path("{id}")
    public Response deleteEventById(UUID id) {
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
    public Response createEvent(Event eventToSave) {
        if (checkJwt()) {
            Event event = service.add(eventToSave);
            return Response.ok(event).build();
        }
        return Response.status(401).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Response edit(UUID id, Event eventToSave) {
        if (checkJwt()) {
            String userId = jwt.getClaim("sub");
            if (service.getByIdAndUserId(id, userId) == null) {
                return Response.status(204).build();
            }
            Event event = service.update(id, eventToSave);
            return Response.ok(event).build();
        }
        return Response.status(401).build();
    }

    @GET
    @Path("/user")
    public Response getByUserId() {
        if (checkJwt()) {
            String userId = jwt.getClaim("sub");
            List<Event> events = service.getByUserId(userId);
            if (!events.isEmpty()) {
                return Response.ok(events).build();
            }
            return Response.status(404).build();
        }
        return Response.status(401).build();
    }

    private boolean checkJwt() {
        return jwt.containsClaim("sub") && jwt.getClaim("sub") != null;
    }
}
