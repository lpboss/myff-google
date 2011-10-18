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
        
            //当前云台是否转动。
            var isTurning = false;
            var turningDirection = "stop";
            
            Ext.onReady(function() {
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
                
                var leftButton = Ext.create('Ext.Button', {
                    text: '设置当前监控云台为192.168.254.65',
                    renderTo:'ptz_setChannel',
                    handler: function(){
                    	document.getElementById("map").setChannel("192.168.254.65","../images/map.png");
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
                        action_type : ptzActionStr
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
                        <embed id="vlcEmb"  type="application/x-google-vlc-plugin" version="VideoLAN.VLCPlugin.2" autoplay="yes" loop="no" width="400" height="300" target="rtsp://admin:12345@192.168.254.64/h264/ch1/main/av_stream" ></embed>
                    </OBJECT>
                </td>
                <td>
                    <div id="visible_light_camera">
                        <OBJECT classid="clsid:9BE31822-FDAD-461B-AD51-BE1D1C159921" width="400" height="300" id="vlc" events="True">
                            <param name="Src" value="rtsp://admin:12345@192.168.254.64/h264/ch2/main/av_stream" />
                            <param name="ShowDisplay" value="True" />
                            <param name="AutoLoop" value="False" />
                            <param name="AutoPlay" value="true" />
                            <embed id="vlcEmb"  type="application/x-google-vlc-plugin" version="VideoLAN.VLCPlugin.2" autoplay="yes" loop="no" width="400" height="300" target="rtsp://admin:12345@192.168.254.64/h264/ch2/main/av_stream" ></embed>
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
                            </tr>
                            <tr>
                                <td id="ptz_left"></td>
                                <td id="ptz_cruise"></td>
                                <td id="ptz_right"></td>
                            </tr>
                            <tr>
                                <td id="ptz_down_left"></td>
                                <td id="ptz_down"></td>
                                <td id="ptz_down_right"></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td></td>
                                <td id="ptz_setChannel"></td>
                            </tr>                            
                        </table>
                    </div>
                </td>
            </tr>
        </table>    
    </body>
</html>
