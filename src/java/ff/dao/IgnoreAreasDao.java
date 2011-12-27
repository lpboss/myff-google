/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.dao;

import ff.model.IgnoreAreas;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface IgnoreAreasDao {

    List<IgnoreAreas> getIgnoreAreases();    //得到所有的报警忽视地区信息

    IgnoreAreas saveOrUpdate(IgnoreAreas ignoreAreas); //保存

    List<IgnoreAreas> getById(Integer id);   //得到某一条数据   
    
    IgnoreAreas getIgnoreAreasById(Integer id);  //得到要修改的某一条数据

    IgnoreAreas getIgnoreAreasByName(String name); //添加

    void deleteIgnoreAreas(String id); //删除
    
    boolean inIgnoreAreas(float angelX, float angelY);
}
