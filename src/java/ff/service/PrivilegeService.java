/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.service;

import ff.model.Privilege;
import ff.model.PrivilegeDetail;
import ff.model.User;

/**
 *
 * @author jerry
 */
public interface PrivilegeService {
    //得到某个节点下，第一层的，所有的权限列表。

    String getSysPrivilegeChildrenById(Long nodeId,Integer isLocked);

    Privilege getPrivilegeById(Long id);

    Privilege getPrivilegeByParentIdSortId(Long parentId, Integer sortId);

    String getPrivilegeJSONById(Long id);

    String getAllModulesJSON();

    Privilege saveOrUpdate(Privilege privilege);
    
    //得到同一parentId下，sortid值最大的那个privilege
    Integer getMaxSortIdByParentId(Long parentId);
    
    String delete(Long id);
}
