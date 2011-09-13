package ff.controller;

import ff.service.UserService;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 *
 * @author jerry
 */
public class UserController extends MultiActionController {

    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     *作者：Kimi
     *描述：用户列表页面
     *
     */
    public ModelAndView userList(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Fuc............................................k");
        ModelAndView mav = new ModelAndView();
        return mav;
    }

    /**
     *作者：Kimi
     *描述：新建用户页面
     *
     */
    public ModelAndView newUser(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        return mav;
    }

    /**
     *作者：Kimi
     *描述：编辑用户信息页面
     *
     */
    public ModelAndView editUser(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        return mav;
    }

    public ModelAndView editPrivilege(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        return mav;
    }

    /**
     *作者：joey
     *描述：得到用户列表
     */
    public void getAllUsers(HttpServletRequest request, HttpServletResponse response) {
        String jsonStr = userService.getUserList();
        logger.info(jsonStr);
        PrintWriter pw;
        try {
            response.setContentType("text/json; charset=utf-8");
            response.setHeader("Cache-Control", "no-cache");
            pw = response.getWriter();
            pw.write(jsonStr);
            pw.close();
        } catch (IOException e) {
            logger.info(e);
        }
    }

    /**
     *作者：joey
     *描述：创建用户
     */
    public void create(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        Long roleId = Long.valueOf(request.getParameter("roleId"));
        String priority = request.getParameter("priority");
        String jsonStr = userService.createUser(name, password, roleId, priority);
        PrintWriter pw;
        try {
            response.setContentType("text/json; charset=utf-8");
            response.setHeader("Cache-Control", "no-cache");
            pw = response.getWriter();
            pw.write(jsonStr);
            pw.close();
        } catch (IOException e) {
            logger.info(e);
        }
    }

    /**
     *作者：joey
     *描述：删除用户
     */
    public void deleteUser(HttpServletRequest request, HttpServletResponse response) {
        Long id = Long.valueOf(request.getParameter("id"));
        String jsonStr = userService.deleteUser(id);
        PrintWriter pw;
        try {
            response.setContentType("text/json; charset=utf-8");
            response.setHeader("Cache-Control", "no-cache");
            pw = response.getWriter();
            pw.write(jsonStr);
            pw.close();
        } catch (IOException e) {
            logger.info(e);
        }
    }

    /**
     *作者：joey
     *描述：编辑用户
     */
    public void getUserById(HttpServletRequest request, HttpServletResponse response) {
        Long id = Long.valueOf(request.getParameter("id"));
        String jsonStr = userService.getUserById(id);
        logger.info(jsonStr);
        PrintWriter pw;
        try {
            response.setContentType("text/json; charset=utf-8");
            response.setHeader("Cache-Control", "no-cache");
            pw = response.getWriter();
            pw.write(jsonStr);
            pw.close();
        } catch (IOException e) {
            logger.info(e);
        }
    }

    /**
     *作者：joey
     *描述：更新用户
     */
    public void update(HttpServletRequest request, HttpServletResponse response) {
        Long id = Long.valueOf(request.getParameter("id"));
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        Long roleId = Long.valueOf(request.getParameter("roleId"));
        logger.info(roleId);
        PrintWriter pw;
        try {
            logger.info("user update..............................................Begin..........");
            String jsonStr = userService.updateUser(id, name, password, roleId);
            logger.info("user update..............................................");
            logger.info(jsonStr);

            response.setContentType("text/json; charset=utf-8");
            response.setHeader("Cache-Control", "no-cache");
            pw = response.getWriter();
            pw.write(jsonStr);
            pw.close();
        } catch (IOException e) {
            logger.info(e);
        }
    }

    /**
     *作者：joey
     *描述：验证用户登录
     */
    public void validateUser(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String jsonStr = userService.validateUser(name, password);
        if (jsonStr.contains("success:true,info:'success'")) {
            String id = jsonStr.substring(32, jsonStr.length() - 1);
            HttpSession session = request.getSession();
            session.setAttribute("userId", id);                             //在session中放入当前用户的用户名
        }
        PrintWriter pw;
        try {
            response.setContentType("text/json; charset=utf-8");
            response.setHeader("Cache-Control", "no-cache");
            pw = response.getWriter();
            pw.write(jsonStr);
            pw.close();
        } catch (IOException e) {
            logger.info(e);
        }
    }
}