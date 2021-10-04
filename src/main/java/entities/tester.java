package entities;

import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class tester
{


    public static void main(String[] args)
    {
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        CityInfo cityInfo = new CityInfo(3400, "Hillerød");
        Address a1 = new Address("Jordbærvej", "Den bedste vej");
        Person p1 = new Person("jatak@gmail.com", "Jens","Olsen");
        Phone phone = new Phone("18181818", "pizza maaan");

        cityInfo.addAddress(a1);
        a1.addPerson(p1);
        p1.addPhone(phone);
        em.getTransaction().begin();
        em.persist(p1);

        em.getTransaction().commit();

    }
}
