/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.service;

import ff.model.RolesPrivilegeDetail;
import ff.model.SysAction;
import java.util.List;

/**
 *
 * @author Joey
 */
public interface SysActionService {
    
    String getAllSysActions(Long sysControllerId);
    
    SysAction getSysActionById(Long id);
}
