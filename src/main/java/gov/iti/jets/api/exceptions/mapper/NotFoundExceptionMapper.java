package gov.iti.jets.api.exceptions.mapper;

import gov.iti.jets.api.exceptions.dto.ErrorMessage;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import gov.iti.jets.api.exceptions.MyNotFoundException;
@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<MyNotFoundException> {

    @Override
    public Response toResponse(MyNotFoundException exception) {
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), 404, "probably wrong ID ");
        return Response.status(Response.Status.NOT_FOUND).entity(errorMessage).build();
    }

}
