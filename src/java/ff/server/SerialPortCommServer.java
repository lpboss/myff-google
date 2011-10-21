/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.server;

import java.io.IOException;
import java.text.DecimalFormat;
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
    //key为ip,value为true或false，当value为false时，自动巡航方法不再控制巡航。主要是标记哪些云台当前允许其实自动巡航。
    private static Map<String, Boolean> allowCruise = new ConcurrentHashMap<String, Boolean>();
    //key为ip,value为true或false，当value为false时，当前正在巡航的云台。当前正在巡航的云台，不会重复发送巡航右转命令。以减少命令发送量。
    private static Map<String, Boolean> isCruising = new ConcurrentHashMap<String, Boolean>();
    //为巡航，比如削苹果皮等准备。其中Key为ip.value为预置的Y角度，只有达到此角度时才接受其它命令。
    private static Map<String, Integer> isCruisingPresetAngleY = new ConcurrentHashMap<String, Integer>();
    //为了修正在回到断点云台不到位的情况。加上命令调整时间，如果超过1秒，就再发送一次，相同的命令。值为毫秒数。
    private static Map<String, Integer> breakPointReturnBeginTime = new ConcurrentHashMap<String, Integer>();
    //巡航断点，用来记录巡航人为停止时的XY角度。Key为IP,Value为（X|Y）记录二个角度。
    private static Map<String, String> cruiseBreakpoint = new ConcurrentHashMap<String, String>();
    //标记正在为回到断点，继续巡航的云台，提供回到断点状态位的标志。如果正在回到断点则不再发送调整命令。    
    private static Map<String, Boolean> isAdjustingXYForBreakpoint = new ConcurrentHashMap<String, Boolean>();
    //key为ip,value为up或down，告知，当前的云台自动巡航是向上还是向下。以方便判断当角度到达359度时，云台是up还是down
    private static Map<String, String> ptzOrientation = new ConcurrentHashMap<String, String>();
    //所有云台命令,以IP为Key，以Queue为Value。
    private static Map<String, LinkedList> commandMap = new ConcurrentHashMap<String, LinkedList>();
    DecimalFormat df = new DecimalFormat("0.00");

    /**
     * 将socket连接对象以客户端ip地址为key，放入HashMap保存
     * @param ip
     * @param nbc
     * @throws
     */
    public void addConnection(String ip, INonBlockingConnection nbc) {
        connectionMap.put(ip, nbc);
    }

    /**
     * 根据客户端ip地址，从HashMap获取socket连接对象
     * @param ip
     * @return
     * @throws
     */
    public INonBlockingConnection getConnection(String ip) {
        return connectionMap.get(ip);
    }

    /**
     * 根据客户端ip地址，从HashMap删除socket连接对象
     * @param ip
     * @throws
     */
    public void removeConnection(String ip) {
        connectionMap.remove(ip);
    }

    /**
     * 删除HashMap中所有的socket连接对象
     * 
     * @throws
     */
    public void removeConnectionAll() {
        connectionMap.clear();
    }

    /**
     * 以云台ip为key，将云台水平角度信息存放在HashMap中
     * @param ip
     * @param angle_x
     * @throws
     */
    public void setAngleX(String ip, float angle_x) {
        angleX.put(ip, df.format(angle_x));
    }

    /**
     * 根据云台ip，获取云台水平角度（浮点型小数）
     * @param ip
     * @return
     * @throws
     */
    public float getAngleX(String ip) {
        if (angleX.get(ip) != null) {
            return Float.parseFloat(angleX.get(ip));
        } else {
            return 0;
        }
    }

    /**
     * 根据云台ip，获取云台水平角度（保留两位小数的格式化字符串）
     * @param ip
     * @return
     * @throws
     */
    public String getAngleXString(String ip) {
        if (angleX.get(ip) != null) {
            return angleX.get(ip);
        } else {
            return "0.00";
        }
    }

    /**
     * 根据云台ip，删除其水平角度信息
     * @param ip
     * @throws
     */
    public void removeAngleX(String ip) {
        angleX.remove(ip);
    }

    /**
     * 删除所有云台的水平角度信息
     * 
     * @throws
     */
    public void removeAngleXAll() {
        angleX.clear();
    }

    /**
     * 以云台ip为key，将云台垂直角度信息存放在HashMap中
     * @param ip
     * @param angle_y
     * @throws
     */
    public void setAngleY(String ip, float angle_y) {
        angleY.put(ip, df.format(angle_y));
    }

    /**
     * 根据云台ip，获取云台垂直角度（浮点型小数）
     * @param ip
     * @return
     * @throws
     */
    public float getAngleY(String ip) {
        if (angleY.get(ip) != null) {
            return Float.parseFloat(angleY.get(ip));
        } else {
            return 0;
        }
    }

    /**
     * 根据云台ip，获取云台垂直角度（保留两位小数的格式化字符串）
     * @param ip
     * @return
     * @throws
     */
    public String getAngleYString(String ip) {
        if (angleY.get(ip) != null) {
            return angleY.get(ip);
        } else {
            return "0.00";
        }
    }

    /**
     * 根据云台ip，删除其垂直角度信息
     * @param ip
     * @throws
     */
    public void removeAngleY(String ip) {
        angleY.remove(ip);
    }

    /**
     * 删除所有云台的垂直角度信息
     * 
     * @throws
     */
    public void removeAngleYAll() {
        angleY.clear();
    }

    /**
     * 向客户端发送十六进制指令
     * @param connection
     * @param command
     * @return
     * @throws IOException
     * @throws
     */
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

    /**
     * 向指定的云台，发送十六进制指令
     * @param ip
     * @param command
     * @return
     * @throws IOException
     * @throws
     */
    public boolean sendCommand(String ip, String command) throws IOException {
        INonBlockingConnection connection = connectionMap.get(ip);
        return sendCommand(connection, command);
    }

    /**
     * 向客户端发送文本信息
     * @param connection
     * @param msg
     * @return
     * @throws IOException
     * @throws
     */
    public boolean sendMsg(INonBlockingConnection connection, String msg)
            throws IOException {
        if (connection != null && connection.isOpen()) {
            connection.write(msg);
            connection.flush();
            return true;
        }
        return false;
    }

    /**
     * 向客户端发送文本信息
     * @param ip
     * @param msg
     * @return
     * @throws IOException
     * @throws
     */
    public boolean sendMsg(String ip, String msg) throws IOException {
        INonBlockingConnection connection = connectionMap.get(ip);
        return sendMsg(connection, msg);
    }

    /**
     * 向flex客户端发送云台信息
     * @param connection
     * @param headIp
     * @return
     * @throws IOException
     * @throws
     */
    public boolean sendHeadInfo(INonBlockingConnection connection, String headIp)
            throws IOException {
        if (connection != null && connection.isOpen()) {
            String headInfo = getAngleX(headIp) + "," + getAngleY(headIp);
            //System.out.println("向flex客户端发送信息(" + headIp + "):" + headInfo);
            if (headInfo != null) {
                connection.write(headInfo);
                connection.flush();
                return true;
            }
        }
        return false;
    }

    /**
     *  向flex客户端发送云台信息
     * @param ip
     * @param headIp
     * @return
     * @throws IOException
     * @throws
     */
    public boolean sendHeadInfo(String ip, String headIp) throws IOException {
        INonBlockingConnection connection = connectionMap.get(ip);
        return sendHeadInfo(connection, headIp);
    }

    /**
     * 将byte[]转换为十六进制字符串
     * @param b
     * @return
     * @throws
     */
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

    /**
     * 将十六进制字符串转换为byte[]
     * @param s
     * @return
     * @throws
     */
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
     * 向命令队列中追加命令
     * @param ip
     * @param ptzCommand
     * @throws
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

    public Map<String, Boolean> getIsCruising() {
        return isCruising;
    }

    public Map<String, Integer> getIsCruisingPresetAngleY() {
        return isCruisingPresetAngleY;
    }

    public Map<String, String> getCruiseBreakpoint() {
        return cruiseBreakpoint;
    }

    public Map<String, Boolean> getIsAdjustingXYForBreakpoint() {
        return isAdjustingXYForBreakpoint;
    }

    public Map<String, Integer> getBreakPointReturnBeginTime() {
        return breakPointReturnBeginTime;
    }
}
