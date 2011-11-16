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
    
    String createPTZ(String name); // 添加
    
    List<PTZ> getAllPTZs(); //得到所有的云台信息
    
    String getAllPTZsJSON(); //得到所有的云台信息以Json返回。
        
    PTZ saveOrUpdate(PTZ ptz); //保存

    String updatePTZ(Long id, String name); //更新
}
