package com.lenovo.bda.ca.casearchpios.utilities;

import com.lenovo.bda.ca.casearchpios.entities.POI;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;
import org.apache.lucene.queryparser.surround.parser.ParseException;
import org.apache.lucene.search.Query;
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

import java.awt.geom.Point2D;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ESPOIIndexUtils {
    private final static String ES_INDEX_HOST = "ca_poi";
    private final static String ES_INDEX_NAME = "ca_poi";
    static Client client = null;

    static {
        if (client == null) {
            Settings settings = Settings.settingsBuilder().put("cluster.name", "ca").put("client.transport.sniff", true).build();

            try {
                client = TransportClient.builder().settings(settings).build()
                        //.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.3.5"), 9300));
                        .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("10.100.217.233"), 9300));
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }

    }
    /*=================================多边形查询===============================*/

    /**
     * 多边形+ typeCode
     */
    public static String searchByLocationsAndType(List<Point2D.Double> doubleList, String typecode) {
        if (doubleList != null && doubleList.size() > 0 && typecode != null && typecode.length() > 0) {
            int endIndex = doubleList.size() - 1;
            List<Point2D.Double> copeDoubles = new LinkedList<>(doubleList);
            List<Point2D.Double> doublesByLang = PointCheckUtils.sortLocation(doubleList, "lang");
            List<Point2D.Double> doublesBylat = PointCheckUtils.sortLocation(doubleList, "lat");
            String jsonString = byTypeCodeAndLocation(typecode, doublesByLang.get(0).getX(), doublesByLang.get(endIndex).getX(),
                    doublesBylat.get(0).getY(), doublesBylat.get(endIndex).getY());
            String checkedJsonString = checkPointInLocations(copeDoubles, jsonString);
            if (checkedJsonString != null) return checkedJsonString;
        }
        return "[]";
    }

    /**
     * 多边形 + keyword
     */
    public static String searchByLocationsAndKeyword(List<Point2D.Double> doubleList, String keyword) {
        if (doubleList != null && doubleList.size() > 0 && keyword != null && keyword.length() > 0) {
            int endIndex = doubleList.size() - 1;
            List<Point2D.Double> copeDoubles = new LinkedList<>(doubleList);
            List<Point2D.Double> doublesByLang = PointCheckUtils.sortLocation(doubleList, "lang");
            List<Point2D.Double> doublesBylat = PointCheckUtils.sortLocation(doubleList, "lat");
            String jsonString = byKeyWordsAndLocation(keyword, doublesByLang.get(0).getX(), doublesByLang.get(endIndex).getX(),
                    doublesBylat.get(0).getY(), doublesBylat.get(endIndex).getY());
            String checkedJsonString = checkPointInLocations(copeDoubles, jsonString);
            if (checkedJsonString != null) return checkedJsonString;
        }
        return "[]";
    }

    /**
     * 多边形 + keyword +typecode
     */
    public static String searchBylocationsAndkeyAndtype(List<Point2D.Double> doubleList, String keyword, String typecode) {
        if (doubleList != null && doubleList.size() > 0 && keyword != null && keyword.length() > 0 && typecode != null && typecode.length() > 0) {
            int endIndex = doubleList.size() - 1;
            List<Point2D.Double> copeDoubles = new LinkedList<>(doubleList);
            List<Point2D.Double> doublesBylang = PointCheckUtils.sortLocation(doubleList, "lang");
            double min = doublesBylang.get(0).getX();
            double max = doublesBylang.get(endIndex).getX();
            List<Point2D.Double> doublesBylat = PointCheckUtils.sortLocation(doubleList, "lat");
            String jsonString = byKeyWordsAndTypeCode(typecode, keyword, min, max,
                    doublesBylat.get(0).getY(), doublesBylat.get(endIndex).getY());
            String checkedJsonString = checkPointInLocations(copeDoubles, jsonString);
            if (checkedJsonString != null) return checkedJsonString;
        }
        return "[]";
    }

    /*==================================中心点查询==============================*/

    /**
     * 根据typecode 和location进行查询
     * location1 中心点经度
     * location2 中心点纬度
     * distance 搜索半径,单位m
     */
    public static String searchbyLocationAndType(String typeCode, Double location1, Double location2, Double distance) {
        if (typeCode != null && typeCode.length() > 0 && location1 != null && location2 != null && distance != null) {
            double aLong = PointCheckUtils.distancePoor(location1, location2, distance, "long");
            double lat = PointCheckUtils.distancePoor(location1, location2, distance, "lat");
            String jsonString = byTypeCodeAndLocation(typeCode, location1 - aLong, location1 + aLong, location2 - lat, location2 + lat);
            String jsonSortString = sortByDistance(location1, location2, distance, jsonString);
            if (jsonSortString != null) return jsonSortString;
        }
        return "[]";
    }

    /**
     * 根据typecode 和 location 以及keyword查询
     * keyword 关键字
     */
    public static String searchBykeyAndTypeCode(String typeCode, String keyword, Double location1, Double location2, Double distance) {
        if (typeCode != null && typeCode.length() > 0 && location1 != null && location2 != null && distance != null && keyword != null && keyword.length() > 0) {
            double aLong = PointCheckUtils.distancePoor(location1, location2, distance, "long");
            double lat = PointCheckUtils.distancePoor(location1, location2, distance, "lat");
            String jsonString = byKeyWordsAndTypeCode(typeCode, keyword, location1 - aLong, location1 + aLong, location2 - lat, location2 + lat);
            String jsonSortString = sortByDistance(location1, location2, distance, jsonString);
            if (jsonSortString != null) return jsonSortString;
        }
        return "[]";
    }

    /**
     * 根据keyword 和 location 查询
     */
    public static String searchByKeyAndLocation(String keyword, Double location1, Double location2, Double distance) {
        if (location1 != null && location2 != null && distance != null && keyword != null && keyword.length() > 0) {
            double aLong = PointCheckUtils.distancePoor(location1, location2, distance, "long");
            double lat = PointCheckUtils.distancePoor(location1, location2, distance, "lat");
            String jsonString = byKeyWordsAndLocation(keyword, location1 - aLong, location1 + aLong, location2 - lat, location2 + lat);
            String jsonSortString = sortByDistance(location1, location2, distance, jsonString);
            if (jsonSortString != null) return jsonSortString;
        }
        return "[]";
    }

    public static String byKeyWordsAndTypeCode(String typeCode, String keyword, Double location1min, Double location1max,
                                               Double location2min, Double location2max) {
        if ((typeCode != null && typeCode.length() > 0) && (keyword != null && keyword.length() > 0)) {
            Map<String, String> sortMap = new LinkedHashMap<String, String>();
            QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                    .must(QueryBuilders.matchQuery("typecode", typeCode))
                    .must(QueryBuilders.boolQuery()
                            .should(QueryBuilders.matchQuery("name", keyword))
                            .should(QueryBuilders.matchQuery("address", keyword))
                            .should(QueryBuilders.matchQuery("pname", keyword))
                            .should(QueryBuilders.matchQuery("cityname", keyword)))
                    .must(QueryBuilders.rangeQuery("location1").gte(location1min).lte(location1max))
                    .must(QueryBuilders.rangeQuery("location2").gte(location2min).lte(location2max));
            return searchResponseString(sortMap, queryBuilder);
        }
        return "[]";
    }


    public static String byKeyWordsAndLocation(String keyWord, Double location1min, Double location1max, Double location2min, Double location2max) {
        if (keyWord == null || keyWord.length() == 0) {
            return "[]";
        }
        Map<String, String> sortMap = new LinkedHashMap<>();

        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.rangeQuery("location1").gte(location1min).lte(location1max))
                .must(QueryBuilders.rangeQuery("location2").gte(location2min).lte(location2max))
                .must(QueryBuilders.boolQuery()
                        .should(QueryBuilders.matchQuery("name", keyWord))
                        .should(QueryBuilders.matchQuery("address", keyWord))
                        .should(QueryBuilders.matchQuery("pname", keyWord))
                        .should(QueryBuilders.matchQuery("cityname", keyWord))
                );
        return searchResponseString(sortMap, queryBuilder);
    }


    public static String byTypeCodeAndLocation(
            String typeCode, Double location1min, Double location1max, Double location2min, Double location2max) {
        if (typeCode == null || typeCode.length() == 0) {
            return "[]";
        }
        QueryBuilder queryBuilder = null;
        Map<String, String> sortMap = new LinkedHashMap<String, String>();
        queryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.matchQuery("typecode", typeCode))
                .must(QueryBuilders.rangeQuery("location1").gte(location1min).lte(location1max))
                .must(QueryBuilders.rangeQuery("location2").gte(location2min).lte(location2max));
        return searchResponseString(sortMap, queryBuilder);
    }

    private static String getJsonStringFromHit(Map<String, String> sortMap, SearchHits hits, String jsonResult) {
        if (hits.totalHits() > 0) {
            for (SearchHit hit : hits
                    ) {
                sortMap.put(hit.getId(), hit.getSourceAsString());
            }
            jsonResult += "[";
            for (String json : sortMap.values()) {
                jsonResult += json + ",";
            }
            if (jsonResult.endsWith(",")) {
                jsonResult = jsonResult.substring(0, jsonResult.length() - 1);
            }
            jsonResult += "]";
        } else {
            jsonResult = "[]";
            System.out.println("搜到0条结果");
        }
        return jsonResult;
    }

    private static String searchResponseString(Map<String, String> sortMap, QueryBuilder queryBuilder) {
        SearchResponse searchResponse = client.prepareSearch(ES_INDEX_NAME)
                .setTypes("poi")
                .setFrom(0)
                .setSize(30)
                .setQuery(queryBuilder).execute().actionGet();
        SearchHits hits = searchResponse.getHits();
        String jsonResult = "";
        jsonResult = getJsonStringFromHit(sortMap, hits, jsonResult);
        sortMap.clear();
        sortMap = null;
        // System.out.println(jsonResult);
        return jsonResult;
    }

    /**
     * 距离由近到远排序
     */
    private static String sortByDistance(Double location1, Double location2, Double distance, String jsonString) {
        if (!jsonString.equals("[]")) {
            JSONArray jsonArray = JSONArray.fromObject(jsonString);
            Map<Double, JSONObject> jsonObjectMap = new TreeMap<>((x, y) -> x.compareTo(y));
            for (Object obj : jsonArray) {
                JSONObject jsonObject = JSONObject.fromObject(obj);
                Double checkLocation1 = (Double) jsonObject.get("location1");
                Double checkLocation2 = (Double) jsonObject.get("location2");
                double distancePoor = PointCheckUtils.Distance(location1, location2, checkLocation1, checkLocation2);
                if (distancePoor <= distance) {
                    jsonObjectMap.put(distancePoor, jsonObject);
                }
            }
            String jsonSortString = "[";
            for (Double d : jsonObjectMap.keySet()) {
                jsonSortString += jsonObjectMap.get(d).toString() + ",";
            }
            if (jsonSortString.endsWith(",")) {
                jsonSortString = jsonSortString.substring(0, jsonSortString.length() - 1);
            }
            return jsonSortString + "]";
        }
        return null;
    }

    /**
     * 判断点是否落在多边形内部
     */
    private static String checkPointInLocations(List<Point2D.Double> doubleList, String jsonString) {
        if (!jsonString.equals("[]")) {
            JSONArray jsonArray = JSONArray.fromObject(jsonString);
            List<JSONObject> checkedJson = new ArrayList<>();
            for (Object obj : jsonArray) {
                JSONObject jsonObject = JSONObject.fromObject(obj);
                Point2D.Double point = new Point2D.Double(jsonObject.getDouble("location1"), jsonObject.getDouble("location2"));
                if (PointCheckUtils.IsPtInPoly(point, doubleList)) {
                    checkedJson.add(jsonObject);
                }
            }
            String checkedJsonString = "[";
            for (JSONObject jsonObject : checkedJson) {
                checkedJsonString += jsonObject.toString() + ",";
            }
            if (checkedJsonString.endsWith(",")) {
                checkedJsonString = checkedJsonString.substring(0, checkedJsonString.length() - 1);
            }
            return checkedJsonString + "]";
        }
        return null;
    }
}
