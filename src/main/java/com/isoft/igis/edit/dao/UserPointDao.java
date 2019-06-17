package com.isoft.igis.edit.dao;

import com.isoft.igis.edit.domain.UserPointDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author whwei
 * @email whwlei@163.com
 * @date 2019-04-12 15:28:10
 */
@Mapper
public interface UserPointDao {

    UserPointDO get(Long id);

    List<UserPointDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(UserPointDO userPoint);

    int update(UserPointDO userPoint);

    int remove(Long id);

    int batchRemove(Long[] ids);

    List<UserPointDO> getUserData(Map<String, Object> params);

    void deleteByParkName(String parkname);

    void deleteByParkNameAndBuildNameANdFloorName(Map<String, Object> params);
}
