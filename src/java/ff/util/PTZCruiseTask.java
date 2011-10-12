/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.util;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import ff.xsocket.SerialPortCommServer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

/**
 *
 * @author jerry
 * 此类中的方法，负责执行定时巡航任务。
 */
@Service
public class PTZCruiseTask {

    private SerialPortCommServer serialPortCommServer;

    public void setSerialPortCommServer(SerialPortCommServer serialPortCommServer) {
        this.serialPortCommServer = serialPortCommServer;
    }

    /**
     *作者：jerry
     *描述：发送云台角度查询命令，命令的结果会在HeadServerHandler的回调方法onData中进行分析，然后放入serialPortCommServer的angleX，angleY二个类变量中。
     */
    @Scheduled(fixedDelay = 300)
    public void getPTZAngle() {
        // 发送云台角度查询命令
        try {
            //serialPortCommServer.sendCommand("192.168.254.65", "FF 01 00 51 00 00 52 FF 01 00 53 00 00 54");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *作者：jerry
     *描述：发送云台角度查询命令，命令的结果会在HeadServerHandler的回调方法onData中进行分析，然后放入serialPortCommServer的angleX，angleY二个类变量中。
     */
    @Scheduled(fixedDelay = 400)
    public void sendPTZCommand() {
        // 发送云台角度查询命令
        try {
            Map<String, LinkedList> commandMap = serialPortCommServer.getCommandMap();
            Iterator<String> ips = commandMap.keySet().iterator();
            while (ips.hasNext()) {
                String ip = ips.next();
                LinkedList<String> commandQueue = commandMap.get(ip);
                if (commandQueue.size() > 0) {
                    Calendar calendar = Calendar.getInstance();
                    long milliseconds = calendar.getTimeInMillis();
                    SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss SSS");
                    Date date = new Date(milliseconds);
                    System.out.println("ip, commandQueue.getFirst():" + ip + ",:" + commandQueue.getFirst() + ",Date:" + timeFormat.format(date));
                    serialPortCommServer.sendCommand(ip, commandQueue.getFirst());
                    commandQueue.removeFirst();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *作者：jerry
     *描述：让所有的云台，按既有模式旋转，比如削苹果皮模式。
     */
    @Scheduled(fixedDelay = 200)
    public void PTZCruise() {
        //System.out.println("public void PTZCruise()+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        //判断所有的云台，如果没有key添加值并巡航，如果有值，则依次判断巡航方向，比如是向上还是向下。如果角度在359度时，则上跳或下跳X度。
        //如果ptzOrientation中无值，默认向下转动云台。
        //暂时，只是为只有一个平台，即IP为：192.168.254.65
        if (serialPortCommServer.getAllowCruise().get("192.168.254.65") == null) {
            System.out.println("serialPortCommServer.getAllowCruise() == null :----------------------------------------------------------------------");
            serialPortCommServer.getAllowCruise().put("192.168.254.65", Boolean.TRUE);
            //在此，说明云台从来没有进行巡航，同时，启动巡航。向右，顺时针。
            serialPortCommServer.pushCommand("192.168.254.65", "FF 01 00 02 10 00 42");
            //发命令，读云台角度
            serialPortCommServer.pushCommand("192.168.254.65", "FF 01 00 51 00 00 52 FF 01 00 53 00 00 54");

        } else {
            //如果云台巡航有相关标志参数。则判断参数的值。
            //System.out.println("serialPortCommServer.getAllowCruise() == " + serialPortCommServer.getAllowCruise().get("192.168.254.65") + ",Time+" + new Date());
            //System.out.println("serialPortCommServer.angleX:" + serialPortCommServer.getAngleX("192.168.254.65") + ", angleY :" + serialPortCommServer.getAngleY("192.168.254.65"));
            if (serialPortCommServer.getAllowCruise().get("192.168.254.65") == Boolean.TRUE) {

                //TODO:如何判断云台当前是否正在转动？因无法判断，当前每次发送转动命令。
                serialPortCommServer.pushCommand("192.168.254.65", "FF 01 00 02 10 00 13");
                //发命令，读云台角度
                serialPortCommServer.pushCommand("192.168.254.65", "FF 01 00 51 00 00 52");
                serialPortCommServer.pushCommand("192.168.254.65", "FF 01 00 53 00 00 54");
            }
        }
        //判断当前云台是否有旋转方向的标记，如果没有则默认设置向下。
        if (serialPortCommServer.getPtzOrientation().get("192.168.254.65") == null) {
            serialPortCommServer.getPtzOrientation().put("192.168.254.65", "down");
        }
    }
}
