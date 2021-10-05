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
    public static void populate(){
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        
        Person person = new Person("test@test.com", "Anders", "Larsen");
        Address address = new Address("Flemmingvej 34 1. tv", "Bank på tre gange");
       CityInfo cityInfo = new CityInfo(10310, "jordbærby");
        person.addHobby(new Hobby("Bage", "Alle bollerne"));
        person.addPhone(new Phone("23756493", "Home phone"));
        address.addPerson(person);
        cityInfo.addAddress(address);

        Person person1 = new Person("kage@fisk.com", "Lars", "Andersen");
        Address address1 = new Address("Hennigsvej 22 7. th", "Hop tre gange foran døren");
       // CityInfo cityInfo1 = new CityInfo(2970, "Hørsholm");
        person1.addHobby(new Hobby("Bage", "Alle bollerne"));
        person1.addPhone(new Phone("75643927", "Ude phone"));
        address1.addPerson(person1);
       // cityInfo1.addAddress(address1);

        em.getTransaction().begin();
        em.persist(person);
        em.getTransaction().commit();

        em.getTransaction().begin();
        em.persist(person1);
        em.getTransaction().commit();
    }
    
    public static void main(String[] args) {
        populate();
    }
}
