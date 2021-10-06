package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.HobbyCountDTO;
import dtos.HobbyDTO;
import dtos.PersonDTO;
import dtos.PhoneDTO;
import entities.Person;
import facades.PhoneFacade;
import utils.EMF_Creator;
import facades.PersonFacade;
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
import javax.ws.rs.core.MediaType;

/**
 *
 * @author lukas
 */
@Path("person")
public class PersonResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final PersonFacade FACADE = PersonFacade.getPersonFacade(EMF);
    private static final PhoneFacade PHONE_FACADE = PhoneFacade.getPhoneFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public String getPersonByID(@PathParam("id") int id) {
        return GSON.toJson(FACADE.getPersonByID(id));
    }

    //This should return a user with a given phone number
    @GET
    @Path("phone/{phone}")
    @Produces({MediaType.APPLICATION_JSON})
    public String userByPhone(@PathParam("phone") String phone) {

        PersonDTO person = FACADE.getPersonByPhone(phone);

        if (person != null) {
            return GSON.toJson(person);
        } else {
            return "{\"msg\":\"No one with this phone number could be found.\"}";
        }
    }

    //This should return a list of users with a given hobby
    @GET
    @Path("hobby/{hobby}")
    @Produces({MediaType.APPLICATION_JSON})
    public String userByHobby(@PathParam("hobby") String hobby) {

        List<PersonDTO> personsHobby = FACADE.getPersonsByHobby(hobby);

        if (personsHobby != null) {
            return GSON.toJson(personsHobby);
        } else {
            return "{\"msg\":\"You need to specify a hobby.\"}";
        }
    }

    //This should return a list of users living in a given city
    @GET
    @Path("city/{city}")
    @Produces({MediaType.APPLICATION_JSON})
    public String getPersonsByCity(@PathParam("city") String city) {
        List<PersonDTO> personsCity = FACADE.getPersonsByCity(city);

        if (personsCity != null) {
            return GSON.toJson(personsCity);
        } else {
            return "{\"msg\":\"You need to specify a city.\"}";
        }
    }

    @GET
    @Path("hobbycount/{hobby}")
    @Produces({MediaType.APPLICATION_JSON})
    public String hobbyCount(@PathParam("hobby") String hobby) {

        HobbyCountDTO hobbyCount = FACADE.getNumberOfPersonsByHobby(hobby);

        if (hobbyCount != null) {
            return GSON.toJson(hobbyCount);
        } else {
            return "{\"msg\":\"No one has this hobby.\"}";
        }
    }

    @POST
    @Path("create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String createPerson(Person person) {
        return GSON.toJson(FACADE.insertPerson(person));
    }

    @PUT
    @Path("update/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String updateSinglePerson(@PathParam("id") int id, PersonDTO personDTO) {
        return GSON.toJson(FACADE.updatePerson(personDTO, id));
    }
    
    @PUT
    @Path("addHobby/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String addHobby(@PathParam("id") int id, HobbyDTO hobbyDTO){
        return GSON.toJson(FACADE.addHobbyToPerson(hobbyDTO, id));
    }
    
    @PUT
    @Path("removeHobby/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String removeHobby(@PathParam("id") int id, HobbyDTO hobbyDTO){
        return GSON.toJson(FACADE.removeHobbyFromPerson(hobbyDTO,id));
    }

    @DELETE
    @Path("delete/{personId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteSinglePerson(@PathParam("personId") int personId) {
        return GSON.toJson(FACADE.deletePerson(personId));
    }


}
