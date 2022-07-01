package com.co.sofka.services;

import com.co.sofka.entity.TyCEntity;
import com.co.sofka.repository.TyCRepository;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class TyCServices {

    @Inject
    TyCRepository repository;

    public Uni<Object> getLastVersion(){
        var uni = repository.findAll().list().map(ver -> ver.get(ver.size()-1));
        return repository.findAll().list().map(ver -> ver.get(ver.size()-1));
    }

    public Uni<TyCEntity> addTyC(TyCEntity tyCEntity){
        return repository.persist(tyCEntity);
    }

    public Uni<List<TyCEntity>> getAllTyC(){
        return repository.findAll().list();
    }

    public Uni<TyCEntity> findVersion(Integer version){
        return repository.
                find("version", version).firstResult();
    }

}
