/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.text.Collator;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 *
 * @author olive
 */
public class PersonDTOSorter {

    public PersonDTOSorter() {
    }
    
    public static List<PersonDTO> personDTOSort(List<PersonDTO> personsDTO){
        
        
        personsDTO.forEach((t) -> {
            if(t.getHobbies() != null){
                Collections.sort(t.getHobbies(), (a, b) -> a.getName().compareToIgnoreCase(b.getName()));
            }
            if(t.getPhones() != null){
                Collections.sort(t.getPhones(), (a, b) -> a.getDescription().compareToIgnoreCase(b.getDescription()));
            }
        });
        
        Collections.sort(personsDTO, (a, b) -> a.getFirstName().compareToIgnoreCase(b.getFirstName()));
        
        return personsDTO;
    }
    
}
