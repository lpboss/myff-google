/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.xsocket;

import java.util.HashMap;
import java.util.Map;

import org.xsocket.connection.IServer;
import org.xsocket.connection.Server;
import org.xsocket.connection.INonBlockingConnection;

import java.io.IOException;
import org.apache.log4j.Logger;

/**
 *
 * @author jerry
 * 保持与串口的连接，并发送各种命令
 */
public class SerialPortCommServerBootstratp {

    static Logger logger = Logger.getLogger(SerialPortCommServerBootstratp.class.getName());
    private static Map<String, INonBlockingConnection> connectionMap = new HashMap<String, INonBlockingConnection>();
    private static Map<String, String> headInfoMap = new HashMap<String, String>();

    public SerialPortCommServerBootstratp(int headServerPort, int alertServerPort, int flexServerPort, int flexAuthServerPort, HeadServerHandler headServerHandler, AlertServerHandler alertServerHandler, FlexServerHandler flexServerHandler, FlexAuthServerHandler flexAuthServerHandler) {
        IServer serverHead = null;
        IServer serverAlert = null;
        IServer serverFlex = null;
        IServer serverFlexAuth = null;

        try {
            //启动云台控制服务
            serverHead = new Server(headServerPort, headServerHandler);
            serverHead.setConnectionTimeoutMillis(10000);
            serverHead.setIdleTimeoutMillis(10000);
            serverHead.start();

            //启动报警检测服务
            serverAlert = new Server(alertServerPort, alertServerHandler);
            serverAlert.setConnectionTimeoutMillis(10000);
            serverAlert.setIdleTimeoutMillis(10000);
            serverAlert.start();

            //启动flex安全沙箱验证服务
            serverFlexAuth = new Server(flexAuthServerPort, flexAuthServerHandler);
            serverFlexAuth.start();

            //启动flex服务
            serverFlex = new Server(flexServerPort, flexServerHandler);
            serverFlex.setIdleTimeoutMillis(10000);
            serverFlex.setConnectionTimeoutMillis(10000);
            serverFlex.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SerialPortCommServerBootstratp(HeadServerHandler headServerHandler) {
        System.out.println("+++++++++++++++++++++++++++++++++++++ , AeadServerHandler:" + headServerHandler);
    }
}
