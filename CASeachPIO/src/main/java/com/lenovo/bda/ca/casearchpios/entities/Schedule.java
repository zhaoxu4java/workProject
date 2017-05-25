package com.lenovo.bda.ca.casearchpios.entities;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.lenovo.bda.ca.casearchpios.utilities.JsonUtils;

public class Schedule {
	private Integer ID;
	private Integer cinema_id;
	private Integer film_id;
	private String cinema_name;//电影院
	private String film_name;//电影
	private String screen_date;//排期时间(播放日期)
	private String start_time;//开始时间
	private Integer start_time_sort=-1;
	private String end_time;//结束时间
	private String lang_version;//语言版本
	private String screen_hall;//放映厅
	private Float price;//价格
	private String crawldate;//爬取日期
	private String url;
	private String update_time;//记录更新时间
	
	//cinema info
	private String cinema_amap_name;
	private String cinema_amap_id;
	private String cinema_province;//所在省
	private String cinema_city;//市
	private String cinema_area;//区
	private String cinema_addr;//电影院地址
	private Float cinema_score;//电影院评分
	private String cinema_tel;//电话
	private String cinema_service;//影院服务
	private String cinema_store_service;//影院门店服务
	private String cinema_description;//门店介绍
	private String cinema_url;//url地址
	
	//film info 
	private String film_image_name;
	private String film_screendate;//上映日期
	private String film_duration;//时长
	private String film_type;//电影类型
	private String film_director;//导演
	private String film_region;//电影所属国家
	private String film_actors;//演员
	private Float film_score;//评分
	private String film_url;
	
	private String index_text;
	
	
	public String getIndex_text() {	
		return index_text;
	}
	
	
	
	public void setIndex_text(String index_text) {
		if(this.index_text == null){
			this.index_text=this.film_name+"\t"+this.film_actors+"\t"+this.film_director+"\t"+this.film_region+"\t"+this.film_type;
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
	public Integer getCinema_id() {
		return cinema_id;
	}
	public void setCinema_id(Integer cinema_id) {
		this.cinema_id = cinema_id;
	}
	public Integer getFilm_id() {
		return film_id;
	}
	public void setFilm_id(Integer film_id) {
		this.film_id = film_id;
	}
	public String getCinema_name() {
		return cinema_name;
	}
	public void setCinema_name(String cinema_name) {
		this.cinema_name = JsonUtils.CleanUpSpecialChars(cinema_name);
	}
	public String getFilm_name() {
		return film_name;
	}
	public void setFilm_name(String film_name) {
		this.film_name = JsonUtils.CleanUpSpecialChars(film_name);
	}
	public String getScreen_date() {
		return screen_date;
	}
	public void setScreen_date(String screen_date) {
		this.screen_date = JsonUtils.CleanUpSpecialChars(screen_date);
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = JsonUtils.CleanUpSpecialChars(start_time);
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = JsonUtils.CleanUpSpecialChars(end_time);
	}
	public String getLang_version() {
		return lang_version;
	}
	public void setLang_version(String lang_version) {
		this.lang_version = JsonUtils.CleanUpSpecialChars(lang_version);
	}
	public String getScreen_hall() {
		return screen_hall;
	}
	public void setScreen_hall(String screen_hall) {
		this.screen_hall = JsonUtils.CleanUpSpecialChars(screen_hall);
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public String getCrawldate() {
		return crawldate;
	}
	public void setCrawldate(String crawldate) {
		this.crawldate = crawldate;
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
	
	public String getCinema_province() {
		return cinema_province;
	}
	public void setCinema_province(String cinema_province) {
		this.cinema_province = cinema_province;
	}
	public String getCinema_city() {
		return cinema_city;
	}
	public void setCinema_city(String cinema_city) {
		this.cinema_city = cinema_city;
	}
	public String getCinema_area() {
		return cinema_area;
	}
	public void setCinema_area(String cinema_area) {
		this.cinema_area = cinema_area;
	}
	public String getCinema_addr() {
		return cinema_addr;
	}
	public void setCinema_addr(String cinema_addr) {
		this.cinema_addr = cinema_addr;
	}
	public Float getCinema_score() {
		return cinema_score;
	}
	public void setCinema_score(Float cinema_score) {
		this.cinema_score = cinema_score;
	}
	public String getCinema_tel() {
		return cinema_tel;
	}
	public void setCinema_tel(String cinema_tel) {
		this.cinema_tel = cinema_tel;
	}
	public String getCinema_service() {
		return cinema_service;
	}
	public void setCinema_service(String cinema_service) {
		this.cinema_service = cinema_service;
	}
	public String getCinema_store_service() {
		return cinema_store_service;
	}
	public void setCinema_store_service(String cinema_store_service) {
		this.cinema_store_service = cinema_store_service;
	}
	public String getCinema_description() {
		return cinema_description;
	}
	public void setCinema_description(String cinema_description) {
		this.cinema_description = cinema_description;
	}
	public String getCinema_url() {
		return cinema_url;
	}
	public void setCinema_url(String cinema_url) {
		this.cinema_url = cinema_url;
	}
	public String getFilm_screendate() {
		return film_screendate;
	}
	public void setFilm_screendate(String film_screendate) {
		this.film_screendate = film_screendate;
	}
	public String getFilm_duration() {
		return film_duration;
	}
	public void setFilm_duration(String film_duration) {
		this.film_duration = film_duration;
	}
	public String getFilm_type() {
		return film_type;
	}
	public void setFilm_type(String film_type) {
		this.film_type = film_type;
	}
	public String getFilm_director() {
		return film_director;
	}
	public void setFilm_director(String film_director) {
		this.film_director = film_director;
	}
	public String getFilm_region() {
		return film_region;
	}
	public void setFilm_region(String film_region) {
		this.film_region = film_region;
	}
	public String getFilm_actors() {
		return film_actors;
	}
	public void setFilm_actors(String film_actors) {
		this.film_actors = film_actors;
	}
	public Float getFilm_score() {
		return film_score;
	}
	public void setFilm_score(Float film_score) {
		this.film_score = film_score;
	}
	public String getFilm_url() {
		return film_url;
	}
	public void setFilm_url(String film_url) {
		this.film_url = film_url;
	}
	public String getCinema_amap_name() {
		return cinema_amap_name;
	}
	public void setCinema_amap_name(String cinema_amap_name) {
		this.cinema_amap_name = cinema_amap_name;
	}
	public String getCinema_amap_id() {
		return cinema_amap_id;
	}
	public void setCinema_amap_id(String cinema_amap_id) {
		this.cinema_amap_id = cinema_amap_id;
	}
	public String getFilm_image_name() {
		return film_image_name;
	}
	public void setFilm_image_name(String film_image_name) {
		this.film_image_name = film_image_name;
	}
	
	public Integer getStart_time_sort() {
		return start_time_sort;
	}
	public void setStart_time_sort(Integer start_time_sort) {
		this.start_time_sort = start_time_sort;
	}
	public void setCinema(Cinema cinema){
		if(cinema == null){
			return;
		}
		
		this.cinema_province=cinema.getProvince();//所在省
		this.cinema_city=cinema.getCity();//市
		this.cinema_area=cinema.getArea();//区
		this.cinema_addr=cinema.getAddr();//电影院地址
		this.cinema_score=cinema.getScore();//电影院评分
		this.cinema_tel=cinema.getTel();//电话
		this.cinema_service=cinema.getService();//影院服务
		this.cinema_store_service=cinema.getStore_service();//影院门店服务
		this.cinema_description=cinema.getDescription();//门店介绍
		this.cinema_url=cinema.getUrl();//url地址
		this.cinema_amap_id = cinema.getAmap_id();
		this.cinema_amap_name = cinema.getAmap_name();
	}
	
	public void setFilm(Film film){
		if(film==null){
			return;
		}
		this.film_screendate = film.getScreendate();//上映日期
		this.film_duration = film.getDuration();//时长
		this.film_type = film.getType();//电影类型
		this.film_director = film.getDirector();//导演
		this.film_region = film.getRegion();//电影所属国家
		this.film_actors = film.getActors();//演员
		this.film_score = film.getScore();//评分
		this.film_url = film.getUrl();
		this.film_image_name = film.getImage_name();
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
	public static Schedule GetInstance(String jsonString) {
		if (StringUtils.isEmpty(jsonString)) {
			return null;
		}

		try {
			JSONObject obj = JSONObject.fromObject(jsonString);
			Schedule entity = (Schedule) JSONObject.toBean(obj, Schedule.class);

			return entity;
		} catch (Exception e) {
			return null;
		}
	}
	
}
