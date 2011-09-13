/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.controller;

import ff.service.PrivilegeService;
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
    public ModelAndView getSysPrivilegeChildrenById(HttpServletRequest request, HttpServletResponse response) {
        logger.info("systemPrivilege page");
        ModelAndView mav = new ModelAndView();
        return mav;
    }
    
    
}
