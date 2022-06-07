package com.co.sofka.services;

import com.co.sofka.entity.AcepTyCEntity;
import com.co.sofka.repository.AcepTyCRepository;
import io.smallrye.mutiny.Uni;
import org.jetbrains.annotations.NotNull;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ApplicationScoped
public class AcepTyCServices {

    @Inject
    AcepTyCRepository acepTyCRepository;

    public boolean validationCedula(String document){
        Pattern pattern = Pattern.compile("[0-9]{2}-[PN]{2}-[0-9]{3}-[0-9]{4}");
        Matcher matcher = pattern.matcher(document);
        return matcher.matches();
    }

    public boolean validationPassport(String document){
        Pattern pattern = Pattern.compile("[^ñÑ][a-zA-Z0-9-]{5,16}");
        Matcher matcher = pattern.matcher(document);
        return matcher.matches();
    }

    public Uni<AcepTyCEntity> addPassportAceptTyC(@NotNull AcepTyCEntity acepTyCEntity){
        if(validationPassport(acepTyCEntity.getDocumento())){
            return acepTyCRepository.persist(acepTyCEntity);
        }
        throw new IllegalArgumentException();
    }


    public Uni<AcepTyCEntity> addCedulaAceptTyC(@NotNull AcepTyCEntity acepTyCEntity){
        if(validationCedula(acepTyCEntity.getDocumento())){
            return acepTyCRepository.persist(acepTyCEntity);
        }
        throw new IllegalArgumentException();
    }

}
