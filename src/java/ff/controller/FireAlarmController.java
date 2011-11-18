/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.controller;

import ff.model.FireAlarm;
import ff.service.FireAlarmService;
import java.io.IOException;
import java.io.PrintWriter;
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

    public ModelAndView fireAlarmList(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Fuc............................................k");
        ModelAndView mav = new ModelAndView();
        return mav;
    }

    public void getAllFireAlarm(HttpServletRequest request, HttpServletResponse response) {
        String jsonStr = fireAlarmService.getFireAlarmList();
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
}
