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
import org.xsocket.connection.IServer;
import org.xsocket.connection.Server;

/**
 * 红外摄像头报警信息回传及参数设置服务器
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
    public boolean onConnect(INonBlockingConnection connection) throws IOException,
            BufferUnderflowException, MaxReadSizeExceededException {

        String ip = connection.getRemoteAddress().getHostAddress();
        serialPortCommServer.addConnection(ip, connection);

        System.out.println("云台控制客户端(" + ip + ":" + connection.getLocalPort() + ")已连接！");

        //发送云台水平角度查询命令
        //serialPortCommServer.sendCommand(connection, "FF 01 00 04 30 00 35");//水平转动
        //serialPortCommServer.sendCommand(connection, "FF 01 00 00 00 00 01");//停止
        serialPortCommServer.sendCommand(connection, "FF 01 00 51 00 00 52");

        return true;
    }

    /**
     * 即如果失去连接应当如何处理？ 需要实现 IDisconnectHandler 这个接口 连接断开时的操作
     */
    @Override
    public boolean onDisconnect(INonBlockingConnection connection) throws IOException {
        String ip = connection.getRemoteAddress().getHostAddress();
        serialPortCommServer.removeConnection(ip);
        System.out.println("onDisconnect");
        return false;
    }

    /**
     * 即这个方法不光是说当接收到一个新的网络包的时候会调用而且如果有新的缓存存在的时候也会被调用。而且 当连接被关闭的时候也会被调用的!
     */
    @Override
    public boolean onData(INonBlockingConnection connection) throws IOException,
            BufferUnderflowException, ClosedChannelException,
            MaxReadSizeExceededException {
        String ip = connection.getRemoteAddress().getHostAddress();

        ByteBuffer buffer = ByteBuffer.allocate(7);
        connection.read(buffer);
        byte[] b = buffer.array();

        String s = serialPortCommServer.byteArray2HexString(b);
        System.out.println(s);
        if (s.indexOf("FF010059") > -1) {
            float angle_x = (float) Integer.parseInt(s.substring(8, 12), 16) / 100;
            serialPortCommServer.setAngleX(ip, angle_x);

            System.out.println(angle_x);
        }


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //发送云台水平角度查询命令
        serialPortCommServer.sendCommand(connection, "FF 01 00 51 00 00 52");

        return true;
    }

    /**
     * 请求处理超时的处理事件
     */
    @Override
    public boolean onIdleTimeout(INonBlockingConnection connection)
            throws IOException {
        System.out.println("onIdleTimeout");

        return false;
    }

    /**
     * 连接超时处理事件
     */
    @Override
    public boolean onConnectionTimeout(INonBlockingConnection connection)
            throws IOException {
        System.out.println("onConnectionTimeout");

        return false;
    }

}