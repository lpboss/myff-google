/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.service;

import ff.model.PTZ;
import ff.model.RolePtz;
import java.util.List;

/**
 *
 * @author Haoqingmeng
 */
public interface RolePtzSetService {

    String getAllRoles(); //得到角色列表

    String getPTZList(); //得到ptz数据列表

    RolePtz getRolePtzById(Long id,Long roleid); //得到某一条数据

    String RolePtzDefault(RolePtz rolePtz); //是否默认

    String resetDefault(Long id);  // 重置默认
    
    String getRolePtzSetJSONById(Integer id); //通过id得到rolePtz列表
    
    String create(RolePtz rolePtz,Long ptzId,Long roleId); // 添加
    
    String deleteRolePtz(String id,String roleid); //删除
    
    String getRolePtzList(); //得到rolePtz数据列表
    
    String getRolePtzSetById(Long id); //通过id得到rolePtz列表
    
}
