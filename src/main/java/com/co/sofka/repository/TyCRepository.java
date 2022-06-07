package com.co.sofka.repository;

import com.co.sofka.entity.TyCEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TyCRepository implements ReactivePanacheMongoRepository<TyCEntity> {


}
