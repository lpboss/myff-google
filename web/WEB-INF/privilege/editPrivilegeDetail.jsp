<%-- 
    Document   : editPrivilegeDetail
    Created on : 2011-9-14, 17:59:18
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
    <title>权限细节编辑</title>
  </head>
  <body>
    <script type="text/javascript">
      Ext.onReady(function(){
        var pdId = Ext.create('Ext.form.field.Hidden', {
          name: 'id',
          value: '<%=request.getParameter("id")%>'
        });

        var name = Ext.create('Ext.form.field.Text', {
          fieldLabel: '权限名称',
          name: 'name',
          anchor: '90%'
        });

        var sysController = Ext.create('Ext.form.ComboBox', {
          store: sysControllerStore,
          fieldLabel: '控制器',
          allowBlank: false,
          blankText: "控制器必须选择",
          valueField: 'id',
          displayField: 'name',
          name: 'sysControllerId',//如果不想提交displayField，则在这儿指定要提交的Key，value就是valueField．
          emptyText: '请选择...',
          loadingText: '搜索中...',
          anchor: '95%',
          //pageSize: 10,
          minChars: 0          
        });

        sysController.on('select', function() {
          sysAction.reset();
          sysActionStore.removeAll();
          sysActionStore.proxy.extraParams = {'sys_controller_id':sysController.getValue()};
          sysActionStore.load();
        });

        var sysAction = Ext.create('Ext.form.ComboBox', {
          store: sysActionStore,
          fieldLabel: '行为方法',
          allowBlank: false,
          blankText: "行为方法必须选择",
          valueField: 'id',
          displayField: 'name',
          name: 'sysActionId',//如果不想提交displayField，则在这儿指定要提交的Key，value就是valueField．
          emptyText: '请选择...',          
          loadingText: '搜索中...',
          anchor: '95%',
          //pageSize: 10,
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
            if (editPrivilegeFormPanel.form.isValid()) {
              editPrivilegeFormPanel.form.submit({
                success: function(result, resp){
                  if (resp.result.info.indexOf("success") >= 0) {
                    editPrivilegeDetailWin.destroy();
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
          url: '<%=basePath%>privilege/updateSysPrivilegeDetail.htm',
          method: 'POST',
          reader: Ext.create('Ext.data.reader.Json',{
            model: 'SysPrivilegeDetail',
            root: 'root'
          }),
          items: [pdId,name,sysController,sysAction,subType,params,desc],
          buttons: [submitButton,{
              text: '关闭',
              handler: function(){
                editPrivilegeDetailWin.destroy();
              }
            }]
        });

        sysControllerStore.load({callback: function(record, options, success){
            if(success){
              sysActionStore.proxy.extraParams = {'sys_controller_id':<%= request.getAttribute("sysControllerId") %>};
              sysActionStore.load({callback: function(record, options, success){
                  if(success){
                    editPrivilegeFormPanel.form.load({
                      url: '<%=basePath%>privilege/getPrivilegeDetailById.htm?id=' + pdId.getValue(),
                      method : 'GET',
                      waitMsg: '正在载入数据...',
                      success: function(form, action){
                      },
                      failure: function(form, action){
                        Ext.MessageBox.alert('提示信息', '信息加载失败');
                      }
                    });
                  }else{
                    Ext.MessageBox.alert('操作','失败！');
                  }
                }});
            }else{
              Ext.MessageBox.alert('操作','失败！');
            }
          }});
          
        editPrivilegeFormPanel.render('sys_privilege_form');
      })
    </script>
    <div id="sys_privilege_form"></div>
  </body>
</html>
