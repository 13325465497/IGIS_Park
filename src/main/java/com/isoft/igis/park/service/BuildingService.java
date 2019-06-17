package com.isoft.igis.park.service;

import com.isoft.igis.common.utils.R;
import com.isoft.igis.park.domain.BuildingDO;

import java.util.List;
import java.util.Map;

/**
 * @author whwlei
 * @email whwlei@163.com
 * @date 2018-12-06 09:25:37
 */
public interface BuildingService {

    BuildingDO get(Long buildid);

    List<BuildingDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    R save(BuildingDO building);

    int update(BuildingDO building);

    boolean remove(Long buildid);

    int batchRemove(Long[] buildids);

    String getFileName(Long buildid);

    /**
     * 根据楼栋文件夹查询楼栋标签
     *
     * @param buildFileName
     * @param parkId
     * @return
     */
    String findBuildLableByBuildFileName(String buildFileName, Long parkId);

    /**
     * 根据楼栋文件名查询楼栋id
     *
     * @param buildFileName
     * @param parkId
     * @return
     */
    Long findBuildIdByBuildFIleName(String buildFileName, Long parkId);

}
