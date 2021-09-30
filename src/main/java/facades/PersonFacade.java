/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dtos.PersonDTO;
import dtos.PersonListDTO;
import entities.CityInfo;
import entities.Hobby;
import entities.Person;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author olive
 */
public class PersonFacade {
    
    private static PersonFacade instance;
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
    
    public PersonDTO getPersonByPhone(String phone){
        EntityManager em = emf.createEntityManager();
        Person person;
        try {
            TypedQuery<Person> tq = em.createQuery("SELECT p FROM Person p WHERE p.phone = :phone", Person.class);
            tq.setParameter("phone", phone);
            person = tq.getSingleResult();
        } finally {
            em.close();
        }
        return new PersonDTO(person);
    }
    
    public List<PersonDTO> getPersonsByHobby(Hobby hobby){
        EntityManager em = emf.createEntityManager();
        List<Person> persons;
        try{
            TypedQuery<Person> tq = em.createQuery("SELECT p FROM Person p WHERE p.hobby = :hobby", Person.class);
            tq.setParameter("hobby", hobby);
            persons = tq.getResultList();
        }finally{
            em.close();
        }
        return new PersonListDTO(persons).getPersonsDTO();
    } 
    
    public List<PersonDTO> getPersonsByCity(String city){
        EntityManager em = emf.createEntityManager();
        List<Person> persons;
        try{
            TypedQuery<Person> tq = em.createQuery("SELECT p FROM Person p WHERE p.cityInfo = city", Person.class);
            tq.setParameter("city", city);
            persons = tq.getResultList();
        }finally{
            em.close();
        }
        return new PersonListDTO(persons).getPersonsDTO();
    }
    
    public long getNumberOfPersonsByHobby(String hobby){
        EntityManager em = emf.createEntityManager();
        long count;
        try{
            Query q = em.createQuery("SELECT COUNT(p) FROM Person p WHERE p.hobby = :hobby");
            q.setParameter("hobby", hobby);
            count = (long) q.getSingleResult();
        }finally{
            em.close();
        }
        return count;
    }
    
    public List<CityInfo> getAllZipCodes(){
        EntityManager em = emf.createEntityManager();
        List<CityInfo> cityInfo;
        try{
            TypedQuery<CityInfo> tq = em.createQuery("", CityInfo.class);
            
        }finally{
            
        }
        return null;
    }
    
    public Person insertPerson(){
        return null;
    }
    
    public Person updatePerson(){
        return null;
    }
}
