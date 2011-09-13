/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.service.impl;

import ff.dao.PrivilegeDao;

import ff.service.PrivilegeService;
import org.apache.log4j.Logger;

/**
 *
 * @author jerry
 */
public class PrivilegeServiceImpl implements PrivilegeService {

    static Logger logger = Logger.getLogger(PrivilegeServiceImpl.class.getName());
    //注入区：
    private PrivilegeDao privilegeDao;

    public void setPrivilegeDao(PrivilegeDao privilegeDao) {
        this.privilegeDao = privilegeDao;
    }

    @Override
    public String getSysPrivilegeChildrenById(Long nodeId) {
        return privilegeDao.getSysPrivilegeChildrenById(nodeId);
    }
}
