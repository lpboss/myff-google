/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.controller;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 *
 * @author jerry
 */
public class TestController extends MultiActionController {
    //把控制器中的所有方法插入到privilege_details表中

    public ModelAndView doAllControllerMethods(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> cMap = new HashMap<String, String>();
        cMap.put("admin", "ff.controller.AdminController");
        cMap.put("index", "ff.controller.IndexController");
        cMap.put("privilege", "ff.controller.PrivilegeController");
        cMap.put("user", "ff.controller.UserController");
        //循环控制器中的所有方法
        Iterator keyIterator = cMap.keySet().iterator();
        while (keyIterator.hasNext()) {
            try {
                String key = (String) keyIterator.next();
                logger.info(key);
                Class cls = Class.forName(cMap.get(key));
                logger.info(cls.getName());
                Method[] methods = cls.getMethods();//得到某类的所有Public方法
                for (Method method : methods) {
                    logger.info(method.getName() + "  " + method.getReturnType().getName() + " " + method.getModifiers());
                }
            } catch (ClassNotFoundException ex) {
                logger.info(ex);
            }

        }
        logger.info("My Class +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        ModelAndView mav = new ModelAndView();
        return mav;
    }
}
