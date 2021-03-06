package facades;

import dtos.CityInfoDTO;
import dtos.HobbyCountDTO;
import dtos.PersonDTO;
import entities.Address;
import entities.CityInfo;
import entities.Hobby;
import entities.Person;
import entities.Phone;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

/**
 *
 * @author olive
 */
public class PersonFacadeTest {

    private static EntityManagerFactory emf;
    private static PersonFacade facade;

    public PersonFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() throws Exception {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = PersonFacade.getPersonFacade(emf);
    }

    @BeforeEach
    public void setUp() throws Exception {
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

            Address address = new Address("Flemmingvej 34 1. tv", "Bank på tre gange");
            address.setCityInfo(new CityInfo(1200, "København"));
            em.merge(address);

            Person person = new Person("test@test.com", "Anders", "Larsen");
            person.addHobby(new Hobby("Bage", "Alle bollerne"));
            person.addPhone(new Phone("23756493", "Home phone"));
            person.setAddress(address);
            em.merge(person);
            em.getTransaction().commit();

            em.getTransaction().begin();
            Address address1 = new Address("Hennigsvej 22 7. th", "Hop tre gange foran døren");
            address1.setCityInfo(new CityInfo(2970, "Hørsholm"));
            em.merge(address1);

            Person person1 = new Person("kage@fisk.com", "Lars", "Andersen");
            person1.addHobby(new Hobby("Bage", "Alle bollerne"));
            person1.addPhone(new Phone("75643927", "Ude phone"));
            person1.setAddress(address1);
            em.merge(person1);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

//    @Test
//    public void testGetPersonByPhone() {
//        PersonDTO person = facade.getPersonByPhone("23756493");
//        assertEquals("Anders", person.getFirstName());
//    }
//
//    @Test
//    public void testGetPersonsByHobby() {
//        List<PersonDTO> persons = facade.getPersonsByHobby("Bage");
//        assertEquals(2, persons.size());
//    }
//
//    @Test
//    public void testGetPersonsByCity() {
//        List<PersonDTO> persons = facade.getPersonsByCity("Hørsholm");
//        assertEquals(1, persons.size());
//    }
//
//    @Test
//    public void testGetNumberOfPersonsByHobby() {
//        HobbyCountDTO hobbyCount = facade.getNumberOfPersonsByHobby("Bage");
//        assertEquals(2, hobbyCount.getCount());
//    }
//
//    @Test
//    public void testGetAllZipCodes() {
//        List<CityInfoDTO> cityInfos = facade.getAllZipCodes();
//        assertEquals(2, cityInfos.size());
//    }
//
//    @Test
//    public void testGetPersonByID() {
//        PersonDTO person = facade.getPersonByID(1);
//        assertEquals("Anders", person.getFirstName());
//    }
//
//    @Test
//    public void testInsertPerson() {
//        PersonDTO person = facade.insertPerson(new PersonDTO(new Person("tis@mand.dk", "Henrik", "Hansen")));
//        PersonDTO personFromDB = facade.getPersonByID(3);
//        assertEquals(person.getFirstName(), personFromDB.getFirstName());
//    }
//
//    @Test
//    public void testUpdatePerson() {
//        PersonDTO person = facade.updatePerson(new PersonDTO(new Person("henning@svendsen", "Henning", "Svendsen")), 1);
//        PersonDTO personFromDB = facade.getPersonByID(1);
//        assertEquals(person.getFirstName(), personFromDB.getFirstName());
//    }
}
