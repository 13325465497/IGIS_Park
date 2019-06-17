package com.isoft.igis.common.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.Charset;

import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.geojson.feature.FeatureJSON;
import org.opengis.feature.simple.SimpleFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 读取shp格式文件，转成json格式数据
 */
public class FormatShp {
	
	private static Logger logger = LoggerFactory.getLogger(SHPUtils.class);

	public static boolean shpTOJson(String shpPath, String jsonPath) {
		StringBuilder sb = new StringBuilder();
		FeatureJSON fJson = new FeatureJSON();
		File fileShp = new File(shpPath);// 读取shp文件
		if (fileShp == null || !fileShp.exists()) {
			return false;
		}
		ShapefileDataStore store = null;
		SimpleFeatureSource featureSource = null;
		JSONArray array = new JSONArray();
		JSONObject json = new JSONObject();
		FileOutputStream fos = null;
		try {
			sb.append("{\"type\": \"FeatureCollection\",\"features\": ");
			store = new ShapefileDataStore(fileShp.toURL());// 获取文件
			store.setCharset(Charset.forName("UTF-8"));// 设置字符集
			String typeName = store.getTypeNames()[0];
			featureSource = store.getFeatureSource(typeName);
			SimpleFeatureCollection collection = featureSource.getFeatures();
			SimpleFeatureIterator iterator = collection.features();
			while (iterator.hasNext()) {
				SimpleFeature feature = iterator.next();
				StringWriter writer = new StringWriter();
				fJson.writeFeature(feature, writer);
				json = JSONObject.parseObject(writer.toString());
				array.add(json);
			}
			iterator.close();
			sb.append(array);
			sb.append("}");
		} catch (IOException e) {
			logger.error("解析shp文件失败");
		}
		try {
			File fileJson = new File(jsonPath);
			fos = new FileOutputStream(fileJson);
			fos.write(sb.toString().getBytes("utf-8"));
		} catch (Exception e) {
			logger.error("输出json文件失败");
		} finally {
			if (null != fos) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return true;
	}

	public static void main(String[] args) {
		shpTOJson("D:/Develop/IdeaProjects/IGIS_park/data/xietongchuangxingang/outdoor/wfs/poi.shp",
				"D:/MYC/tools/apache-tomcat-8.0.51/webapps/IGIS_Park/WEB-INF/classes/static/json/poitest.json");
	}
}
