/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.service;

import ff.model.PTZ;
import ff.model.RolePtz;

/**
 *
 * @author Haoqingmeng
 */
public interface RolePtzSetService {

    String getAllRoles(); //得到角色列表

    String getPTZList(); //得到ptz数据列表

    PTZ getPTZById(Long id); //得到某一条数据

    String ptzLock(PTZ ptz); //是否锁定

    String resetDefault(Long id);  // 重置默认
    
    String getRolePtzSetJSONById(Integer id); //
    
    String create(RolePtz rolePtz); // 添加
    
    String deleteRolePtz(String id,String roleid); //删除
    
    String getRolePtzList(); //得到rolePtz数据列表
    
}
