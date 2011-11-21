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

    public ModelAndView fireAlarmList(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Fk");
        ModelAndView mav = new ModelAndView();
        return mav;
    }

    public ModelAndView newFireAlarm(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Fuc............................................k");
        ModelAndView mav = new ModelAndView();
        return mav;
    }

    public ModelAndView editFireAlarm(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Fuc............................................k");
        ModelAndView mav = new ModelAndView();
        return mav;
    }

    public void getAllFireAlarm(HttpServletRequest request, HttpServletResponse response) {
        Integer ptzId;

        if (!(request.getParameter("PTZId") == null || "".equals(request.getParameter("PTZId")))) {
            logger.info(request.getParameter("PTZId"));
            ptzId = Integer.valueOf(request.getParameter("PTZId"));
        } else {
            ptzId = null;
        }



        String jsonStr = fireAlarmService.getFireAlarmList(ptzId);

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
        fireAlarm.setVersion(6);

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
        System.out.println("aaaaaaaaaaaaaaaaa");
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

    public void update(HttpServletRequest request, HttpServletResponse response) {
        Long id = Long.valueOf(request.getParameter("id"));



        String description = request.getParameter("description");
        Integer heatMax = Integer.valueOf(request.getParameter("heatMax"));
        Float ptzAngleX = Float.valueOf(request.getParameter("ptzHAngle"));
        Float ptzAngleY = Float.valueOf(request.getParameter("ptzVAngle"));

        Integer heatAvg = Integer.valueOf(request.getParameter("heatAvg"));
        Integer heatMin = Integer.valueOf(request.getParameter("heatMin"));
        Integer userId = Integer.valueOf(request.getParameter("userId"));
        FireAlarm fireAlarm = fireAlarmService.getFireAlarmById(id);
        logger.info("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

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
        fireAlarm.setVersion(6);




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
