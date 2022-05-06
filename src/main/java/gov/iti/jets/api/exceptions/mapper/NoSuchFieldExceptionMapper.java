package gov.iti.jets.api.exceptions.mapper;

import gov.iti.jets.api.exceptions.MyNoSuchFieldException;
import gov.iti.jets.api.exceptions.dto.ErrorMessage;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import gov.iti.jets.api.exceptions.MyNotFoundException;
@Provider
public class NoSuchFieldExceptionMapper implements ExceptionMapper<MyNoSuchFieldException> {

    @Override
    public Response toResponse(MyNoSuchFieldException exception) {
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), 400, "probably not a field ");
        return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
    }

}



//NoSuchFieldExceptionMapper