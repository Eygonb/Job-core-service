package com.vega.resources;

import com.vega.entities.Vacancy;
import com.vega.repositories.VacancyRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

public class VacancyResource {

    @Inject
    VacancyRepository vacancyRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllVacancy(@QueryParam("sort") List<String> sortQuery,
                                  @QueryParam("page") @DefaultValue("0") int pageIndex,
                                  @QueryParam("size") @DefaultValue("20") int pageSize){
        Page page = Page.of(pageIndex, pageSize);
      //  Sort sort = getSortFromQuery(sortQuery);
        List<Vacancy> vacancy = vacancyRepository.findAll().page(page).list();
        return Response.ok(vacancy).build();

    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOne(@PathParam("id") UUID id){
        Vacancy vacancy = vacancyRepository.getVacancy(id);
        if (vacancy == null) {
            throw new WebApplicationException(404);
        }
        return Response.ok(vacancy).build();
    }

    @Transactional
    @DELETE
    @Path("{id}")
    public void deleteVacancyById(UUID id) {
        if (!vacancyRepository.deleteVacancy(id)) {
            throw new WebApplicationException(404);
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response createVacancy(Vacancy vacancyToSave){
        Vacancy vacancy = vacancyRepository.addVacancy(vacancyToSave);
        //return  Response.status(401).build();
        return Response.ok(vacancy).build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response edit(UUID id, Vacancy vacancyToSave){
        if(vacancyRepository.getVacancy(id)==null)
        {
          //  Vacancy vacancy = vacancyRepository.editVacancy(id, vacancyToSave);
            return Response.status(204).build();
        }
        Vacancy vacancy = vacancyRepository.editVacancy(id,vacancyToSave);
        //return  Response.status(401).build();
        return Response.ok(vacancy).build();

    }
}
