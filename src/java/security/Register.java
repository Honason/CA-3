/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package security;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nimbusds.jose.JOSEException;
import deploy.DeploymentConfiguration;
import entity.Role;
import entity.User;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("register")
public class Register {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String gt(){
      return "{\"txt\" : \"TEST\"}";
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(String jsonString) throws JOSEException {
        Role userRole = new Role("User");
        
        JsonObject json = new JsonParser().parse(jsonString).getAsJsonObject();
        String username = json.get("username").getAsString(); 
        String password = json.get("password").getAsString(); 

        EntityManagerFactory emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
        EntityManager em = emf.createEntityManager();

        try {
            User user = new User(username, PasswordHash.createHash(password));
            user.AddRole(userRole);
            
            try {
                em.getTransaction().begin();
                em.persist(user);
                em.merge(userRole);
                em.getTransaction().commit();
            } finally {
                em.close();
            }
            
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            Logger.getLogger(DeploymentConfiguration.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        JsonObject loginJson = new JsonObject();
        loginJson.addProperty("username", username);
        loginJson.addProperty("password", password); 
        
        Login login = new Login();
        
        return login.login(loginJson.toString());
    }
    
}
