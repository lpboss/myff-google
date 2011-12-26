/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.controller;

import ff.model.IgnoreAreas;
import ff.model.PTZ;
import ff.service.IgnoreAreasService;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author Administrator
 */
public class IgnoreAreasController extends MultiActionController {

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
        System.out.println("12s12s");
        System.out.println(id);
        System.out.println("43vb54");
        String jsonStr = ignoreAreasService.getIgnoreAreasJSONById(Integer.parseInt(request.getParameter("id")));
        //   String jsonStr = ignoreAreasService.getIgetIgnoreAreasJSONByIdgnoreAreasList();
        PrintWriter pw;
        try {
            response.setContentType("text/json; charset=utf-8");
            response.setHeader("Cache-Control", "no-cache");
            pw = response.getWriter();
            pw.write(jsonStr);
            pw.close();
        } catch (IOException e) {
        }
    }

    //添加报警忽视地区信息
    public void create(HttpServletRequest request, HttpServletResponse response) {

        IgnoreAreas ignoreAreas = new IgnoreAreas();
        PTZ ptz = new PTZ();
        ptz.setId(Long.valueOf(request.getParameter("ptz_id"))); //云台ID
        ignoreAreas.setPtz(ptz);
        if (!request.getParameter("ptz_angel_x").equals("")) {
            ignoreAreas.setPtzAngelX(Integer.valueOf(request.getParameter("ptz_angel_x"))); //火警时云台的水平角度
        }
        if (!request.getParameter("ptz_angel_y").equals("")) {
            ignoreAreas.setPtzAngelY(Integer.valueOf(request.getParameter("ptz_angel_y"))); //火警时云台的Y角度
        }
        if (!request.getParameter("ccd_area").equals("")) {
            ignoreAreas.setCcdArea(Integer.valueOf(request.getParameter("ccd_area"))); //热成像起火面积值
        }
        if (!request.getParameter("heat_max").equals("")) {
            ignoreAreas.setHeatMax(Integer.valueOf(request.getParameter("heat_max"))); //最大热值
        }

        if (!(request.getParameter("end_date") == null) && !request.getParameter("begin_date").equals("")) {
            ignoreAreas.setBeginDate(Timestamp.valueOf(request.getParameter("begin_date"))); //火警时间范围(开始)
        } else {
            ignoreAreas.setBeginDate(new Timestamp(Calendar.getInstance().getTime().getTime()));
        }
        if (!(request.getParameter("end_date") == null) && !request.getParameter("end_date").equals("")) {
            ignoreAreas.setEndDate(Timestamp.valueOf(request.getParameter("end_date"))); //火警时间范围(结束)
        } else {

            ignoreAreas.setEndDate(new Timestamp(Calendar.getInstance().getTime().getTime()));
        }
        if (!request.getParameter("is_locked").equals("")) {
            ignoreAreas.setIsLocked(Long.getLong("1")); //状态isLocked
        }

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
        }
    }

    //编辑AllAlarmIgnoreAreases
    public void getIgnoreAreasesById(HttpServletRequest request, HttpServletResponse response) {
        Integer id = Integer.valueOf(request.getParameter("id"));
        String jsonStr = ignoreAreasService.getEditIgnoreAreasJSONById(id);
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
        Integer id = Integer.valueOf(request.getParameter("id"));
        IgnoreAreas ignoreAreas = ignoreAreasService.getIgnoreAreasById(id);
        if (!request.getParameter("ptzAngelX").equals("")) {
            ignoreAreas.setPtzAngelX(Integer.valueOf(request.getParameter("ptzAngelX"))); //火警时云台的水平角度
        }
        if (!request.getParameter("ptzAngelY").equals("")) {
            ignoreAreas.setPtzAngelY(Integer.valueOf(request.getParameter("ptzAngelY"))); //火警时云台的Y角度
        }
        if (!request.getParameter("ccdArea").equals("")) {
            ignoreAreas.setCcdArea(Integer.valueOf(request.getParameter("ccdArea"))); //热成像起火面积值
        }
        if (!request.getParameter("heatMax").equals("")) {
            ignoreAreas.setHeatMax(Integer.valueOf(request.getParameter("heatMax"))); //最大热值
        }
        if (!request.getParameter("beginDate").equals("")) {
            ignoreAreas.setBeginDate(Timestamp.valueOf(request.getParameter("beginDate"))); //火警时间范围(开始)
        }
        if (!request.getParameter("endDate").equals("")) {
            ignoreAreas.setEndDate(Timestamp.valueOf(request.getParameter("endDate"))); //火警时间范围(结束)
        }
       if (!request.getParameter("isLocked").equals("")) {
            ignoreAreas.setIsLocked(Long.valueOf(request.getParameter("isLocked")));
        }else{
            ignoreAreas.setIsLocked(Long.valueOf("0"));
        }//状态isLocked    
        PrintWriter pw;
        try {

            String jsonStr = ignoreAreasService.update(ignoreAreas);
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
        String id = request.getParameter("key");
        String jsonStr = ignoreAreasService.deleteIgnoreAreas(id);
        PrintWriter pw;
        try {
            response.setContentType("text/json; charset=utf-8");
            response.setHeader("Cache-Control", "no-cache");
            pw = response.getWriter();
            pw.write(jsonStr);
            pw.close();
        } catch (IOException e) {
        }
    }

    //处理是否锁定状态
    public void ignoreareasLock(HttpServletRequest request, HttpServletResponse response) {
        Integer id = Integer.valueOf(request.getParameter("id"));
        IgnoreAreas ignoreAreas = ignoreAreasService.getIgnoreAreasById(id);

        if (ignoreAreas.getIsLocked() == 1) {
            ignoreAreas.setIsLocked(Long.valueOf("0"));
        } else {
            ignoreAreas.setIsLocked(Long.valueOf("1"));
        }
        PrintWriter pw;
        try {
            String jsonStr = ignoreAreasService.ignoreAreasLock(ignoreAreas);
            response.setContentType("text/json; charset=utf-8");
            response.setHeader("Cache-Control", "no-cache");
            pw = response.getWriter();
            pw.write(jsonStr);
            pw.close();
        } catch (IOException e) {
        }
    }
}
