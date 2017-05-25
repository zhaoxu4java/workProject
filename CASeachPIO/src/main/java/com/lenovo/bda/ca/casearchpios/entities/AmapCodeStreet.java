package com.lenovo.bda.ca.casearchpios.entities;

/**
 * Created by lenovo on 2017/5/24.
 */
public class AmapCodeStreet {
    private Integer id;
    private String amap_id;
    private String city_code;
    private String province;
    private String province_adcode;
    private String city;
    private String city_adcode;
    private String district;
    private String district_adcode;
    private String street;
    private String street_adcode;
    private String street_center;

    public AmapCodeStreet() {
    }

    public AmapCodeStreet(Integer id, String amap_id, String city_code, String province, String province_adcode, String city, String city_adcode, String district, String district_adcode, String street, String street_adcode, String street_center) {

        this.id = id;
        this.amap_id = amap_id;
        this.city_code = city_code;
        this.province = province;
        this.province_adcode = province_adcode;
        this.city = city;
        this.city_adcode = city_adcode;
        this.district = district;
        this.district_adcode = district_adcode;
        this.street = street;
        this.street_adcode = street_adcode;
        this.street_center = street_center;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAmap_id() {
        return amap_id;
    }

    public void setAmap_id(String amap_id) {
        this.amap_id = amap_id;
    }

    public String getCity_code() {
        return city_code;
    }

    public void setCity_code(String city_code) {
        this.city_code = city_code;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProvince_adcode() {
        return province_adcode;
    }

    public void setProvince_adcode(String province_adcode) {
        this.province_adcode = province_adcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity_adcode() {
        return city_adcode;
    }

    public void setCity_adcode(String city_adcode) {
        this.city_adcode = city_adcode;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDistrict_adcode() {
        return district_adcode;
    }

    public void setDistrict_adcode(String district_adcode) {
        this.district_adcode = district_adcode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreet_adcode() {
        return street_adcode;
    }

    public void setStreet_adcode(String street_adcode) {
        this.street_adcode = street_adcode;
    }

    public String getStreet_center() {
        return street_center;
    }

    public void setStreet_center(String street_center) {
        this.street_center = street_center;
    }
}
