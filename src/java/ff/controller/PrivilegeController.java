/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.controller;

import ff.service.PrivilegeDetailService;
import ff.service.PrivilegeService;
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
public class PrivilegeController extends MultiActionController {

    private PrivilegeService privilegeService;
    private PrivilegeDetailService privilegeDetailService;

    public void setPrivilegeService(PrivilegeService privilegeService) {
        this.privilegeService = privilegeService;
    }

    public void setPrivilegeDetailService(PrivilegeDetailService privilegeDetailService) {
        this.privilegeDetailService = privilegeDetailService;
    }

    /**
     *作者：jerry
     *描述：系统权限页面
     */
    public ModelAndView systemPrivilege(HttpServletRequest request, HttpServletResponse response) {
        logger.info("systemPrivilege page");
        ModelAndView mav = new ModelAndView();
        return mav;
    }
    
    /**
     *作者：jerry
     *描述：添加权限细节的页面
     */
    public ModelAndView newPrivilegeDetail(HttpServletRequest request, HttpServletResponse response) {
        logger.info("newPrivilegeDetail page");
        ModelAndView mav = new ModelAndView();
        return mav;
    }

    /**
     *作者：jerry
     *描述：得到某个节点的权限树结构。
     */
    public void getSysPrivilegeChildrenById(HttpServletRequest request, HttpServletResponse response) {
        String nodeId = request.getParameter("node");
        String jsonStr = privilegeService.getSysPrivilegeChildrenById(Long.parseLong(nodeId));
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
     *描述：得到某个菜单的详细权限。
     */
    public void getPrivilegeDetailsById(HttpServletRequest request, HttpServletResponse response) {
        String privilegeId = request.getParameter("privilege_id");
        String jsonStr = privilegeDetailService.getPrivilegeDetailsById(Long.parseLong(privilegeId));
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
