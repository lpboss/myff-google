/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.dao;

import ff.model.AlarmIgnoreAreas;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface AlarmIgnoreAreasDao {
    
    List<AlarmIgnoreAreas> getAlarmIgnoreAreases();    //得到所有的报警忽视地区信息
    
    AlarmIgnoreAreas saveOrUpdate(AlarmIgnoreAreas alarmIgnoreAreas); //保存
    
    AlarmIgnoreAreas getAlarmIgnoreAreasById(Long id);   //得到某一条数据
    
    AlarmIgnoreAreas getAlarmIgnoreAreasByName(String name); //添加
    
}
