/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.dao;

import ff.model.Role;
import ff.model.RolesPrivilegeDetail;
import java.util.List;

/**
 *
 * @author Joey
 */
public interface RoleDao {

    Role saveOrUpdate(Role role);

    String deleteRole(Long id);

    Role getRoleById(Long id);

    List<Role> getAllRoles();

    Role getRoleByName(String name);
    
    //得到角色相关的模块
    List<Object[]> getRoleModules(Long roleId);
    
    //得到模块相关的菜单
    List<Integer> getRoleModuleMenus(Long roleId,Long moduleId);
}
