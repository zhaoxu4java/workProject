package com.lenovo.bda.ca.casearchpios.entities;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.lenovo.bda.ca.casearchpios.utilities.JsonUtils;

public class Cinema {
	private Integer ID;
	private String cinema_name;//电影院名称
	private String amap_name;
	private String amap_id;
	private String province;//所在省
	private String city;//市
	private String area;//区
	private String addr;//电影院地址
	private Float score;//电影院评分
	private String tel;//电话
	private String service;//影院服务
	private String store_service;//影院门店服务
	private String description;//门店介绍
	private String url;//url地址
	private String update_time;//记录更新时间
	private String index_text;
	
	public Integer getID() {
		return ID;
	}
	public void setID(Integer iD) {
		ID = iD;
	}
	public String getCinema_name() {
		return cinema_name;
	}
	public void setCinema_name(String cinema_name) {
		this.cinema_name = JsonUtils.CleanUpSpecialChars(cinema_name);
	}
	
	
	public String getAmap_name() {
		return amap_name;
	}
	public void setAmap_name(String amap_name) {
		this.amap_name =  JsonUtils.CleanUpSpecialChars(amap_name);
	}
	public String getAmap_id() {
		return amap_id;
	}
	public void setAmap_id(String amap_id) {
		this.amap_id = amap_id;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = JsonUtils.CleanUpSpecialChars(province);
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = JsonUtils.CleanUpSpecialChars(city);
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = JsonUtils.CleanUpSpecialChars(area);
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = JsonUtils.CleanUpSpecialChars(addr);
	}
	public Float getScore() {
		return score;
	}
	public void setScore(Float score) {
		this.score = score;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = JsonUtils.CleanUpSpecialChars(service);
	}
	public String getStore_service() {
		return store_service;
	}
	public void setStore_service(String store_service) {
		this.store_service = JsonUtils.CleanUpSpecialChars(store_service);
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = JsonUtils.CleanUpSpecialChars(description);
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	
	public String getIndex_text() {

		return index_text;
	}

	public void setIndex_text(String index_text) {
		if(this.index_text == null){
			this.index_text = this.cinema_name+"\t"+this.amap_name
					+"\t"+this.addr+"\t"+this.area
					+"\t"+this.city+"\t"+this.province
					+"\t"+this.description+"\t"+this.service
					+"\t"+this.store_service;
		}else{
		this.index_text = index_text;
		}
	}
	public String toJsonString() {
		JSONObject jsonObj = JSONObject.fromObject(this);

		return jsonObj.toString();
	}

	/**
	 * Convert jsonString to Film instance
	 * 
	 * @param jsonString
	 *            json string
	 * @return
	 */
	public static Cinema GetInstance(String jsonString) {
		if (StringUtils.isEmpty(jsonString)) {
			return null;
		}

		try {
			JSONObject obj = JSONObject.fromObject(jsonString);
			Cinema entity = (Cinema) JSONObject.toBean(obj, Cinema.class);

			return entity;
		} catch (Exception e) {
			return null;
		}
	}

	
}
