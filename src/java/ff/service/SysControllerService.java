/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.service;

import ff.model.RolesPrivilegeDetail;
import ff.model.SysController;
import java.util.List;

/**
 *
 * @author Joey
 */
public interface SysControllerService {

    String getAllSysControllers();
    
    SysController getSysControllerById(Long id);
    
}
