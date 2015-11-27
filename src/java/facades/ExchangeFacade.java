package facades;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import deploy.DeploymentConfiguration;
import entity.Rate;
import java.util.HashMap;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class ExchangeFacade {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
    HashMap<String, Double> cache;
    String cacheJSON;

    // We use singleton to simplify setting/getting the cached version of the daily rates
    private static ExchangeFacade instance = null;

    protected ExchangeFacade() {
        // Exists only to defeat instantiation.
    }

    public static ExchangeFacade getInstance() {
        if (instance == null) {
            instance = new ExchangeFacade();
        }
        return instance;
    }

    // Call this method every 24 hours along with daily rates
    public void cacheRates() {
        EntityManager em = emf.createEntityManager();

        List<Rate> rates;

        try {
            Query q = em.createQuery("select R from Rate R where R.date = (select max(R.date) from Rate R)");
            rates = q.getResultList();
        } finally {
            em.close();
        }

        // Resetting cache to clear yesterdays rates
        cache = new HashMap<>();

        if (rates != null) {
            for (Rate rate : rates) {
                cache.put(rate.getCurrency(), rate.getRate());
            }
            cacheJSON = new Gson().toJson(cache);
        } else {
            System.out.println("cache is fukin pooped");
        }
    }

    public String getCache() {
        // Returning placeholder instad of the real cache:
       HashMap<String, Double> kek = new HashMap<>();
        kek.put("USD", 704.76);
                kek.put("DKK", 100.00);
        System.out.println(new Gson().toJson(kek));
       return new Gson().toJson(kek);
       // return cacheJSON;
    }

}
