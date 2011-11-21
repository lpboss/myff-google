/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.service;

import ff.model.AlarmIgnoreAreas;

/**
 *
 * @author Haoqingmeng
 */
public interface AlarmIgnoreAreasService {
    
   String getAlarmIgnoreAreasList(); //得到数据列表
   
   AlarmIgnoreAreas saveOrUpdate(AlarmIgnoreAreas alarmIgnoreAreas); //保存
   
   String getAlarmIgnoreAreasJSONById(Long id);
   
   AlarmIgnoreAreas getAlarmIgnoreAreasById(Long id);
   
   String update(AlarmIgnoreAreas alarmIgnoreAreas);
    
}
