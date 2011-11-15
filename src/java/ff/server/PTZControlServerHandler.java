package ff.server;

import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.xsocket.MaxReadSizeExceededException;
import org.xsocket.connection.IConnectHandler;
import org.xsocket.connection.IConnectionTimeoutHandler;
import org.xsocket.connection.IDataHandler;
import org.xsocket.connection.IDisconnectHandler;
import org.xsocket.connection.IIdleTimeoutHandler;
import org.xsocket.connection.INonBlockingConnection;

/**
 * 云台控制及角度回传服务器
 *   
 * @author   Jiangshilin
 * @Date     2011-10-17
 */
public class PTZControlServerHandler implements IDataHandler, IConnectHandler,
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

        System.out.println("云台控制客户端(" + ip + ":" + connection.getLocalPort()
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
            System.out.println("云台控制客户端(" + ip + ":"
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

            //接收从云台发送的角度信息
            ByteBuffer buffer = ByteBuffer.allocate(7);
            connection.read(buffer);
            byte[] b = buffer.array();
            String s = serialPortCommServer.byteArray2HexString(b);

            //added by Jerry 2011-11-14
            Calendar calendar = Calendar.getInstance();
            long milliseconds = calendar.getTimeInMillis();
            SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss SSS");
            Date date = new Date(milliseconds);
            //System.out.println("打印时实传回的角度信息：" + s + ",Date:" + timeFormat.format(date) + ",==================================");
            if (s.indexOf("FF010059") > -1) {//水平角度信息回传
                //System.out.println("打印时实传回的云台水平角度信息：" + s + ",Date:" + timeFormat.format(date));
                float angle_x = 0;
                try {
                    angle_x = (float) Integer.parseInt(s.substring(s.indexOf("FF010059") + 8, s.indexOf("FF010059") + 12), 16) / 100;
                } catch (Exception e) {
                    System.out.println("打印解析传回的云台水平角度信息时发生的错误：" + e);
                }
                serialPortCommServer.setAngleX(ip, angle_x);

                //System.out.println("云台水平角度：" + serialPortCommServer.getAngleXString(ip));
            } else if (s.indexOf("FF01005B") > -1) {//垂直角度信息回传
                //System.out.println("打印时实传回的云台垂直角度信息：" + s + ",Date:" + timeFormat.format(date));
                float angle_y = 0f;
                int y = 0;
                try {
                    y = Integer.parseInt(s.substring(s.indexOf("FF01005B") + 8, s.indexOf("FF01005B") + 12), 16);
                } catch (Exception e) {
                    System.out.println("打印解析传回的云台垂直角度信息时发生的错误：" + e);
                }
                if (y < 18000) {
                    angle_y = 0 - (float) y / 100;
                } else if (y > 18000) {
                    angle_y = (float) (36000 - y) / 100;
                }
                serialPortCommServer.setAngleY(ip, angle_y);

                //System.out.println("云台垂直角度：" + serialPortCommServer.getAngleY(ip));
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
            System.out.println("云台控制客户端(" + ip + ":"
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
            System.out.println("云台控制客户端(" + ip + ":"
                    + connection.getLocalPort() + ")连接超时！");
        }

        return false;
    }
}