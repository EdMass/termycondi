package com.co.sofka.resource;

import com.co.sofka.dto.TyCDto;
import com.co.sofka.entity.TyCEntity;
import com.co.sofka.services.TyCServices;
import io.smallrye.mutiny.Uni;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/terms")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TyCResources {

    @Inject
    TyCServices tyCServices;

    private final LocalDateTime dateTime = LocalDateTime.now(ZoneId.of("Etc/UTC") );

    @POST
    public Uni<Response> createTyC(@NotNull TyCDto tyCDto){
        TyCEntity tyCEntity = new TyCEntity(tyCDto.getTexto(), tyCDto.getVersion(), dateTime);
        return tyCServices.addTyC(tyCEntity).map(tyc -> Response.ok(tyc).build());
    }

    @GET
    @Path("/todos")
    @Produces(APPLICATION_JSON)
    public Uni<Response> getAllTyC(){
        return tyCServices.getAllTyC().map(
                tyc -> Response.ok(tyc).build());
    }

    @GET
    @Path("/ultimo")
    @Produces(APPLICATION_JSON)
    public Uni<Response> getLastTyC(){
        return tyCServices.getLastVersion().map(
                tyc -> Response.ok(tyc).build());
    }

    @GET
    @Path("/buscar/{version}")
    @Produces(APPLICATION_JSON)
    public Uni<Response> findVersion(@PathParam("version") Integer version){
        var uni = tyCServices.findVersion(version);
        return tyCServices.findVersion(version).map(tyc -> Response.ok(tyc).build());
    }



}
