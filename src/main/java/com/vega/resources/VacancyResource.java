package com.vega.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vega.entities.Vacancy;
import com.vega.processing.Filter;
import com.vega.processing.Sorter;
import com.vega.service.VacancyService;
import io.quarkus.vertx.web.Header;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Path("/vcs")
public class VacancyResource {

    @Inject
    VacancyService service;

    @Inject
    ObjectMapper objectMapper;

    @GET
    @Path("/all")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@QueryParam("sort") String sortParam, @QueryParam("filter") String filterParam,
                           @QueryParam("page") @DefaultValue("0") int pageIndex,
                           @QueryParam("size") @DefaultValue("20") int pageSize) throws JsonProcessingException {
        List<Sorter> sorts = objectMapper.readValue(sortParam, new TypeReference<>() {});
        List<Filter> filters = objectMapper.readValue(filterParam, new TypeReference<>() {});
        int countVacancy = service.count(sorts, filters);
        return Response.ok(service.getAll(sorts, filters,pageIndex,pageSize)).
                header("CountVacancy", countVacancy).build();
    }

    @GET
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") UUID id){
        return Response.ok(service.get(id)).build();
    }

    @Transactional
    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void delete(@PathParam("id") UUID id) {
       service.delete(id);
    }

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response create(Vacancy vacancy){
        return Response.ok(service.add(vacancy)).build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response edit(@PathParam("id") UUID id, Vacancy vacancy){
        return Response.ok(service.update(id,vacancy)).build();
    }
}














