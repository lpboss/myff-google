/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.controller;

import ff.service.PrivilegeService;
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
public class PrivilegeController extends MultiActionController {
    private PrivilegeService privilegeService;

    public void setPrivilegeService(PrivilegeService privilegeService) {
        this.privilegeService = privilegeService;
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
    
    
}
