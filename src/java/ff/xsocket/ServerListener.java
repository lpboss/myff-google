package ff.xsocket;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.xsocket.connection.IServer;
import org.xsocket.connection.Server;

public class ServerListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent event) {

        int headServerPort = Integer.parseInt(event.getServletContext().getInitParameter("headServerPort"));
        int alertServerPort = Integer.parseInt(event.getServletContext().getInitParameter("alertServerPort"));
        int flexServerPort = Integer.parseInt(event.getServletContext().getInitParameter("flexServerPort"));
        int flexAuthServerPort = Integer.parseInt(event.getServletContext().getInitParameter("flexAuthServerPort"));

        IServer server_head = null;
        IServer server_alert = null;
        IServer server_flex = null;
        IServer server_flexAuth = null;

        try {
            //启动云台控制服务
            server_head = new Server(headServerPort, new HeadServerHandler());
            server_head.setConnectionTimeoutMillis(10000);
            server_head.setIdleTimeoutMillis(10000);
            server_head.start();

            //启动报警检测服务
            server_alert = new Server(alertServerPort, new AlertServerHandler());
            server_alert.setConnectionTimeoutMillis(10000);
            server_alert.setIdleTimeoutMillis(10000);
            server_alert.start();

            //启动flex安全沙箱验证服务
            server_flexAuth = new Server(flexAuthServerPort, new FlexAuthServerHandler());
            server_flexAuth.start();

            //启动flex服务
            server_flex = new Server(flexServerPort, new FlexServerHandler());
            server_flex.setIdleTimeoutMillis(10000);
            server_flex.setConnectionTimeoutMillis(10000);
            server_flex.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
    }
}