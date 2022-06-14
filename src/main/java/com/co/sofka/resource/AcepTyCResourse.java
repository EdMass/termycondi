package com.co.sofka.resource;

import com.co.sofka.dto.AcepTyCDto;
import com.co.sofka.entity.AcepTyCEntity;
import com.co.sofka.services.AcepTyCServices;
import io.smallrye.mutiny.Uni;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.NOT_ACCEPTABLE;

@Path("/terms")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AcepTyCResourse {

    @Inject
    AcepTyCServices acepTyCServices;

    private final LocalDateTime dateTime = LocalDateTime.now(ZoneId.of("Etc/UTC") );

    @POST
    @Path("/aceptar")
    @Produces(APPLICATION_JSON)
    public Uni<Response> createAceptTyC(@NotNull AcepTyCDto acepTyCDto){
        if(acepTyCDto.getTipoDocumento().equals("Cedula")){
            AcepTyCEntity acepTyCEntity = new AcepTyCEntity(acepTyCDto.getTipoDocumento(),
                    acepTyCDto.getDocumento(), acepTyCDto.getVersionAcep(), dateTime);
            return acepTyCServices.addCedulaAceptTyC(acepTyCEntity).map(acep -> Response.ok(acep).build());
        } else if (acepTyCDto.getTipoDocumento().equals("Passport")) {
            AcepTyCEntity acepTyCEntity = new AcepTyCEntity(acepTyCDto.getTipoDocumento(),
                    acepTyCDto.getDocumento(), acepTyCDto.getVersionAcep(), dateTime);
            return acepTyCServices.addPassportAceptTyC(acepTyCEntity).map(acep -> Response.ok(acep).build());
        }else {
            return Uni.createFrom().item(Response.status(NOT_ACCEPTABLE).build());
        }
    }

}
