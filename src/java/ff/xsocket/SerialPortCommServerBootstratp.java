/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.xsocket;

import org.apache.log4j.Logger;
import org.xsocket.connection.IServer;
import org.xsocket.connection.Server;

/**
 * 管理各xsocket服务器，以Spring Bean的方式随容器启动而启动，
 *   
 * @author   Jiangshilin
 * @Date     2011-10-17
 */
public class SerialPortCommServerBootstratp {

    static Logger logger = Logger.getLogger(SerialPortCommServerBootstratp.class.getName());

    public SerialPortCommServerBootstratp(int headServerPort, int alertServerPort, int flexServerPort, int flexAuthServerPort, HeadServerHandler headServerHandler, AlertServerHandler alertServerHandler, FlexServerHandler flexServerHandler, FlexAuthServerHandler flexAuthServerHandler) {
        IServer serverHead = null;
        IServer serverAlert = null;
        IServer serverFlex = null;
        IServer serverFlexAuth = null;

        try {
            //启动云台控制服务
            serverHead = new Server(headServerPort, headServerHandler);
            serverHead.start();

            //启动报警检测服务
            serverAlert = new Server(alertServerPort, alertServerHandler);
            serverAlert.start();

            //启动flex安全沙箱验证服务
            serverFlexAuth = new Server(flexAuthServerPort, flexAuthServerHandler);
            serverFlexAuth.start();

            //启动flex服务
            serverFlex = new Server(flexServerPort, flexServerHandler);
            serverFlex.setIdleTimeoutMillis(60000);
            serverFlex.setConnectionTimeoutMillis(3600000);
            serverFlex.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
