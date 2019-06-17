package com.isoft.igis.park.controller;

import com.alibaba.fastjson.JSON;
import com.isoft.igis.common.utils.PageUtils;
import com.isoft.igis.common.utils.Query;
import com.isoft.igis.common.utils.R;
import com.isoft.igis.park.domain.ParkServiceDO;
import com.isoft.igis.park.service.BuildingService;
import com.isoft.igis.park.service.FloorService;
import com.isoft.igis.park.service.ParkService;
import com.isoft.igis.park.service.ParkServiceService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author whwlei
 * @email whwlei@163.com
 * @date 2019-03-25 15:57:35
 */

@Controller
@RequestMapping("/park/parkService")
public class ParkServiceController {
    @Autowired
    private ParkServiceService parkServiceService;
    @Autowired
    private ParkService parkService;
    @Autowired
    private BuildingService buildingService;
    @Autowired
    private FloorService floorService;

    @GetMapping()
    @RequiresPermissions("park:park:park")
    String Park() {
        return "park/parkService/parkService";
    }

    @GetMapping("/generate/{id}")
    String generate(Model model, @PathVariable("id") Long id) {
        ParkServiceDO serviceDO = this.get(id);
        String parkServiceJson = JSON.toJSONString(serviceDO);
        model.addAttribute("parkServiceJson", parkServiceJson);
        return "park/parkService/generate";
    }

    @ResponseBody
    @RequestMapping("/list")
    public PageUtils list(@RequestParam Map<String, Object> params) {
        Query query = new Query(params);
        int total = parkServiceService.count(query);
        List<ParkServiceDO> parkServiceList = parkServiceService.list(query);
        PageUtils pageUtils = new PageUtils(parkServiceList, total);
        return pageUtils;
    }

    @RequestMapping("/get")
    @ResponseBody
    public ParkServiceDO get(@RequestParam("id") Long id) {
        return parkServiceService.get(id);
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    public R save(ParkServiceDO parkService) {
        if (parkServiceService.save(parkService) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 修改
     */
    @ResponseBody
    @PostMapping("/update")
    public R update(@RequestBody ParkServiceDO parkServiceDO) {
        return parkServiceService.update(parkServiceDO);
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    public R remove(@RequestParam("parkname") String parkname) {
        if (parkServiceService.remove(parkname) > 0) {
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
        parkServiceService.batchRemove(ids);
        return R.ok();
    }

    /**
     *
     */
    @PostMapping("/getFileName/{type}/{id}")
    @ResponseBody
    public Map<String, String> getFileName(@PathVariable("type") Integer type, @PathVariable("id") Long id) {
        return parkServiceService.getFileName(type, id);
    }
}
