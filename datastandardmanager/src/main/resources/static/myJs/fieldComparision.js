/**
 * 字段比对的相关js操作
 * 用于已建表页面的 新增字段 删除表的操作
 */
// 点击 添加到表 到列表中
var leftStanderLength = 0;
var rightStanderLength = 0;
var addColumnList = [];
var createdTableMessage = null;
var createdTableIsPartition = false;
var obj = {leftTableColumn:[{rowNum:1,columnEngname:'column',columnChinese:'测试字段',needAdd:true},
                            {rowNum:2,columnEngname:'column1',columnChinese:'测试字段1',needAdd:false}]
             ,rightTableColumn:[{rowNum:1,columnEngname:'column',columnChinese:'测试字段'}]};

var fileldClickModel = {

  'click .addColumnToTable': function (event, value, row, index) {
      // 添加到字段到右侧的表中
    if(row.partition === false){
      addColumnList.push(row);
      var addRow = {rowNum:obj.rightTableColumn.length,columnEngname:row.columnEngname,
        columnChinese:row.columnChinese,needAdd:row.needAdd, type:row.type,
        columnType:row.columnType
      };
      obj.rightTableColumn.push(addRow);
      $("#rightCreateTable").bootstrapTable('load',obj.rightTableColumn);
      row.needAdd = false;
      $("#leftStandardTable").bootstrapTable('load',obj.leftTableColumn);
      addTotalAndtitle('#leftstanderTableDiv', obj.leftTableColumn.length);
      addTotalAndtitle('#rightstanderTableDiv', obj.rightTableColumn.length);
    }else{
      toastr.info("分区字段不能添加")
    }
  },
  'click .cancelAddColumn':function (event, value, row, index) {
      delArrayByName(addColumnList,"columnEngname",row.columnEngname);
      delArrayByName(obj.rightTableColumn,"columnEngname",row.columnEngname)
      $("#rightCreateTable").bootstrapTable('load',obj.rightTableColumn);
      // 当取消添加之后需要把 左侧的表对应的操作改成 可以添加
      for(var i =0; i<obj.leftTableColumn.length;i++){
        if(obj.leftTableColumn[i].columnEngname === row.columnEngname){
           obj.leftTableColumn[i].needAdd = true;
           break;
        }
      }
      $("#leftStandardTable").bootstrapTable('load',obj.leftTableColumn);
    addTotalAndtitle('#leftstanderTableDiv', obj.leftTableColumn.length);
    addTotalAndtitle('#rightstanderTableDiv', obj.rightTableColumn.length);
  }
};

/**
 * 删除对象列表中指定key  指定值 的对应对象数据
 * @param arry
 * @param keyName
 * @param value
 */
function delArrayByName(arry,keyName , value) {
   for(var i =0; i<arry.length;i++){
      if(arry[i][keyName] === value){
         arry.splice(i,1)
         break;
      }
   }

}

/**
 * 获取字段对比的相关数据
 * @param row 已建表的相关信息
 */
function getDateComparision(row) {
  $("#rightFieldType").text(row.tableBase);
  addColumnList = [];
  createdTableMessage = row;
  var objColumn = {createdTableMessage:row,leftStandardColumn :column_table_list};
  $.ajax({
    type: "post",
    url:  "getCreatedFieldComparision",
    contentType: 'application/json',
    dataType: 'json',
    data: JSON.stringify(objColumn),
    calculable: true,
    async: true,
    success: function (result) {
      if(result.status === 1){
         obj = result.result;
      }else{
         obj = {leftTableColumn:[],rightTableColumn:[]};
         toastr.error(result.message);
      }
      $("#leftStandardTable").bootstrapTable('load',obj.leftTableColumn);
      $("#rightCreateTable").bootstrapTable('load',obj.rightTableColumn);
      leftStanderLength = obj.leftTableColumn.length;
      rightStanderLength = obj.rightTableColumn.length;
      addTotalAndtitle('#leftstanderTableDiv', leftStanderLength);
      addTotalAndtitle('#rightstanderTableDiv', rightStanderLength);
      createdTableIsPartition = obj.tablePartition;
      $("#fieldComparisonModal").modal('show');
      // monitorClick("tableOne","rightCreateTable");
      $.unblockUI();
    },
    error: function (result) {
      toastr.error("获取字段比对信息报错，后台程序未启动");
      $.unblockUI();
    }
  });
}

// 左侧标准表字段进行初始化
function leftStandardTableInit(columnDataList) {
  $("#leftStandardTable").bootstrapTable("destroy").bootstrapTable({
    sidePagination: "client", // 分页方式：client客户端分页，server服务端分页
    pagination: true,// 启动分页
    pageSize: 10,// 每页显示的记录条数
    pageNumber: 1,// 当前显示第几页
    pageList: [10, 20, 30, 50],
    search: false,// 是否启用查询,
    showRefresh: false, // 是否显示刷新按钮
    showColumns: false, // 是否显示所有的列
    minimumCountColumns: 2, // 最少允许的列数
    singleSelect: true,
    paginationPreText: '<',
    paginationNextText: '>',
    paginationShowPageGo: true,
    showJumpto: true,
    smartDisplay: false,
    // height: caculateTableHeight(),
    paginationLoop: false,
    clickToSelect: true,
    columns: [
      {field: 'rowNum', title: "序号", valign: "middle", align: "center", colspan: 1, rowspan: 1},
      {field: 'columnEngname', title: "英文名", valign: "middle", align: "center", colspan: 1, rowspan: 1},
      {field: 'columnChinese', title: "中文名", valign: "middle", align: "center", colspan: 1, rowspan: 1},
      {field: 'columnType', title: "字段类型", valign: "middle", align: "center", colspan: 1, rowspan: 1,
        editable:{
          type:'select',
          title:'建表字段类型',
          source:columnDataList,
          placement:'bottom'
        }},
      {
        title: "操作", valign: "middle", align: "center", colspan: 1, rowspan: 1,
        formatter: function (value, row, index) {
          //如果左侧的字段在右侧没有 则需要展示出  添加到表 这个功能
          if(row.needAdd){
              return [
                '<i class="addColumnToTable" style="color:var(--main-color);cursor: pointer;font-style: normal;" >添加到表</i>'
              ].join('');
          }else{
              return [
                ''
              ].join('');
          }

        },
        events: fileldClickModel
      }
    ],
    onPageChange: function (obj) {
      addTotalAndtitle('#leftstanderTableDiv', leftStanderLength);
    }
  });

}


// 右侧标准表字段进行初始化
function rightCreateTableInit() {
  $("#rightCreateTable").bootstrapTable({
    sidePagination: "client", // 分页方式：client客户端分页，server服务端分页
    pagination: true,// 启动分页
    pageSize: 10,// 每页显示的记录条数
    pageNumber: 1,// 当前显示第几页
    pageList: [10, 20, 30, 50],
    search: false,// 是否启用查询,
    showRefresh: false, // 是否显示刷新按钮
    showColumns: false, // 是否显示所有的列
    minimumCountColumns: 2, // 最少允许的列数
    singleSelect: true,
    paginationPreText: '<',
    paginationNextText: '>',
    paginationShowPageGo: true,
    showJumpto: true,
    smartDisplay: false,
    // height: caculateTableHeight(),
    paginationLoop: false,
    clickToSelect: true,
    columns: [
      {field: 'rowNum', title: "序号", valign: "middle", align: "center", colspan: 1, rowspan: 1},
      {field: 'columnEngname', title: "英文名", valign: "middle", align: "center", colspan: 1, rowspan: 1},
      {field: 'columnChinese', title: "中文名", valign: "middle", align: "center", colspan: 1, rowspan: 1},
      {field: 'columnType', title: "字段类型", valign: "middle", align: "center", colspan: 1, rowspan: 1},
      {
        title: "操作", valign: "middle", align: "center", colspan: 1, rowspan: 1,
        formatter: function (value, row, index) {
          //如果左侧的字段在右侧没有 则需要展示出  添加到表 这个功能
          if(row.type === "left"){
            return [
              '<i class="cancelAddColumn" style="color:red;cursor: pointer;font-style: normal;" >取消添加</i>'
            ].join('');
          }else{
            return [
              ''
            ].join('');
          }

        },
        events: fileldClickModel
      }
    ],
    onPageChange: function (obj) {
      addTotalAndtitle('#rightstanderTableDiv', rightStanderLength);
    }
  });

}

/**
 *  保存字段对比的相关信息
 */
function saveFieldComparison() {
  if(addColumnList.length === 0){
    toastr.info("没有需要新增的字段信息");
    cancelSaveFieldComparison();
    return;
  }
  try{
    findIp(function (ip) {
      console.log('get ip: ', ip);
      ajaxSaveFieldComparison(ip);
    })
  }catch (e) {
    ajaxSaveFieldComparison("");
    console.log("获取ip地址报错"+e);
  }
}


function ajaxSaveFieldComparison(localIp) {
  addMask();
  var data = {columnList:addColumnList,
    createdTableData:createdTableMessage,
    tableId:$("#tableId").text(),
    createdTableIsPartition: createdTableIsPartition,
    localIp: localIp,
    userId: getUserId()
  };
  $.ajax({
    type: "post",
    url:  "saveFieldComparison",
    contentType: 'application/json',
    dataType: 'json',
    data: JSON.stringify(data),
    calculable: true,
    async: true,
    success: function (result) {
      // 如果需要审批，则会跳转到审批页面
      // 否则是直接展示新增字段的成功状况
      $.unblockUI();
      var data = result.result;
      if(result.status === 1){
        if(data.approvalInfo === "true"){
          var iframeUrl = data.message;
          $('#approvalInfoHtml').attr('src', iframeUrl);
          $("#importApprovalInfoModal").modal('show');
        }else{
          toastr.info(data.message);
        }
        cancelSaveFieldComparison();
      }else{
        toastr.error(result.message);
      }
    },
    error: function (result) {
      $.unblockUI();
      toastr.error("保存字段比对信息报错，后台程序未启动");
    }
  });
}

function saveFieldComparisonPrompt() {
    if(addColumnList.length === 0){
      toastr.info("没有需要新增的字段信息,请检查相关操作");
      return;
    }
    var str = "";
    for(var i=0;i<addColumnList.length;i++){
       str += addColumnList[i].columnEngname+" ";
    }
    $("#addColumnModal").text(str);
    $("#fieldComparisonModal").modal('hide');
    $("#fieldComparisonModalPrompt").modal('show');
}

function cancelSaveFieldComparison() {
  $("#fieldComparisonModalPrompt").modal('hide');
  $("#fieldComparisonModal").modal('hide');

}

/**
 * 监控下一页按钮以及使另一个表格也同步点击
 * @param clickDiv  监控点击的div   tableOne
 * @param otherTableId  需要同步点击的另一个 tableId     tableDataSourceTwo
 */
// function monitorClick(clickDiv , otherTableId) {
//   var el = document.getElementById(clickDiv);
//   el.querySelector(".fixed-table-pagination").addEventListener("click",function () {
//     // 获取点击按钮上的值 获取本次点击的页数
//     var clickValue = $("#"+clickDiv+" .pagination .active a").text();
//     $('#'+otherTableId).bootstrapTable('selectPage' , clickValue);
//   })
// }