package com.isoft.igis.edit.service;

import com.isoft.igis.common.utils.R;
import com.isoft.igis.edit.domain.PointDataInfo;
import com.isoft.igis.edit.domain.PointInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PointService {
    List<PointInfo> findAll();

    int add(PointInfo pointInfo);

    /**
     * 根据条件查询point
     *
     * @param pointDataInfo
     * @return
     */
    List<PointInfo> search(PointDataInfo pointDataInfo);

    /**
     * 导入csv point
     *
     * @param file
     * @return
     */
    R ImportPointCSV(MultipartFile file) throws IOException;

    /**
     * 导入excel point
     *
     * @param file
     * @return
     */
    R ImportPointEXCEL(MultipartFile file) throws IOException;

}
