package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.*;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import java.net.URI;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

public class PersonResourceTest
{
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();


    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api/";
    private static Person p1, p2, p3, p4, p5;

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
    public void testServerIsUp(){
        System.out.println("Testing is server up");
        given().when().get("/xxx").then().statusCode(200);
    }

    @Test
    public void testDummyMsg() throws Exception {
        given()
                .contentType("application/json")
                .get("/xxx/")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("msg", equalTo("Hello World"));
    }

    @Test
    public void testFindPersonByPhone() {
        given()
                .contentType("application/json")
                .get("/person/phone/20212021")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("firstName", equalTo("Peter"));
    }

    @Test
    public void testFindPersonsByHobby() {
        given()
                .contentType("application/json")
                .get("/person/hobby/badre")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("persons", hasSize(2));
    }

    @Test
    public void testFindpersonsByCity() {
        given()
                .contentType("application/json")
                .get("/person/city/New York")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("firstName", hasItem("Peter"));
    }
    @Test
    public void testFindNumberOfPersonsWithHobby() {
        given()
                .contentType("application/json")
                .get("/person/hobbycount/Badre")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("count", equalTo(2));
    }

  @Test
    public void testCreatePerson() {
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(GSON.toJson(new Person("SteveRogers@test.com", "Steve", "Rogers")))
                .when()
                .post("/person/create")
                .then()
                .extract().response();

        Assertions.assertEquals(200, response.getStatusCode());
        Assertions.assertEquals("Steve", response.jsonPath().getString("firstName"));
        Assertions.assertEquals("Rogers", response.jsonPath().getString("lastName"));
        Assertions.assertEquals("SteveRogers@test.com", response.jsonPath().getString("email"));
    }

    @Test
    public void testUpdatePerson() {
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(GSON.toJson(p5))
                .when()
                .put("/person/update/3")
                .then()
                .extract().response();
       // Assertions.assertEquals("LokeOdinson@test.com", response.jsonPath().getString("email"));
        Assertions.assertEquals(200, response.getStatusCode());
        Assertions.assertEquals("Loke", response.jsonPath().getString("firstName"));
        Assertions.assertEquals("Odinson", response.jsonPath().getString("lastName"));


    }

    @Test
    public void testDeletePerson()
    {
        Response response = given()
                .header("Content-type", "application/json")
                .when()
                .delete("/person/delete/3")
                .then()
                .extract().response();

        Assertions.assertEquals(200, response.getStatusCode());
    }
}
