package gov.iti.jets.security;


import java.io.IOException;

import gov.iti.jets.util.Util;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.PreMatching;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.Provider;

import java.util.Objects;

@Provider
@PreMatching
//@Priority(Priorities.AUTHENTICATION)
public class AuthFilter implements ContainerRequestFilter {
    @Context
    UriInfo uriInfo;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authHeaderVal = requestContext.getHeaderString("Auth-Token");
        if(authHeaderVal != null){
            try {
                authHeaderVal = Util.decodeString(authHeaderVal.trim());
            }catch (IllegalArgumentException e){
                authHeaderVal="";
            }
        }
        requestContext.setSecurityContext(new MySecurityContext(Objects.requireNonNullElse(authHeaderVal, "")));

//        String authHeaderVal = requestContext.getHeaderString("Auth-Token");
//        System.out.println(authHeaderVal);
//        if (authHeaderVal!=null) {
//            final SecurityContext securityContext = requestContext.getSecurityContext();
//            requestContext.setSecurityContext(new MySecurityContext(authHeaderVal));
//        }
//        else throw new WebApplicationException("not authorized");

    }
}