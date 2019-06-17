package com.isoft.igis.edit.service.impl;

import com.isoft.igis.edit.dao.UserPointDao;
import com.isoft.igis.edit.domain.UserPointDO;
import com.isoft.igis.edit.service.UserPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class UserPointServiceImpl implements UserPointService {
    @Autowired
    private UserPointDao userPointDao;

    @Override
    public UserPointDO get(Long id) {
        return userPointDao.get(id);
    }

    @Override
    public List<UserPointDO> list(Map<String, Object> map) {
        return userPointDao.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return userPointDao.count(map);
    }

    @Override
    public int save(UserPointDO userPoint) {
        Double random = (Math.random() * 9 + 1) * 10000;
        userPoint.setId(random.longValue());
        return userPointDao.save(userPoint);
    }

    @Override
    public int update(UserPointDO userPoint) {
        return userPointDao.update(userPoint);
    }

    @Override
    public int remove(Long id) {
        return userPointDao.remove(id);
    }

    @Override
    public int batchRemove(Long[] ids) {
        return userPointDao.batchRemove(ids);
    }

    @Override
    public List<UserPointDO> getUserData(Map<String, Object> params) {
        String buildname = (String) params.get("buildname");
        String floorname = (String) params.get("floorname");
        if (buildname == null || floorname == null) {
            return userPointDao.getUserData(params);
        } else {
            return userPointDao.list(params);
        }

    }

    @Override
    public void deleteByParkName(String parkname) {
        userPointDao.deleteByParkName(parkname);
    }

    @Override
    public void deleteByParkNameAndBuildNameANdFloorName(Map<String, Object> params) {
        userPointDao.deleteByParkNameAndBuildNameANdFloorName(params);
    }

}
