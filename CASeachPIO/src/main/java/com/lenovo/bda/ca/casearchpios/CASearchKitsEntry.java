package com.lenovo.bda.ca.casearchpios;

import java.io.IOException;
import java.util.HashMap;

import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Requests;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import com.lenovo.bda.ca.casearchpios.entities.Cinema;
import com.lenovo.bda.ca.casearchpios.entities.Film;
import com.lenovo.bda.ca.casearchpios.entities.Schedule;
import com.lenovo.bda.ca.casearchpios.factories.ClientFactory;
import com.lenovo.bda.ca.casearchpios.utilities.DBUtilities;

/**
 * Hello world!
 *
 */
public class CASearchKitsEntry {
	public static void main(String[] args) {
		System.out.println(DBUtilities.GetTableRowCount("cinema_info"));
		System.out.println(DBUtilities.GetTableRowCount("film_info"));
		System.out.println(DBUtilities.GetTableRowCount("film_schedule_info"));
		
		// TODO Auto-generated method stub

		Client client = ClientFactory.getInstance();
		//deleteIndex(client,"ca_bot");
		//createInitCabotIndexs(client);
		
		createCABotIndex(client);

		//query(client);

		client.close();
	}

	private static boolean deleteIndex(Client client, String index) {
		IndicesExistsRequest inExistsRequest = new IndicesExistsRequest(index);

		IndicesExistsResponse inExistsResponse = client.admin().indices().exists(inExistsRequest).actionGet();
		if (inExistsResponse.isExists()) {

			DeleteIndexResponse dResponse = client.admin().indices().prepareDelete(index).execute().actionGet();
			return dResponse.isAcknowledged();
			
		}else{
			return false;
		}
	}

	/**
	 * 创建mapping(feid("indexAnalyzer","ik")该字段分词IK索引
	 * ；feid("searchAnalyzer","ik")该字段分词ik查询；具体分词插件请看IK分词插件说明)
	 * 
	 * @param indices
	 *            索引名称；
	 * @param mappingType
	 *            类型
	 * @throws Exception
	 */
	public static void createInitCabotIndexs(Client client) {
		try {
			client.admin().indices().prepareCreate("ca_bot").execute().actionGet();
			new XContentFactory();
			XContentBuilder builder = XContentFactory.jsonBuilder().startObject().startObject("cinema").startObject("properties")
//					.startObject("cinema_name").field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject()
//					.startObject("amap_name").field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject()
//					.startObject("province").field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject()
//					.startObject("city").field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject()
//					.startObject("area").field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject()
//					.startObject("addr").field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject()
//					.startObject("description").field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject()
//					.startObject("service").field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject()
//					.startObject("store_service").field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject()
					.startObject("index_text").field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject().endObject().endObject();

			PutMappingRequest mapping = Requests.putMappingRequest("ca_bot").type("cinema").source(builder);
			client.admin().indices().putMapping(mapping).actionGet();

			XContentBuilder filmbuilder = XContentFactory.jsonBuilder().startObject().startObject("film").startObject("properties")
//					.startObject("film_name").field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject()
//					.startObject("image_name").field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject()
//					.startObject("type").field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject()
//					.startObject("director").field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject()
//					.startObject("region").field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject()
//					.startObject("actors").field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject()
					.startObject("index_text").field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject().endObject().endObject();
			PutMappingRequest filmmapping = Requests.putMappingRequest("ca_bot").type("film").source(filmbuilder);
			client.admin().indices().putMapping(filmmapping).actionGet();

			XContentBuilder schedulebuilder = XContentFactory.jsonBuilder().startObject().startObject("film_schedule").startObject("properties")
//					.startObject("cinema_name").field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject()
//					.startObject("film_name").field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject()
//					.startObject("lang_version").field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject()
//					.startObject("screen_hall").field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject()
//					.startObject("cinema_amap_name").field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject()
//					.startObject("cinema_province").field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject()
//					.startObject("cinema_city").field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject()
//					.startObject("cinema_area").field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject()
//					.startObject("cinema_addr").field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject()
//					.startObject("cinema_service").field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject()
//					.startObject("cinema_store_service").field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject()
//					.startObject("cinema_description").field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject()
//					.startObject("film_type").field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject()
//					.startObject("film_director").field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject()
//					.startObject("film_region").field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject()
//					.startObject("film_actors").field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject()
//					.startObject("film_image_name").field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject()
					.startObject("index_text").field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject().endObject().endObject();
			PutMappingRequest scheduleMapping = Requests.putMappingRequest("ca_bot").type("film_schedule").source(schedulebuilder);
			client.admin().indices().putMapping(scheduleMapping).actionGet();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
//		try {
//			client.admin().indices().prepareCreate("ca_bot").execute().actionGet();
//			new XContentFactory();
//			XContentBuilder builder = XContentFactory.jsonBuilder().startObject().startObject("cinema").startObject("properties").startObject("cinema_name").field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject().startObject("amap_name")
//					.field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject().startObject("province")
//					.field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject().startObject("city").field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject().startObject("area")
//					.field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject().startObject("addr").field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject().startObject("description")
//					.field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject().startObject("service").field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject().startObject("store_service")
//					.field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject().startObject("index_text")
//					.field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject().endObject().endObject();
//
//			PutMappingRequest mapping = Requests.putMappingRequest("ca_bot").type("cinema").source(builder);
//			client.admin().indices().putMapping(mapping).actionGet();
//
//			XContentBuilder filmbuilder = XContentFactory.jsonBuilder().startObject().startObject("film").startObject("properties").startObject("film_name").field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject().startObject("image_name")
//					.field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject().startObject("type").field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject().startObject("director")
//					.field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject().startObject("region").field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject().startObject("actors")
//					.field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject().startObject("index_text")
//					.field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject().endObject().endObject();
//			PutMappingRequest filmmapping = Requests.putMappingRequest("ca_bot").type("film").source(filmbuilder);
//			client.admin().indices().putMapping(filmmapping).actionGet();
//
//			XContentBuilder schedulebuilder = XContentFactory.jsonBuilder().startObject().startObject("film_schedule").startObject("properties").startObject("cinema_name").field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject()
//					.startObject("film_name").field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject().startObject("lang_version").field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject()
//					.startObject("screen_hall").field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject().startObject("cinema_amap_name").field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject()
//					.startObject("cinema_province").field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject()
//					.startObject("cinema_city").field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject().startObject("cinema_area").field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject()
//					.startObject("cinema_addr").field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject().startObject("cinema_service").field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject()
//					.startObject("cinema_store_service").field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject().startObject("cinema_description").field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed")
//					.endObject().startObject("film_type").field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject().startObject("film_director").field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed")
//					.endObject().startObject("film_region").field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject().startObject("film_actors").field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed")
//					.endObject().startObject("film_image_name").field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject().startObject("index_text")
//					.field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject().endObject().endObject();
//			PutMappingRequest scheduleMapping = Requests.putMappingRequest("ca_bot").type("film_schedule").source(schedulebuilder);
//			client.admin().indices().putMapping(scheduleMapping).actionGet();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

	private static void createCABotIndex(Client client) {
		HashMap<Integer, Cinema> cinemaMap = getAllCinema();
		HashMap<Integer, Film> filmMap = getAllFilm();

		createCinemaIndex(client, cinemaMap);
		createFilmIndex(client, filmMap);
		createFilmScheduleIndex(client, cinemaMap, filmMap);
	}

	private static void query(Client client) {
		// 查询
		QueryBuilder qb = QueryBuilders.matchQuery("cinema_name", "星美国际影城");

		/*
		 * QueryBuilder qb = QueryBuilders // .boolQuery() //
		 * .must(QueryBuilders.fuzzyQuery("cinema_name", "华谊"))
		 * .must(QueryBuilders.fuzzyQuery("film_name", "潘金莲")) ;
		 * .mustNot(QueryBuilders.termQuery("isRealMen", false))
		 * .should(QueryBuilders.termQuery("now_home", "山西省太原市"));
		 */

		SearchResponse searchresponse = client.prepareSearch("ca_bot")// index
				.setTypes("cinema")// type
				.setQuery(qb)// query
				.addHighlightedField("cinema_name")// 高亮字段
				.setHighlighterPreTags("<b>")// 设置搜索条件前后标签
				.setHighlighterPostTags("</b>").setHighlighterFragmentSize(50)// 返回的高亮部分上下文长度
				.execute().actionGet();

		SearchHits hits = searchresponse.getHits();// 获取返回值
		if (hits.totalHits() > 0) {
			for (SearchHit hit : hits) {
				System.out.println("score:" + hit.getScore() + ":\t" + hit.getId());// .get("title").getSource()
				System.out.println("content:" + hit.getFields().get("cinema_name"));
				System.out.println("content:" + hit.getSourceAsString());
			}
		} else {
			System.out.println("搜到0条结果");
		}
	}

	private static HashMap<Integer, Cinema> getAllCinema() {
		Integer total = DBUtilities.GetTableRowCount("cinema_info");
		Integer limit = 1000;
		Integer cycle = (total / limit) + 1;

		HashMap<Integer, Cinema> cinemaMap = new HashMap<Integer, Cinema>();
		HashMap<Integer, Cinema> tmp = null;

		for (int i = 0; i < cycle; i++) {
			tmp = DBUtilities.GetCinemaMap(limit * i, limit);
			if (tmp != null && tmp.size() > 0) {
				cinemaMap.putAll(tmp);
				tmp.clear();
				tmp = null;
			}
		}

		return cinemaMap.size() == 0 ? null : cinemaMap;
	}

	private static HashMap<Integer, Film> getAllFilm() {
		Integer total = DBUtilities.GetTableRowCount("film_info");
		Integer limit = 1000;
		Integer cycle = (total / limit) + 1;

		HashMap<Integer, Film> filmMap = new HashMap<Integer, Film>();
		HashMap<Integer, Film> tmp = null;

		for (int i = 0; i < cycle; i++) {
			tmp = DBUtilities.GetFilmMap(limit * i, limit);
			if (tmp != null && tmp.size() > 0) {
				filmMap.putAll(tmp);
				tmp.clear();
				tmp = null;
			}
		}

		return filmMap.size() == 0 ? null : filmMap;
	}

	private static void createCinemaIndex(Client client, HashMap<Integer, Cinema> cinemaMap) {
		IndexResponse response = null;

		for (Integer cinimeID : cinemaMap.keySet()) {
			// 插入文档，参数依次为index、type、id
			response = client.prepareIndex("ca_bot", "cinema", cinimeID + "").setSource(cinemaMap.get(cinimeID).toJsonString()).get();

			// Index name
			String _index = response.getIndex();
			// Type name
			String _type = response.getType();
			// Document ID (generated or not)
			String _id = response.getId();
			// Version (if it's the first time you index this document, you
			// will
			// get: 1)
			long _version = response.getVersion();
			// isCreated() is true if the document is a new one, false if it
			// has
			// been updated
			boolean created = response.isCreated();// 插入后返回的信息可以通过response获取
			System.out.println("index:" + _index);
			System.out.println("type:" + _type);
			System.out.println("id:" + _id);
			System.out.println("version:" + _version);
			System.out.println("created:" + created);
		}
	}

	private static void createFilmIndex(Client client, HashMap<Integer, Film> filmMap) {
		IndexResponse response = null;

		for (Integer filmID : filmMap.keySet()) {
			// 插入文档，参数依次为index、type、id
			response = client.prepareIndex("ca_bot", "film", filmID + "").setSource(filmMap.get(filmID).toJsonString()).get();

			// Index name
			String _index = response.getIndex();
			// Type name
			String _type = response.getType();
			// Document ID (generated or not)
			String _id = response.getId();
			// Version (if it's the first time you index this document, you
			// will
			// get: 1)
			long _version = response.getVersion();
			// isCreated() is true if the document is a new one, false if it
			// has
			// been updated
			boolean created = response.isCreated();// 插入后返回的信息可以通过response获取
			System.out.println("index:" + _index);
			System.out.println("type:" + _type);
			System.out.println("id:" + _id);
			System.out.println("version:" + _version);
			System.out.println("created:" + created);
		}

	}

	private static void createFilmScheduleIndex(Client client, HashMap<Integer, Cinema> cinemaMap, HashMap<Integer, Film> filmMap) {
		Integer total = DBUtilities.GetTableRowCount("film_schedule_info");
		Integer limit = 1000;
		Integer cycle = (total / limit) + 1;

		HashMap<Integer, Schedule> scheduleMap = null;

		IndexResponse response = null;

		Schedule schedule = null;
		for (int i = 0; i < cycle; i++) {
			scheduleMap = DBUtilities.GetFilmScheduleMap(limit * i, limit);

			if (scheduleMap == null || scheduleMap.size() == 0) {
				continue;
			}

			for (Integer scheudleID : scheduleMap.keySet()) {
				schedule = scheduleMap.get(scheudleID);

				if (schedule == null||cinemaMap.get(schedule.getCinema_id()) == null || filmMap.get(schedule.getFilm_id()) == null) {
					continue;
				}
				
				schedule.setCinema(cinemaMap.get(schedule.getCinema_id()));
				
				schedule.setFilm(filmMap.get(schedule.getFilm_id()));
				schedule.setIndex_text(null);
				// 插入文档，参数依次为index、type、id
				response = client.prepareIndex("ca_bot", "film_schedule", scheudleID + "").setSource(schedule.toJsonString()).get();

				// Index name
				String _index = response.getIndex();
				// Type name
				String _type = response.getType();
				// Document ID (generated or not)
				String _id = response.getId();
				// Version (if it's the first time you index this document, you
				// will
				// get: 1)
				long _version = response.getVersion();
			
				
				// isCreated() is true if the document is a new one, false if it
				// has
				// been updated
				boolean created = response.isCreated();// 插入后返回的信息可以通过response获取
				
				if(_version>1 || !created){
					System.out.println("Update index finished at "+_id);
					return;
				}
				
				System.out.println("index:" + _index);
				System.out.println("type:" + _type);
				System.out.println("id:" + _id);
				System.out.println("version:" + _version);
				System.out.println("created:" + created);
			}
		}
	}
}
