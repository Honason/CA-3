package rest;

import facades.UserFacade;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("admin")
@RolesAllowed("Admin")
public class Admin {
  
   UserFacade users = new UserFacade();
    
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/users")
  public String getAllUsers(){
    return users.getUsers();
  }
  
  @DELETE
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/users/{id}")
  public void deleteUser(@PathParam("id") String id) {
      users.deleteUserByUserId(id);
  }
  
}
