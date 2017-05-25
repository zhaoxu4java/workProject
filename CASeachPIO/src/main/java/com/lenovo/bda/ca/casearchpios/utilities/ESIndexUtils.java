package com.lenovo.bda.ca.casearchpios.utilities;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.queryparser.surround.parser.ParseException;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.MatchQueryBuilder.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;

public class ESIndexUtils {
	private final static String ES_INDEX_HOST = "ca_bot";
	private final static String ES_INDEX_NAME = "ca_bot";
	static Client client = null;
	static{
		if(client == null){
    		Settings settings = Settings.settingsBuilder().put("cluster.name","ca").put("client.transport.sniff", true).build();
    		
    		 try {
				client = TransportClient.builder().settings(settings).build()
				.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.3.5"), 9300));
				//.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("10.100.217.233"), 9300));
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}			
    	}
    	
	}
	
	public static String QueryCinemaList(String cinemaIds,String keywords){
		if((cinemaIds==null || cinemaIds.length() ==0) && (keywords == null || keywords.length() == 0)){
			return "[]";
		}
		
		QueryBuilder qb = null;
		Map<String,String> sortMap = new LinkedHashMap<String,String>();

		if(cinemaIds!=null && cinemaIds.length() >0){
			cinemaIds = cinemaIds.toLowerCase().replaceAll("\\s", "");
			String[] cinemaIdArray = cinemaIds.split(",");
			if(cinemaIdArray == null || cinemaIdArray.length ==0){
				return "[]";
			}
			
			for(Integer i=0;i<cinemaIdArray.length;i++){
				sortMap.put(cinemaIdArray[i], null);
			}
			
			// 查询
			if(keywords==null || keywords.length() ==0){
				qb = QueryBuilders.idsQuery("cinema").addIds(cinemaIdArray);//QueryBuilders.termsQuery("ID", "1","2","3");
			}else{
				qb = QueryBuilders.boolQuery()  
                .must(QueryBuilders.idsQuery("cinema").addIds(cinemaIdArray))  
                .must(QueryBuilders.matchQuery("index_text", keywords).operator(Operator.AND));
//                .must(QueryBuilders.boolQuery()
//                		.should(QueryBuilders.matchQuery("cinema_name", keywords))
//                		.should(QueryBuilders.matchQuery("amap_name", keywords))
//                		.should(QueryBuilders.matchQuery("addr", keywords))
//                		.should(QueryBuilders.matchQuery("area", keywords))
//                		.should(QueryBuilders.matchQuery("city", keywords))
//                		.should(QueryBuilders.matchQuery("province", keywords))
//                		.should(QueryBuilders.matchQuery("description", keywords))
//                		.should(QueryBuilders.matchQuery("service", keywords))
//                		.should(QueryBuilders.matchQuery("store_service", keywords))
//                		);
			}
			
		}else if(keywords!=null && keywords.length() >0){
			qb = QueryBuilders.boolQuery()
					.must(QueryBuilders.matchQuery("index_text", keywords).operator(Operator.AND));
//	            		.should(QueryBuilders.matchQuery("cinema_name", keywords))
//	            		.should(QueryBuilders.matchQuery("amap_name", keywords))
//	            		.should(QueryBuilders.matchQuery("addr", keywords))
//	            		.should(QueryBuilders.matchQuery("area", keywords))
//	            		.should(QueryBuilders.matchQuery("city", keywords))
//	            		.should(QueryBuilders.matchQuery("province", keywords))
//	            		.should(QueryBuilders.matchQuery("description", keywords))
//	            		.should(QueryBuilders.matchQuery("service", keywords))
//	            		.should(QueryBuilders.matchQuery("store_service", keywords));
		}else{
			return "[]";
		}
		
		SearchResponse searchresponse = client.prepareSearch(ES_INDEX_NAME)// index
				.setTypes("cinema")// type
				.setQuery(qb)// query
				.execute().actionGet();

		SearchHits hits = searchresponse.getHits();// 获取返回值
		String jsonResult="";
		if (hits.totalHits() > 0) {
			for (SearchHit hit : hits) {
//				System.out.println("score:" + hit.getScore() + ":\t" + hit.getId());// .get("title").getSource()
//				System.out.println("content:" + hit.getSourceAsString());
				sortMap.put(hit.getId(), hit.getSourceAsString());
			}
			
			jsonResult+="[";
			for(String json:sortMap.values()){
				jsonResult+=json+",";
			}
			if(jsonResult.endsWith(",")){
				jsonResult = jsonResult.substring(0, jsonResult.length()-1);
			}
			jsonResult+="]";
		} else {
			jsonResult="[]";
			System.out.println("搜到0条结果");
		}
		
		sortMap.clear();
		sortMap = null;
		System.out.println(jsonResult);
		return jsonResult;
	}
	
	public static String QueryCinemaListByAmapId(String cinemaAmapIds,String keywords){
		if((cinemaAmapIds==null || cinemaAmapIds.length() ==0) && (keywords == null || keywords.length() == 0)){
			return "[]";
		}
		
		QueryBuilder qb = null;
		Map<String,String> sortMap = new LinkedHashMap<String,String>();

		if(cinemaAmapIds!=null && cinemaAmapIds.length() >0){
			cinemaAmapIds = cinemaAmapIds.toLowerCase().replaceAll("\\s", "");
			String[] cinemaAmapIdArray = cinemaAmapIds.split(",");
			if(cinemaAmapIdArray == null || cinemaAmapIdArray.length ==0){
				return "[]";
			}
			
			for(Integer i=0;i<cinemaAmapIdArray.length;i++){
				sortMap.put(cinemaAmapIdArray[i], null);
			}
			 
				// 查询
				if(keywords==null || keywords.length() ==0){
					 qb = QueryBuilders.termsQuery("amap_id", cinemaAmapIdArray);
				}else{
					qb = QueryBuilders.boolQuery()  
	                .must(QueryBuilders.termsQuery("amap_id", cinemaAmapIdArray))  
	                .must(QueryBuilders.matchQuery("index_text", keywords).operator(Operator.AND));
//	                .must(QueryBuilders.boolQuery()
//	                		.should(QueryBuilders.matchQuery("cinema_name", keywords))
//	                		.should(QueryBuilders.matchQuery("amap_name", keywords))
//	                		.should(QueryBuilders.matchQuery("addr", keywords))
//	                		.should(QueryBuilders.matchQuery("area", keywords))
//	                		.should(QueryBuilders.matchQuery("city", keywords))
//	                		.should(QueryBuilders.matchQuery("province", keywords))
//	                		.should(QueryBuilders.matchQuery("description", keywords))
//	                		.should(QueryBuilders.matchQuery("service", keywords))
//	                		.should(QueryBuilders.matchQuery("store_service", keywords))
//	                		);
				}
				
		}else if(keywords!=null && keywords.length() >0){
			qb = QueryBuilders.boolQuery()
					.must(QueryBuilders.matchQuery("index_text", keywords).operator(Operator.AND));
//            		.should(QueryBuilders.matchQuery("cinema_name", keywords))
//            		.should(QueryBuilders.matchQuery("amap_name", keywords))
//            		.should(QueryBuilders.matchQuery("addr", keywords))
//            		.should(QueryBuilders.matchQuery("area", keywords))
//            		.should(QueryBuilders.matchQuery("city", keywords))
//            		.should(QueryBuilders.matchQuery("province", keywords))
//            		.should(QueryBuilders.matchQuery("description", keywords))
//            		.should(QueryBuilders.matchQuery("service", keywords))
//            		.should(QueryBuilders.matchQuery("store_service", keywords));

		}else{
			return "[]";
		}
		
		SearchResponse searchresponse = client.prepareSearch(ES_INDEX_NAME)// index
				.setTypes("cinema")// type
				.setQuery(qb)// query
				.execute().actionGet();

		SearchHits hits = searchresponse.getHits();// 获取返回值
		String jsonResult="";
		if (hits.totalHits() > 0) {
			jsonResult+="[";
			for (SearchHit hit : hits) {
				/*System.out.println("score:" + hit.getScore() + ":\t" + hit.getId());// .get("title").getSource()
				System.out.println("content:" + hit.getSourceAsString());*/
				jsonResult+=hit.getSourceAsString()+",";
			}
			
			if(jsonResult.endsWith(",")){
				jsonResult = jsonResult.substring(0, jsonResult.length()-1);
			}
			jsonResult+="]";
		} else {
			jsonResult="[]";
			System.out.println("搜到0条结果");
		}
		System.out.println(jsonResult);
		return jsonResult;
	}
	
	public static String QueryFilmList(String filmIds,String keywords) {
		if((filmIds==null || filmIds.length() ==0) && (keywords == null || keywords.length() == 0)){
			return "[]";
		}
		
		QueryBuilder qb = null;
		Map<String,String> sortMap = new LinkedHashMap<String,String>();

		if(filmIds!=null && filmIds.length() >0){
			filmIds = filmIds.toLowerCase().replaceAll("\\s", "");
			String[] filmIdArray = filmIds.split(",");
			if(filmIdArray == null || filmIdArray.length ==0){
				return "[]";
			}
			
			for(Integer i=0;i<filmIdArray.length;i++){
				sortMap.put(filmIdArray[i], null);
			}
			
			// 查询
			if(keywords==null || keywords.length() ==0){
				qb = QueryBuilders.idsQuery("film").addIds(filmIdArray);//QueryBuilders.termsQuery("ID", "1","2","3");
			}else{
				
				qb = QueryBuilders.boolQuery()  
                .must(QueryBuilders.idsQuery("film").addIds(filmIdArray))  
                .must(QueryBuilders.matchQuery("index_text", keywords).operator(Operator.AND));
//                .must(QueryBuilders.boolQuery()
//                 		.should(QueryBuilders.matchQuery("film_name", keywords).operator(Operator.AND))
//                 		.should(QueryBuilders.matchQuery("actors", keywords).operator(Operator.AND))
//                 		.should(QueryBuilders.matchQuery("director", keywords).operator(Operator.AND))
//                 		.should(QueryBuilders.matchQuery("region", keywords).operator(Operator.AND))
//                 		.should(QueryBuilders.matchQuery("type", keywords).operator(Operator.AND)));
			}
		}else if(keywords!=null && keywords.length() >0){
			qb = QueryBuilders.boolQuery()
					.must(QueryBuilders.matchQuery("index_text", keywords).operator(Operator.AND));
//             		.should(QueryBuilders.matchQuery("film_name", keywords).operator(Operator.AND))
//             		.should(QueryBuilders.matchQuery("actors", keywords).operator(Operator.AND))
//             		.should(QueryBuilders.matchQuery("director", keywords).operator(Operator.AND))
//             		.should(QueryBuilders.matchQuery("region", keywords).operator(Operator.AND))
//             		.should(QueryBuilders.matchQuery("type", keywords).operator(Operator.AND));
		}else{
			return "[]";
		}
		
		
		SearchResponse searchresponse = client.prepareSearch(ES_INDEX_NAME)// index
				.setTypes("film")// type
				.setQuery(qb)// query
				.execute().actionGet();

		SearchHits hits = searchresponse.getHits();// 获取返回值
		String jsonResult="";
		if (hits.totalHits() > 0) {
			for (SearchHit hit : hits) {
				/*System.out.println("score:" + hit.getScore() + ":\t" + hit.getId());// .get("title").getSource()
				System.out.println("content:" + hit.getSourceAsString());*/
				sortMap.put(hit.getId(), hit.getSourceAsString());
			}
			
			jsonResult+="[";
			for(String json:sortMap.values()){
				jsonResult+=json+",";
			}
			if(jsonResult.endsWith(",")){
				jsonResult = jsonResult.substring(0, jsonResult.length()-1);
			}
			jsonResult+="]";
		} else {
			jsonResult="[]";
			System.out.println("搜到0条结果");
		}
		
		sortMap.clear();
		sortMap = null;
		System.out.println(jsonResult);
		return jsonResult;
	}
	
	public static String QueryFilmScheduleList(String cinemaAmapIds,String keywords,String outputType){
		if(cinemaAmapIds==null || cinemaAmapIds.length() ==0){
			return "[]";
		}
		
		cinemaAmapIds = cinemaAmapIds.toLowerCase().replaceAll("\\s", "");
		String[] cinemaAmapIdArray = cinemaAmapIds.split(",");
		if(cinemaAmapIdArray == null || cinemaAmapIdArray.length ==0){
			return "[]";
		}
		
		Calendar cal =Calendar.getInstance();
		Integer currentTime = cal.get(Calendar.HOUR_OF_DAY)*100+cal.get(Calendar.MINUTE);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		QueryBuilder qb = null;
		String screenDate =null;
		SearchResponse searchresponse =null;
		SearchHits hits = null;
		
		SortBuilder sortBuilder = SortBuilders.fieldSort("start_time_sort")
	                .order(SortOrder.ASC);
		
		List<SearchHit>  allHits = new ArrayList<SearchHit>();
		
		for(Integer i=0;i<cinemaAmapIdArray.length;i++){
			String cinemaAmapId = cinemaAmapIdArray[i];
			if(cinemaAmapId == null || cinemaAmapId.length()==0){
				continue;
			}
			
			// 查询
			cal =Calendar.getInstance();
			for(int j=0;j<30;j++){
				screenDate =  df.format(cal.getTime());
				
				if(keywords!=null && keywords.length()>0){
					qb = QueryBuilders.boolQuery()  
	                .must(QueryBuilders.termsQuery("cinema_amap_id", cinemaAmapId))  
	                .must(QueryBuilders.termsQuery("screen_date", screenDate))
	                .must(QueryBuilders.rangeQuery("start_time_sort").gt(currentTime)) 
	                .must(QueryBuilders.matchQuery("index_text", keywords).operator(Operator.AND));
	             //   .must(QueryBuilders.boolQuery()
//	                		.should(QueryBuilders.matchQuery("film_name", keywords))
//	                		.should(QueryBuilders.matchQuery("film_actors", keywords))
//	                		.should(QueryBuilders.matchQuery("film_director", keywords))
//	                		.should(QueryBuilders.matchQuery("film_region", keywords))
//	                		.should(QueryBuilders.matchQuery("film_type", keywords)));
	                 	
				}else{
				 	qb = QueryBuilders.boolQuery()  
				 			.must(QueryBuilders.termsQuery("cinema_amap_id", cinemaAmapId))  
			                .must(QueryBuilders.termsQuery("screen_date", screenDate))
			                .must(QueryBuilders.rangeQuery("start_time_sort").gt(currentTime))  ;
				}
			 
			 	searchresponse = client.prepareSearch(ES_INDEX_NAME)// index
					.setTypes("film_schedule")// type
					.setQuery(qb)// query
					.addSort(sortBuilder).execute().actionGet();

			 	hits = searchresponse.getHits();// 获取返回值
			 
			 	if (hits.totalHits() > 0) {
			 		for(SearchHit hit : hits){
			 			System.out.println(hit.getScore()+hit.getSourceAsString());
			 			allHits.add(hit);
			 		}
					break;
				} else {
					System.out.println(screenDate);
					cal.add(Calendar.DAY_OF_YEAR, -1);
				}
			}
		}
	
		//按照start_time_sort排序
		 Collections.sort(allHits, new Comparator<SearchHit>() {
		      @Override
		      public int compare(SearchHit o1, SearchHit o2) {
		        return ((Integer) o1.sourceAsMap().get("start_time_sort")).compareTo((Integer)o2.sourceAsMap().get("start_time_sort"));
		      }

		    });

		
		if(outputType == null || outputType.length() == 0|| "cinema".equalsIgnoreCase(outputType)){
			Map<Integer,ArrayList<SearchHit>> hitMap = new LinkedHashMap<Integer,ArrayList<SearchHit>>();
			
			for(SearchHit hit:allHits){
				if(!hitMap.containsKey(hit.sourceAsMap().get("cinema_id"))){
					hitMap.put((Integer)hit.sourceAsMap().get("cinema_id"), new ArrayList<SearchHit>());
				}
				
				hitMap.get((Integer)hit.sourceAsMap().get("cinema_id")).add(hit);
			}
			
			String jsonResult = "[";
			for(Integer cinemaId: hitMap.keySet()){
				jsonResult+=getCinemaFilmScheduleJson(hitMap.get(cinemaId))+",";
			}
			
			if(jsonResult.endsWith(",")){
				jsonResult = jsonResult.substring(0, jsonResult.length()-1);
			}
			jsonResult+="]";
			
			System.out.println(jsonResult);
			return jsonResult;
		}else{
			Map<Integer,ArrayList<SearchHit>> hitMap = new LinkedHashMap<Integer,ArrayList<SearchHit>>();
			
			for(SearchHit hit:allHits){
				if(!hitMap.containsKey(hit.sourceAsMap().get("film_id"))){
					hitMap.put((Integer)hit.sourceAsMap().get("film_id"), new ArrayList<SearchHit>());
				}
				
				hitMap.get((Integer)hit.sourceAsMap().get("film_id")).add(hit);
			}
			
			String jsonResult = "[";
			for(Integer filmId: hitMap.keySet()){
				jsonResult+=getFilmCinemaScheduleJson(hitMap.get(filmId))+",";
			}
			
			if(jsonResult.endsWith(",")){
				jsonResult = jsonResult.substring(0, jsonResult.length()-1);
			}
			jsonResult+="]";
			
			System.out.println(jsonResult);
			return jsonResult;
		}
	
	}
	
	public static String QueryTopNFilmScheduleList(String cinemaAmapIds,String keywords,Integer topN){
		if(cinemaAmapIds==null || cinemaAmapIds.length() ==0){
			return "[]";
		}
		
		cinemaAmapIds = cinemaAmapIds.toLowerCase().replaceAll("\\s", "");
		String[] cinemaAmapIdArray = cinemaAmapIds.split(",");
		if(cinemaAmapIdArray == null || cinemaAmapIdArray.length ==0){
			return "[]";
		}
		
		Calendar cal =Calendar.getInstance();
		Integer currentTime = cal.get(Calendar.HOUR_OF_DAY)*100+cal.get(Calendar.MINUTE);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		QueryBuilder qb = null;
		String screenDate =null;
		SearchResponse searchresponse =null;
		SearchHits hits = null;
		
		SortBuilder sortBuilder = SortBuilders.fieldSort("start_time_sort")
	                .order(SortOrder.ASC);
		
		List<SearchHit>  allHits = new ArrayList<SearchHit>();
		
		for(Integer i=0;i<cinemaAmapIdArray.length;i++){
			String cinemaAmapId = cinemaAmapIdArray[i];
			if(cinemaAmapId == null || cinemaAmapId.length()==0){
				continue;
			}
			
			// 查询
			cal =Calendar.getInstance();
			for(int j=0;j<30;j++){
				screenDate =  df.format(cal.getTime());
				
				if(keywords!=null && keywords.length()>0){
					qb = QueryBuilders.boolQuery()  
	                .must(QueryBuilders.termsQuery("cinema_amap_id", cinemaAmapId))  
	                .must(QueryBuilders.termsQuery("screen_date", screenDate))
	                .must(QueryBuilders.rangeQuery("start_time_sort").gt(currentTime)) 
	                .must(QueryBuilders.matchQuery("index_text", keywords).operator(Operator.AND));
//	                .must(QueryBuilders.boolQuery()
//	                		.should(QueryBuilders.matchQuery("film_name", keywords))
//	                		.should(QueryBuilders.matchQuery("film_actors", keywords))
//	                		.should(QueryBuilders.matchQuery("film_director", keywords))
//	                		.should(QueryBuilders.matchQuery("film_region", keywords))
//	                		.should(QueryBuilders.matchQuery("film_type", keywords)));

				}else{
				 	qb = QueryBuilders.boolQuery()  
				 			.must(QueryBuilders.termsQuery("cinema_amap_id", cinemaAmapId))  
			                .must(QueryBuilders.termsQuery("screen_date", screenDate))
			                .must(QueryBuilders.rangeQuery("start_time_sort").gt(currentTime))  ;
				}
			 
			 	searchresponse = client.prepareSearch(ES_INDEX_NAME)// index
					.setTypes("film_schedule")// type
					.setQuery(qb)// query
					.addSort(sortBuilder).execute().actionGet();

			 	hits = searchresponse.getHits();// 获取返回值
			 
			 	if (hits.totalHits() > 0) {
			 		for(SearchHit hit : hits){
			 			allHits.add(hit);
			 		}
					break;
				} else {
					System.out.println(screenDate);
					cal.add(Calendar.DAY_OF_YEAR, -1);
				}
			}
		}
	
		//按照start_time_sort排序
		 Collections.sort(allHits, new Comparator<SearchHit>() {
		      @Override
		      public int compare(SearchHit o1, SearchHit o2) {
		        return ((Integer) o1.sourceAsMap().get("start_time_sort")).compareTo((Integer)o2.sourceAsMap().get("start_time_sort"));
		      }

		    });
		 
		 String jsonResult ="[";
		 for(int i=0;i<topN;i++){
			 jsonResult+=allHits.get(i).sourceAsString()+","; 
		 }
		 
		
		if(jsonResult.endsWith(",")){
			jsonResult = jsonResult.substring(0, jsonResult.length()-1);
		}
		jsonResult+="]";
		
		System.out.println(jsonResult);
		return jsonResult;
	}
	
	private static String getFilmCinemaScheduleJson(ArrayList<SearchHit> hits){
		String filmJson="{"+getFilmJson(hits.get(0))+",\"cinemas\":[";
		
		//cinema info
		Map<Integer,String> map =new LinkedHashMap<Integer,String>();
		for (SearchHit hit : hits) {
				Integer cinemaId = (Integer) hit.sourceAsMap().get("cinema_id");
				if(map.containsKey(cinemaId)){
					map.put(cinemaId,map.get(cinemaId)+getScheduleJson(hit)+",");
				}else{
					map.put(cinemaId, getCinemaJson(hit)+",\"schedules\":["+getScheduleJson(hit)+",");
				}
		}
		
		for(String cinemaJson:map.values()){
			if(cinemaJson.endsWith(",")){
				cinemaJson = cinemaJson.substring(0,cinemaJson.length()-1);
			}
			
			cinemaJson+="]";
			filmJson+="{"+cinemaJson+"},";
		}
		
		
		if(filmJson.endsWith(",")){
			filmJson = filmJson.substring(0,filmJson.length()-1);
		}
		
		return filmJson+"]}";
	}
	
	private static String getCinemaFilmScheduleJson(ArrayList<SearchHit> hits){
		String cinemaJson="{"+getCinemaJson(hits.get(0))+",\"films\":[";
		
		//cinema info
		Map<Integer,String> map =new LinkedHashMap<Integer,String>();
		for (SearchHit hit : hits) {
				Integer filmId = (Integer) hit.sourceAsMap().get("film_id");
				if(map.containsKey(filmId)){
					map.put(filmId,map.get(filmId)+getScheduleJson(hit)+",");
				}else{
					map.put(filmId, getFilmJson(hit)+",\"schedules\":["+getScheduleJson(hit)+",");
				}
		}
		
		for(String filmJson:map.values()){
			if(filmJson.endsWith(",")){
				filmJson = filmJson.substring(0,filmJson.length()-1);
			}
			
			filmJson+="]";
			cinemaJson+="{"+filmJson+"},";
		}
		
		
		if(cinemaJson.endsWith(",")){
			cinemaJson = cinemaJson.substring(0,cinemaJson.length()-1);
		}
		
		return cinemaJson+"]}";
	}
	
	private static String getCinemaJson(SearchHit hit){
		return  getKeyIntValue(hit,"cinema_id")+","+getKeyStrValue(hit,"cinema_name")+","
			+getKeyStrValue(hit,"cinema_amap_id")+","+getKeyStrValue(hit,"cinema_amap_name")+","
			+getKeyStrValue(hit,"cinema_province")+","+getKeyStrValue(hit,"cinema_city")+","
			+getKeyStrValue(hit,"cinema_area")+","+getKeyStrValue(hit,"cinema_addr")+","
			+getKeyIntValue(hit,"cinema_score")+","+getKeyStrValue(hit,"cinema_tel")+","
			+getKeyStrValue(hit,"cinema_service")+","+getKeyStrValue(hit,"cinema_store_service")+","
			+getKeyStrValue(hit,"cinema_description")+","+getKeyStrValue(hit,"cinema_url");
		
	}
	
	private static String getFilmJson(SearchHit hit){
		return getKeyIntValue(hit,"film_id")+","+getKeyStrValue(hit,"film_name")+","
				+getKeyStrValue(hit,"film_image_name")+","+getKeyStrValue(hit,"film_screendate")+","
				+getKeyStrValue(hit,"film_duration")+","+getKeyStrValue(hit,"film_type")+","
				+getKeyStrValue(hit,"film_director")+","+getKeyStrValue(hit,"film_region")+","
				+getKeyIntValue(hit,"film_score")+","+getKeyStrValue(hit,"film_actors")+","
				+getKeyStrValue(hit,"film_url");
		
	}
	
	private static String getScheduleJson(SearchHit hit){
		return "{"+getKeyIntValue(hit,"ID")+","+getKeyStrValue(hit,"screen_date")+","
				+getKeyStrValue(hit,"start_time")+","+getKeyIntValue(hit,"start_time_sort")+","
				+getKeyStrValue(hit,"end_time")+","+getKeyStrValue(hit,"lang_version")+","
				+getKeyStrValue(hit,"screen_hall")+","+getKeyIntValue(hit,"price")+","
				+getKeyStrValue(hit,"crawldate")+","+getKeyStrValue(hit,"url")+","
				+getKeyStrValue(hit,"update_time")+"}";
	}
	
	private static String getKeyIntValue(SearchHit hit,String key){
		return "\""+key+"\":"+hit.sourceAsMap().get(key)+"";
	}
	
	private static String getKeyStrValue(SearchHit hit,String key){
		return "\""+key+"\":\""+hit.sourceAsMap().get(key)+"\"";
	}
	
	public static void main(String[] args) throws ParseException {
		
		String ids="1,2,3";
		String 	ampids = "B000A87LXJ,B000ABCOON,B000AAB22L,B0FFGPMMZK,B000A8UMG2";
		
	

		//System.out.println(QueryCinemaList(null,"中影丰台"));
	//	System.out.println(QueryFilmList(null,"奇幻美国"));
	System.out.println(QueryFilmScheduleList(ampids,"张艺谋",""));
		//System.out.println(QueryTopNFilmScheduleList(ampids,"",5));
	}

}
