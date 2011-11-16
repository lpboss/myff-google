<%-- 
    Document   : newPTZ
    Created on : 2011-11-9, 20:14:28
    Author     : Haoqingmeng
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
        <title>添加云台信息</title>
        <script type="text/javascript">
            Ext.onReady(function(){
                
                var name = Ext.create('Ext.form.field.Text', {
                    fieldLabel: '名字',
                    allowBlank: false,
                    blankText: "用云台名字不能为空",
                    name: 'name',
                    anchor: '95%'
                });
                
                var controllUrl = Ext.create('Ext.form.field.Text', {
                    fieldLabel: '编码器IP',
                    name: 'controll_url',
                    anchor: '95%'
                });
                
                var pelcodCommandUrl = Ext.create('Ext.form.field.Text', {
                    fieldLabel: '通过串口,发pelcod的ip',
                    name: 'pelcod_command_url',
                    anchor: '95%'
                });
                
                var visibleCameraUrl = Ext.create('Ext.form.field.Text', {
                    fieldLabel: '可见光摄像机地址,模拟请参考controll_url',
                    name: 'visible_camera_url',
                    anchor: '95%'
                });
                
                var visibleRTSPUrl = Ext.create('Ext.form.field.Text', {
                    fieldLabel: '可见光RTSP流',            
                    name: 'visible_rtsp_url',
                    anchor: '95%'
                });
                
                var infraredRTSPUrl = Ext.create('Ext.form.field.Text', {
                    fieldLabel: '红外RTSP流',
                    name: 'infrared_rtsp_url',
                    anchor: '95%'
                });
                
                var infraredCameraUrl = Ext.create('Ext.form.field.Text', {
                    fieldLabel: '红外摄像机地址',
                    name: 'infrared_camera_url',
                    anchor: '95%'
                });
                
                var infraredCircuitUrl = Ext.create('Ext.form.field.Text', {
                    fieldLabel: '红外电路板设备地址',
                    name: 'infrared_circuit_url',
                    anchor: '95%'
                });
                
                var northMigration = Ext.create('Ext.form.field.Text', {
                    fieldLabel: '摄像机0角度与正北的偏移',
                    name: 'north_migration',
                    anchor: '95%'
                });
                
                var gisMapUrl = Ext.create('Ext.form.field.Text', {
                    fieldLabel: '地图文件存放位置',
                    name: 'gis_map_url',
                    anchor: '95%'
                });
                
                var visualAngleX = Ext.create('Ext.form.field.Text', {
                    fieldLabel: '红外视角X',
                    name: 'visual_angle_x',
                    anchor: '95%'
                });
                
                var visualAngleY = Ext.create('Ext.form.field.Text', {
                    fieldLabel: '红外视角Y',
                    name: 'visual_angle_y',
                    anchor: '95%'
                });
                
                var infraredPixelX = Ext.create('Ext.form.field.Text', {
                    fieldLabel: '红外摄像机X方向像素',
                    name: 'infrared_pixel_x',
                    anchor: '95%'
                });
                
                var infraredPixelY = Ext.create('Ext.form.field.Text', {
                    fieldLabel: '红外摄像机Y方向像素',
                    name: 'infrared_pixel_y',
                    anchor: '95%'
                });
                
                var version = Ext.create('Ext.form.field.Text', {
                    fieldLabel: '版本',
                    name: 'version',
                    anchor: '95%'
                });
                
                var isLocked = Ext.create('Ext.form.field.Text', {
                    fieldLabel: '状态',
                    name: 'is_locked',
                    anchor: '95%'
                });
               
                var newPTZForm = Ext.create('Ext.form.Panel', {
                    fieldDefaults: {
                        labelWidth: 55,
                        labelAlign: 'right'
                    },
                    url:'<%=basePath%>user/create.htm',
                    frame:true,
                    bodyStyle:'padding:5px 5px 0',
                    width: 605,
                     items: [{
                            layout: 'column',
                            xtype: 'container',
                            items: [{
                                    columnWidth: .3,
                                    layout: 'anchor',
                                    xtype: 'container',
                                    items: [loginId]
                                }, {
                                    columnWidth: .35,
                                    layout: 'anchor',
                                    xtype: 'container',
                                    items: [password]
                                }, {
                                    columnWidth: .35,
                                    layout: 'anchor',
                                    xtype: 'container',
                                    items: [role]
                                }]
                        },{
                            layout: 'column',
                            xtype: 'container',
                            items: [{
                                    columnWidth: .3,
                                    layout: 'anchor',
                                    xtype: 'container',
                                    items: [name]
                                },{
                                    columnWidth: .35,
                                    layout: 'anchor',
                                    xtype: 'container',
                                    items: [phone]
                                },{
                                    columnWidth: .35,
                                    layout: 'anchor',
                                    xtype: 'container',
                                    items: [identityCard]
                                }]
                        },address,email,{
                            layout: 'column',
                            xtype: 'container',
                            items: [{
                                    columnWidth: 1,
                                    layout: 'anchor',
                                    xtype: 'container',
                                    items: [desc]
                                }]
                        }],
                    
                })
               
                
            })
        </script>
    </head>
    <body>
        <div id="new_PTZ_form"></div>
    </body>
</html>
