var allSummaryObjectTable=[]
// 这个是一级分类的中文
var primaryClassifyCh ="";
// 这个是二级分类的中文
var secondaryClassifyCh ="";
var tableId="";

// $(document).ready(function () {
//   createTree();
//   // 时间的初始化器
//   initDataPickerSelect();
//   // 初始化 汇总表
//   initSummaryObjectTable();
//   // 查询具体的信息
//   querySummaryObjectTable();
// })


function querySummaryObjectTable(){
  //  var obj = getQueryParams(10,1);
  //  if(obj === null) {
  //    return;
  //  }
  // var opt = {
  //   url:"searchSummaryTable",
  //   query:obj
  // };
  // $('#summaryObjectTable').bootstrapTable("refresh",opt);
  $('#summaryObjectTable').bootstrapTable("refreshOptions",{pageNumber:1,pageSize:20});


}

/**
 * 查询参数的获取
 */
function getQueryParams(pageSize , pageIndex,sortOrder,sort) {
  var obj = new Object();
  try{
    obj.dataType = dataTypeCode;
    obj.primaryClassifyCh = primaryClassifyCh;
    obj.secondaryClassifyCh = secondaryClassifyCh;
    obj.tableId = tableId;
    obj.searchCode = $("#searchInputSummary").val().trim();
    obj.timeTypeSummary = $("#timeTypeSummary").val();
    obj.startTimeText = $("#startTimeText").val();
    obj.endTimeText = $("#endTimeText").val();
    obj.pageSize = pageSize;
    obj.pageIndex = pageIndex;
    obj.sortOrder = sortOrder;
    obj.sort = sort;
  }catch (err) {
    obj =null;
    toastr.error("生成查询参数报错"+err);
  }
  return obj;
}

/**
 * 汇总表的初始化
 */
function initSummaryObjectTable() {
//构造表格
  $("#summaryObjectTable").bootstrapTable({
    sidePagination: "server", // 分页方式：client客户端分页，server服务端分页
    contentType: "application/x-www-form-urlencoded",
    dataType: "json",
    url:"searchSummaryTable",
    queryParams: function (params) {
      return getQueryParams(params.limit,params.offset / params.limit + 1,params.order,params.sort);
    },
    pagination: true,// 启动分页
    pageSize: 20,// 每页显示的记录条数
    pageNumber: 1,// 当前显示第几页
    pageList: [20, 30, 50],
    paginationPreText: '<',
    paginationNextText: '>',
    paginationShowPageGo: true,
    showJumpto: true,
    smartDisplay: false,
    paginationLoop: false,
    search: false,// 是否启用查询,
    showRefresh: false, // 是否显示刷新按钮
    showColumns: false, // 是否显示所有的列
    minimumCountColumns: 2, // 最少允许的列数
    resizable:true,
    height: caculateTableHeight1(),
    clickToSelect: true,
    columns: [
      {checkbox: true},
      {field: 'recno', title: "序号", valign: "middle", align: "center", colspan: 1, rowspan: 1,titleTooltip:"序号"},
      {field: 'dataSourceCh', title: "应用系统", titleTooltip:"应用系统", valign: "middle", align: "center", colspan: 1,
        rowspan: 1,formatter:paramsMatter},
      {field: 'objectName', title: "中文名称", titleTooltip:"中文名称", valign: "middle", align: "center", colspan: 1,
        rowspan: 1,formatter:paramsMatter,width:"130"},
      {field: 'tableName', title: "真实表名", titleTooltip:"真实表名", valign: "middle", align: "center", colspan: 1, rowspan: 1
        ,sortable:"true",formatter:paramsMatter,width:"130"},
      {field: 'tableId', title: "资源标识", titleTooltip:"资源标识", valign: "middle", align: "center", colspan: 1, rowspan: 1
        ,sortable:"true",formatter:paramsMatter,width:"130"},
      {field: 'objectStateStr', title: "数据状态", titleTooltip:"数据状态", valign: "middle", align: "center", colspan: 1, rowspan: 1
        ,sortable:"true",formatter:paramsMatter},
      {field: 'createdTables', title: "已建物理表", titleTooltip:"已建物理表", valign: "middle", align: "center", colspan: 1, rowspan: 1},
      {field: 'creator', title: "创建人", titleTooltip:"创建人", valign: "middle", align: "center", colspan: 1, rowspan: 1
        ,sortable:"true",formatter:paramsMatter},
      {field: 'createTime', title: "创建时间", titleTooltip:"创建时间", valign: "middle", align: "center", colspan: 1,
        rowspan: 1,sortable:"true",formatter:paramsMatter},
      {field: 'updater', title: "最后修改人", titleTooltip:"最后修改人", valign: "middle", align: "center", colspan: 1
        , rowspan: 1,sortable:"true",formatter:paramsMatter},
      {field: 'updateTime', title: "最后更新时间", titleTooltip:"最后更新时间", valign: "middle", align: "center",
        colspan: 1, rowspan: 1,sortable:"true",formatter:paramsMatter},
      {title: "操作", titleTooltip:"操作", valign: "middle", align: "center", colspan: 1,rowspan: 1,
        width:"250",
        formatter:summaryOperate, events: summaryOperateClick}
      ],
      onLoadSuccess:function (data) {
        try{
          addTotalAndtitle('#contentPagin', data.total);
        }catch (e) {
          console.log(e);
          addTotalAndtitle('#contentPagin', 0);
        }
      },
      onLoadError:function (status) {
        addTotalAndtitle('#contentPagin', 0);
      }
  });
  addTotalAndtitle('#contentPagin', 0);
}


function initDataPickerSelect(){
  $("#startTimeText").datetimepicker({
    language: 'zh-CN',
    format: 'yyyy-mm-dd',
    initialDate: new Date(),
    autoclose: true,
    todayBtn: true,
    pickerPosition:'bottom-right',
    todayHighlight:true,
    startView:'month',
    minView:2
  });
  $("#endTimeText").datetimepicker({
    language: 'zh-CN',
    format: 'yyyy-mm-dd',
    initialDate: new Date(),
    autoclose: true,
    todayBtn: true,
    pickerPosition:'bottom-right',
    todayHighlight:true,
    startView:'month',
    minView:2
  });
}



// 创建左侧的tree
function createTree() {
// 树状图初始化
  var setting = ztreeSetting();
  treeObj = $.fn.zTree.init($("#dszTree"), setting);
}

//zTree对象
var treeObj;

/**
 * 20201127 节点点击之后不再查询具体的 查询汇总信息
 * @returns
 */
function ztreeSetting() {
  var setting = {
    async: {
      enable: true,//是否异步加载
      url:  "resourceManageZTreeNodes",
      // url:  "resourceManageZTreeNodes",
      type: "get",
      otherParam: {
        "name": function () {
          return $('#dataCenterName').val();
        },
        "dataType": function () {
          return dataTypeCode;
        },
        "moduleName": function () {
          return getQueryParam("moduleName");
        }
      }
    },
    view: {
      expandSpeed: "",
      selectedMulti: false
    },
    edit: {
      enable: false,
      showRemoveBtn: false,
      showRenameBtn: false
    },
    data: {
      simpleData: {
        enable: true,
        idKey: "id",
        pidKey: "pId",
        rootId: "root"
      }
    },
    callback: {
      // beforeRename: zTreeOnRename,
      onClick: zTreeOnClickSummary,
      // beforeRemove: zTreeBeforeRemove,
      onAsyncSuccess: function (event, treeId, treeNode, msg) {
      }
    }
  };
  return setting;
}


/**
 * 节点点击事件 查询汇总信息 20201127
 * @param event
 * @param treeId
 * @param treeNode
 */
function zTreeOnClickSummary(event, treeId, treeNode) {
  //如果是父节点就跳过
  if (treeNode.tableNodeFlag === false) {
    // 这个是分类信息的节点
    treeObj.expandNode(treeNode);
    // 获取到 一级分类和二级分类的相关信息 回填具体的值 然后再查询右侧的表格
    if(checkIsNull(treeNode.pId)){
       // 说明这个是一级分类的标签
       primaryClassifyCh =treeNode.id;
       secondaryClassifyCh ="";
       tableId = "";
    }else{
       primaryClassifyCh =treeNode.pId;
       var ids = treeNode.id.split("|");
       secondaryClassifyCh = ids[ids.length -1];
       tableId ="";
    }
  }else {
    // 此时pid是   原始库|互联网数据
    tableId = treeNode.id;
    var pids = treeNode.pId.split("|");
    if(pids.length === 2){
      primaryClassifyCh = pids[0];
      secondaryClassifyCh = pids[1];
    }else{
      primaryClassifyCh = pids[0];
      secondaryClassifyCh = "";
    }
  }
  querySummaryObjectTable();
}


function switchTabQuerySummary() {
  // 这个是一级分类的中文
  primaryClassifyCh ="";
  // 这个是二级分类的中文
  secondaryClassifyCh ="";
  tableId="";
  querySummaryObjectTable();
}


function caculateTableHeight1() {
  var tableHeight = $("#TableDiv").height() -30;
  return tableHeight;
}

// 单元格悬浮显示td的内容
function paramsMatter(value,row, index,field) {
  var span = document.createElement('span');
  span.setAttribute('title',value);
  span.innerHTML = value;
  return span.outerHTML;

}

// 汇总表中的操作按钮组
function summaryOperate(value,row, index,field) {
  return [
    '<i class="standardDefinition" style="color:var(--main-color);cursor: pointer;font-style: normal" >标准定义|</i>'
    +'<i class="qualityDefinition" style="color:var(--main-color);cursor: pointer;font-style: normal" >质量定义|</i>'
    +'<i class="processDefinition" style="color:var(--main-color);cursor: pointer;font-style: normal" >处理定义|</i> '
    +'<i class="dataBloodline" style="color:var(--main-color);cursor: pointer;font-style: normal" >数据血缘</i> '
  ].join('');
}


var summaryOperateClick ={
  // 查看具体的表信息
  'click .standardDefinition': function (event, value, row, index) {
    showPage(2);
    // 然后根据传入的值 查询具体的标准信息
    var treeNode = new Object();
    treeNode.id = row.tableId;
    treeNode.name = "";
    treeNode.tableNodeFlag = true;
    zTreeOnClick(null, null, treeNode)

  }
}

/**
 * 跳转到详细页面
 */
function addNewObject() {
  showPage(2);
  editResourceManageTable("click")
}

/**
 * 显示哪个页面 是汇总页面还是详细页面
 * @param num  1：汇总页面  2：详细页面
 */
function showPage(num) {
  if(num === 1){
    $("#summaryInformation").css("display","inline-block")
    $("#details").css("display","none");
    $(".treeDiv").css("display","inline-block");
    $(".TableDiv").css("margin-left","265px");
    $(".TableDiv").css("width","calc( 100% - 270px )");
  }else{
    $("#summaryInformation").css("display","none")
    $("#details").css("display","inline-block");
    $(".treeDiv").css("display","none");
    $(".TableDiv").css("margin-left","15px");
    $(".TableDiv").css("width","99%")
  }

}