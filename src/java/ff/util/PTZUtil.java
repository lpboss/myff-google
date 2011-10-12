/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.util;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import ff.xsocket.SerialPortCommServer;

/**
 *
 * @author jerry
 * 控制云台相关的所有动作
 */
public class PTZUtil {

    static Logger logger = Logger.getLogger(PTZUtil.class.getName());
    private SerialPortCommServer serialPortCommServer;

    public void setSerialPortCommServer(SerialPortCommServer serialPortCommServer) {
        this.serialPortCommServer = serialPortCommServer;
    }

    public void PTZAction(String ptzAction) {
        logger.info("ptzAction:" + ptzAction);
        try {

            boolean connResult;

            //先停止再发送新命令。
            connResult = serialPortCommServer.sendCommand("192.168.254.65", "FF 01 00 00 00 00 01");
            
            serialPortCommServer.sendCommand("192.168.254.65", "FF 01 00 51 00 00 52 FF 01 00 53 00 00 54");
            //发送命令前，先设置停止巡航状态位。
            serialPortCommServer.getAllowCruise().put("192.168.254.65", Boolean.FALSE);
            if (ptzAction.equals("up")) {
                connResult = serialPortCommServer.sendCommand("192.168.254.65", "FF 01 00 08 00 3F 48");
                logger.info("FF 01 00 08 00 3F 48 UP..........................." + connResult);
            } else if (ptzAction.equals("down")) {
                connResult = serialPortCommServer.sendCommand("192.168.254.65", "FF 01 00 10 00 3F 50");
                logger.info("FF 01 00 10 00 3F 50 DOWN........................." + connResult);
            } else if (ptzAction.equals("right")) {
                connResult = serialPortCommServer.sendCommand("192.168.254.65", "FF 01 00 02 3F 00 42");
                logger.info("FF 01 00 02 3F 00 42 RIGHT........................" + connResult);
            } else if (ptzAction.equals("left")) {
                connResult = serialPortCommServer.sendCommand("192.168.254.65", "FF 01 00 04 3F 00 35");
                logger.info("FF 01 00 04 30 00 35 LEFT........................." + connResult);
            } else if (ptzAction.equals("stop")) {
                connResult = serialPortCommServer.sendCommand("192.168.254.65", "FF 01 00 00 00 00 01");
                logger.info("FF 01 00 00 00 00 01 STOP........................." + connResult);
            } else if (ptzAction.equals("cruise")) {
                serialPortCommServer.getAllowCruise().put("192.168.254.65", Boolean.TRUE);
            }
        } catch (IOException ex) {
            Logger.getLogger(PTZUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
