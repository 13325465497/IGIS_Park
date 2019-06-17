package com.isoft.igis.common.utils;

import java.util.ArrayList;
import java.util.List;

import org.gdal.gdal.gdal;
import org.gdal.ogr.DataSource;
import org.gdal.ogr.Feature;
import org.gdal.ogr.Layer;
import org.gdal.ogr.ogr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SHPUtils {
	
	private static Logger logger = LoggerFactory.getLogger(SHPUtils.class);

	public static void initGDAL() {
		gdal.AllRegister();
		ogr.RegisterAll();
		gdal.SetConfigOption("GDAL_FILENAME_IS_UTF8", "YES");
		gdal.SetConfigOption("SHAPEFILE_ENCODING", "");
		gdal.SetConfigOption("GDAL_DATA", "D:/home/Library/gdal_data");
	}

	/**
	 * geojson转shp
	 */
	public static void jsonTOShp(String jsonPath, String shpPath) {
		// 打开数据
		DataSource ds = ogr.Open(jsonPath, 0);
		if (ds == null) {
			logger.error("打开文件失败！");
			return;
		}
		// GeoJSON shp转json的驱动
		// 面的记录  ESRI Shapefile
		// 线的记录  ESRI Shapefile
		// 点的记录  ESRI Shapefile
		String strDriverName = "ESRI Shapefile";
		org.gdal.ogr.Driver dv = ogr.GetDriverByName(strDriverName);
		if (dv == null) {
			logger.error("打开驱动失败！");
			return;
		}
		dv.CopyDataSource(ds, shpPath);
		dv.delete();
		ds.delete();
	}

	/**
	 * shp转geojson
	 */
	public static boolean shpTOJson(String shpPath, String jsonPath) {
		// 打开数据
		DataSource ds = ogr.Open(shpPath, 0);
		if (ds == null) {
			logger.error("打开文件失败！");
			return false;
		}
		// GeoJSON shp转json的驱动
		org.gdal.ogr.Driver dv = ogr.GetDriverByName("GeoJSON");
		if (dv == null) {
			logger.error("打开驱动失败！");
			return false;
		}
		dv.CopyDataSource(ds, jsonPath);
		dv.delete();
		ds.delete();
		return true;
	}
	
	public static List<String> getRegionName(String shpPath) {
		// 打开数据
		DataSource ds = ogr.Open(shpPath, 0);
		if (ds == null) {
			logger.error("打开文件失败！");
			return null;
		}
		Layer layer = ds.GetLayer(0);
		if (null == layer) {
			logger.error("打开文件失败！");
			return null;
		}
		long featureCount = layer.GetFeatureCount();
		List<String> regionNameList = new ArrayList<String>(); 
		for (long i = 0; i < featureCount; i++) {
			Feature feature = layer.GetFeature(i);
			if (null == feature) {
				logger.error("打开文件失败！");
				return null;
			}
			String name = feature.GetFieldAsString("buildname");
			if (!StringUtils.isAllEmpty(name)){
				regionNameList.add(name);
			}
			feature.delete();
		}
		layer.delete();
		ds.delete();
		return regionNameList;
	}

}
