/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.controller;

/**
 *
 * @author jerry
 */
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class PTZController extends MultiActionController {
    /**
     *作者：jerry
     *描述：PTZ首页，分5大块，可见光，红外，GIS，云台列表，云台控制
     */
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
        logger.info("PTZ index page");
        ModelAndView mav = new ModelAndView();
        return mav;
    }
}
