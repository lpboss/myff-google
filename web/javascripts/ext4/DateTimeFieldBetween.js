/**
 * 时间控件，from...to类型的
 */
Ext.define('Ext.ux.form.DateTimeFieldBetween', {
	extend: 'Ext.container.Container',
    alias: ['widget.datetimefieldbetween'],
    layout: 'hbox',
    format : 'Y-m-d H:i:s',
    defaultType: 'datetimefield',
    labelWidth: 100,
    initComponent: function() {
    	var oThis = this;
    	this.items = [
    	    {
    	    	fieldLabel: oThis.fieldLabel || '',
    	    	labelWidth: oThis.fieldLabel ? oThis.labelWidth : 0,
    	    	labelAlign: oThis.labelAlign ? oThis.labelAlign : 'left',
    	    	format: oThis.format
    	    },
    	    {
    	    	fieldLabel : '--',
				labelWidth : 20,
				labelSeparator : '',
				labelStyle: 'text-align:center',
    	    	format: oThis.format
    	    }
    	];
    	
    	this.callParent(arguments);
    }
});