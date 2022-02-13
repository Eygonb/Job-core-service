package com.vacancinated.started;


import javax.enterprise.context.ApplicationScoped;
import javax.inject.*;

import org.flywaydb.core.Flyway;


@ApplicationScoped
public class MigrationService {
    // You can Inject the object if you want to use it manually
    @Inject
    Flyway flyway; 

    public void checkMigration() {
        System.out.println(flyway.info().current().getVersion().toString());
       
    }
}