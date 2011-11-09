/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.dao.impl;

import ff.dao.PTZDao;
import ff.model.PTZ;
import java.util.List;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 *
 * @author Haoqingmeng
 */
public class PTZDaoHImpl extends HibernateDaoSupport implements PTZDao  {

    @Override
    public PTZ saveOrUpdate(PTZ ptz) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String deletePTZ(Long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<PTZ> getAllPTZs() {
        List<PTZ> ptzs = this.getHibernateTemplate().find("from PTZ");
        return ptzs;
    }

    @Override
    public PTZ getPTZById(Long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
