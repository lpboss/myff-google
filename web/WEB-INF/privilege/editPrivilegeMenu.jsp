<%-- 
    Document   : editPrivilegeMenu
    Created on : 2011-9-16, 9:33:07
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
    <title>编辑菜单（权限）</title>
  </head>
  <body>
    <script type="text/javascript">
      Ext.onReady(function(){
        var moduleId = Ext.create('Ext.form.field.Hidden', {
          name: 'id',
          value:'<%=request.getParameter("id") %>'
        });
        
        var parentId = Ext.create('Ext.form.field.Hidden', {
          name: 'parent_id'
        });

        var name = Ext.create('Ext.form.field.Text', {
          fieldLabel: '权限名称',
          name: 'name',
          anchor: '90%'
        });

        var module = Ext.create('Ext.form.ComboBox', {
          store: moduleStore,
          fieldLabel: '上级模块',
          allowBlank: false,
          blankText: "上级模块必须选择",
          valueField: 'id',
          displayField: 'name',
          name: 'module_id',//如果不想提交displayField，则在这儿指定要提交的Key，value就是valueField．
          emptyText: '请选择...',          
          loadingText: '搜索中...',
          anchor: '90%',
          //pageSize: 10,
          //hideTrigger: false,
          readOnly:false,
          minChars: 0
        });

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
          //hideTrigger: false,
          readOnly:false,
          minChars: 0          
        });

        sysController.on('select', function() {
          sysActionStore.removeAll();
          sysActionStore.proxy.extraParams.sys_controller_id = sysController.getValue();
          sysActionStore.load();
        });

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
          //hideTrigger: false,
          readOnly:false,
          minChars: 0
        });
        
        var desc = Ext.create('Ext.form.TextArea', {
          fieldLabel: '权限描述',
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
                  if (resp.result.info.indexOf("成功") >= 0) {
                    editPrivilegeMenuWin.destroy();
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
          url: '/privilege/updateSysPrivilege',
          method: 'GET',
          reader: Ext.create('Ext.data.reader.Json',{
            model: 'Privilege',
            root: 'root'
          }),
          items: [moduleId,parentId,name,module,sysController,sysAction,desc],
          buttons: [submitButton,{
              text: '关闭',
              iconCls: 'exit',
              handler: function(){
                editPrivilegeMenuWin.destroy();
              }
            }]
        });

        moduleStore.load({callback: function(record, options, success){
            if(success){
              sysControllerStore.load({callback: function(record, options, success){
                  if(success){
                      sysActionStore.proxy.extraParams = {'sys_controller_id':<%= request.getAttribute("sysControllerId") %>};
                    sysActionStore.load({callback: function(record, options, success){
                        if(success){
                          editPrivilegeFormPanel.form.load({
                            url: '/privilege/getSysPrivilegeById?id=' + moduleId.getValue(),
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
            }else{
              Ext.MessageBox.alert('操作','失败！');
            }
          }});
        
        editPrivilegeFormPanel.render('sys_privilege_menu_form');
      })
    </script>
    <div id="sys_privilege_menu_form"></div>
  </body>
</html>
