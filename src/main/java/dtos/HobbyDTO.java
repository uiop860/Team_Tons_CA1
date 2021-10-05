/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.Hobby;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author olive
 */
public class HobbyDTO {
    
    private String name;
    private String description;

    public HobbyDTO() {}
    
    public HobbyDTO(Hobby hobby){
        this.name = hobby.getName();
        this.description = hobby.getDescription();
    }
    
    public static List<HobbyDTO> getDTO(List<Hobby> hobbies){
        List<HobbyDTO> hobbiesDTO = new ArrayList<>();
        hobbies.forEach(x -> hobbiesDTO.add(new HobbyDTO(x)));
        return hobbiesDTO;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
