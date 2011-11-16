/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.service.impl;
import ff.dao.FireAlarmDao;
import ff.model.FireAlarm;

import ff.service.FireAlarmService;
import ff.util.DateJsonValueProcessor;
import java.sql.Timestamp;
import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class FireAlarmServiceImpl implements FireAlarmService{

    @Override
    public String getFireAlarmList() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
