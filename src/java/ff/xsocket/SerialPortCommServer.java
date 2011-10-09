/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.xsocket;

import java.util.HashMap;
import java.util.Map;

import org.xsocket.connection.INonBlockingConnection;

import java.io.IOException;
import java.util.Date;
import org.apache.log4j.Logger;

/**
 *
 * @author jerry
 * 保持与串口的连接，并发送各种命令
 */
public class SerialPortCommServer {

    static Logger logger = Logger.getLogger(SerialPortCommServer.class.getName());
    private static Map<String, INonBlockingConnection> connectionMap = new HashMap<String, INonBlockingConnection>();
    private static Map<String, String> headInfoMap = new HashMap<String, String>();

    public SerialPortCommServer() {
        System.out.println("SerialPortCommServer已经被初始化。。。。。。。。。。" + new Date() + ",SerialPortCommServer hashCode:" + SerialPortCommServer.class.hashCode() + ",实例HashCode:" + this.hashCode() + ",this类名:" + this.getClass().getName());
    }

    public void addConnection(String ip, INonBlockingConnection nbc) {
        System.out.println("++++++++ addConnection.............................................................................................................." + ip);
        System.out.println("++++++++ connectionMap.isEmpty():" + connectionMap.isEmpty());
        connectionMap.put(ip, nbc);
    }

    public INonBlockingConnection getConnection(String ip) {
        return connectionMap.get(ip);
    }

    public void removeConnection(String ip) {
        connectionMap.remove(ip);
    }

    public void removeConnectionAll() {
        connectionMap.clear();
    }

    public void addHeadInfo(String ip, String headInfo) {
        headInfoMap.put(ip, headInfo);
    }

    public String getHeadInfo(String ip) {
        return headInfoMap.get(ip);
    }

    public void removeHeadInfo(String ip) {
        headInfoMap.remove(ip);
    }

    public void removeHeadInfoAll() {
        headInfoMap.clear();
    }

    // 向客户端发送十六进制指令
    public boolean sendCommand(INonBlockingConnection connection, String command)
            throws IOException {
        if (connection != null && connection.isOpen()) {
            byte[] c = hexString2ByteArray(command);
            connection.write(c);
            connection.flush();
            return true;
        }
        return false;
    }

    // 向指定的云台，发送十六进制指令
    public boolean sendCommand(String ip, String command) throws IOException {
        //检查connectionMap是否为空
        System.out.println("------------ sendCommand--------------------------，" + ip + ",command:" + command + "--------------------------------------------");
        System.out.println("------------ connectionMap.isEmpty():" + connectionMap.isEmpty());
        INonBlockingConnection connection = connectionMap.get(ip);
        return sendCommand(connection, command);
    }

    // 向客户端发送文本信息
    public boolean sendMsg(INonBlockingConnection connection, String msg)
            throws IOException {
        if (connection != null && connection.isOpen()) {
            connection.write(msg);
            connection.flush();
            return true;
        }
        return false;
    }

    // 向客户端发送文本信息
    public boolean sendMsg(String ip, String msg) throws IOException {
        INonBlockingConnection connection = connectionMap.get(ip);
        return sendMsg(connection, msg);
    }

    // 向flex客户端发送云台信息
    public boolean sendHeadInfo(INonBlockingConnection connection, String headIp)
            throws IOException {
        if (connection != null && connection.isOpen()) {
            String headInfo = getHeadInfo(headIp);

            if (headInfo != null) {
                connection.write(headInfo);
                connection.flush();
                return true;
            }
        }
        return false;
    }

    // 向flex客户端发送云台信息
    public boolean sendHeadInfo(String ip, String headIp) throws IOException {
        INonBlockingConnection connection = connectionMap.get(ip);
        return sendHeadInfo(connection, headIp);
    }

    // 将byte[]转换为十六进制字符串
    public String byteArray2HexString(byte[] b) {
        String s = "";
        if (b != null && b.length > 0) {
            for (int i : b) {
                String c = "";
                i = i & 0xff;
                if (i >= 0 && i < 10) {
                    c = String.format("%02d", i);
                } else if (i >= 10 && i < 16) {
                    c = "0" + Integer.toHexString(i);
                } else {
                    c = Integer.toHexString(i);
                }

                s += c.toUpperCase();
            }
        }
        return s;
    }

    // 将十六进制字符串转换为byte[]
    public byte[] hexString2ByteArray(String s) {
        s = s.replaceAll(" ", "").toUpperCase();

        byte[] b = new byte[s.length() / 2];
        for (int i = 0; i < b.length; i++) {
            String t = s.substring(i * 2, i * 2 + 2);
            b[i] = (byte) Integer.parseInt(t, 16);
        }
        return b;
    }
}
