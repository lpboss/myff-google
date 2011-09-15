/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.controller;

import ff.model.RolesPrivilegeDetail;
import ff.model.User;
import ff.service.RoleService;
import ff.service.SysActionService;
import ff.service.SysControllerService;
import ff.service.UserService;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.String;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.json.JSONObject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 *
 * @author jerry
 */
public class AdminController extends MultiActionController {

    private UserService userService;
    private RoleService roleService;
    private SysControllerService sysControllerService;
    private SysActionService sysActionService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    public void setSysActionService(SysActionService sysActionService) {
        this.sysActionService = sysActionService;
    }

    public void setSysControllerService(SysControllerService sysControllerService) {
        this.sysControllerService = sysControllerService;
    }

    /**
     *作者：joey
     *描述：得到角色列表
     */
    public void getAllRoles(HttpServletRequest request, HttpServletResponse response) {
        String jsonStr = roleService.getAllRoles();
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

    /**
     *作者：jerry
     *描述：得到所有控制器
     */
    public void getAllSysControllers(HttpServletRequest request, HttpServletResponse response) {
        String jsonStr = sysControllerService.getAllSysControllers();
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

    /**
     *作者：jerry
     *描述：得到所有控制器
     */
    public void getAllSysActions(HttpServletRequest request, HttpServletResponse response) {
        String sysControllerId = request.getParameter("sys_controller_id");
        //免于作复杂判断，所有为null的值，设置为0
        if (sysControllerId == null) {
            sysControllerId = "0";
        }
        String jsonStr = sysActionService.getAllSysActions(Long.parseLong(sysControllerId));
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
                Method[] methods = cls.getDeclaredMethods();//得到某类的所有方法
                for (Method method : methods) {
                    logger.info(method.getName() + "  " + method.getReturnType() + " " + method.getModifiers());
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        logger.info("My Class +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        ModelAndView mav = new ModelAndView();
        return mav;
    }
    //得到某个包下的所有类名。
}
