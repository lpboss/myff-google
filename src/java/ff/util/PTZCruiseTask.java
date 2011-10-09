/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.util;

import ff.xsocket.SerialPortCommServer;
import java.util.Date;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

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
    
    @Scheduled(fixedDelay = 3000)
    public void testTwoPrint() {
        
        System.out.println("TestTwo测试打印" + new Date() + ",云台角度：" + serialPortCommServer.getHeadInfo("192.168.254.65"));
    }
}
