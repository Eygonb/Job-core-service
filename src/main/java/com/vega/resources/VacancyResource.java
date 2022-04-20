package com.vega.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vega.entities.Vacancy;
import com.vega.processing.Filter;
import com.vega.processing.Sorter;
import com.vega.service.VacancyService;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

@Path("/vacancies")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class VacancyResource {
    @Inject
    JsonWebToken jwt;
    @Inject
    VacancyService service;
    @Inject
    ObjectMapper objectMapper;

    @GET
    public Response getAll(@QueryParam("sort") @DefaultValue("[]") String sortParam,
                           @QueryParam("filter") @DefaultValue("[]") String filterParam,
                           @QueryParam("page") @DefaultValue("0") int pageIndex,
                           @QueryParam("size") @DefaultValue("20") int pageSize) throws JsonProcessingException {
        if (checkJwt()) {
            String userId = jwt.getClaim("sub");
          //  String userId = "quarkus.user";
            List<Sorter> sorts = objectMapper.readValue(sortParam, new TypeReference<>() {});
            List<Filter> filters = objectMapper.readValue(filterParam, new TypeReference<>() {});
            List<Vacancy> vacancyList = service.getAll(sorts, filters,pageIndex,pageSize,userId);
            Long countVacancy = service.count(filters,userId);
            return Response.ok(vacancyList).
                    header("X-Total-Count", countVacancy).build();
        }
        return Response.status(401).build();
    }

    @GET
    @Path("{id}")
    public Response getOne(@PathParam("id") UUID id) {
        if (checkJwt()) {
            String userId = jwt.getClaim("sub");
            Vacancy vacancy = service.getByIdAndUserId(id, userId);
            if (vacancy == null) {
                return Response.status(404).build();
            }
            return Response.ok(vacancy).build();
        }
        return Response.status(401).build();
    }

    @Transactional
    @DELETE
    @Path("{id}")
    public Response deleteVacancyById(@PathParam("id")  UUID id) {
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
    public Response createVacancy(Vacancy vacancyToSave) {
        if (checkJwt()) {
            String userId = jwt.getClaim("sub");
            Vacancy vacancy = service.add(vacancyToSave, userId);
            return Response.ok(vacancy).build();
        }
        return Response.status(401).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Response edit(@PathParam("id")  UUID id, Vacancy vacancyToSave) {
        if (checkJwt()) {
            String userId = jwt.getClaim("sub");
            if (service.getByIdAndUserId(id, userId) == null) {
                return Response.status(204).build();
            }
            Vacancy vacancy = service.update(id, vacancyToSave);
            return Response.ok(vacancy).build();
        }
        return Response.status(401).build();
    }

    private boolean checkJwt() {
        return jwt.containsClaim("sub") && jwt.getClaim("sub") != null;
    }
}















