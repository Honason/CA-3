/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Role;
import entity.User;
import facades.ExchangeFacade;
import facades.UserFacade;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import security.PasswordHash;

/**
 *
 * @author Lasse
 */
public class UserTest {
    /*
    Methods to test:
    getUsersById(String id)
    authenticateUser(String username, String password)
    getUsers()
    deleteUserById(String id)
    
    */
    UserFacade facade = new UserFacade();
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("CA3RemPU");
    
    public UserTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void happyPathAuthenticateUser() throws NoSuchAlgorithmException, InvalidKeySpecException {
        
        // CREATING DUMMY USER 
            Role userRole = new Role("User");
            User user = new User("USER", PasswordHash.createHash("TEST"));
            user.AddRole(userRole);
            EntityManager em = emf.createEntityManager();
            
            try {
                em.getTransaction().begin();
                em.persist(user);
                em.merge(userRole);
                em.getTransaction().commit();
            } finally {
                em.close();
            }
            // authenticateUser returns null if the user isn't authenticated
           assertTrue(facade.authenticateUser("USER", "TEST") != null);      
    }
    
    @Test 
    public void badPathAuthenticateUser() {
        assertTrue(facade.authenticateUser("BREAKING", "AUTHENTICATE") == null);
    }
    
    @Test
    public void happyPathGetUsersById() {
       // checking if user 'USER' created earlier exists
       assertTrue(facade.getUserByUserId("USER") != null);
    }
    
    @Test
    public void badPathGetUsersById() {
        // checking for a user that doesn't exist
        assertTrue(facade.getUserByUserId("I DONT EXIST") == null);
    }
    
    @Test
    public void happyPathgetUsers() {
        // checking if getUsers returns JSON (then we know it works)
        assertTrue(facade.getUsers().startsWith("{"));
    }
     
    @Test
    public void happyPathDeleteUser() {
        // making sure we have a user called "USER" before we delete it
        if (facade.getUserByUserId("USER") != null) {
            facade.deleteUserByUserId("USER");
        }
        // confirming whether we removed USER or not
        assertTrue(facade.getUserByUserId("USER") == null);
    }
    

}
