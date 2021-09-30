/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.Person;
import entities.Phone;
import entities.RenameMe;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
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
    private static FacadeExample facade;
    
    public PersonFacadeTest(String testName) {
    }

    @BeforeAll
    public static void setUpClass() throws Exception {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = FacadeExample.getFacadeExample(emf);
    }

    @AfterAll
    public static void tearDownClass() throws Exception {
    }

    @BeforeEach
    public void setUp() throws Exception {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
//            em.createNamedQuery("RenameMe.deleteAllRows").executeUpdate();
            em.persist(new Person());
            em.persist(new Person());

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() throws Exception {
    }

    /**
     * Test of getPersonByPhone method, of class PersonFacade.
     */
    @Test
    public void testGetPersonByPhone() {

    }
    
}
