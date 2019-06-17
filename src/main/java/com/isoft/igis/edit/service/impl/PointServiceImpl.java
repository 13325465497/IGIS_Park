package com.isoft.igis.edit.service.impl;

import au.com.bytecode.opencsv.CSVReader;
import com.isoft.igis.common.utils.GisUtils;
import com.isoft.igis.common.utils.R;
import com.isoft.igis.common.utils.StringUtils;
import com.isoft.igis.edit.dao.PointDao;
import com.isoft.igis.edit.domain.PointDataInfo;
import com.isoft.igis.edit.domain.PointInfo;
import com.isoft.igis.edit.service.PointService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class PointServiceImpl implements PointService {
    @Autowired
    private PointDao pointDao;

    @Override
    public List<PointInfo> findAll() {
        return pointDao.findAll();
    }

    @Override
    public int add(PointInfo pointInfo) {
        return pointDao.add(pointInfo);
    }

    @Override
    public List<PointInfo> search(PointDataInfo pointDataInfo) {
        if (pointDataInfo.getZoom() == null) {//校验地图级别级别
            return null;
        }
        if (StringUtils.isEmpty(pointDataInfo.getParkName())) {//校验所属园区
            return null;
        }
        Double[] point = pointDataInfo.getPoint();
        List<Double[]> pointList = null;
        if (point != null) {
            if (point.length == 4) {
                pointList = new ArrayList<Double[]>();
                Double[] point1 = {point[0], point[1]};
                Double[] point2 = {point[0], point[3]};
                Double[] point3 = {point[2], point[3]};
                Double[] point4 = {point[2], point[1]};
                pointList.add(point1);
                pointList.add(point2);
                pointList.add(point3);
                pointList.add(point4);
                pointList.add(point1);
            } else {
                return null;
            }
        } else {
            return null;
        }

        return pointDao.search(pointDataInfo.getParkName(), pointDataInfo.getBuildName(), pointDataInfo.getFloorName(), pointDataInfo.getPointType(), pointList, pointDataInfo.getZoom());
    }

    @Override
    @Transactional
    public R ImportPointCSV(MultipartFile file) throws IOException {
        List<PointInfo> pointInfoList = null;
        try {
            pointInfoList = new ArrayList<PointInfo>();
            CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream(), "GBK"));
            String[] header = reader.readNext();//拿到首行
            for (String headName : header) {
                System.out.print(headName + ",");
            }
            List<String[]> list = reader.readAll();
            PointInfo pointInfo;
            for (String[] strings : list) {
                pointInfo = new PointInfo();
                if (!StringUtils.isEmpty(strings[0])) {
                    pointInfo.setParkName(strings[0]);
                }
                if (!StringUtils.isEmpty(strings[1])) {
                    pointInfo.setBuildName(strings[1]);
                }
                if (!StringUtils.isEmpty(strings[2])) {
                    pointInfo.setFloorName(strings[2]);
                }
                if (!StringUtils.isEmpty(strings[3])) {
                    pointInfo.setLable(strings[3]);
                }
                if (!StringUtils.isEmpty(strings[4])) {
                    Double lon = Double.parseDouble(strings[4]);
                    if (lon != 0.0) {
                        pointInfo.setLon(lon);
                    }
                }
                if (!StringUtils.isEmpty(strings[5])) {
                    Double lat = Double.parseDouble(strings[5]);
                    if (lat != 0.0) {
                        pointInfo.setLat(lat);
                    }
                }
                if (!StringUtils.isEmpty(strings[6])) {
                    Integer pointType = Integer.parseInt(strings[6]);
                    if (pointType != null) {
                        pointInfo.setPointType(pointType);
                        pointInfo.setIconUrl(pointTypeIconUrl[pointType]);
                    }
                }
                pointInfoList.add(pointInfo);
            }
            GisUtils.pointCollisionCalc(20, pointInfoList);
        } catch (Exception e) {
            return R.error("导入数据格式有误");
        }
        pointDao.insertBatch(pointInfoList);
        int i = 20;
        if (i == pointInfoList.size()) {
            return R.ok();
        } else {
            try {
                int a = 1 / 0;//抛出异常
            } catch (Exception e) {
                //事务回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
        }
        return R.error("导入数据有误");
    }

    //1 ,烟感,2 ,摄像头 ,3 灯泡 , 4 , 吊灯 , 5 射灯
    private final String[] pointTypeIconUrl = {"0", "ing/icon/Smoke.png", "img/icon/Camera.png", "img/icon/Bulb.png", "img/icon/Chandelier.png", "img/icon/Spotlight.png"};

    @Override
    @Transactional
    public R ImportPointEXCEL(MultipartFile file) throws IOException {
        boolean notNull = false;
        List<PointInfo> pointInfoList = new ArrayList<PointInfo>();
        String fileName = file.getOriginalFilename();
        boolean isExcel2003 = true;
        if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
            isExcel2003 = false;
        }
        InputStream is = file.getInputStream();
        Workbook wb = null;
        if (isExcel2003) {
            wb = new HSSFWorkbook(is);
        } else {
            wb = new XSSFWorkbook(is);
        }
        Sheet sheet = wb.getSheetAt(0);
        if (sheet != null) {
            notNull = true;
        }
        if (!notNull) {

        }
        PointInfo PointInfo;
        if (sheet != null) {
            if (sheet.getLastRowNum() > 1) {
                for (int r = 1; r <= sheet.getLastRowNum(); r++) {
                    Row row = sheet.getRow(r);
                    if (row == null) {
                        continue;
                    }
                    PointInfo = new PointInfo();
                    String parkName = null;
                    if (row.getCell(0) != null) {
                        //园区名称
                        parkName = row.getCell(0).toString();
                        if (!"".equals(parkName)) {
                            PointInfo.setParkName(parkName);
                        }
                    }

                    String buildName = null;
                    if (row.getCell(1) != null) {
                        //楼栋名称
                        buildName = row.getCell(1).toString();
                        if (!"".equals(buildName)) {
                            PointInfo.setBuildName(buildName);
                        }
                    }
                    String floorName = null;
                    if (row.getCell(2) != null) {
                        //楼层名称
                        floorName = row.getCell(2).toString();
                        if (!"".equals(floorName)) {
                            PointInfo.setFloorName(floorName);
                        }
                    }
                    String lable = null;
                    if (row.getCell(3) != null) {
                        //标签
                        lable = row.getCell(3).toString();
                        if (!"".equals(lable)) {
                            PointInfo.setLable(lable);
                        }
                    }
                    Double lon = null;
                    if (row.getCell(4) != null) {
                        //经度
                        String pTypeString = row.getCell(4).toString();
                        if (!"".equals(pTypeString)) {
                            lon = Double.parseDouble(pTypeString);
                            if (lon != 0.0) {
                                PointInfo.setLon(lon);
                            }
                        }
                    }
                    Double lat = null;
                    if (row.getCell(5) != null) {
                        //纬度
                        String pTypeString = row.getCell(5).toString();
                        if (!"".equals(pTypeString)) {
                            lat = Double.parseDouble(pTypeString);
                            if (lat != 0.0) {
                                PointInfo.setLat(lat);
                            }
                        }
                    }
                    Double pointType = null;
                    if (row.getCell(6) != null) {
                        //点类型
                        String pTypeString = row.getCell(6).toString();
                        if (!"".equals(pTypeString)) {
                            pointType = Double.parseDouble(pTypeString);
                            PointInfo.setPointType(pointType.intValue());
                            PointInfo.setIconUrl(pointTypeIconUrl[pointType.intValue()]);//获取点类型图片
                        }
                    }
                    pointInfoList.add(PointInfo);
                }
            }
        }
        GisUtils.pointCollisionCalc(20, pointInfoList);
        pointDao.insertBatch(pointInfoList);
        int i = 20;
        if (i == pointInfoList.size()) {
            return R.ok();
        } else {
            try {
                int a = 1 / 0;//抛出异常
            } catch (Exception e) {
                //事务回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }

        }
        return R.error("批量添加失败");
    }

}
