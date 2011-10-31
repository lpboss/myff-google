/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import ff.server.SerialPortCommServer;

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
    @Scheduled(fixedDelay = 15)
    public synchronized void sendPTZCommand() {
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
                    System.out.println("ip:" + ip + ", commandQueue.getFirst():" + commandQueue.getFirst() + ",Date:" + timeFormat.format(date) + "-------------------------------");
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
    @Scheduled(fixedDelay = 15)
    public synchronized void PTZCruise() {
        //System.out.println("public void PTZCruise()+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        //判断所有的云台，如果没有key添加值并巡航，如果有值，则依次判断巡航方向，比如是向上还是向下。如果角度在359度时，则上跳或下跳X度。
        //如果ptzOrientation中无值，默认向下转动云台。
        //暂时，只是为只有一个平台，即IP为：192.168.254.65
        Calendar calendar = Calendar.getInstance();
        long milliseconds = calendar.getTimeInMillis();
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss SSS");
        Date date = new Date(milliseconds);
        String testIP = "192.168.254.65";
        //System.out.println("Angle (192.168.254.65) X:" + serialPortCommServer.getAngleXString(testIP) + ",Y:" + serialPortCommServer.getAngleYString(testIP) + "------------------,Date:" + timeFormat.format(date));
        if (serialPortCommServer.getAllowCruise().get(testIP) == null) {
            System.out.println("serialPortCommServer.getAllowCruise() == null :----------------------------------------------------------------------");
            serialPortCommServer.getAllowCruise().put(testIP, Boolean.TRUE);
            //在此，说明云台从来没有进行巡航，同时，启动巡航。向右，顺时针。指定转到360度处停止。
            //serialPortCommServer.pushCommand(testIP, "FF 01 00 02 10 00 42");
            //以10为步长，右转
            serialPortCommServer.pushCommand(testIP, PTZUtil.getPELCODCommandHexString(1, 0, 0x02, 15, 0, "right"));
        } else {
            //如果云台巡航有相关标志参数。则判断参数的值。保证巡航期间，右转命令只发送一次。
            if (serialPortCommServer.getAllowCruise().get(testIP) == Boolean.TRUE) {
                //以20为步长，右转.判断，如果有当前正在旋转巡航，则不发送
                if (serialPortCommServer.getIsCruising().get(testIP) == null) {
                    serialPortCommServer.getIsCruising().put(testIP, Boolean.TRUE);
                    serialPortCommServer.pushCommand(testIP, PTZUtil.getPELCODCommandHexString(1, 0, 0x02, 15, 0, "right"));
                } else {
                    if (serialPortCommServer.getIsCruising().get(testIP) == Boolean.FALSE && serialPortCommServer.getCruiseBreakpoint().get(testIP) == null) {
                        serialPortCommServer.getIsCruising().put(testIP, Boolean.TRUE);
                        serialPortCommServer.pushCommand(testIP, PTZUtil.getPELCODCommandHexString(1, 0, 0x02, 15, 0, "right"));
                    } else {
                        //如果在巡航断点中有值，且getIsCruising().get(ip) = true,则说明要继续断点，继续巡航。
                        //先把云台调整到断点时的位置。同时调整二个角度。
                        if (serialPortCommServer.getCruiseBreakpoint().get(testIP) != null) {
                            String breakPointAngleX = serialPortCommServer.getCruiseBreakpoint().get(testIP).split("\\|")[0];
                            String breakPointAngleY = serialPortCommServer.getCruiseBreakpoint().get(testIP).split("\\|")[1];
                            //判断当前的XY与断点中的XY是否相等，如果不相同发送调整命令。如果相同，清除断点信息。
                            String currentAngleX = serialPortCommServer.getAngleXString(testIP);
                            String currentAngleY = serialPortCommServer.getAngleYString(testIP);
                            System.out.println("breakPointAngleX:" + breakPointAngleX + ",breakPointAngleY:" + breakPointAngleY + ",currentAngleX:" + currentAngleX + ",currentAngleY:" + currentAngleY);
                            if (breakPointAngleX.equals(currentAngleX) && breakPointAngleY.equals(currentAngleY)) {
                                serialPortCommServer.getCruiseBreakpoint().remove(testIP);
                                serialPortCommServer.getIsAdjustingXYForBreakpoint().remove(testIP);
                                serialPortCommServer.getBreakPointReturnBeginTime().remove(testIP);
                                serialPortCommServer.getIsCruising().put(testIP, Boolean.FALSE);
                                System.out.println("巡航......断点完全复位......继续巡航。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。");
                            } else {
                                String adjustXCommand = PTZUtil.getPELCODCommandHexString(1, 0, 0x4B, Integer.parseInt(breakPointAngleX.split("\\.")[0]), Integer.parseInt(breakPointAngleX.split("\\.")[1]), "ANGLE_X");
                                String adjustYCommand = PTZUtil.getPELCODCommandHexString(1, 0, 0x4D, Integer.parseInt(breakPointAngleY.split("\\.")[0]), Integer.parseInt(breakPointAngleY.split("\\.")[1]), "ANGLE_Y");

                                if (serialPortCommServer.getIsAdjustingXYForBreakpoint().get(testIP) == null) {
                                    if (serialPortCommServer.getBreakPointReturnBeginTime().get(testIP) == null) {
                                        serialPortCommServer.getBreakPointReturnBeginTime().put(testIP, new Date().getTime());
                                    }
                                    serialPortCommServer.getIsAdjustingXYForBreakpoint().put(testIP, Boolean.TRUE);
                                    serialPortCommServer.getIsCruising().put(testIP, Boolean.FALSE);
                                    serialPortCommServer.pushCommand(testIP, adjustXCommand);
                                    serialPortCommServer.pushCommand(testIP, adjustYCommand);
                                } else {
                                    //这是一个补丁功能，有时，云台调整不到位。如果超时4秒，再调整一次。
                                    if (new Date().getTime() - serialPortCommServer.getBreakPointReturnBeginTime().get(testIP) > 4000) {
                                        serialPortCommServer.getBreakPointReturnBeginTime().put(testIP, new Date().getTime());
                                        serialPortCommServer.pushCommand(testIP, adjustXCommand);
                                        serialPortCommServer.pushCommand(testIP, adjustYCommand);
                                    }
                                }
                                System.out.println("巡航......断点复位......正在调整角度。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。");
                                return;
                            }
                        }
                    }
                }
                //添加一个补丁块，以修正削苹果皮时角度通过0度时，不巡航的问题。
                if (serialPortCommServer.getAngleX(testIP) >= 0.0 && serialPortCommServer.getAngleX(testIP) < 2.0) {
                    String currentAngleY = String.valueOf(serialPortCommServer.getAngleYString(testIP));
                    if (serialPortCommServer.getIsCruisingPresetAngleY().get(testIP) != null && serialPortCommServer.getIsCruisingPresetAngleY().get(testIP) == Integer.parseInt(currentAngleY.split("\\.")[0])) {
                        //继续巡航。
                        serialPortCommServer.getIsCruisingPresetAngleY().remove(testIP);
                        serialPortCommServer.pushCommand(testIP, PTZUtil.getPELCODCommandHexString(1, 0, 0x02, 15, 0, "right"));
                    }
                } else {
                    //有可能在设置上升以后，角度并不骨超过360度。这时要继续右转。
                    //有这个值，说明还在上升的过程中。或者说已经上升了，但当时云台没有超过0度，所以这个值一直没有去掉。补丁的作法就是继续转动，以让云台超过0度。
                    if (serialPortCommServer.getIsCruisingPresetAngleY().get(testIP) != null) {
                        //判断，如果当前的角度，已经符合上杨角度，则执行下面的命令。
                        String currentAngleY = String.valueOf(serialPortCommServer.getAngleYString(testIP));
                        if (serialPortCommServer.getIsCruisingPresetAngleY().get(testIP) == Integer.parseInt(currentAngleY.split("\\.")[0])) {
                            serialPortCommServer.pushCommand(testIP, PTZUtil.getPELCODCommandHexString(1, 0, 0x02, 15, 0, "right"));
                        }
                    }
                }
                //判断，如果角度为359.99度，则垂直变化角度。
                if (serialPortCommServer.getAngleX(testIP) > 359.50 && serialPortCommServer.getAngleX(testIP) < 359.99) {
                    System.out.println("Y角度切换中。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。");

                    //这里要判断一下，读取系统预置设置的Y角度，如果达到角度要求则执行水平转动命令。并清空数据库。否则继续等待Y角度的调整。
                    System.out.println("serialPortCommServer.getIsCruisingPresetAngleY().get(192.168.254.65):" + serialPortCommServer.getIsCruisingPresetAngleY().get(testIP));
                    if (serialPortCommServer.getIsCruisingPresetAngleY().get(testIP) == null) {
                        String currentAngleY = serialPortCommServer.getAngleYString(testIP);
                        //上扬10度
                        int angleY1 = Integer.parseInt(currentAngleY.split("\\.")[0]) + 10;
                        int angleY2 = Integer.parseInt(currentAngleY.split("\\.")[1]);
                        if (angleY1 > 90) {
                            angleY1 = 90;
                            angleY1 = 0;
                        } else if (angleY1 == 100) {
                            angleY1 = 0;
                            angleY1 = 0;
                        } else {
                            //小角度不计算。
                            angleY2 = 0;
                        }
                        serialPortCommServer.getIsCruisingPresetAngleY().put(testIP, angleY1);
                        try {
                            serialPortCommServer.sendCommand(testIP, "FF 01 00 00 00 00 01");
                        } catch (IOException ex) {
                            Logger.getLogger(PTZCruiseTask.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        serialPortCommServer.pushCommand(testIP, "FF 01 00 00 00 00 01");

                        serialPortCommServer.pushCommand(testIP, PTZUtil.getPELCODCommandHexString(1, 0, 0x4D, angleY1, angleY2, "ANGLE_Y"));
                    } else {
                        //判断角度是否达到预置的高度了。
                        String currentAngleY = String.valueOf(serialPortCommServer.getAngleYString(testIP));
                        if (serialPortCommServer.getIsCruisingPresetAngleY().get(testIP) == Integer.parseInt(currentAngleY.split("\\.")[0])) {
                            //继续巡航。下面屏蔽了一行，因为来不及转动，所以总是角度在360以内。
                            //serialPortCommServer.getIsCruisingPresetAngleY().remove(testIP);
                            serialPortCommServer.pushCommand(testIP, PTZUtil.getPELCODCommandHexString(1, 0, 0x02, 15, 0, "right"));
                        } else {
                            System.out.println("继续等待云台Y角度调整。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。");
                        }
                    }
                }
            }
        }
        //判断当前云台是否有旋转方向的标记，如果没有则默认设置向下。
        if (serialPortCommServer.getPtzOrientation().get(testIP) == null) {
            serialPortCommServer.getPtzOrientation().put(testIP, "down");
        }
    }

    /**
     *作者：jerry
     *描述：根据热值，判断是否为火警。
     */
    @Scheduled(fixedDelay = 15)
    public synchronized void judgeFireAlarm() {
        String testIP = "192.168.254.65";
        //判断，如果当前没有进行置中操作，则从新判断热值 。
        System.out.println("serialPortCommServer.getIsMovingCenterForFireAlarm().get(testIP):" + serialPortCommServer.getIsMovingCenterForFireAlarm().get(testIP));
        if (serialPortCommServer.getIsMovingCenterForFireAlarm().get(testIP) == null) {
            //System.out.println("serialPortCommServer.getAlertMax(192.168.1.50)" + serialPortCommServer.getAlertMax("192.168.1.50"));
            if (serialPortCommServer.getAlertMax("192.168.1.50") > 1500) {
                int heatPosX = serialPortCommServer.getAlertX("192.168.1.50");
                int heatPosY = serialPortCommServer.getAlertY("192.168.1.50");

                //如果正在巡航，则在发送其它命令前，先保存断点。并停止巡航。
                serialPortCommServer.getAllowCruise().put(testIP, Boolean.FALSE);
                serialPortCommServer.pushCommand(testIP, "FF 01 00 00 00 00 01");
                if (serialPortCommServer.getIsCruising().get(testIP) != null && serialPortCommServer.getIsCruising().get(testIP) == Boolean.TRUE) {
                    if (serialPortCommServer.getCruiseBreakpoint().get(testIP) == null) {
                        serialPortCommServer.getCruiseBreakpoint().put(testIP, serialPortCommServer.getAngleXString(testIP) + "|" + serialPortCommServer.getAngleYString(testIP));
                    }
                }
                /*逐渐让热成像对准中心。
                4点区域法，左上（151.171），右上（228，171），左下（152，114），右下（228，114），（192，144）为中心点。
                得到当前的角度
                 */
                String currentAngleX = serialPortCommServer.getAngleXString(testIP);
                String currentAngleY = serialPortCommServer.getAngleYString(testIP);
                System.out.println("当前热值：" + serialPortCommServer.getAlertMax("192.168.1.50"));
                System.out.println("热成像X:" + heatPosX + ",当前水平角度：" + currentAngleX);
                System.out.println("热成像Y:" + heatPosY + "当前垂直角度：" + currentAngleY);

                int angleY1 = Integer.parseInt(currentAngleY.split("\\.")[0]);//Y1为角度的整数部分，2为小数点部分
                int angleY2 = Integer.parseInt(currentAngleY.split("\\.")[1]);
                int angleX1 = Integer.parseInt(currentAngleX.split("\\.")[0]);
                int angleX2 = Integer.parseInt(currentAngleX.split("\\.")[1]);
                //如果水平方向，小于192，水平逆时针转动。否则顺时针
                if (heatPosX < 192) {
                    if (angleX1 > 1 || angleX1 == 1) {
                        angleX1 = angleX1 - 1;
                    } else {
                        angleX1 = 359;
                    }
                } else {
                    if (angleX1 == 359) {
                        angleX1 = 0;
                    } else {
                        angleX1 = angleX1 + 1;
                    }
                }

                //垂直方向，如果小于144，向下转动。否则向上
                if (heatPosY < 144) {
                    if (angleY1 > 1) {
                        angleY1 = angleY1 - 1;
                    }
                } else {
                    if (angleY1 < 89) {
                        angleY1 = angleY1 + 1;
                    }
                }

                System.out.println("微调要求水平角度：" + angleX1 + "." + angleX2);
                System.out.println("微调要求垂直角度：" + angleY1 + "." + angleY2);
                /*
                 * 准备好角度以后，进行角度调整命令。在角度调整后，计算出1度对于热成像方位值变化的比例。然后进行一次命令调整。
                 */
                String adjustXCommand = PTZUtil.getPELCODCommandHexString(1, 0, 0x4B, angleX1, angleX2, "ANGLE_X");
                String adjustYCommand = PTZUtil.getPELCODCommandHexString(1, 0, 0x4D, angleY1, angleY2, "ANGLE_Y");
                serialPortCommServer.pushCommand(testIP, adjustXCommand);
                serialPortCommServer.pushCommand(testIP, adjustYCommand);
                //设置正在置中状态位。
                serialPortCommServer.getIsMovingCenterForFireAlarm().put(testIP, Boolean.TRUE);
                serialPortCommServer.getFineMovingCenterForFireAlarm().put(testIP, angleX1 + "." + angleX2 + "|" + adjustXCommand + "|" + angleY1 + "." + angleY2 + "|" + adjustXCommand + "|" + new Date().getTime());
                //serialPortCommServer.getFineMovingCenterForFireAlarm().put(testIP, angleX1 + "." + angleX2 + "|" + adjustXCommand + "|" + angleY1 + "|" + adjustXCommand);
            }
        } else if (serialPortCommServer.getIsMovingCenterForFireAlarm().get(testIP) == Boolean.TRUE) {
            //如果当前正在微调。判断微调角度是否到位。如果不到位，继续调整。
            String currentAngleX = serialPortCommServer.getAngleXString(testIP);
            String currentAngleY = serialPortCommServer.getAngleYString(testIP);
            System.out.println("火警微调后的角度：" + currentAngleX + "," + currentAngleY);
            String fineMovingInfo = serialPortCommServer.getFineMovingCenterForFireAlarm().get(testIP);
            Float currentfloatAngleX = Float.parseFloat(currentAngleX);
            Float currentfloatAngleY = Float.parseFloat(currentAngleY);
            Float fineAngleX = Float.parseFloat(fineMovingInfo.split("\\|")[0]);
            Float fineAngleY = Float.parseFloat(fineMovingInfo.split("\\|")[2]);
            System.out.println("火警微调要求角度：" + fineAngleX + "," + fineAngleY);
            Long fineBeginTime = Long.parseLong(fineMovingInfo.split("\\|")[4]);
            //各误差在0.5之内，并且已经过去0.5秒，即马上停止微调阶段。
            if (Math.abs(currentfloatAngleX - fineAngleX) < 0.5 && Math.abs(currentfloatAngleY - fineAngleY) < 0.5 && new Date().getTime() - fineBeginTime > 500) {
                //调整到位后，清除微调信息。角度误差在0.5度时，停止调整。
                serialPortCommServer.getAllowCruise().put(testIP, Boolean.TRUE);
                serialPortCommServer.getFineMovingCenterForFireAlarm().remove(testIP);
                serialPortCommServer.getIsMovingCenterForFireAlarm().remove(testIP);
                System.out.println("微调已经到位了 --------------------------------------------------------------------------");
            } else if ((Math.abs(currentfloatAngleX - fineAngleX) > 0.5 || Math.abs(currentfloatAngleY - fineAngleY) > 0.5) && new Date().getTime() - fineBeginTime > 500) {
                //只要有一个角度有误差，且时间超过0.5秒，就继续发送调整命令。
                serialPortCommServer.pushCommand(testIP, fineMovingInfo.split("\\|")[1]);
                serialPortCommServer.pushCommand(testIP, fineMovingInfo.split("\\|")[3]);
                //设置正在置中状态位。
                serialPortCommServer.getFineMovingCenterForFireAlarm().put(testIP, fineMovingInfo.split("\\|")[0] + "|" + fineMovingInfo.split("\\|")[1] + "|" + fineMovingInfo.split("\\|")[2] + "|" + fineMovingInfo.split("\\|")[3] + "|" + new Date().getTime());
            }
        }
    }
}
