/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.util;

import ff.model.PTZ;
import java.util.logging.Logger;

import ff.server.SerialPortCommServer;
import ff.service.PTZService;

/**
 *
 * @author jerry
 * 控制云台相关的所有动作
 */
public class PTZUtil {

    static Logger logger = Logger.getLogger(PTZUtil.class.getName());
    private SerialPortCommServer serialPortCommServer;
    private PTZService ptzService;

    public void setPtzService(PTZService ptzService) {
        this.ptzService = ptzService;
    }

    public void setSerialPortCommServer(SerialPortCommServer serialPortCommServer) {
        this.serialPortCommServer = serialPortCommServer;
    }

    public void PTZAction(Long ptzId, String ptzAction) {
        logger.info("ptzAction:" + ptzAction + ",   0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
        //先固定一个云台测试。
        PTZ ptz = ptzService.getPTZById(ptzId);
        String ptzIP = ptz.getPelcodCommandUrl();
        Integer shiftStep = ptz.getShiftStep();

        boolean connResult;

        //先停止再发送新命令。
        serialPortCommServer.pushCommand(ptzIP, "FF 01 00 00 00 00 01");


        //发送命令前，先设置停止巡航状态位。
        serialPortCommServer.getAllowCruise().put(ptzIP, Boolean.FALSE);
        //动手控制前，先清空云台的所有命令。
        serialPortCommServer.getCommandMap().remove(ptzIP);
        String pelcodCommand = "";
        if (ptzAction.equals("up")) {
            setCruiseBreakpoint(ptzIP);
            pelcodCommand = PTZUtil.getPELCODCommandHexString(1, 0, 0x08, 0, shiftStep, "right", ptz.getBrandType());
            serialPortCommServer.pushCommand(ptzIP, pelcodCommand);
            //logger.info("FF 01 00 08 00 3F 48 UP..........................." + connResult);
        } else if (ptzAction.equals("up_left")) {
            setCruiseBreakpoint(ptzIP);
            pelcodCommand = PTZUtil.getPELCODCommandHexString(1, 0, 0x0C, shiftStep, shiftStep, "up_left", ptz.getBrandType());
            serialPortCommServer.pushCommand(ptzIP, pelcodCommand);
            //logger.info("FF 01 00 10 00 3F 50 DOWN........................." + connResult);FF 01 00 14 2F 2F 73
        } else if (ptzAction.equals("up_right")) {
            setCruiseBreakpoint(ptzIP);
            pelcodCommand = PTZUtil.getPELCODCommandHexString(1, 0, 0x0A, shiftStep, shiftStep, "up_right", ptz.getBrandType());
            serialPortCommServer.pushCommand(ptzIP, pelcodCommand);
        } else if (ptzAction.equals("down")) {
            setCruiseBreakpoint(ptzIP);
            pelcodCommand = PTZUtil.getPELCODCommandHexString(1, 0, 0x10, 0, shiftStep, "down", ptz.getBrandType());
            serialPortCommServer.pushCommand(ptzIP, pelcodCommand);
        } else if (ptzAction.equals("down_left")) {
            setCruiseBreakpoint(ptzIP);
            pelcodCommand = PTZUtil.getPELCODCommandHexString(1, 0, 0x14, shiftStep, shiftStep, "down_left", ptz.getBrandType());
            serialPortCommServer.pushCommand(ptzIP, pelcodCommand);
        } else if (ptzAction.equals("down_right")) {
            setCruiseBreakpoint(ptzIP);
            pelcodCommand = PTZUtil.getPELCODCommandHexString(1, 0, 0x12, shiftStep, shiftStep, "down_right", ptz.getBrandType());
            serialPortCommServer.pushCommand(ptzIP, pelcodCommand);
        } else if (ptzAction.equals("right")) {
            setCruiseBreakpoint(ptzIP);
            pelcodCommand = PTZUtil.getPELCODCommandHexString(1, 0, 0x02, shiftStep, 0, "right", ptz.getBrandType());
            serialPortCommServer.pushCommand(ptzIP, pelcodCommand);
        } else if (ptzAction.equals("left")) {
            setCruiseBreakpoint(ptzIP);
            pelcodCommand = PTZUtil.getPELCODCommandHexString(1, 0, 0x04, shiftStep, 0, "left", ptz.getBrandType());
            serialPortCommServer.pushCommand(ptzIP, pelcodCommand);
        } else if (ptzAction.equals("stop")) {
            setCruiseBreakpoint(ptzIP);
            serialPortCommServer.pushCommand(ptzIP, "FF 01 00 00 00 00 01");
            //logger.info("FF 01 00 00 00 00 01 STOP........................." + connResult);
        } else if (ptzAction.equals("cruise")) {
            serialPortCommServer.getAllowCruise().put(ptzIP, Boolean.TRUE);
            serialPortCommServer.getIsCruising().put(ptzIP, Boolean.FALSE);
            serialPortCommServer.getIsCruisingPresetAngleY().remove(ptzIP);
            serialPortCommServer.getIsAdjustingXYForBreakpoint().remove(ptzIP);
            //为了方便测试程度，下面取消返回断点的行为。
            serialPortCommServer.getCruiseBreakpoint().remove(ptzIP);
            //允许再次报火警
            serialPortCommServer.getAllowAlarm().put(ptzIP, Boolean.TRUE);
        } else if (ptzAction.equals("clear_fire_alarm")) {
            //清了火警信息后，只有点巡航按钮后，方可再次对火情发出火警
            System.out.println("已经清空有关火警状态的信息。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。");
            serialPortCommServer.getAllowCruise().put(ptzIP, Boolean.FALSE);
            serialPortCommServer.getCruiseBreakpoint().remove(ptzIP);
            serialPortCommServer.getIsAdjustingXYForBreakpoint().remove(ptzIP);
            serialPortCommServer.getIsMovingCenterForFireAlarm().remove(ptzIP);
            //不允许再次报火警
            serialPortCommServer.getAllowAlarm().put(ptzIP, Boolean.FALSE);
        }
    }

    private void setCruiseBreakpoint(String ip) {
        //如果正在巡航，则在发送其它命令前，先保存断点。
        if (serialPortCommServer.getIsCruising().get(ip) != null && serialPortCommServer.getIsCruising().get(ip) == Boolean.TRUE) {
            if (serialPortCommServer.getCruiseBreakpoint().get(ip) == null) {
                serialPortCommServer.getCruiseBreakpoint().put(ip, serialPortCommServer.getAngleXString(ip) + "|" + serialPortCommServer.getAngleYString(ip));
            }
        }
    }

    /**
     *
     * @author jerry
     * 根据命令，参数，得到整个命令的16位值的，字符串。
     * 注意：FF会被自动作为前缀，所以请不要在command中再传送FF，即不传从0位，1-3位，作为command传送，4，5作为params传送，
     *       第6位会由方法计算并自动追加上。即返回的命令会是一个完整的，可执行的命令字符串。同时传送的double参数以16进制表示。
     */
    public static String getPELCODCommandHexString(int address, int command1, int command2, int param1, int param2, String angleType, String ptzType) {
        StringBuilder command = new StringBuilder();
        command.append("FF ");
        //处理地址
        if (Integer.toHexString(address).length() == 1) {
            command.append("0");
            command.append(Integer.toHexString(address));
        } else {
            command.append(Integer.toHexString(address));
        }
        command.append(" ");
        //处理命令1
        if (Integer.toHexString(command1).length() == 1) {
            command.append("0");
            command.append(Integer.toHexString(command1));
        } else {
            command.append(Integer.toHexString(command1));
        }
        command.append(" ");
        //处理命令2
        if (Integer.toHexString(command2).length() == 1) {
            command.append("0");
            command.append(Integer.toHexString(command2));
        } else {
            command.append(Integer.toHexString(command2));
        }
        command.append(" ");

        //这里要判断参数，对于角度的处理是二个度数相加再求的总的16进制，再拆分为二个参数。314.12即31412，得7AB4，再拆分。
        //处理参数1

        if (angleType.equals("ANGLE_X") || angleType.equals("ANGLE_Y")) {
            int param3 = 0;
            if (angleType.equals("ANGLE_X")) {
                param3 = param1 * 100 + param2;
            } else {
                if (ptzType.equalsIgnoreCase("FY")) {
                    param3 = param1 * 100 + param2;
                } else {
                    param3 = 36000 - (param1 * 100 + param2);
                }

            }
            //System.out.println("Type:" + type + ",Param1:" + param1 + ",Param2:" + param2 + ",Param3(36000 - (param1 * 100 + param2)):" + param3 + ",2222222222222222222222222");

            String param3Hex = Integer.toHexString(param3);
            if (param3Hex.length() == 1) {
                command.append("00 0");
                command.append(param3Hex);
            } else if (param3Hex.length() == 2) {
                command.append("00 ");
                command.append(param3Hex);
            } else if (param3Hex.length() == 3) {
                command.append("0");
                command.append(param3Hex.substring(0, 1));
                command.append(" ");
                command.append(param3Hex.substring(1, 3));
            } else if (param3Hex.length() == 4) {
                command.append(param3Hex.substring(0, 2));
                command.append(" ");
                command.append(param3Hex.substring(2, 4));
            }
            command.append(" ");
        } else {
            //其它情况如何处理，暂不明确，先放着。
            if (Integer.toHexString(param1).length() == 1) {
                command.append("0");
                command.append(Integer.toHexString(param1));
            } else {
                command.append(Integer.toHexString(param1));
            }
            command.append(" ");
            //处理参数2
            if (Integer.toHexString(param2).length() == 1) {
                command.append("0");
                command.append(Integer.toHexString(param2));
            } else {
                command.append(Integer.toHexString(param2));
            }
            command.append(" ");
        }

        //处理check sum
        int checkSum = 0;
        if (angleType.equals("ANGLE_X") || angleType.equals("ANGLE_Y")) {
            String curruentCommand = command.toString().toUpperCase();
            checkSum = address + command1 + command2 + Integer.valueOf(curruentCommand.substring(12, 14), 16) + Integer.valueOf(curruentCommand.substring(15, 17), 16);
        } else {
            checkSum = address + command2 + param1 + param2;
        }
        String checkSumStr = Integer.toHexString(checkSum);
        if (checkSumStr.length() == 1) {
            command.append("0");
            command.append(checkSumStr);
        } else if (checkSumStr.length() == 2) {
            command.append(checkSumStr);
        } else {
            //处理大于2的情况。截取后二位。
            checkSumStr = checkSumStr.substring(checkSumStr.length() - 2, checkSumStr.length());
            command.append(checkSumStr);
        }
        //System.out.println("type:" + type + ",command:" + command.toString().toUpperCase() + " ,222222222222222222222222222222222222222222222222222222," + param1 + ":" + param2);
        return command.toString().toUpperCase();
    }
}
