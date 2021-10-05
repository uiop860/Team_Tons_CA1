package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.PersonDTO;
import entities.Person;
import utils.EMF_Creator;
import facades.PersonFacade;
import static java.lang.System.console;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author lukas
 */

@Path("person")
public class PersonResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final PersonFacade FACADE =  PersonFacade.getPersonFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
            
    @GET
    @Path("demo")
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }
    
    //This should return a user with a given phone number
    @GET
    @Path("phone/{phone}")
    @Produces({MediaType.APPLICATION_JSON})
    public String userByPhone(@PathParam("phone") String phone) {
       
        PersonDTO person = FACADE.getPersonByPhone(phone);
        
        if (person != null){        
            return GSON.toJson(person);
        }else{
            return "{\"msg\":\"No one with this phone number could be found.\"}";
        }
    }

    //This should return a list of users with a given hobby
    @GET
    @Path("hobby/{hobby}")
    @Produces({MediaType.APPLICATION_JSON})
    public String userByHobby(@PathParam("hobby") String hobby) {
       
        List<PersonDTO> personsHobby = FACADE.getPersonsByHobby(hobby);

        if (personsHobby != null){
            return GSON.toJson(personsHobby);
        }else{
            return "{\"msg\":\"You need to specify a hobby.\"}";
        }
    }
    
    //This should return a list of users living in a given city
    @GET
    @Path("city/{city}")
    @Produces({MediaType.APPLICATION_JSON})
    public String getPersonsByCity(@PathParam("city") String city) {
       
        List<PersonDTO> personsCity = FACADE.getPersonsByCity(city);

        if (personsCity != null){
            return GSON.toJson(personsCity);
        }else{
            return "{\"msg\":\"You need to specify a city.\"}";
        }
    }
    
    @GET
    @Path("hobbycount/{hobby}")
    @Produces({MediaType.APPLICATION_JSON})
    public String hobbyCount(@PathParam("hobby") String hobby) {
       
        long numberOfPersonsByHobby = FACADE.getNumberOfPersonsByHobby(hobby);
        
        if(numberOfPersonsByHobby != 0){
            return GSON.toJson(numberOfPersonsByHobby);
        }else{
            return "{\"msg\":\"No one has this hobby.\"}";
        }
    }
    
    @POST
    @Path("create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public PersonDTO createPerson(Person person){
   
        return FACADE.insertPerson(person);
    }
    
    @PUT
    @Path("update/{personId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public PersonDTO updateSinglePerson(@PathParam("personId") int personId, Person person){
        
        person.setId(personId);
        return FACADE.updatePerson(person,personId);
    }
    
    @DELETE
    @Path("delete/{personId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void deleteSinglePerson(@PathParam("personId") int personId, Person person){
        
        FACADE.deletePerson(personId);
    }
    
}
