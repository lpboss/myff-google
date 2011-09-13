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
import java.io.IOException;
import java.io.PrintWriter;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        String jsonStr = sysActionService.getAllSysActions();
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
