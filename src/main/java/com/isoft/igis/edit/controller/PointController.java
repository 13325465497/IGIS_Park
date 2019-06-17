package com.isoft.igis.edit.controller;

import com.isoft.igis.common.utils.R;
import com.isoft.igis.edit.domain.PointDataInfo;
import com.isoft.igis.edit.domain.PointInfo;
import com.isoft.igis.edit.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("point")
public class PointController {
    @Autowired
    private PointService pointService;

    @PostMapping("search")
    @ResponseBody
    public List<PointInfo> search(@RequestBody PointDataInfo pointDataInfo) {
        List<PointInfo> search = pointService.search(pointDataInfo);
        if (search == null) {
            search = new ArrayList<>();
        }
        return search;
    }

    @GetMapping("ImportPoint")
    public String ImportPoint() {
        return "park/park/parkPointAdd";
    }

    @PostMapping("ImportPoint")
    @ResponseBody
    public R ImportPoint(@RequestParam("file") MultipartFile file) throws IOException {
        if (file != null) {
            String filename = file.getOriginalFilename();
            if (filename.contains("csv")) {
                return pointService.ImportPointCSV(file);
            } else if (filename.contains("xls") || filename.contains("xlsx")) {
                return pointService.ImportPointEXCEL(file);
            }

        }
        return R.error("请上传Excel/csv格式数据");
    }
}
