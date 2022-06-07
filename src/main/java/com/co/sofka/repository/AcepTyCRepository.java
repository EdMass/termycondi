package com.co.sofka.repository;

import com.co.sofka.entity.AcepTyCEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AcepTyCRepository implements ReactivePanacheMongoRepository<AcepTyCEntity> {

}
