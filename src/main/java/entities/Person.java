/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import dtos.PersonDTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author Magnus
 */
@Entity
@NamedQuery(name = "Person.deleteAllRows", query = "DELETE from Person")
@NamedNativeQuery(name = "Person.resetAutoIncrement", query = "ALTER TABLE Person AUTO_INCREMENT = 1;")
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String email;
    private String firstName;
    private String lastName;

    @OneToMany(mappedBy = "person", cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private List<Phone> phones;

    @ManyToOne
    private Address address;

    @ManyToMany(mappedBy = "persons", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private List<Hobby> hobbies;
    
    public Person() {}

    public Person(String email, String firstName, String lastName) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phones = new ArrayList<>();
        this.hobbies = new ArrayList<>();
        this.address = null;
    }    
    
    public void addHobby(Hobby hobby) {
        if (hobby != null) {
            this.hobbies.add(hobby);
            hobby.getPersons().add(this);
        }
    }

    public void removeHobbies(List<Hobby> hobbies) {
        if (hobbies != null) {
            this.hobbies = new ArrayList<>();
            hobbies.forEach((t) -> {
                t.setPersons(null);
            });
        }
    }
    
    public void removePhones(List<Phone> phones){
        if(phones != null){
            this.phones = new ArrayList<>();
            phones.forEach((t) ->{
                t.setPerson(null);
            });
        }
    }
    
    public void addPhone(Phone phone) {
        this.phones.add(phone);
        if (phone != null) {
            phone.setPerson(this);
        }
    }
    
    public List<Phone> getPhones() {
        return phones;
    }

    public List<Hobby> getHobbies() {
        return hobbies;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "entities.Person[ id=" + id + " ]";
    }
}
