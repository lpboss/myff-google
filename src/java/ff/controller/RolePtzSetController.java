/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.controller;

import ff.model.PTZ;
import ff.model.Role;
import ff.model.RolePtz;
import ff.service.PTZService;
import ff.service.RolePtzSetService;
import ff.service.RoleService;
import java.io.IOException;
import java.io.PrintWriter;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Haoqingmeng
 */
public class RolePtzSetController extends MultiActionController {

    private RolePtzSetService rolePtzSetService;
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

    public RolePtzSetService getRolePtzSetService() {
        return rolePtzSetService;
    }

    public void setRolePtzSetService(RolePtzSetService rolePtzSetService) {
        this.rolePtzSetService = rolePtzSetService;
    }

    //返回rolePtzSet页面
    public ModelAndView rolePtzSet(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        return mav;
    }

    //返回添加页面
    public ModelAndView newRolePtz(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        return mav;
    }

    //得到角色表
    public void getAllRoles(HttpServletRequest request, HttpServletResponse response) {
        String jsonStr = rolePtzSetService.getAllRoles();
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
            jsonStr = rolePtzSetService.getPTZList();
        } else {
            jsonStr = rolePtzSetService.getRolePtzSetJSONById(Integer.parseInt(request.getParameter("id")));
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
    public void rolePtzSetLock(HttpServletRequest request, HttpServletResponse response) {
        Long id = Long.valueOf(request.getParameter("id"));
        Long roleid = Long.valueOf("88");
        rolePtzSetService.resetDefault(roleid);
        PTZ ptz = rolePtzSetService.getPTZById(id);
        if (ptz.getIsDefault() == 0) {
            ptz.setIsDefault(Long.valueOf("1"));
        } else {
            ptz.setIsDefault(Long.valueOf("0"));
        }
        PrintWriter pw;
        try {
            String jsonStr = rolePtzSetService.ptzLock(ptz);
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
        logger.info("1212");
        logger.info(request.getParameter("id"));
        logger.info("34v35g");
        if(request.getParameter("id")!= null){
            logger.info("565555");
           jsonStr = rolePtzSetService.getRolePtzSetJSONById(Integer.parseInt(request.getParameter("id")));
        }else{
            logger.info("33567486");
           jsonStr = rolePtzSetService.getRolePtzList();
        }
        logger.info("468748");
        logger.info(jsonStr);
        logger.info("265g6h");
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

        Long ids = Long.valueOf(request.getParameter("ptz")); //云台Id
        PTZ ptz = ptzService.getPTZById(ids);
        rolePtz.setPtz(ptz);

        Long idds = Long.valueOf(request.getParameter("userId")); //角色id
        Role role = roleService.getRoleById(idds);
        rolePtz.setRole(role);
        if (!request.getParameter("is_default").equals("")) {
            rolePtz.setIsDefault(Long.getLong("1"));
        }//状态 
        
        String jsonStr = rolePtzSetService.create(rolePtz);
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
        logger.info("4v6h31");
         logger.info(id);
          logger.info(roleid);
           logger.info("4v6h31");
        String jsonStr = rolePtzSetService.deleteRolePtz(id,roleid);
        
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
        String jsonStr = rolePtzSetService.getRolePtzList();
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
