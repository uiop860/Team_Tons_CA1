package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.HobbyCountDTO;
import dtos.HobbyDTO;
import dtos.PersonDTO;
import dtos.PhoneDTO;
import exceptions.PersonNotFoundException;
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
import utils.EMF_Creator;

/**
 *
 * @author lukas
 */
@Path("person")
public class PersonResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final PersonFacade FACADE = PersonFacade.getPersonFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllPersons() throws PersonNotFoundException{
        
        List<PersonDTO> personsDTO = null;
 
        try {
            personsDTO = FACADE.getAllPersons();
        } catch (Exception e) {
            throw new PersonNotFoundException("Error getting all persons");
        }
        if (personsDTO != null && !personsDTO.isEmpty()) {
            return GSON.toJson(personsDTO);
        } else {
            throw new PersonNotFoundException("Error getting all persons");
        }
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public String getPersonByID(@PathParam("id") int id) {
        return GSON.toJson(FACADE.getPersonByID(id));
    }

    @GET
    @Path("phone/{phone}")
    @Produces({MediaType.APPLICATION_JSON})
    public String getPersonByPhone(@PathParam("phone") String phone) throws PersonNotFoundException {

        PersonDTO personDTO = null;

        if (phone != null) {
            try {
                personDTO = FACADE.getPersonByPhone(phone);
            } catch (Exception e) {
                throw new PersonNotFoundException("Person not found on phone number", phone);
            }
        } else {
            throw new PersonNotFoundException("Missing phone number");
        }
        if (personDTO != null) {
            return GSON.toJson(personDTO);
        } else {
            throw new PersonNotFoundException("Person not found on phone number", phone);
        }
    }

    @GET
    @Path("hobby/{hobby}")
    @Produces({MediaType.APPLICATION_JSON})
    public String getPersonByHobby(@PathParam("hobby") String hobby) throws PersonNotFoundException {

        List<PersonDTO> personsDTO = null;

        if (hobby != null) {
            try {
                personsDTO = FACADE.getPersonsByHobby(hobby);
            } catch (Exception e) {
                throw new PersonNotFoundException("Person not found on hobby", hobby);
            }
        } else {
            throw new PersonNotFoundException("Missing hobby");
        }
        if (personsDTO != null && !personsDTO.isEmpty()) {
            return GSON.toJson(personsDTO);
        } else {
            throw new PersonNotFoundException("Person not found on hobby", hobby);
        }
    }

    @GET
    @Path("city/{city}")
    @Produces({MediaType.APPLICATION_JSON})
    public String getPersonsByCity(@PathParam("city") String city) throws PersonNotFoundException {

        List<PersonDTO> personsDTO = null;

        if (city != null) {
            try {
                personsDTO = FACADE.getPersonsByCity(city);
            } catch (Exception e) {
                throw new PersonNotFoundException("Persons not found on city", city);
            }
        } else {
            throw new PersonNotFoundException("Missing city");
        }
        if (personsDTO != null && !personsDTO.isEmpty()) {
            return GSON.toJson(personsDTO);
        } else {
            throw new PersonNotFoundException("Persons not found on city", city);
        }
    }

    @GET
    @Path("hobbyCount/{hobby}")
    @Produces({MediaType.APPLICATION_JSON})
    public String getHobbyCount(@PathParam("hobby") String hobby) throws PersonNotFoundException {

        HobbyCountDTO hobbyCountDTO = null;

        if (hobby != null) {
            try {
                hobbyCountDTO = FACADE.getNumberOfPersonsByHobby(hobby);
            } catch (Exception e) {
                throw new PersonNotFoundException("Count not found on hobby", hobby);
            }
        } else {
            throw new PersonNotFoundException("Missing hobby");
        }
        if (hobbyCountDTO != null && hobbyCountDTO.getCount() != 0) {
            return GSON.toJson(hobbyCountDTO);
        } else {
            throw new PersonNotFoundException("Count not found on hobby", hobby);
        }
    }

    @POST
    @Path("createPerson")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String insertPerson(PersonDTO personDTO) throws PersonNotFoundException {

        PersonDTO personResponseDTO = null;

        if (personDTO != null) {
            try {
                personResponseDTO = FACADE.insertPerson(personDTO);
            } catch (Exception e) {
                throw new PersonNotFoundException("Person could not be inserted", personDTO);
            }
        } else {
            throw new PersonNotFoundException("Missing person");
        }
        if (personResponseDTO != null) {
            return GSON.toJson(personResponseDTO);
        } else {
            throw new PersonNotFoundException("Person could not be inserted", personDTO);
        }
    }

    @PUT
    @Path("updatePerson/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String updatePerson(@PathParam("id") int id, PersonDTO personDTO) throws PersonNotFoundException {

        PersonDTO personResponseDTO = null;

        if (personDTO != null && id != 0) {
            try {
                personResponseDTO = FACADE.updatePerson(personDTO, id);
            } catch (Exception e) {
                throw new PersonNotFoundException("Person could not be updated", personDTO);
            }
        } else {
            throw new PersonNotFoundException("Missing person or id");
        }
        if (personResponseDTO != null) {
            return GSON.toJson(personResponseDTO);
        } else {
            throw new PersonNotFoundException("Person could not be updated", personDTO);
        }
    }

    @PUT
    @Path("addHobby/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String addHobbyToPerson(@PathParam("id") int id, HobbyDTO hobbyDTO) throws PersonNotFoundException {

        PersonDTO personResponseDTO = null;

        if (hobbyDTO != null && id != 0) {
            try {
                personResponseDTO = FACADE.addHobbyToPerson(hobbyDTO, id);
            } catch (Exception e) {
                throw new PersonNotFoundException("Hobby could not be added", hobbyDTO);
            }
        } else {
            throw new PersonNotFoundException("Missing hobby or id");
        }
        if (personResponseDTO != null) {
            return GSON.toJson(personResponseDTO);
        } else {
            throw new PersonNotFoundException("Hobby could not be added", hobbyDTO);
        }
    }

    @PUT
    @Path("addPhone/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String addPhoneToPerson(@PathParam("id") int id, PhoneDTO phoneDTO) throws PersonNotFoundException {

        PersonDTO personResponseDTO = null;

        if (phoneDTO != null && id != 0) {
            try {
                personResponseDTO = FACADE.addPhoneToPerson(phoneDTO, id);
            } catch (Exception e) {
                throw new PersonNotFoundException("Phone could not be added", phoneDTO);
            }
        } else {
            throw new PersonNotFoundException("Missing phone or id");
        }
        if (personResponseDTO != null) {
            return GSON.toJson(personResponseDTO);
        } else {
            throw new PersonNotFoundException("Phone could not be added", phoneDTO);
        }
    }

    @DELETE
    @Path("removeHobby/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String removeHobbyFromPerson(@PathParam("id") int id, HobbyDTO hobbyDTO) throws PersonNotFoundException {

        PersonDTO personResponseDTO = null;

        if (hobbyDTO != null && id != 0) {
            try {
                personResponseDTO = FACADE.removeHobbyFromPerson(hobbyDTO, id);
            } catch (Exception e) {
                throw new PersonNotFoundException("Hobby could not be removed", hobbyDTO);
            }
        } else {
            throw new PersonNotFoundException("Missing hobby or id");
        }
        if (personResponseDTO != null) {
            return GSON.toJson(personResponseDTO);
        } else {
            throw new PersonNotFoundException("Hobby could not be removed", hobbyDTO);
        }
    }

    @DELETE
    @Path("removePhone/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String removePhoneFromPerson(@PathParam("id") int id, PhoneDTO phoneDTO) throws PersonNotFoundException {

        PersonDTO personResponseDTO = null;

        if (phoneDTO != null && id != 0) {
            try {
                personResponseDTO = FACADE.removePhoneFromPerson(phoneDTO, id);
            } catch (Exception e) {
                throw new PersonNotFoundException("Phone could not be removed", phoneDTO);
            }
        } else {
            throw new PersonNotFoundException("Missing phone or id");
        }
        if (personResponseDTO != null) {
            return GSON.toJson(personResponseDTO);
        } else {
            throw new PersonNotFoundException("Phone could not be removed", phoneDTO);
        }
    }

    @DELETE
    @Path("removePerson/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String removePerson(@PathParam("id") int id) throws PersonNotFoundException {

        PersonDTO personResponseDTO = null;

        if (id != 0) {
            try {
                personResponseDTO = FACADE.removePerson(id);
            } catch (Exception e) {
                throw new PersonNotFoundException("Person could not be removed", id);
            }
        } else {
            throw new PersonNotFoundException("Missing id");
        }
        if (personResponseDTO != null) {
            return GSON.toJson(personResponseDTO);
        } else {
            throw new PersonNotFoundException("Person could not be removed", id);
        }
    }
}
