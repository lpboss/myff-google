/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.controller;

import ff.model.FireAlarm;
import ff.service.FireAlarmService;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import java.sql.Timestamp;

/**
 *
 * @author Administrator
 */
public class FireAlarmController extends MultiActionController {

    private FireAlarmService fireAlarmService;

    public void setFireAlarmService(FireAlarmService fireAlarmService) {
        this.fireAlarmService = fireAlarmService;
    }

    public FireAlarmService getFireAlarmService() {
        return fireAlarmService;
    }
//显示火警信息页面

    public ModelAndView fireAlarmList(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Fk");
        ModelAndView mav = new ModelAndView();
        return mav;
    }
//添加页面

    public ModelAndView newFireAlarm(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Fuc............................................k");
        ModelAndView mav = new ModelAndView();
        return mav;
    }
//编辑页面

    public ModelAndView editFireAlarm(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Fuc............................................k");
        ModelAndView mav = new ModelAndView();
        return mav;
    }
//  得到火警信息    包含条件筛选

    public void getAllFireAlarm(HttpServletRequest request, HttpServletResponse response) {
        Integer ptzId;
        java.sql.Timestamp beginTime;
        java.sql.Timestamp endTime;
        logger.info("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        if (!(request.getParameter("PTZId") == null || "".equals(request.getParameter("PTZId")))) {
            ptzId = Integer.valueOf(request.getParameter("PTZId"));
        } else {
            ptzId = null;
        }
        if (!(request.getParameter("BeginTime") == null || "".equals(request.getParameter("BeginTime")))) {
            beginTime = java.sql.Timestamp.valueOf(request.getParameter("BeginTime"));
        } else {
            beginTime = null;
        }
        if (!(request.getParameter("EndTime") == null || "".equals(request.getParameter("EndTime")))) {
            endTime = java.sql.Timestamp.valueOf(request.getParameter("EndTime"));
        } else {
            endTime = null;
        }


        String jsonStr = fireAlarmService.getFireAlarmList(ptzId, beginTime, endTime);

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
// 创建

    public void create(HttpServletRequest request, HttpServletResponse response) {

        String description = request.getParameter("description");
        Integer heatMax = Integer.valueOf(request.getParameter("heatMax"));
        Float ptzAngleX = Float.valueOf(request.getParameter("ptzHAngle"));
        Float ptzAngleY = Float.valueOf(request.getParameter("ptzVAngle"));
        Integer heatAvg = Integer.valueOf(request.getParameter("heatAvg"));
        Integer heatMin = Integer.valueOf(request.getParameter("heatMin"));
        Integer userId = Integer.valueOf(request.getParameter("userId"));
        FireAlarm fireAlarm = new FireAlarm();
        fireAlarm.setDescription(description);
        fireAlarm.setPtzId(userId);
        fireAlarm.setHeatMax(heatMax);
        fireAlarm.setHeatMin(heatMin);
        fireAlarm.setPtzAngleX(ptzAngleX);
        fireAlarm.setPtzAngleY(ptzAngleY);
        fireAlarm.setHeatAvg(heatAvg);
        fireAlarm.setDealDate(java.sql.Timestamp.valueOf(request.getParameter("dealDate")));
        fireAlarm.setActionDate(java.sql.Timestamp.valueOf(request.getParameter("actionDate")));
        fireAlarm.setUserId(userId);
        fireAlarm.setVersion(0);
        fireAlarm.setIs_alarming(Short.valueOf("1"));

        logger.info(description);
        String jsonStr = fireAlarmService.create(fireAlarm);
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
//删除

    public void deleteFireAlarm(HttpServletRequest request, HttpServletResponse response) {

        String ids = request.getParameter("id");
        String id = ids.substring(0, ids.length() - 1);


        String jsonStr = fireAlarmService.deleteFireAlarm(id);
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

    public void getFireAlarmById(HttpServletRequest request, HttpServletResponse response) {
        Long id = Long.valueOf(request.getParameter("id"));
        String jsonStr = fireAlarmService.getFireAlarmJSONById(id);
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

    public void fireAlarmLock(HttpServletRequest request, HttpServletResponse response) {
        Long id = Long.valueOf(request.getParameter("id"));
        FireAlarm fireAlarm = fireAlarmService.getFireAlarmById(id);
        if (fireAlarm.getIsLocked() == Long.valueOf("1")) {
            fireAlarm.setIsLocked(Long.valueOf("0"));
        } else {
            fireAlarm.setIsLocked(Long.valueOf("1"));
        }

        PrintWriter pw;
        try {
            String jsonStr = fireAlarmService.fireAlarmLock(fireAlarm);
          

            response.setContentType("text/json; charset=utf-8");
            response.setHeader("Cache-Control", "no-cache");
            pw = response.getWriter();
            pw.write(jsonStr);
            pw.close();
        } catch (IOException e) {
            logger.info(e);
        }
    }

    public void update(HttpServletRequest request, HttpServletResponse response) {
        Long id = Long.valueOf(request.getParameter("id"));



        String description = request.getParameter("description");
        Integer heatMax = Integer.valueOf(request.getParameter("heatMax"));
        Float ptzAngleX = Float.valueOf(request.getParameter("ptzAngleX"));
        Float ptzAngleY = Float.valueOf(request.getParameter("ptzAngleY"));

        Integer heatAvg = Integer.valueOf(request.getParameter("heatAvg"));
        Integer heatMin = Integer.valueOf(request.getParameter("heatMin"));
        Integer userId = Integer.valueOf(request.getParameter("userId"));
        FireAlarm fireAlarm = fireAlarmService.getFireAlarmById(id);

        fireAlarm.setDescription(description);
        fireAlarm.setPtzId(userId);
        fireAlarm.setHeatMax(heatMax);
        fireAlarm.setHeatMin(heatMin);
        fireAlarm.setPtzAngleX(ptzAngleX);
        fireAlarm.setPtzAngleY(ptzAngleY);
        fireAlarm.setHeatAvg(heatAvg);
        fireAlarm.setDealDate(java.sql.Timestamp.valueOf(request.getParameter("dealDate")));
        fireAlarm.setActionDate(java.sql.Timestamp.valueOf(request.getParameter("actionDate")));
        fireAlarm.setUserId(userId);
        fireAlarm.setVersion(fireAlarm.getVersion() + 1);




        PrintWriter pw;
        try {
            logger.info("user update..............................................Begin..........");
            String jsonStr = fireAlarmService.update(fireAlarm);
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
