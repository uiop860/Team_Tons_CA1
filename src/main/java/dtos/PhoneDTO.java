/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.Person;
import entities.Phone;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author olive
 */
public class PhoneDTO {
    
    private String number;
    private String description;

    public PhoneDTO() {}
    
    public PhoneDTO(Phone phone){
        this.number = phone.getNumber();
        this.description = phone.getDescription();
    }
    
    public static List<PhoneDTO> getDTO(List<Phone> phones){
        List<PhoneDTO> phonesDTO = new ArrayList<>();
        phones.forEach(x -> phonesDTO.add(new PhoneDTO(x)));
        return phonesDTO;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
