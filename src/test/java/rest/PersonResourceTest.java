package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.HobbyDTO;
import dtos.PersonDTO;
import dtos.PhoneDTO;
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

public class PersonResourceTest {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api/";

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

            Address address = new Address("Queensvej", "Der hvor spiderman bor");
            address.setCityInfo(new CityInfo(9820, "New York"));
            em.merge(address);

            Person person = new Person("peter@parker.com", "Peter", "Parker");
            person.addHobby(new Hobby("Badre", "Badre skurke"));
            person.addPhone(new Phone("20212021", "This year"));
            person.setAddress(address);
            em.merge(person);
            em.getTransaction().commit();

            em.getTransaction().begin();
            Address address1 = new Address("Nastrovia", "Safe house");
            address1.setCityInfo(new CityInfo(7364, "Moskov"));
            em.merge(address1);

            Person person1 = new Person("BlackWidow@test.com", "Natasha", "Romanoff");
            person1.addHobby(new Hobby("Badre", "Badre skurke"));
            person1.addPhone(new Phone("20202020", "Last year"));
            person1.setAddress(address1);
            em.merge(person1);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

//    @Test
//    public void testServerIsUp() {
//        System.out.println("Testing is server up");
//        given().when().get("/xxx").then().statusCode(200);
//    }
//
//    @Test
//    public void testDummyMsg() throws Exception {
//        given()
//                .contentType("application/json")
//                .get("/xxx/")
//                .then()
//                .assertThat()
//                .statusCode(HttpStatus.OK_200.getStatusCode())
//                .body("msg", equalTo("Hello World"));
//    }
//
//    @Test
//    public void testFindPersonByPhone() {
//        given()
//                .contentType("application/json")
//                .get("/person/phone/20212021")
//                .then()
//                .assertThat()
//                .statusCode(HttpStatus.OK_200.getStatusCode())
//                .body("firstName", equalTo("Peter"));
//    }
//
//    @Test
//    public void testFindPersonsByHobby() {
//        given()
//                .contentType("application/json")
//                .get("/person/hobby/badre")
//                .then()
//                .assertThat()
//                .statusCode(HttpStatus.OK_200.getStatusCode())
//                .body("persons", hasSize(2));
//    }
//
//    @Test
//    public void testFindpersonsByCity() {
//        given()
//                .contentType("application/json")
//                .get("/person/city/New York")
//                .then()
//                .assertThat()
//                .statusCode(HttpStatus.OK_200.getStatusCode())
//                .body("firstName", hasItem("Peter"));
//    }
//
//    @Test
//    public void testFindNumberOfPersonsWithHobby() {
//        given()
//                .contentType("application/json")
//                .get("/person/hobbyCount/Badre")
//                .then()
//                .assertThat()
//                .statusCode(HttpStatus.OK_200.getStatusCode())
//                .body("count", equalTo(2));
//    }
//
//    @Test
//    public void testInsertPerson() {
//        Response response = given()
//                .contentType("application/json")
//                .and()
//                .body(GSON.toJson(new PersonDTO(new Person("SteveRogers@test.com", "Steve", "Rogers"))))
//                .when()
//                .post("/person/createPerson")
//                .then()
//                .extract().response();
//        Assertions.assertEquals(200, response.getStatusCode());
//        Assertions.assertEquals("Steve", response.jsonPath().getString("firstName"));
//        Assertions.assertEquals("Rogers", response.jsonPath().getString("lastName"));
//        Assertions.assertEquals("SteveRogers@test.com", response.jsonPath().getString("email"));
//    }
//
//    @Test
//    public void testUpdatePerson() {
//        PersonDTO personDTO = new PersonDTO(new Person("loke@odinson.com", "Loke", "Odinson"));
//        Response response = given()
//                .contentType("application/json")
//                .and()
//                .body(GSON.toJson(personDTO))
//                .when()
//                .put("/person/updatePerson/2")
//                .then()
//                .extract().response();
//        Assertions.assertEquals("loke@odinson.com", response.jsonPath().getString("email"));
//        Assertions.assertEquals(200, response.getStatusCode());
//        Assertions.assertEquals("Loke", response.jsonPath().getString("firstName"));
//        Assertions.assertEquals("Odinson", response.jsonPath().getString("lastName"));
//
//    }
//
//    @Test
//    public void testAddHobby() {
//        HobbyDTO hobbyDTO = new HobbyDTO(new Hobby("Killing", "Ice giants"));
//        Response response = given()
//                .contentType("application/json")
//                .and()
//                .body(GSON.toJson(hobbyDTO))
//                .when()
//                .put("person/addHobby/1")
//                .then()
//                .extract().response();
//        HobbyDTO hobbyResponse = response.jsonPath().getList("hobbies", HobbyDTO.class).get(1);
//        Assertions.assertEquals(200, response.getStatusCode());
//        Assertions.assertEquals(2, response.jsonPath().getList("hobbies").size());
//        Assertions.assertEquals("Killing", hobbyResponse.getName());
//        Assertions.assertEquals("Ice giants", hobbyResponse.getDescription());
//    }
//
////    TODO: no worky, gives statusCode 400
////    @Test
//    public void testRemoveHobby() {
//        HobbyDTO hobbyDTO = new HobbyDTO(new Hobby("Badre", "Badre skurke"));
//        Response response = given()
//                .contentType("application/json")
//                .and()
//                .body(GSON.toJson(hobbyDTO))
//                .when()
//                .delete("person/removeHobby/1")
//                .then()
//                .extract().response();
//        System.out.println(GSON.toJson(hobbyDTO));
//        Assertions.assertEquals(200, response.getStatusCode());
//        response.jsonPath().prettyPeek();
//        Assertions.assertEquals(true, response.jsonPath().getList("hobbies").isEmpty());
//    }
//
//    @Test
//    public void testAddPhone() {
//        PhoneDTO phoneDTO = new PhoneDTO(new Phone("75648326", "Hulks phone number"));
//        Response response = given()
//                .contentType("application/json")
//                .and()
//                .body(GSON.toJson(phoneDTO))
//                .when()
//                .put("person/addPhone/1")
//                .then()
//                .extract().response();
//        PhoneDTO phoneResponse = response.jsonPath().getList("phones", PhoneDTO.class).get(1);
//        Assertions.assertEquals(200, response.getStatusCode());
//        Assertions.assertEquals(2, response.jsonPath().getList("phones").size());
//        Assertions.assertEquals("75648326", phoneResponse.getNumber());
//        Assertions.assertEquals("Hulks phone number", phoneResponse.getDescription());
//    }
//
////    TODO: no worky, gives statusCode 400
////    @Test
//    public void testRemovePhone() {
//        PhoneDTO phoneDTO = new PhoneDTO(new Phone("20212021", "This year"));
//        Response response = given()
//                .contentType("application/json")
//                .and()
//                .body(GSON.toJson(phoneDTO))
//                .when()
//                .delete("person/removePhone/1")
//                .then()
//                .extract().response();
//        System.out.println(GSON.toJson(phoneDTO));
//        Assertions.assertEquals(200, response.getStatusCode());
//        Assertions.assertEquals(true, response.jsonPath().getList("phones").isEmpty());
//    }
//
//    @Test
//    public void testDeletePerson() {
//        Response response = given()
//                .contentType("application/json")
//                .when()
//                .delete("/person/removePerson/1")
//                .then()
//                .extract().response();
//
//        Assertions.assertEquals(200, response.getStatusCode());
//    }
}
