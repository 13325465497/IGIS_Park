package com.isoft.igis.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.isoft.igis.edit.domain.PointInfo;

public class GisUtils {

	public static final double[] projBounds = new double[] { -20037508.342789248, -20037508.342789248,
			20037508.342789248, 20037508.342789248 };

	public static final double[] geoBounds = new double[] { -180, -90, 180, 90 };
	public static final int minLevel = 10;
	public static final int maxLevel = 28;

	public static void pointCollisionCalc(int iconSize, List<PointInfo> pointList) {
		Map<String, List<PointInfo>> map = new HashMap<String, List<PointInfo>>();
		for (PointInfo pointInfo : pointList) {
			if (map.get(pointInfo.getBuildName() + pointInfo.getFloorName()) == null) {
				List<PointInfo> list = new ArrayList<PointInfo>();
				list.add(pointInfo);
				map.put(pointInfo.getBuildName() + pointInfo.getFloorName(), list);
			} else {
				map.get(pointInfo.getBuildName() + pointInfo.getFloorName()).add(pointInfo);
			}
		}
		pointList.clear();
		Set<String> set = map.keySet();
		List<PointInfo> samePlanePointList;
		for (String string : set) {
			samePlanePointList = map.get(string);
			for (int i = minLevel; i <= maxLevel; i++) {
				double resulotion = getResolution(i, "4326");
				double distance = resulotion * iconSize;
				List<double[]> exists = new ArrayList<double[]>();
				for (PointInfo pointInfo : samePlanePointList) {
					double[] point = new double[] { pointInfo.getLon(), pointInfo.getLat() };
					double[] extent = new double[] { point[0] - distance / 2, point[1] - (distance / 2),
							point[0] + distance / 2, point[1] + (distance / 2) };
					boolean flag = true;
					for (double[] tExists : exists) {
						if (!(extent[0] > tExists[2] || extent[1] > tExists[3] || extent[2] < tExists[0]
								|| extent[3] < tExists[1])) {
							flag = false;
							break;
						}
					}
					if (flag) {
						exists.add(extent);
						if (null == pointInfo.getDisplayLevel()) {
							pointInfo.setDisplayLevel(i + ",");
						} else {
							pointInfo.setDisplayLevel(pointInfo.getDisplayLevel() + i + ",");
						}
					}
					
				}
			}
		}
		for (String string : set) {
			pointList.addAll(map.get(string));
		}
		map.clear();
	}

	public static double getResolution(int zoom, String srid) {

		if (StringUtils.equals("900913", srid) || StringUtils.equals("3857", srid)) {
			return projBounds[2] * 2 / 256 / Math.pow(2, zoom);
		} else if (StringUtils.equals("4326", srid)) {
			return geoBounds[2] * 2 / 256 / Math.pow(2, zoom);
		}
		return -1;
	}
}
