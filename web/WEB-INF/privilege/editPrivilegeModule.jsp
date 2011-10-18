<%-- 
    Document   : editPrivilegeModule
    Created on : 2011-9-19, 10:50:08
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
    <title>编辑模块（权限）添加</title>
  </head>
  <body>
    <script type="text/javascript">
      Ext.onReady(function(){
        
        var moduleId = Ext.create('Ext.form.field.Hidden', {
          name: 'id',
          value:'<%= request.getParameter("id")%>'
        });

        var parentId = Ext.create('Ext.form.field.Hidden', {
          name: 'parentId'
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
          iconCls: 'icon-save',
          handler: function(){
            submitButton.setDisabled(true);
            if (editPrivilegeFormPanel.form.isValid()) {
              editPrivilegeFormPanel.form.submit({
                success: function(result, resp){
                  if (resp.result.info.indexOf("success") >= 0) {
                    editPrivilegeModuleWin.destroy();
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
        
        var editPrivilegeFormPanel = Ext.create('Ext.form.Panel', {
          fieldDefaults: {
            labelWidth: 75,
            labelAlign: 'right'
          },
          width: '345',
          frame : true,
          url: '<%=basePath%>privilege/updateSysPrivilege.htm',
          method: 'POST',
          reader: Ext.create('Ext.data.reader.Json',{
            model: 'Privilege',
            root: 'root'
          }),
          items: [moduleId,parentId,name,desc],
          buttons: [submitButton,{
              text: '关闭',
              iconCls: 'exit',
              handler: function(){
                editPrivilegeModuleWin.destroy();
              }
            }]
        });
        
        editPrivilegeFormPanel.form.load({
          url: '<%=basePath%>privilege/getSysPrivilegeById.htm?id=' + moduleId.getValue(),
          method : 'GET',
          waitMsg: '正在载入数据...',
          success: function(form, action){
          },
          failure: function(form, action){
            Ext.MessageBox.alert('提示信息', '信息加载失败');
          }
        });
        editPrivilegeFormPanel.render('sys_privilege_module_form');
      })
    </script>
    <div id="sys_privilege_module_form"></div>
  </body>
</html>
