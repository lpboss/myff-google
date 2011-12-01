/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.controller;

import ff.dao.RolePtzDao;
import ff.model.PTZ;
import ff.model.Role;
import ff.model.RolePtz;
import ff.service.AdminService;
import ff.service.PTZService;
import ff.service.PrivilegeService;
import ff.service.RolePtzService;
import ff.service.RoleService;
import ff.service.SysActionService;
import ff.service.SysControllerService;
import ff.service.UserService;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 *
 * @author jerry
 * 描述：统一处理管理介面多项功能
 */
public class AdminController extends MultiActionController {

    private UserService userService;
    private RoleService roleService;
    private SysControllerService sysControllerService;
    private SysActionService sysActionService;
    private PrivilegeService privilegeService;
    private AdminService adminService;
    private PTZService ptzService;
    private RolePtzService rolePtzService;
    private RolePtzDao rolePtzDao;

    public PTZService getPtzService() {
        return ptzService;
    }

    public void setPtzService(PTZService ptzService) {
        this.ptzService = ptzService;
    }

    public RolePtzDao getRolePtzDao() {
        return rolePtzDao;
    }

    public void setRolePtzDao(RolePtzDao rolePtzDao) {
        this.rolePtzDao = rolePtzDao;
    }  
    
    public RolePtzService getRolePtzService() {
        return rolePtzService;
    }

    public void setRolePtzService(RolePtzService rolePtzService) {
        this.rolePtzService = rolePtzService;
    }

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

        String jsonStr = adminService.doRolePrivilegeDetailsById(roleId, privilegeId, parentPrivilegeId);
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
     *描述：锁定角色
     */
    public void roleLock(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        String jsonStr = roleService.roleLock(Long.parseLong(id));
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
     *描述：新增加角色页面
     */
    public ModelAndView newRole(HttpServletRequest request, HttpServletResponse response) {
        logger.info("newRole page");
        ModelAndView mav = new ModelAndView();
        return mav;
    }

    /**
     *作者：jerry
     *描述：编辑角色页面
     */
    public ModelAndView editRole(HttpServletRequest request, HttpServletResponse response) {
        logger.info("editRole page");
        ModelAndView mav = new ModelAndView();
        return mav;
    }

    /**
     *作者：jerry
     *描述：锁定角色
     */
    public void getRoleById(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        String jsonStr = roleService.getRoleJSONById(Long.parseLong(id));
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
     *描述：更新角色
     */
    public void updateRole(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        Role role = roleService.getRoleById(Long.parseLong(id));
        role.setName(request.getParameter("name"));
        role.setDescription(request.getParameter("description"));
        Long ptzid = Long.valueOf(request.getParameter("ptz"));
        PTZ ptz = ptzService.getPTZById(ptzid);
        role.setPtz(ptz);
//
//        Long ptzid = Long.valueOf(request.getParameter("ptz"));
//        logger.info("43v2rv");
//        logger.info(id);
//        logger.info(request.getParameter("ptz"));
//        logger.info(ptzid);
//        
//        logger.info("43v2rv");
//        String rolePtz = rolePtzService.getRolePtzById(ptzid);
//      //  role.setRolePtzDetails(rolePtz);
//      //  role.setPtz(rolePtz);



        roleService.saveOrUpdate(role);
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
     *作者：jerry
     *描述：新增角色
     */
    public void createRole(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        Role role = new Role();
        role.setName(request.getParameter("name"));
        role.setDescription(request.getParameter("description"));
       
        roleService.saveOrUpdate(role);
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
     *作者：jerry
     *描述：锁定或解锁角色的权限细节
     */
    public void rolePrivilegeDetailLock(HttpServletRequest request, HttpServletResponse response) {
        String jsonStr = adminService.rolePrivilegeDetailLock(Long.parseLong(request.getParameter("id")));

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
