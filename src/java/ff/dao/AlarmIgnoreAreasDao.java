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
    
}
