/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.service;

import ff.model.PTZ;

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
}
