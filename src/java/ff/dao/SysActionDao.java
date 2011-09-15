/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.dao;

import ff.model.SysAction;
import java.util.List;

/**
 *
 * @author Joey
 */
public interface SysActionDao {

    List<SysAction> getAllSysActions(Long sysControllerId);
    
    SysAction getSysActionById(Long id);

    //List<Role> getRoleList();
    
    //根据控制器名和controllerId得到sysAction
    SysAction getSysActionByNameCId(String name,Long CId);
    
}
