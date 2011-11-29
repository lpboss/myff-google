<%-- 
    Document   : editPTZ
    Created on : 2011-11-9, 20:14:54
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
        <title>编辑PTZ信息</title>
        <script type="text/javascript">
            Ext.onReady(function(){
                var userId = <%=request.getParameter("id")%>;
                
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
 
               var name = Ext.create('Ext.form.field.Text', {
                    fieldLabel: '名字',
                    allowBlank: false,
                    blankText: "用云台名字不能为空",
                    name: 'name',
                    anchor: '95%'
                });
                
                var controllUrl = Ext.create('Ext.form.field.Text', {
                    fieldLabel: '编码器IP',
                    name: 'controllUrl',      //controll_url            
                    anchor: '95%'
                });
                
                var pelcodCommandUrl = Ext.create('Ext.form.field.Text', {
                    fieldLabel: '通过串口,发pelcod的ip',
                    lableWidth:100,
                    name: 'pelcodCommandUrl',
                    anchor: '95%'
                });
                
                var visibleCameraUrl = Ext.create('Ext.form.field.Text', {
                    fieldLabel: '可见光摄像机地址',
                    name: 'visibleCameraUrl',
                    anchor: '95%'
                });
                
                var visibleRTSPUrl = Ext.create('Ext.form.field.Text', {
                    fieldLabel: '可见光RTSP流',            
                    name: 'visibleRTSPUrl',
                    anchor: '95%'
                });
                
                var infraredRTSPUrl = Ext.create('Ext.form.field.Text', {
                    fieldLabel: '红外RTSP流',
                    name: 'infraredRTSPUrl',
                    anchor: '95%'
                });
                
                var infraredCameraUrl = Ext.create('Ext.form.field.Text', {
                    fieldLabel: '红外摄像机地址',
                    name: 'infraredCameraUrl',
                    anchor: '95%'
                });
                
                var infraredCircuitUrl = Ext.create('Ext.form.field.Text', {
                    fieldLabel: '红外电路板设备地址',
                    name: 'infraredCircuitUrl',
                    anchor: '95%'
                });
                
                var northMigration = Ext.create('Ext.form.field.Number', {
                    fieldLabel: '摄像机0角度与正北的偏移',
                    name: 'northMigration',
                    maxValue: 100,   
                    minValue: 0,
                    anchor: '95%'
                });
                
                var gisMapUrl = Ext.create('Ext.form.field.Text', {
                    fieldLabel: '地图文件存放位置',
                    name: 'gisMapUrl',
                    anchor: '95%'
                });
                
                var visualAngleX = Ext.create('Ext.form.field.Number', {
                    fieldLabel: '红外视角X',
                    name: 'visualAngleX',
                    anchor: '95%',
                    maxValue: 90,   
                    minValue: 0
                });
                
                var visualAngleY = Ext.create('Ext.form.field.Number', {
                    fieldLabel: '红外视角Y',
                    name: 'visualAngleY',
                    anchor: '95%',
                    maxValue: 90,   
                    minValue: 0
                });
                
                var infraredPixelX = Ext.create('Ext.form.field.Number', {
                    fieldLabel: '红外摄像机X方向像素',
                    name: 'infraredPixelX',
                    anchor: '95%',
                    maxValue: 90,   
                    minValue: 0
                });
                
                var infraredPixelY = Ext.create('Ext.form.field.Number', {
                    fieldLabel: '红外摄像机Y方向像素',
                    name: 'infraredPixelY',
                    anchor: '95%',
                    maxValue: 90,   
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
                    name:'brandType',
                    //allowBlank: false,
                    anchor: '95%'
                });
                
                var cruiseStep = Ext.create('Ext.form.field.Number', {
                    fieldLabel: '巡航步长',
                    name: 'cruiseStep',
                    maxValue: 100,   
                    minValue: 0,
                    anchor: '95%'
                });
                
                var cruiseRightLimit = Ext.create('Ext.form.field.Number', {
                    fieldLabel: '巡航右边界',
                    name: 'cruiseRightLimit',
                    anchor: '95%'
                });
                
                var cruiseLeftLimit = Ext.create('Ext.form.field.Number', {
                    fieldLabel: '巡航左边界',
                    name: 'cruiseLeftLimit',
                    anchor: '95%'
                });
                
                var cruiseUpLimit = Ext.create('Ext.form.field.Number', {
                    fieldLabel: '最大上仰角度',
                    name: 'cruiseUpLimit',
                    anchor: '95%'
                });
                
                var cruiseDownLimit = Ext.create('Ext.form.field.Number', {
                    fieldLabel: '巡航时最大俯角',
                    name: 'cruiseDownLimit',
                    anchor: '95%'
                });
                
                var isAlarm = Ext.create('Ext.form.field.Number', {
                    fieldLabel: '是否正在报警',
                    name: 'isAlarm',
                    anchor: '95%'
                });
                
                var alarmHeatValue = Ext.create('Ext.form.field.Number', {
                    fieldLabel: '报警最高热值',
                    name: 'alarmHeatValue',
                    anchor: '95%'
                });
                
                var shiftStep = Ext.create('Ext.form.field.Number', {
                    fieldLabel: '云台非巡航状态下默认移动步长',
                    name: 'shiftStep',
                    anchor: '95%'
                });
                
                var version = Ext.create('Ext.form.field.Number', {
                    fieldLabel: '版本',
                    name: 'version',
                    anchor: '95%'
                });
                
                var isLocked = Ext.create('Ext.form.field.Text', {
                    fieldLabel: '状态',
                    name: 'isLocked',
                    hidden:true,
                    anchor: '95%'
                });
                
                var PTZStore =  Ext.create('Ext.data.Store', {
                    model : 'PTZ',
                    proxy : {
                        type : 'ajax',
                        url : '<%=basePath%>ptz/getAllPTZs.htm?for_cbb=true',
                        reader : {
                            type : 'json',
                            root : 'root',// JSON数组对象名
                            totalProperty : 'totalProperty'// 数据集记录总数
                        }
                    }
                });
                
                var editPTZForm = Ext.create('Ext.form.Panel', {
                    fieldDefaults: {
                        labelWidth: 180,
                        labelAlign: 'right'
                    },
                    trackResetOnLoad: true,
                    frame:true,
                    url: '<%=basePath%>ptz/update.htm?id=' + userId,
                    reader: Ext.create('Ext.data.reader.Json',{
                        model: 'PTZ',
                        root: ''
                    }),
                    bodyStyle:'padding:5px 5px 0',
                    width: 1190,
                    height: 290,
                    items: [{
                            layout: 'column',
                            xtype: 'container',
                            items: [{
                                    columnWidth: .38,
                                    layout: 'anchor',                                  
                                    xtype: 'container',
                                    items: [name,controllUrl,pelcodCommandUrl,gisMapUrl,cruiseStep,brandType,cruiseRightLimit,cruiseLeftLimit,shiftStep]
                                }, {
                                    columnWidth: .38,
                                    layout: 'anchor',
                                    xtype: 'container',                                  
                                    items: [infraredRTSPUrl,infraredCameraUrl,infraredCircuitUrl,northMigration,visibleCameraUrl,visibleRTSPUrl,cruiseUpLimit,isAlarm]
                                }, {
                                    columnWidth: .23,
                                    layout: 'anchor',
                                    xtype: 'container',
                                    items: [visualAngleX,visualAngleY,infraredPixelX,infraredPixelY,version,isLocked,cruiseDownLimit,alarmHeatValue]
                                }]
                        }],
                    buttons: [{
                            text: '提交',
                            iconCls: 'icon-save',
                            handler: function(){
                                // check form value
                                if (editPTZForm.form.isValid()) {
                                    this.disable();
                                    editPTZForm.form.submit({
                                        method: 'POST',
                                        success: function(result, response){
                                            if (response.result.info == "success") {
                                                //添加成功后，隐藏窗口，并刷新Grid
                                                editPTZWin.destroy();
                                            }
                                            else {
                                                Ext.MessageBox.alert('消息', response.result.info);
                                            }
                                        },
                                        failure: function(result, response){
                                            Ext.MessageBox.alert('提示', result.responseText);
                                            editPTZWin.destroy();
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
                                editPTZWin.destroy();
                            }
                        }]
                });
                //编辑前，把数据引入到表单中。
                // load form and assign value to fields

                PTZStore.load({callback: function(record, options, success){
                        if(success){
                            editPTZForm.form.load({
                                url: '<%=basePath%>ptz/getPTZById.htm?id=' + userId,
                                method : 'POST',
                                waitMsg: '正在载入数据...',
                                success: function(form, action){
                                },
                                failure: function(form, action){
                                    Ext.MessageBox.alert('提示信息', '信息加载失败');
                                }
                            });
                        }else{
                            Ext.MessageBox.alert('操作','roleStore失败！');
                        }
                    }});
                    
                editPTZForm.render('edit_PTZ_form'); 
            })
        </script>
    </head>
    <body>
        <div id="edit_PTZ_form"></div>
    </body>
</html>