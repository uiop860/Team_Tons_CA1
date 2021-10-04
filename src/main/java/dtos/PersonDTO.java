/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.Address;
import entities.CityInfo;
import entities.Hobby;
import entities.Person;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author olive
 */
public class PersonDTO {

    private String email;
    private String firstName;
    private String lastName;
    private Address address;
    private List<Hobby> hobby;

    public PersonDTO(Person person) {
        this.email = person.getEmail();
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        if (person.getAddress() != null) {
            this.address = new Address(person.getAddress().getStreet(), person.getAddress().getAdditionalInfo());
            if (person.getAddress().getCityInfo() != null) {
                this.address.setCityInfo(new CityInfo(person.getAddress().getCityInfo().getZipCode(), person.getAddress().getStreet()));
            }
        }
        if (person.getHobbies() != null || person.getHobbies().isEmpty()) {
            hobby = new ArrayList<>();
            person.getHobbies().forEach(x -> this.hobby.add(new Hobby(x.getName(), x.getDescription())));
//            for(Hobby h: person.getHobbies()){
//                hobby = new ArrayList<>();
//                this.hobby.add(new Hobby(h.getName(), h.getDescription()));
//            }
        }
    }

    public static List<PersonDTO> getDTO(List<Person> persons) {
        if (persons != null) {
            List<PersonDTO> personDTO = new ArrayList<>();
            persons.forEach(p -> personDTO.add(new PersonDTO(p)));
            return personDTO;
        } else {
            return null;
        }
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
}
