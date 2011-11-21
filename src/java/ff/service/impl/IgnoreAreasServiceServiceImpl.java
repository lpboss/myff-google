/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.service.impl;

import ff.dao.ignoreAreasDao;
import ff.model.IgnoreAreas;
import ff.service.IgnoreAreasService;
import ff.util.DateJsonValueProcessor;
import java.sql.Timestamp;
import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 *
 * @author Administrator
 */
public class IgnoreAreasServiceServiceImpl implements IgnoreAreasService {

    private ignoreAreasDao ignoreAreasDao;

    public ignoreAreasDao getIgnoreAreasDao() {
        return ignoreAreasDao;
    }

    public void setIgnoreAreasDao(ignoreAreasDao ignoreAreasDao) {
        this.ignoreAreasDao = ignoreAreasDao;
    }

    //得到数据列表
    @Override
    public String getIgnoreAreasList() {
        List ignoreAreases = ignoreAreasDao.getIgnoreAreases();
        JsonConfig jsonConfig = new JsonConfig();
        //这是需要过滤掉的变量名。
        jsonConfig.setExcludes(new String[]{});
        jsonConfig.registerJsonValueProcessor(Timestamp.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        JSONArray ptzJS = JSONArray.fromObject(ignoreAreases, jsonConfig);
        String jsonStr = "{totalProperty:" + ignoreAreases.size() + ",root:" + ptzJS.toString() + "}";
        return jsonStr;
    }

    @Override
    public IgnoreAreas saveOrUpdate(IgnoreAreas ignoreAreas) {
        return ignoreAreasDao.saveOrUpdate(ignoreAreas);
    }

    @Override
    public String getIgnoreAreasJSONById(Long id) {
        IgnoreAreas ptz = ignoreAreasDao.getIgnoreAreasById(id);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{"videos", "users"});
        jsonConfig.registerJsonValueProcessor(Timestamp.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm"));
        JSONObject userJS = JSONObject.fromObject(ptz, jsonConfig);
        String jsonStr = userJS.toString();
        return jsonStr;
    }

    @Override
    public IgnoreAreas getIgnoreAreasById(Long id) {
        return ignoreAreasDao.getIgnoreAreasById(id);
    }

    @Override
    public String update(IgnoreAreas ignoreAreas) {
        String info = null;
        if (ignoreAreas == null) {
            info = "没有该用户，不能编辑！";
        } else {
            ignoreAreasDao.saveOrUpdate(ignoreAreas);
            info = "success";
        }
        String jsonStr = "{success:true,info:'" + info + "'}";
        return jsonStr;
    }

    @Override
    public String deleteIgnoreAreas(Long id) {
        String info = ignoreAreasDao.deleteIgnoreAreas(id);
        String jsonStr = "{success:true,info:\"" + info + "\"}";
        return jsonStr;
    }
}
