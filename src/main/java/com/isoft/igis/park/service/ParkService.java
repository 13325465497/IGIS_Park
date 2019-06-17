package com.isoft.igis.park.service;

import com.isoft.igis.common.utils.R;
import com.isoft.igis.park.domain.ParkDO;
import com.isoft.igis.park.domain.ParkMapInfo;
import com.isoft.igis.park.domain.QueryInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @author whwlei
 * @email whwlei@163.com
 * @date 2018-12-06 09:25:43
 */
public interface ParkService {

    ParkDO get(Long parkid);

    List<ParkDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    R save(ParkDO park);

    int update(ParkDO park);

    boolean remove(Long parkid);

    int batchRemove(Long[] parkids);

    /**
     * 上传园区室外数据
     *
     * @param parkid
     * @param files
     * @return
     */
    R outdoorAddData(Long parkid, MultipartFile[] files);

    /**
     * 获取园区发布服务数据
     *
     * @param parkid
     * @return
     */
    ParkMapInfo ReleaseService(Long parkid);

    Map<String, Object> listTree(QueryInfo queryInfo);

    String getFileName(Long parkid);

    /**
     * 查询园区是否有wms
     *
     * @param parkid
     * @return
     */
    boolean findParkDataStatus(Long parkid);

    /**
     * 查询园区内部轮廓数据中的楼栋名称
     *
     * @param parkName
     * @return
     */
    List<String> getParkData(String parkName);

    /**
     * 根据文件名称查询园区id
     *
     * @param parkFileName
     * @return
     */
    Long findParkIdByParkFileName(String parkFileName);

    /**
     * 修改底图url
     *
     * @param baseMapUrl
     * @return
     */
    R baseMapUpdate(String baseMapUrl);

    /**
     * 获取底图url
     *
     * @return
     */
    String baseMapGetBaseMapUrl();
}
