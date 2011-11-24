/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.service;

import ff.model.PTZ;
import java.util.List;

/**
 *
 * @author Haoqingmeng
 */
public interface PTZService {

    String deletePTZ(Long id); //删除

    String editPTZ(Long id);   //修改

    String create(PTZ ptz); // 添加

    List<PTZ> getAllPTZs(); //得到所有的云台信息

    String getAllPTZsJSON(); //得到所有的云台信息以Json返回。

    String getIsAlarmPTZsJSON(); //得到所有火警云台信息以Json返回。

    String getPTZList(); //得到数据列表

    PTZ saveOrUpdate(PTZ ptz); //保存

    PTZ getPTZById(Long id);

    String update(PTZ ptz);

    String getPTZJSONById(Long id);

    String updatePTZ(Long id, String name); //更新
    String ptzLock(PTZ ptz);
    
}