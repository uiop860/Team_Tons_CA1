/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.CityInfo;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author olive
 */
public class CityInfoDTO {

    private int zipCode;
    private String city;

    public CityInfoDTO() {
    }

    public CityInfoDTO(CityInfo cityInfo) {
        this.zipCode = cityInfo.getZipCode();
        this.city = cityInfo.getCity();
    }

    public static List<CityInfoDTO> getDTO(List<CityInfo> cityInfos) {
        List<CityInfoDTO> cityInfoDTOs = new ArrayList<>();
        cityInfos.forEach(p -> cityInfoDTOs.add(new CityInfoDTO(p)));
        return cityInfoDTOs;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
