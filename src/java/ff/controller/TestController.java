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
                    sysController.setName(key);
                    sysControllerService.saveOrUpdate(sysController);
                }

                Method[] methods = cls.getMethods();//得到某类的所有Public方法,getDeclaredMethods() 
                for (Method method : methods) {
                    logger.info(method.getName() + "  " + method.getReturnType().getName() + " " + method.getModifiers());
                    //只有本类的方法，才加入。
                    if (method.getDeclaringClass().getName() == cls.getName()) {
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
        LinkedList<String> commandQueue = new LinkedList<String>();
        commandQueue.addLast("1");
        commandQueue.addLast("2");
        commandQueue.addLast("3");
        commandQueue.addLast("4");
        commandQueue.addLast("5");
        System.out.println("commandQueue.getFirst():000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
        System.out.println("commandQueue.getFirst():" + commandQueue.getFirst());
        commandQueue.removeFirst();
        System.out.println("commandQueue.getFirst():" + commandQueue.getFirst());

        Integer command = 32;
        System.out.println("hex command:" + Integer.toHexString(command).toUpperCase());
        System.out.println("dec command:" + command);
        command = 64;
        System.out.println("hex command:" + Integer.toHexString(command));
        System.out.println("dec command:" + command);
        command = 128;
        System.out.println("hex command:" + Integer.toHexString(command));
        System.out.println("dec command:" + command);
        command = 256;
        System.out.println("hex command:" + Integer.toHexString(command));
        System.out.println("dec command:" + command);

        System.out.println("PTZ Buffer String :" + PTZUtil.getPELCODCommandHexString(1, 0, 0x59, 45, 88, "angle"));
        //System.out.println("PTZ Buffer String :" + "1234".substring(0, 2));
        String test = "FF 01 00 4D 18 F3 59";
        System.out.println(test.substring(12, 14));
        System.out.println(Integer.valueOf(test.substring(12, 14), 16));
        
        System.out.println(test.substring(15, 17));
        System.out.println(Integer.valueOf(test.substring(15, 17), 16));
    }
}
