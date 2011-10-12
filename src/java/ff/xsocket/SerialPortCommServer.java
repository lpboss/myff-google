/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.xsocket;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.xsocket.connection.INonBlockingConnection;

/**
 *
 * @author jerry
 * 保持与串口的连接，并发送各种命令
 */
public class SerialPortCommServer {

    static Logger logger = Logger.getLogger(SerialPortCommServer.class.getName());
    private static Map<String, INonBlockingConnection> connectionMap = new HashMap<String, INonBlockingConnection>();
    private static Map<String, String> angleX = new ConcurrentHashMap<String, String>();
    private static Map<String, String> angleY = new ConcurrentHashMap<String, String>();
    //key为ip,value为true或false，当value为false时，自动巡航方法不再控制巡航
    private static Map<String, Boolean> allowCruise = new ConcurrentHashMap<String, Boolean>();
    //key为ip,value为up或down，告知，当前的云台自动巡航是向上还是向下。以方便判断当角度到达359度时，云台是up还是down
    private static Map<String, String> ptzOrientation = new ConcurrentHashMap<String, String>();
    //所有云台命令,以IP为Key，以Queue为Value。
    private static Map<String, LinkedList> commandMap = new ConcurrentHashMap<String, LinkedList>();

    public void addConnection(String ip, INonBlockingConnection nbc) {
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

    public void setAngleX(String ip, float angle_x) {
        angleX.put(ip, angle_x + "");
    }

    public float getAngleX(String ip) {
        if (angleX.get(ip) != null) {
            return Float.parseFloat(angleX.get(ip));
        } else {
            return 0;
        }
    }

    public void removeAngleX(String ip) {
        angleX.remove(ip);
    }

    public void removeAngleXAll() {
        angleX.clear();
    }

    public void setAngleY(String ip, float angle_y) {
        angleY.put(ip, angle_y + "");
    }

    public float getAngleY(String ip) {
        if (angleY.get(ip) != null) {
            return Float.parseFloat(angleY.get(ip));
        } else {
            return 0;
        }
    }

    public void removeAngleY(String ip) {
        angleY.remove(ip);
    }

    public void removeAngleYAll() {
        angleY.clear();
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
            String headInfo = getAngleX(headIp) + "," + getAngleY(headIp);
            System.out.println("向flex客户端发送信息(" + headIp + "):" + headInfo);
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

    public Map<String, Boolean> getAllowCruise() {
        return allowCruise;
    }

    public Map<String, String> getPtzOrientation() {
        return ptzOrientation;
    }

    /**
     *
     * @author jerry
     * 向命令队列中追加命令。
     */
    public synchronized void pushCommand(String ip, String ptzCommand) {
        LinkedList<String> commandQueue = commandMap.get(ip);
        if (commandQueue == null) {
            commandQueue = new LinkedList<String>();
        }
        commandQueue.addLast(ptzCommand);
        commandMap.put(ip, commandQueue);
    }

    public Map<String, LinkedList> getCommandMap() {
        return commandMap;
    }
    
    
}
