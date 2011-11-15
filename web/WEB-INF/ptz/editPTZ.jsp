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
                Ext.define('UserEdit', {
                    extend : 'Ext.data.Model',
                    fields : [{
                            name: 'id'
                        }, {
                            name: 'name'
                        }, {
                            name: 'controll_url'
                        }, {
                            name: 'pelcod_command_url'
                        }, {
                            name: 'visible_camera_url'
                        }, {
                            name: 'visible_rtsp_url'
                        }, {
                            name: 'identity_card'
                        }, {
                            name: 'infrared_rtsp_url'
                        }, {
                            name: 'infrared_camera_url'
                        }, {
                            name: 'infrared_circuit_url'
                        }, {
                            name: 'north_migration'
                        }, {
                            name: 'gis_map_url'
                        }, {
                            name: 'visual_angle_x'
                        }, {
                            name: 'visual_angle_y'
                        }, {
                            name: 'infrared_pixel_x'
                        }, {
                            name: 'infrared_pixel_y'
                        }, {
                            name: 'version'
                        }, {
                            name: 'is_locked'
                        }, {
                            name: 'createAt',
                            mapping:'createAt.id'
                        }, {
                            name: 'updateAt',
                            mapping:'updateAt.id'
                        }
                    ]
                });
                
                var name = Ext.create('Ext.form.field.Text', {
                    fieldLabel: '名字',
                    allowBlank: false,
                    blankText: "云台名字不能为空",
                    name: 'name',
                    anchor: '95%'
                });
                
                var controllUrl = Ext.create('Ext.form.field.Text', {
                    fieldLabel: '编码器IP',
                    allowBlank: true,                  
                    name: 'controll_url',
                    anchor: '95%'
                });
                
                var pelcodCommandUrl = Ext.create('Ext.form.field.Text', {
                    fieldLabel: '通过串口,发pelcod的ip',
                    lableWidth:100,
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
                
                var PTZStore =  Ext.create('Ext.data.Store', {
                    model : 'Role',
                    proxy : {
                        type : 'ajax',
                        url : '<%=basePath%>PTZ/getAllPTZs.htm?for_cbb=true',
                        reader : {
                            type : 'json',
                            root : 'root',// JSON数组对象名
                            totalProperty : 'totalProperty'// 数据集记录总数
                        }
                    }
                });
                
                var editPTZForm = Ext.create('Ext.form.Panel', {
                    fieldDefaults: {
                        labelWidth: 55,
                        labelAlign: 'right'
                    },
                    trackResetOnLoad: true,
                    frame:true,
                    url: '<%=basePath%>user/update.htm?id=' + userId,
                    reader: Ext.create('Ext.data.reader.Json',{
                        model: 'UserEdit',
                        root: ''
                    }),
                    bodyStyle:'padding:5px 5px 0',
                    width: 605,
                    //height:300,
                    items: [{
                            layout: 'column',
                            xtype: 'container',
                            items: [{
                                    columnWidth: .3,
                                    layout: 'anchor',
                                    xtype: 'container',
                                    items: [name,controllUrl,pelcodCommandUrl,visibleCameraUrl,visibleRTSPUrl]
                                }]
                        },{
                            layout: 'column',
                            xtype: 'container',
                            items: [{
                                    columnWidth: .3,
                                    layout: 'anchor',
                                    xtype: 'container',
                                    items: [infraredRTSPUrl,infraredCameraUrl,infraredCircuitUrl,northMigration,gisMapUrl]
                                }, {
                                    columnWidth: .35,
                                    layout: 'anchor',
                                    xtype: 'container',
                                    items: [visualAngleX,visualAngleY,infraredPixelX,infraredPixelY,version,isLocked]
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
                                url: '<%=basePath%>user/getUserById.htm?id=' + userId,
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
