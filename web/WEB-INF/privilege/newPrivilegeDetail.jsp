<%-- 
    Document   : newPrivilegeDetail
    Created on : 2011-9-13, 18:01:42
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
    <title>新权限细节添加</title>
  </head>
  <body>
    <script type="text/javascript">
      Ext.onReady(function(){
        var privilegeId = Ext.create('Ext.form.field.Hidden', {
          name: 'privilege_id',
          value: '<%= request.getParameter("privilege_id")%>'
        });

        var name = Ext.create('Ext.form.field.Text', {
          fieldLabel: '权限名称',
          allowBlank: false,
          name: 'name',
          anchor: '90%'
        });

        var sysController = Ext.create('Ext.form.ComboBox', {
          store: sysControllerStore,
          fieldLabel: '控制器',
          allowBlank: false,
          blankText: "控制器必须选择",
          emptyText: '请选择...',
          valueField: 'id',
          displayField: 'name',
          name: 'sys_controller_id',//如果不想提交displayField，则在这儿指定要提交的Key，value就是valueField．          
          loadingText: '搜索中...',
          anchor: '95%',
          minChars: 0
        });

        sysController.on('select', function() {
          sysAction.reset();
          sysActionStore.removeAll();
          sysActionStore.proxy.extraParams = {'sys_controller_id':sysController.getValue()};
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
          anchor: '95%',
          //pageSize: 10,
          readOnly:false,
          minChars: 0
        });

        var subType = Ext.create('Ext.form.field.Text', {
          fieldLabel: '类型',
          name: 'sub_type',
          anchor: '90%'
        });

        var params = Ext.create('Ext.form.field.Text', {
          fieldLabel: '参数',
          name: 'params',
          anchor: '90%'
        });

        var desc = Ext.create('Ext.form.TextArea', {
          fieldLabel: '备注',
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
                    newPrivilegeDetailWin.destroy();
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
            labelWidth: 55,
            labelAlign: 'right'
          },
          width: '345',
          frame : true,
          url: '<%=basePath%>privilege/createSysPrivilegeDetail.htm',
          method: 'post',
          items: [privilegeId,name,sysController,sysAction,subType,params,desc],
          buttons: [submitButton,{
              text: '关闭',
              iconCls: 'exit',
              handler: function(){
                newPrivilegeDetailWin.destroy();
              }
            }]
        });
        newPrivilegeFormPanel.render('sys_privilege_form');
      })
    </script>
    <div id="sys_privilege_form"></div>
  </body>
</html>