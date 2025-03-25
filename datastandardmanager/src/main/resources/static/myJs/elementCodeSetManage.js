var path=window.document.location.pathname.split("/")[1];
path="/"+path;
var codeIdByLeft=null;
var codeIdByRight=null;

$(document).ready(function() {
  //元素代码集
  codeTableManage();
  //元素代码集值
  codeValTableManage();
  var path1 = path+"/getCodeValIdList?condition=";
  twoSuggestNew("parcodeIdInput",path1);
  twoSuggestNew("brotherCodeIdInput",path1);
  InitExcelFile();

});

window.operateEvents={
  // 点击编辑页面之后的事件
  'click .edit':function(e,value,row,index){
    console.log(row);
    $(".pageAddressThree").css("display","inline");
    $(".codediv").css("display","none");
    $(".codeValdiv").css("display","inline");
    codeIdByRight=row.codeId;

    $("#codeValTable").bootstrapTable('refresh');
    // 回填输入框
    $("#sourceProtocolMode").val(row.codeName);
    // $("#sourceProtocolMode").attr("readOnly","true");
    $("#codeTextInput2").val(row.codeText);
    // $("#codeTextInput2").attr("readOnly","true");
    $("#parcodeIdInput").val(row.parcodeId);
    // $("#parcodeIdInput").attr("readOnly","true");
    $("#brotherCodeIdInput").val(row.brotherCodeId);
    // $("#brotherCodeIdInput").attr("readOnly","true");
    $("#codeId").attr("readOnly","true");
    $("#codeId").val(row.codeId);
  },
  // 点击删除之后，先弹出确定框，然后再删除选择的一行数据
  'click .delete':function (e,value,row,index) {
    bootbox.confirm({
      size: "small",
      backdrop: true,
      title:"<span style='font-size: 16px;'>提示</span>",
      message: "<img src='img/myImg/warn.png' style='margin: -5px 10px 0 0;float: left'><span>是否要删除选择的元素代码值信息？</span>",
      buttons : {
        confirm : {
          label : '确定',
          className : 'my-btn-confirm'
        },
        cancel : {
          label : '取消',
          className : 'my-btn-cancle'
        }
      },
      callback : function(result) {
        if (result) {
          $.ajax({
            type:"post",
            url:path+"/delCodeTable",
            data:JSON.stringify(row),
            calculable : false,
            async:false,
            contentType:'application/json',
            dataType:'json',
            success:function(result){
              if(result.status === 1){
                toastr.info(result.data);
                // 刷新刚删除的数据
                $("#codeTable").bootstrapTable('refresh');
              }else{
                toastr.error(result.message);
              }
            },
            error:function (result) {
              toastr.error("删除失败!!!!!!");
              return;
            }
          });
        }
      }
    });
  },
  //  点击代码集基本信息中的编辑信息
  'click .codeValEdit':function (e,value,row,index) {
    $('#addCodeValModal').modal('show');
    $("#valTextModal").val(row.valText);
    $("#valValueModal").val(row.valValue);
    $("#sortIndexModal").val(row.sortIndex);
    $("#valTextTitleModal").val(row.valTextTitle);
    $("#memoModal").val(row.memo);
    $("#codeValIdModalBack").val(row.codeValId);
  },
  // 点击删除之后，先弹出确定框，然后再删除选择的一行数据
  'click .codeValDelete':function (e,value,row,index) {
    bootbox.confirm({
      size : "middle",
      message : "是否要删除选择的代码集基本信息",
      buttons : {
        confirm : {
          label : '确定',
          className : 'btn-default'
        },
        cancel : {
          label : '取消',
          className : 'btn-default'
        }
      },
      callback : function(result) {
        if (result) {
          $.ajax({
            type:"post",
            url:path+"/delCodeValTable",
            data:JSON.stringify(row),
            calculable : false,
            async:false,
            contentType:'application/json',
            dataType:'json',
            success:function(result){
              if(result.status === 1){
                toastr.info(result.data);
                // 刷新刚删除的数据
                $("#codeValTable").bootstrapTable('refresh');
              }else{
                toastr.error(result.message);
              }
            },
            error:function (result) {
              toastr.error("删除失败!!!!!!");
              return;
            }
          });
        }
      }
    });
  }
};

//创建bootstrap表格(元素代码集)
function codeTableManage(){
  //构造表格
  $("#codeTable").bootstrapTable({
    url : "codeTable",
    method: "post",
    dataType:"json",
    contentType : "application/x-www-form-urlencoded",
    queryParams: function (params){
      return {
        pageSize: params.limit,   //页面大小
        pageIndex: params.offset/params.limit+1,  //页码
        codeName:$.trim($('#codeNameInput').val()),
        codeText:$.trim($('#codeTextInput').val()),
        codeId:codeIdByLeft
      };
    },
    sidePagination : "server", // 分页方式：client客户端分页，server服务端分页
    pagination : true,// 启动分页
    pageSize : 15,// 每页显示的记录条数
    pageNumber : 1,// 当前显示第几页
    pageList : [15],
    paginationPreText: '上一页',
    paginationNextText: '下一页',
    search : false,// 是否启用查询,
    showRefresh : false, // 是否显示刷新按钮
    showColumns : false, // 是否显示所有的列
    minimumCountColumns : 2, // 最少允许的列数
    clickToSelect : false,
    // onClickRow:function(row,$element){
    // 	$('.info').removeClass('info');
    // 	$($element).addClass('info');
    //
    // },
    uniqueId :"codeId",
    columns : [
      {checkbox : true},
      {field: 'codeId',title:"主键ID",valign:"middle",align:"center",colspan:1,rowspan: 1,visible : false},
      {field: 'serialNum',title: "序号",valign:"middle",align:"center",colspan: 1,rowspan: 1},
      {field: 'codeName',title: "代码英文名称",valign:"middle",align:"center",colspan: 1,rowspan: 1},
      {field: 'codeText',title: "代码中文名称",valign:"middle",align:"center",colspan: 1,rowspan: 1},
      {field: 'parcodeId',title: "代码集父ID",valign:"middle",align:"center",colspan: 1,rowspan: 1},
      {field: 'brotherCodeId',title: "引用的代码集ID",valign:"middle",align:"center",colspan: 1,rowspan: 1},
      {title: "操作",valign:"middle",align:"center",colspan: 1,rowspan: 1,events:operateEvents
        ,formatter:function () {
          return [
            '<span style="color:var(--main-color);cursor: pointer;" class="edit myiconfont" title="编辑">&#xe63c;</span>  |  '+
            '<span style="color:var(--main-color);cursor: pointer;" class="delete myiconfont" title="删除">&#xe505;</span> ',
          ].join("")
        }
      }
    ],
  });
};
//创建bootstrap表格(元素代码集值)
function codeValTableManage(){
  //构造表格
  $("#codeValTable").bootstrapTable({
    method: "post",
    contentType : "application/x-www-form-urlencoded",
    dataType:"json",
    url : path+"/codeValTable",
    sidePagination : "server", // 分页方式：client客户端分页，server服务端分页
    queryParams: function (params){
      return {
        pageSize: params.limit,   //页面大小
        pageIndex: params.offset/params.limit+1,  //页码
        valValue:$.trim($('#valValueInput').val()),
        valText:$.trim($('#valTextInput').val()),
        codeId:codeIdByRight
      };
    },
    pagination : true,// 启动分页
    pageSize : 13,// 每页显示的记录条数
    pageNumber : 1,// 当前显示第几页
    pageList : [5],
    paginationPreText: '上一页',
    paginationNextText: '下一页',
    search : false,// 是否启用查询,
    showRefresh : false, // 是否显示刷新按钮
    showColumns : false, // 是否显示所有的列
    minimumCountColumns : 2, // 最少允许的列数
    onClickRow:function(row,$element){
      $('.info').removeClass('info');
      $($element).addClass('info');
    },
    clickToSelect : true,
    uniqueId :"id",
    columns : [
      {checkbox : true},
      {field: 'id',title:"主键id",valign:"middle",align:"center",colspan:1,rowspan: 1,visible : false},
      {field: 'valText',title:"代码集名",valign:"middle",align:"center",colspan:1,rowspan: 1},
      {field: 'valValue',title: "代码集值",valign:"middle",align:"center",colspan: 1,rowspan: 1},
      {field: 'sortIndex',title: "代码值顺序",valign:"middle",align:"center",colspan: 1,rowspan: 1},
      {field: 'valTextTitle',title: "英文简写",valign:"middle",align:"center",colspan: 1,rowspan: 1},
      {field: 'memo',title: "备注",valign:"middle",align:"center",colspan: 1,rowspan: 1},
      {title: "操作",valign:"middle",align:"center",colspan: 1,rowspan: 1,events:operateEvents
        ,formatter:function () {
          return [
            '<span style="color:var(--main-color);cursor: pointer;" class="edit myiconfont" title="编辑">&#xe63c;</span>  |  '+
            '<span style="color:var(--main-color);cursor: pointer;" class="delete myiconfont" title="删除">&#xe505;</span> ',
          ].join("")
        }
      }
    ]
  });
};

/**
 *查询按钮
 */
function selectBtnCodeClick(){
  $("#codeTable").bootstrapTable('refresh');
}

/**
 *查询按钮
 */
function selectBtnValClick(){
  $("#codeValTable").bootstrapTable('refresh');
}

/**
 *清空按钮
 */
function cleanBtnCodeClick(){
  codeIdByLeft=null;
  $('#codeNameInput').val(""),
    $('#codeTextInput').val(""),
    $("#codeTable").bootstrapTable('refresh');
}


/**
 *清空按钮
 */
function cleanBtnValClick(){
  codeIdByRight=null;
  $("#codeId").val("");
  $('#valValueInput').val("");
  $('#valTextInput').val("");
  $("#codeValTable").bootstrapTable('refresh');
}

function returnCodediv() {
  $(".codediv").css("display","block");
  $(".codeValdiv").css("display","none");
  $("#sourceProtocolMode").val("");
  $("#sourceProtocolMode").removeAttr("readonly");
  $("#codeTextInput2").val("");
  $("#codeTextInput2").removeAttr("readonly");
  $("#parcodeIdInput").val("");
  $("#parcodeIdInput").removeAttr("readonly");
  $("#brotherCodeIdInput").val("");
  $("#brotherCodeIdInput").removeAttr("readonly");
  codeIdByRight = null;
  $("#codeId").val("");
  $("#codeId").removeAttr("readonly");
}

// 添加新的元素代码集值
function addCodeVal() {
  $('#addCodeValModal').modal('show');
}

// 添加新的元素代码集值信息
function addCodeValButton() {
  //  代码集名
  var valTextModal = $("#valTextModal").val().toUpperCase();
  //  代码集值
  var valValueModal = $("#valValueModal").val().toUpperCase();
  // 代码值顺序
  var sortIndexModal = $("#sortIndexModal").val().toUpperCase();
  // 英文缩写
  var valTextTitleModal = $("#valTextTitleModal").val().toUpperCase();
  // 备注信息
  var memoModal = $("#memoModal").val().toUpperCase();
  // 判断代码值顺序是否为数字
  if(!checkIsNull(sortIndexModal)){
    if(!checkIsNumber(sortIndexModal)){
      toastr.error("代码值顺序必须是数字");
      return;
    }
  }
  //  CODEVALID
  var codeValIdModalBack = $("#codeValIdModalBack").val().toUpperCase();
  var codeId = $("#codeId").val().toUpperCase();
  var addObject = new Object();
  addObject.valText = valTextModal.trim();
  addObject.valValue = valValueModal.trim();
  addObject.sortIndex = sortIndexModal.trim();
  addObject.valTextTitle = valTextTitleModal.trim();
  addObject.memo = memoModal.trim();
  addObject.codeValId = codeValIdModalBack.trim();
  addObject.codeId = codeId;
  $.ajax({
    type:"post",
    url:path+"/addCodeValTable",
    data:JSON.stringify(addObject),
    calculable : false,
    async:false,
    contentType:'application/json',
    dataType:'json',
    success:function(result){
      if(result.status === 1){
        toastr.info(result.data);
        // 刷新刚刚添加的元素代码集值信息
        $('#addCodeValModal').modal('hide');
        closeModalCodel();
        $("#codeValTable").bootstrapTable('refresh');
      }else{
        toastr.error(result.msg);
      }
    },
    error:function (result) {
      toastr.error("添加新的元素代码集值信息报错!!!!!!");
      return;
    }
  });
}

// 关闭添加模态框
function closeModalCodel() {
  $("#valTextModal").val("");
  $("#valValueModal").val("");
  $("#sortIndexModal").val("");
  $("#valTextTitleModal").val("");
  $("#memoModal").val("");
  $("#codeValIdModalBack").val("");
}

// 添加新的元素代码集
function addBtnCodeClick() {
  $(".pageAddressThree").css("display","inline");
  $(".codediv").css("display","none");
  $(".codeValdiv").css("display","inline");
  codeIdByRight="null";
  $("#codeValTable").bootstrapTable('refresh');
  // 回填输入框
  $("#sourceProtocolMode").val("");
  $("#sourceProtocolMode").removeAttr("readOnly");
  $("#codeTextInput2").val("");
  $("#codeTextInput2").removeAttr("readOnly");
  $("#parcodeIdInput").val("");
  $("#parcodeIdInput").removeAttr("readOnly");
  $("#brotherCodeIdInput").val("");
  $("#brotherCodeIdInput").removeAttr("readOnly");
  $("#codeId").val("");
  $("#codeId").removeAttr("readOnly");
}

/**
 *  保存代码集基本信息
 */
var isClick = false;
function saveCodediv() {
  if(isClick){
    return ;
  }
  isClick = true;
  try{
    var codeId = $("#codeId").val().trim().toUpperCase();
    var codeName = $("#sourceProtocolMode").val().trim().toUpperCase();
    var codeText = $("#codeTextInput2").val().trim().toUpperCase();
    var parCodeId = $("#parcodeIdInput").val().trim().toUpperCase();
    var brotherCodeId = $("#brotherCodeIdInput").val().trim().toUpperCase();
    var object = new Object();
    object.codeId = codeId;
    object.codeName = codeName;
    object.codeText = codeText;
    object.parcodeId = parCodeId;
    object.brotherCodeId = brotherCodeId;
    $.ajax({
      type:"post",
      url:path+"/addOneCodeMessage",
      data:JSON.stringify(object),
      calculable : false,
      async:false,
      contentType:'application/json',
      dataType:'json',
      success:function(result){
        if(result.status === 1){
          toastr.info(result.data);
          $("#codeId").attr("readOnly","true");
          codeIdByRight=codeId;
          $("#codeValTable").bootstrapTable('refresh');
          $("#codeTable").bootstrapTable('refresh');
        }else{
          toastr.error(result.msg);
        }
      },
      error:function (result) {
        toastr.error("添加新的元素代码集值信息报错!!!!!!");
        return;
      }
    });
  }catch(errMsg){
    toastr.error(errMsg)
  }
  setTimeout(function () {
    isClick = false;
  },1500)

}

// 提示框中提示两列数据
function twoSuggestNew(domID,url) {
  $("#" + domID).bsSuggest('init',{
    url:url,
    // indexId : 0, // data.value 的第几个数据，作为input输入框的显示的内容
    // indexKey: 0,
    idField:"id",
    keyField: "value",
    getDataMethod:"url", //数据从ajax中来
    effectiveFields: ["value","name"],
    searchFields: [ "value"],
    effectiveFieldsAlias:{name: "中文名"},
    ignorecase: true,
    clearable: true, //可清除已输入内容
    showHeader: true,
    showBtn: false,     //不显示下拉按钮
    delayUntilKeyup:true,  //为url时不起作用
    // jsonp: 'cb',                      //如果从url获取数据，并且需要跨域，则该参数必须设置
    listStyle: {
      'padding-top': 0, 'max-height': '300px', 'max-width': '280px',"min-width":"280px",
      'overflow': 'auto', 'width': '280px',
      'transition': '0.3s', '-webkit-transition': '0.3s', '-moz-transition': '0.3s', '-o-transition': '0.3s',

    },                              //列表的样式控制
    autoMinWidth: false,
    listAlign: 'left'             //提示列表对齐位置，left/right/auto
  }).on('onDataRequestSuccess', function(e, result){
    console.log('onDataRequestSuccess', result);
  }).on('onSetSelectValue', function(e, keyword, data){
    console.log('onSetSelectValue: ', keyword, data);
  }).on('onUnsetSelectValue', function(){
    console.log("onUnsetSelectValue");
  });
}

/**
 * 删除选择的多行代码数据
 */
function delBtnCodeClick() {
  var allDelCode = $("#codeTable").bootstrapTable("getAllSelections");
  if(allDelCode.length === 0 ){
    toastr.error("请选择需要删除的数据");
    return;
  }
  bootbox.confirm({
    size: "middle",
    message: "是否删除所选信息",
    buttons: {
      confirm: {
        label: '确定',
        className: 'btn-default'
      },
      cancel: {
        label: '取消',
        className: 'btn-default'
      }
    },
    callback: function (result) {
      if(result){
        $.ajax({
          type:"post",
          url:path+"/delAllSelectCode",
          data:JSON.stringify(allDelCode),
          calculable : false,
          async:false,
          contentType:'application/json',
          dataType:'json',
          success:function(result){
            if(result.status === 1){
              toastr.info(result.data);
              // 刷新刚删除的数据
              $("#codeTable").bootstrapTable('refresh');
            }else{
              toastr.error(result.msg);
            }
          },
          error:function (result) {
            toastr.error("删除元素代码集值信息报错!!!!!!");
            return;
          }
        });
      }
    }
  });
}

function importCodeValClick(){
  $("#importCodeVal").modal('show');
}

function InitExcelFile() {
  $("#codeValXlsFile").fileinput({
    uploadUrl: path+"/uploadCodeValXlsFile",//上传的地址
    uploadAsync: true,              //异步上传
    language: "zh",                 //设置语言
    showCaption: true,              //是否显示标题
    showUpload: true,               //是否显示上传按钮
    showRemove: true,               //是否显示移除按钮
    // showPreview: true,             //是否显示预览按钮
    browseClass: "btn btn-primary", //按钮样式
    uploadLabel: "导入",           //设置上传按钮的汉字
    enctype: 'multipart/form-data',
    dropZoneEnabled: false,         //是否显示拖拽区域
    allowedFileExtensions: ["xls","xlsx"], //接收的文件后缀
    maxFileCount: 1,                        //最大上传文件数限制
    overwritelnitial:false,
    initialPreviewAsData:true,
    maxFilesNum:1,
    maxFileSize:0,
    previewFileIcon: '<i class="glyphicon glyphicon-open-file"></i>',
    allowedPreviewTypes: null,
    minImageWidth: 20, //图片的最小宽度
    minImageHeight: 20,//图片的最小高度
    maxImageWidth: 50,//图片的最大宽度
    maxImageHeight: 50,//图片的最大高度
    msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",
    previewFileIconSettings: {
      'xls': '<i class="glyphicon glyphicon-open-file"></i>',
      'xlsx': '<i class="glyphicon glyphicon-open-file"></i>'
    }
  }).on("fileuploaded", function (event, data) {
    if(data.response.status === 1){
      toastr.info(data.response.data);
      $("#codeValTable").bootstrapTable('refresh');
    }else{
      toastr.error(data.response.msg);
    }
  }).on('fileerror', function (event, data, msg) {  //一个文件上传失败
    // alert('文件上传失败！' + msg);
    toastr.error('文件上传失败！' + msg)
  }).on("filebatchselected", function(event, files) {
    // 选择文件成功后，将结果显示页面去除

  });
}

/**
 * 页面点击之后的跳转信息
 */
function pageClick() {
  $(".codediv").css("display","block");
  $(".codeValdiv").css("display","none");
  $("#sourceProtocolMode").val("");
  $("#sourceProtocolMode").removeAttr("readonly");
  $("#codeTextInput2").val("");
  $("#codeTextInput2").removeAttr("readonly");
  $("#parcodeIdInput").val("");
  $("#parcodeIdInput").removeAttr("readonly");
  $("#brotherCodeIdInput").val("");
  $("#brotherCodeIdInput").removeAttr("readonly");
  codeIdByRight = null;
  $("#codeId").val("");
  $("#codeId").removeAttr("readonly");
  $(".pageAddressThree").css("display","none");
}

/**
 * 下载元素代码集值管理的模板文件
 */
function downTemplateFile() {
  location.href =path+"/downElementCodeTemplateFile";
}

/**
 * 批量删除表格中的数据
 */
function deleteSelectRows() {
  var rowsList = $('#codeValTable').bootstrapTable('getSelections');
  if(rowsList.length <= 0){
    toastr.error("请先选择需要删除的数据!");
    return;
  }else{
    bootbox.confirm({
      size : "middle",
      message : "是否要删除选择的代码集基本信息",
      buttons : {
        confirm : {
          label : '确定',
          className : 'btn-default'
        },
        cancel : {
          label : '取消',
          className : 'btn-default'
        }
      },
      callback : function(result) {
        if (result) {
          $.ajax({
            type:"post",
            url:path+"/delBatchCodeValTable",
            data:JSON.stringify(rowsList),
            calculable : false,
            async:false,
            contentType:'application/json',
            dataType:'json',
            success:function(result){
              if(result.status === 1){
                toastr.info(result.data);
                // 刷新刚删除的数据
                $("#codeValTable").bootstrapTable('refresh');
              }else{
                toastr.error(result.message);
              }
            },
            error:function (result) {
              toastr.error("删除失败!!!!!!");
              return;
            }
          });
        }
      }
    });
  }
}
