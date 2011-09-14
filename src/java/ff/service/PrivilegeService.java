/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.service;

import ff.model.PrivilegeDetail;
import ff.model.User;

/**
 *
 * @author jerry
 */
public interface PrivilegeService {
   //得到某个节点下，第一层的，所有的权限列表。
    String getSysPrivilegeChildrenById(Long nodeId);
    
}
