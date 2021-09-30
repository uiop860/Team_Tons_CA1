/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author Magnus
 */
@Entity
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String street;
    private String addionalInfo;

    @OneToMany(mappedBy = "address", cascade = CascadeType.PERSIST)
    private List<Person> persons;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private CityInfo cityInfo;



    public Address(String street, String addionalInfo)
    {
        this.street = street;
        this.addionalInfo = addionalInfo;
        this.persons = new ArrayList<>();
    }

    public Address()
    {
    }

    public CityInfo getCityInfo()
    {
        return cityInfo;
    }

    public void setCityInfo(CityInfo cityInfo)
    {
        this.cityInfo = cityInfo;
    }

    public String getStreet()
    {
        return street;
    }

    public void setStreet(String street)
    {
        this.street = street;
    }

    public String getAddionalInfo()
    {
        return addionalInfo;
    }

    public void setAddionalInfo(String addionalInfo)
    {
        this.addionalInfo = addionalInfo;
    }

    public List<Person> getPersons()
    {
        return persons;
    }

    public void addPerson(Person person)
    {
        this.persons.add(person);
        if(person != null) {
            person.setAddress(this);
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    

    @Override
    public String toString() {
        return "entities.Address[ id=" + id + " ]";
    }
    
}
