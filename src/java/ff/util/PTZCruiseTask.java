/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.util;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 *
 * @author jerry
 * 此类中的方法，负责执行定时巡航任务。
 */

@Service
public class PTZCruiseTask {
    @Scheduled(fixedDelay = 30000)
    public void testTwoPrint() {
        System.out.println("TestTwo测试打印");
    }    
}
