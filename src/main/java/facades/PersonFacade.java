/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dtos.CityInfoDTO;
import dtos.HobbyCountDTO;
import dtos.HobbyDTO;
import dtos.PersonDTO;
import dtos.PhoneDTO;
import entities.Address;
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
    public static PersonFacade getPersonFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

    public PersonDTO getPersonByPhone(String number) {
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

    public List<PersonDTO> getPersonsByHobby(String hobby) {
        EntityManager em = emf.createEntityManager();
        List<Person> persons;
        try {
            TypedQuery<Person> tq = em.createQuery("SELECT p FROM Person p JOIN FETCH p.hobbies h WHERE h.name = :hobby", Person.class);
            tq.setParameter("hobby", hobby);
            persons = tq.getResultList();
        } finally {
            em.close();
        }
        return PersonDTO.getDTO(persons);
    }

    public List<PersonDTO> getPersonsByCity(String city) {
        EntityManager em = emf.createEntityManager();
        List<Person> persons;
        try {
            TypedQuery<Person> tq = em.createQuery("SELECT p FROM Person p JOIN FETCH p.address a JOIN FETCH a.cityInfo c WHERE c.city = :city", Person.class);
            tq.setParameter("city", city);
            persons = tq.getResultList();
        } finally {
            em.close();
        }

        return PersonDTO.getDTO(persons);
    }

    public HobbyCountDTO getNumberOfPersonsByHobby(String hobby) {
        EntityManager em = emf.createEntityManager();
        long count;
        try {
            Query q = em.createQuery("SELECT COUNT(p) FROM Person p JOIN FETCH p.hobbies h WHERE h.name = :hobby");
            q.setParameter("hobby", hobby);
            count = (long) q.getSingleResult();
        } finally {
            em.close();
        }
        return new HobbyCountDTO(count);
    }

    public List<CityInfoDTO> getAllZipCodes() {
        EntityManager em = emf.createEntityManager();
        List<CityInfo> cityInfos;
        try {
            TypedQuery<CityInfo> tq = em.createQuery("SELECT c FROM CityInfo c", CityInfo.class);
            cityInfos = tq.getResultList();
        } finally {
            em.close();
        }
        return CityInfoDTO.getDTO(cityInfos);
    }

    public PersonDTO getPersonByID(int id) {
        EntityManager em = emf.createEntityManager();
        Person person;
        try {
            em.getTransaction().begin();
            person = em.find(Person.class, id);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new PersonDTO(person);
    }
    
    public PersonDTO insertPerson(PersonDTO personDTO) {
        EntityManager em = emf.createEntityManager();
        Person personToInsert = null;
        boolean addresUpdated = false;
        try {
            em.getTransaction().begin();
            Address address = null;
            if(personDTO.getAddress() != null){
                address = new Address(personDTO.getAddress().getStreet(),personDTO.getAddress().getAdditionalInfo());
                addresUpdated = true;
                if(personDTO.getAddress().getCityInfo() != null){
                    address.setCityInfo(new CityInfo(personDTO.getAddress().getCityInfo().getZipCode(),personDTO.getAddress().getCityInfo().getCity()));
                    em.merge(address);
                }
                if(personDTO.getAddress().getCityInfo() == null){
                    em.merge(address);
                }
            }
            if(personDTO.getEmail() != null && personDTO.getFirstName() != null && personDTO.getLastName() != null){
                personToInsert = new Person(personDTO.getEmail(),personDTO.getFirstName(),personDTO.getLastName());
                if(personDTO.getHobbies() != null){
                    for(HobbyDTO h: personDTO.getHobbies()){
                        personToInsert.addHobby(new Hobby(h.getName(), h.getDescription()));
                    }
                }
                if(personDTO.getPhones() != null){
                    for (PhoneDTO h : personDTO.getPhones()) {
                        personToInsert.addPhone(new Phone(h.getNumber(), h.getDescription()));
                    }
                }
            }
            if(addresUpdated && address != null){
                personToInsert.setAddress(address);
            }
            em.merge(personToInsert);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new PersonDTO(personToInsert);
    }

    public PersonDTO updatePerson(PersonDTO personDTO, int id) {
        EntityManager em = emf.createEntityManager();
        Person personToUpdate;
        try {
            em.getTransaction().begin();
            
            personToUpdate = em.find(Person.class, id);
            
            if(personDTO.getFirstName() != null){
                personToUpdate.setFirstName(personDTO.getFirstName());
            }
            if(personDTO.getLastName() != null){
                personToUpdate.setLastName(personDTO.getLastName());
            }
            if(personDTO.getEmail() != null){
                personToUpdate.setEmail(personDTO.getEmail());
            }            
            if(personDTO.getAddress() != null){
                if(personDTO.getAddress().getAdditionalInfo() != null){
                    personToUpdate.getAddress().setAdditionalInfo(personDTO.getAddress().getAdditionalInfo());
                }
                if(personDTO.getAddress().getCityInfo() != null){
                    personToUpdate.getAddress().setStreet(personDTO.getAddress().getStreet());
                }
                if(personDTO.getAddress().getCityInfo() != null){
                    if(personDTO.getAddress().getCityInfo().getCity() != null){
                        personToUpdate.getAddress().getCityInfo().setCity(personDTO.getAddress().getCityInfo().getCity());
                    }
                    if(personDTO.getAddress().getCityInfo().getZipCode() != 0){
                        personToUpdate.getAddress().getCityInfo().setZipCode(personDTO.getAddress().getCityInfo().getZipCode());
                    }
                }
            }
            if(personDTO.getHobbies() != null){
                personToUpdate.getHobbies().forEach((t) -> {
                    em.remove(t);
                });
                personToUpdate.removeHobbies(personToUpdate.getHobbies());
                personDTO.getHobbies().forEach((t) -> {
                    personToUpdate.addHobby(new Hobby(t.getName(),t.getDescription()));
                });
            }
            if(personDTO.getPhones() != null ){
                personToUpdate.getPhones().forEach((t) ->{
                    em.remove(t);
                });
                personToUpdate.removePhones(personToUpdate.getPhones());
                personDTO.getPhones().forEach((t) ->{
                    personToUpdate.addPhone(new Phone(t.getNumber(),t.getDescription()));
                });
            }
            em.merge(personToUpdate);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new PersonDTO(personToUpdate);
    }
    
    public PersonDTO addHobbyToPerson(HobbyDTO hobbyDTO, int id){
        EntityManager em = emf.createEntityManager();
        Person personToUpdate;
        try{
            em.getTransaction().begin();
            personToUpdate = em.find(Person.class, id);
            personToUpdate.addHobby(new Hobby(hobbyDTO.getName(), hobbyDTO.getDescription()));
            em.merge(personToUpdate);
            em.getTransaction().commit();
        }finally{
            em.close();
        }
        return new PersonDTO(personToUpdate);
    }
    
    public PersonDTO removeHobbyFromPerson(HobbyDTO hobbyDTO, int id) {
        EntityManager em = emf.createEntityManager();
        Person personToUpdate;
        try{
            em.getTransaction().begin();
            personToUpdate = em.find(Person.class, id);
            Hobby hobbyToRemove = personToUpdate.removeHobby(hobbyDTO);
            em.remove(hobbyToRemove);
            em.merge(personToUpdate);
            em.getTransaction().commit();
        }finally{
            em.close();
        }
        return new PersonDTO(personToUpdate);
    }

    public PersonDTO addPhoneToPerson(PhoneDTO phoneDTO, int personId) {
        EntityManager em = emf.createEntityManager();
        Person personToUpdate;
        try {
            em.getTransaction().begin();
            personToUpdate = em.find(Person.class, personId);
            personToUpdate.addPhone(new Phone(phoneDTO.getNumber(), phoneDTO.getDescription()));
            em.merge(personToUpdate);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new PersonDTO(personToUpdate);
    }
    
    public PersonDTO removePhoneFromPerson(PhoneDTO phoneDTO, int id) {
        EntityManager em = emf.createEntityManager();
        Person personToUpdate;
        try {
            em.getTransaction().begin();
            personToUpdate = em.find(Person.class, id);
            Phone phoneToRemove = personToUpdate.removePhone(phoneDTO);
            em.remove(phoneToRemove);
            em.merge(personToUpdate);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new PersonDTO(personToUpdate);
    }

    public PersonDTO deletePerson(int id) {
        EntityManager em = emf.createEntityManager();
        Person person;
        try {
            em.getTransaction().begin();
            person = em.find(Person.class, id);
            if(person.getPhones() != null){
                person.getPhones().forEach((t) -> {
                    em.remove(t);
                });
            }
            if(person.getAddress() != null){
                em.remove(person.getAddress());
                if(person.getAddress().getCityInfo() != null){
                    em.remove(person.getAddress().getCityInfo());
                }
            }
            if(person.getHobbies() != null){
                person.getHobbies().forEach((t) -> {
                    em.remove(t);
                });
            }
            em.remove(person);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new PersonDTO(person);
    }
}
