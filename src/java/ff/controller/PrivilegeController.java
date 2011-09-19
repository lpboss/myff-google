/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.controller;

import ff.model.Privilege;
import ff.model.PrivilegeDetail;
import ff.model.SysAction;
import ff.model.SysController;
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
     *描述：添加模块菜单
     */
    public ModelAndView newPrivilegeModule(HttpServletRequest request, HttpServletResponse response) {
        logger.info("newPrivilegeModule page");
        ModelAndView mav = new ModelAndView();
        return mav;
    }

    /**
     *作者：jerry
     *描述：编辑模块菜单
     */
    public ModelAndView editPrivilegeModule(HttpServletRequest request, HttpServletResponse response) {
        logger.info("newPrivilegeModule page");
        ModelAndView mav = new ModelAndView();
        return mav;
    }

    /**
     *作者：jerry
     *描述：添加权限菜单
     */
    public ModelAndView newPrivilegeMenu(HttpServletRequest request, HttpServletResponse response) {
        logger.info("newPrivilegeDetail page");
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
     *描述：编辑权限细节的页面
     */
    public ModelAndView editPrivilegeDetail(HttpServletRequest request, HttpServletResponse response) {
        PrivilegeDetail privilegeDetail = privilegeDetailService.getPrivilegeDetailById(Long.parseLong(request.getParameter("id")));
        logger.info("newPrivilegeDetail page");
        ModelAndView mav = new ModelAndView();
        mav.addObject("sysControllerId", privilegeDetail.getSysController().getId());
        return mav;
    }

    /**
     *作者：jerry
     *描述：编辑菜单的页面
     */
    public ModelAndView editPrivilegeMenu(HttpServletRequest request, HttpServletResponse response) {
        Privilege privilege = privilegeService.getPrivilegeById(Long.parseLong(request.getParameter("id")));
        logger.info("newPrivilegeDetail page");
        ModelAndView mav = new ModelAndView();
        mav.addObject("sysControllerId", privilege.getSysController().getId());
        return mav;
    }

    /**
     *作者：jerry
     *描述：得到某个节点的权限树结构。
     */
    public void getPrivilegeDetailById(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        String jsonStr = privilegeDetailService.getPrivilegeDetailJSONById(Long.parseLong(request.getParameter("id")));
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
     *描述：创建系统菜单或模块
     */
    public void createSysPrivilege(HttpServletRequest request, HttpServletResponse response) {
        String parentId = request.getParameter("parent_id");
        String name = request.getParameter("name");
        String sysControllerId = request.getParameter("sys_controller_id");
        String sysActionId = request.getParameter("sys_action_id");
        String description = request.getParameter("description");
        Privilege privilege = new Privilege();
        privilege.setName(name);
        privilege.setParentId(Long.parseLong(parentId));
        if (privilege.getParentId() == 0) {
            privilege.setLevel(0);
            privilege.setLeaf("false");
        } else {
            privilege.setLevel(1);
            privilege.setLeaf("true");
            privilege.setSysController(sysControllerService.getSysControllerById(Long.parseLong(sysControllerId)));
            privilege.setSysAction(sysActionService.getSysActionById(Long.parseLong(sysActionId)));
        }

        privilege.setDescription(description);

        //如果在非根节点添加了权限，要把上一级权限的CSS设定为leaf=false
        Privilege parentPrivilege;
        if (privilege.getParentId() != 0) {
            parentPrivilege = privilegeService.getPrivilegeById(Long.parseLong(parentId));
            parentPrivilege.setLeaf("false");
            privilegeService.saveOrUpdate(parentPrivilege);
        }

        //得到sortId最大值
        Integer maxSortId = privilegeService.getMaxSortIdByParentId(Long.parseLong(parentId));
        privilege.setSortId(maxSortId + 1);
        privilegeService.saveOrUpdate(privilege);

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
     *描述：创建系统权限细节。
     */
    public void createSysPrivilegeDetail(HttpServletRequest request, HttpServletResponse response) {
        String privilegeId = request.getParameter("privilege_id");
        String name = request.getParameter("name");
        logger.info("name:" + name);
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

    /**
     *作者：jerry
     *描述：更新系统权限细节。
     */
    public void updateSysPrivilegeDetail(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String sysControllerId = request.getParameter("sysControllerId");
        String sysActionId = request.getParameter("sysActionId");
        String description = request.getParameter("description");
        PrivilegeDetail privilegeDetail = privilegeDetailService.getPrivilegeDetailById(Long.parseLong(id));
        privilegeDetail.setName(name);
        privilegeDetail.setSysController(sysControllerService.getSysControllerById(Long.parseLong(sysControllerId)));
        privilegeDetail.setSysAction(sysActionService.getSysActionById(Long.parseLong(sysActionId)));
        privilegeDetail.setDescription(description);

        String jsonStr = privilegeDetailService.update(privilegeDetail);
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
     *描述：锁定系统权限细节。
     */
    public void privilegeDetailLock(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        PrivilegeDetail privilegeDetail = privilegeDetailService.getPrivilegeDetailById(Long.parseLong(id));
        logger.info("privilegeDetail.getIsLocked():" + privilegeDetail.getIsLocked());
        if (privilegeDetail.getIsLocked() == null || privilegeDetail.getIsLocked() == 0) {
            privilegeDetail.setIsLocked((long) 1);
        } else {
            privilegeDetail.setIsLocked((long) 0);
        }
        String jsonStr = privilegeDetailService.update(privilegeDetail);
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
     *描述：得到某个系统权限。
     */
    public void getSysPrivilegeById(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        String jsonStr = privilegeService.getPrivilegeJSONById(Long.parseLong(id));
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
     *描述：得到所有模块
     */
    public void getAllModules(HttpServletRequest request, HttpServletResponse response) {
        String jsonStr = privilegeService.getAllModulesJSON();
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
     *描述：更新菜单(更新模块等方法，共用此方法)
     */
    public void updateSysPrivilege(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        Privilege privilege = privilegeService.getPrivilegeById(Long.parseLong(id));

        privilege.setName(request.getParameter("name"));
        privilege.setDescription(request.getParameter("description"));

        if (Long.parseLong("parent_id") > 0) {
            String moduleId = request.getParameter("moduleId");
            String sysControllerId = request.getParameter("sysControllerId");
            String sysActionId = request.getParameter("sysActionId");
            privilege.setParentId(Long.parseLong(moduleId));
            SysController sysController = sysControllerService.getSysControllerById(Long.parseLong(sysControllerId));
            SysAction sysAction = sysActionService.getSysActionById(Long.parseLong(sysActionId));
            privilege.setSysController(sysController);
            privilege.setSysAction(sysAction);
            //RolesPrivilegeDetail.update_all("module_id = #{privilege.parent_id}","menu_id = #{privilege.id}")
        }
        
        privilegeService.saveOrUpdate(privilege);

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
     *描述：排序上移
     */
    public void sortUp(HttpServletRequest request, HttpServletResponse response) {
        Privilege privilege = privilegeService.getPrivilegeById(Long.parseLong(request.getParameter("id")));
        Privilege previousPrivilege;
        Long parentId = new Long(0);
        //等于0，说明是模块级，非0代表是菜单级
        if (privilege.getParentId() != 0) {
            parentId = privilege.getParentId();
        }

        previousPrivilege = privilegeService.getPrivilegeByParentIdSortId(parentId, privilege.getSortId() - 1);

        logger.info("previousPrivilege:" + previousPrivilege);

        if (previousPrivilege != null) {
            privilege.setSortId(privilege.getSortId() - 1);
            privilegeService.saveOrUpdate(privilege);
            previousPrivilege.setSortId(previousPrivilege.getSortId() + 1);
            privilegeService.saveOrUpdate(previousPrivilege);
        }

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
     *描述：排序下移
     */
    public void sortDown(HttpServletRequest request, HttpServletResponse response) {
        Privilege privilege = privilegeService.getPrivilegeById(Long.parseLong(request.getParameter("id")));
        Privilege previousPrivilege;
        Long parentId = new Long(0);
        //等于0，说明是模块级，非0代表是菜单级
        if (privilege.getParentId() != 0) {
            parentId = privilege.getParentId();
        }

        previousPrivilege = privilegeService.getPrivilegeByParentIdSortId(parentId, privilege.getSortId() + 1);
        if (previousPrivilege != null) {
            privilege.setSortId(privilege.getSortId() + 1);
            privilegeService.saveOrUpdate(privilege);
            previousPrivilege.setSortId(previousPrivilege.getSortId() - 1);
            privilegeService.saveOrUpdate(previousPrivilege);
        }

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
}
