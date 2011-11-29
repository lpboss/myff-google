/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.server;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.xsocket.connection.INonBlockingConnection;

/**
 * 
 * @author jerry 保持与串口的连接，并发送各种命令
 */
public class SerialPortCommServer {

    static Logger logger = Logger.getLogger(SerialPortCommServer.class.getName());
    //客户端连接池，key为客户端ip，value为连接对象
    private static Map<String, INonBlockingConnection> connectionMap = new HashMap<String, INonBlockingConnection>();
    //存放热成像报警值，key为热成像传感器串口服务器ip，value为报警值数组，顺序分别为最大值、最小值、平均值、最大值行、最大值列
    private static Map<String, int[]> alertMap = new ConcurrentHashMap<String, int[]>();
    //存放客户端警报，key为热成像传感器串口服务器ip，value为云台控制串口服务器ip
    private static Map<String, String> alarmMap = new ConcurrentHashMap<String, String>();
    //云台水平角度，key为云台ip，value为角度值
    private static Map<String, String> angleX = new ConcurrentHashMap<String, String>();
    //云台垂直角度，key为云台ip，value为角度值
    private static Map<String, String> angleY = new ConcurrentHashMap<String, String>();
    // key为ip,value为true或false，当value为false时，自动巡航方法不再控制巡航。主要是标记哪些云台当前允许其实自动巡航。
    private static Map<String, Boolean> allowCruise = new ConcurrentHashMap<String, Boolean>();
    //巡航方向,由于设定了巡航限制，所有有left,right,loop几种巡航方向,key为ptzid
    private static Map<Long, String> cruiseDirection = new ConcurrentHashMap<Long, String>();
    // key为ip,value为true或false，当value为false时。主要是标记哪些云台当前为允许对火警作出反应为null或true都可以反应为false时不允许再次对火警反应。
    private static Map<String, Boolean> allowAlarm = new ConcurrentHashMap<String, Boolean>();
    // key为ip,value为true或false，当value为false时，当前正在巡航的云台。当前正在巡航的云台，不会重复发送巡航右转命令。以减少命令发送量。
    private static Map<String, Boolean> isCruising = new ConcurrentHashMap<String, Boolean>();
    // 为巡航，比如削苹果皮等准备。其中Key为ip.value为预置的Y角度，只有达到此角度时才接受其它命令。
    private static Map<String, Integer> isCruisingPresetAngleY = new ConcurrentHashMap<String, Integer>();
    //火警时，如果正在调整起火点到中心位置，则以下变量中的状态为true;
    private static Map<String, Boolean> isMovingCenterForFireAlarm = new ConcurrentHashMap<String, Boolean>();
    //火警时，当场保存的起火方位与热值，时间。由于火警地点随时变化，所以这里只记录最初的方位。Key为IP，值为：X|Y|AngleX|AngleY|MaxValue|Time
    private static Map<String, String> sceneFireAlarmInfo = new ConcurrentHashMap<String, String>();
    //火警时，最终到调整到的中心角度，其中的值格式为X1|X1Command|Y1|Y1Command，X1，Y1代表水平，垂直角度。
    private static Map<String, String> finalMovingCenterForFireAlarm = new ConcurrentHashMap<String, String>();
    // 为了修正在回到断点云台不到位的情况。加上命令调整时间，如果超过1秒，就再发送一次，相同的命令。值为毫秒数。
    private static Map<String, Long> breakPointReturnBeginTime = new ConcurrentHashMap<String, Long>();
    // 巡航断点，用来记录巡航人为停止时的XY角度。Key为IP,Value为（X|Y）记录二个角度。
    private static Map<String, String> cruiseBreakpoint = new ConcurrentHashMap<String, String>();
    // 标记正在为回到断点，继续巡航的云台，提供回到断点状态位的标志。如果正在回到断点则不再发送调整命令。
    private static Map<String, Boolean> isAdjustingXYForBreakpoint = new ConcurrentHashMap<String, Boolean>();
    // key为ip,value为up或down，告知，当前的云台自动巡航是向上还是向下。以方便判断当角度到达359度时，云台是up还是down
    private static Map<String, String> ptzOrientation = new ConcurrentHashMap<String, String>();
    // 所有云台命令,以IP为Key，以Queue为Value。
    private static Map<String, LinkedList> commandMap = new ConcurrentHashMap<String, LinkedList>();
    DecimalFormat df = new DecimalFormat("0.00");

    /**
     * 将socket连接对象以客户端ip地址为key，放入HashMap保存
     * 
     * @param ip
     * @param nbc
     * @throws
     */
    public void addConnection(String ip, INonBlockingConnection nbc) {
        connectionMap.put(ip, nbc);
    }

    /**
     * 根据客户端ip地址，从HashMap获取socket连接对象
     * 
     * @param ip
     * @return
     * @throws
     */
    public INonBlockingConnection getConnection(String ip) {
        return connectionMap.get(ip);
    }

    /**
     * 根据客户端ip地址，从HashMap删除socket连接对象
     * 
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
    public void removeConnection() {
        connectionMap.clear();
    }

    /**
     * 以热成像仪传感器ip为key，保存热成像仪传感器回传数据至Hashmap，保存对象为int数组
     * 
     * @param ip
     * @param degree_max
     *            最大灰度值
     * @param degree_min
     *            最小灰度值
     * @param degree_avg
     *            平均值灰度值
     * @param x
     *            最大灰度值对应列
     * @param y
     *            最大灰度值对应行    
     * @throws
     */
    public void setAlert(String ip, int degree_max, int degree_min,
            int degree_avg, int x, int y) {
        int[] alert = {degree_max, degree_min, degree_avg, x, y};
        alertMap.put(ip, alert);
    }

    /**
     * 根据热成像仪传感器ip获取当前的报警值信息
     * @param ip
     * @return 以int数组的形式回传，顺序分别是 最大值、最小值、平均值、最大值行、最大值列
     * @throws
     */
    public int[] getAlert(String ip) {
        return alertMap.get(ip);
    }

    /**
     * 根据热成像仪传感器ip，获取最大灰度值
     * 
     * @param ip
     * @return
     * @throws
     */
    public int getAlertMax(String ip) {
        int[] alert = alertMap.get(ip);
        if (alert != null) {
            return alert[0];
        }
        return 0;
    }

    /**
     * 根据热成像仪传感器ip，获取最小灰度值
     * 
     * @param ip
     * @return
     * @throws
     */
    public int getAlertMin(String ip) {
        int[] alert = alertMap.get(ip);
        if (alert != null) {
            return alert[1];
        }
        return 0;
    }

    /**
     * 根据热成像仪传感器ip，获取平均灰度值
     * 
     * @param ip
     * @return
     * @throws
     */
    public int getAlertAvg(String ip) {
        int[] alert = alertMap.get(ip);
        if (alert != null) {
            return alert[2];
        }
        return 0;
    }

    /**
     * 根据热成像仪传感器ip，获取最大灰度值对应行
     * 
     * @param ip
     * @return
     * @throws
     */
    public int getAlertY(String ip) {
        int[] alert = alertMap.get(ip);
        if (alert != null) {
            return alert[4];
        }
        return 0;
    }

    /**
     * 根据热成像仪传感器ip，获取最大灰度值对应列
     * 
     * @param ip
     * @return
     * @throws
     */
    public int getAlertX(String ip) {
        int[] alert = alertMap.get(ip);
        if (alert != null) {
            return alert[3];
        }
        return 0;
    }

    /**
     * 检查某个云台节点是否警报
     * @param alertIP
     * @param ptzIP
     * @return ture 警报；false 正常
     * @throws
     */
    public boolean isAlarm(String alertIP, String ptzIP) {
        if (alarmMap.get(alertIP) != null) {
            return true;
        }
        return false;
    }

    /**
     * 检查所有云台是否有警报的
     * @return ture 警报；false 正常
     * @throws
     */
    public boolean isAlarm() {
        if (!alarmMap.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * 以云台ip为key，将云台水平角度信息存放在HashMap中
     * 
     * @param ip
     * @param angle_x
     * @throws
     */
    public void setAngleX(String ip, float angle_x) {
        angleX.put(ip, df.format(angle_x));
    }

    /**
     * 根据云台ip，获取云台水平角度（浮点型小数）
     * 
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
     * 
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
     * 
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
    public void removeAngleX() {
        angleX.clear();
    }

    /**
     * 以云台ip为key，将云台垂直角度信息存放在HashMap中
     * 
     * @param ip
     * @param angle_y
     * @throws
     */
    public void setAngleY(String ip, float angle_y) {
        angleY.put(ip, df.format(angle_y));
    }

    /**
     * 根据云台ip，获取云台垂直角度（浮点型小数）
     * 
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
     * 
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
     * 
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
    public void removeAngleY() {
        angleY.clear();
    }

    /**
     * 向客户端发送十六进制指令
     * 
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
     * 
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
     * 
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
     * 
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
     * 向flex客户端发送云台信息，xml格式
     * 
     * @param connection
     * @param headIp
     * @return
     * @throws IOException
     * @throws
     */
    public boolean sendHeadInfo(INonBlockingConnection connection,
            String ptzIp, String alertIp) throws IOException {
        if (connection != null && connection.isOpen()) {
            // 以xml格式发送
            String headInfo = "<xml><angleX>" + getAngleX(ptzIp)
                    + "</angleX><angleY>" + getAngleY(ptzIp)
                    + "</angleY><alertMax>" + getAlertMax(alertIp)
                    + "</alertMax><alertMin>" + getAlertMin(alertIp)
                    + "</alertMin><alertAvg>" + getAlertAvg(alertIp)
                    + "</alertAvg><alertX>" + getAlertX(alertIp)
                    + "</alertX><alertY>" + getAlertY(alertIp)
                    + "</alertY><myAlarm>" + isAlarm(alertIp, ptzIp)
                    + "</myAlarm><globalAlarm>" + isAlarm()
                    + "</globalAlarm></xml>";

            //System.out.println("向flex客户端发送信息:" + headInfo);
            if (headInfo != null) {
                connection.write(headInfo);
                connection.flush();
                return true;
            }
        }
        return false;
    }

    /**
     * 向flex客户端发送云台信息
     * 
     * @param ip
     * @param headIp
     * @return
     * @throws IOException
     * @throws
     */
    public boolean sendHeadInfo(String ip, String ptzIp, String alertIp)
            throws IOException {
        INonBlockingConnection connection = connectionMap.get(ip);
        return sendHeadInfo(connection, ptzIp, alertIp);
    }

    /**
     * 将byte[]转换为十六进制字符串
     * 
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
     * 
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

    /**
     * 将一个双字节的有符号十六进制字符串（补码）转换为int类型，本项目主要用于热成像传感器回传数据解析
     * 注意：无符号转换不能用本方法，可以直接用Integer.parseInt("0xffff", 16)
     * 
     * @param s
     * @return
     * @throws
     */
    public int hexString2Int(String s) {
        int i = Integer.parseInt(s, 16);
        if ((i >> 15) == 1) {// 判断最高位，如果为1，表明是负数，先取反，再去掉最高位符号位，再加一，然后取负值
            i = -((~i & 0x7fff) + 1);
        }
        return i;
    }

    public Map<String, Boolean> getAllowCruise() {
        return allowCruise;
    }

    public Map<String, String> getPtzOrientation() {
        return ptzOrientation;
    }

    /**
     * 向命令队列中追加命令
     * 
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

    public Map<String, Long> getBreakPointReturnBeginTime() {
        return breakPointReturnBeginTime;
    }

    public Map<String, Boolean> getIsMovingCenterForFireAlarm() {
        return isMovingCenterForFireAlarm;
    }

    public Map<String, String> getFinalMovingCenterForFireAlarm() {
        return finalMovingCenterForFireAlarm;
    }

    public Map<String, Boolean> getAllowAlarm() {
        return allowAlarm;
    }

    public Map<String, String> getSceneFireAlarmInfo() {
        return sceneFireAlarmInfo;
    }

    public Map<Long, String> getCruiseDirection() {
        return cruiseDirection;
    }

    public static void main(String[] args) {
        SerialPortCommServer server = new SerialPortCommServer();

        System.out.println(server.hexString2Int("FFA6"));
    }
}
