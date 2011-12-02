/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.controller;

import ff.dao.RolePtzDao;
import ff.model.PTZ;
import ff.model.Role;
import ff.model.RolePtz;
import ff.service.PTZService;
import ff.service.RolePtzService;
import ff.service.RoleService;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Haoqingmeng
 */
public class RolePtzController extends MultiActionController {

    private RolePtzService rolePtzService;
    private PTZService ptzService;
    private RoleService roleService;
   

    public RoleService getRoleService() {
        return roleService;
    }

    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    public PTZService getPtzService() {
        return ptzService;
    }

    public void setPtzService(PTZService ptzService) {
        this.ptzService = ptzService;
    }

    public RolePtzService getRolePtzService() {
        return rolePtzService;
    }

    public void setRolePtzService(RolePtzService rolePtzService) {
        this.rolePtzService = rolePtzService;
    }

    //返回rolePtz页面
    public ModelAndView rolePtz(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        return mav;
    }

    //返回添加页面
    public ModelAndView newRolePtz(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        return mav;
    }
    
     //返回editRolePtz页面
    public ModelAndView editRolePtz(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        return mav;
    }

    //得到角色表
    public void getAllRoles(HttpServletRequest request, HttpServletResponse response) {
        String jsonStr = rolePtzService.getAllRoles();
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
     *描述：得到PTZ列表
     */
    public void getAllPTZs(HttpServletRequest request, HttpServletResponse response) {
        String jsonStr = "";
        String id = request.getParameter("id");
        if (request.getParameter("id") == null) {
            jsonStr = rolePtzService.getPTZList();
        } else {
            jsonStr = rolePtzService.getRolePtzJSONById(Integer.parseInt(request.getParameter("id")));
        }

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

    //处理是否设置默认云台
    public void rolePtzLock(HttpServletRequest request, HttpServletResponse response) {
        Long id = Long.valueOf(request.getParameter("id"));

        Long roleid = Long.valueOf(request.getParameter("roleId"));
        logger.info("321c2r");
        logger.info(id);
        logger.info(roleid);
        logger.info("321c2r");
        rolePtzService.resetDefault(roleid);
        RolePtz rolePtz = rolePtzService.getRolePtzById(id, roleid);

        PrintWriter pw;
        try {
            String jsonStr = rolePtzService.RolePtzDefault(rolePtz);
            response.setContentType("text/json; charset=utf-8");
            response.setHeader("Cache-Control", "no-cache");
            pw = response.getWriter();
            pw.write(jsonStr);
            pw.close();
        } catch (IOException e) {
            logger.info(e);
        }
    }

    //得到某一角色的所有ptz
    public void getRolePtzs(HttpServletRequest request, HttpServletResponse response) {
        String jsonStr = "";
        String id = request.getParameter("id");
        if (request.getParameter("id") != null) {
            jsonStr = rolePtzService.getRolePtzJSONById(Integer.parseInt(request.getParameter("id")));
        } else {
            jsonStr = rolePtzService.getRolePtzList();
        }
        //   String jsonStr = ignoreAreasService.getIgetIgnoreAreasJSONByIdgnoreAreasList();
        PrintWriter pw;
        try {
            response.setContentType("text/json; charset=utf-8");
            response.setHeader("Cache-Control", "no-cache");
            pw = response.getWriter();
            pw.write(jsonStr);
            pw.close();
        } catch (IOException e) {
        }
    }

    //添加rolePtz信息
    public void create(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        RolePtz rolePtz = new RolePtz();

        Long ptzId = Long.valueOf(request.getParameter("ptz")); //云台Id
        PTZ ptz = ptzService.getPTZById(ptzId);
        rolePtz.setPtz(ptz);

        Long roleId = Long.valueOf(request.getParameter("userId")); //角色id
        Role role = roleService.getRoleById(roleId);
        rolePtz.setRole(role);

        logger.info("43fv345");
        logger.info(ptzId);
        logger.info(roleId);
        logger.info(rolePtz);
        logger.info("2t5gbk6");
        String jsonStr = rolePtzService.create(rolePtz, ptzId, roleId);
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

    //删除rolePtz
    public void deleteRolePtz(HttpServletRequest request, HttpServletResponse response) {

        String id = request.getParameter("key");
        String roleid = request.getParameter("roleid");
        String jsonStr = rolePtzService.deleteRolePtz(id, roleid);

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

    //得到rolePtz列表
    public void getAllRolePtzs(HttpServletRequest request, HttpServletResponse response) {
        logger.info("12s2");
        String jsonStr = rolePtzService.getRolePtzList();
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
    
    //更新角色
    public void updateRole(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        Role role = roleService.getRoleById(Long.parseLong(id));
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
    
     //得到要编辑的role
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
    
//    //通过id得到rolePtz列表
//    public void getAllRolePtzsById(HttpServletRequest request, HttpServletResponse response) {
//        String id = request.getParameter("id");
//        logger.info("12s2");
//        String jsonStr = rolePtzService.getRolePtzById(Integer.parseInt(request.getParameter("id")));
//        PrintWriter pw;
//        try {
//            response.setContentType("text/json; charset=utf-8");
//            response.setHeader("Cache-Control", "no-cache");
//            pw = response.getWriter();
//            pw.write(jsonStr);
//            pw.close();
//        } catch (IOException e) {
//            logger.info(e);
//        }
//    }
    
    
    
}
