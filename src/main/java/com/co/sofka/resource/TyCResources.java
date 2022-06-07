package com.co.sofka.resource;

import com.co.sofka.dto.AcepTyCDto;
import com.co.sofka.dto.TyCDto;
import com.co.sofka.entity.AcepTyCEntity;
import com.co.sofka.entity.TyCEntity;
import com.co.sofka.services.AcepTyCServices;
import com.co.sofka.services.TyCServices;
import io.smallrye.mutiny.Uni;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.time.LocalDateTime;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;
import static javax.ws.rs.core.Response.Status.NOT_ACCEPTABLE;

@Path("/terms")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TyCResources {

    @Inject
    TyCServices tyCServices;

    @Inject
    AcepTyCServices acepTyCServices;

    @POST
    public Uni<Response> createTyC(@NotNull TyCDto tyCDto){
        TyCEntity tyCEntity = new TyCEntity(tyCDto.getTexto(), tyCDto.getVersion(), LocalDateTime.now());
        return tyCServices.addTyC(tyCEntity).map(tyc -> Response.ok(tyc).build());
    }

    @GET
    @Path("/todos")
    @Produces(APPLICATION_JSON)
    public Uni<Response> getAllTyC(){
        return tyCServices.getAllTyC().map(tyc -> Response.ok(tyc).build());
    }

    @GET
    @Path("/ultimo")
    @Produces(TEXT_PLAIN)
    public Uni<Response> getLastTyC(){
        return tyCServices.getLastVersion().map(tyc -> Response.ok(tyc).build());
    }

    @POST
    @Path("/aceptar")
    @Produces(APPLICATION_JSON)
    public Uni<Response> createAceptTyC(@NotNull AcepTyCDto acepTyCDto){
        if(acepTyCDto.getTipoDocumento().equals("Cedula")){
            AcepTyCEntity acepTyCEntity = new AcepTyCEntity(acepTyCDto.getTipoDocumento(),
                    acepTyCDto.getDocumento(), acepTyCDto.getVersionAcep(), LocalDateTime.now());
            return acepTyCServices.addCedulaAceptTyC(acepTyCEntity).map(acep -> Response.ok(acep).build());
        } else if (acepTyCDto.getTipoDocumento().equals("Passport")) {
            AcepTyCEntity acepTyCEntity = new AcepTyCEntity(acepTyCDto.getTipoDocumento(),
                    acepTyCDto.getDocumento(), acepTyCDto.getVersionAcep(), LocalDateTime.now());
            return acepTyCServices.addPassportAceptTyC(acepTyCEntity).map(acep -> Response.ok(acep).build());
        }else {
            return Uni.createFrom().item(Response.status(NOT_ACCEPTABLE).build());
        }
    }
}
