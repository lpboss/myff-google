package ff.server;

import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;

import org.xsocket.MaxReadSizeExceededException;
import org.xsocket.connection.IConnectHandler;
import org.xsocket.connection.IConnectionTimeoutHandler;
import org.xsocket.connection.IDataHandler;
import org.xsocket.connection.IDisconnectHandler;
import org.xsocket.connection.IIdleTimeoutHandler;
import org.xsocket.connection.INonBlockingConnection;

/**
 * 热成像传感器报警信息回传及参数设置服务器
 *   
 * @author   Jiangshilin
 * @Date     2011-10-17
 */
public class PTZAlertServerHandler implements IDataHandler, IConnectHandler,
        IIdleTimeoutHandler, IConnectionTimeoutHandler, IDisconnectHandler {

    private SerialPortCommServer serialPortCommServer;

    public void setSerialPortCommServer(SerialPortCommServer serialPortCommServer) {
        this.serialPortCommServer = serialPortCommServer;
    }

    /**
     * 即当建立完连接之后可以进行的一些相关操作处理。包括修改连接属性、准备资源、等！ 连接的成功时的操作
     */
    @Override
    public boolean onConnect(INonBlockingConnection connection)
            throws IOException, BufferUnderflowException,
            MaxReadSizeExceededException {

        String ip = connection.getRemoteAddress().getHostAddress();
        serialPortCommServer.addConnection(ip, connection);

        System.out.println("热成像传感器报警客户端(" + ip + ":" + connection.getLocalPort()
                + ")已连接！");

        return true;
    }

    /**
     * 即如果失去连接应当如何处理？ 需要实现 IDisconnectHandler 这个接口 连接断开时的操作
     */
    @Override
    public boolean onDisconnect(INonBlockingConnection connection)
            throws IOException {
        if (connection != null && connection.isOpen()) {
            String ip = connection.getRemoteAddress().getHostAddress();
            serialPortCommServer.removeConnection(ip);
            System.out.println("热成像传感器报警(" + ip + ":"
                    + connection.getLocalPort() + ")已断开！");
        }
        return false;
    }

    /**
     * 即这个方法不光是说当接收到一个新的网络包的时候会调用而且如果有新的缓存存在的时候也会被调用。而且 当连接被关闭的时候也会被调用的!
     */
    @Override
    public boolean onData(INonBlockingConnection connection)
            throws IOException, BufferUnderflowException,
            ClosedChannelException, MaxReadSizeExceededException {
        if (connection != null && connection.isOpen()) {
            String ip = connection.getRemoteAddress().getHostAddress();

            //接收从热成像传感器发送的报警信息
            ByteBuffer buffer = ByteBuffer.allocate(14);
            connection.read(buffer);
            byte[] b = buffer.array();
            String s = serialPortCommServer.byteArray2HexString(b);

            //处理回传数据，详见数据格式文档
            if (s.indexOf("7F7F") == 0 && s.lastIndexOf("7F7F") == 24) {
                //System.out.println("热成像传感器报警信息回传：" +s);
                int degree_avg = serialPortCommServer.hexString2Int(s.substring(6, 8) + s.substring(4, 6));
                int degree_max = serialPortCommServer.hexString2Int(s.substring(10, 12) + s.substring(8, 10));
                int degree_min = serialPortCommServer.hexString2Int(s.substring(22, 24) + s.substring(20, 22));
                //列数x
                int x = Integer.parseInt(s.substring(18, 20) + s.substring(16, 18), 16);
                //行数y
                int y = Integer.parseInt(s.substring(14, 16) + s.substring(12, 14), 16);

                serialPortCommServer.setAlert(ip, degree_max, degree_min, degree_avg, x, y);

                System.out.println("热成像传感器报警信息解析，平均值：" + degree_avg + "，最大值：" + degree_max + "，最小值：" + degree_min + "，列X：" + x + "，行Y：" + y);
            }
        }
        return true;
    }

    /**
     * 请求处理超时的处理事件
     */
    @Override
    public boolean onIdleTimeout(INonBlockingConnection connection)
            throws IOException {
        if (connection != null && connection.isOpen()) {
            String ip = connection.getRemoteAddress().getHostAddress();
            System.out.println("热成像传感器报警(" + ip + ":"
                    + connection.getLocalPort() + ")请求处理超时！");
        }

        return false;
    }

    /**
     * 连接超时处理事件
     */
    @Override
    public boolean onConnectionTimeout(INonBlockingConnection connection)
            throws IOException {
        if (connection != null && connection.isOpen()) {
            String ip = connection.getRemoteAddress().getHostAddress();
            System.out.println("热成像传感器报警(" + ip + ":"
                    + connection.getLocalPort() + ")连接超时！");
        }

        return false;
    }
}