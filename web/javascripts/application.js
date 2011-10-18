// Place your application-specific JavaScript functions and classes here
// This file is automatically included by javascript_include_tag :defaults
var bodyWidth =  document.body.clientWidth;
var bodyHeight =  document.body.clientHeight;
var screenWidth = window.screen.width;
var screenHeight = window.screen.height;
var offsetWidth = document.body.offsetWidth;
var offsetHeight = document.body.offsetHeight;
var availHeight = window.screen.availHeight;
var availWidth = window.screen.availWidth;

if (window.navigator.userAgent.indexOf("Firefox")>=1) {
//screenWidth = window.screen.width + window.screen.width/9;
//screenHeight = window.screen.height + window.screen.height/9;
}

//得到当前日期时间格式为2010-01-01 12：12：12
function getDate()
{
    var d,s,t;
    d=new Date();
    s=d.getFullYear().toString(10)+"-";
    t=d.getMonth()+1;
    s+=(t>9?"":"0")+t+"-";
    t=d.getDate();
    s+=(t>9?"":"0")+t+" ";
    t=d.getHours();
    s+=(t>9?"":"0")+t+":";
    t=d.getMinutes();
    s+=(t>9?"":"0")+t+":";
    t=d.getSeconds();
    s+=(t>9?"":"0")+t;
    return s;
}

//不需要的字段放在rejects中
function gridStoreToJson(store,rejects){
    //Ext.util.MixedCollection,:=>keys
    //var modifiedRecords = store.getModifiedRecords();
    var keyArray = new Array();
    var modifiedRecords = store.getRange(0,store.getCount());
    //首先得到相关的字段

    //var keys = store.fields
    var keys = modifiedRecords[0].fields;
    if (keyArray.length == 0){
        if (rejects === undefined){
            keys.eachKey(function(key){
                keyArray.push(key);
            });
        }else{
            keys.eachKey(function(key){
                var isHas = false;
                for (var h=0;h < rejects.length;h++){
                    if (rejects[h] == key){
                        isHas = true;
                        break;
                    }
                }
                if (isHas == false){
                    keyArray.push(key);
                }
                isHas = false;
            });
        }
    }

        
    var jsonStr = "["
    for (var i = 0;i<modifiedRecords.length;i++) {
        jsonStr = jsonStr + '{';
        var record = modifiedRecords[i];

        for (var j=0;j<keyArray.length;j++){
            var key = keyArray[j];
            //如果没有选择产品
            
            if(key === "product_id" && record.data[key]==undefined){
                return "";
            }
            //如果选择的产品数量为0
            if(key === "quantity" && record.data[key] < 1){
                return "";
            }
            var value;
            if (record.data[key]==undefined){
                value = "";
            }else{
                value = record.data[key];
            }
            jsonStr = jsonStr + "\"" + key + "\":\"" + value + "\",";
        }
        jsonStr = Ext.util.Format.substr(jsonStr,0,jsonStr.length-1);
        jsonStr = jsonStr + "}";
        if(i < modifiedRecords.length - 1){
            jsonStr = jsonStr + ",";
        }
    //alert(keys.getCount());
    }
    return jsonStr =  jsonStr + "]";
}

//得到某个GridStore的某个Id的值串，中间以|分隔
//不需要的字段放在rejects中
function getFieldStringFromGridStore(store,fieldName){
    var modifiedRecords = store.getRange(0,store.getCount());
    //首先得到相关的字段

    var feildStr = "";
    for (var i = 0;i<modifiedRecords.length;i++) {
        var record = modifiedRecords[i];
        if (record.get(fieldName) != undefined){
            feildStr = feildStr + record.get(fieldName) + ",";
        }
    }
    if (feildStr != ""){
        feildStr = Ext.util.Format.substr(feildStr,0,feildStr.length-1);
    }
    return feildStr;
}

//给GridStore中的某个字段清空值 
function clearFieldValueForGridStore(store,fieldName,fieldValue){
    var modifiedRecords = store.getRange(0,store.getCount());
    //首先得到相关的字段

    var feildStr = "";
    for (var i = 0;i<modifiedRecords.length;i++) {
        var record = modifiedRecords[i];
        record.set(fieldName,fieldValue);
    }
    return;
}


//判断一下当前的Store中有没有重复的记录。
function isDuplicateByProduct(store,productId,recordId){
    var modifiedRecords = store.getRange(0,store.getCount());
    for (var i = 0;i<modifiedRecords.length;i++) {
        var record = modifiedRecords[i];
        //排除自身。
        if (parseInt(record.data["product_id"]) === parseInt(productId)&& record.id != recordId){
            return true;
        }
    }
    return false;
}

//判断一下当前的Store中有没有重复的记录。
function isDuplicateBySaleOrderProduct(store,saleOrderId,productId){
    var modifiedRecords = store.getRange(0,store.getCount());
    for (var i = 0;i<modifiedRecords.length;i++) {
        var record = modifiedRecords[i];
        //alert(record.data["sale_order_id"] + ":" + saleOrderId + "|" + record.data["product_id"] + ":" + productId)
        if (parseInt(record.data["sale_order_id"]) === parseInt(saleOrderId) && parseInt(record.data["product_id"]) === parseInt(productId)){
            return true;
        }
    }
    return false;
}

//判断一下当前的Store中有没有重复的记录特指销售订单，产品ID，价格，这三元素相同的。这是禁止的。
function isDuplicateForPurchase(store){
    var modifiedRecords = store.getRange(0,store.getCount());
    var strSPPComb = null;//saleid,productid,price三者的组合
    for (var i = 0;i<modifiedRecords.length;i++) {
        var record = modifiedRecords[i];
        if (strSPPComb == null){
            strSPPComb = record.data["sale_order_id"]+record.data["product_id"]+record.data["unit_price"];
        }else{
            if (strSPPComb == record.data["sale_order_id"]+record.data["product_id"]+record.data["unit_price"]){
                return true;
            }else{
                strSPPComb = record.data["sale_order_id"]+record.data["product_id"]+record.data["unit_price"];
            }
        }
    }
    return false;
}

//授信Render
function yesOrNoRender(value){
    if (value === null){
        value = "";
    }
    if(value.indexOf("是")>=0){
        return "<font color=red>"+value+"</font>"
    }else{
        return value;
    }
}

//订单编辑时的状态显示
function orderEditStatusRender(value){
    if (value === null){
        value = "";
    }
    if(value.indexOf("add")>=0){
        return "<font color=green>新增</font>"
    }else if(value.indexOf("edit")>=0){
        return "<font color=blue>编辑</font>"
    }else if(value.indexOf("del")>=0){
        return "<font color=red>删除</font>"
    }else{
        return value;
    }
}

//授信Render
function stockQuantityRender(value){
    if(value == 0){
        return "<font color=red>"+value+"</font>"
    }else{
        return value;
    }
}


//审批时的状态描述Render功能。
function auditingStatusRender(value){
    if (value === null){
        value = "";
    }
    if(value.indexOf("申请")>=0){
        return "<font color=blue>"+value+"</font>"
    }else if(value.indexOf("通过")>=0){
        return "<font color=green>"+value+"</font>"
    }else if(value.indexOf("驳回")>=0){
        return "<font color=red>"+value+"</font>"
    }else if(value.indexOf("撤消")>=0){
        return "<font color=red>"+value+"</font>"
    }else{
        return value;
    }
}

//订单处理的Render功能。
function dealWithStatusRender(value){
    if (value === null){
        value = "";
    }
    if(value.indexOf("出库中")>=0 || value.indexOf("入库中")>=0){
        return "<font color=green>"+value+"</font>"
    }else if(value=="已撤消"){
        return "<font color=red>"+value+"</font>"
    }else if(value=="出库完毕"||value=="入库完毕"){
        return "<font color=blue>"+value+"</font>"
    }else{
        return value;
    }
}

//是否显示金额的Render
function moneyPrivilegeRender(value){
    if (financialManagement === '是'){
        return Ext.util.Format.number(value,'0,000.00');
    }else{
        return '';
    }
}

//是否显示金额的Render
function readStatusRender(value){
    if (value === '未读'){
        return "<font color=red><b>"+value+"</b></font>";
    }else{
        return "<font color=green><b>"+value+"</b></font>";
    }
}

privilegeJsonCallBack = {
    callback: function(record, options, success){
        if (success == false){
            alert("您没有权限执行此操作！");
        }
    }
}
privilegeCallBack =  function(record, options, success){
    if (success == false){
        alert("您没有权限执行此操作！");
    }
}

var pageSize = 10;

departmentSubTypeArray = [
    ['部门'],
    ['店铺'],
    ['分公司']
    ]

saleOrderSubTypeArray = [
    ['个人'],
    ['家装'],
    ['代理'],
    ['工程']
    ]

paymentTypeArray = [
    ['现金'],
    ['刷卡'],
    ['支票'],
    ['汇款'],
    ['电汇'],
    ['网上银行'],
    ['其它']
    ]

articleSubTypeArray = [
    ['新闻'],
    ['帮助'],
    ['问题']
    ]

auditStatusArray = [
    ['申请','申请'],
    ['通过','通过'],
    ['驳回','驳回'],
    ['撤消','撤消'],
    ['未审批',' ']
    ]

logisticsTypeArray = [
    ['陆运'],
    ['海运'],
    ['空运']
    ]
