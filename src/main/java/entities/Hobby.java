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
@NamedQuery(name = "Hobby.deleteAllRows", query = "DELETE from Hobby")
@NamedNativeQuery(name = "Hobby.resetAutoIncrement", query = "ALTER TABLE Hobby AUTO_INCREMENT = 1;")
public class Hobby implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;

    @ManyToMany()
    private List<Person> persons;

    public Hobby(String name, String description)
    {
        this.name = name;
        this.description = description;
        this.persons = new ArrayList<>();
    }

    public Hobby()
    {
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public List<Person> getPersons()
    {
        return persons;
    }

    public void setPersons(List<Person> persons)
    {
        this.persons = persons;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

  

    @Override
    public String toString() {
        return "entities.Hobby[ id=" + id + " ]";
    }
    
}
