/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.controller;

import ff.service.AdminService;
import ff.service.PrivilegeService;
import ff.service.RoleService;
import ff.service.SysActionService;
import ff.service.SysControllerService;
import ff.service.UserService;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    private PrivilegeService privilegeService;
    private AdminService adminService;

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

    public void setPrivilegeService(PrivilegeService privilegeService) {
        this.privilegeService = privilegeService;
    }

    public void setAdminService(AdminService adminService) {
        this.adminService = adminService;
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

    /**
     *作者：jerry
     *描述：角色列表页面
     */
    public ModelAndView rolePrivilege(HttpServletRequest request, HttpServletResponse response) {
        logger.info("rolePrivilege page");
        ModelAndView mav = new ModelAndView();
        return mav;
    }

    /**
     *作者：jerry
     *描述：新增角色
     */
    public void createRole(HttpServletRequest request, HttpServletResponse response) {
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
     *描述：得到所有角色，可以选择的所有权限。类似系统系统树一样，一层层的取数据。
     */
    public void getRolePrivilegeById(HttpServletRequest request, HttpServletResponse response) {
        String jsonStr = privilegeService.getSysPrivilegeChildrenById(Long.parseLong(request.getParameter("node")), 0);
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
     *描述：得到角色权限的详细权限。
     */
    public void getRolePrivilegeDetailsById(HttpServletRequest request, HttpServletResponse response) {
        Long roleId = Long.parseLong(request.getParameter("role_id"));
        Long privilegeId = Long.parseLong(request.getParameter("privilege_id"));
        Long parentPrivilegeId = Long.parseLong(request.getParameter("parent_privilege_id"));

        String jsonStr = adminService.getRolePrivilegeDetailsById(roleId, privilegeId, parentPrivilegeId);
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
