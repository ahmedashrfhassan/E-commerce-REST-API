package gov.iti.jets.security;

import jakarta.ws.rs.core.SecurityContext;
import java.security.Principal;

public class MySecurityContext implements SecurityContext {
    private final String role;

    public MySecurityContext(String role){
        this.role = role;
    }

    @Override
    public Principal getUserPrincipal() {
       return new Principal() {
           @Override
           public String getName() {
               return role;
           }
       };
    }

    @Override
    public boolean isUserInRole(String role) {
        return role.equalsIgnoreCase(this.role);
    }

    @Override
    public boolean isSecure() {
        return true;
    }

    @Override
    public String getAuthenticationScheme() {
        return "Auth-Token";
    }
}
