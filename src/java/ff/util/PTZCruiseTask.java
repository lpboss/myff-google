/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.util;

import ff.model.PTZ;
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
import ff.service.PTZService;
import java.util.List;

/**
 *
 * @author jerry
 * 此类中的方法，负责执行定时巡航任务�
 */
@Service
public class PTZCruiseTask {

    private PTZService ptzService;
    private SerialPortCommServer serialPortCommServer;
    private List<PTZ> ptzs;

    public void setSerialPortCommServer(SerialPortCommServer serialPortCommServer) {
        this.serialPortCommServer = serialPortCommServer;
    }

    public void setPtzService(PTZService ptzService) {
        this.ptzService = ptzService;
    }

    /**
     *作者：jerry
     *描述：发送云台角度查询命令，命令的结果会在HeadServerHandler的回调方法onData中进行分析，然后放入serialPortCommServer的angleX，angleY二个类变量中�
     */
    @Scheduled(fixedDelay = 15)
    public synchronized void sendPTZCommand() {
        // 发送云台角度查询命�
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
     *描述：让所有的云台，按既有模式旋转，比如削苹果皮模式�
     */
    @Scheduled(fixedDelay = 15)
    public synchronized void PTZCruise() {
        for (PTZ ptz : ptzs) {
            String ptzIP = ptz.getPelcodCommandUrl();
            if (ptz.getCruiseLeftLimit() == 0 && ptz.getCruiseRightLimit() == 0) {
                serialPortCommServer.getCruiseDirection().put(ptz.getId(), "loop");
            } else if (serialPortCommServer.getCruiseDirection().get(ptz.getId()) == null) {
                try {
                    //如果从来没有预置方向，默认为向右转。
                    //为空是刚刚开机，这时一定要发停止命令停止转动。连发二次停止命令。
                    serialPortCommServer.sendCommand(ptzIP, "FF 01 00 00 00 00 01");
                    serialPortCommServer.pushCommand(ptzIP, "FF 01 00 00 00 00 01");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                serialPortCommServer.getCruiseDirection().put(ptz.getId(), "right");
            }
            int cruiseStep = ptz.getCruiseStep();
            //System.out.println("public void PTZCruise()+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            //判断所有的云台，如果没有key添加值并巡航，如果有值，则依次判断巡航方向，比如是向上还是向下。如果角度在359度时，则上跳或下跳X度�
            //如果ptzOrientation中无值，默认向下转动云台�
            //暂时，只是为只有一个平台，即IP为：192.168.254.65
            Calendar calendar = Calendar.getInstance();
            long milliseconds = calendar.getTimeInMillis();
            SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss SSS");
            Date date = new Date(milliseconds);

            //System.out.println("角度信息，Angle (" + ptzIP + ") X:" + serialPortCommServer.getAngleXString(ptzIP) + ",Y:" + serialPortCommServer.getAngleYString(ptzIP) + "------------------,Date:" + timeFormat.format(date) + ",方向：" + serialPortCommServer.getCruiseDirection().get(ptz.getId()));
            //System.out.println("当前的云台" + ptzIP + "是否允许巡航" + serialPortCommServer.getAllowCruise().get(ptzIP));

            if (serialPortCommServer.getAllowCruise().get(ptzIP) == null) {
                //System.out.println("serialPortCommServer.getAllowCruise() == null :----------------------------------------------------------------------");

                //在此，说明云台从来没有进行巡航，同时，启动巡航。向右，顺时针。指定转�60度处停止�
                //serialPortCommServer.pushCommand(ptzIP, "FF 01 00 02 10 00 42");
                //�0为步长，右转
                boolean commandResult;
                try {
                    //刚开机时，有可能命令运行失败，所以要判断命令执行的结果�
                    commandResult = serialPortCommServer.sendCommand(ptzIP, PTZUtil.getPELCODCommandHexString(1, 0, 0x02, cruiseStep, 0, "right", ptz.getBrandType()));
                    if (commandResult) {
                        serialPortCommServer.getAllowCruise().put(ptzIP, Boolean.TRUE);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(PTZCruiseTask.class.getName()).log(Level.SEVERE, null, ex);
                }

                //serialPortCommServer.pushCommand(ptzIP, PTZUtil.getPELCODCommandHexString(1, 0, 0x02, cruiseStep, 0, "right"));
            } else {
                //如果云台巡航有相关标志参数。则判断参数的值。保证巡航期间，右转命令只发送一次�
                //System.out.println("当前的云台：" + ptzIP + "，是否正在巡航：" + serialPortCommServer.getIsCruising().get(ptzIP));
                if (serialPortCommServer.getAllowCruise().get(ptzIP) == Boolean.TRUE) {
                    //�0为步长，右转.判断，如果有当前正在旋转巡航，则不发�
                    if (serialPortCommServer.getIsCruising().get(ptzIP) == null) {
                        serialPortCommServer.getIsCruising().put(ptzIP, Boolean.TRUE);
                        serialPortCommServer.pushCommand(ptzIP, PTZUtil.getPELCODCommandHexString(1, 0, 0x02, cruiseStep, 0, "right", ptz.getBrandType()));
                    } else {
                        if (serialPortCommServer.getIsCruising().get(ptzIP) == Boolean.FALSE && serialPortCommServer.getCruiseBreakpoint().get(ptzIP) == null) {
                            serialPortCommServer.getIsCruising().put(ptzIP, Boolean.TRUE);
                            serialPortCommServer.pushCommand(ptzIP, PTZUtil.getPELCODCommandHexString(1, 0, 0x02, cruiseStep, 0, "right", ptz.getBrandType()));
                        } else {
                            //实其实是个补丁，如果在发送巡航指令时，有可能命令并没有被执行，所以要每次发送�
                            //serialPortCommServer.pushCommand(ptzIP, PTZUtil.getPELCODCommandHexString(1, 0, 0x02, cruiseStep, 0, "right"));
                            //如果在巡航断点中有值，且getIsCruising().get(ip) = true,则说明要继续断点，继续巡航�
                            //先把云台调整到断点时的位置。同时调整二个角度�
                            if (serialPortCommServer.getCruiseBreakpoint().get(ptzIP) != null) {
                                String breakPointAngleX = serialPortCommServer.getCruiseBreakpoint().get(ptzIP).split("\\|")[0];
                                String breakPointAngleY = serialPortCommServer.getCruiseBreakpoint().get(ptzIP).split("\\|")[1];
                                //判断当前的XY与断点中的XY是否相等，如果不相同发送调整命令。如果相同，清除断点信息�
                                String currentAngleX = serialPortCommServer.getAngleXString(ptzIP);
                                String currentAngleY = serialPortCommServer.getAngleYString(ptzIP);
                                System.out.println("breakPointAngleX:" + breakPointAngleX + ",breakPointAngleY:" + breakPointAngleY + ",currentAngleX:" + currentAngleX + ",currentAngleY:" + currentAngleY);
                                if (breakPointAngleX.equals(currentAngleX) && breakPointAngleY.equals(currentAngleY)) {
                                    serialPortCommServer.getCruiseBreakpoint().remove(ptzIP);
                                    serialPortCommServer.getIsAdjustingXYForBreakpoint().remove(ptzIP);
                                    serialPortCommServer.getBreakPointReturnBeginTime().remove(ptzIP);
                                    serialPortCommServer.getIsCruising().put(ptzIP, Boolean.FALSE);
                                    System.out.println("巡航......断点完全复位......继续巡航。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。�");
                                } else {
                                    String adjustXCommand = PTZUtil.getPELCODCommandHexString(1, 0, 0x4B, Integer.parseInt(breakPointAngleX.split("\\.")[0]), Integer.parseInt(breakPointAngleX.split("\\.")[1]), "ANGLE_X", ptz.getBrandType());
                                    String adjustYCommand = PTZUtil.getPELCODCommandHexString(1, 0, 0x4D, Integer.parseInt(breakPointAngleY.split("\\.")[0]), Integer.parseInt(breakPointAngleY.split("\\.")[1]), "ANGLE_Y", ptz.getBrandType());

                                    if (serialPortCommServer.getIsAdjustingXYForBreakpoint().get(ptzIP) == null) {
                                        if (serialPortCommServer.getBreakPointReturnBeginTime().get(ptzIP) == null) {
                                            serialPortCommServer.getBreakPointReturnBeginTime().put(ptzIP, new Date().getTime());
                                        }
                                        serialPortCommServer.getIsAdjustingXYForBreakpoint().put(ptzIP, Boolean.TRUE);
                                        serialPortCommServer.getIsCruising().put(ptzIP, Boolean.FALSE);
                                        serialPortCommServer.pushCommand(ptzIP, adjustXCommand);
                                        serialPortCommServer.pushCommand(ptzIP, adjustYCommand);
                                    } else {
                                        //这是一个补丁功能，有时，云台调整不到位。如果超�秒，再调整一次�
                                        if (new Date().getTime() - serialPortCommServer.getBreakPointReturnBeginTime().get(ptzIP) > 4000) {
                                            serialPortCommServer.getBreakPointReturnBeginTime().put(ptzIP, new Date().getTime());
                                            serialPortCommServer.pushCommand(ptzIP, adjustXCommand);
                                            serialPortCommServer.pushCommand(ptzIP, adjustYCommand);
                                        }
                                    }
                                    System.out.println("巡航......断点复位......正在调整角度。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。�");
                                    return;
                                }
                            }
                        }
                    }

                    //判断，如果角度为359.99度，则垂直变化角度�
                    //这儿，也要判断巡航方向或模式
                    if (serialPortCommServer.getCruiseDirection().get(ptz.getId()).equals("loop")) {
                        if (serialPortCommServer.getAngleX(ptzIP) > 359.50 && serialPortCommServer.getAngleX(ptzIP) < 359.99) {
                            System.out.println("Loop,Y角度切换中。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。�");

                            //这里要判断一下，读取系统预置设置的Y角度，如果达到角度要求则执行水平转动命令。并清空数据库。否则继续等待Y角度的调整�
                            System.out.println("serialPortCommServer.getIsCruisingPresetAngleY().get(192.168.254.65):" + serialPortCommServer.getIsCruisingPresetAngleY().get(ptzIP));
                            if (serialPortCommServer.getIsCruisingPresetAngleY().get(ptzIP) == null) {
                                setYAngleForCruise(ptz);
                            } else {
                                //判断角度是否达到预置的高度了�
                                String currentAngleY = String.valueOf(serialPortCommServer.getAngleYString(ptzIP));
                                //飞越云台的质量问题，所以当前垂直角度有误差，所以在0.01度以内，认为合理�
                                System.out.println("预定Y角度与实际Y角度误差" + Math.abs(serialPortCommServer.getIsCruisingPresetAngleY().get(ptzIP) - Double.parseDouble(currentAngleY)));
                                if (serialPortCommServer.getIsCruisingPresetAngleY().get(ptzIP) == Integer.parseInt(currentAngleY.split("\\.")[0]) || Math.abs(serialPortCommServer.getIsCruisingPresetAngleY().get(ptzIP) - Double.parseDouble(currentAngleY)) < 0.02) {
                                    //继续巡航。下面屏蔽了一行，因为来不及转动，所以总是角度�60以内�
                                    serialPortCommServer.getCruiseDirection().put(ptz.getId(), "loop");
                                    serialPortCommServer.pushCommand(ptzIP, PTZUtil.getPELCODCommandHexString(1, 0, 0x02, cruiseStep, 0, "right", ptz.getBrandType()));
                                } else {
                                    System.out.println("继续等待云台Y角度调整。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。�");
                                }
                            }
                        } else if (serialPortCommServer.getAngleX(ptzIP) > 0 && serialPortCommServer.getAngleX(ptzIP) < 1) {
                            //小于2度时，再清除Y角度上仰状态位。
                            serialPortCommServer.getIsCruisingPresetAngleY().remove(ptzIP);
                            serialPortCommServer.pushCommand(ptzIP, PTZUtil.getPELCODCommandHexString(1, 0, 0x02, cruiseStep, 0, "right", ptz.getBrandType()));
                        }
                    } else if (serialPortCommServer.getCruiseDirection().get(ptz.getId()).equals("right")) {
                        /*
                         * 只要不是loop都有一个位置要修改，即左，右边界的处理问题。这里要结合CruiseFromTo,并以CrusiseFromTo为首要判断条件
                         * 以LR为例，只要过了R限定角度，就完全是L命令，只要过了L限定角度，就完全是R命令。当前默认以0为左边界。也就是左边固定，暂时
                         * 同时规定二角的最少差，不能小于10度。
                         */
                        if (ptz.getCruiseFromTo().equals("LR")) {
                            //再判断，当前的角度
                            if (serialPortCommServer.getAngleX(ptzIP) >= ptz.getCruiseRightLimit()) {
                                if (serialPortCommServer.getIsCruisingPresetAngleY().get(ptzIP) == null) {
                                    //System.out.println("马上要开始设置Y角度。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。");
                                    //只有大于1度时才调整角度，其它情况可能是复位引起的角度过大。
                                    if (serialPortCommServer.getAngleX(ptzIP) - ptz.getCruiseRightLimit() > 0 && serialPortCommServer.getAngleX(ptzIP) - ptz.getCruiseRightLimit() < 1) {
                                        setYAngleForCruise(ptz);
                                    }
                                } else {
                                    //判断角度是否达到预置的高度了�
                                    String currentAngleY = String.valueOf(serialPortCommServer.getAngleYString(ptzIP));
                                    //飞越云台的质量问题，所以当前垂直角度有误差，所以在0.01度以内，认为合理�
                                    System.out.println("预定Y角度与实际Y角度误差" + Math.abs(serialPortCommServer.getIsCruisingPresetAngleY().get(ptzIP) - Double.parseDouble(currentAngleY)));

                                    if (serialPortCommServer.getIsCruisingPresetAngleY().get(ptzIP) == Integer.parseInt(currentAngleY.split("\\.")[0]) || Math.abs(serialPortCommServer.getIsCruisingPresetAngleY().get(ptzIP) - Double.parseDouble(currentAngleY)) < 0.02) {
                                        //继续巡航。下面屏蔽了一行，因为来不及转动，所以总是角度�60以内
                                        serialPortCommServer.getCruiseDirection().put(ptz.getId(), "left");
                                        serialPortCommServer.pushCommand(ptzIP, PTZUtil.getPELCODCommandHexString(1, 0, 0x04, cruiseStep, 0, "left", ptz.getBrandType()));
                                    } else {
                                        System.out.println("继续等待云台Y角度调整。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。�");
                                    }
                                }
                            } else if (serialPortCommServer.getAngleX(ptzIP) - ptz.getCruiseLeftLimit() > 0 && serialPortCommServer.getAngleX(ptzIP) - ptz.getCruiseLeftLimit() < 1) {
                                //在返回转动过程中，只有返回的度数超过0，小于1度时才清除状态
                                System.out.println("正在清掉左转到头是的上仰角度信息。。。。。。。。。。。");
                                serialPortCommServer.getIsCruisingPresetAngleY().remove(ptzIP);
                            }
                        } else {
                        }
                    } else if (serialPortCommServer.getCruiseDirection().get(ptz.getId()).equals("left")) {
                        if (ptz.getCruiseFromTo().equals("LR")) {
                            //再判断，当前的角度
                            if (serialPortCommServer.getAngleX(ptzIP) <= ptz.getCruiseLeftLimit()) {
                                if (serialPortCommServer.getIsCruisingPresetAngleY().get(ptzIP) == null) {
                                    //System.out.println("马上要开始设置Y角度。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。");
                                    if (ptz.getCruiseLeftLimit() - serialPortCommServer.getAngleX(ptzIP) > 0 && ptz.getCruiseLeftLimit() - serialPortCommServer.getAngleX(ptzIP) < 1) {
                                        setYAngleForCruise(ptz);
                                    }
                                } else {
                                    //判断角度是否达到预置的高度了�
                                    String currentAngleY = String.valueOf(serialPortCommServer.getAngleYString(ptzIP));
                                    //飞越云台的质量问题，所以当前垂直角度有误差，所以在0.01度以内，认为合理�
                                    System.out.println("预定Y角度与实际Y角度误差" + Math.abs(serialPortCommServer.getIsCruisingPresetAngleY().get(ptzIP) - Double.parseDouble(currentAngleY)));
                                    //发转动时，也有限制，就是要在接近左边界时才能发，不能只判断Y的角度情况。如果从右向左转，而且则转过右界时，Y状态还没有清掉，这时只判断Y不行。\
                                    if (serialPortCommServer.getIsCruisingPresetAngleY().get(ptzIP) == Integer.parseInt(currentAngleY.split("\\.")[0]) || Math.abs(serialPortCommServer.getIsCruisingPresetAngleY().get(ptzIP) - Double.parseDouble(currentAngleY)) < 0.02) {
                                        //继续巡航。下面屏蔽了一行，因为来不及转动，所以总是角度�60以内
                                        serialPortCommServer.getCruiseDirection().put(ptz.getId(), "right");
                                        serialPortCommServer.pushCommand(ptzIP, PTZUtil.getPELCODCommandHexString(1, 0, 0x02, cruiseStep, 0, "right", ptz.getBrandType()));
                                    } else {
                                        System.out.println("继续等待云台Y角度调整。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。�");
                                    }
                                }
                            } else if (ptz.getCruiseRightLimit() - serialPortCommServer.getAngleX(ptzIP) > 0 && ptz.getCruiseRightLimit() - serialPortCommServer.getAngleX(ptzIP) < 1) {
                                //这其实是一种互相清，这时，它其实是在清上次右转到头时设置的Y角度状态
                                System.out.println("正在清掉右转到头是的上仰角度信息。。。。。。。。。。。");
                                serialPortCommServer.getIsCruisingPresetAngleY().remove(ptzIP);
                            }
                        } else {
                        }
                    }

                } else {
                    //如果不允许巡航了，判断一下，适当停止。如果正在火警调节中，就暂时不发停止命令如果不是，发送停止命令�
                /*if (serialPortCommServer.getIsMovingCenterForFireAlarm().get(ptzIP) == null || serialPortCommServer.getIsMovingCenterForFireAlarm().get(ptzIP) == Boolean.FALSE) {
                    serialPortCommServer.pushCommand(ptzIP, "FF 01 00 00 00 00 01");
                    }*/
                }
            }
            //判断当前云台是否有旋转方向的标记，如果没有则默认设置向下�
            if (serialPortCommServer.getPtzOrientation().get(ptzIP) == null) {
                serialPortCommServer.getPtzOrientation().put(ptzIP, "down");
            }
        }
    }

    private void setYAngleForCruise(PTZ ptz) {
        //判断，如果当前的Y
        String ptzIP = ptz.getPelcodCommandUrl();
        String currentAngleY = serialPortCommServer.getAngleYString(ptzIP);
        //上扬角度，由参数决定，默认10�
        int angleY1 = Integer.parseInt(currentAngleY.split("\\.")[0]) + ptz.getCruiseAngleYStep();
        int angleY2 = Integer.parseInt(currentAngleY.split("\\.")[1]);
        angleY2 = 0;//呼略小角度。
        //如果将要上仰的角度大于最大上仰角度，但不高于最大上仰角与上仰步长之合时
        //飞越云台不准，经常误差0.2度，所以要作很多处理。
        if (angleY1 > ptz.getCruiseUpLimit() && Math.abs(Double.parseDouble(currentAngleY) - ptz.getCruiseUpLimit()) > 0.2) {
            angleY1 = ptz.getCruiseUpLimit();
        } else if (Math.abs(Double.parseDouble(currentAngleY) - ptz.getCruiseUpLimit()) < 0.3) {
            angleY1 = ptz.getCruiseDownLimit();
        } else if (Math.abs(angleY1 - (ptz.getCruiseUpLimit() + ptz.getCruiseAngleYStep())) < 0.3) {
            angleY1 = ptz.getCruiseDownLimit();
        }
        System.out.println("调整后的Y角度值是：" + angleY1 + " ,.....................................................");
        serialPortCommServer.getIsCruisingPresetAngleY().put(ptzIP, angleY1);
        try {
            serialPortCommServer.sendCommand(ptzIP, "FF 01 00 00 00 00 01");
        } catch (IOException ex) {
            Logger.getLogger(PTZCruiseTask.class.getName()).log(Level.SEVERE, null, ex);
        }
        serialPortCommServer.pushCommand(ptzIP, "FF 01 00 00 00 00 01");

        serialPortCommServer.pushCommand(ptzIP, PTZUtil.getPELCODCommandHexString(1, 0, 0x4D, angleY1, angleY2, "ANGLE_Y", ptz.getBrandType()));
    }

    /**
     *作者：jerry
     *描述：根据热值，判断是否为火警�
     */
    @Scheduled(fixedDelay = 15)
    public synchronized void judgeFireAlarm() {
        //double visualAngleX = 20.2;//视角X
        //double visualAngleY = 15.2;//视角Y

        //double visualAngleX = 30.84;//视角X
        for (PTZ ptz : ptzs) {
            //System.out.println("PTZ:" + ptz);
            double visualAngleX = ptz.getVisualAngleX();//50;//视角X
            double visualAngleY = ptz.getVisualAngleY();//38;//视角Y

            int infraredPixelX = ptz.getInfraredPixelX();//382;//红外像素X数量
            int infraredPixelY = ptz.getInfraredPixelY();// 288;//红外像素Y数量
            double anglePerPixelX = visualAngleX / infraredPixelX;//每个像素的角�
            double anglePerPixelY = visualAngleY / infraredPixelY;//每个像素的角�

            String ptzIP = ptz.getPelcodCommandUrl();//"192.168.254.65";
            String infraredSetupIP = ptz.getInfraredCircuitUrl();//"192.168.1.50";

            //判断，如果当前没有进行置中操作，则从新判断热
            if (serialPortCommServer.getIsMovingCenterForFireAlarm().get(ptzIP) == null && (serialPortCommServer.getAllowAlarm().get(ptzIP) == null || serialPortCommServer.getAllowAlarm().get(ptzIP) == Boolean.TRUE)) {
                //System.out.println("serialPortCommServer.getAlertMax(infraredSetupIP):" + serialPortCommServer.getAlertMax(infraredSetupIP));
                if (serialPortCommServer.getAlertMax(infraredSetupIP) > ptz.getAlarmHeatValue()) {
                    //超过热值后，首先设置云台的报警状态。
                    ptz.setIsAlarm(1);
                    ptzService.saveOrUpdate(ptz);
                    int heatPosX = serialPortCommServer.getAlertX(infraredSetupIP);
                    int heatPosY = serialPortCommServer.getAlertY(infraredSetupIP);

                    //如果正在巡航，则在发送其它命令前，先保存断点。并停止巡航�
                    serialPortCommServer.getAllowCruise().put(ptzIP, Boolean.FALSE);
                    //同时不允许此云台再次报告火警
                    serialPortCommServer.getAllowAlarm().put(ptzIP, Boolean.FALSE);
                    serialPortCommServer.pushCommand(ptzIP, "FF 01 00 00 00 00 01");

                    if (serialPortCommServer.getIsCruising().get(ptzIP) != null && serialPortCommServer.getIsCruising().get(ptzIP) == Boolean.TRUE) {
                        if (serialPortCommServer.getCruiseBreakpoint().get(ptzIP) == null) {
                            serialPortCommServer.getCruiseBreakpoint().put(ptzIP, serialPortCommServer.getAngleXString(ptzIP) + "|" + serialPortCommServer.getAngleYString(ptzIP));
                        }
                        //同时设置当前云台不处于巡航状态�
                        serialPortCommServer.getIsCruising().put(ptzIP, Boolean.FALSE);
                    }
                    /*逐渐让热成像对准中心�
                    4点区域法，左上（151.171），右上�28�71），左下�52�14），右下�28�14），�92�44）为中心点�
                    得到当前的角�
                     */
                    String currentAngleX = serialPortCommServer.getAngleXString(ptzIP);
                    String currentAngleY = serialPortCommServer.getAngleYString(ptzIP);
                    int maxHeatValue = serialPortCommServer.getAlertMax(infraredSetupIP);
                    System.out.println("当前热值：" + maxHeatValue);
                    System.out.println("热成像X:" + heatPosX + ",当前水平角度" + currentAngleX);
                    System.out.println("热成像Y:" + heatPosY + ",当前垂直角度" + currentAngleY);
                    //X|Y|AngleX|AngleY|MaxValue|Time
                    serialPortCommServer.getSceneFireAlarmInfo().put(ptzIP, heatPosX + "|" + heatPosY + "|" + currentAngleX + "|" + currentAngleY + "|" + maxHeatValue + "|" + new Date().getTime());
                    //如果水平方向，小�92，水平逆时针转动。否则顺时针
                    //X方向最高热值与中心点的像素�
                    int dValueX = 0;
                    //Y方向最高热值与中心点的像素�
                    int dValueY = 0;
                    double finalPTZAngleX = Double.parseDouble(currentAngleX);
                    double finalPTZAngleY = Double.parseDouble(currentAngleY);
                    if (heatPosX < (infraredPixelX / 2)) {
                        dValueX = (infraredPixelX / 2) - heatPosX;
                        if ((finalPTZAngleX - dValueX * anglePerPixelX) < 0) {
                            finalPTZAngleX = 360 + (finalPTZAngleX - dValueX * anglePerPixelX);//如果小于0，则�60循环补差�
                        } else if ((finalPTZAngleX - dValueX * anglePerPixelX) == 0) {
                            finalPTZAngleX = 0;
                        } else {
                            finalPTZAngleX = finalPTZAngleX - dValueX * anglePerPixelX;
                        }
                    } else if (heatPosX > (infraredPixelX / 2)) {
                        dValueX = heatPosX - (infraredPixelX / 2);
                        if ((finalPTZAngleX + dValueX * anglePerPixelX) > 360) {
                            finalPTZAngleX = (finalPTZAngleX + dValueX * anglePerPixelX) - 360;//如果小于0，则�60循环补差�
                        } else if ((finalPTZAngleX + dValueX * anglePerPixelX) == 360) {
                            finalPTZAngleX = 0;
                        } else {
                            finalPTZAngleX = finalPTZAngleX + dValueX * anglePerPixelX;
                        }
                    }

                    //垂直方向，如果小�44，向下转动。否则向�
                    if (heatPosY < (infraredPixelY / 2)) {
                        dValueY = (infraredPixelY / 2) - heatPosY;
                        if ((finalPTZAngleY + dValueY * anglePerPixelY) > 90) {
                            finalPTZAngleY = 90;
                        } else if ((finalPTZAngleY + dValueY * anglePerPixelY) == 90) {
                            finalPTZAngleY = 90;
                        } else {
                            finalPTZAngleY = finalPTZAngleY + dValueY * anglePerPixelY;
                        }
                    } else {
                        dValueY = heatPosY - (infraredPixelY / 2);
                        if ((finalPTZAngleY - dValueY * anglePerPixelY) < 0) {
                            finalPTZAngleY = 0;
                        } else if ((finalPTZAngleY - dValueY * anglePerPixelY) == 0) {
                            finalPTZAngleY = 0;
                        } else {
                            finalPTZAngleY = finalPTZAngleY - dValueY * anglePerPixelY;
                        }
                    }

                    //给角度取2位小数�
                    finalPTZAngleX = Math.round(finalPTZAngleX * 100) / 100d;
                    finalPTZAngleY = Math.round(finalPTZAngleY * 100) / 100d;
                    System.out.println("最终要求水平角度：" + finalPTZAngleX);
                    System.out.println("最终要求垂直角度：" + finalPTZAngleY);
                    /*
                     * 准备好角度以后，进行角度调整命令。在角度调整后，计算�度对于热成像方位值变化的比例。然后进行一次命令调整�
                     */
                    String adjustXCommand = PTZUtil.getPELCODCommandHexString(1, 0, 0x4B, (int) Math.floor(finalPTZAngleX), (int) Math.floor((finalPTZAngleX - Math.floor(finalPTZAngleX)) * 100), "ANGLE_X", ptz.getBrandType());
                    String adjustYCommand = PTZUtil.getPELCODCommandHexString(1, 0, 0x4D, (int) Math.floor(finalPTZAngleY), (int) Math.floor((finalPTZAngleY - Math.floor(finalPTZAngleY)) * 100), "ANGLE_Y", ptz.getBrandType());
                    serialPortCommServer.pushCommand(ptzIP, adjustXCommand);
                    serialPortCommServer.pushCommand(ptzIP, adjustYCommand);
                    //设置正在置中状态位�
                    serialPortCommServer.getIsMovingCenterForFireAlarm().put(ptzIP, Boolean.TRUE);
                    //这里要处理小数位的表达问题，比如32.07，其�7�，这里如果字符串相加要处理为�0
                    serialPortCommServer.getFinalMovingCenterForFireAlarm().put(ptzIP, finalPTZAngleX + "|" + adjustXCommand + "|" + finalPTZAngleY + "|" + adjustYCommand + "|" + new Date().getTime());
                }
            } else if (serialPortCommServer.getIsMovingCenterForFireAlarm().get(ptzIP) == Boolean.TRUE) {
                //如果当前正在微调。判断微调角度是否到位。如果不到位，继续调整�
                String currentAngleX = serialPortCommServer.getAngleXString(ptzIP);
                String currentAngleY = serialPortCommServer.getAngleYString(ptzIP);
                //System.out.println("火警调后的当前角度：" + currentAngleX + "," + currentAngleY);
                String finalMovingInfo = serialPortCommServer.getFinalMovingCenterForFireAlarm().get(ptzIP);
                Float currentfloatAngleX = Float.parseFloat(currentAngleX);
                Float currentfloatAngleY = Float.parseFloat(currentAngleY);
                Float finalAngleX = Float.parseFloat(finalMovingInfo.split("\\|")[0]);
                Float finalAngleY = Float.parseFloat(finalMovingInfo.split("\\|")[2]);
                //System.out.println("火警最后要求对准角度：" + finalAngleX + "," + finalAngleY);
                Long finalBeginTime = Long.parseLong(finalMovingInfo.split("\\|")[4]);
                //各误差在0.5之内，并且已经过�秒，即马上停止微调阶段�
                if (Math.abs(currentfloatAngleX - finalAngleX) < 0.03 && Math.abs(currentfloatAngleY - finalAngleY) < 0.03 && new Date().getTime() - finalBeginTime > 2000) {
                    //调整到位后，清除微调信息。角度误差在0.5度时，停止调整�
                    //到位后，依然不允许巡航，要手工允许巡航�
                    //serialPortCommServer.getAllowCruise().put(ptzIP, Boolean.TRUE);
                    serialPortCommServer.getIsMovingCenterForFireAlarm().remove(ptzIP);
                    System.out.println("最终调整已经到位了 --------------------------------------------------------------------------");
                    System.out.println("最终调整已经到位后，角度：" + currentfloatAngleX + "," + currentfloatAngleY);
                    int heatPosX = serialPortCommServer.getAlertX(infraredSetupIP);
                    int heatPosY = serialPortCommServer.getAlertY(infraredSetupIP);
                    System.out.println("最终调整已经到位后，热值位置：" + heatPosX + "," + heatPosY);
                } else if ((Math.abs(currentfloatAngleX - finalAngleX) > 0.03 || Math.abs(currentfloatAngleY - finalAngleY) > 0.03) && new Date().getTime() - finalBeginTime > 2000) {
                    //只要有一个角度有误差，且时间超过1秒，就继续发送调整命令�
                    serialPortCommServer.pushCommand(ptzIP, finalMovingInfo.split("\\|")[1]);
                    serialPortCommServer.pushCommand(ptzIP, finalMovingInfo.split("\\|")[3]);
                    System.out.println("火警发生后，云台调整中，当前的角度：" + currentfloatAngleX + "," + currentfloatAngleY);
                    //设置正在置中状态位�
                    serialPortCommServer.getFinalMovingCenterForFireAlarm().put(ptzIP, finalMovingInfo.split("\\|")[0] + "|" + finalMovingInfo.split("\\|")[1] + "|" + finalMovingInfo.split("\\|")[2] + "|" + finalMovingInfo.split("\\|")[3] + "|" + new Date().getTime());
                }
            }
        }
    }

    @Scheduled(fixedDelay = 3000)
    public synchronized void showInfo() {
        for (PTZ ptz : ptzs) {
            String ptzIP = ptz.getPelcodCommandUrl();
            String currentAngleX = serialPortCommServer.getAngleXString(ptzIP);
            String currentAngleY = serialPortCommServer.getAngleYString(ptzIP);
            //System.out.println("当前的角度信息：" + currentAngleX + "," + currentAngleY);
            String infraredSetupIP = ptz.getInfraredCircuitUrl();
            int heatPosX = serialPortCommServer.getAlertX(infraredSetupIP);
            int heatPosY = serialPortCommServer.getAlertY(infraredSetupIP);
            //System.out.println("当前的最高热值像素信息：" + heatPosX + "," + heatPosY);

            //System.out.println("最高热值：" + serialPortCommServer.getAlertMax(infraredSetupIP));
        }
    }

    //定时的取出所有可用的云台信息关注入相关的变量�
    @Scheduled(fixedDelay = 5000)
    public synchronized void getPTZsInfo() {
        ptzs = ptzService.getAllPTZs();
    }
}
