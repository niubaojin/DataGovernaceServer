var path=window.document.location.pathname.split("/")[1];
path="/"+path;
document.write("<script language='JavaScript' src='myJs/commons.js'> </script>");//引入通用js方法


$(document).ready(function() {  
	//初始化树
    createTree();
    contionInit();

    var tableInit = new fieldCodeValTableInit();
    tableInit.Init();
    // var width = $(window).width()*0.21 -20;
    // $("#codeText").css("width",width+"px");

});

function FieldCodeValBtnQuery(){
    var pageSize =  $('#FieldCodeValTable').bootstrapTable('getOptions').pageSize;
    var opt = {
        url:"fieldCodeValTable",
        query:{
            pageSize: pageSize,   //页面大小
            pageNum:  1,  //页码
            codeId: $("#codeIdHide").val(),
            valText: $("#valText").val()
        }
    };
    $('#FieldCodeValTable').bootstrapTable("refresh",opt);
}

function FieldCodeValBtnExport(){
    bootbox.confirm({
        size: "small",
        backdrop: true,
        title:"<span style='font-size: 16px;'>提示</span>",
        message: "<img src='img/myImg/warn.png' style='margin: -5px 10px 0 0;float: left'><span>是否导出数据？</span>",
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
                var codeId =  $("#codeIdHide").val();
                var valText = $("#valText").val();
                var url = "FieldCodeValBtnExport?codeId="+codeId+"&valText="+valText;
                location.href = url;
            }
        }
    });
}


function contionInit(){
    suggest("codeText","codeTextsQuery?codeText=");
    suggest("valText","valTextQuery?codeId="+$("#codeIdHide").val()+"&valText=");
}

function resetTreeQuery(){
    createTree();
}

function createTree(){
    //获取列表值
    var data="";
    $.ajax({
        type:"post",
        url: "createNationalCodeTableTree",
		data:{
            codeText:$("#codeText").val()
		},
        calculable : false,
        async:false,
        success:function(result){
            data=result;
        }
    });
    $('#treeId').treeview({
        text:"Node 1",
        icon:"",
        selectedIcon:"glyphicon glyphicon-ok-sign",
        href:"#node-1",
        selectedBackColor:"var(--main-color)",
        selectable:false,
        state: {
            checked:false,
            disabled:false,
            expanded:false,
            selected:false
        },
        onNodeSelected:function(event, data) {

            //隐藏codeid,供codeval表调用
            $("#codeIdHide").val(data.tabs[0]);
            //树的搜索条件回显
			$("#codeText").val(data.text);
			//显示code表格数据
            fieldCodeTableQuery(data.tabs[0]);

            //codebval搜索框清空
            $("#valText").val("");
            //显示codeVal表格数据
            FieldCodeValBtnQuery();
            //codeVal搜索框销毁重置
            $("#valText").bsSuggest("destroy");
            $("#valText").css({"width":"220", "height":"31", "display":"inline","padding-left":"30px"});
            suggest("valText","valTextQuery?codeId="+$("#codeIdHide").val()+"&valText="+$("#valText").val());
        },
        tags: ['available'],
        data: data,
        levels: 1   //初始化树的层级数
    });
}

function fieldCodeTableQuery(codeId){
    $('#FieldCodeValTable').bootstrapTable('getOptions').pageNumber = 1;
    $.ajax({
        type:"post",
        url:"fieldCodeTableQuery",
        data:{
            codeId:codeId
        },
        calculable : false,
        async:false,
        success:function(result){
            if(result.length!=0){
                $("#td_codeId").text(result[0].codeId);
                $("#td_codeName").text(result[0].codeName);
                $("#td_codeText").text(result[0].codeText);
                if(result[0].memo==null || result[0].memo=="null"){
                    $("#td_memo").text("");
                }else{
                    $("#td_memo").text(result[0].memo);
                }
            }
        }
    });
}

/**
 * 计算获得表的高度
 * @returns {number}
 */
function caculateTableHeight() {
    var tableHeight = $("#contentPagin").height()-40;
    return tableHeight;
}

var fieldCodeValTableInit = function () {
    var tableInit = new Object();
    tableInit.Init = function (){
        $('#FieldCodeValTable').bootstrapTable({
            type:'POST',                                         //请求方式（*）
            dataType:'json',
            contentType : "application/json,charset=utf-8",
            cache: false,                    //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            url:"fieldCodeValTable",    //请求后台的URL（*）
            queryParams: tableInit.queryParams,//传递参数（*）
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            pageNumber:1,                       //初始化加载第一页，默认第一页
            pageSize: 10,                       //每页的记录行数（*）
            pageList: [10,20,30,50],        //可供选择的每页的行数（*）
            paginationPreText: '<',
            paginationNextText: '>',
            paginationShowPageGo: true,
            showJumpto: true,
            smartDisplay: false,
            paginationLoop: false,
            clickToSelect: true,                //是否启用点击选中行
            sortable: true,                     //是否启用排序
            sortOrder:"",
            sortName:"",
            height: caculateTableHeight(),
            columns : [
                {field : 'codeValId',title : "序号",align : "center",valign:"middle"},
                {field : 'codeId',title : "代码Id",align : "center",valign:"middle"},
                {field : 'valText',title : "代码中文名称",align : "center",valign:"middle"},
                {field : 'valValue',title : "代码名称",align : "center",valign:"middle"},
                {field : 'memo',title : "memo",align : "center",valign:"middle",visible : false},
                {field : 'valTextTitle',title : "valTextTitle",align : "center",valign:"middle",visible : false}
            ],
            onLoadSuccess: function (data) {
                $("#contentPagin .page-list ul li").append('<span>条/页</span>');
                $("#contentPagin .page-list button span.page-size").append('<span>条/页</span>');
                $('#contentPagin .fixed-table-pagination').prepend('<div class="total-num">共 '+ data.total+' 条</div>');
            }
        });
    };
    tableInit.queryParams = function (params) {
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            pageSize: params.limit,   //页面大小
            pageNum: (params.offset / params.limit) + 1,  //页码
            codeId: $("#codeIdHide").val(),
            valText: $("#valText").val()
        };
        return temp;
    };
    return tableInit;
};


