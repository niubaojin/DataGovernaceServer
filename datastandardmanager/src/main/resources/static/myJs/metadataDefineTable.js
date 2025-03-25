var path=window.document.location.pathname.split("/")[1];
	path="/"+path; 
var codeIdModal;
var tableHeight=500;
var dataGovernaceWeb="";
var dataQuiltyUrl="";
var tableObj  = '';
window.operateEvents={
  'click .mod':function(e,value,row,index){
      codeIdModal=row.codeid;
      $('#valTextInput').val('');
      $('#valValueInput').val('');
      codeValTableModal();
      $("#fieldCodeValModalTable").bootstrapTable('refresh');
	  $("#fieldCodeValModal .modal-title").text("【"+row.fieldchinesename+"】码表值信息");
	  // $("#fieldCodeValModal").on("shown.bs.modal",function(){
		//
	  // });
      $("#fieldCodeValModal").off();
      $("#fieldCodeValModal").modal('show');
  },

  'click .tableView': function(ev,value,row,index){
      $("#forView").val(row.fieldid);
      innitTble();
      $("#tableNames").bootstrapTable('refresh');
      $("#viewTable").modal('show');
      $("#viewTable .modal-title").text("【"+row.fieldchinesename+"】关联表信息")

  },
  'click .dataQuilaty':function (ev,value,row,index) {
  	console.log(dataQuiltyUrl+"?fieldId="+row.fieldid+"&jumpType=metadata");
  	if(checkIsNull(dataGovernaceWeb)||checkIsNull(dataQuiltyUrl)){
  		toastr.error("大数据治理平台和数据质量检测规则对应的平台url为空");
  		return;
		}
		if(checkIsNull(row.fieldid)){
      toastr.error("元素编码为空，不能进行跳转");
      return;
		}
    window.open(dataGovernaceWeb+'?src=' +
      encodeURIComponent(dataQuiltyUrl+"?fieldId="+row.fieldid+"&jumpType=metadata") + '&title=检测规则管理','_blank');
  }
};	
$(document).ready(function() {
  // 获取 大数据治理平台和检测规则管理平台的页面对应的url
	getAllUrl();
  //初始化离线任务监控
    tableObj  =  metadataDefineTableTable(null,null,null);
  metadataDefineExportClick();
    windowResizeEvent();
});

/**
 * 计算获得表的高度
 * @returns {number}
 */
function caculateTableHeight() {
    var tableHeight = $("#contentPagin").height();
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
function mocaculateTableHeight() {
    var tableHeight = $("#contentPagin").height()-200;
    console.log( $("#contentPagin").height());
    console.log("设置bootstrap表的高度为【"+tableHeight+"】");
    return tableHeight;
}

//创建bootstrap表格(元素代码集值)
function codeValTableModal(){
	//构造表格
$("#fieldCodeValModalTable").bootstrapTable("destroy").bootstrapTable({
	method: "post",                   
    contentType : "application/x-www-form-urlencoded",
    dataType:"json",
	url : "codeValTableOld",
	sidePagination : "server", // 分页方式：client客户端分页，server服务端分页
	queryParams: function (params){
		return {
			pageSize: params.limit,   //页面大小
			pageIndex: params.offset/params.limit+1,  //页码
			valValue:$.trim($('#valValueInput').val()),
			valText:$.trim($('#valTextInput').val()),
			codeId:codeIdModal
		};
	},		
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
	minimumCountColumns : 1, // 最少允许的列数
	clickToSelect : true,
	uniqueId :"id",
    height: mocaculateTableHeight(),
	columns : [
	    {field: 'id',title:"主键id",valign:"middle",align:"center",colspan:1,rowspan: 1,visible : false},
  	 	{field: 'valText',title:"代码集名",valign:"middle",align:"center",colspan:1,rowspan: 1},
		{field: 'valValue',title: "代码集值",valign:"middle",align:"center",colspan: 1,rowspan: 1},       
		{field: 'sortIndex',title: "代码值顺序",valign:"middle",align:"center",colspan: 1,rowspan: 1},  
        {field: 'memo',title: "备注",valign:"middle",align:"center",colspan: 1,rowspan: 1},
        {field: 'valTextTitle',title: "英文简写",valign:"middle",align:"center",colspan: 1,rowspan: 1},
        {field: 'codeId',title: "外键",valign:"middle",align:"center",colspan: 1,rowspan: 1}
        ],
        onLoadSuccess: function (data) {
            $("#fieldCodeValModal .page-list ul li").append('<span>条/页</span>');
            $("#fieldCodeValModal .page-list button span.page-size").append('<span>条/页</span>');
            $('#fieldCodeValModal .fixed-table-pagination').prepend('<div class="total-num">共 '+ data.total+' 条</div>');

        }
	});    
};
function innitTble(){
    $("#tableNames").bootstrapTable("destroy").bootstrapTable({
        method: "post",
        contentType : "application/x-www-form-urlencoded",
        dataType:"json",
        url : "getAllTableName",
        sidePagination : "server", // 分页方式：client客户端分页，server服务端分页
        queryParams: function (params){
            return {
                pageSize: params.limit,   //页面大小
                pageIndex: params.offset/params.limit+1,  //页码
                fieldId:$("#forView").val(),
            };
        },
        pagination : true,// 启动分页
        pageSize : 10,// 每页显示的记录条数
        pageNumber : 1,// 当前显示第几页
        pageList : [10,20,30,50],
        paginationPreText: '<',
        paginationNextText: '>',
        paginationShowPageGo: true,
        showJumpto: true,
        smartDisplay: false,
        paginationLoop: false,
        search : false,// 是否启用查询,
        showRefresh : false, // 是否显示刷新按钮
        showColumns : false, // 是否显示所有的列
        minimumCountColumns : 1, // 最少允许的列数
        clickToSelect : true,
        height: mocaculateTableHeight(),
        columns : [
            {field: 'tableId',title:"表Id",valign:"middle",align:"center",colspan:1,rowspan: 1},
            {field: 'tableName',title:"表名",valign:"middle",align:"center",colspan:1,rowspan: 1},
            {field: 'tableNameCh',title:"表中文名",valign:"middle",align:"center",colspan:1,rowspan: 1}
        ],
        onLoadSuccess: function (data) {
            $("#viewTable .page-list ul li").append('<span>条/页</span>');
            $("#viewTable .page-list button span.page-size").append('<span>条/页</span>');
            $('#viewTable .fixed-table-pagination').prepend('<div class="total-num">共 '+ data.total+' 条</div>');

        }
    });
}

function getTableLimit(limit) {
    if(!checkIsNull($.trim($('#fieldIdInput').val()))
      || !checkIsNull($.trim($('#columNameInput').val()))
       ||   !checkIsNull($.trim($('#columNameInput').val()))){
        return 1;
    }else{
        return  limit;
    }
}

//创建bootstrap表格(语义表)
function metadataDefineTableTable(fieldId,columName,fieldChineseName){
	//构造表格
  var obj = $("#metadataDefineTable").bootstrapTable("destroy").bootstrapTable({
	method: "post",                   
    contentType : "application/x-www-form-urlencoded",
    dataType:"json",
	url : "metadataDefineTable",
	queryParams: function (params){
		return {
			pageSize: params.limit,   //页面大小
			pageIndex: getTableLimit(params.offset/params.limit+1),  //页码
			fieldId:$.trim($('#fieldIdInput').val()),
			columName:$.trim($('#columNameInput').val()),
			fieldChineseName:$.trim($('#fieldChineseNameInput').val()),
            sort: params.sort,  // 排序列名
            sortOrder: params.order  // 排序命令(desc,asc)
		};
	},	
	sidePagination : "server", // 分页方式：client客户端分页，server服务端分页
	pagination : true,// 启动分页
	pageSize : 20,// 每页显示的记录条数
	pageNumber : 1,// 当前显示第几页
	paginationPreText: '<',
    paginationNextText: '>',
	pageList : [10,20,30,50],
    paginationShowPageGo: true,
    showJumpto: true,
    paginationLoop: false,
    smartDisplay: false,
	search : false,// 是否启用查询,
	showRefresh : false, // 是否显示刷新按钮
	showColumns : false, // 是否显示所有的列
	minimumCountColumns : 2, // 最少允许的列数
	clickToSelect : true,
    height:caculateTableHeight(),
	uniqueId :"fieldid",
    sortable: true,
	columns : [
  	 	{field:'fieldid',title:"元素编码",valign:"middle",align:"center",colspan:1,rowspan: 1},
		{field: 'columnname',title: "数据库列名",valign:"middle",align:"center",colspan: 1,rowspan: 1},
		{field: 'fieldname',title: "标准列名",valign:"middle",align:"center",colspan: 1,rowspan: 1},  
        {field: 'fieldchinesename',title: "中文名称",valign:"middle",align:"center",colspan: 1,rowspan: 1},
	    {field: 'fieldMessage',title: "字段信息",valign:"middle",align:"center",colspan: 1,rowspan: 1},
        {field: 'codeid',title: "codeId",valign:"middle",align:"center",colspan: 1,rowspan: 1,visible : false},
        {field: 'fieldClassCh',title: "字段分类",valign:"middle",align:"center",colspan: 1,rowspan: 1,sortable:true},
        {field: 'codeText',title: "代码表ID",valign:"middle",align:"center",colspan: 1,rowspan: 1,
        	events:operateEvents,formatter:function(value,row,index){
        		if(value!=null){
        			return ['<a class="mod" style="color: var(--main-color);cursor: pointer;">'+value+'</a>'].join("");
        		}
        	}
        },
        {field: 'sameid',title: "sameId",valign:"middle",align:"center",colspan: 1,rowspan: 1,visible : false},
        {field: 'wordName',title: "语义ID",valign:"middle",align:"center",colspan: 1,rowspan: 1},
        {field: 'button',title: "关联表",valign:"middle",align:"center",colspan: 1,rowspan: 1,
            events:operateEvents,formatter:AddFunctionAlty
        },
				{title: "质量检测规则",valign:"middle",align:"center",colspan: 1,visible:false,rowspan: 1,
					events:operateEvents,formatter:AddFunctionDataQuilaty
				}
	],
        onLoadSuccess: function (data) {
            $("#contentPagin .page-list ul li").append('<span>条/页</span>');
            $("#contentPagin .page-list button span.page-size").append('<span>条/页</span>');
            $('#contentPagin .fixed-table-pagination').prepend('<div class="total-num">共 '+ data.total+' 条</div>');
        }
	});
return obj;
};

// '<button id="tableView" type="button" class="btn btn-primary btn-group">查看关联表</button>',
function AddFunctionAlty(value,row,index){
    return [
    	'<span style="color:var(--main-color);cursor: pointer;" class="tableView">查看关联表</span>',
	].join("")
}

function AddFunctionDataQuilaty(value,row,index) {
  return [
    '<span style="color:var(--main-color);cursor: pointer;" class="dataQuilaty">查看规则</span>',
  ].join("")
}
/**
 *查询按钮
 */
function selectBtnClick(){
	$("#metadataDefineTable").bootstrapTable('refresh');
}

/**
 *查询按钮
 */
function selectDataModal(){
	$("#fieldCodeValModalTable").bootstrapTable('refresh');
}
// 获取质量检测和大数据治理平台对应的url
function getAllUrl() {
  $.ajax({
    type:"get",
    url: "getAllClickUrl",
    contentType:'application/json',
    dataType:'json',
    calculable : false,
    async:false,
    success:function(result){
      if(result != null){
        dataGovernaceWeb=result.dataGovernanceWebUrl;
        dataQuiltyUrl=result.dataQuiltyWebUrl;
      }else{
        dataGovernaceWeb="";
        dataQuiltyUrl="";
      }
    },
    error:function (result) {
      toastr.error("获取相关url信息报错");
      return;
    }
  });
}

// 导出按钮
function metadataDefineExportClick() {
  $("#metadataDefineExport").click(function() {
    var fieldId = $.trim($('#fieldIdInput').val());
    var columName = $.trim($('#columNameInput').val());
    var fieldChineseName = $.trim($('#fieldChineseNameInput').val());
    location.href ="metadataDefineExport?fieldId="+fieldId+"&columnName="+columName+"&fieldChineseName="+fieldChineseName;
  });

  $(".exportFieldCodeVal").click(function() {
    var codeId = codeIdModal;
    var text = $("#fieldCodeValModal .modal-title").text().split("【")[1].split("】")[0];
    // 代码值
    var valValue = $("#valValueInput").val();
    //  代码值名
    var valText = $("#valTextInput").val();
    location.href ="metadataExportFieldCodeVal?codeId="+codeId+"&text="+text+"&valValue="+valValue+"&valText="+valText;
  });
  $(".exportTableNames").click(function () {
    var text = $("#viewTable .modal-title").text().split("【")[1].split("】")[0];
    var fieldId = $("#forView").val();
    location.href ="metadataExportTableNames?text="+text+"&fieldId="+fieldId;
  })
}
