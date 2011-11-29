/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.dao;

import ff.model.PTZ;
import ff.model.Role;
import ff.model.RolePtz;
import java.util.List;

/**
 *
 * @author Haoqingmeng
 */
public interface RolePtzSetDao {

    List<Role> getAllRoles(); //得到所有的角色

    List<PTZ> getAllPTZs();    //得到所有的云台信息

    RolePtz getRolePtzById(Long id, Long roleid);   //得到某一条数据

    void setDefault(Long id);   //重置默认

    List<RolePtz> getById(Integer id);   //得到某一条数据   

    List<PTZ> getPtzsByIds(String ids);  //通过IDS 得到云台

    RolePtz saveOrUpdate(RolePtz rolePtz); //保存并更新

    void deleteRolePtz(String id, String roleid); //删除

    List<RolePtz> getAllRolePtzs();    //得到所有的rolePtz信息

    List<RolePtz> getRolePtzByIds(String ids);  //通过id得到rolePtz

    RolePtz getRolePtzByName(Long id, Long idd); //判断有没有重名的
}
