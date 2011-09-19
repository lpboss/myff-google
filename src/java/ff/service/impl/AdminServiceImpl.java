/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.service.impl;

import ff.dao.AdminDao;
import ff.dao.PrivilegeDetailDao;
import ff.dao.RoleDao;
import ff.dao.RolesPrivilegeDetailDao;
import ff.model.PrivilegeDetail;
import ff.model.RolesPrivilegeDetail;
import ff.service.AdminService;
import ff.util.DateJsonValueProcessor;
import java.sql.Timestamp;
import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

/**
 *
 * @author jerry
 */
public class AdminServiceImpl implements AdminService {

    private AdminDao adminDao;
    private PrivilegeDetailDao privilegeDetailDao;
    private RoleDao roleDao;
    private RolesPrivilegeDetailDao rolesPrivilegeDetailDao;

    public void setAdminDao(AdminDao adminDao) {
        this.adminDao = adminDao;
    }

    public void setPrivilegeDetailDao(PrivilegeDetailDao privilegeDetailDao) {
        this.privilegeDetailDao = privilegeDetailDao;
    }

    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public Integer executeNativeUpdateSQL(String sql) {
        return adminDao.executeNativeUpdateSQL(sql);
    }

    @Override
    public String getRolePrivilegeDetailsById(Long roleId, Long privilegeId, Long parentPrivilegeId) {
        //在这里，角色中的privilege已经深化为menu_id，是一系列相关权限细节的集合
        //首先得到系统中，此角色当前已经对应的角色权限细节
        List<RolesPrivilegeDetail> rolesPrivilegeDetails = adminDao.getRolesPrivilegeDetails(privilegeId, roleId);
        //得到，所有非锁定的系统权限
        List<PrivilegeDetail> sysPrivilegeDetails = privilegeDetailDao.getUnlockedSysPrivilegeDetails(privilegeId);

        if (sysPrivilegeDetails.size() > 0) {
            //如果此时角色还没有分配过权限细节，则把现有系统权限一次性追加上。
            if (rolesPrivilegeDetails.size() == 0) {
                for (PrivilegeDetail sysPrivilegeDetail : sysPrivilegeDetails) {
                    RolesPrivilegeDetail rolesPrivilegeDetail = new RolesPrivilegeDetail();
                    rolesPrivilegeDetail.setModuleId(parentPrivilegeId);
                    rolesPrivilegeDetail.setMenuId(privilegeId);
                    rolesPrivilegeDetail.setPrivilegeDetailId(sysPrivilegeDetail.getId());
                    rolesPrivilegeDetail.setRole(roleDao.getRoleById(roleId));
                    rolesPrivilegeDetailDao.saveOrUpdate(rolesPrivilegeDetail);
                }
            } else {
                //分二次循环，一是查是否有多的，第二次是查是滞有少的。
                //有不动，少则加，多则删
                //privilegeAction有三个值，1.add,2.remove,3.nil,""就是无动作
                String privilegeAction = null;
                for (RolesPrivilegeDetail rolesPrivilegeDetail : rolesPrivilegeDetails) {
                    privilegeAction = "remove";
                    for (PrivilegeDetail sysPrivilegeDetail : sysPrivilegeDetails) {
                        if (rolesPrivilegeDetail.getPrivilegeDetailId() == sysPrivilegeDetail.getId()) {
                            privilegeAction = null;
                        }
                    }
                    if (privilegeAction == "remove") {
                        rolesPrivilegeDetailDao.delete(rolesPrivilegeDetail);
                    }
                }
                //第二次循环，就是添加
                privilegeAction = null;
                for (PrivilegeDetail sysPrivilegeDetail : sysPrivilegeDetails) {
                    privilegeAction = "add";
                    for (RolesPrivilegeDetail rolesPrivilegeDetail : rolesPrivilegeDetails) {
                        if (rolesPrivilegeDetail.getPrivilegeDetailId() == sysPrivilegeDetail.getId()) {
                            privilegeAction = null;
                        }
                    }
                    if (privilegeAction == "add") {
                        RolesPrivilegeDetail rolesPrivilegeDetail = new RolesPrivilegeDetail();
                        rolesPrivilegeDetail.setModuleId(parentPrivilegeId);
                        rolesPrivilegeDetail.setMenuId(privilegeId);
                        rolesPrivilegeDetail.setPrivilegeDetailId(sysPrivilegeDetail.getId());
                        rolesPrivilegeDetail.setRole(roleDao.getRoleById(roleId));
                        rolesPrivilegeDetailDao.saveOrUpdate(rolesPrivilegeDetail);
                    }
                }
            }
        }

        rolesPrivilegeDetails = adminDao.getRolesPrivilegeDetails(privilegeId, roleId);
        JsonConfig jsonConfig = new JsonConfig();
        //这是需要过滤掉的变量名。
        //jsonConfig.setExcludes(new String[]{"videos", "users", "role_monitors"});
        jsonConfig.registerJsonValueProcessor(Timestamp.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm"));
        JSONArray rolesPrivilegeDetailsJS = JSONArray.fromObject(rolesPrivilegeDetails, jsonConfig);
        String jsonStr = "{totalProperty:" + rolesPrivilegeDetails.size() + ",root:" + rolesPrivilegeDetailsJS.toString() + "}";
        return jsonStr;
    }
}
