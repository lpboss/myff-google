/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.util;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import ff.xsocket.SerialPortCommServer;

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
    
    @Scheduled(fixedDelay = 1000)
    public void testTwoPrint() {
    	// 发送云台角度查询命令
        try {
			serialPortCommServer.sendCommand("192.168.254.65", "FF 01 00 51 00 00 52 FF 01 00 53 00 00 54");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
