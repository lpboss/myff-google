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
    
    //得到某个节点下，第一层的，所有的权限列表。
    String getSysPrivilegeChildrenById(Long nodeId);
    
    List<Privilege> getAllModules();
  
    Privilege saveOrUpdate(Privilege privilege);
}
