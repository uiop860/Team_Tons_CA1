package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.Person;
import utils.EMF_Creator;
import facades.FacadeExample;
import facades.PersonFacade;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

//Todo Remove or change relevant parts before ACTUAL use
@Path("users")
public class UserResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
       
    private static final PersonFacade FACADE =  PersonFacade.getPersonFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
            
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"user\":\"Hello World\"}";
    }
    
    @Path("user")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String userByHobby() {
       
        FACADE.getNumberOfPersonsByHobby(hobby);
        
        //System.out.println("--------------->"+count);
        return "{\"user\":"+count+"}";  //Done manually so no need for a DTO
    }
 
}
