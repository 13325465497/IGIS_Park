package com.isoft.igis.park.dao;

import com.isoft.igis.park.domain.BuildingDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author whwlei
 * @email whwlei@163.com
 * @date 2018-12-06 09:25:37
 */
@Mapper
public interface BuildingDao {

    BuildingDO get(Long buildid);

    List<BuildingDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(BuildingDO building);

    int update(BuildingDO building);

    int remove(Long buildId);

    int batchRemove(Long[] buildids);

    /**
     * 根据id获取楼栋文件名
     *
     * @param buildid
     * @return
     */
    String getFileName(Long buildid);

    /**
     * 根据楼栋文件夹查询楼栋标签
     *
     * @param map
     * @return
     */
    String findBuildLableByBuildFileName(Map<String, Object> map);

    /**
     * 根据楼栋文件名查询楼栋id
     *
     * @param map
     * @return
     */
    Long findBuildIdByBuildFIleName(Map<String,Object> map);
}
