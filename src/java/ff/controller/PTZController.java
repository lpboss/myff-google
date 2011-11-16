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

        ptzUtil.PTZAction(request.getParameter("action_type"));

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
        logger.info("PTZ PTZList page");
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

    //添加
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
        ptz.setNorthMigration(request.getIntHeader("north_migration"));//摄像机0角度与正北的偏移
        ptz.setGisMapUrl(request.getParameter("gis_map_url"));//地图文件存放位置
        ptz.setVisualAngleX(request.getIntHeader("visual_angle_x"));//红外视角X
        ptz.setVisualAngleY(request.getIntHeader("visual_angle_y"));//红外视角Y
        ptz.setInfraredPixelX(request.getIntHeader("infrared_pixel_x"));//红外摄像机X方向像素
        ptz.setInfraredPixelY(request.getIntHeader("infrared_pixel_y"));//红外摄像机Y方向像素     
        ptz.setVersion(Integer.getInteger("version"));//版本
        ptz.setIsLocked(Long.getLong("isLocked"));//状态
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

        String name = request.getParameter("name");
        String password = request.getParameter("password");
        Long roleId = Long.valueOf(request.getParameter("roleId"));
        PTZ ptz = ptzService.getPTZById(id);
        ptz.setId(id);
        ptz.setName(name);

        logger.info(roleId);
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
    
    
    
}
