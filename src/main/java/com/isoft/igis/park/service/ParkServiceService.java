package com.isoft.igis.park.service;

import com.isoft.igis.common.utils.R;
import com.isoft.igis.park.domain.ParkServiceDO;

import java.util.List;
import java.util.Map;

/**
 * @author whwlei
 * @email whwlei@163.com
 * @date 2019-03-25 15:57:35
 */
public interface ParkServiceService {

    ParkServiceDO get(Long id);

    List<ParkServiceDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(ParkServiceDO parkService);

    R update(ParkServiceDO parkService);

    int remove(String parkname);

    int batchRemove(Long[] ids);

    Map<String, String> getFileName(Integer type, Long id);
}
