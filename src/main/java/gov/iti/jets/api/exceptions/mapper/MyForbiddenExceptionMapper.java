package gov.iti.jets.api.exceptions.mapper;


import gov.iti.jets.api.exceptions.MyForbiddenException;
import gov.iti.jets.api.exceptions.MyNoSuchFieldException;
import gov.iti.jets.api.exceptions.dto.ErrorMessage;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class MyForbiddenExceptionMapper implements ExceptionMapper<MyForbiddenException> {

    @Override
    public Response toResponse(MyForbiddenException exception) {
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), 403, "probably you are not authorized");
        return Response.status(Response.Status.FORBIDDEN).entity(errorMessage).build();
    }

}