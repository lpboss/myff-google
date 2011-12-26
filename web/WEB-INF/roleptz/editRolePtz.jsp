<%-- 
    Document   : editRolePtz
    Created on : 2011-11-29, 19:49:28
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
        <title>设置角色默认云台</title>
        <script type="text/javascript">

            Ext.onReady(function(){               
                //默认云台下拉框模�
                Ext.define('rolePtz', {
                    extend : 'Ext.data.Model',
                    fields : [
                        { name: 'id'},
                        { name: 'ptz',mapping:'ptz'},
                        { name: 'role',mapping:'role.id'}
                    ]
                });
                var userId = <%=request.getParameter("id")%>;
                var ptz =  Ext.create('Ext.data.Store', {
                    //autoDestroy : true,
                    model : 'PTZ',
                    proxy : {
                        type : 'ajax',
                        url : '<%=basePath%>ptz/getAllPTZs.htm',
                        reader : {
                            type : 'json',
                            root : 'root',// JSON数组对象名
                            totalProperty : 'totalProperty'// 数据集记录总数
                        }
                    },
                    //pageSize : pageSize,
                    autoLoad : true
                });
                
                var ptzId = Ext.create('Ext.form.ComboBox', {
                    store: ptz,
                    fieldLabel: '云台ID',
                    allowBlank: false,
                    blankText: "云台ID必须选择",
                    valueField: 'id',
                    displayField: 'name',
                    name: 'ptz',//如果不想提交displayField，则在这儿指定要提交的Key，value就是valueField．
                    emptyText: '请选择...',          
                    loadingText: '搜索中...',
                    anchor: '95%',
                    readOnly:false,
                    minChars: 0,          
                    editable:false
                });
                
                var name = Ext.create('Ext.form.field.Text', {
                    fieldLabel: '角色名称',
                    allowBlank: false,
                    blankText: "用云台名字不能为空",
                    name: 'name',
                    anchor: '95%',
                    readOnly:true,                   
                    style: 'color:#8A2BE2;font-family:黑体;background:#C7C7C7'
                });
                
                var desc = Ext.create('Ext.form.TextArea', {
                    fieldLabel: '备注',
                    allowBlank: false,
                    height:100,
                    name: 'description',
                    anchor: '100%',
                    readOnly:true,                   
                    style: 'color:#8A2BE2;font-family:黑体;background:#C7C7C7'
                });
 
                var roleStore =  Ext.create('Ext.data.Store', {
                    model : 'Role',
                    proxy : {
                        type : 'ajax',
                        url : '<%=basePath%>admin/getAllRoles.htm',
                        reader : {
                            type : 'json',
                            root : 'root',// JSON数组对象名
                            totalProperty : 'totalProperty'// 数据集记录总数
                        }
                    }
                });
                
                var editRolePtzForm = Ext.create('Ext.form.Panel', {
                    fieldDefaults: {
                        labelWidth: 60,
                        labelAlign: 'right'
                    },
                    trackResetOnLoad: true,
                    frame:true,
                    url: '<%=basePath%>admin/updateRole.htm?id=' + userId,
                    reader: Ext.create('Ext.data.reader.Json',{
                        model: 'Role',
                        root: ''
                    }),
                    bodyStyle:'padding:5px 5px 0',
                    width: 250,
                    height: 130,
                    items: [{
                            layout: 'column',
                            xtype: 'container',
                            items: [{
                                    columnWidth: .9,
                                    layout: 'anchor',                                  
                                    xtype: 'container',
                                    items: [name,ptzId,desc]
                                }]
                        }],
                    buttons: [{
                            text: '提交',
                            iconCls: 'icon-save',
                            handler: function(){
                                // check form value
                                if (editRolePtzForm.form.isValid()) {
                                    this.disable();
                                    editRolePtzForm.form.submit({
                                        method: 'POST',
                                        success: function(result, response){
                                            if (response.result.info == "success") {
                                                //添加成功后，隐藏窗口，并刷新Grid
                                                editRolePtzWin.destroy();
                                            }
                                            else {
                                                Ext.MessageBox.alert('消息', response.result.info);
                                            }
                                        },
                                        failure: function(result, response){
                                            Ext.MessageBox.alert('提示', result.responseText);
                                            editRolePtzWin.destroy();
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
                                editRolePtzWin.destroy();
                            }
                        }]
                });
                //编辑前，把数据引入到表单中。
                // load form and assign value to fields

                roleStore.load({callback: function(record, options, success){
                        if(success){
                            editRolePtzForm.form.load({
                                url: '<%=basePath%>admin/getRoleById.htm?id=' + userId,
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
                    
                editRolePtzForm.render('edit_Role_Ptz_Form'); 
            })
        </script>
    </head>
    <body>
        <div id="edit_Role_Ptz_Form"></div>
    </body>
</html>
