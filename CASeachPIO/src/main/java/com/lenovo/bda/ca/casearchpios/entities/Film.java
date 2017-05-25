package com.lenovo.bda.ca.casearchpios.entities;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.lenovo.bda.ca.casearchpios.utilities.JsonUtils;

public class Film {
	private Integer ID;
	private String film_name;
	private String screendate;//上映日期
	private String duration;//时长
	private String type;//电影类型
	private String director;//导演
	private String region;//电影所属国家
	private String actors;//演员
	private Float score;//评分
	private String url;
	private String update_time;//记录更新时间
	private String image_name;
	private String index_text;
	
	
	public String getIndex_text() {
		
		
		return index_text;
	}

	public void setIndex_text(String index_text) {
		if(this.index_text == null){
			this.index_text=this.film_name+"\t"+this.actors+"\t"+this.director+"\t"+this.region+"\t"+this.type;
		}else{
			this.index_text = index_text;
		}
	}
	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

	public String getFilm_name() {
		return film_name;
	}

	public void setFilm_name(String film_name) {
		this.film_name = JsonUtils.CleanUpSpecialChars(film_name);
	}

	public String getScreendate() {
		return screendate;
	}

	public void setScreendate(String screendate) {
		this.screendate = JsonUtils.CleanUpSpecialChars(screendate);
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = JsonUtils.CleanUpSpecialChars(duration);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = JsonUtils.CleanUpSpecialChars(type);
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = JsonUtils.CleanUpSpecialChars(director);
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = JsonUtils.CleanUpSpecialChars(region);
	}

	public String getActors() {
		return actors;
	}

	public void setActors(String actors) {
		this.actors = JsonUtils.CleanUpSpecialChars(actors);
	}

	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
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

	public String getImage_name() {
		return image_name;
	}

	public void setImage_name(String image_name) {
		this.image_name = image_name;
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
	public static Film GetInstance(String jsonString) {
		if (StringUtils.isEmpty(jsonString)) {
			return null;
		}

		try {
			JSONObject obj = JSONObject.fromObject(jsonString);
			Film entity = (Film) JSONObject.toBean(obj, Film.class);

			return entity;
		} catch (Exception e) {
			return null;
		}
	}
}
