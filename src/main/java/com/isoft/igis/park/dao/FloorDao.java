package com.isoft.igis.park.dao;

import com.isoft.igis.park.domain.FloorDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author whwlei
 * @email whwlei@163.com
 * @date 2018-12-06 09:25:40
 */
@Mapper
public interface FloorDao {

    FloorDO get(Long floorid);

    List<FloorDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(FloorDO floor);

    int update(FloorDO floor);

    int remove(Long floorId);

    int batchRemove(Long[] floorids);

    /**
     * 根据id获取楼层文件名
     *
     * @param floorid
     * @return
     */
    String getFileName(Long floorid);

    /**
     * 修改楼层数据状态
     *
     * @param param
     * @return
     */
    int setFloorDataStatus(Map<String, Object> param);

    /**
     * 根据楼层文件名查询楼层标签
     *
     * @param map
     * @return
     */
    String findFloorLableByFloorFileName(Map<String, Object> map);
}
