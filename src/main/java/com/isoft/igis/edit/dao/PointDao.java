package com.isoft.igis.edit.dao;

import com.isoft.igis.edit.domain.PointDataInfo;
import com.isoft.igis.edit.domain.PointInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Mapper
public interface PointDao {
    List<PointInfo> findAll();
    int add(PointInfo pointInfo);

    /**
     * 根据条件查询point
     * @param parkName
     * @param buildName
     * @param floorName
     * @param pointType
     * @param pointList
     * @return
     */
    List<PointInfo> search(@Param("parkName") String parkName, @Param("buildName")String buildName, @Param("floorName")String floorName, @Param("pointType")Integer pointType, @Param("pointList")List<Double[]> pointList,@Param("zoom") Integer zoom);

    /**
     * 批处理添加point
     * @param pointInfoList
     * @return
     */
    int insertBatch(@Param("pointInfoList")List<PointInfo> pointInfoList);
}
