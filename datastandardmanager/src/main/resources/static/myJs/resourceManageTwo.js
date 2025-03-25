/**
 *
 * @param classIds
 */
function getCodeNameByClassifyId(classIds) {
  var returnStr = "";
  $.ajax({
    type: "get",
    url:  "getCodeNameByClassifyId",
    // contentType: 'application/json',
    // dataType: 'json',
    calculable: false,
    async: false,
    data: {
      "classifyIds": classIds
    },
    success: function (result) {
      returnStr = result;
    },
    error: function (result) {
      toastr.error("获取数据来源的相关信息报错");
    }
  });
  return returnStr;
}

/**
 * 当分级分类信息发生变化时，来源协议编码发生变化
 * GA_SOURCE_01001_650100_00016
 * @param codeName
 * @param oldSourceId
 */
function getSourceIdByCodeName(codeName,oldSourceId) {

    var sourceIdNew = "GA_SOURCE_"+codeName+"_";
    // 如果oldSourceId中已经有数据 则将之前已经编写的去除掉
    if(oldSourceId.trim().indexOf("GA_SOURCE_") === 0){
      var oldSourceIdList = oldSourceId.split("_");
      if(oldSourceIdList.length >3 && !checkIsNull(oldSourceIdList[3])){
         var data = oldSourceIdList.splice(3,oldSourceIdList.length - 1);
         $("#SOURCEID_SOURCE_input").val(sourceIdNew + data.join("_"));
      }else{
         $("#SOURCEID_SOURCE_input").val(sourceIdNew + getQueryParam("localAdministrativeCode")+"_80001");
      }
    }else{
      $("#SOURCEID_SOURCE_input").val(sourceIdNew);
    }
}


function showExplain() {
  $("#protocalRule").modal("show");
}

/**
 *  在保存时，先自动更新对应的sourceid值
 *  如果需要，则自动获取 最新的来源协议编码
 */
function getNewSourceId() {
  var moduleName = getQueryParam("moduleName");
  var sourceIdInput = $("#SOURCEID_SOURCE_input").val();
  if(checkIsNull(moduleName) || sourceIdInput === undefined){
    return;
  }
  $.ajax({
    type: "get",
    url:  "getNewSourceIdById",
    // contentType: 'application/json',
    // dataType: 'json',
    calculable: false,
    async: false,
    data: {
      "sourceId": $("#SOURCEID_SOURCE_input").val(),
      "dataSourceClassify": $("#sourceClassifyInput").val(),
      "code": getQueryParam("localAdministrativeCode")
    },
    success: function (result) {
      if( result === null){
         toastr.error("获取数据来源信息页面中最新的来源协议编码报错");
         throw DOMException("");
      }else{
         $("#SOURCEID_SOURCE_input").val(result);
         toastr.info("最新的来源协议编码为:"+result);
      }
    },
    error: function () {
      toastr.error("获取最新的sourceId报错");
    }
  });

}

/**
 *  判断 datahub 数据库类型的参数是否为空
 */
function checkDatahubMessageIsNull() {
   // 数据源名称
   var dataResourceName = $("#dataResourceName option:selected").val();
   // 项目名
   var projectName = $("#projectName option:selected").val();
   // topicName
   var topicName = $("#topicName").val();
   // 通道数
   var shardCount = $("#shardCount").val();
   // 生命周期
   var lifeCycle = $("#lifeCycle").val();

   if (dataResourceName === "selected" || checkIsNull(dataResourceName)) {
      toastr.error("数据源名称不能为空");
      return true;
   }
   if (projectName === "selected" || checkIsNull(projectName)) {
     toastr.error("项目名不能为空");
     return true;
   }
   if (checkIsNull(topicName)) {
     toastr.error("topic不能为空");
     return true;
   }
   if (checkIsNull(shardCount)) {
     toastr.error("通道数不能为空");
     return true;
   }
   if (checkIsNull(lifeCycle)) {
     toastr.error("生命周期不能为空");
     return true;
   }

   // Dt字段必须存在，且是最后一个字段
   var tableSize =  column_table_list.length;
   if(tableSize === 0){
     toastr.error("字段不能为空");
     return true;
   }
   var columnNameLast = column_table_list[tableSize - 1];
   if("DT" !== columnNameLast.columnName){
     toastr.error("Dt字段必须存在，且是最后一个字段");
     return true;
   }
}


/**
 * 关闭状态选择的模态框
 */
function closeStatusCheckModal() {
  $("#statusCheckModal").modal("hide");
}

/**
 * 修改为编辑状态的点击事件
 */
function checkStatusCheck() {
  editResourceManageTable('click');
  $("#statusCheckModal").modal("hide");
}