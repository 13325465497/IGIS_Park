package com.isoft.igis.park.service.impl;

import com.alibaba.fastjson.JSON;
import com.isoft.igis.common.config.IGISParkConfig;
import com.isoft.igis.common.utils.*;
import com.isoft.igis.edit.service.UserPointService;
import com.isoft.igis.park.dao.BaseMapDao;
import com.isoft.igis.park.dao.ParkDao;
import com.isoft.igis.park.domain.*;
import com.isoft.igis.park.service.BuildingService;
import com.isoft.igis.park.service.FloorService;
import com.isoft.igis.park.service.ParkService;
import com.isoft.igis.park.service.ParkServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ParkServiceImpl implements ParkService {
    @Autowired
    private ParkDao parkDao;
    @Autowired
    private BaseMapDao baseMapDao;
    @Autowired
    IGISParkConfig igisConfig;
    private HanyuPinyinHelper hanyuPinyinHelper = new HanyuPinyinHelper();
    @Autowired
    private ParkServiceService parkServiceService;

    @Override
    public ParkDO get(Long parkid) {
        return parkDao.get(parkid);
    }

    @Override
    public List<ParkDO> list(Map<String, Object> map) {
        return parkDao.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return parkDao.count(map);
    }

    @Override
    @Transactional
    public R save(ParkDO park) {

        // 根据园区名称 配置园区文件路径  +D:/Develop/IdeaProjects/IGIS_park/ + 园区字母
        park.setParkdir(igisConfig.getParkPath() + hanyuPinyinHelper.toHanyuPinyin(park.getParkname()) + "/");
        File file = new File(park.getParkdir());
        if (file.exists()) {
            return R.error("园区已经存在！");
        } else if (!file.mkdirs()) {
            return R.error();
        }
        //创建园区 室内,室外(车导,步导)目录
        File indoor = new File(file, "indoor");
        File outdoor = new File(file, "outdoor");
        File walk = new File(outdoor, "walk");
        File drive = new File(outdoor, "drive");
        File wms = new File(outdoor, "wfs");
        File wfs = new File(outdoor, "wms");
        if (indoor.mkdirs() && outdoor.mkdirs() && walk.mkdirs() && drive.mkdirs() && wms.mkdirs() && wfs.mkdirs()) {
            park.setParkDataStatus("[1,1,1,1,1]");//刚创建都未上传数据
            Double random = (Math.random() * 9 + 1) * 10000;
            park.setParkid(random.longValue());
            park.setParkfilename(hanyuPinyinHelper.toHanyuPinyin(park.getParkname()));//园区名称
            if (parkDao.save(park) > 0) {
                ParkServiceDO serviceDO = new ParkServiceDO();
                serviceDO.setParkname(hanyuPinyinHelper.toHanyuPinyin(park.getParkname()));
                serviceDO.setParkusername(park.getParkname());
                int save = parkServiceService.save(serviceDO);
                if (save > 0) {
                    return R.ok();
                }
                return R.error();
            }
        }
        return R.error();
    }

    @Override
    public int update(ParkDO park) {
        return parkDao.update(park);
    }

    @Autowired
    private BuildingService buildingService;
    @Autowired
    private FloorService floorService;
    @Autowired
    private UserPointService userPointService;
    @Override
    @Transactional
    public synchronized boolean remove(Long parkid) {
        //查询园区, 获取数据路径
        ParkDO park = this.get(parkid);
        File file = new File(park.getParkdir());
        //级联删除园区数据, 及该园区,楼栋,楼层信息
        if (file.exists()) {
            Map<String, Object> map = new HashMap<>();
            map.put("parkid", parkid);
            //查询该园区 - 所有楼栋
            List<BuildingDO> list = buildingService.list(map);
            if (list != null) {
                for (BuildingDO buildingDO : list) {
                    buildingService.remove(buildingDO.getBuildid());//删除园区的楼栋
                }
            }
            DeleteDirUtils.deleteDir(file);
            userPointService.deleteByParkName(park.getParkfilename());//删除园区新增poi点
            if (parkDao.remove(parkid) > 0) {
                int remove = parkServiceService.remove(hanyuPinyinHelper.toHanyuPinyin(park.getParkname()));
                if (remove > 0) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    @Override
    public int batchRemove(Long[] parkids) {
        int r = 1;
        if (parkids != null) {
            for (Long parkid : parkids) {
                boolean remove = this.remove(parkid);
                if (!remove) {
                    r = 0;
                }
            }
        }
        return r;
    }

    @Override
    public R outdoorAddData(Long parkid, MultipartFile[] files) {
        String[] pirkDir = {"wms", "wfs", "wfs", "walk", "drive"};
        String[] rename = {"", "region", "poi", "link", "link"};
        ParkDO parkDO = this.get(parkid);
        try {
            if (files != null) {
                String parkDataStatus = parkDO.getParkDataStatus();
                List<Integer> dataStatus = JSON.parseObject(parkDataStatus, List.class);
                for (int i = 0; i < files.length; i++) {
                    if (files[i] != null && files[i].getOriginalFilename().trim().length() > 0) {//标书有数据
                        dataStatus.set(i, 0);
                        String end1Name = files[i].getOriginalFilename();
                        end1Name = end1Name.substring(end1Name.lastIndexOf(".") + 1).trim().toLowerCase();
                        //临时zip位置 不存在则创建
                        String ZipPath = igisConfig.getUploadPath() + System.currentTimeMillis() + "." + end1Name;
                        files[i].transferTo(new File(ZipPath));
                        //解压到园区位置
                        String parkDirPath = parkDO.getParkdir() + "outdoor/" + pirkDir[i] + "/";
                        // ZipPath 临时zip存放目录 , parkDirPath 园区存放目录
                        boolean flag = ZipUtils.JieYa(ZipPath, parkDirPath, rename[i]);
                        if (!flag) {
                            return R.error("上传园区数据失败");
                        }
                    }
                }
                //更改状态
                Map<String, Object> map = new HashMap<>();
                map.put("parkid", parkid);
                map.put("ParkDataStatus", dataStatus.toString());
                parkDao.setParkDataStatus(map);
            }
        } catch (Exception e) {
            return R.error();
        }
        R r=new R();
        r.put("code",0);
        r.put("parkName",parkDO.getParkfilename());
        return r;
    }

    @Override
    public ParkMapInfo ReleaseService(Long parkid) {
        ParkMapInfo mapInfo = parkDao.ReleaseParkService(parkid);
        String dbStatus = mapInfo.getParkDataStatus();
        List<Integer> dbStatusList = JSON.parseObject(dbStatus, List.class);
        mapInfo.setBaseMapStatus(dbStatusList.get(0));//底图
        mapInfo.setOutlineStatus(dbStatusList.get(1));//轮廓
        mapInfo.setPoiStatus(dbStatusList.get(2));//poi
        mapInfo.setWalkStatus(dbStatusList.get(3));//步道
        mapInfo.setDriveStatus(dbStatusList.get(4));//车导
        mapInfo.setParkName(mapInfo.getParkUserName());//园区文件名称
        mapInfo.setParkUserName(mapInfo.getParklable());//园区显示名(Lable标签)
        List<BuildMapInfo> builds = mapInfo.getBuilds();
        Integer floorDataStatus = 1;
        if (builds != null) {
            for (BuildMapInfo build : builds) {
                build.setBuildName(build.getBuildUserName());
                build.setBuildUserName(build.getBuildlable());//楼栋显示名(Lable标签)
                if (build.getFloors() != null) {
                    for (FloorMaoInfo floor : build.getFloors()) {
                        floor.setFloorName(floor.getFloorUserName());
                        floor.setFloorUserName(floor.getFloorlable());//楼层显示名(Lable标签)
                        if (floor.getFloorDataStatus() == 0) {//表示有一个楼层有必须数据
                            floorDataStatus = 0;
                        }
                    }
                }
            }
        }
        mapInfo.setFloorDataStatus(floorDataStatus);//楼层数据
        return mapInfo;
    }

    @Override
    public Map<String, Object> listTree(QueryInfo queryInfo) {
        Map<String, Object> map = new HashMap<>();
        if (queryInfo.getParkName() != null && queryInfo.getParkName().trim().length() > 0) {
            queryInfo.setParkName("%" + queryInfo.getParkName() + "%");
            map.put("parkname", queryInfo.getParkName());
        } else {
            queryInfo.setParkName(null);
        }
        LinkedList<ParkTree> treeLinkedList = new LinkedList<>();
        if (queryInfo.getPageNum() != null) {
            queryInfo.setPageNum((queryInfo.getPageNum() - 1) * queryInfo.getPageSize());
        }
        List<ParkMapInfo> parkMapInfoList = parkDao.listTree(queryInfo);
        //查询总记录数
        int total = this.count(map);
        ParkTree parkTree = null;
        for (ParkMapInfo parkMap : parkMapInfoList) {
            parkTree = new ParkTree();
            parkTree.setParentId(0L);//父id 0
            parkTree.setArea(parkMap.getParkArea());
            parkTree.setName(parkMap.getParkName());
            parkTree.setId(parkMap.getParkid());
            parkTree.setLable(parkMap.getParklable());
            parkTree.setType(0);
            treeLinkedList.add(parkTree);
            List<BuildMapInfo> buildMapInfoList = parkMap.getBuilds();
            for (BuildMapInfo buildMap : buildMapInfoList) {
                parkTree = new ParkTree();
                parkTree.setId(buildMap.getBuildid());
                parkTree.setArea(buildMap.getBuildArea());
                parkTree.setParentId(buildMap.getParkid());//父id
                parkTree.setName(buildMap.getBuildName());
                parkTree.setLable(buildMap.getBuildlable());
                parkTree.setType(1);
                treeLinkedList.add(parkTree);
                List<FloorMaoInfo> floorMaoInfoList = buildMap.getFloors();
                for (FloorMaoInfo floorMap : floorMaoInfoList) {
                    parkTree = new ParkTree();
                    parkTree.setName(floorMap.getFloorName());
                    parkTree.setId(floorMap.getFloorid());
                    parkTree.setLable(floorMap.getFloorlable());
                    parkTree.setParentId(floorMap.getBuildid());
                    parkTree.setArea(floorMap.getFloorArea());
                    parkTree.setType(2);
                    treeLinkedList.add(parkTree);
                }
            }
        }
        map.put("row", treeLinkedList);
        map.put("total", total);
        map.put("pages", (total + (queryInfo.getPageSize() - 1)) / queryInfo.getPageSize());
        return map;
    }

    @Override
    public String getFileName(Long parkid) {
        return parkDao.getFileName(parkid);
    }

    @Override
    public boolean findParkDataStatus(Long parkid) {
        try {
            String parkDataStatus = parkDao.findParkDataStatus(parkid);
            List<Integer> dataStatus = JSON.parseObject(parkDataStatus, List.class);
            if (dataStatus != null) {
                if (dataStatus.get(1) == 0) {//是否存在园区园区内部轮廓数据
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public List<String> getParkData(String parkName) {
        String regionPath = igisConfig.getParkPath() + "/" + parkName + "/outdoor/wfs/region.shp";
        return SHPUtils.getRegionName(regionPath);
    }

    @Override
    public Long findParkIdByParkFileName(String parkFileName) {
        return parkDao.findParkIdByParkFileName(parkFileName);
    }

    @Override
    public R baseMapUpdate(String baseMapUrl) {
        if (baseMapUrl != null && baseMapUrl.trim().length() > 0) {
            BaseMapDO baseMapDO = new BaseMapDO();
            baseMapDO.setId(1L);//默认的
            baseMapDO.setBasemapurl(baseMapUrl);
            int update = baseMapDao.update(baseMapDO);
            if (update > 0) {
                return R.ok();
            }
        }
        return R.error("操作失败,请输入底图Url");
    }

    @Override
    public String baseMapGetBaseMapUrl() {
        BaseMapDO baseMapDO = baseMapDao.get(1L);//默认的
        if (baseMapDO != null) {
            String basemapurl = baseMapDO.getBasemapurl();
            return basemapurl;
        }
        return null;
    }

}
