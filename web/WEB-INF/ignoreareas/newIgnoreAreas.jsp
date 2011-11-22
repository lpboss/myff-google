<%-- 
    Document   : newAlarmIgnoreAreas
    Created on : 2011-11-21, 12:10:10
    Author     : Administrator
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
        <title>添加报警忽视地区信息</title>
        <script type="text/javascript">
            Ext.onReady(function(){
                
                var ptzId = Ext.create('Ext.form.field.Number', {
                    fieldLabel: '云台的编号',
                    allowBlank: false,
                    blankText: "云台的编号不能为空",
                    name: 'ptzId',
                    anchor: '95%'
                });
                
                var ptzAngelX = Ext.create('Ext.form.field.Number', {
                    fieldLabel: '火警时云台的水平角度',
                    allowBlank: true,                  
                    name: 'ptzAngelX',
                    anchor: '95%'
                });
                
                var ptzAngelY = Ext.create('Ext.form.field.Number', {
                    fieldLabel: '火警时云台的Y角度',
                    lableWidth:100,
                    name: 'ptzAngelY',
                    anchor: '95%'
                });
                
                var ccdArea = Ext.create('Ext.form.field.Number', {
                    fieldLabel: '热成像起火面积值',
                    name: 'ccdArea',
                    anchor: '95%'
                });
                
                var heatMax = Ext.create('Ext.form.field.Number', {
                    fieldLabel: '最大热值',            
                    name: 'heatMax',
                    anchor: '95%'
                });
                
                var beginDate = Ext.create('Ext.form.field.DateTime', {
                    fieldLabel: '火警时间范围(开始)',
                    format : 'Y-m-d H:i:s',
                    name: 'beginDate',
                    anchor: '95%'
                });
                
                var endDate = Ext.create('Ext.form.field.DateTime', {
                    fieldLabel: '火警时间范围(结束)',
                    format : 'Y-m-d H:i:s',
                    name: 'endDate',
                    anchor: '95%'
                });

                var version = Ext.create('Ext.form.field.Number', {
                    fieldLabel: '版本',
                    name: 'version',
                    anchor: '95%'
                });
                
                var isLocked = Ext.create('Ext.form.field.Text', {
                    fieldLabel: '状态',
                    name: 'is_locked',
                    anchor: '95%'
                });
               
                var newAlarmIgnoreAreasForm = Ext.create('Ext.form.Panel', {
                    fieldDefaults: {
                        labelWidth: 180,
                        labelAlign: 'right'
                    },
                    url:'<%=basePath%>ignoreareas/create.htm',
                    frame:true,
                    bodyStyle:'padding:5px 5px 0',
                    width: 700,
                    height: 175,
                    items: [{
                            layout: 'column',
                            xtype: 'container',
                            items: [{
                                    columnWidth: .45,
                                    layout: 'anchor',                                  
                                    xtype: 'container',
                                    items: [ptzId,ptzAngelX,ptzAngelY,ccdArea,heatMax]
                                }, {
                                    columnWidth: .53,
                                    layout: 'anchor',
                                    xtype: 'container',                                  
                                    items: [beginDate,endDate,version,isLocked]
                                }]
                        }],
                    buttons: [{
                            text: '提交',
                            iconCls: 'icon-save',
                            handler: function(){
                                // check form value
                                if (newAlarmIgnoreAreasForm.form.isValid()) {
                                    this.disable();
                                    newAlarmIgnoreAreasForm.form.submit({
                                        method : 'POST',
                                        success: function(result, response){
                                            if (response.result.info == "success") {
                                                //添加成功后，隐藏窗口，并刷新Grid
                                                newAlarmIgnoreAreasWin.destroy();
                                            }
                                            else {
                                                Ext.MessageBox.alert('消息', response.result.info);
                                            }
                                        },
                                        failure: function(result, response){
                                            Ext.MessageBox.alert('提示', result.responseText);
                                            newAlarmIgnoreAreasWin.destroy();
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
                                newAlarmIgnoreAreasWin.destroy();
                            }
                        }]
                    
                });
                newAlarmIgnoreAreasForm.render('new_Alarm_Ignore_Areas_Form');
                
            })
        </script>
    </head>
    <body>
        <div id="new_Alarm_Ignore_Areas_Form"></div>
    </body>
</html>
