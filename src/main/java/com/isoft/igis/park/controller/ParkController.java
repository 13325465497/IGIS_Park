package com.isoft.igis.park.controller;

import com.isoft.igis.common.utils.PageUtils;
import com.isoft.igis.common.utils.Query;
import com.isoft.igis.common.utils.R;
import com.isoft.igis.park.domain.ParkDO;
import com.isoft.igis.park.domain.ParkMapInfo;
import com.isoft.igis.park.domain.QueryInfo;
import com.isoft.igis.park.service.ParkService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @author whwlei
 * @email whwlei@163.com
 * @date 2018-12-06 09:25:43
 */

@Controller
@RequestMapping("/park/park")
public class ParkController {
    @Autowired
    private ParkService parkService;

    @GetMapping()
    @RequiresPermissions("park:park:park")
    String Park() {
        return "park/park/park";
    }

    @GetMapping("/baseMap")
    @RequiresPermissions("park:park:park")
    String baseMap() {
        return "park/park/baseMap";
    }

    //园区数据管理
    @GetMapping("/data")
    @RequiresPermissions("park:park:park")
    String ParkData() {
        return "park/park/parkData";
    }

    //园区数据管理
    @GetMapping("/igisEdit")
    @RequiresPermissions("park:park:park")
    String igisEdit() {
        return "park/park/igisEdit";
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("park:park:park")
    public PageUtils list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        List<ParkDO> parkList = parkService.list(query);
        int total = parkService.count(query);
        PageUtils pageUtils = new PageUtils(parkList, total);
        return pageUtils;
    }

    @ResponseBody
    @RequestMapping("/listTree")
    @RequiresPermissions("park:park:park")
    public Map<String, Object> listTree(QueryInfo queryInfo) {
        return parkService.listTree(queryInfo);
    }

    @GetMapping("/add")
    @RequiresPermissions("park:park:add")
    String add() {
        return "park/park/add";
    }

    @GetMapping("/outdoorAdd/{parkid}")
    @RequiresPermissions("park:park:add")
    String outdoorAdd(@PathVariable("parkid") Long parkid, Model model) {
        model.addAttribute("parkid", parkid);
        return "park/park/outdoorAdd";
    }

    @ResponseBody
    @PostMapping("/outdoorAddData")
    @RequiresPermissions("park:park:add")
    public R outdoorAddData(Long parkid, MultipartFile[] files) {
        return parkService.outdoorAddData(parkid, files);
    }

    @GetMapping("/edit/{parkid}")
    @RequiresPermissions("park:park:edit")
    String edit(@PathVariable("parkid") Long parkid, Model model) {
        ParkDO park = parkService.get(parkid);
        model.addAttribute("park", park);
        return "park/park/edit";
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    @RequiresPermissions("park:park:add")
    public R save(ParkDO park) {
        return parkService.save(park);
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("park:park:edit")
    public R update(ParkDO park) {
        parkService.update(park);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    @RequiresPermissions("park:park:remove")
    public R remove(Long parkid) {
        if (parkService.remove(parkid)) {
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
    public R remove(@RequestParam("ids[]") Long[] parkids) {
        parkService.batchRemove(parkids);
        return R.ok();
    }

    /**
     * 获取园区发布服务数据
     *
     * @param parkid
     * @return
     */
    @GetMapping("/ReleaseService/{parkid}")
    @RequiresPermissions("park:park:add")
    @ResponseBody
    public ParkMapInfo ReleaseService(@PathVariable("parkid") Long parkid) {
        return parkService.ReleaseService(parkid);
    }

    @PostMapping("/findParkDataStatus/{parkid}")
    @RequiresPermissions("park:park:park")
    @ResponseBody
    public boolean findParkDataStatus(@PathVariable("parkid") Long parkid) {
        return parkService.findParkDataStatus(parkid);
    }

    @ResponseBody
    @RequestMapping("/getParkRegionName")
    public List<String> getParkData(@RequestParam String parkName) {
        return parkService.getParkData(parkName);
    }

    @ResponseBody
    @RequestMapping("/baseMap/update")
    public R baseMapUpdate(@RequestParam String baseMapUrl) {
        return parkService.baseMapUpdate(baseMapUrl);
    }

    @ResponseBody
    @RequestMapping("/baseMap/get")
    public String baseMapGetBaseMapUrl() {
        return parkService.baseMapGetBaseMapUrl();
    }

}
