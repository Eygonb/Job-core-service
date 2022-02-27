package com.vega.started;


import javax.inject.*;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.vega.repositories.ContactRepository;
import com.vega.repositories.EventRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import com.vega.entities.Contact;
import com.vega.entities.Event;
import com.vega.entities.Status;
import com.vega.entities.Vacancy;
import com.vega.repositories.StatusRepository;
import com.vega.repositories.VacancyRepository;
import org.flywaydb.core.Flyway;

@Path("/hello")
public class RestAPI {


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello baby!";
    }
    // You can Inject the object if you want to use it manually
    @Inject
    Flyway flyway;

    public void checkMigration() {
        System.out.println(flyway.info().current().getVersion().toString());
       
     }
}