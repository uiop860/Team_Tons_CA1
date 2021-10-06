/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.Address;
import entities.CityInfo;
import entities.Hobby;
import entities.Person;
import entities.Phone;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import utils.EMF_Creator;

/**
 *
 * @author tha
 */
public class Populator {

    public static void populate() {
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            Person person = new Person("test@test.com", "Anders", "Larsen");
            person.addHobby(new Hobby("Bage", "Alle bollerne"));
            person.addPhone(new Phone("23756493", "Home phone"));
            Address address = new Address("Flemmingvej 34 1. tv", "Bank på tre gange");
            em.persist(address);
            CityInfo cityInfo = new CityInfo(1200, "København");
            em.persist(cityInfo);
            address.setCityInfo(cityInfo);
            em.merge(address);
            em.persist(person);
            person.setAddress(address);
            em.merge(person);

            Person person1 = new Person("kage@fisk.com", "Lars", "Andersen");
            person1.addHobby(new Hobby("Bage", "Alle bollerne"));
            person1.addPhone(new Phone("75643927", "Ude phone"));
            Address address1 = new Address("Hennigsvej 22 7. th", "Hop tre gange foran døren");
            em.persist(address1);
            CityInfo cityInfo1 = new CityInfo(2970, "Hørsholm");
            em.persist(cityInfo1);
            address1.setCityInfo(cityInfo1);
            em.merge(address1);
            em.persist(person1);
            person1.setAddress(address1);
            em.merge(person1);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public static void main(String[] args) {
        populate();
    }
}
