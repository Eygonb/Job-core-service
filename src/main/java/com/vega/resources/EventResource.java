package com.vega.resources;

import com.vega.entities.Event;
import com.vega.repositories.EventRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

public class EventResource {


    @Inject
    EventRepository eventRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllEvents(@QueryParam("sort") List<String> sortQuery,
                                   @QueryParam("page") @DefaultValue("0") int pageIndex,
                                   @QueryParam("size") @DefaultValue("20") int pageSize){
        Page page = Page.of(pageIndex, pageSize);
        //  Sort sort = getSortFromQuery(sortQuery);
        List<Event> events = eventRepository.findAll().page(page).list();
        return Response.ok(events).build();

    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOne(@PathParam("id") UUID id){
        Event event = eventRepository.getEvent(id);
        if (event == null) {
            throw new WebApplicationException(404);
        }
        return Response.ok(event).build();
    }

    @Transactional
    @DELETE
    @Path("{id}")
    public void deleteEventById(UUID id) {
        if (!eventRepository.deleteEvent(id)) {
            throw new WebApplicationException(404);
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response createEvent(Event eventToSave){
        Event event = eventRepository.addEvent(eventToSave);
        //return  Response.status(401).build();
        return Response.ok(event).build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response edit(UUID id, Event eventToSave){
        if(eventRepository.getEvent(id)==null)
        {
            return Response.status(204).build();
        }
        Event event = eventRepository.editEvent(id, eventToSave);
        //return  Response.status(401).build();
        return Response.ok(event).build();

    }
}
