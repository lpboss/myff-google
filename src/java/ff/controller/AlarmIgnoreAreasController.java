/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 *
 * @author Administrator
 */
public class AlarmIgnoreAreasController extends MultiActionController{

    public ModelAndView alarmIgnoreAreasList(HttpServletRequest request, HttpServletResponse response) {
     
        ModelAndView mav = new ModelAndView();
        return mav;
    }
}
