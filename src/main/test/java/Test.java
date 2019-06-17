import com.isoft.igis.BootdoApplication;
import com.isoft.igis.common.config.IGISParkConfig;
import com.isoft.igis.common.utils.FileUtil;
import com.isoft.igis.edit.service.PointService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BootdoApplication.class)
public class Test {
    @Autowired
    private PointService pointService;
    @Autowired
    IGISParkConfig igisConfig;
    public static String NEW_LINE = System.getProperty("line.separator");
    @org.junit.Test
    public void test2() throws IOException {
        String ss="/D:/Develop/tomcat/apache-tomcat-igis-server/apache-tomcat-8.5.35/webapps/IGIS_Park/WEB-INF/classes/";
        String substring = ss.substring(1, ss.length() - 17);
        System.out.println(substring+"**************************************************************************");
     /*   String ip = "10.61.181.14";
        String port = "8080";
        if (new File("D:\\Develop\\DB\\config.js").exists()) {
            FileWriter fileWriter = new FileWriter("D:\\Develop\\DB\\config.js", false);
            fileWriter.write("var baseMapUrl = 'http://114.115.183.47:8000/com.cloud.isoft.wxs.service/WMSServices/loadStdout?serviceName=gisserver_wms_shape_osm_night';"+NEW_LINE);
            fileWriter.write("var navigationHeard = 'http://" + ip + ":" + port + "/com.cloud.isoft.graphhopperreadershp.service'; //导航header"+NEW_LINE);
            fileWriter.write("var serverHeader = 'http://" + ip + ":" + port + "'; //服务header"+NEW_LINE);
            fileWriter.write(" localStorage.setItem('serverAddress', 'http://" + ip + ":" + port + "');"+NEW_LINE);
            fileWriter.write(" localStorage.setItem('serverAddress', 'http://" + ip + ":" + port + "/com.cloud.isoft.wxs.service');"+NEW_LINE);
            fileWriter.flush();
            fileWriter.close();
        }*/
    }

    @org.junit.Test
    public void test() throws IOException {
        List<Long> pointIdList = new ArrayList<>();
        pointIdList.add(1001L);
        pointIdList.add(1002L);
        Map<String, Object> map = new HashMap<>();
        map.put("pointIdList", pointIdList);
        map.put("parkName", "西安交大");
        //pointService.findAll();
   /*     List<PointInfo> pointInfoList = pointService.findAll();
        System.out.println("**********************************************************************");
        for (PointInfo pointInfo : pointInfoList) {
            System.out.println("经度 : "+pointInfo.getLon());
            System.out.println("纬度 : "+pointInfo.getLat());
        }
       PointInfo pointInfo =new PointInfo();
       pointInfo.setBuildName("沣东4");
       pointInfo.setParkName("菜心5");
       pointInfo.setFloorName("YICENG");
       pointInfo.setPointTYpe(1);
       pointInfo.setLon(108.786941);
       pointInfo.setLat(34.261421);
       pointInfo.setIconUrl("url.img");
       pointInfo.setLable("报警器");
        System.out.println("**********************************************************************");
        System.out.println(pointService.add(pointInfo));*/
        /*List<PointInfo> all = pointService.findAll();
        for (PointInfo pointInfo : all) {
            System.out.println(pointInfo);
        }*/
    }

    @org.junit.Test
    public void inmort() throws IOException {
        String parkDir = igisConfig.getParkPath() + "xianjiaoda" + "/" + "outdoor/wms/";
        File originalMapFile = new File(parkDir + "original.map");
        if (originalMapFile.exists()) {//查询是否有原始.map文件
            File file = new File(parkDir);
            File[] files = file.listFiles();
            String serviceMapName = null;
            for (File f : files) {
                String fileName = f.getName();
                if (fileName.endsWith(".map") && !fileName.equals("original.map")) {
                    serviceMapName = f.getAbsolutePath();//获取生成服务的.map文件名 然后删除
                    f.delete();
                    File serviceMapFIle = new File(serviceMapName);
                    FileUtil.fileChannelCopy(originalMapFile, serviceMapFIle);
                }
            }
        }
    }
}
