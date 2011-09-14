/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.controller;

import ff.model.PrivilegeDetail;
import ff.service.PrivilegeDetailService;
import ff.service.PrivilegeService;
import ff.service.SysActionService;
import ff.service.SysControllerService;
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
    private SysControllerService sysControllerService;
    private SysActionService sysActionService;

    public void setPrivilegeService(PrivilegeService privilegeService) {
        this.privilegeService = privilegeService;
    }

    public void setPrivilegeDetailService(PrivilegeDetailService privilegeDetailService) {
        this.privilegeDetailService = privilegeDetailService;
    }

    public void setSysActionService(SysActionService sysActionService) {
        this.sysActionService = sysActionService;
    }

    public void setSysControllerService(SysControllerService sysControllerService) {
        this.sysControllerService = sysControllerService;
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
    
    /**
     *作者：jerry
     *描述：创建系统权限细节。
     */
    public void createSysPrivilegeDetail(HttpServletRequest request, HttpServletResponse response) {
        String privilegeId = request.getParameter("privilege_id");
        String name = request.getParameter("name");
        String sysControllerId = request.getParameter("sys_controller_id");
        String sysActionId = request.getParameter("sys_action_id");
        String description = request.getParameter("description");
        PrivilegeDetail privilegeDetail = new PrivilegeDetail();
        privilegeDetail.setName(name);
        privilegeDetail.setSysController(sysControllerService.getSysControllerById(Long.parseLong(sysControllerId)));
        privilegeDetail.setSysAction(sysActionService.getSysActionById(Long.parseLong(sysActionId)));
        privilegeDetail.setPrivilegeId(Long.parseLong(privilegeId));
        privilegeDetail.setDescription(description);
        
        
        String jsonStr = privilegeDetailService.create(privilegeDetail);
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
