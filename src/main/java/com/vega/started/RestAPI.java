package com.vega.started;


import javax.inject.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
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