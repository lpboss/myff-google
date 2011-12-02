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
                              
                ptzBrandStore = Ext.create('Ext.data.ArrayStore', {
                    autoDestroy: true,
                    storeId: 'ptzBrandStore',
                    // reader configs
                    idIndex: 0,
                    fields: [
                        {name: 'text', type: 'string'},
                        {name: 'value', type: 'string'}
                    ],
                    data : ptzBrandArray
                });
                
                cruiseFromToStore = Ext.create('Ext.data.ArrayStore', {
                    autoDestroy: true,
                    storeId: 'cruiseFromToStore',
                    // reader configs
                    idIndex: 0,
                    fields: [
                        {name: 'text', type: 'string'},
                        {name: 'value', type: 'string'}
                    ],
                    data : cruiseFromToArray
                });
                
                var name = Ext.create('Ext.form.field.Text', {
                    fieldLabel: '名字',
                    allowBlank: false,
                    blankText: "用云台名字不能为空",
                    name: 'name',
                    anchor: '100%'
                });
                
                var controllUrl = Ext.create('Ext.form.field.Text', {
                    fieldLabel: '编码器IP',
                    name: 'controll_url',
                    anchor: '100%'
                });
                
                var pelcodCommandUrl = Ext.create('Ext.form.field.Text', {
                    fieldLabel: '通过串口,发pelcod的ip',                   
                    name: 'pelcod_command_url',
                    anchor: '100%'
                });
                
                var visibleCameraUrl = Ext.create('Ext.form.field.Text', {
                    fieldLabel: '可见光摄像机地址',
                    name: 'visible_camera_url',
                    anchor: '100%'
                });
                
                var visibleRTSPUrl = Ext.create('Ext.form.field.Text', {
                    fieldLabel: '可见光RTSP流',            
                    name: 'visible_rtsp_url',
                    anchor: '100%'
                });
                
                var infraredRTSPUrl = Ext.create('Ext.form.field.Text', {
                    fieldLabel: '红外RTSP流',
                    name: 'infrared_rtsp_url',
                    anchor: '100%'
                });
                
                var infraredCameraUrl = Ext.create('Ext.form.field.Text', {
                    fieldLabel: '红外摄像机地址',
                    name: 'infrared_camera_url',
                    anchor: '100%'
                });
                
                var infraredCircuitUrl = Ext.create('Ext.form.field.Text', {
                    fieldLabel: '红外电路板设备地址',
                    name: 'infrared_circuit_url',
                    anchor: '100%'
                });
                
                var northMigration = Ext.create('Ext.form.field.Number', {
                    fieldLabel: '云台0角度与正北的偏移',
                    name: 'north_migration',
                    //  maxValue: 100,   
                    minValue: 0,
                    anchor: '100%'
                });
                
                var gisMapUrl = Ext.create('Ext.form.field.Text', {
                    fieldLabel: '地图文件存放位置',
                    name: 'gis_map_url',
                    anchor: '100%'
                });
                
                var visualAngleX = Ext.create('Ext.form.field.Number', {
                    fieldLabel: '红外视角X',
                    name: 'visual_angle_x',
                    anchor:'100%',
                    maxValue: 90,   
                    minValue: 0
                });
                
                var visualAngleY = Ext.create('Ext.form.field.Number', {
                    fieldLabel: '红外视角Y',
                    name: 'visual_angle_y',
                    anchor: '100%',
                    maxValue: 90,   
                    minValue: 0
                });
                
                var infraredPixelX = Ext.create('Ext.form.field.Number', {
                    fieldLabel: '红外摄像机X方向像素',
                    name: 'infrared_pixel_x',
                    anchor: '100%',
                    maxValue: 900,  
                    minValue: 0
                });
                
                var infraredPixelY = Ext.create('Ext.form.field.Number', {
                    fieldLabel: '红外摄像机Y方向像素',
                    name: 'infrared_pixel_y',
                    anchor: '100%',
                    maxValue: 600,    
                    minValue: 0
                });
                
                var brandType = Ext.create('Ext.form.ComboBox', {
                    fieldLabel: '云台品牌',
                    store: ptzBrandStore,
                    // allowBlank: false,
                    valueField:'text',
                    displayField:'text',
                    typeAhead: true,
                    mode: 'local',
                    name:'brand_type',
                    //   emptyText:'请选择云台品牌...',
                    readOnly:false,
                    editable:false,
                    anchor: '100%'
                });
                
                var cruiseStep = Ext.create('Ext.form.field.Number', {
                    fieldLabel: '巡航步长',
                    name: 'cruise_step',
                    //   maxValue: 100,   
                    minValue: 0,
                    anchor: '100%'
                });
                
                var cruiseRightLimit = Ext.create('Ext.form.field.Number', {
                    fieldLabel: '巡航右边界',
                    name: 'cruise_right_limit',
                    anchor: '100%'
                });
                
                var cruiseLeftLimit = Ext.create('Ext.form.field.Number', {
                    fieldLabel: '巡航左边界',
                    name: 'cruise_left_limit',
                    anchor: '100%'
                });
                
                var cruiseUpLimit = Ext.create('Ext.form.field.Number', {
                    fieldLabel: '巡航时最大仰角',
                    name: 'cruise_up_limit',
                    anchor: '100%'
                });
                
                var cruiseDownLimit = Ext.create('Ext.form.field.Number', {
                    fieldLabel: '巡航时最大俯角',
                    name: 'cruise_down_limit',
                    anchor: '100%'
                });
                
                var isAlarm = Ext.create('Ext.form.field.Number', {
                    fieldLabel: '是否正在报警',
                    name: 'is_alarm',
                    anchor: '100%'
                });
                
                var alarmHeatValue = Ext.create('Ext.form.field.Number', {
                    fieldLabel: '报警最高热值',
                    name: 'alarm_heat_value',
                    anchor: '100%'
                });
                
                var shiftStep = Ext.create('Ext.form.field.Number', {
                    fieldLabel: '非巡航状态下移动步长',
                    name: 'shift_step',
                    anchor: '100%'
                });
                
                var isLocked = Ext.create('Ext.form.field.Text', {
                    fieldLabel: '状态',
                    name: 'is_locked',
                    hidden:true,
                    anchor: '100%'
                });
                
                var cruiseFromTo = Ext.create('Ext.form.ComboBox', {
                    fieldLabel: '巡航方向',
                    store: cruiseFromToStore,
                    // allowBlank: false,
                    valueField:'text',
                    displayField:'text',
                    typeAhead: true,
                    mode: 'local',
                    name:'cruise_from_to',
                    readOnly:false,
                    editable:false,
                    anchor: '100%'
                });
                
                var cruiseAngleYStep = Ext.create('Ext.form.field.Number', {
                    fieldLabel: '巡航上扬角度步长',
                    name: 'cruise_angle_y_step',
                    anchor: '100%'
                });

                var newPTZForm = Ext.create('Ext.form.Panel', {
                    fieldDefaults: {
                        labelWidth: 140,
                        labelAlign: 'right'
                    },
                    url:'<%=basePath%>ptz/create.htm',
                    frame:true,
                    bodyStyle:'padding:5px 5px 0',
                    width: 1290,
                    height: 290,
                    items: [{
                            layout: 'column',
                            xtype: 'container',
                            items: [{
                                    columnWidth: .4,
                                    layout: 'anchor',
                                    xtype: 'container',
                                    items: [name]
                                }, {
                                    columnWidth: .4,
                                    layout: 'anchor',
                                    xtype: 'container',
                                    items: [infraredRTSPUrl]
                                }, {
                                    columnWidth: .2,
                                    layout: 'anchor',
                                    xtype: 'container',
                                    items: [alarmHeatValue]
                                }]
                        },{
                            layout: 'column',
                            xtype: 'container',
                            items: [{
                                    columnWidth: .4,
                                    layout: 'anchor',
                                    xtype: 'container',
                                    items: [brandType]
                                },{
                                    columnWidth: .4,
                                    layout: 'anchor',
                                    xtype: 'container',
                                    items: [infraredCameraUrl]
                                }, {
                                    columnWidth: .2,
                                    layout: 'anchor',
                                    xtype: 'container',
                                    items: [isAlarm]
                                }]
                        },{
                            layout: 'column',
                            xtype: 'container',
                            items: [{
                                    columnWidth: .4,
                                    layout: 'anchor',
                                    xtype: 'container',
                                    items: [pelcodCommandUrl]
                                }, {
                                    columnWidth: .4,
                                    layout: 'anchor',
                                    xtype: 'container',
                                    items: [infraredCircuitUrl]
                                }]},
                        {
                            layout: 'column',
                            xtype: 'container',
                            items: [{
                                    columnWidth: .4,
                                    layout: 'anchor',
                                    xtype: 'container',
                                    items: [gisMapUrl]
                                }, {
                                    columnWidth: .2,
                                    layout: 'anchor',
                                    xtype: 'container',
                                    items: [visualAngleX]
                                },{
                                    columnWidth: .2,
                                    layout: 'anchor',
                                    xtype: 'container',
                                    items: [visualAngleY]
                                }]
                        },
                        {
                            layout: 'column',
                            xtype: 'container',
                            items: [{
                                    columnWidth: .4,
                                    layout: 'anchor',
                                    xtype: 'container',
                                    items: [controllUrl]
                                }, {
                                    columnWidth: .2,
                                    layout: 'anchor',
                                    xtype: 'container',
                                    items: [infraredPixelX]
                                }, {
                                    columnWidth: .2,
                                    layout: 'anchor',
                                    xtype: 'container',
                                    items: [infraredPixelY]
                                }]
                        },
                        {
                            layout: 'column',
                            xtype: 'container',
                            items: [{
                                    columnWidth: .2,
                                    layout: 'anchor',
                                    xtype: 'container',
                                    items: [cruiseStep]
                                },{
                                    columnWidth: .2,
                                    layout: 'anchor',
                                    xtype: 'container',
                                    items: [shiftStep]
                                }, {
                                    columnWidth: .4,
                                    layout: 'anchor',
                                    xtype: 'container',
                                    items: [visibleRTSPUrl]
                                }]
                        },
                        {
                            layout: 'column',
                            xtype: 'container',
                            items: [{
                                    columnWidth: .2,
                                    layout: 'anchor',
                                    xtype: 'container',
                                    items: [cruiseLeftLimit]
                                }, {
                                    columnWidth: .2,
                                    layout: 'anchor',
                                    xtype: 'container',
                                    items: [cruiseRightLimit]
                                }, {
                                    columnWidth: .4,
                                    layout: 'anchor',
                                    xtype: 'container',
                                    items: [visibleCameraUrl]
                                }]
                        },
                        {
                            layout: 'column',
                            xtype: 'container',
                            items: [{
                                    columnWidth: .2,
                                    layout: 'anchor',
                                    xtype: 'container',
                                    items: [isLocked]
                                }]
                        },
                        {
                            layout: 'column',
                            xtype: 'container',
                            items: [ {
                                    columnWidth: .2,
                                    layout: 'anchor',
                                    xtype: 'container',
                                    items: [cruiseUpLimit]
                                }, {
                                    columnWidth: .2,
                                    layout: 'anchor',
                                    xtype: 'container',
                                    items: [cruiseDownLimit]
                                }, {
                                    columnWidth: .4,
                                    layout: 'anchor',
                                    xtype: 'container',
                                    items: [northMigration]
                                }]
                        },
                        {
                            layout: 'column',
                            xtype: 'container',
                            items: [{
                                    columnWidth: .2,
                                    layout: 'anchor',
                                    xtype: 'container',
                                    items: [cruiseFromTo]
                                }, {
                                    columnWidth: .2,
                                    layout: 'anchor',
                                    xtype: 'container',
                                    items: [cruiseAngleYStep]
                                }, {
                                    columnWidth: .4,
                                    layout: 'anchor',
                                    xtype: 'container',
                                    items: []
                                }]
                        }
                    ],
                    buttons: [{
                            text: '提交',
                            iconCls: 'icon-save',
                            handler: function(){
                                // check form value
                                if (newPTZForm.form.isValid()) {
                                    //                                    this.disable();
                                    newPTZForm.form.submit({
                                        method : 'POST',
                                        success: function(result, response){
                                            if (response.result.info == "success") {
                                                //添加成功后，隐藏窗口，并刷新Grid
                                                Ext.MessageBox.alert('消息', "成功");
                                                newPTZWin.destroy();
                                            }
                                            else {
                                                Ext.MessageBox.alert('消息', response.result.info);
                                            
                                            }
                                        },
                                        failure: function(result, response){
                                            Ext.MessageBox.alert('提示', result.responseText);
                                            newPTZWin.destroy();
                                        }
                                    });
                                }
                                else {
                                    Ext.MessageBox.alert('错误提示', '请按要求填写必输选项.');
                                }
                            }
                        },{
                            text: '取消',
                            iconCls: 'exit',
                            handler: function(){
                                newPTZWin.destroy();
                            }
                        }]
                    
                });
                newPTZForm.render('new_PTZ_form');
                
            })
        </script>
    </head>
    <body>
        <div id="new_PTZ_form"></div>
    </body>
</html>
