package com.isoft.igis.common.controller;

import org.springframework.stereotype.Controller;

import com.isoft.igis.common.utils.ShiroUtils;
import com.isoft.igis.system.domain.UserDO;

@Controller
public class BaseController {
	public UserDO getUser() {
		return ShiroUtils.getUser();
	}

	public Long getUserId() {
		return getUser().getUserId();
	}

	public String getUsername() {
		return getUser().getUsername();
	}
}