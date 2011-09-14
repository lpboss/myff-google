<%-- 
    Document   : newUser
    Created on : 2011-9-9, 14:14:44
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
        <title>添加用户</title>
        <meta http-equiv="content-type" content="text/html; charset=utf-8">
        <script type="text/javascript">
            Ext.onReady(function(){
                var number = Ext.create('Ext.form.field.Number', {
                    fieldLabel: '编号',
                    allowBlank: false,
                    blankText: "编号不能为空",
                    name: 'number',
                    anchor: '95%'
                });

                var name = Ext.create('Ext.form.field.Text', {
                    fieldLabel: '姓名',
                    allowBlank: false,
                    blankText: "用户姓名不能为空",
                    name: 'name',
                    anchor: '95%'
                });

                var password = Ext.create('Ext.form.field.Text', {
                    fieldLabel: '登录密码',
                    allowBlank: false,
                    blankText: "用户名称不能为空",
                    name: 'password',
                    anchor: '95%'
                });

                var loginId = Ext.create('Ext.form.field.Text', {
                    fieldLabel: '登录Id',
                    allowBlank: false,
                    blankText: "登录Id不能为空",
                    name: 'login_id',
                    anchor: '95%'
                });
        
                var phone = Ext.create('Ext.form.field.Text', {
                    fieldLabel: '电话',
                    name: 'phone',
                    anchor: '95%'
                });

                var identityCard = Ext.create('Ext.form.field.Text', {
                    fieldLabel: '身份证',
                    name: 'identity_card',
                    anchor: '95%'
                });

                var email = Ext.create('Ext.form.field.Text', {
                    fieldLabel: '电子邮箱',
                    name: 'email',
                    anchor: '95%'
                });

                var address = Ext.create('Ext.form.field.Text', {
                    fieldLabel: '地址',
                    name: 'address',
                    anchor: '95%'
                });

                var desc = Ext.create('Ext.form.TextArea', {
                    fieldLabel: '描述',
                    name: 'description',
                    height:110,
                    anchor: '95%'
                });


                var roleStore =  Ext.create('Ext.data.Store', {
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
        
                //供应商
                var role = Ext.create('Ext.form.ComboBox', {
                    store: roleStore,
                    fieldLabel: '系统角色',
                    allowBlank: false,
                    blankText: "角色必须选择",
                    valueField: 'id',
                    displayField: 'name',
                    name: 'role_id',//如果不想提交displayField，则在这儿指定要提交的Key，value就是valueField．
                    emptyText: '请选择...',          
                    loadingText: '搜索中...',
                    anchor: '95%',
                    //pageSize: 10,          
                    readOnly:false,
                    minChars: 0,          
                    editable:false
                });
        
                var newUserForm = Ext.create('Ext.form.Panel', {
                    fieldDefaults: {
                        labelWidth: 55,
                    labelAlign: 'right'
                    },
                    url:'<%=basePath%>user/create.htm',
                    frame:true,
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
                                    items: [number]
                                }]
                        },{
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
                    buttons: [{
                            text: '提交',
                            iconCls: 'icon-save',
                            handler: function(){
                                // check form value
                                if (newUserForm.form.isValid()) {
                                    this.disable();
                                    newUserForm.form.submit({
                                        method: 'GET',
                                        success: function(result, response){
                                            if (response.result.info == "success") {
                                                //添加成功后，隐藏窗口，并刷新Grid
                                                newUserWin.destroy();
                                            }
                                            else {
                                                Ext.MessageBox.alert('消息', response.result.info);
                                            }
                                        },
                                        failure: function(result, response){
                                            Ext.MessageBox.alert('提示', result.responseText);
                                            newUserWin.destroy();
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
                                newUserWin.destroy();
                            }
                        }]
                });
                newUserForm.render('new_user_form');
            })
        </script>
    </head>
    <body>
        <div id="new_user_form"></div>
    </body>
</html>

