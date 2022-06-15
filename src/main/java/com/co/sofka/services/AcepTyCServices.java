package com.co.sofka.services;

import com.co.sofka.dto.AcepTyCDto;
import com.co.sofka.entity.AcepTyCEntity;
import com.co.sofka.repository.AcepTyCRepository;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.jetbrains.annotations.NotNull;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static javax.ws.rs.core.Response.Status.SERVICE_UNAVAILABLE;
import static javax.ws.rs.core.Response.Status.NOT_ACCEPTABLE;

@ApplicationScoped
public class AcepTyCServices {

    @Inject
    AcepTyCRepository acepTyCRepository;

    private final LocalDateTime dateTime = LocalDateTime.now(ZoneId.of("Etc/UTC") );

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

    @Fallback(fallbackMethod = "createFallbackMethod")
    @Retry(maxRetries = 4)
    public Uni<Response> createAceptTyC(@NotNull AcepTyCDto acepTyCDto){
        String TIPO_CEDULA = "Cedula";
        String TIPO_PASSPORT = "Passport";
        if(acepTyCDto.getTipoDocumento().equals(TIPO_CEDULA)){
            AcepTyCEntity acepTyCEntity = new AcepTyCEntity(acepTyCDto.getTipoDocumento(),
                    acepTyCDto.getDocumento(), acepTyCDto.getVersionAcep(), dateTime);
            return addCedulaAceptTyC(acepTyCEntity).map(accept -> Response.ok(accept).build());
        } else if (acepTyCDto.getTipoDocumento().equals(TIPO_PASSPORT)) {
                AcepTyCEntity acepTyCEntity = new AcepTyCEntity(acepTyCDto.getTipoDocumento(),
                        acepTyCDto.getDocumento(), acepTyCDto.getVersionAcep(), dateTime);
                return addPassportAceptTyC(acepTyCEntity).map(accept -> Response.ok(accept).build());
        } else {
                return Uni.createFrom().item(Response.status(NOT_ACCEPTABLE).build());
        }
    }

    public Uni<Response> createFallbackMethod(){
        return Uni.createFrom().item(Response.status(SERVICE_UNAVAILABLE).build());
    }

}
