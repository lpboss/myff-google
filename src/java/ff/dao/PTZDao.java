/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.dao;

import ff.model.PTZ;
import java.util.List;

/**
 *
 * @author Haoqingmeng
 */
public interface PTZDao {

    PTZ getPTZByName(String name); //添加

    PTZ saveOrUpdate(PTZ ptz); //保存

    void deletePTZ(String id); //删除

    List<PTZ> getAllPTZs();    //得到所有的云台信息

    PTZ getPTZById(Long id);   //得到某一条数据

    List<PTZ> getIsAlarmPTZs();  //得到所有正在报火警的云台
}
