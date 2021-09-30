/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.CityInfo;

/**
 *
 * @author olive
 */
public class CityInfoDTO {
    
    private int zipCode;
    
    public CityInfoDTO(CityInfo cityInfo){
        this.zipCode = cityInfo.getZipCode;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }
}
