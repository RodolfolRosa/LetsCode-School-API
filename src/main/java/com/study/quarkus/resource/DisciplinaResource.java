package com.study.quarkus.resource;

import com.study.quarkus.dto.DisciplinaRequest;
import com.study.quarkus.dto.ErrorResponse;
import com.study.quarkus.service.DisciplinaService;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/disciplinas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class DisciplinaResource {

    private final DisciplinaService service;

    @GET
    public Response list() {
        final var response = service.retrieveAll();

        return Response.ok(response).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") int id) {

        final var response = service.getById(id);

        return Response.ok(response).build();
    }

    @POST
    public Response save(final DisciplinaRequest request) {
        try {
            final var response = service.save(request);

            return Response
                    .status(Response.Status.CREATED)
                    .entity(response)
                    .build();

        } catch(ConstraintViolationException e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(ErrorResponse.createFromValidation(e))
                    .build();
        }
    }

    

    @PATCH
    @Path("/{id}/titular/{idProfessor}")
    public Response updateTitular(@PathParam("id") int idDisciplina, @PathParam("idProfessor") int idProfessor) {
        final var response = service.updateTitular(idDisciplina, idProfessor);

        return Response
                .status(Response.Status.CREATED)
                .entity(response)
                .build();
    }

}