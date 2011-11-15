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
        logger.info("ddddddddddddddddddddddddddddddddd");
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
        ptz.setControllUrl(request.getParameter("description")); //
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
}
