/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.controller;

/**
 *
 * @author jerry
 */
import ff.model.PTZ;
import ff.util.PTZUtil;
import ff.service.PTZService;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class PTZController extends MultiActionController {

    private PTZUtil ptzUtil;
    private PTZService ptzService;

    public void setPtzService(PTZService ptzService) {
        this.ptzService = ptzService;
    }

    public void setPtzUtil(PTZUtil ptzUtil) {
        this.ptzUtil = ptzUtil;
    }

    /**
     *作者：jerry
     *描述：PTZ首页，分5大块，可见光，红外，GIS，云台列表，云台控制
     */
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
        logger.info("PTZ index page");
        ModelAndView mav = new ModelAndView();
        return mav;
    }

    /**
     *作者：jerry
     *描述：PTZ命令，上，下，左，右，停    
     */
    public void ptzAction(HttpServletRequest request, HttpServletResponse response) {
        logger.info("PTZ index page");
        long ptzId = Long.parseLong(request.getParameter("ptz_id"));
        if (request.getParameter("action_type").equalsIgnoreCase("stop_fire_alarm")) {
            PTZ ptz = ptzService.getPTZById(ptzId);
            ptz.setIsAlarm(0);
            ptzService.saveOrUpdate(ptz);
        } else {
            ptzUtil.PTZAction(ptzId, request.getParameter("action_type"));
        }
        
        String info = "success";
        String jsonStr = "{success:true,info:'" + info + "'}";

        PrintWriter pw;
        try {
            response.setContentType("text/json; charset=utf-8");
            response.setHeader("Cache-Control", "no-cache");
            pw = response.getWriter();
            pw.write(jsonStr);
            pw.close();
        } catch (IOException e) {
            logger.info(e);
        }
    }

    /**
     *作者：Haoqingmeng
     *描述：返回PTZ页面
     */
    public ModelAndView PTZList(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        return mav;
    }

    //返回 添加页面
    public ModelAndView newPTZ(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        return mav;
    }

    //返回 修改页面
    public ModelAndView editPTZ(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        return mav;
    }

    /**
     *作者：Haoqingmeng
     *描述：得到PTZ列表
     */
    public void getAllPTZs(HttpServletRequest request, HttpServletResponse response) {
        String jsonStr = ptzService.getPTZList();
        logger.info(jsonStr);
        PrintWriter pw;
        try {
            response.setContentType("text/json; charset=utf-8");
            response.setHeader("Cache-Control", "no-cache");
            pw = response.getWriter();
            pw.write(jsonStr);
            pw.close();
        } catch (IOException e) {
            logger.info(e);
        }
    }

    //添加PTZ信息
    public void create(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        PTZ ptz = new PTZ();
        ptz.setName(request.getParameter("name")); //名称
        ptz.setControllUrl(request.getParameter("controll_url"));//编码器IP
        ptz.setPelcodCommandUrl(request.getParameter("pelcod_command_url"));//通过串口,发pelcod的ip
        ptz.setVisibleCameraUrl(request.getParameter("visible_camera_url"));//可见光摄像机地址,模拟请参考controll_url
        ptz.setVisibleRTSPUrl(request.getParameter("visible_rtsp_url"));//可见光RTSP流
        ptz.setInfraredRTSPUrl(request.getParameter("infrared_rtsp_url"));//红外RTSP流
        ptz.setInfraredCameraUrl(request.getParameter("infrared_camera_url"));//红外摄像机地址
        ptz.setInfraredCircuitUrl(request.getParameter("infrared_circuit_url"));//红外电路板设备地址       
        ptz.setNorthMigration(Float.valueOf(request.getParameter("north_migration")));//摄像机0角度与正北的偏移
        ptz.setGisMapUrl(request.getParameter("gis_map_url"));//地图文件存放位置
        ptz.setVisualAngleX(Float.valueOf(request.getParameter("visual_angle_x")));//红外视角X
        ptz.setVisualAngleY(Float.valueOf(request.getParameter("visual_angle_y")));//红外视角Y
        ptz.setInfraredPixelX(Integer.valueOf(request.getParameter("infrared_pixel_x")));//红外摄像机X方向像素
        ptz.setInfraredPixelY(Integer.valueOf(request.getParameter("infrared_pixel_y")));//红外摄像机Y方向像素    
        ptz.setBrandType(request.getParameter("brand_type"));//品牌类型,不同品牌，特性不同，plcod命令拼接方式不同。
        logger.info(request.getParameter("cruise_step"));
        ptz.setCruiseStep(Integer.valueOf(request.getParameter("cruise_step")));//巡航步长
        ptz.setVersion(Integer.valueOf(request.getParameter("version")));//版本
        ptz.setIsLocked(Long.getLong("is_locked"));//状态isLocked
        ptz.setCruiseRightLimit(Integer.valueOf(request.getParameter("cruise_right_limit"))); //巡航右边界
        ptz.setCruiseLeftLimit(Integer.valueOf(request.getParameter("cruise_left_limit"))); //巡航左边界
        ptz.setCruiseUpLimit(Integer.valueOf(request.getParameter("cruise_up_limit"))); //最大上仰角度
        ptz.setCruiseDownLimit(Integer.valueOf(request.getParameter("cruise_down_limit"))); //巡航时最大俯角
        ptzService.saveOrUpdate(ptz);
        String info = "success";
        String jsonStr = "{success:true,info:'" + info + "'}";
        PrintWriter pw;
        try {
            response.setContentType("text/json; charset=utf-8");
            response.setHeader("Cache-Control", "no-cache");
            pw = response.getWriter();
            pw.write(jsonStr);
            pw.close();
        } catch (IOException e) {
            logger.info(e);
        }
    }

    //编辑PTZ
    public void getPTZById(HttpServletRequest request, HttpServletResponse response) {
        Long id = Long.valueOf(request.getParameter("id"));
        String jsonStr = ptzService.getPTZJSONById(id);
        PrintWriter pw;
        try {
            response.setContentType("text/json; charset=utf-8");
            response.setHeader("Cache-Control", "no-cache");
            pw = response.getWriter();
            pw.write(jsonStr);
            pw.close();
        } catch (IOException e) {
            logger.info(e);
        }
    }

    //更新PTZ
    public void update(HttpServletRequest request, HttpServletResponse response) {
        Long id = Long.valueOf(request.getParameter("id"));
        String name = request.getParameter("name"); //名称
        String controllUrl = request.getParameter("controllUrl");//编码器IP
        String pelcodCommandUrl = request.getParameter("pelcodCommandUrl"); //通过串口,发pelcod的ip
        String visibleCameraUrl = request.getParameter("visibleCameraUrl"); //可见光摄像机地址,模拟请参考controll_url
        String visibleRTSPUrl = request.getParameter("visibleRTSPUrl"); //可见光RTSP流
        String infraredRTSPUrl = request.getParameter("infraredRTSPUrl"); //红外RTSP流
        String infraredCameraUrl = request.getParameter("infraredCameraUrl"); //红外摄像机地址
        String infraredCircuitUrl = request.getParameter("infraredCircuitUrl"); //红外电路板设备地址         
        Float northMigration = Float.valueOf(request.getParameter("infraredCircuitUrl")); //摄像机0角度与正北的偏移
        String gisMapUrl = request.getParameter("gisMapUrl");//地图文件存放位置
        Float visualAngleX = Float.valueOf(request.getParameter("visualAngleX"));//红外视角X
        Float visualAngleY = Float.valueOf(request.getParameter("visualAngleY"));//红外视角Y
        Integer infraredPixelX = Integer.valueOf(request.getParameter("infraredPixelX"));//红外摄像机X方向像素
        Integer infraredPixelY = Integer.valueOf(request.getParameter("infraredPixelY"));//红外摄像机Y方向像素    
        String brandType = request.getParameter("brandType");//品牌类型
        Integer cruiseStep = Integer.valueOf(request.getParameter("cruiseStep")); //巡航步长
        Integer version = Integer.valueOf(request.getParameter("version")); //版本
        Integer cruiseRightLimit = Integer.valueOf(request.getParameter("cruiseRightLimit")); //巡航右边界
        Integer cruiseLeftLimit = Integer.valueOf(request.getParameter("cruiseLeftLimit")); //巡航左边界
        Integer cruiseUpLimit = Integer.valueOf(request.getParameter("cruiseUpLimit")); //最大上仰角度
        Integer cruiseDownLimit = Integer.valueOf(request.getParameter("cruiseDownLimit")); //巡航时最大俯角
        //  Long isLocked = Long.valueOf(request.getParameter("isLocked"));//状态
        //   Long ptzId = Long.valueOf(request.getParameter("roleId"));
        PTZ ptz = ptzService.getPTZById(id);
        ptz.setId(id);
        ptz.setName(name);
        ptz.setControllUrl(controllUrl);
        ptz.setPelcodCommandUrl(pelcodCommandUrl);
        ptz.setVisibleCameraUrl(visibleCameraUrl);
        ptz.setVisibleRTSPUrl(visibleRTSPUrl);
        ptz.setInfraredRTSPUrl(infraredRTSPUrl);
        ptz.setInfraredCameraUrl(infraredCameraUrl);
        ptz.setInfraredCircuitUrl(infraredCircuitUrl);
        ptz.setNorthMigration(northMigration);
        ptz.setGisMapUrl(gisMapUrl);
        ptz.setVisualAngleX(visualAngleX);
        ptz.setVisualAngleY(visualAngleY);
        ptz.setInfraredPixelX(Integer.valueOf(infraredPixelX));
        ptz.setInfraredPixelY(Integer.valueOf(infraredPixelY));
        ptz.setBrandType(brandType);
        ptz.setCruiseStep(Integer.valueOf(cruiseStep));
        ptz.setVersion(Integer.valueOf(version));
        ptz.setInfraredPixelX(Integer.valueOf(cruiseRightLimit));
        ptz.setInfraredPixelX(Integer.valueOf(cruiseLeftLimit));
        ptz.setInfraredPixelX(Integer.valueOf(cruiseUpLimit));
        ptz.setInfraredPixelX(Integer.valueOf(cruiseDownLimit));
        //    ptz.setIsLocked(Long.valueOf(isLocked));


        //  logger.info(ptzId);
        PrintWriter pw;
        try {
            logger.info("user update..............................................Begin..........");
            String jsonStr = ptzService.update(ptz);
            logger.info("user update..............................................");
            logger.info(jsonStr);

            response.setContentType("text/json; charset=utf-8");
            response.setHeader("Cache-Control", "no-cache");
            pw = response.getWriter();
            pw.write(jsonStr);
            pw.close();
        } catch (IOException e) {
            logger.info(e);
        }
    }

    //删除PTZ
    public void deletePTZ(HttpServletRequest request, HttpServletResponse response) {
        Long id = Long.valueOf(request.getParameter("key"));
        logger.info("sss");
        logger.info(id);
        logger.info("www");
        String jsonStr = ptzService.deletePTZ(id);
        PrintWriter pw;
        try {
            response.setContentType("text/json; charset=utf-8");
            response.setHeader("Cache-Control", "no-cache");
            pw = response.getWriter();
            pw.write(jsonStr);
            pw.close();
        } catch (IOException e) {
            logger.info(e);
        }
    }
}
