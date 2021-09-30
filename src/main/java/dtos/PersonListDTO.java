/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.Person;
import java.util.List;

/**
 *
 * @author olive
 */
public class PersonListDTO {
    
    private List<PersonDTO> personsDTO;
    
    public PersonListDTO(List<Person> persons){
        persons.forEach(p -> {
            personsDTO.add(new PersonDTO(p));
        });
    }

    public List<PersonDTO> getPersonsDTO() {
        return personsDTO;
    }

    public void setPersonsDTO(List<PersonDTO> personsDTO) {
        this.personsDTO = personsDTO;
    }
}
