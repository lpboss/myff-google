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
            Ext.onReady(function() {
                //当前云台是否转动。
                var isTurning = false;
                //按钮
                var upButton = Ext.create('Ext.Button', {
                    text: '上',
                    iconCls: 'arrow_up',
                    renderTo:'ptz_up',
                    handler: function(){
                        if (isTurning == false){
                            ptzAction("up");
                        }else{
                            ptzAction("stop");
                        }
                    }
                })
                
                var downButton = Ext.create('Ext.Button', {
                    text: '下',
                    iconCls: 'arrow_down',
                    renderTo:'ptz_down',
                    handler: function(){
                        if (isTurning == false){
                            ptzAction("down");
                        }else{
                            ptzAction("stop");
                        }
                    }
                })
                
                var rightButton = Ext.create('Ext.Button', {
                    text: '右',
                    iconCls: 'arrow_right',
                    renderTo:'ptz_right',
                    handler: function(){
                        if (isTurning == false){
                            ptzAction("right");
                        }else{
                            ptzAction("stop");
                        }
                    }
                })
                
                var leftButton = Ext.create('Ext.Button', {
                    text: '左',
                    iconCls: 'arrow_left',
                    renderTo:'ptz_left',
                    handler: function(){
                        if (isTurning == false){
                            ptzAction("left");
                        }else{
                            ptzAction("stop");
                        }
                    }
                })
                
            });
            
            function ptzAction(ptzActionStr){
                Ext.Ajax.request({
                    url : '<%=basePath%>/ptz/ptzAction.htm',
                    success : function (result, request) {
                        if(ptzActionStr == "stop"){
                            isTurning = false;
                        }else{
                            isTurning = true;
                        }
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
                    <OBJECT classid="clsid:9BE31822-FDAD-461B-AD51-BE1D1C159921" width="600" height="450" id="vlc" events="True">
                        <param name="Src" value="rtsp://admin:12345@192.168.254.64/h264/ch1/main/av_stream" />
                        <param name="ShowDisplay" value="True" />
                        <param name="AutoLoop" value="False" />
                        <param name="AutoPlay" value="true" />
                        <embed id="vlcEmb"  type="application/x-google-vlc-plugin" version="VideoLAN.VLCPlugin.2" autoplay="yes" loop="no" width="600" height="450" target="rtsp://admin:12345@192.168.254.64/h264/ch1/main/av_stream" ></embed>
                    </OBJECT>
                </td>
                <td>
                    <div id="visible_light_camera">
                        <OBJECT classid="clsid:9BE31822-FDAD-461B-AD51-BE1D1C159921" width="800" height="450" id="vlc" events="True">
                            <param name="Src" value="rtsp://admin:12345@192.168.254.64/h264/ch2/main/av_stream" />
                            <param name="ShowDisplay" value="True" />
                            <param name="AutoLoop" value="False" />
                            <param name="AutoPlay" value="true" />
                            <embed id="vlcEmb"  type="application/x-google-vlc-plugin" version="VideoLAN.VLCPlugin.2" autoplay="yes" loop="no" width="800" height="450" target="rtsp://admin:12345@192.168.254.64/h264/ch2/main/av_stream" ></embed>
                        </OBJECT>
                    </div>
                </td>
            </tr>
            <tr>                
                <td>
                    <object id="map" type="application/x-shockwave-flash" data="map.swf" width="600" height="300">
                        <param name="movie" value="map.swf" />
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
                                <td></td>
                                <td id="ptz_up"></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td id="ptz_left"></td>
                                <td></td>
                                <td  id="ptz_right"></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td  id="ptz_down"></td>
                                <td></td>
                            </tr>
                        </table>
                    </div>
                </td>
            </tr>
        </table>    
    </body>
</html>
