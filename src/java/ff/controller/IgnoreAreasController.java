/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.controller;

import ff.model.IgnoreAreas;
import ff.service.IgnoreAreasService;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 *
 * @author Administrator
 */
public class IgnoreAreasController extends MultiActionController{
    
    private IgnoreAreasService ignoreAreasService;

    public IgnoreAreasService getIgnoreAreasService() {
        return ignoreAreasService;
    }

    public void setIgnoreAreasService(IgnoreAreasService ignoreAreasService) {
        this.ignoreAreasService = ignoreAreasService;
    }
    

    //返回报警忽视地区页面
    public ModelAndView ignoreAreasList(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        return mav;
    }
    
    //返回 添加页面
    public ModelAndView newIgnoreAreas(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        return mav;
    }

    //返回 修改页面
    public ModelAndView editIgnoreAreas(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        return mav;
    }
    
    //得到报警忽视地区信息
    public void getAllIgnoreAreases(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        logger.info("88888888888");
        logger.info(id);
        String jsonStr = ignoreAreasService.getIgnoreAreasJSONById(Long.parseLong(request.getParameter("id")));
     //   String jsonStr = ignoreAreasService.getIgnoreAreasList();
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
    
    //添加报警忽视地区信息
    public void create(HttpServletRequest request, HttpServletResponse response) {
     //   String id = request.getParameter("id");
        IgnoreAreas ignoreAreas = new IgnoreAreas();  
        ignoreAreas.setPtzId(Integer.valueOf(request.getParameter("ptz_id"))); //云台ID
        ignoreAreas.setPtzAngelX(Integer.valueOf(request.getParameter("ptz_angel_x"))); //火警时云台的水平角度       
        ignoreAreas.setPtzAngelY(Integer.valueOf(request.getParameter("ptz_angel_y"))); //火警时云台的Y角度
        ignoreAreas.setCcdArea(Integer.valueOf(request.getParameter("ccd_area"))); //热成像起火面积值
        ignoreAreas.setHeatMax(Integer.valueOf(request.getParameter("heat_max"))); //最大热值
        ignoreAreas.setBeginDate(Timestamp.valueOf(request.getParameter("begin_date"))); //火警时间范围(开始)
        ignoreAreas.setEndDate(Timestamp.valueOf(request.getParameter("end_date"))); //火警时间范围(结束)
        ignoreAreas.setVersion(Integer.valueOf(request.getParameter("version"))); //版本
        ignoreAreas.setIsLocked(Long.getLong("is_locked")); //状态isLocked
        ignoreAreasService.saveOrUpdate(ignoreAreas);
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
    
    //编辑AllAlarmIgnoreAreases
    public void getIgnoreAreasesById(HttpServletRequest request, HttpServletResponse response) {
        Long id = Long.valueOf(request.getParameter("id"));
        String jsonStr = ignoreAreasService.getIgnoreAreasJSONById(id);
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
    
     //更新报警忽视地区
    public void update(HttpServletRequest request, HttpServletResponse response) {
        Long id = Long.valueOf(request.getParameter("id"));
        Integer ptzAngelX = Integer.valueOf(request.getParameter("ptz_angel_x")); //火警时云台的水平角度    
        Integer ptzAngelY = Integer.valueOf(request.getParameter("ptz_angel_y")); //火警时云台的Y角度
        Integer ccdArea = Integer.valueOf(request.getParameter("ccd_area")); //热成像起火面积值
        Integer heatMax = Integer.valueOf(request.getParameter("heat_max"));  //最大热值
        Timestamp beginDate = Timestamp.valueOf(request.getParameter("begin_date"));  //火警时间范围(开始)
        Timestamp endDate = Timestamp.valueOf(request.getParameter("end_date")); //火警时间范围(结束)
        Integer version = Integer.valueOf(request.getParameter("version")); //版本
        Long isLocked = Long.valueOf(request.getParameter("is_locked"));//状态
     //   Long ptzId = Long.valueOf(request.getParameter("roleId"));
        IgnoreAreas ignoreAreas = ignoreAreasService.getIgnoreAreasById(id);
        ignoreAreas.setId(id);
        ignoreAreas.setPtzAngelX(ptzAngelX);
        ignoreAreas.setPtzAngelY(ptzAngelY);
        ignoreAreas.setCcdArea(ccdArea);
        ignoreAreas.setHeatMax(heatMax);
        ignoreAreas.setBeginDate(beginDate);
        ignoreAreas.setEndDate(endDate);
        ignoreAreas.setVersion(version);
        ignoreAreas.setIsLocked(isLocked);
       // ignoreAreas.setIsLocked(Long.valueOf(isLocked));
      //  logger.info(ptzId);
        PrintWriter pw;
        try {
            logger.info("user update..............................................Begin..........");
            String jsonStr = ignoreAreasService.update(ignoreAreas);
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
    
     //删除报警忽视地区
    public void deleteIgnoreAreas(HttpServletRequest request, HttpServletResponse response) {
        Long id = Long.valueOf(request.getParameter("key"));
        logger.info ("sss");
        logger.info (id);
        logger.info ("www");
      String jsonStr = ignoreAreasService.deleteIgnoreAreas(id);
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
