package com.isoft.igis.park.controller;

import com.isoft.igis.common.utils.*;
import com.isoft.igis.park.domain.BuildingDO;
import com.isoft.igis.park.domain.FloorDO;
import com.isoft.igis.park.domain.ParkDO;
import com.isoft.igis.park.service.BuildingService;
import com.isoft.igis.park.service.FloorService;
import com.isoft.igis.park.service.ParkService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author whwlei
 * @email whwlei@163.com
 * @date 2018-12-06 09:25:40
 */

@Controller
@RequestMapping("/park/floor")
public class FloorController {
    @Autowired
    private FloorService floorService;

    @Autowired
    private BuildingService buildingService;

    @Autowired
    private ParkService parkService;

    @GetMapping()
    @RequiresPermissions("park:park:park")
    String Floor() {
        return "park/floor/floor";
    }

    @GetMapping("/indoorAdd/{floorid}")
    @RequiresPermissions("park:park:add")
    String indoorAdd(@PathVariable("floorid") Long floorid, Model model) {
        model.addAttribute("floorid", floorid);
        return "park/floor/indoorAdd";
    }

    @ResponseBody
    @PostMapping("/indoorAddData")
    @RequiresPermissions("park:park:add")
    public R indoorAdd(Long floorid, MultipartFile[] files) {
        return floorService.indoorAdd(floorid, files);
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("park:park:park")
    public PageUtils list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        List<FloorDO> floorList = floorService.list(query);
        int total = floorService.count(query);
        PageUtils pageUtils = new PageUtils(floorList, total);
        return pageUtils;
    }

    @GetMapping("/add/{buildId}/{buildName}")
    @RequiresPermissions("park:park:add")
    String add(Model model, @PathVariable("buildId") Long buildId, @PathVariable("buildName") String buildName) {
        //查询所有楼栋
        //model.addAttribute("park", parkService.list(new HashMap<String, Object>()));
        model.addAttribute("buildId", buildId);
        model.addAttribute("buildName", buildName);
        BuildingDO buildingDO = buildingService.get(buildId);
        ParkDO parkDO = parkService.get(buildingDO.getParkid());
        model.addAttribute("parkId", parkDO.getParkid());
        model.addAttribute("parkName", parkDO.getParkname());
        return "park/floor/add";
    }

    @ResponseBody
    @GetMapping("/getBuilding")
    R getBuilding(Long parkid) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("parkid", parkid);
        List<BuildingDO> buildings = buildingService.list(map);
        return R.ok(JSONUtils.listToJson(buildings));
    }

    @GetMapping("/edit/{floorid}")
    @RequiresPermissions("park:park:edit")
    String edit(@PathVariable("floorid") Long floorid, Model model) {
        FloorDO floor = floorService.get(floorid);
        BuildingDO buildingDO = buildingService.get(floor.getBuildid());
        ParkDO parkDO = parkService.get(buildingDO.getParkid());
        floor.setParkName(parkDO.getParkname());
        floor.setBuildingName(buildingDO.getBuildname());
        model.addAttribute("floor", floor);
        return "park/floor/edit";
    }

    private HanyuPinyinHelper hanyuPinyinHelper = new HanyuPinyinHelper();

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    @RequiresPermissions("park:park:add")
    public R save(FloorDO floor) {
        return floorService.save(floor);
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("park:park:edit")
    public R update(FloorDO floor) {
        floorService.update(floor);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    @RequiresPermissions("park:park:remove")
    public R remove(Long floorid) {
        if (floorService.remove(floorid)) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 删除
     */
    @PostMapping("/batchRemove")
    @ResponseBody
    @RequiresPermissions("park:park:batchRemove")
    public R remove(@RequestParam("ids[]") Long[] floorids) {
        floorService.batchRemove(floorids);
        return R.ok();
    }

}
