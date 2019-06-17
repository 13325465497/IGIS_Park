package com.isoft.igis.common.service;

import org.springframework.stereotype.Service;

import com.isoft.igis.common.domain.LogDO;
import com.isoft.igis.common.domain.PageDO;
import com.isoft.igis.common.utils.Query;
@Service
public interface LogService {
	void save(LogDO logDO);
	PageDO<LogDO> queryList(Query query);
	int remove(Long id);
	int batchRemove(Long[] ids);
	void DeleteAll();
}
