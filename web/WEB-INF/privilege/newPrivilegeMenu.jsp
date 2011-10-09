<%-- 
    Document   : newPrivilegeMenu
    Created on : 2011-9-16, 16:41:36
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
    <title>新系统权限(菜单)添加</title>
  </head>
  <body>
    <script type="text/javascript">
      Ext.onReady(function(){
        var parentId = Ext.create('Ext.form.field.Hidden', {
          name: 'parent_id',
          value: '<%= request.getParameter("parent_id")%>'
        });

        var name = Ext.create('Ext.form.field.Text', {
          fieldLabel: '菜单名称',
          name: 'name',
          anchor: '90%'
        });

        //系统控制器
        var sysController = Ext.create('Ext.form.ComboBox', {
          store: sysControllerStore,
          fieldLabel: '控制器',
          allowBlank: false,
          blankText: "控制器必须选择",
          valueField: 'id',
          displayField: 'name',
          name: 'sys_controller_id',//如果不想提交displayField，则在这儿指定要提交的Key，value就是valueField．
          emptyText: '请选择...',          
          loadingText: '搜索中...',
          anchor: '90%',
          //pageSize: 10,
          readOnly:false,
          minChars: 0
        });

        sysController.on('select', function() {
          sysActionStore.removeAll();
          sysActionStore.proxy.extraParams.sys_controller_id = sysController.getValue();
          sysActionStore.load();
        });

        //sysActionStore.load();
        var sysAction = Ext.create('Ext.form.ComboBox', {
          store: sysActionStore,
          fieldLabel: '行为方法',
          allowBlank: false,
          blankText: "行为方法必须选择",
          valueField: 'id',
          displayField: 'name',
          name: 'sys_action_id',//如果不想提交displayField，则在这儿指定要提交的Key，value就是valueField．
          emptyText: '请选择...',
          loadingText: '搜索中...',
          anchor: '90%',
          //pageSize: 10,
          readOnly:false,
          minChars: 0
        });

        var desc = Ext.create('Ext.form.TextArea', {
          fieldLabel: '菜单描述',
          name: 'description',
          allowBlank: true,
          height: 80,
          anchor: '90%'
        });
        
        //提交按钮
        var submitButton = Ext.create('Ext.Button', {
          text: '提交',
          iconCls: 'icon-save',
          handler: function(){
            submitButton.setDisabled(true);
            if (newPrivilegeFormPanel.form.isValid()) {
              newPrivilegeFormPanel.form.submit({
                success: function(result, resp){
                  if (resp.result.info.indexOf("success") >= 0) {
                    newPrivilegeMenuWin.destroy();
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
          items: [parentId,name,sysController,sysAction,desc],
          buttons: [submitButton,{
              text: '关闭',
              iconCls: 'exit',
              handler: function(){
                newPrivilegeMenuWin.destroy();
              }
            }]
        });
        newPrivilegeFormPanel.render('sys_privilege_menu_form');
      })
    </script>
    <div id="sys_privilege_menu_form"></div>
  </body>
</html>
