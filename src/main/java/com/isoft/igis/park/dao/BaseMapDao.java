package com.isoft.igis.park.dao;

import com.isoft.igis.park.domain.BaseMapDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author whwei
 * @email whwlei@163.com
 * @date 2019-04-15 10:43:17
 */
@Mapper
public interface BaseMapDao {
    BaseMapDO get(Long id);

    int save(BaseMapDO baseMap);

    int update(BaseMapDO baseMap);

    int remove(Long id);

}
