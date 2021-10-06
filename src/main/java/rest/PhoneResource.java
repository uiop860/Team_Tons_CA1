package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.PhoneDTO;
import entities.Phone;
import facades.PhoneFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("phone")
public class PhoneResource
{
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final PhoneFacade PHONE_FACADE = PhoneFacade.getPhoneFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();


    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }


    @PUT
    @Path("addPhone/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String addPhone(@PathParam("id") int id, Phone phone) {
        return GSON.toJson(PHONE_FACADE.addPhone(phone, id));
    }

    @DELETE
    @Path("delete/{number}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String deletePhone(@PathParam("number")String number) {
        return GSON.toJson(PHONE_FACADE.deletePhone(number));
    }
}
