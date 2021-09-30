/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.CityInfo;
import entities.Hobby;
import entities.Person;
import entities.Phone;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * @author olive
 */
public class PersonFacade {
    
    private static FacadeExample instance;
    private static EntityManagerFactory emf;
    
    private PersonFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static PersonFacade getFacadeExample(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }
    
    public Person getPersonByPhone(String phone){
        EntityManager em = emf.createEntityManager();
        Person person;
        try {
            TypedQuery<Person> tq = em.createQuery("SELECT p FROM Person p WHERE p.phone = :phone", Person.class);
            tq.setParameter("phone", phone);
            person = tq.getSingleResult();
        } finally {
            em.close();
        }
        return null;
    }
    
    public List<Person> getPersonsByHobby(Hobby hobby){
        
        return null;
    } 
    
    public List<Person> getPersonsByCity(String city){
        return null;
    }
    
    public long getNumberOfPersonsByHobby(String hobby){
        return 0;
    }
    
    public List<CityInfo> getAllZipCodes(){
        return null;
    }
    
    public Person insertPerson(){
        return null;
    }
    
    public Person updatePerson(){
        return null;
    }
}
