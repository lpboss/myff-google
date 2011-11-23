/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.controller;

import ff.dao.SysActionDao;
import ff.dao.SysControllerDao;
import ff.model.SysAction;
import ff.model.SysController;
import ff.service.SysActionService;
import ff.service.SysControllerService;
import ff.util.PTZUtil;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 *
 * @author jerry
 */
public class TestController extends MultiActionController {

    private SysControllerDao sysControllerDao;
    private SysActionDao sysActionDao;
    private SysControllerService sysControllerService;
    private SysActionService sysActionService;

    public void setSysActionDao(SysActionDao sysActionDao) {
        this.sysActionDao = sysActionDao;
    }

    public void setSysControllerDao(SysControllerDao sysControllerDao) {
        this.sysControllerDao = sysControllerDao;
    }

    public void setSysActionService(SysActionService sysActionService) {
        this.sysActionService = sysActionService;
    }

    public void setSysControllerService(SysControllerService sysControllerService) {
        this.sysControllerService = sysControllerService;
    }

    //把控制器中的所有方法插入到privilege_details表中
    public ModelAndView doAllControllerMethods(HttpServletRequest request, HttpServletResponse response) {
        logger.info("In doAllControllerMethods..................................................................");
        Map<String, String> cMap = new HashMap<String, String>();
        cMap.put("admin", "ff.controller.AdminController");
        cMap.put("index", "ff.controller.IndexController");
        cMap.put("privilege", "ff.controller.PrivilegeController");
        cMap.put("user", "ff.controller.UserController");
        cMap.put("ptz", "ff.controller.PTZController");
        cMap.put("fireAlarm", "ff.controller.FireAlarmController");
        //循环控制器中的所有方法
        Iterator keyIterator = cMap.keySet().iterator();
        while (keyIterator.hasNext()) {
            try {
                String key = (String) keyIterator.next();
                logger.info(key);
                Class cls = Class.forName(cMap.get(key));
                logger.info(cls.getName());
                //查看表中有无此控制器记录，如果没有则插入记录
                SysController sysController = sysControllerDao.getSysControllerByName(key);
                if (sysController == null) {
                    sysController = new SysController();
                    sysController.setName(key.toLowerCase());
                    sysControllerService.saveOrUpdate(sysController);
                }

                Method[] methods = cls.getMethods();//得到某类的所有Public方法,getDeclaredMethods() 
                for (Method method : methods) {
                    System.out.println(method.getName() + "  " + method.getReturnType().getName() + " " + method.getModifiers());
                    logger.info(method.getName() + "  " + method.getReturnType().getName() + " " + method.getModifiers());
                    //只有本类的方法，才加入。
                    if (method.getDeclaringClass().getName().equals(cls.getName())) {
                        //排除掉注入方法
                        if (method.getName().indexOf("set") == -1 && method.getName().indexOf("Service") == -1) {
                            logger.info("method.getName():" + method.getName());
                            SysAction sysAction = sysActionDao.getSysActionByNameCId(method.getName(), sysController.getId());
                            if (sysAction == null) {
                                sysAction = new SysAction();
                                sysAction.setName(method.getName());
                                sysAction.setSysControllerId(sysController.getId());
                                sysActionService.saveOrUpdate(sysAction);
                            }
                        }
                    }
                }
            } catch (ClassNotFoundException ex) {
                logger.info(ex);
            }
        }
        logger.info("My Class +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        ModelAndView mav = new ModelAndView();
        return mav;
    }

    public ModelAndView test(HttpServletRequest request, HttpServletResponse response) {
        logger.info("test page ++++++++++++++++++++++++++++++++++++++++++++++++++++");
        ModelAndView mav = new ModelAndView();
        return mav;
    }

    public void testQueue(HttpServletRequest request, HttpServletResponse response) {
        Date dt = new Date();
        Long time = dt.getTime();//这就是距离1970年1月1日0点0分0秒的毫秒数
        System.out.println("time:" + time + ", SECOND...............................");
    }
    
    public ModelAndView sound(HttpServletRequest request, HttpServletResponse response) {
        logger.info("sound page ++++++++++++++++++++++++++++++++++++++++++++++++++++");
        ModelAndView mav = new ModelAndView();
        return mav;
    }
}
