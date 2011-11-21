/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.controller;

import ff.model.AlarmIgnoreAreas;
import ff.service.AlarmIgnoreAreasService;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 *
 * @author Administrator
 */
public class AlarmIgnoreAreasController extends MultiActionController{
    
    private AlarmIgnoreAreasService alarmIgnoreAreasService;
    

    //返回报警忽视地区页面
    public ModelAndView alarmIgnoreAreasList(HttpServletRequest request, HttpServletResponse response) {     
        ModelAndView mav = new ModelAndView();
        return mav;
    }
    
    //返回 添加页面
    public ModelAndView newAlarmIgnoreAreas(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        return mav;
    }

    //返回 修改页面
    public ModelAndView editAlarmIgnoreAreas(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        return mav;
    }
    
    //得到报警忽视地区信息
    public void getAllAlarmIgnoreAreases(HttpServletRequest request, HttpServletResponse response) {
        String jsonStr = alarmIgnoreAreasService.getAlarmIgnoreAreasList();
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
        String id = request.getParameter("id");
        AlarmIgnoreAreas alarmIgnoreAreas = new AlarmIgnoreAreas();            
        alarmIgnoreAreas.setPtzAngelX(Integer.valueOf(request.getParameter("ptz_angel_x"))); //火警时云台的水平角度       
        alarmIgnoreAreas.setPtzAngelY(Integer.valueOf(request.getParameter("ptz_angel_y"))); //火警时云台的Y角度
        alarmIgnoreAreas.setCcdArea(Integer.valueOf(request.getParameter("ccd_area"))); //热成像起火面积值
        alarmIgnoreAreas.setHeatMax(Integer.valueOf(request.getParameter("heat_max"))); //最大热值
        alarmIgnoreAreas.setBeginDate(null);//火警时间范围(开始)
        alarmIgnoreAreas.setEndDate(null);//火警时间范围(结束)
        alarmIgnoreAreas.setVersion(Integer.valueOf(request.getParameter("version")));//版本
        alarmIgnoreAreas.setIsLocked(Long.getLong("isLocked"));//状态isLocked
        alarmIgnoreAreasService.saveOrUpdate(alarmIgnoreAreas);
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
    
    
}
