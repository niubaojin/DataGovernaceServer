document.write("<script language='JavaScript' src='myJs/commons.js'> </script>");
var path=window.document.location.pathname.split("/")[1];
path="/"+path;

var GLOBAL = {
    dwParams:{
        sourceProtocol:'',
        tableName:'',
        sourceFirm:'',
        sourceSystem:''
    },
    tableID:'JZ_RESOURCE_MEE',
    tableStructJsonStr:'',//列信息
    dwTableInfo:{},
    isFlow:true //是否是流程
}
var initData;

$(document).ready(function(){
    toastr.options.positionClass = 'toast-top-center';

    checkAndGetParams();//检查是否从流程中来


    //初始化目标协议
    //initTargetProtocol(sourceProtocol);

    //初始化厂商
    initManufacturer();

    //初始化协议系统代码
    initProtocolNum();

    //初始元素编码添加表
    initSourceFieldTable();

    //标准字段表
    //initStandardFieldTable();

    $('#importSrcFieldModal').modal('show');
    //保存按钮
    save();
});

function checkAndGetParams(){
    //获取url中的参数
    GLOBAL.dwParams.sourceProtocol = getQueryParam("sourceProtocol");//来源协议
    GLOBAL.dwParams.tableName = getQueryParam("tableName");//数据名称
    GLOBAL.dwParams.sourceFirm = getQueryParam("sourceFirm");//来源单位
    GLOBAL.dwParams.sourceSystem = getQueryParam("sourceSystem");//来源系统

    var tableStructJsonStr = getQueryParam("tableStructJsonStr");//表结构
    if(!checkIsNull(tableStructJsonStr)){
        GLOBAL.dwTableInfo = decodeURIComponent(tableStructJsonStr);
    }else{
        GLOBAL.isFlow=false;
    }

    console.log("sourceProtocol:"+ GLOBAL.dwParams.sourceProtocol);
    console.log("tableName:"+ GLOBAL.dwParams.tableName);
    console.log("sourceFirm:"+ GLOBAL.dwParams.sourceFirm);
    console.log("sourceSystem:"+ GLOBAL.dwParams.sourceSystem);
    console.log("tableStructJsonStr:"+GLOBAL.dwTableInfo);
}

function initManufacturer(){//初始化厂商
    $.ajax({
        type:"post",
        data:{},
        url: "initManufacturer",
        calculable : false,
        async:true,
        before:function(){},
        success:function(data){
            if(data.status==1){//成功
                var dataList = data.result;
                $("#manufacturerSelect").empty(); //clear
                $.each(dataList, function(index, value){
                    $("#manufacturerSelect").append("<option value="+value+">"+value+"</option>");
                });
            }else{//失败
                bootbox.alert("在初始化厂商时出错,错误信息为:"+data.message);
            }
        },
        error:function(result){}
    });
}

function initProtocolNum(){//初始化协议系统代码
    $.ajax({
        type:"post",
        data:{},
        url: "initProtocolNum",
        calculable : false,
        async:true,
        before:function(){},
        success:function(data){
            if(data.status==1){//成功
                var dataList = data.result;
                $("#sysName").empty(); //clear
                $.each(dataList, function(index, value){
                    $("#sysName").append("<option value="+value.sysID+">"+value.sysChiName+"("+value.sysID+")"+"</option>");
                });
            }else{//失败
                bootbox.alert("在初始化协议时出错,错误信息为:"+data.message);
            }
        },
        error:function(data){}
    });
}

//标准表字段
function initStandardFieldTable(){

    $("#standardFieldTableTitle").text(GLOBAL.tableID+"标准字段表");
    $("#standardFieldTable").bootstrapTable({
        url :  "initStandardFieldTable",
        queryParams:{
            "tableID":GLOBAL.tableID
        },
        data:[],
        sidePagination : "client", // 分页方式：client客户端分页，server服务端分页
        pagination : true,// 启动分页
        paginationPreText: '上一页',
        paginationNextText: '下一页',
        pageSize : 10,  //每页显示的记录条数
        pageNumber : 1, //当前显示第几页
        pageList : [5],
        search : false,// 是否启用查询,
        showRefresh : false, // 是否显示刷新按钮
        showColumns : false, // 是否显示所有的列
        minimumCountColumns : 2, // 最少允许的列数
        clickToSelect : true,
        uniqueId :"id",
        singleSelect : true,
        columns : [
            {checkbox : true},
            {field: 'fieldName',title: "标准字段名",valign:"middle",align:"center",colspan: 1,rowspan: 1},
            {field: 'fieldtypes',title: "字段类型",valign:"middle",align:"center",colspan: 1,rowspan: 1},
            //{field: 'fieldLen',title: "字段长度",valign:"middle",align:"center",colspan: 1,rowspan: 1},
            {field: 'fieldChineeName',title: "字段描述信息",valign:"middle",align:"center",colspan: 1,rowspan: 1},
        ],
    });
}

//源字段信息
function initSourceFieldTable(){
    console.log("dwParams:"+GLOBAL.dwParams);
    //获取源协议
    // $("#sourceFieldTableTitle").text(GLOBAL.dwParams.sourceProtocol+"源字段表");
    $("#sourceFieldTable").bootstrapTable({
        type:'POST',
        url : "initSourceFieldTable",
        queryParams:{
            "sourceProtocol":GLOBAL.dwParams.sourceProtocol,
            "tableName":GLOBAL.dwParams.tableName,
            "sourceFirm": GLOBAL.dwParams.sourceFirm,
            "sourceSystem":GLOBAL.dwParams.sourceSystem ,
            "dwTableInfo":GLOBAL.dwTableInfo
        },
        contentType : "application/json,charset=utf-8",
        sidePagination : "client", // 分页方式：client客户端分页，server服务端分页
        pagination : true,// 启动分页
        paginationPreText: '上一页',
        paginationNextText: '下一页',
        pageSize : 8,  //每页显示的记录条数
        pageNumber : 1, //当前显示第几页
        pageList : [5],
        search : false,// 是否启用查询,
        showRefresh : false, // 是否显示刷新按钮
        showColumns : false, // 是否显示所有的列
        minimumCountColumns : 2, // 最少允许的列数
        clickToSelect : true,
        uniqueId :"id",
        //singleSelect : true,
        columns : [
            {checkbox : true},
            {field: 'fieldName',title: "源字段名",valign:"middle",align:"center",colspan: 1,rowspan: 1},
            {field: 'fieldType',title: "字段类型",valign:"middle",align:"center",colspan: 1,rowspan: 1},
            {field: 'fieldLength',title: "字段长度",valign:"middle",align:"center",colspan: 1,rowspan: 1},
            {field: 'isPrimarykey',title: "是否是索引",valign:"middle",align:"center",colspan: 1,rowspan: 1},
            {field: 'isNonnull',title: "是否为必填",valign:"middle",align:"center",colspan: 1,rowspan: 1},
            {field: 'fieldDescription',title: "字段描述信息",valign:"middle",align:"center",colspan: 1,rowspan: 1},
        ],
    });
}

function initTargetProtocol(sourceProtocol){//初始化目标协议
    //需要根据来源协议，来查找目标协议
    if(checkIsNull(sourceProtocol)){//如果探查传递过来的数据为空，则让他们回去，继续探查
        bootbox.alert("接受到的源协议为空，请返回到[数据探查]步骤,填写[协议编码]必填项");
    }else{//ajax请求后台，需要将源协议转换成目标协议
        $.ajax({
            type:"post",
            data:{

            },
            url: "initTargetProtocol",
            calculable : false,
            async:true,
            before:function(){},
            success:function(result){},
            error:function(result){
                //$("#tableIdTd").value("1111")
            }
        });
    }
}
function save(){
    $("#save").click(function (){
        //获取sourceFieldTable选中的数据行
        var getSelectRows = $('#sourceFieldTable').bootstrapTable('getSelections');
        if (getSelectRows.length <= 0) {return;}
        console.log(getSelectRows);
        $.ajax({
            type:"post",
            data: JSON.stringify({
                'sourceFieldInfo':getSelectRows,
                'tableID':GLOBAL.tableID
            }),
            contentType:'application/json',
            dataType:'json',
            url: "saveSrcField",
            calculable : false,
            async:false,
            before:function(){},
            success:function(result){
                if(result.status === 1){//表示成功！
                    toastr.info("保存成功");
                }else{
                    toastr.error("保存失败");
                }
            },
            error:function(result){
                toastr.error("保存失败");
                //$("#tableIdTd").value("1111")
            }
        });

        // for(var i=0;i<getSelectRows.length;i++){
        //     columnName = getSelectRows[i].columnName;
        //     fieldChineeName = getSelectRows[i].fieldChineeName;
        //     ds = ds + columnName +" as "+fieldChineeName;
        //     if((getSelectRows.length!=1)&&(i!=(getSelectRows.length-1))){
        //         ds =  ds + ",</br>";
        //     }
        // }

        //如果是流程中，需要进行回调
        // if(GLOBAL.isFlow){
        //     var message={},taskInfo={};
        //     taskInfo.standardId = "irmkdilosiourioejfkldsa";
        //     message.pageTag='standard';
        //     message.standardPropStr=JSON.stringify(taskInfo);
        //     message.outSourceSystem = GLOBAL.dwParams.sourceSystem;
        //     message.outTableName = GLOBAL.dwParams.tableName;
        //     message.outSourceProtocol = GLOBAL.dwParams.sourceProtocol;
        //     message.outSourceFirm = GLOBAL.dwParams.sourceFirm;
        //     message.outSourceSystemName = GLOBAL.dwParams.sourceSystem;
        //     message.objectId="165";
        //     window.parent.postMessage(message,"*");
        // }

    });
}






