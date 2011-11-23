/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.service;

import ff.model.IgnoreAreas;

/**
 *
 * @author Haoqingmeng
 */
public interface IgnoreAreasService {

    String getIgnoreAreasList(); //得到数据列表

    IgnoreAreas saveOrUpdate(IgnoreAreas ignoreAreas); //保存

    String getIgnoreAreasJSONById(Integer id);

    IgnoreAreas getIgnoreAreasById(Integer id);
    
    String getEditIgnoreAreasJSONById(Integer id);

    String update(IgnoreAreas ignoreAreas);

    String deleteIgnoreAreas(Long id); //删除
}
