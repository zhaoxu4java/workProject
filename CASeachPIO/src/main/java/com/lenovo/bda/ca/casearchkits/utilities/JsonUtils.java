package com.lenovo.bda.ca.casearchkits.utilities;

import java.io.IOException;

import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import com.lenovo.bda.ca.casearchkits.entities.Blog;

public class JsonUtils {
	public static void main(String[] args){
		System.out.println(CleanUpSpecialChars("\\中影电影院为融汇影视大视野，明亮度，进场效果灯方面的设计理念的观影场所。影城以现代时尚，高端数字化为主题（放映机均为数字放映机），运用现代多厅影城的管理手段，并通过中国电影集团自身的行业资源优势和强大的院线支持为观念提供高品质的试听享受。营业面积4300平方米，共8个放映厅，可以容纳1343人同时观影。其中6、7号影厅通往VIP休息室。休息室可用作贵宾和领导休息，媒体见面会及影迷交流。影城还设有品种齐全的小食部，礼品部，电影纪念品展示长廊，互动式电子海报播出屏。\"中影国际影城（北清路永旺店）为融汇国际一流影城大视野、明亮度、进场效果等方面的设计理念的观影场所。 影城以现代时尚、高端数字化为主题（放映机均为数字放映机），运用现代多厅影城的管理手段，并通过中国电影集团自身的行业资源优势和**强大的院线支持为观众提供高品质的视听享受。营业面积4300平方米，共8个放映厅，可以容纳1344人同时观影。其中，6、7号影厅通往VIP休息室。此休息室可用作贵宾和领导休息，媒体见面会，及影迷交流。影城还设有品种齐全的小食部，礼品部，电影纪念品展示长廊，互动式电子海报播出屏。 半个多世纪以来，中国电影集团（简称中影）制作了近千部影片，占据中国电影制片业的**优势地位。同时，它还是中国中外影片发行的主渠道，拥有中国**完善，**强大的影片发行网络渠道。它将成为中国电影终端市场（电影院）建设的国内知名投资实体，拥有政策，资源，资金，品牌的**优势地位。\"城大堂 免押金，免费提供镜片擦拭纸"));
	}
	
	
	// Java实体对象转json对象
    public static String model2Json(Blog blog) {
        String jsonData = null;
        try {
            XContentBuilder jsonBuild = XContentFactory.jsonBuilder();
            jsonBuild.startObject().field("id", blog.getId()).field("title", blog.getTitle())
                    .field("posttime", blog.getPosttime()).field("content",blog.getContent()).endObject();

            jsonData = jsonBuild.string();
            //System.out.println(jsonData);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonData;
    }
    
    
    public static String CleanUpSpecialChars(String oldStr){
    	if(oldStr == null || oldStr.length() == 0){
    		return null;
    	}
    	
    	return	oldStr.replaceAll("\"", "'").replaceAll("\\\\", "/");	
    }
    
    
}
