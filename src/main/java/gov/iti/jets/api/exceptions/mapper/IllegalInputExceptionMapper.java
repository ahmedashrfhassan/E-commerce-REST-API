package gov.iti.jets.api.exceptions.mapper;

import gov.iti.jets.api.exceptions.IllegalInputException;
import gov.iti.jets.api.exceptions.dto.ErrorMessage;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class IllegalInputExceptionMapper implements ExceptionMapper<IllegalInputException> {

    @Override
    public Response toResponse(IllegalInputException e) {
        ErrorMessage errorMessage = new ErrorMessage(e.getMessage(), 407, "Illegal Input ");
        return Response.status(Response.Status.NOT_FOUND).entity(errorMessage).build();
    }

}