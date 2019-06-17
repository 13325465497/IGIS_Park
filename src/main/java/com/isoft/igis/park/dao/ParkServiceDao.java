package com.isoft.igis.park.dao;

import com.isoft.igis.park.domain.ParkServiceDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ParkServiceDao {

    ParkServiceDO get(Long id);

    List<ParkServiceDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(ParkServiceDO parkService);

    int update(ParkServiceDO parkService);

    int remove(String parkname);

    int batchRemove(Long[] ids);
}
