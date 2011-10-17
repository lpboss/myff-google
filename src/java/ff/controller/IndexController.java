/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.controller;

import ff.service.RoleService;
import ff.service.UserService;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 *
 * @author jerry
 */
public class IndexController extends MultiActionController {

    private UserService userService;
    
    private RoleService roleService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }
    

    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {

        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
        
        
        logger.info("你好。。。。。。。。。。。。。。。。。。。。。。。。。。。Begin Index");
        String menusJSON = roleService.getRoleAllMenus(Long.parseLong("1"));
        /*List<Object[]> moduleIdList = roleService.getRoleAllMenus((long)1);
        logger.info("Menu Module Ready....................................");
        logger.info(moduleIdList.size());
        for (Object[] objects : moduleIdList){
            logger.info("Menu Module....................................");
            logger.info(objects[0]);
            logger.info(objects[1]);
        }*/

        ModelAndView mav = new ModelAndView();
        mav.addObject("menus", menusJSON);
        return mav;
    }

    /**
     *作者：jerry
     *描述：登录页面
     */
    public void validateUser(HttpServletRequest request, HttpServletResponse response) {
        logger.info("validate.......user..............");
        logger.debug("Fuck...........................................");
        String loginId = request.getParameter("user_name");
        String password = request.getParameter("pwd");
        String jsonStr = userService.validateUser(loginId, password);
        if (jsonStr.contains("success:true,info:'success'")) {
            String id = jsonStr.substring(32, jsonStr.length() - 1);
            HttpSession session = request.getSession();
            session.setAttribute("userId", id);                             //在session中放入当前用户的用户名
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

    /**
     *作者：jerry
     *描述：登录页面
     */
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response) {
        logger.info("now downloading the login page");
        ModelAndView mav = new ModelAndView();
        return mav;
    }

    /**
     * 作者：Jerry
     * 描述：退出系统，清空所有session
     */
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();
        String result = "{success:true,info:'success'}";
        PrintWriter pw;
        try {
            response.setContentType("text/json; charset=utf-8");
            response.setHeader("Cache-Control", "no-cache");
            pw = response.getWriter();
            pw.write(result);
            pw.close();
        } catch (IOException e) {
            logger.info(e);
        }
    }

    private String getMenus() {
        /*    HttpSession session = httpreq.getSession(false);
        logger.info ("------------------index--getMenus----------------------");
        int roleId = (Integer)session.getAttribute("roleId");
        
        //这是新形势的菜单，先抓取模块，再一条条的抓取详细的菜单。
        //总菜单
        //totalMenuArray <-- moduleMenuHash <-- subMenuArray <-- subMenuHash
        
        //totalMenuArray = Array.new
        ArrayList totalMenuArray = new ArrayList();
        rolesPrivilegeDetails = RolesPrivilegeDetail.find(:all,:include=>[:module],:select=>"DISTINCT rpd.module_id",:conditions=>["p.parent_id = 0"],:joins=>" as rpd left join privileges as p on rpd.role_id = #{roleId} AND rpd.company_id = #{companyId} AND rpd.is_locked = 0 AND rpd.module_id = p.id",:order=>"p.sort_id")
        rolesPrivilegeDetails.each do |sysModule|
        moduleMenuHash = Hash.new
        moduleMenuHash[:name] = sysModule.module.name
        moduleMenuHash[:image] = '/images/system/plugin.gif'
        moduleMenuHash[:leaf] = false
        #开始生成菜单      
        roleMenus = RolesPrivilegeDetail.find(:all,:select=>"DISTINCT rpd.menu_id",:conditions=>["rpd.module_id = ? AND rpd.is_locked = 0 AND rpd.role_id = ?",sysModule.module_id,roleId],:joins=>"as rpd left join privileges as p on rpd.menu_id = p.id",:order=>"p.sort_id")
        subMenuArray = Array.new
        roleMenus.each do |roleMenu|
        subMenuHash = Hash.new
        #subMenuHash[:id] = roleMenu.menu.id
        subMenuHash[:text] = roleMenu.menu.name
        subMenuHash[:url] = "#{roleMenu.menu.sys_controller.name}/#{roleMenu.menu.sys_action.name}"
        subMenuHash[:id] = "#{roleMenu.menu.sys_controller.name}/#{roleMenu.menu.sys_action.name}"
        subMenuHash[:icon] = '/images/system/plugin.gif'
        subMenuHash[:leaf] = true
        subMenuArray << subMenuHash
        logger.info "#{roleMenu.menu.sys_controller.name}/#{roleMenu.menu.sys_action.name}"
        end
        moduleMenuHash[:children] = subMenuArray
        
        //totalMenuArray << moduleMenuHash
        totalMenuArray.add(moduleMenuHash);
        end
        logger.info "totalMenuArray............................."
        menus = totalMenuArray.to_json
        return menus    */
        return null;
    }
}
