/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.service.impl;

import ff.dao.PrivilegeDao;
import ff.dao.RoleDao;
import ff.model.Privilege;
import ff.model.Role;
import ff.service.RoleService;
import ff.util.DateJsonValueProcessor;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.log4j.Logger;

/**
 *
 * @author Joey
 */
public class RoleServiceImpl implements RoleService {

    static Logger logger = Logger.getLogger(RoleServiceImpl.class.getName());
    private RoleDao roleDao;
    private PrivilegeDao privilegeDao;

    public RoleDao getRoleDao() {
        return roleDao;
    }

    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public String createRole(String name) {
        Role role = new Role();
        String info = null;
        if (roleDao.getRoleByName(name) == null) {
            role.setName(name);
            roleDao.saveOrUpdate(role);                               //存储对象
            info = "success";
        } else {
            info = "该角色名已使用，请更换！";
        }
        String jsonStr = "{success:true,info:'" + info + "'}";
        return jsonStr;
    }

    @Override
    public String deleteRole(Long id) {
        String info = roleDao.deleteRole(id);
        String jsonStr = "{success:true,info:\"" + info + "\"}";
        return jsonStr;
    }

    @Override
    public String editRole(Long id) {
        Role role = roleDao.getRoleById(id);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{});                         //这是需要过滤掉的变量名。不过滤会引起循环
        jsonConfig.registerJsonValueProcessor(Timestamp.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm"));
        JSONObject monitorJS = JSONObject.fromObject(role, jsonConfig);
        String jsonStr = monitorJS.toString();
        return jsonStr;
    }

    @Override
    public String updateRole(Long id, String name) {
        Role role = roleDao.getRoleById(id);
        String info = null;
        if (role == null) {
            info = "没有该摄像机权限，不能编辑！";
        } else {
            Role monitorDB = roleDao.getRoleByName(name);
            if (monitorDB != null && monitorDB.getId() != id) {
                info = "该摄像机权限编号已使用，请更换！";
            } else {
                role.setName(name);
                roleDao.saveOrUpdate(role);             //存储对象
                info = "success";
            }
        }
        String jsonStr = "{success:true,info:'" + info + "'}";
        return jsonStr;
    }

    @Override
    public String getAllRoles() {
        List<Role> roles = roleDao.getAllRoles();
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{"users","rolesPrivilegeDetails"});
        jsonConfig.registerJsonValueProcessor(Timestamp.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm"));
        JSONArray rolesJS = JSONArray.fromObject(roles, jsonConfig);
        String jsonStr = "{totalProperty:" + roles.size() + ",root:" + rolesJS.toString() + "}";
        return jsonStr;
    }

    /**
     *作者：Jerry
     *描述：得到和此角色菜单相关的所有菜单 JSON
     */
    @Override
    public String getRoleAllMenus(Long roleId) {
        List<Object[]> modulesList = roleDao.getRoleModules((long) roleId);
        List allMenusList = new ArrayList();
        logger.info("Menu Module Ready....................................");
        logger.info(modulesList.size());
        for (Object[] module : modulesList) {
            logger.info("Menu Module....................................");
            logger.info(module[0]);
            logger.info(module[1]);
            LinkedHashMap moduleMap = new LinkedHashMap();
            moduleMap.put("name", module[1]);
            moduleMap.put("image", "/images/system/plugin.gif");
            moduleMap.put("leaf", "false");
            //得到某模块下的所有菜单
            List<Integer> menuIdList = roleDao.getRoleModuleMenus(roleId, Long.parseLong(module[0].toString()));
            logger.info("menuIdList.size():" + menuIdList.size());
            //保存某个模块下的所有菜单
            List menusList = new ArrayList();
            for (Integer menuId : menuIdList) {
                Privilege privilege = privilegeDao.getPrivilegeById(Long.parseLong(menuId.toString()));
                LinkedHashMap menuMap = new LinkedHashMap();
                menuMap.put("text", privilege.getName());
                menuMap.put("url", privilege.getSysController().getName() + "/" + privilege.getSysAction().getName() + ".htm");
                menuMap.put("id", privilege.getSysController().getName() + "/" + privilege.getSysAction().getName() + ".htm");
                menuMap.put("icon", "/images/system/plugin.gif");
                menuMap.put("leaf", true);
                menusList.add(menuMap);
            }
            moduleMap.put("children", menusList);
            allMenusList.add(moduleMap);
        }
        JsonConfig jsonConfig = new JsonConfig();
        //这是需要过滤掉的变量名。
        //jsonConfig.setExcludes(new String[]{"videos", "users", "role_monitors"});
        //jsonConfig.registerJsonValueProcessor(Timestamp.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm"));
        JSONArray menusJSON = JSONArray.fromObject(allMenusList, jsonConfig);
        logger.info(menusJSON);
        return menusJSON.toString();
    }

    public void setPrivilegeDao(PrivilegeDao privilegeDao) {
        this.privilegeDao = privilegeDao;
    }

    @Override
    public Role getRoleById(Long id) {
        Role role = roleDao.getRoleById(id);
        return role;
    }

    @Override
    public String roleLock(Long roleId) {
        return roleDao.roleLock(roleId);
    }

    @Override
    public String getRoleJSONById(Long id) {
        Role role = roleDao.getRoleById(id);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{"rolesPrivilegeDetails", "users"});
        jsonConfig.registerJsonValueProcessor(Timestamp.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm"));
        JSONObject roleJS = JSONObject.fromObject(role, jsonConfig);
        String jsonStr = roleJS.toString();
        return jsonStr;
    }

    @Override
    public Role saveOrUpdate(Role role) {
        return roleDao.saveOrUpdate(role);
    }
}
