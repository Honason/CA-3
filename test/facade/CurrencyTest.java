/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import facades.ExchangeFacade;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Lasse
 */
public class CurrencyTest {
    /* 
    Methods to test here:
    getBank
    getTodaysRates
   
    */
    
    ExchangeFacade facade = ExchangeFacade.getInstance();
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("CA3RemPU");
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
    public void emptyCache() {
        // We haven't called getBank() yet so obviously we can't get any rates - confirmed by asserting that todays rates are null
        assertTrue(facade.getTodaysRates() == null);
    }
    
    @Test 
    public void happyPathGetBank() {
        facade.getBank();
    
        // getBank() indirectly tests cacheRates() and allows getTodaysRates() to receive a JSON string from the cache (if we succeeded in getting our rates); 
        // we confirm the JSON string to exist by checking if the first letter in the string is an opening JSON curly bracket
        assertTrue(facade.getTodaysRates().startsWith("{"));
    }
   
 
}
