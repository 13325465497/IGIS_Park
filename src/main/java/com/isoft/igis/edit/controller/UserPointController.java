package com.isoft.igis.edit.controller;

import com.isoft.igis.common.utils.PageUtils;
import com.isoft.igis.common.utils.R;
import com.isoft.igis.edit.domain.UserPointDO;
import com.isoft.igis.edit.service.UserPointService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * @author whwei
 * @email whwlei@163.com
 * @date 2019-04-12 15:28:10
 */

@Controller
@RequestMapping("/edit/userPoint")
public class UserPointController {
    @Autowired
    private UserPointService userPointService;

    @ResponseBody
    @GetMapping("/list")
    public PageUtils list(@RequestParam Map<String, Object> params) {
        List<UserPointDO> userPointList = userPointService.list(params);
        int total = userPointService.count(params);
        PageUtils pageUtils = new PageUtils(userPointList, total);
        return pageUtils;
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    public R save(UserPointDO userPoint) {
        if (userPointService.save(userPoint) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    public R update(UserPointDO userPoint) {
        userPointService.update(userPoint);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    @RequiresPermissions("edit:userPoint:remove")
    public R remove(Long id) {
        if (userPointService.remove(id) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 删除
     */
    @PostMapping("/batchRemove")
    @ResponseBody
    public R remove(@RequestParam("ids[]") Long[] ids) {
        userPointService.batchRemove(ids);
        return R.ok();
    }

}
