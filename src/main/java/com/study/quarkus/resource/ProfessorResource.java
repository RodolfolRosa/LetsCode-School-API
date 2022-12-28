package com.study.quarkus.resource;

import com.study.quarkus.dto.ErrorResponse;
import com.study.quarkus.dto.ProfessorRequest;
import com.study.quarkus.service.DisciplinaService;
import com.study.quarkus.service.AlunoService;
import com.study.quarkus.service.ProfessorService;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/professores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class ProfessorResource {

    private final ProfessorService service;
    private final DisciplinaService disciplinaService;
    private final AlunoService alunoService;

    @GET
    public Response listProfessors() {
        final var response = service.retrieveAll();

        return Response.ok(response).build();
    }

    @GET
    @Path("/{id}")
    public Response getProfessor(@PathParam("id") int id) {

        final var response = service.getById(id);

        return Response.ok(response).build();
    }

    @POST
    public Response saveProfessor(final ProfessorRequest professor) {
        try {
            final var response = service.save(professor);

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

    @PUT
    @Path("/{id}")
    public Response updateProfessor(@PathParam("id") int id, ProfessorRequest professor) {

        try {

            var response = service.update(id, professor);

            return Response
                    .ok(response)
                    .build();

        } catch(ConstraintViolationException e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(ErrorResponse.createFromValidation(e))
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response removeProfessor(@PathParam("id") int id) {

        service.delete(id);

        return Response
                .status(Response.Status.NO_CONTENT)
                .build();
    }

    @GET
    @Path("/{id}/disciplina")
    public Response getDisciplina(@PathParam("id") int id) {

        final var response = disciplinaService.getDisciplinaByProfessorId(id);

        return Response.ok(response).build();
    }

    @GET
    @Path("/{id}/tutorados")
    public Response getAluno(@PathParam("id") int id) {

        final var response = alunoService.getAlunoByProfessorId(id);

        return Response.ok(response).build();
    }

}