package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.PhoneDTO;
import entities.*;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class PhoneResourceTest
{
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();


    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api/";
    private static Person p1, p2, p3, p4, p5;
    private static Phone ph1, ph2, ph3, ph4;

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static org.glassfish.grizzly.http.server.HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        p1 = new Person("PeterParker@test.com","Peter", "Parker");
        p2 = new Person("TonyStark@test.com", "Tony", "Stark");
        p3 = new Person("BlackWidow@test.com", "Natasha", "Romanoff");
        p4 = new Person("ThorOdinson@test.com", "Thor", "Odinson");
        p5 = new Person("LokeOdinson@test.com", "Loke", "Odinson");

        ph1 = new Phone("12345678", "Easy Numbers");



        Phone phone1 = new Phone("20212021", "This year");
        Phone phone2 = new Phone("20202020", "Last year");

        Hobby hobby1 = new Hobby("Badre", "Badre skurke");

        Address address1 = new Address("Queensvej", "Der hvor spiderman bor");

        CityInfo cityInfo1 = new CityInfo(9820, "New York");
        p1.addPhone(phone1);
        p2.addPhone(phone2);
        p1.addHobby(hobby1);
        p2.addHobby(hobby1);
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Phone.deleteAllRows").executeUpdate();
            em.createNamedQuery("Phone.resetAutoIncrement").executeUpdate();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
            em.createNamedQuery("Person.resetAutoIncrement").executeUpdate();
            em.createNamedQuery("Hobby.deleteAllRows").executeUpdate();
            em.createNamedQuery("Hobby.resetAutoIncrement").executeUpdate();
            em.createNamedQuery("Address.deleteAllRows").executeUpdate();
            em.createNamedQuery("Address.resetAutoIncrement").executeUpdate();
            em.createNamedQuery("CityInfo.deleteAllRows").executeUpdate();
            em.createNamedQuery("CityInfo.resetAutoIncrement").executeUpdate();

            em.persist(address1);
            em.persist(cityInfo1);
            address1.setCityInfo(cityInfo1);
            em.merge(address1);
            em.persist(p1);
            p1.setAddress(address1);
            em.merge(p1);
            em.persist(p2);
            em.persist(p3);
            em.persist(p4);
            em.getTransaction().commit();
        } finally
        {
            em.close();
        }
    }


    @Test
    public void testAddPhone() {

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(GSON.toJson(ph1))
                .when()
                .put("/phone/addPhone/3")
                .then()
                .extract().response();
        Assertions.assertEquals(200, response.getStatusCode());
        Assertions.assertEquals("12345678", response.jsonPath().getString("number"));
        Assertions.assertEquals("Easy Numbers", response.jsonPath().getString("description"));


    }

    @Test
    public void testDeletePhone() {
        when()
                .delete("/phone/delete/20212021")
                .then()
                .statusCode(200)
                .log().all();

    }

}
