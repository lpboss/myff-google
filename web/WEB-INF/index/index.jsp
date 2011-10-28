<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>浩海森林防火预警平台</title>
<script src="<%=basePath%>javascripts/ext4/ext-all.js" type="text/javascript"></script>
<script src="<%=basePath%>javascripts/ext4/locale/ext-lang-zh_CN.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>javascripts/greybox/gb_styles.css" media="all" />
<script type="text/javascript">var GB_ROOT_DIR = "<%=basePath%>javascripts/greybox/";</script>
<script type="text/javascript" src="<%=basePath%>javascripts/greybox/AJS.js"></script>
<script type="text/javascript" src="<%=basePath%>javascripts/greybox/AJS_fx.js"></script>
<script type="text/javascript" src="<%=basePath%>javascripts/greybox/gb_scripts.js"></script>
<script>
//当前云台是否转动。
var isTurning = false;
//当前正在转动的方向
var turningDirection = "stop";

function ptzAction(ptzActionStr){
	//如果方向相同，就要停止转动。
	//console.warn("Before:   ptzActionStr:",ptzActionStr,",turningDirection:",turningDirection,",isTurning:"+isTurning);
	if (ptzActionStr == turningDirection){
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
			//console.warn("After :   ptzActionStr:",ptzActionStr,",turningDirection:",turningDirection,",isTurning:"+isTurning);
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

function setChannel(){
	document.getElementById("map").setChannel("192.168.254.65","192.168.1.50","../images/map.png");
}
</script>
</head>

<body>
<p>监控预览 报警事件 录像查询 <a href="index2.htm" title="用户设置" rel="gb_page[800, 600]">用户设置</a> 系统设置</p>
<table width="100%" border="1">
  <tr>
    <td width="30%">
<OBJECT classid="clsid:9BE31822-FDAD-461B-AD51-BE1D1C159921" width="330" height="270" id="vlc" events="True">
            <param name="Src" value="rtsp://admin:12345@192.168.254.64/h264/ch1/main/av_stream" />
            <param name="ShowDisplay" value="True" />
            <param name="AutoLoop" value="False" />
            <param name="AutoPlay" value="true" />
            <embed id="vlcEmb"  type="application/x-google-vlc-plugin" version="VideoLAN.VLCPlugin.2" autoplay="yes" loop="no" width="330" height="270" target="rtsp://admin:12345@192.168.254.64/h264/ch1/main/av_stream" ></embed>
        </OBJECT>
    </td>
    <td width="70%" rowspan="2">
<OBJECT classid="clsid:9BE31822-FDAD-461B-AD51-BE1D1C159921" width="960" height="540" id="vlc" events="True">
            <param name="Src" value="rtsp://admin:12345@192.168.254.64/h264/ch2/main/av_stream" />
            <param name="ShowDisplay" value="True" />
            <param name="AutoLoop" value="False" />
            <param name="AutoPlay" value="true" />
            <embed id="vlcEmb"  type="application/x-google-vlc-plugin" version="VideoLAN.VLCPlugin.2" autoplay="yes" loop="no" width="960" height="540" target="rtsp://admin:12345@192.168.254.64/h264/ch2/main/av_stream" ></embed>
        </OBJECT>
    </td>
  </tr>
  <tr>
    <td>
<object id="map" type="application/x-shockwave-flash" data="<%=basePath%>images/map.swf" width="330" height="270">
            <param name="movie" value="<%=basePath%>images/map.swf" />
            <param name="quality" value="high" />
            <param name="allowScriptAccess" value="sameDomain" />
            <param name="allowFullScreen" value="true" />
            <param name="FlashVars" value="port=8004" />
        </object>
    </td>
  </tr>
</table>
<table width="100%" border="1">
  <tr>
    <td width="20%" nowrap="nowrap"><input type="button" name="button" id="button" value="亚安云台" onclick="setChannel();" /></td>
    <td width="20%" nowrap="nowrap"><input type="button" name="button3" id="button3" value="左上" onclick="ptzAction('up_left');" />
    <input type="button" name="button4" id="button4" value=" 上 " onclick="ptzAction('up');" />
    <input type="button" name="button5" id="button5" value="右上" onclick="ptzAction('up_right');" /></td>
    <td width="20%" nowrap="nowrap"><input type="button" name="button12" id="button12" value="远" />
      调焦
    <input type="button" name="button14" id="button14" value="近" /></td>
    <td width="20%" nowrap="nowrap"><input type="button" name="button18" id="button18" value="螺旋扫描" />
    <input type="button" name="button33" id="button35" value="设置" /></td>
    <td nowrap="nowrap"><input type="button" name="button30" id="button31" value="显示模式" />      <input type="button" name="button28" id="button29" value="轮询监视" /></td>
  </tr>
  <tr>
    <td nowrap="nowrap"><input type="button" name="button2" id="button2" value="飞跃云台" /></td>
    <td nowrap="nowrap"><input type="button" name="button6" id="button6" value=" 左 " onclick="ptzAction('left');" />
    <input type="button" name="button7" id="button7" value="巡航" onclick="ptzAction('cruise');" />
    <input type="button" name="button8" id="button8" value=" 右 " onclick="ptzAction('right');" /></td>
    <td nowrap="nowrap"><input type="button" name="button13" id="button13" value="左" />
      聚焦
        <input type="button" name="button15" id="button15" value="右" /></td>
    <td nowrap="nowrap"><input type="button" name="button16" id="button16" value="削苹果皮" />
    <input type="button" name="button34" id="button36" value="设置" /></td>
    <td nowrap="nowrap"><input type="button" name="button27" id="button28" value="图像抓拍" />
    <input type="button" name="button29" id="button30" value="声音对讲" /></td>
  </tr>
  <tr>
    <td nowrap="nowrap">&nbsp;</td>
    <td nowrap="nowrap">
    <input type="button" name="button10" id="button10" value="左下" onclick="ptzAction('down_left');" />
    <input type="button" name="button11" id="button11" value=" 下 " onclick="ptzAction('down');" />
    <input type="button" name="button9" id="button9" value="右下" onclick="ptzAction('down_right');" /></td>
    <td nowrap="nowrap"><input type="button" name="button31" id="button32" value="大" />
光圈
  <input type="button" name="button31" id="button33" value="小" /></td>
    <td nowrap="nowrap"><input type="button" name="button17" id="button17" value="预置点巡航" />
    <input type="button" name="button35" id="button37" value="设置" /></td>
    <td nowrap="nowrap"><input type="button" name="button19" id="button19" value="雨刷开" />
    <input type="button" name="button19" id="button20" value="透雾开" />
    <input type="button" name="button32" id="button34" value="声音开" /></td>
  </tr>
</table>

</body>
</html>