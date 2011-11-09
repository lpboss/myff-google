<%-- 
    Document   : newCamera
    Created on : 2011-11-9, 14:54:30
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
        <title>添加摄像机</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript">
            Ext.onReady(function(){

                var name = Ext.create('Ext.form.field.Text', {
                    fieldLabel: '摄像机名称',
                    allowBlank: false,
                    blankText: "摄像机名称不能为空",
                    name: 'name',
                    anchor: '95%'
                });
                
                var controllUrl = Ext.create('Ext.form.field.Text', {
                    fieldLabel: '编码器IP',
                    allowBlank: false,
                    blankText: "编码器IP不能为空",
                    name: 'controll_url',
                    anchor: '95%'
                });
                
                var pelcodCommandUrl = Ext.create('Ext.form.field.Text', {
                    fieldLabel: '通过串口,发pelcod的ip',
                    allowBlank: false,
                    blankText: "通过串口,发pelcod的ip不能为空",
                    name: 'pelcod_command_url',
                    anchor: '95%'
                });
                
                var visibleCameraUrl = Ext.create('Ext.form.field.Text', {
                    fieldLabel: '可见光摄像机地址,模拟请参考controll_url',
                    allowBlank: false,
                    blankText: "可见光摄像机地址,模拟请参考controll_url不能为空",
                    name: 'visible_camera_url',
                    anchor: '95%'
                });
                
                var visibleRtspUrl = Ext.create('Ext.form.field.Text', {
                    fieldLabel: '可见光RTSP流',
                    allowBlank: false,
                    blankText: "可见光RTSP流不能为空",
                    name: 'visible_rtsp_url',
                    anchor: '95%'
                });
                
                var infraredRtspUrl = Ext.create('Ext.form.field.Text', {
                    fieldLabel: '红外RTSP流',
                    allowBlank: false,
                    blankText: "红外RTSP流不能为空",
                    name: 'infrared_rtsp_url',
                    anchor: '95%'
                });
                
                var infraredCameraUrl = Ext.create('Ext.form.field.Text', {
                    fieldLabel: '红外摄像机地址',
                    allowBlank: false,
                    blankText: "红外摄像机地址不能为空",
                    name: 'infrared_camera_url',
                    anchor: '95%'
                });
                
                var pelcodCommandUrl = Ext.create('Ext.form.field.Text', {
                    fieldLabel: '通过串口,发pelcod的ip',
                    allowBlank: false,
                    blankText: "通过串口,发pelcod的ip不能为空",
                    name: 'pelcod_command_url',
                    anchor: '95%'
                });
                
                var pelcodCommandUrl = Ext.create('Ext.form.field.Text', {
                    fieldLabel: '通过串口,发pelcod的ip',
                    allowBlank: false,
                    blankText: "通过串口,发pelcod的ip不能为空",
                    name: 'pelcod_command_url',
                    anchor: '95%'
                });
                
                var cameraStore =  Ext.create('Ext.data.Store', {
                    //autoDestroy : true,
                    model : 'Role',
                    proxy : {
                        type : 'ajax',
                        url : '<%=basePath%>admin/getAllRoles.htm?for_cbb=true',
                        reader : {
                            type : 'json',
                            root : 'root',// JSON数组对象名
                            totalProperty : 'totalProperty'// 数据集记录总数
                        }
                    },
                    //pageSize : pageSize,
                    autoLoad : true
                });
                
                var newCameraForm = Ext.create('Ext.form.Panel',{
                    fieldDefaults: {
                        labelWidth: 55,
                        labelAlign: 'right'
                    },
                    url:'<%=basePath%>user/create.htm',
                    frame:true,
                    bodyStyle:'padding:5px 5px 0',
                    width: 300,
                    items: [{
                            layout: 'column',
                            xtype: 'container',
                            items: [{
                                    columnWidth: .5,
                                    layout: 'anchor',
                                    xtype: 'container',
                                    items: [controllUrl,name,modelNumber]
                                }]
                        }],
                    buttons: [{
                            text: '提交',
                            iconCls: 'icon-save',
                            handler: function(){
                                // check form value
                                if (newCameraForm.form.isValid()) {
                                    this.disable();
                                    newCameraForm.form.submit({
                                        method : 'POST',
                                        success: function(result, response){
                                            if (response.result.info == "success") {
                                                //添加成功后，隐藏窗口，并刷新Grid
                                                newCameraWin.destroy();
                                            }
                                            else {
                                                Ext.MessageBox.alert('消息', response.result.info);
                                            }
                                        },
                                        failure: function(result, response){
                                            Ext.MessageBox.alert('提示', result.responseText);
                                            newCameraWin.destroy();
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
                                newCameraWin.destroy();
                            }
                        }]
                    
                });
                newCameraForm.render('new_camera_form'); 
            })
        </script>
    </head>
    <body>
        <div id="new_camera_form"></div>
    </body>
</html>
