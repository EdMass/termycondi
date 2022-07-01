package com.co.sofka.resource;

import com.co.sofka.dto.AcepTyCDto;
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

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/terms")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AcepTyCResource {

    @Inject
    AcepTyCServices acepTyCServices;

    @POST
    @Path("/aceptar")
    @Produces(APPLICATION_JSON)
    public Uni<Response> createAceptTyC(@NotNull AcepTyCDto acepTyCDto){
            return acepTyCServices.createAceptTyC(acepTyCDto);
    }

}
