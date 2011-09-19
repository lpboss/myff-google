/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.dao;

import ff.model.Privilege;
import java.util.List;

/**
 *
 * @author Joey
 */
public interface PrivilegeDao {

    Privilege getPrivilegeById(Long id);

    Privilege getPrivilegeByParentIdSortId(Long parentId, Integer sortId);

    //得到某个节点下，第一层的，所有的权限列表。
    String getSysPrivilegeChildrenById(Long nodeId,Integer isLocked);

    List<Privilege> getAllModules();

    Privilege saveOrUpdate(Privilege privilege);
    
    //得到同一parentId下，sortid值最大的那个privilege
    Integer getMaxSortIdByParentId(Long parentId);
}
