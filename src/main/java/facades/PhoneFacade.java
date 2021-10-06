package facades;

import dtos.HobbyDTO;
import dtos.PersonDTO;
import dtos.PhoneDTO;
import entities.Hobby;
import entities.Person;
import entities.Phone;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

public class PhoneFacade
{
    private static PhoneFacade instance;
    private static EntityManagerFactory emf;

    private PhoneFacade() {

    }
    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */

    public static PhoneFacade getPhoneFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PhoneFacade();
        }
        return instance;
    }

    public PhoneDTO deletePhone(String number)
    {
        EntityManager em = emf.createEntityManager();
        Phone ph;
        try {
            em.getTransaction().begin();
            TypedQuery<Phone> query = em.createQuery("SELECT ph FROM Phone ph WHERE ph.number = :number", Phone.class);
            query.setParameter("number", number);
            ph = query.getSingleResult();
            em.remove(ph);
            em.getTransaction().commit();
        } finally
        {
            em.close();
        }
        return new PhoneDTO(ph);
    }

    public PhoneDTO addPhone(Phone phone, int personId){
        EntityManager em = emf.createEntityManager();
        Person person;
        try{
            em.getTransaction().begin();
            person = em.find(Person.class, personId);
            person.addPhone(phone);
            em.merge(person);
            em.getTransaction().commit();
        }finally{
            em.close();
        }
        return new PhoneDTO(phone);
    }
}
