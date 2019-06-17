package com.isoft.igis.park.service;

import com.isoft.igis.common.utils.R;
import com.isoft.igis.park.domain.FloorDO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @author whwlei
 * @email whwlei@163.com
 * @date 2018-12-06 09:25:40
 */
public interface FloorService {

    FloorDO get(Long floorid);

    List<FloorDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    R save(FloorDO floor);

    int update(FloorDO floor);

    boolean remove(Long floorid);

    int batchRemove(Long[] floorids);

    /**
     * 上传楼层室内数据
     *
     * @param floorid
     * @param files
     * @return
     */
    R indoorAdd(Long floorid, MultipartFile[] files);

    String getFileName(Long floorid);

    /**
     * 根据楼层文件名查询楼层标签
     * @param floorFileName
     * @return
     */
    String findFloorLableByFloorFileName(String floorFileName,Long buildId);
}
