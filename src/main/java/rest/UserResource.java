package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.PersonDTO;
import entities.Person;
import utils.EMF_Creator;
import facades.FacadeExample;
import facades.PersonFacade;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author lukas
 */

@Path("person")
public class UserResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
       
    private static final PersonFacade FACADE =  PersonFacade.getPersonFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
            
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }
    
    
    //This should return a user with a given phone number
    @GET
    @Path("phone")
    @Produces({MediaType.APPLICATION_JSON})
    public String userByPhone(@PathParam("phone)") int phone) {
       
        Person person = new Person(FACADE.getPersonByPhone(phone));
        
        if (person != null){        
        return GSON.toJson(person);
        }else{
        return null;
        }
    }
    
    //This should return a list of users with a given hobby
    @GET
    @Path("hobby")
    @Produces({MediaType.APPLICATION_JSON})
    public String userByHobby(@PathParam("hobby)") String hobby) {
       
        List<Person> personsHobby = (FACADE.getPersonsByHobby(hobby));

        if (personsHobby != null){        
        return GSON.toJson(personsHobby);
        }else{
        return null;
        }
    }

    
    //This should return a list of users living in a given city
    @GET
    @Path("city")
    @Produces({MediaType.APPLICATION_JSON})
    public String getPersonsByCity(@PathParam("city)") int postCode) {
       
        List<Person> personsCity = (FACADE.getPersonsByCity(postCode));
        
        if (personsCity != null){        
        return GSON.toJson(personsCity);
        }else{
        return null;
        }
        
    }
    
    @GET
    @Path("hobbynumber")
    @Produces({MediaType.APPLICATION_JSON})
    public String hobbyAmount() {
       
        //FACADE.getNumberOfPersonsByHobby(hobby)
        
        return "{\"user\":\"This should return a number of users with a given hobby\"}";
    }
    
    
    
    
}
