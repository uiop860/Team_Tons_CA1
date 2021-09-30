/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dtos.CityInfoDTO;
import dtos.PersonDTO;
import entities.CityInfo;
import entities.Hobby;
import entities.Person;
import entities.Phone;
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
    
    public PersonDTO getPersonByPhone(String number){
        EntityManager em = emf.createEntityManager();
        Person person;
        try {
            TypedQuery<Person> tq = em.createQuery("SELECT p FROM Person p JOIN FETCH p.phones ph WHERE ph.number = :number", Person.class);
            tq.setParameter("number", number);
            person = tq.getSingleResult();
        } finally {
            em.close();
        }
        return new PersonDTO(person);
    }
    
    public List<PersonDTO> getPersonsByHobby(String hobby){
        EntityManager em = emf.createEntityManager();
        List<Person> persons;
        try{
            TypedQuery<Person> tq = em.createQuery("SELECT p FROM Person p JOIN FETCH p.hobbies h WHERE h.name = :hobby", Person.class);
            tq.setParameter("hobby", hobby);
            persons = tq.getResultList();
        }finally{
            em.close();
        }
        return PersonDTO.getDTO(persons);
    } 
    
    public List<PersonDTO> getPersonsByCity(String city){
        EntityManager em = emf.createEntityManager();
        List<Person> persons;
        try{
            TypedQuery<Person> tq = em.createQuery("SELECT p FROM Person p JOIN FETCH p.address a JOIN FETCH a.cityInfo c WHERE c.city = :city", Person.class);
            tq.setParameter("city", city);
            persons = tq.getResultList();
        }finally{
            em.close();
        }
        
        return PersonDTO.getDTO(persons);
    }
    
    public long getNumberOfPersonsByHobby(String hobby){
        EntityManager em = emf.createEntityManager();
        long count;
        try{
            Query q = em.createQuery("SELECT COUNT(p) FROM Person p JOIN FETCH p.hobbies h WHERE h.name = :hobby");
            q.setParameter("hobby", hobby);
            count = (long) q.getSingleResult();
        }finally{
            em.close();
        }
        return count;
    }
    
    public List<CityInfoDTO> getAllZipCodes(){
        EntityManager em = emf.createEntityManager();
        List<CityInfo> cityInfos;
        try{
            TypedQuery<CityInfo> tq = em.createQuery("SELECT c FROM CityInfo c", CityInfo.class);
            cityInfos = tq.getResultList();
        }finally{
            em.close();
        }
        return CityInfoDTO.getDTO(cityInfos);
    }
    
    public PersonDTO getPersonByID(int id){
        EntityManager em = emf.createEntityManager();
        PersonDTO person;
        try{
            em.getTransaction().begin();
            person = new PersonDTO(em.find(Person.class, id));
            em.getTransaction().commit();
        }finally{
            em.close();
        }
        return person;
    }
    
    public PersonDTO insertPerson(Person person){
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(person);
            em.getTransaction().commit();
        }finally{
            em.close();
        }
        return new PersonDTO(person);
    }
    
    public PersonDTO updatePerson(Person person, int id){
        EntityManager em = emf.createEntityManager();
        Person updatePerson;
        try{
            em.getTransaction().begin();
            updatePerson = em.find(Person.class, id);
            updatePerson.updatePerson(person);
            em.getTransaction().commit();
        }finally{
            em.close();
        }
        return new PersonDTO(updatePerson);
    }
}
