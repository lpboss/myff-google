<%-- 
    Document   : editAlarmIgnoreAreas
    Created on : 2011-11-21, 12:09:30
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
        <title>编辑报警忽视地区</title>
        <script type="text/javascript">
            Ext.onReady(function(){
                var userId = <%=request.getParameter("id")%>;
 
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
                
                var alarmIgnoreAreasStore =  Ext.create('Ext.data.Store', {
                    model : 'PTZEdit',
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
                
                var editAlarmIgnoreAreasForm = Ext.create('Ext.form.Panel', {
                    fieldDefaults: {
                        labelWidth: 180,
                        labelAlign: 'right'
                    },
                    trackResetOnLoad: true,
                    frame:true,
                    url: '<%=basePath%>ignoreareas/update.htm?id=' + userId,
                    reader: Ext.create('Ext.data.reader.Json',{
                        model: 'alarmIgnoreAreas',
                        root: ''
                    }),
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
                                if (editAlarmIgnoreAreasForm.form.isValid()) {
                                    this.disable();
                                    editAlarmIgnoreAreasForm.form.submit({
                                        method: 'POST',
                                        success: function(result, response){
                                            if (response.result.info == "success") {
                                                //添加成功后，隐藏窗口，并刷新Grid
                                                editAlarmIgnoreAreasWin.destroy();
                                            }
                                            else {
                                                Ext.MessageBox.alert('消息', response.result.info);
                                            }
                                        },
                                        failure: function(result, response){
                                            Ext.MessageBox.alert('提示', result.responseText);
                                            editAlarmIgnoreAreasWin.destroy();
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
                                editAlarmIgnoreAreasWin.destroy();
                            }
                        }]
                });
                //编辑前，把数据引入到表单中。
                // load form and assign value to fields

                alarmIgnoreAreasStore.load({callback: function(record, options, success){
                        if(success){
                            editAlarmIgnoreAreasForm.form.load({
                                url: '<%=basePath%>ignoreareas/getIgnoreAreasesById.htm?id=' + userId,
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
                    
                editAlarmIgnoreAreasForm.render('edit_Alarm_Ignore_Areas_Form'); 
            })
        </script>
    </head>
    <body>
        <div id="edit_Alarm_Ignore_Areas_Form"></div>
    </body>
</html>
