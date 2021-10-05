/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.Address;

/**
 *
 * @author olive
 */
public class AddressDTO {
    
    
    private String street;
    private String additionalInfo;
    private CityInfoDTO cityInfo;

    public AddressDTO() {}
    
    public AddressDTO(Address address){
        this.street = address.getStreet();
        this.additionalInfo = address.getAdditionalInfo();
        this.cityInfo = new CityInfoDTO(address.getCityInfo());
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public CityInfoDTO getCityInfo() {
        return cityInfo;
    }

    public void setCityInfo(CityInfoDTO cityInfo) {
        this.cityInfo = cityInfo;
    }
}
