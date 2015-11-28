package rest;

import facades.ExchangeFacade;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("currency")
//@RolesAllowed("User")
public class Currency {

    ExchangeFacade exchange = ExchangeFacade.getInstance();


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/dailyrates")
    public String dailyRates() {
        return exchange.getTodaysRates();
        // return "ayy";
    }
    
    // Kinda irrelevant to have two when we do calculator on client side anyway :PPPP remove later
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/calculator")
    public String calculator() {
        return exchange.getBank();
        // return exchange.getCache();
    }
    
}
