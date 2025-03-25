var path=window.document.location.pathname.split("/")[1];
path="/"+path;
document.write("<script language='JavaScript' src='myJs/commons.js'> </script>");//引入通用js方法

var tableObj  = '';
$(document).ready(function() {  
	//初始化树
    createTree();
    contionInit();

    var tableInit = new CodeValTableInit();
    tableObj = tableInit.Init();

    //初始化上传插件
    initTextUpload();

   // var width = $(window).width()*0.21 -20;
   // $("#dmzdzwm").css("width",width+"px");

});

function CodeValTableBtnQuery(){
    var pageSize =  $('#CodeValTable').bootstrapTable('getOptions').pageSize;
    $('#CodeValTable').bootstrapTable('getOptions').pageNumber = 1;
    var opt = {
        url:"CodeValTable",
        query:{
            pageSize: pageSize,   //页面大小
            pageNum:  1,  //页码
            dmzd: $("#DMZDHide").val(),
            dmmc: $("#dmmc").val()
        }
    };
    $('#CodeValTable').bootstrapTable("refresh",opt);
}

function CodeValTableBtnExport(){
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
                var dmzd =  $("#DMZDHide").val();
                var dmmc = $("#dmmc").val();
                var url = "CodeValTableBtnExport?dmzd="+dmzd+"&dmmc="+dmmc;
                location.href = url;
            }
        }
    });
}


function contionInit(){
    suggest("dmzdzwm","dmzdzwmQuery?dmzdzwm=");
    suggest("dmmc","dmmcQuery?dmzd="+$("#DMZDHide").val()+"&dmmc=");
}

function resetTreeQuery(){
    createTree();
}

function createTree(){
    //获取列表值
    var data="";
    $.ajax({
        type:"post",
        url: "createDMZDZWMTree",
		data:{
            dmzdzwm:$("#dmzdzwm").val()
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
            $("#DMZDHide").val(data.tabs[0]);
            //树的搜索条件回显
			$("#dmzdzwm").val(data.text);
			//显示code表格数据
            CodeTableQuery(data.tabs[0]);

            //codebval搜索框清空
            $("#dmmc").val("");
            //显示codeVal表格数据
            CodeValTableBtnQuery();
            //codeVal搜索框销毁重置
            $("#dmmc").bsSuggest("destroy");
            $("#dmmc").css({"width":"220", "height":"31", "display":"inline","padding-left":"30px"});
            suggest("dmmc","dmmcQuery?dmzd="+$("#DMZDHide").val()+"&dmmc="+$("#dmmc").val());

        },
        tags: ['available'],
        data: data,
        levels: 1   //初始化树的层级数
    });
}

function CodeTableQuery(dmzd){
    $.ajax({
        type:"post",
        url:"CodeTableQuery",
        data:{
            dmzd:dmzd
        },
        calculable : false,
        async:false,
        success:function(result){
            if(result.length!=0){
                $("#td_dmzd").text(result[0].dmzd);
                $("#td_dmzdzwm").text(result[0].dmzdzwm);
            }
        }
    });
}

function CodeValTableBtnDelete(){
    var getSelectRows = $('#CodeValTable').bootstrapTable('getSelections');
    if (getSelectRows.length <= 0) {
        /*bootbox.alert("请先选择一条数据!");*/
        toastr.info("请先选择一条数据!")
        return;
    } else {
        bootbox.confirm({
            size: "small",
            backdrop: true,
            title:"<span style='font-size: 16px;'>提示</span>",
            message: "<img src='img/myImg/warn.png' style='margin: -5px 10px 0 0;float: left'><span>是否要删除所选数据？</span>",
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
                    for(var i=0;i<getSelectRows.length;i++){
                        var dmzd = null; var dmzdzwm = null; var dm = null; var dmmc = null;
                        dmzd = getSelectRows[i].dmzd;
                        dmzdzwm = getSelectRows[i].dmzdzwm;
                        dm = getSelectRows[i].dm;
                        dmmc = getSelectRows[i].dmmc;
                        $.ajax({
                            type:"post",
                            url: "CodeValTableDelete",
                            data:{
                                dmzd:dmzd,
                                dmzdzwm:dmzdzwm,
                                dm:dm,
                                dmmc:dmmc
                            },
                            calculable : false,
                            async:false,
                            success:function(result){
                            }
                        });
                    }
                    //刷新表格数据
                    CodeValTableBtnQuery();
                   // bootbox.alert("删除成功!");
                }
            }
        });
    }
}

/**
 * 计算获得表的高度
 * @returns {number}
 */
function caculateTableHeight() {
    var tableHeight = $("#contentPagin").height()-70;
    return tableHeight;
}
/**
 * 窗口大小改变事件
 */
function windowResizeEvent() {
    $(window).resize(function () {
        var tableHeight = $("#contentPagin").height()-70;
        tableObj.bootstrapTable('resetView',{
            height:tableHeight
        });
    });
}


var CodeValTableInit = function () {
    var tableInit = new Object();
    tableInit.Init = function (){
        var obj = $('#CodeValTable').bootstrapTable({
            type:'POST',                                         //请求方式（*）
            dataType:'json',
            contentType : "application/json,charset=utf-8",
            cache: false,                    //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            url:"CodeValTable",    //请求后台的URL（*）
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
                {checkbox : true},
                {field : 'dmzd',title : "代码字段",align : "center",valign:"middle",visible : false},
                {field : 'dmzdzwm',title : "代码字段中文名",align : "center",valign:"middle",visible : false},
                {field : 'dm',title : "代码",align : "center",valign:"middle",editable:{
                    placement:'bottom'
                    }},
                {field : 'dmmc',title : "代码名称",align : "center",valign:"middle",editable:{
                        placement:'bottom'
                    }}
            ],
            onEditableSave: function (field,row,oldValue,$el) {
                //条件
                var dmzd = row.dmzd;
                if(field=="dm"){
                     var dm = oldValue;
                     var dmmc = row.dmmc;
                }else {
                     var dm = row.dm;
                     var dmmc = oldValue;
                }
                //要更新的新值
                var dmNew = row.dm;
                var dmmcNew = row.dmmc;

                $.ajax({
                    type:"post",
                    url:"updateDMAndDMMC",
                    data:{
                        dmzd : dmzd,
                        dm : dm,
                        dmmc : dmmc,
                        dmNew:dmNew,
                        dmmcNew:dmmcNew
                    },
                    calculable : false,
                    async:false,
                    success:function(result){
                        if(result=="success"){
                            toastr.success("修改成功!")

                        }else{
                            toastr.error("修改失败!")
                        }
                    }
                });

            },
            onLoadSuccess: function (data) {
                $("#contentPagin .page-list ul li").append('<span>条/页</span>');
                $("#contentPagin .page-list button span.page-size").append('<span>条/页</span>');
                $('#contentPagin .fixed-table-pagination').prepend('<div class="total-num">共 '+ data.total+' 条</div>');
            }
        });
        return obj;
    };
    tableInit.queryParams = function (params) {
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            pageSize: params.limit,   //页面大小
            pageNum: (params.offset / params.limit) + 1,  //页码
            dmzd: $("#DMZDHide").val(),
            dmmc: $("#dmmc").val()
        };
        return temp;
    };
    return tableInit;
};


function exportFormwork(){
    var url = "exportFormwork?dmzd=&dmmc=";
    location.href = url;
}


function importDatas(){
    $("#textUpload").modal('show');
}


function initTextUpload(){
    //上传插件实例化
    $("#txt_file").fileinput({
        language: 'zh', //设置语言
        uploadUrl: "importRegionalCodeData",  //上传的地址(访问接口地址)
        allowedFileExtensions: ['xlsx','xls'],  //接收的文件后缀
        uploadAsync: true,  //默认异步上传
        showUpload: true,  //是否显示上传按钮
        showRemove : true,  //显示移除按钮
        showPreview : true,  //是否显示预览
        showCaption: false,  //是否显示标题
        browseClass: "btn btn-primary",  //按钮样式
        dropZoneEnabled: false,  //是否显示拖拽区域
        minImageWidth: 50,  //图片的最小宽度
        minImageHeight: 50,  //图片的最小高度
        maxImageWidth: 1000,  //图片的最大宽度
        maxImageHeight: 1000,//图片的最大高度
        uploadLabel: "导入",           //设置上传按钮的汉字
        maxFileSize: 0, //单位为kb，如果为0表示不限制文件大小
        maxFileCount: 1,  //表示允许同时上传的最大文件个数
        enctype: 'multipart/form-data',
        validateInitialCount:true,
        previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
        msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",
    });
    $("#txt_file").on('fileuploaded',function(event,data,preview,index){
        if(data.response=="success") {
            $("#textUpload").modal('hide');
            toastr.success("文件上传成功!");
            //刷新CodeValTable
            CodeValTableBtnQuery();

            //重置树的搜索条件
            $("#dmzdzwm").bsSuggest("destroy");
            $("#dmzdzwm").css({"width":"222", "height":"30", "display":"inline"});
            suggest("dmzdzwm","dmzdzwmQuery?dmzdzwm=");

        }else{
            $("#txt_file").fileinput('refresh');
            toastr.error("上传出错,请检查文件重新上传!");
        }
    });
}