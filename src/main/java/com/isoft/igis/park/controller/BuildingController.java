package com.isoft.igis.park.controller;

import com.isoft.igis.common.utils.PageUtils;
import com.isoft.igis.common.utils.Query;
import com.isoft.igis.common.utils.R;
import com.isoft.igis.park.domain.BuildingDO;
import com.isoft.igis.park.domain.ParkDO;
import com.isoft.igis.park.service.BuildingService;
import com.isoft.igis.park.service.ParkService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author whwlei
 * @email whwlei@163.com
 * @date 2018-12-06 09:25:37
 */

@Controller
@RequestMapping("/park/building")
public class BuildingController {
    @Autowired
    private BuildingService buildingService;

    @Autowired
    private ParkService parkService;

    @GetMapping()
    @RequiresPermissions("park:park:park")
    String Building() {
        return "park/building/building";
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("park:park:park")
    public PageUtils list(@RequestParam Map<String, Object> params) {
        //查询楼栋 + 添加分页
        Query query = new Query(params);
        //查询楼栋总记录
        List<BuildingDO> buildingList = buildingService.list(query);
        int total = buildingService.count(query);
        PageUtils pageUtils = new PageUtils(buildingList, total);
        return pageUtils;

    }

    @GetMapping("/add/{parkId}/{parkName}")
    @RequiresPermissions("park:park:add")
    String add(Model model, @PathVariable("parkId") Long parkId, @PathVariable("parkName") String parkName) {
        //查询所有园区
        model.addAttribute("parkId", parkId);
        model.addAttribute("parkName", parkName);
        //查询园区内部轮廓数据中的楼栋名称
        List<String> buildList = parkService.getParkData(parkService.getFileName(parkId));
        if (buildList != null) {
            model.addAttribute("buildList", buildList);
        } else {
            List<String> buildListNull = new ArrayList<>();
            buildListNull.add("您上传的园区内部轮廓数据有误");
            model.addAttribute("buildList", buildListNull);
        }
        return "park/building/add";
    }

    @GetMapping("/edit/{buildid}")
    @RequiresPermissions("park:park:edit")
    String edit(@PathVariable("buildid") Long buildid, Model model) {
        BuildingDO building = buildingService.get(buildid);
        ParkDO parkDO = parkService.get(building.getParkid());
        building.setParkName(parkDO.getParkname());
        model.addAttribute("building", building);
        return "park/building/edit";
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    @RequiresPermissions("park:park:add")
    public R save(BuildingDO building) {
        return buildingService.save(building);
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("park:park:edit")
    public R update(BuildingDO building) {
        buildingService.update(building);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    @RequiresPermissions("park:park:remove")
    public R remove(Long buildid) {
        if (buildingService.remove(buildid)) {
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
    public R remove(@RequestParam("ids[]") Long[] buildids) {
        buildingService.batchRemove(buildids);
        return R.ok();
    }

}
