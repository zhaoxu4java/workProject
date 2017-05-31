package com.lenovo.bda.ca.casearchpios.entities;

import net.sf.json.JSONObject;

import java.util.Date;

/**
 * Created by lenovo on 2017/5/24.
 */
public class POI {
    private Integer id;
    private String poiid;
    private String name;
    private String tag;
    private String type;
    private String typecode;
    private String address;
    private Double location1;
    private Double location2;
    private String tel;
    private String postcode;
    private String website;
    private String email;
    private String pcode;
    private String pname;
    private String citycode;
    private String cityname;
    private String adcode;
    private String adname;
    private String gridcode;
    private String navi_poiid;
    private String entr_location;
    private String alias;
    private String photos;
    private Date insertTime;

    public POI() {
    }

    public POI(Integer id, String poiid, String name, String tag, String type, String typecode, String address, Double location1,Double location2, String tel, String postcode, String website, String email, String pcode, String pname, String citycode, String cityname, String adcode, String adname, String gridcode, String navi_poiid, String entr_location, String alias, String photos, Date insertTime) {

        this.id = id;
        this.poiid = poiid;
        this.name = name;
        this.tag = tag;
        this.type = type;
        this.typecode = typecode;
        this.address = address;
        this.location1 = location1;
        this.location2 = location2;
        this.tel = tel;
        this.postcode = postcode;
        this.website = website;
        this.email = email;
        this.pcode = pcode;
        this.pname = pname;
        this.citycode = citycode;
        this.cityname = cityname;
        this.adcode = adcode;
        this.adname = adname;
        this.gridcode = gridcode;
        this.navi_poiid = navi_poiid;
        this.entr_location = entr_location;
        this.alias = alias;
        this.photos = photos;
        this.insertTime = insertTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPoiid() {
        return poiid;
    }

    public void setPoiid(String poiid) {
        this.poiid = poiid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypecode() {
        return typecode;
    }

    public void setTypecode(String typecode) {
        this.typecode = typecode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLocation1() {
        return location1;
    }

    public void setLocation1(Double location1) {
        this.location1 = location1;
    }
    public Double getLocation2() {
        return location2;
    }

    public void setLocation2(Double location2) {
        this.location2 = location2;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public String getAdcode() {
        return adcode;
    }

    public void setAdcode(String adcode) {
        this.adcode = adcode;
    }

    public String getAdname() {
        return adname;
    }

    public void setAdname(String adname) {
        this.adname = adname;
    }

    public String getGridcode() {
        return gridcode;
    }

    public void setGridcode(String gridcode) {
        this.gridcode = gridcode;
    }

    public String getNavi_poiid() {
        return navi_poiid;
    }

    public void setNavi_poiid(String navi_poiid) {
        this.navi_poiid = navi_poiid;
    }

    public String getEntr_location() {
        return entr_location;
    }

    public void setEntr_location(String entr_location) {
        this.entr_location = entr_location;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }
    public String toJsonString() {
        JSONObject jsonObj = JSONObject.fromObject(this);

        return jsonObj.toString();
    }
}
