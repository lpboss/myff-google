/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.controller;

/**
 *
 * @author jerry
 */
import ff.util.PTZUtil;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class PTZController extends MultiActionController {

    private PTZUtil ptzUtil;

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
}
