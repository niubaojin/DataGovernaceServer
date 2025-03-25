var path=window.document.location.pathname.split("/")[1];
	path="/"+path;
var sameId= null;
var dataLen = 0;
var tableObj ='';
$(document).ready(function() {	
  //语义表
  tableObj = SemanticTableManageTable(null,null,null);
  buttonClickExport();
  initTable();
  InitExcelFile();
  // windowResizeEvent();
});

window.operateEvents={
  'click .delete':function(e,value,row,index){
    bootbox.confirm({
      size: "small",
      backdrop: true,
      title:"<span style='font-size: 16px;float:left;'>提示</span>",
      message: "<img src='img/myImg/warn.png' style='margin: -5px 10px 0 0;'><span>是否删除所选信息？</span>",
      buttons: {
        confirm: {
          label: '确定',
          className: 'my-btn-confirm'
        },
        cancel: {
          label: '取消',
          className: 'my-btn-cancle'
        }
      },
      callback: function (result) {
        if(result){
          $.ajax({
            type:"post",
            contentType:'application/json',
            dataType:'json',
            url: "delOneSemanticManage",
            data:JSON.stringify(row),
            calculable : false,
            async:false,
            success:function(result){
              if(result.status === 1){
                toastr.info(result.data);
                selectBtnClick();
              }else{
                toastr.error(result.msg)
              }
            }
          });
        }
      }
    })
  },
  'click .edit':function(e,value,row,index){
      console.log(row);
      $("#sameIdModal").val(row.id);
      $("#wordNameModal").val(row.wordname);
      $("#memoModal").val(row.memo);
      $("#wordModal").val(row.word);
      $("#addSemanticModal").modal('show');
      $("#addSemanticModalTitle").html("编辑语义");
      $("#sameIdModal").attr("readOnly","true");
  },
  'click .look':function(e,value,row,index){
      sameId = row.id;
      $("#tableNames").bootstrapTable('refresh');
      $("#relatedTable .modal-title").text("【"+row.wordname+"】关联元素集信息");
      $("#relatedTable").modal("show");
  },
  'click .dataQuilaty':function (ev,value,row,index) {
      console.log("质检规则的点击按钮");
      // 实时获取质检规则的相关url
      var sameId = row.id;
      $.ajax({
        type: "get",
        data: {
          "sameId": sameId
        },
        url: "getDataQuilatyUrl",
        calculable: false,
        async: false,
        success: function (result) {
            if(result.status === 1){
               window.open(result.data, '_blank')
            }else{
              toastr.error(result.msg);
            }
        },
        error: function (result) {
          toastr.error("获取查看质检规则的url报错，后台程序未启动");
        }
      });
  }
};

// 初始化 关联元素集表的相关信息
function initTable() {
  $("#tableNames").bootstrapTable({
    method: "post",
    contentType : "application/x-www-form-urlencoded",
    dataType:"json",
    url : "getMetadataDefineTableBySameid",
    sidePagination : "server", // 分页方式：client客户端分页，server服务端分页
    queryParams: function (params){
      return {
        pageSize: params.limit,   //页面大小
        pageIndex: params.offset/params.limit+1,  //页码
        sameId:sameId
      };
    },
    pagination : true,// 启动分页
    pageSize : 10,// 每页显示的记录条数
    pageNumber : 1,// 当前显示第几页
    pageList : [10,20,30,50],
    paginationShowPageGo: true,
    showJumpto: true,
    paginationLoop: false,
    paginationPreText: '<',
    paginationNextText: '>',
    smartDisplay: false,
    search : false,// 是否启用查询,
    showRefresh : false, // 是否显示刷新按钮
    showColumns : false, // 是否显示所有的列
    minimumCountColumns : 1, // 最少允许的列数
    clickToSelect : false,
    height: mocaculateTableHeight(),
    columns : [
      {field: 'fieldid',title:"元素编码",valign:"middle",align:"center",colspan:1,rowspan: 1},
      {field: 'fieldname',title:"标准名",valign:"middle",align:"center",colspan:1,rowspan: 1},
      {field: 'fieldchinesename',title:"中文名",valign:"middle",align:"center",colspan:1,rowspan: 1}
    ],
      onLoadSuccess: function (data) {
          $("#relatedTable .page-list ul li").append('<span>条/页</span>');
          $("#relatedTable .page-list button span.page-size").append('<span>条/页</span>');
          $('#relatedTable .fixed-table-pagination').prepend('<div class="total-num">共 '+ data.total+' 条</div>');
      }
  });
}

/**
 * 计算获得表的高度
 * @returns {number}
 */
function caculateTableHeight() {
  var tableHeight = $("#contentPagin").height();
  return tableHeight;
}

function mocaculateTableHeight() {
  var tableHeight = $("#contentPagin").height()-180;
  console.log( $("#contentPagin").height());
  console.log("设置bootstrap表的高度为【"+tableHeight+"】");
  return tableHeight;
}
/**
 * 窗口大小改变事件
 */
function windowResizeEvent() {
  $(window).resize(function () {
    var tableHeight = $("#contentPagin").height();
    tableObj.bootstrapTable('resetView',{
      height:tableHeight
    });
  });
}


//创建bootstrap表格(语义表)
function SemanticTableManageTable(sameId,wordName,word){
	//构造表格
var obj = $("#semanticTableManageTable").bootstrapTable({
	url : "semanticTableManage",
	queryParams:{
			sameId:sameId,
			wordName:wordName,
			word:word,
	},
	sidePagination : "client", // 分页方式：client客户端分页，server服务端分页
	pagination : true,// 启动分页
	paginationPreText: '<',
	paginationNextText: '>',
	pageSize : 20,  //每页显示的记录条数
	pageNumber : 1, //当前显示第几页
	pageList : [10,20,30,50],
    paginationShowPageGo: true,
    showJumpto: true,
    paginationLoop: false,
    smartDisplay: false,
    height: caculateTableHeight(),
	search : false,// 是否启用查询,
	showRefresh : false, // 是否显示刷新按钮
	showColumns : false, // 是否显示所有的列
	minimumCountColumns : 2, // 最少允许的列数
	clickToSelect : false,
	uniqueId :"id",
	columns : [
    {checkbox : true},
  	{field: 'id',title:"主键ID",valign:"middle",align:"center",colspan:1,rowspan: 1},
    {field: 'wordname',title: "中文语义",valign:"middle",align:"center",colspan: 1,rowspan: 1},
    {field: 'word',title: "英文语义",valign:"middle",align:"center",colspan: 1,rowspan: 1},
    {field: 'memo',title: "备注",valign:"middle",align:"center",colspan: 1,rowspan: 1},
    {title: "关联元素集",valign:"middle",align:"center",colspan: 1,rowspan: 1,events:operateEvents,
      formatter:function () {
        return [
          /*'<span style="color:#007DDB;cursor: pointer;" class="look">查看</span>'*/
          '<span style="color:var(--main-color);cursor: pointer;" class="look myiconfont" title="查看">&#xe62c;</span>'
        ].join("")
      }
    },
    // ,
    // {title: "质检规则",valign:"middle",align:"center",colspan: 1,rowspan: 1,events:operateEvents
    //   ,formatter:function () {
    //     return [
    //       '<span style="color:var(--main-color);cursor: pointer;" class="dataQuilaty">查看规则</span> ',
    //       /*'<span style="color:#007DDB;cursor: pointer;" class="dataQuilaty myiconfont" title="查看规则">&#xe62c;</span> ',*/
    //     ].join("")
    //   }
    // },
    {title: "操作",valign:"middle",align:"center",colspan: 1,rowspan: 1,events:operateEvents
      ,formatter:function () {
        return [
          /*'<span style="color:#007DDB;cursor: pointer;" class="edit">编辑</span>  |  '+
          '<span style="color:#007DDB;cursor: pointer;" class="delete">删除</span> ',*/
          '<span style="color:var(--main-color);cursor: pointer;" class="edit myiconfont" title="编辑">&#xe63c;</span>   '+
          '<span style="color:var(--main-color);cursor: pointer;font-size: 17px" class="delete myiconfont" title="删除">&#xe505;</span> ',
        ].join("")
      }
    }
	],
      onLoadSuccess: function (data) {
          dataLen = data.length;
        $("#contentPagin .page-list ul li").append('<span>条/页</span>');
        $("#contentPagin button span.page-size").append('<span>条/页</span>');
        $('#contentPagin .fixed-table-pagination').prepend('<div class="total-num">共 '+ dataLen+' 条</div>');

      },
      onPageChange: function () {
          $("#contentPagin .page-list ul li").append('<span>条/页</span>');
          $("#contentPagin .page-list button span.page-size").append('<span>条/页</span>');
          $('#contentPagin .fixed-table-pagination').prepend('<div class="total-num">共 '+ dataLen+' 条</div>');
      }
	});
return obj;
};

/**
 *查询按钮
 */
function selectBtnClick(){
	var sameId=$.trim($('#sameIdInput').val());
	var wordName=$.trim($('#wordNameInput').val());
	var word=$.trim($('#wordInput').val());
	//传值到后台
	$.ajax({
		type:"post",
		url: "semanticTableManage",
		data:{
			"sameId":sameId,
			"wordName":wordName,
			"word":word
		},
		calculable : false,
		async:false,
		success:function(result){
          dataLen = result.length;
			$("#semanticTableManageTable").bootstrapTable('load',result);
          $("#contentPagin .page-list ul li").append('<span>条/页</span>');
          $("#contentPagin button span.page-size").append('<span>条/页</span>');
          $('#contentPagin .fixed-table-pagination').prepend('<div class="total-num">共 '+dataLen+' 条</div>');
		}	
	});
}

// 导出按钮
function buttonClickExport() {
  $("#export").click(function () {
    var sameIdInput = $("#sameIdInput").val();
    var wordNameInput = $("#wordNameInput").val();
    var wordInput = $("#wordInput").val();
    location.href = "semanticTableExport?sameId="+sameIdInput+"&wordName="+wordNameInput+"&word="+wordInput;
  });
  $("#addSemantic").click(function () {
    $("#sameIdModal").removeAttr("readOnly");
    $("#sameIdModal").val("");
    $("#wordNameModal").val("");
    $("#memoModal").val("");
    $("#wordModal").val("");
    $("#addSemanticModalTitle").html("新建语义");
    $("#addSemanticModal").modal('show');
  });
  // 保存新的语义表信息
  $(".saveNewSemantic").click(function () {
    var sameIdModal = $("#sameIdModal").val();
    var wordNameModal = $("#wordNameModal").val();
    var wordModal = $("#wordModal").val();
    var memoModal = $("#memoModal").val();
    var obj = new Object();
    obj.id = sameIdModal;
    obj.wordname = wordNameModal;
    obj.word = wordModal;
    obj.memo = memoModal;
    $.ajax({
      type:"post",
      contentType:'application/json',
      dataType:'json',
      url: "addOneSemanticManage",
      data:JSON.stringify(obj),
      calculable : false,
      async:false,
      success:function(result){
        if(result.status === 1){
            toastr.info(result.data);
            selectBtnClick();
            $("#addSemanticModal").modal('hide');
        }else{
            toastr.error(result.msg)
        }
      }
    });
  });
  // 删除选择的数据
  $("#delSemantic").click(function () {
      var selectAllRow = $("#semanticTableManageTable").bootstrapTable("getAllSelections");
      if(selectAllRow.length <1){
        toastr.error("请选择具体的数据");
        return ;
      };
      bootbox.confirm({
        size: "small",
        backdrop: true,
        title:"<span style='font-size: 16px;float:left;'>提示</span>",
        message: "<img src='img/myImg/warn.png' style='margin: -5px 10px 0 0;'><span>是否删除所选信息？</span>",
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
              contentType:'application/json',
              dataType:'json',
              url: "delAllSemanticManage",
              data:JSON.stringify(selectAllRow),
              calculable : false,
              async:false,
              success:function(result){
                if(result.status === 1){
                  toastr.info(result.data);
                  selectBtnClick();
                }else{
                  toastr.error(result.msg)
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
  });
  $("#import").click(function () {
      $("#importSemanticTable").modal("show");
  })
}


function InitExcelFile() {
  $("#semanticFile").fileinput({
    uploadUrl: "uploadSemanticFile",//上传的地址
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
      selectBtnClick();
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
 * 下载元素代码集值管理的模板文件
 */
function downTemplateFile() {
  location.href ="downSemanticTableTemplateFile";
}