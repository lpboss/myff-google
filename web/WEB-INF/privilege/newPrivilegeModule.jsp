<%-- 
    Document   : newPrivilegeModule
    Created on : 2011-9-19, 10:01:23
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
    <title>新系统权限添加(模块)</title>
  </head>
  <body>
    <script type="text/javascript">
      Ext.onReady(function(){
        var parentId = Ext.create('Ext.form.field.Hidden', {
          name: 'parent_id',
          value: '<%= request.getParameter("parent_id")%>'
        });

        var name = Ext.create('Ext.form.field.Text', {
          fieldLabel: '模块名称',
          name: 'name',
          anchor: '90%'
        });

        var desc = Ext.create('Ext.form.TextArea', {
          fieldLabel: '模块描述',
          name: 'description',
          allowBlank: false,
          height: 80,
          anchor: '90%'
        });
        
        //提交按钮
        var submitButton = Ext.create('Ext.Button', {
          text: '提交',
          handler: function(){
            submitButton.setDisabled(true);
            if (newPrivilegeFormPanel.form.isValid()) {
              newPrivilegeFormPanel.form.submit({
                success: function(result, resp){
                  if (resp.result.info.indexOf("success") >= 0) {
                    newPrivilegeModuleWin.destroy();
                  } else {
                    Ext.MessageBox.show({
                      title: '消息',
                      msg: resp.result.info,
                      buttons: Ext.MessageBox.OK,
                      icon: Ext.MessageBox.WARNING
                    });                    
                  }
                  submitButton.enable();
                },
                failure: function(result, request){
                  submitButton.enable();
                  Ext.MessageBox.show({
                    title: '消息',
                    msg: "通讯失败，请从新操作",
                    buttons: Ext.MessageBox.OK,
                    icon: Ext.MessageBox.WARNING
                  });
                }
              });
            }
            else {
              submitButton.enable();
              
            }
          }
        })
        
        var newPrivilegeFormPanel = Ext.create('Ext.form.Panel', {
          fieldDefaults: {
            labelWidth: 75,
            labelAlign: 'right'
          },
          width: '345',
          frame : true,
          url: '<%=basePath%>privilege/createSysPrivilege.htm',
          method: 'POST',
          items: [parentId,name,desc],
          buttons: [submitButton,{
              text: '关闭',
              iconCls: 'exit',
              handler: function(){
                newPrivilegeModuleWin.destroy();
              }
            }]
        });
        newPrivilegeFormPanel.render('sys_privilege_module_form');
      })
    </script>
    <div id="sys_privilege_module_form"></div>
  </body>
</html>
