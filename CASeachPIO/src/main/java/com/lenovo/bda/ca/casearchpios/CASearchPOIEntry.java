package com.lenovo.bda.ca.casearchpios;

import com.lenovo.bda.ca.casearchpios.entities.Cinema;
import com.lenovo.bda.ca.casearchpios.entities.POI;
import com.lenovo.bda.ca.casearchpios.factories.ClientFactory;
import com.lenovo.bda.ca.casearchpios.utilities.DBUtilities;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Requests;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by lenovo on 2017/5/24.
 */
public class CASearchPOIEntry {
    public static void main(String arg[]) {
        System.out.println(DBUtilities.GetTableRowCount("POI"));
        Client client = ClientFactory.getInstance();
        deleteIndex(client, "ca_poi");
        createInitCabotIndexs(client);
        createPOIIndex(client, getAllPOI());
    }

    /*mapping配置：定义索引映射类型*/
    public static void createInitCabotIndexs(Client client) {
        try {
            client.admin().indices().prepareCreate("ca_poi").execute().actionGet();
            new XContentFactory();
            XContentBuilder builder = XContentFactory.jsonBuilder().startObject().startObject("poi").startObject("properties")
                    .startObject("name").field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject()
                    .startObject("tag").field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject()
                    .startObject("type").field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject()
                    .startObject("typecode").field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject()
                    .startObject("address").field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject()
                    .startObject("location1").field("type", "double").field("store", "yes").endObject()
                    .startObject("location2").field("type", "double").field("store", "yes").endObject()
                    .startObject("tel").field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject()
                    .startObject("postcode").field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject()
                    .startObject("website").field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject()
                    .startObject("email").field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject()
                    .startObject("pname").field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject()
                    .startObject("cityname").field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject()
                    .startObject("adname").field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject().endObject().endObject();
            PutMappingRequest mapping = Requests.putMappingRequest("ca_poi").type("poi").source(builder);
            client.admin().indices().putMapping(mapping).actionGet();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*将poi数据全部放入map中*/
    private static HashMap<Integer, POI> getAllPOI() {
        Integer total = DBUtilities.GetTableRowCount("POI");
        Integer limit = 1000;
        Integer cycle = (total / limit) + 1;

        HashMap<Integer, POI> poiMap = new HashMap<Integer, POI>();
        HashMap<Integer, POI> tmp = null;

        for (int i = 0; i < cycle; i++) {
            tmp = DBUtilities.GetPOIMap(limit * i, limit);
            if (tmp != null && tmp.size() > 0) {
                poiMap.putAll(tmp);
                tmp.clear();
                tmp = null;
            }
            System.out.println(i);
        }
        System.out.println("end put:" + poiMap.size());
        return poiMap.size() == 0 ? null : poiMap;
    }

    /*插入poi数据*/
    private static void createPOIIndex(Client client, HashMap<Integer, POI> poiHashMap) {
        IndexResponse response = null;

        for (Integer poiid : poiHashMap.keySet()) {
            // 插入文档，参数依次为index、type、id
            response = client.prepareIndex("ca_poi", "poi", poiid + "").setSource(poiHashMap.get(poiid).toJsonString()).get();

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

    /*删除索引*/
    private static boolean deleteIndex(Client client, String index) {
        IndicesExistsRequest inExistsRequest = new IndicesExistsRequest(index);

        IndicesExistsResponse inExistsResponse = client.admin().indices().exists(inExistsRequest).actionGet();
        if (inExistsResponse.isExists()) {

            DeleteIndexResponse dResponse = client.admin().indices().prepareDelete(index).execute().actionGet();
            return dResponse.isAcknowledged();

        } else {
            return false;
        }
    }
}
