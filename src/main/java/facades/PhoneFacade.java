package facades;

import javax.persistence.EntityManagerFactory;

public class PhoneFacade {

    private static PhoneFacade instance;
    private static EntityManagerFactory emf;

    private PhoneFacade() {

    }

//    /**
//     *
//     * @param _emf
//     * @return an instance of this facade class.
//     */
//    public static PhoneFacade getPhoneFacade(EntityManagerFactory _emf) {
//        if (instance == null) {
//            emf = _emf;
//            instance = new PhoneFacade();
//        }
//        return instance;
//    }
//
//    public PersonDTO deletePhone(PhoneDTO phoneDTO, int id) {
//        EntityManager em = emf.createEntityManager();
//        Person personToUpdate;
//        try {
//            em.getTransaction().begin();
//            personToUpdate = em.find(Person.class, id);
//            Phone phoneToRemove = personToUpdate.removePhone(phoneDTO);
//            em.remove(phoneToRemove);
//            em.merge(personToUpdate);
//            em.getTransaction().commit();
//        } finally {
//            em.close();
//        }
//        return new PersonDTO(personToUpdate);
//    }
//
//    public PersonDTO addPhone(PhoneDTO phoneDTO, int personId) {
//        EntityManager em = emf.createEntityManager();
//        Person personToUpdate;
//        try {
//            em.getTransaction().begin();
//            personToUpdate = em.find(Person.class, personId);
//            personToUpdate.addPhone(new Phone(phoneDTO.getNumber(),phoneDTO.getDescription()));
//            em.merge(personToUpdate);
//            em.getTransaction().commit();
//        } finally {
//            em.close();
//        }
//        return new PersonDTO(personToUpdate);
//    }
}
