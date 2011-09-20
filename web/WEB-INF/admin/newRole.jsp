<%-- 
    Document   : newRole
    Created on : 2011-9-20, 16:18:09
    Author     : jerry
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
  <head>
    <title>添加权限</title>
  </head>
  <body>
    <script type="text/javascript">
      Ext.onReady(function(){
        var name = Ext.create('Ext.form.field.Text', {
          fieldLabel: '名称',
          allowBlank: false,
          name: 'name',
          anchor: '100%'
        });
        
        var desc = Ext.create('Ext.form.TextArea', {
          fieldLabel: '备注',
          allowBlank: false,
          height:100,
          name: 'description',
          anchor: '100%'
        });        
        
        //提交按钮
        var addRoleButton = Ext.create('Ext.Button', {
          text: '提交',
          iconCls: 'icon-save',
          handler: function(){
            addRoleButton.setDisabled(true);
            if (newRoleFormPanel.form.isValid()) {
              newRoleFormPanel.form.submit({
                success: function(result, resp){
                  if (resp.result.info.indexOf("success") >= 0) {
                    newRoleWin.destroy();
                  } else {
                    Ext.MessageBox.show({
                      title: '消息',
                      msg: resp.result.info,
                      buttons: Ext.MessageBox.OK,
                      icon: Ext.MessageBox.WARNING
                    });                    
                  }
                  addRoleButton.enable();
                },
                failure: function(result, request){
                  addRoleButton.enable();
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
              addRoleButton.enable();
              
            }
          }
        })
        
        var newRoleFormPanel = Ext.create('Ext.form.Panel', {
          fieldDefaults: {
            labelWidth: 65,
            labelAlign: 'right'
          },
          width: 400,
          frame : true,
          url: '<%=basePath%>admin/createRole.htm',
          method: 'POST',
          items: [name,desc],
          buttons: [addRoleButton,{
              text: '关闭',
              iconCls: 'exit',
              handler: function(){
                newRoleWin.destroy();
              }
            }]
        });
        newRoleFormPanel.render('new_role_form');
      })
    </script>
    <div id="new_role_form"></div>
  </body>
</html>
