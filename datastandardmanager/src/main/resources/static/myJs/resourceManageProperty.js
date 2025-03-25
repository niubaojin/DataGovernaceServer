var path=window.document.location.pathname.split("/")[1];
path="/"+path;
var queryTableId = "";
var queryObjectId = "";
var queryTableName = "";  //精确到表
var queryProjectName = "";  // 查询到的项目名
var queryType = ""; //   数据库类型
var conLength = 0;
var dataListLength = 0;
var tableObj = '';

$(document).ready(function() {
  toastr.options.positionClass = 'toast-top-left';
  queryTableId = getQueryParam("queryTableId");
  queryObjectId = getQueryParam("queryObjectId");
  queryTableName = getQueryParam("queryTableName");
  queryProjectName = getQueryParam("queryProjectName");
  queryType = getQueryParam("queryType");
    windowResizeEvent();
  // 字段定义表的创建
    tableObj = resourceManageObjectField();
  addTotalAndtitle('#contentPaig',conLength);
  if(checkIsNull(queryTableId)){
    toastr.error("传入的tableId为空");
    return;
  }
  // 获取对应的数据信息和字段信息
  getDataColumnMessage();
  if(checkIsNull(queryObjectId)){
      toastr.error("传入的objectId为空");
  }
  if(checkIsNull(queryTableName)||checkIsNull(queryProjectName)||checkIsNull(queryType)){
      toastr.error("传入的表名/项目名/查询类型为空");
      return;
  }
  //  获取所有数据的data和column
  getAllExampleData();

});

/**
 * 获取 数据信息和字段信息
 */
function getDataColumnMessage() {
  $.ajax({
    type:"post",
    data:{
      "tableId":queryTableId
    },
    url:"resourceManageObjectDetail",
    calculable : false,
    async:true,
    success:function(result){
      if(result.tableId!=""&&result.tableId!=null){
        $("#tableId").text(result.tableId);
      }else{
        $("#tableId").text("");
      }
      // sourceId的相关信息
      if(result.sourceId!=""&&result.sourceId!=null){
        $("#sourceId").text(result.sourceId);
      }else{
        $("#sourceId").text("");
      }
      // 注释的信息 20191118
      if(result.objectMemo!=""&&result.objectMemo!=null){
        $("#objectMemo").text(result.objectMemo);
      }else{
        $("#objectMemo").text("");
      }
      // if(result.objectId!=""&&result.objectId!=null){
      //   $("#objectIdManage").text(result.objectId);
      // }else{
      //   $("#objectIdManage").text("");
      // }
      if(result.dataSourceName!=""&&result.dataSourceName!=null){
        $("#dataSourceName").text(result.dataSourceName);
      }else{
        $("#dataSourceName").text("");
      }
      if(result.realTablename!=""&&result.realTablename!=null){
        $("#realTableName").text(result.realTablename);
      }else{
        $("#realTableName").text("");
      }
      if(result.storageTableStatus!=""&&result.storageTableStatus!=null){
        $("#storageTableStatus").text(result.storageTableStatus);
      }else{
        $("#storageTableStatus").text("");
      }
      if(result.storageDataMode!=""&&result.storageDataMode!=null){
        $("#storageMode").text(result.storageDataMode);
      }else{
        $("#storageMode").text("");
      }
      if(result.ownerFactory!=""&&result.ownerFactory!=null){
        $("#sourceFirm").text(result.ownerFactory);
      }else{
        $("#sourceFirm").text("");
      }
      if(result.codeTextTd!=""&&result.codeTextTd!=null){
        $("#objectName").val(result.codeTextTd);
      }else{
        $("#objectName").val("");
      }
      if(result.codeTextCh!=""&&result.codeTextCh!=null){
        $("#objectName").text(result.codeTextCh);
      }else{
        $("#objectName").text("");
      }
      if(result.organizationClassify!=""&&result.organizationClassify!=null){
        $("#organizationClassify").text(result.organizationClassify);
      }else{
        $("#organizationClassify").text("");
      }
      // if(result.sourceClassify!=""&&result.sourceClassify!=null){
      //   $("#sourceClassify").text(result.sourceClassify);
      // }else{
      //   $("#sourceClassify").text("");
      // }
    },
    error:function(result){
      //$("#tableIdTd").value("1111")
    }
  });
  // 查询字段定义的相关信息
  $("#resourceManageObjectField").bootstrapTable('removeAll');
  addMask();
  $.ajax({
    type:"post",
    data:{
      "tableId":queryTableId
    },
    url:"resourceManageObjectField",
    calculable : false,
    async:true,
    success:function(result){
      try{
        $("#resourceManageObjectField").bootstrapTable('load',result);
      }catch (e){
        console.log(e);
        toastr.error("获取字段信息失败，没有字段信息");
      }
      $.unblockUI();
        conLength = result.length;
      addTotalAndtitle('#contentPaig',conLength);
       var scrollbar = new PerfectScrollbar("#contentPaig .fixed-table-body",{});
    },
    error:function(result){
      $.unblockUI();
    }
  });
}


/**
 * 获取样例数据
 */
function getAllExampleData() {
    addMask();
    var column = [{field: 'result',title:"样例数据",valign:"middle",align:"center"}];
    initTableDemoData(column);
    $("#tableExampleData").bootstrapTable('load',[{"result":"查询中！！！！"}]);
    $.ajax({
        type:"get",
        url:"getAllExampleData",
        calculable : false,
        data:{
            queryTableName:queryTableName,
            queryProjectName:queryProjectName,
            queryType:queryType
        },
        async:true,
        success:function(result){
            if(result.status === 1){
                // 样例数据表的创建
                var columnList = result.data.columnList;
                var dataList = result.data.exampleDataList;
                dataListLength = dataList.length;
                initTableDemoData(columnList);
                $("#tableExampleData").bootstrapTable('load',dataList);

                addTotalAndtitle('#dataPagi',dataListLength);
            }else{
                var column = [{field: 'result',title:"获取样例数据报错",valign:"middle",align:"center"}];
                initTableDemoData(column);
                $("#tableExampleData").bootstrapTable('load',[{"result":"报错信息："+result.msg}]);
                // toastr.error(result.msg)

                addTotalAndtitle('#dataPagi',dataListLength);
            }
            $.unblockUI();
        },
        error:function(result){
            $.unblockUI();
            var column = [{field: 'result',title:"获取样例数据报错",valign:"middle",align:"center"}];
            initTableDemoData(column);
            $("#tableExampleData").bootstrapTable('load',[{"result":"获取样例数据报错，后台服务未启动"}]);
            // toastr.error("获取样例数据报错");

            addTotalAndtitle('#dataPagi',dataListLength);
        }
    });
}

/**
 * 窗口大小改变事件
 */
function windowResizeEvent() {
    $(window).resize(function () {
        var tableHeight = $("#contentPaig").height()-20;
        tableObj.bootstrapTable('resetView',{
            height:tableHeight
        });
    });
}

/**
 * 计算获得表的高度
 * @returns {number}
 */

function caculateTableHeight() {
    var tableHeight = $("#contentPaig").height()-60;
    return tableHeight;
}


function mocaculateTableHeight() {
    var tableHeight = $("#dataPagi").height() - 20;
    return tableHeight;
}

//创建bootstrap表格(对象字段表)
function resourceManageObjectField(){
  //构造表格
 var obj = $("#resourceManageObjectField").bootstrapTable({
//	sidePagination : "server", // 分页方式：client客户端分页，server服务端分页
    pagination : true,// 启动分页
    pageSize : 10,// 每页显示的记录条数
    pageNumber : 1,// 当前显示第几页
    pageList : [10,20,30,50],
    paginationPreText: '<',
    paginationNextText: '>',
    paginationShowPageGo: true,
    showJumpto: true,
    paginationLoop: false,
    smartDisplay: false,
    search : false,// 是否启用查询,
    showRefresh : false, // 是否显示刷新按钮
    showColumns : false, // 是否显示所有的列
    minimumCountColumns : 2, // 最少允许的列数
    height: caculateTableHeight(),
    onDblClickRow:function(row,$element){  //双击效果
      // showObjectField(row);
    },
    clickToSelect : false,
    columns : [
      {field: 'recno',title:"序号",valign:"middle",align:"center",colspan:1,rowspan: 1},
      {field: 'fieldId',title:"元素编码",valign:"middle",align:"center",colspan:1,rowspan: 1},
      {field: 'columnName',title: "建表字段 ",valign:"middle",align:"center",colspan: 1,rowspan: 1},
      {field: 'fieldChineeName',title: "字段中文名",valign:"middle",align:"center",colspan: 1,rowspan: 1},
      {field: 'fieldType',title: "字段类型",valign:"middle",align:"center",colspan: 1,rowspan: 1,
        formatter:function(value,row,index){
          var value = "";
          if(row.fieldType=="0"){
            value = "整形";
          }else if(row.fieldType=="1"){
            value = "浮点型";
          }else if(row.fieldType=="2"){
            value = "字符串类型";
          }else if(row.fieldType=="3"){
            value = "日期型，格式为yyyyMMdd";
          }else if(row.fieldType=="4"){
            value = "日期型，格式为yyyyMMddHH:mm:ss";
          }else if(row.fieldType=="6"){
            value = "Long类型";
          }else if(row.fieldType=="7"){
            value = "double类型";
          }
          return value;
        }
      },
      {field: 'fieldLen',title: "字段长度",valign:"middle",align:"center",colspan: 1,rowspan: 1},
      {field: 'isIndex',title: "是否索引",valign:"middle",align:"center",colspan: 1,rowspan: 1,
        formatter:function(value,row,index){
          var value = "";
          if(row.isIndex=="0"){
            value = "否";
          }else if(row.isIndex=="1"){
            value = "是";
          }
          return value;
        }
      },
      {field: 'needValue',title: "是否必填",valign:"middle",align:"center",colspan: 1,rowspan: 1,
        formatter:function(value,row,index){
          var value = "";
          if(row.needValue=="0"){
            value = "否";
          }else if(row.needValue=="1"){
            value = "是";
          }
          return value;
        }
      },
      {field: 'codeText',title: "代码中文名",valign:"middle",align:"center",colspan: 1,rowspan: 1},
      {field: 'codeid',title: "codeId",valign:"middle",align:"center",colspan: 1,rowspan: 1,visible : false},
      {field: 'memo',title: "备注",valign:"middle",align:"center",colspan: 1,rowspan: 1},
      {field: 'fieldName',title: "标准列名",valign:"middle",align:"center",colspan: 1,rowspan: 1}
    ],
    onPageChange: function () {
        addTotalAndtitle('#contentPaig',conLength);

    }

  });
 return obj;

};

// 表详细信息 的点击事件
function tableDivOnclick() {
  $(".TableDiv").css("display","inline");
  $("#rightPanelPanel").css("display","none");
  $(".dataTableDiv").css("display","none");
}

function dataOnclick() {
  $(".TableDiv").css("display","none");
  $("#rightPanelPanel").css("display","none");
  $(".dataTableDiv").css("display","inline");

}

function classifyOnclick() {
  $(".TableDiv").css("display","none");
  $(".dataTableDiv").css("display","none");
  $("#rightPanelPanel").css("display","inline")
  // 获取配置的资源服务平台对应的页面
  addMask();
  $.ajax({
    type:"get",
    data:{
      "objectId":queryObjectId
    },
    url: "getClassifyOnclick",
    calculable : false,
    async:true,
    success:function(result){
      if(result.status === 1){
        $('#contentPage').attr('src',result.data);
      }else{
        toastr.error(result.message)
      }
      $.unblockUI();
    },
    error:function(result){
      $.unblockUI();
    }
  });
}

// 样例数据
function initTableDemoData(myColumns){
    $('#tableExampleData').bootstrapTable('destroy');
    $("#tableExampleData").bootstrapTable({
        method: "get",
        dataType:"json",
        sidePagination : "client", // 分页方式：client客户端分页，server服务端分页
        pagination : true,// 启动分页
        pageSize : 20,// 每页显示的记录条数
        pageNumber : 1,// 当前显示第几页
        pageList : [10,20,30,50],
        paginationPreText: '<',
        paginationNextText: '>',
        paginationShowPageGo: true,
        showJumpto: true,
        paginationLoop: false,
        smartDisplay: false,
        cache: false,
        columns : myColumns,
        height: mocaculateTableHeight(),
        onPageChange: function () {
            addTotalAndtitle('#dataPagi',dataListLength);
        }

    });
    $('#tableExampleData th div.th-inner').each(function(i, el){
        $(el).attr('title', $(el).text());
    });
    var scrollbar = new PerfectScrollbar("#dataPagi .fixed-table-body",{});

}


// 添加遮罩
function addMask() {
  $.blockUI({
    message: '<div style="line-height: 2"><div class="loadEffect">\n' +
    '        <span></span>\n' +
    '        <span></span>\n' +
    '        <span></span>\n' +
    '        <span></span>\n' +
    '        <span></span>\n' +
    '        <span></span>\n' +
    '        <span></span>\n' +
    '        <span></span>\n' +
    '</div><div style="font-size: 16px">正在加载中，请稍后...</div></div>',
    css: {
      marginLeft: '0.5%',
      marginTop: '50px',
      boxShadow: '0 1px 5px #CBCCCD',
      border: 'none',
      '-webkit-border-radius': '10px',
      width: '25%'
    },
    overlayCSS: {
      marginLeft: '0.5%',
      marginTop: '50px',
      opacity: '0.1'
    }
  });
}

function addTotalAndtitle(id,num) {
    $( id+" .page-list ul li").append('<span>条/页</span>');
    $( id+" .page-list button span.page-size").append('<span>条/页</span>');
    $( id+' .fixed-table-pagination').prepend('<div class="total-num">共 '+ num+' 条</div>');
}