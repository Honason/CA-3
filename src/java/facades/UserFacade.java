package facades;

import com.google.gson.Gson;
import deploy.DeploymentConfiguration;
import entity.Role;
import entity.User;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import security.PasswordHash;

public class UserFacade {

  EntityManagerFactory emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);

  public UserFacade() {
   

   
  }
  
  public boolean userExist(String username) {
      EntityManager em = emf.createEntityManager();
    try {
      Query q = em.createQuery("select u from User u where u.userName = :username");
      return q.setParameter("username", username).getResultList().size() > 0;
    } finally {
      em.close();
    }
  }

  public User getUserByUserId(String id) {
    EntityManager em = emf.createEntityManager();
    try {
      return em.find(User.class, id);
    } finally {
      em.close();
    }
  }
  /*
   Return the Roles if users could be authenticated, otherwise null
   */
  public List<String> authenticateUser(String userName, String password) {
    EntityManager em = emf.createEntityManager();
    try {
      User user =  em.find(User.class, userName);
      if(user == null){
        return null;
      }
      try {
        boolean authenticated = PasswordHash.validatePassword(password, user.getPassword());
        return authenticated ? user.getRolesAsStrings() : null;
      } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
        Logger.getLogger(UserFacade.class.getName()).log(Level.SEVERE, null, ex);
        return null;
      }
      
    } finally {
      em.close();
    }
    
  }

  
    public String getUsers() {
        EntityManager em = emf.createEntityManager();

        List<User> users;

        try {
            Query q = em.createQuery("select U from User u");
            users = q.getResultList();
        } finally {
            em.close();
        }
        HashMap<String, String> userMap = new HashMap();

        for (User user : users) {
            List<Role> roles = user.getRoles();
            String roleString = "";
            for (Role role : roles) {
                roleString += role.getRoleName();
            }

            userMap.put(user.getUserName(), roleString);
        }

        return new Gson().toJson(userMap);
    }

    public void deleteUserByUserId(String id) {
        EntityManager em = emf.createEntityManager();
        try {
            User user = em.find(User.class, id);
            em.getTransaction().begin();
            em.remove(user);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
  
  
}
