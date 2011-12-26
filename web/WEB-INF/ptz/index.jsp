<%-- 
    Document   : index
    Created on : 2011-9-30, 9:43:41
    Author     : jerry
--%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script>
            //默认只只控一个云台，这是测试时预置为1.
            ptzAssignedStepStore = Ext.create('Ext.data.ArrayStore', {
                autoDestroy: true,
                storeId: 'ptz_assigned_step_store',
                // reader configs
                idIndex: 0,
                fields: ['text'],
                data : ptzAssignedStepArray
            });
            
            var ptzAssignedStep = Ext.create('Ext.form.ComboBox', {
                fieldLabel: '移动步长',
                store: ptzAssignedStepStore,
                valueField:'text',
                displayField:'text',
                allowBlank: false,
                typeAhead: true,
                mode: 'local',
                name:'ptz_assigned_step',
                emptyText:'选择步长...',
                anchor: '95%',
                renderTo:'ptz_assigned_step'
            });
        
            var ptzId = 1;
           alarmSound ;
            soundManager.onready(function(oStatus) {
                if (!oStatus.success) {
                    return false;
                }
                
                alarmSound = soundManager.createSound({
                    id:'alarm_sound',
                    url:'<%=basePath%>javascripts/sound/alarmsound.mp3'
                });
                
            });

  
            //当前云台是否转动。
            var isTurning = false;
            //当前正在转动的方向
            var turningDirection = "stop";

            Ext.onReady(function() {
                getPTZAlarmsInfo = function(){
                    Ext.Ajax.request({
                        url : '<%=basePath%>ptz/getIsAlarmPTZs.htm',
                        success : function (result, request) {
                            var alarmJSON = result.responseText
                            alarmJSON =  Ext.JSON.decode(alarmJSON);
                            if (alarmJSON.root.length > 0){
                                ptzAlarmLabel.setText("当前火警情况:"+alarmJSON.root[0].name);
                                alarmSound.play({
                                    onfinish: function() {
                                        loopSound(alarmSound);
                                    }
                                });
                            }else{
                                alarmSound.stop(alarmSound);
                                ptzAlarmLabel.setText("当前火警情况:无");
                            }
                        },
                        failure : function (result, request){
                        },
                        method : 'GET'
                    });
                }

                //Extjs 4
                //
                var task = {
                    run: function(){
                        getPTZAlarmsInfo();
                    },
                    interval: 8000 //3 second
                }
                Ext.TaskManager.start(task);
                
                var ptzAlarmLabel = Ext.create('Ext.form.Label', {
                    name: 'ptz_alarm_label',
                    renderTo:'ptz_alarm_label'
                })
                ptzAlarmLabel.setText("当前火警情况:无");
        
                //按钮                
                var cruise = Ext.create('Ext.Button', {
                    text: '巡航',
                    iconCls: 'blue_point',
                    renderTo:'ptz_cruise',
                    handler: function(){
                        ptzAction("cruise");
                    }
                })

                var upButton = Ext.create('Ext.Button', {
                    text: '上&nbsp;&nbsp;&nbsp;&nbsp;',
                    iconCls: 'arrow_up',
                    renderTo:'ptz_up',
                    handler: function(){
                        ptzAction("up");
                    }
                })

                var upLeftButton = Ext.create('Ext.Button', {
                    text: '左上',
                    iconCls: 'arrow_up_left',
                    renderTo:'ptz_up_left',
                    handler: function(){
                        ptzAction("up_left");
                    }
                })

                var upRightButton = Ext.create('Ext.Button', {
                    text: '右上',
                    iconCls: 'arrow_up_right',
                    renderTo:'ptz_up_right',
                    handler: function(){
                        ptzAction("up_right");
                    }
                })

                var downButton = Ext.create('Ext.Button', {
                    text: '下&nbsp;&nbsp;&nbsp;&nbsp;',
                    iconCls: 'arrow_down',
                    renderTo:'ptz_down',
                    handler: function(){
                        ptzAction("down");
                    }
                })

                var downLeftButton = Ext.create('Ext.Button', {
                    text: '左下',
                    iconCls: 'arrow_down_left',
                    renderTo:'ptz_down_left',
                    handler: function(){
                        ptzAction("down_left");
                    }
                })

                var downRightButton = Ext.create('Ext.Button', {
                    text: '右下',
                    iconCls: 'arrow_down_right',
                    renderTo:'ptz_down_right',
                    handler: function(){
                        ptzAction("down_right");
                    }
                })

                var rightButton = Ext.create('Ext.Button', {
                    text: '右&nbsp;&nbsp;&nbsp;&nbsp;',
                    iconCls: 'arrow_right',
                    renderTo:'ptz_right',
                    handler: function(){
                        ptzAction("right");
                    }
                })

                var leftButton = Ext.create('Ext.Button', {
                    text: '左&nbsp;&nbsp;&nbsp;&nbsp;',
                    iconCls: 'arrow_left',
                    renderTo:'ptz_left',
                    handler: function(){
                        ptzAction("left");
                    }
                })  

                var clearFireAlarmButton = Ext.create('Ext.Button', {
                    text: '清火警相关值',
                    //iconCls: 'arrow_down_right',
                    renderTo:'clear_fire_alarm',
                    handler: function(){
                        ptzAction("clear_fire_alarm");
                    }
                })

                var stopFireAlarmButton = Ext.create('Ext.Button', {
                    text: '停止报警',
                    //iconCls: 'arrow_down_right',
                    renderTo:'stop_fire_alarm',
                    handler: function(){
                        ptzAction("stop_fire_alarm");                        
                    }
                })
            });

            function ptzAction(ptzActionStr){
                //如果方向相同，就要停止转动。
                console.warn("Before:   ptzActionStr:",ptzActionStr,",turningDirection:",turningDirection,",isTurning:"+isTurning);
                if (ptzActionStr === turningDirection){
                    isTurning = false;
                    turningDirection = "stop";
                    ptzActionStr = "stop";
                }

                Ext.Ajax.request({
                    url : '<%=basePath%>ptz/ptzAction.htm',
                    success : function (result, request) {
                        turningDirection = ptzActionStr;
                        if(ptzActionStr == "stop"){
                            isTurning = false;
                        }else{
                            isTurning = true;
                        }
                        console.warn("After :   ptzActionStr:",ptzActionStr,",turningDirection:",turningDirection,",isTurning:"+isTurning);
                    },
                    failure : function (result, request){
                        Ext.MessageBox.show({
                            title: '消息',
                            msg: "通讯失败，请从新操作",
                            buttons: Ext.MessageBox.OK,
                            icon: Ext.MessageBox.WARNING
                        });
                    },
                    method : 'GET',
                    params : {
                        action_type : ptzActionStr,
                        ptz_id: ptzId,
                        assigned_step:ptzAssignedStep.getValue()                        
                    }
                });
            }    
        </script>
    </head>
    <body>
        <table>
            <tr>
                <td>
                    <OBJECT classid="clsid:9BE31822-FDAD-461B-AD51-BE1D1C159921" width="400" height="300" id="vlc" events="True">
                        <param name="Src" value="rtsp://admin:12345@192.168.254.64/h264/ch1/main/av_stream" />
                        <param name="ShowDisplay" value="True" />
                        <param name="AutoLoop" value="False" />
                        <param name="AutoPlay" value="true" />
                        <param name="rtsp-caching" value="80" />
                        <embed id="vlcEmb"  type="application/x-google-vlc-plugin" version="VideoLAN.VLCPlugin.2" autoplay="yes" loop="no" width="400" height="300" rtsp-caching="60" target="rtsp://admin:12345@192.168.254.64/h264/ch1/main/av_stream" ></embed>
                    </OBJECT>
                </td>
                <td>
                    <div id="visible_light_camera">
                        <OBJECT classid="clsid:9BE31822-FDAD-461B-AD51-BE1D1C159921" width="400" height="300" id="vlc" events="True">
                            <param name="Src" value="rtsp://admin:12345@192.168.254.64/h264/ch2/main/av_stream" />
                            <param name="ShowDisplay" value="True" />
                            <param name="AutoLoop" value="False" />
                            <param name="AutoPlay" value="true" />
                            <param name="rtsp-caching" value="80" />
                            <embed id="vlcEmb"  type="application/x-google-vlc-plugin" version="VideoLAN.VLCPlugin.2" autoplay="yes" loop="no" width="400" height="300" rtsp-caching="60" target="rtsp://admin:12345@192.168.254.64/h264/ch2/main/av_stream" ></embed>
                        </OBJECT>
                    </div>
                </td>
            </tr>
            <tr>                
                <td>
                    <object id="map" type="application/x-shockwave-flash" data="<%=basePath%>images/map.swf" width="400" height="400">
                        <param name="movie" value="<%=basePath%>images/map.swf" />
                        <param name="quality" value="high" />
                        <param name="allowScriptAccess" value="sameDomain" />
                        <param name="allowFullScreen" value="true" />
                        <param name="FlashVars" value="port=8004" />
                    </object>
                </td>
                <td>
                    <div id="ptz">
                        <table>
                            <tr>
                                <td id="ptz_up_left"></td>
                                <td id="ptz_up"></td>
                                <td id="ptz_up_right"></td>
                                <td id="clear_fire_alarm"></td>
                            </tr>
                            <tr>
                                <td id="ptz_left"></td>
                                <td id="ptz_cruise"></td>
                                <td id="ptz_right"></td>
                                <td id="stop_fire_alarm"></td>
                            </tr>
                            <tr>
                                <td id="ptz_down_left"></td>
                                <td id="ptz_down"></td>
                                <td id="ptz_down_right"></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td></td>
                                <td id="ptz_setChannel"></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td id="ptz_alarm_label" colspan="4"></td>
                            </tr>     
                            <tr>
                                <td id="ptz_assigned_step" colspan="4"></td>
                            </tr>   
                        </table>
                    </div>
                </td>
            </tr>
        </table>    
    </body>
</html>
