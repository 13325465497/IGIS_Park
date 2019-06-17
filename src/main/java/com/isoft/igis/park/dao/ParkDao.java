package com.isoft.igis.park.dao;

import com.isoft.igis.park.domain.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author whwlei
 * @email whwlei@163.com
 * @date 2018-12-06 09:25:43
 */
@Mapper
public interface ParkDao {

    ParkDO get(Long parkid);

    List<ParkDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(ParkDO park);

    int update(ParkDO park);

    int remove(Long parkId);

    int batchRemove(Long[] parkids);

    /**
     * 根据id获取园区文件名
     * @param parkid
     * @return
     */
    String getFileName(Long parkid);
    /**
     * 更新园区数据状态
     *
     * @param parkDataStatus
     * @return
     */
    int setParkDataStatus(Map<String, Object> parkDataStatus);

    /**
     * 查询园区数据(封装服务bean)
     *
     * @param parkid
     * @return
     */
    ParkMapInfo ReleaseParkService(Long parkid);

    /**
     * 查询园区下的所有楼栋
     *
     * @param parkid
     * @return
     */
    List<BuildMapInfo> ReleaseBuildService(Long parkid);

    /**
     * 查询楼栋下的所有楼层
     *
     * @param buildid
     * @return
     */
    List<FloorMaoInfo> ReleaseFloorService(Long buildid);

    List<ParkMapInfo> listTree(QueryInfo queryInfo);

    /**
     * 查询园区已上传数据
     * @param parkid
     * @return
     */
    String findParkDataStatus(Long parkid);
    /**
     * 根据文件名称查询园区id
     * @param parkFileName
     * @return
     */
    Long findParkIdByParkFileName(String parkFileName);
}
