package deploy;

import entity.Role;
import entity.User;
import facades.UserFacade;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.ws.rs.core.Context;
import security.PasswordHash;
import facades.ExchangeFacade;
import services.Util;

@WebListener
public class DeploymentConfiguration implements ServletContextListener {

  public static String PU_NAME = "PU-Local";
  private ScheduledExecutorService scheduler;

  @Override
  public void contextInitialized(ServletContextEvent sce) {

    // Start daily rate fetching
    ExchangeFacade.getInstance().getBank();

    long timeToMidnight = ((long) 1000*60*60*24) - (Util.getDate(true) - Util.getDate());
    System.out.println("timeToMidnight: " + timeToMidnight);
    scheduler = Executors.newSingleThreadScheduledExecutor();
    scheduler.scheduleAtFixedRate(new Runnable() {
      @Override
      public void run() {
        ExchangeFacade.getInstance().getBank();
      }
    }, timeToMidnight / 1000 / 60, 60*24, TimeUnit.MINUTES);

    Map<String, String> env = System.getenv();
    //If we are running in the OPENSHIFT environment change the pu-name 
    if (env.keySet().contains("OPENSHIFT_MYSQL_DB_HOST")) {
      PU_NAME = "PU_OPENSHIFT";
    }
    /*try {
      ServletContext context = sce.getServletContext();
      EntityManagerFactory emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
      EntityManager em = emf.createEntityManager();
      
      //This flag is set in Web.xml -- Make sure to disable for a REAL system
      boolean makeTestUsers = context.getInitParameter("makeTestUsers").toLowerCase().equals("true");
      if (!makeTestUsers
              || (em.find(User.class, "user") != null && em.find(User.class, "admin") != null && em.find(User.class, "user_admin") != null)) {
        return;
      }
      Role userRole = new Role("User");
      Role adminRole = new Role("Admin");

      User user = new User("user", PasswordHash.createHash("test"));
      User admin = new User("admin", PasswordHash.createHash("test"));
      user.AddRole(userRole);
      admin.AddRole(adminRole); 

      try {
        em.getTransaction().begin();
        em.persist(userRole);
        em.persist(adminRole);

        em.persist(user);
        em.persist(admin);
        em.getTransaction().commit();
      } finally {
        em.close();
      }
    } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
      Logger.getLogger(DeploymentConfiguration.class.getName()).log(Level.SEVERE, null, ex);
    }*/

  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    scheduler.shutdownNow();
  }
}
