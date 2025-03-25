var path = window.document.location.pathname.split("/")[1];
path = "/" + path;
//所有的协议厂商中文名
var allSourceFirmList = ["全部", "普天", "汇智", "三所", "烽火", "三汇", "锐安", "部中心", "部中心mq", "海康"];
// 存储表状态
var storageTableStatusList = ["正式使用", "临时使用", "废止"];
// 更新表类型
var isActiveTableList = [{label:"实时表",value:"1"}, {label:"批量表",value:"0"}]
// 存储方式
var storageModeList = ["hbase", "oracle", "sql", "ads"];
var sourceClassifyId = "";
//存储修改后的分级分类信息
var organizationClassifyId = "";
var sourceClassifyId = "";

var createTableObj;

var columnInfo = [];

var codeIdModal;
// 左侧tree中显示哪个tab页
var dataTypeCode = 1;
var scrollbar;
var GLOBAL = {
    dwParams: {
        standardId: '',
        sourceProtocol: '',
        tableName: '',
        sourceFirm: '',
        sourceSystem: '',
        belongSystemCode: '',
        centerId: ''
    },
    tableID: 'JZ_RESOURCE_MEE',
    tableStructJsonStr: '',//列信息
    dwTableInfo: {},
    isFlow: true, //是否是流程
    turnIndex: '',//审批流程被驳回重新编辑后跳转在办列表
    pageUrl: '',
    dataGovernaceWeb: ''
};

// // 数据仓库跳转过来存储的数据参数
// dataResourceJumpPage:{
//   dataId:"",
//     tableId:"",
//     inTableName:"",
//     inSourceFirmCode:"",
//     inSourceProtocol:""
// }

//  因为最后要统一用一个保存按钮，即对表格的修改只修改页面，所以
//   字段信息/来源关系/数据信息用三个全局变量 来存储
//   @add 20200217
// 字段定义的信息
var column_table_list = [];
//  来源关系
var source_relationship_list = [];
// 数据信息
var dataid_message = new Object();
var addUpdateColumn = "";
// 判断是否点击了编辑按钮  true表示点击了编辑按钮
var editFlag = false;
//  审批流程的
var approvalId = "";
var createApprovalId = "";
// 数据仓库跳转过来的centerId
var centerId = "";
// ----------------------------------------------------------------------

//用于表检测的对象
var backFilePojo = {
    mianClassfical: "",
    subClassfical: "",
    dataName: "",
    tableName: ""
};
// 页面url
pageUrl = "";
// 当 大平台是 其他 中页面显示ADS、odps相关列名，需进行优化
//  大数据平台的类型为 huaweiyun / aliyun
var dataBaseType = "aliyun";
// 存储已建表信息的对象
var created_table_list = [];



function checkAndGetParams() {
    //获取url中的参数
    GLOBAL.dwParams.sourceProtocol = getQueryParam("sourceProtocol");//来源协议
    GLOBAL.dwParams.tableName = decodeURIComponent(getQueryParam("tableName"));//数据名称
    GLOBAL.dwParams.sourceFirm = decodeURIComponent(getQueryParam("sourceFirm"));//来源单位
    GLOBAL.dwParams.sourceSystem = decodeURIComponent(getQueryParam("sourceSystem"));//来源系统
    GLOBAL.dwParams.standardId = getQueryParam("standardId");//tableID
    GLOBAL.dwParams.belongSystemCode = decodeURIComponent(getQueryParam("belongSystemCode"));//来源系统中文名
    GLOBAL.dwParams.centerId = decodeURIComponent(getQueryParam("centerId"));//数据中心名
    var tableStructJsonStr = getQueryParam("tableStructJsonStr");//表结构

    if (checkNullAndFalse(tableStructJsonStr) && checkNullAndFalse(GLOBAL.dwParams.sourceProtocol) &&
        checkNullAndFalse(GLOBAL.dwParams.tableName) && checkNullAndFalse(GLOBAL.dwParams.sourceFirm) &&
        checkNullAndFalse(GLOBAL.dwParams.sourceSystem) || checkNullAndFalse(GLOBAL.dwParams.standardId)) {
        //回填以上信息

    } else {//不是流程
        GLOBAL.isFlow = false;
        return;
    }
    GLOBAL.dwTableInfo = decodeURIComponent(tableStructJsonStr);
    // $("#flowComplete").css("display","block");

    console.log("sourceProtocol:" + GLOBAL.dwParams.sourceProtocol);
    console.log("tableName:" + GLOBAL.dwParams.tableName);
    console.log("sourceFirm:" + GLOBAL.dwParams.sourceFirm);
    console.log("sourceSystem:" + GLOBAL.dwParams.sourceSystem);
    console.log("tableStructJsonStr:" + GLOBAL.dwTableInfo);
}

function checkNullAndFalse(value) {
    return !checkIsNull(value) || value == "false";
}

function getSourceFirmCode(key) {
    if (checkIsNull(key)) return "";
    switch (key) {
        case "全部":
            return "0";
        case "普天":
            return "1";
        case "汇智":
            return "2";
        case "三所":
            return "3";
        case "烽火":
            return "4";
        case "三汇":
            return "5";
        case "锐安":
            return "6";
        case "部中心":
            return "7";
        case "部中心mq":
            return "8";
        case "海康":
            return "9";
    }
}

function backFill() {
    //如果有tableID，则需要回填
    var tableID = getQueryParam("standardId");//

    if (checkIsNull(tableID)) return;
    if (tableID == "false") return;
    $("#editResource").attr("style", "display:inline");
    $("#saveResource").attr("style", "display:none");
    // 将select框默认选择成全列  输入框置空
    $("#needValueSelect").val("all");
    $("#searchInputContext").val("");
    // 查询数据信息
    creatObjectDetail(tableID, "");
    // 查询字段定义的相关信息
    $("#resourceManageObjectField").bootstrapTable('removeAll');
    updateObjectField(tableID);
    // 查询来源关系
    $("#sourceRelationShip").bootstrapTable('removeAll');
    sourceRelationShipDataGet(tableID);
    //回填完先回调一下
    // $("#flowComplete").click();
    var taskInfo = {};
    taskInfo.standardId = $("#tableId").text();
    var data = getTableColumnByTableIdFlow();
    var outSourceFirmStr = checkIsNull($("#sourceFirmTdSelect").val()) ? $("#sourceFirm").text() : $("#sourceFirmTdSelect").val()
    var lastUpdatedProp = {
        standardPropStr: JSON.stringify(taskInfo),
        outSourceSystem: $("#objectName").val(),     //所属系统代码 (数值)
        sysChiName: $("#objectName").text(),     //所属系统中文名
        outSourceProtocol: $("#tableId").text(),            //数据协议
        outTableName: $("#dataSourceName").text(),           //协议中文名
        outSourceFirm: outSourceFirmStr,          //输出-目标产商中文名
        outSourceSystemName: $("#objectName").val(), //输出-来源系统中文名(数值)
        sourceFirmCode: getSourceFirmCode(outSourceFirmStr),//输出-产商代码
        objectId: $("#objectIdManage").text(),   //序号
        standardFieldJsonStr: data
    };
    var message = {
        lastUpdatedProp: lastUpdatedProp,
        pageTag: 'standard'
    };

    window.parent.postMessage(message, "*");
}


window.operateEvents = {
    'click .mod': function (e, value, row, index) {
        $("#fieldCodeValModal").off();
        $("#fieldCodeValModal").modal('show');
        $("#fieldCodeValModal").on("shown.bs.modal", function () {
            codeIdModal = row.codeid;
            $('#valTextInput').val('');
            $('#valValueInput').val('');
            codeValTableModal(codeIdModal);
            $("#fieldCodeValModalTable").bootstrapTable('refresh');
        });
    }
};
$(document).ready(function () {
    toastr.options.positionClass = 'toast-top-center';
    $('[data-toggle="popover"]').popover();
    // 获取到大数据平台的类型 然后处理掉其它信息里面的的标签信息
    getDataBaseType();
    //处理div滚动条
    // motifyDivOverflow();
    checkAndGetParams();

    //左侧tree的创建
    // createTree();
    // 字段定义表的创建
    resourceManageObjectField();
    //来源关系表的创建
    sourceRelationShipTable();
    // 已建表的页面的创建
    createdTableTab();
    jumpPage();
    // 获取一级分类的相关信息
    mainClassChange("1");
    inputBlurFunction();

    //如果是流程中来的，则需要回填某些值
    if (GLOBAL.isFlow) {
        initTableParams();//表信息的保存
        addRelationShipRecord();//关系信息的保存
        // 如果是流程中过来的，不保存 需要自动保存
        // $("#saveResource").click();//点击保存按钮
        $("#resourceManageObjectField").bootstrapTable('removeAll');
        updateObjectField($("#tableId").text());
        //如果是流程，把那个 + 按键隐藏掉
        $("#addTab").css("display", "none");
    } else {
        $("#addTab").css("display", "block");
    }
    backFill();
    // 数据仓库那边需要跳转过来，编写对应的js函数

    // 滚动条初始化
     scrollbar = new PerfectScrollbar(".TableDiv", {});

    //接收消息监听器事件
    window.addEventListener('message', function (e) {
        approvalCallbackFunc(e);
    });
    getPageUrl();
    // refreshPageCook
    refreshPage();
    addTotalAndtitle('#resourceManageObjectField', column_table_list.length);
    $("#dataCenterName").bind("keydown", function (event) {
        if (event.keyCode === 13) {
            createTree()
        }
    })
    $("#searchInputContext").bind("keydown", function (event) {
        if (event.keyCode === 13) {
            objectFieldQuery()
        }
    });
    $(".TableDiv").scroll(function(){
        try{
            var scroH = $(".TableDiv").scrollTop();
            if(scroH > 0 && $(".table-filter")[0].style.display === "block"){
                $(".table-filter").css("display", "none");
            }
        }catch(e){
            console.log(e)
        }
    });
    // 字段的敏感分类需要从数据库中获取 所以需要先获取这个数据
    getSensitivityLevelSelect();
    getJumpOrthePage();

});

/**
 * 获对应的大数据类型
 */
function getDataBaseType() {
    $.ajax({
        type: "get",
        url: "getDataBaseType",
        calculable: true,
        async: true,
        success: function (result) {
            // 直接获取到大数据 数据库类型
            dataBaseType = result
            if("huaweiyun" === result.toLowerCase()){
                $("#partitionRecnoAddLabel").css("display","none");
                $("#odpsPattitionLabel").html("hive的分区列：");
            }else{
                $("#partitionRecnoAddLabel").css("display","block");
                $("#odpsPattitionLabel").html("ODPS的分区列：");
            }
        },
        error: function (result) {
            toastr.error("后端程序未启动");
        }
    });

}




function refreshPage() {
    var refreshPage = sessionStorage.getItem("refreshPageCook");
    if (refreshPage) {
        sessionStorage.removeItem("refreshPageCook");
        editResourceManageTable('click');
    }
}

// 真实表名框获取焦点事件
function realTableFocus() {
    $("#realTableNameInput").bind('input porpertychange', function () {
        var returnFlag = checkRealTableNameisOldOrNew($(this).val());
        if ($("#objectIdManage").text() == "" || !returnFlag) {
            //根据数据组织 和数据名来回填真实表名
            var oldTableNameList = $(this).val().split("_");
            if (checkIsNull($("#organizationClassifyInput").val()) && checkIsNull($("#dataSourceNameInput").val().trim().replace(/_|\(|\)|\（|\）|-|,|，|—/g,""))) {
                if (checkIsNull($("#organizationClassifyInput").val())) {
                    toastr.error("请先选择数据组织分类的相关信息");
                    $(this).val("");
                    return;
                }
                if (checkIsNull($("#dataSourceNameInput").val().trim().replace(/_|\(|\)|\（|\）|-|,|，|—/g,""))) {
                    toastr.error("请先填写数据名的相关信息");
                    $(this).val("");
                    return;
                }
            }
            fillBackRealName($("#organizationClassifyInput").val(), $("#sourceClassifyInput").val(), $("#dataSourceNameInput").val().replace(/_|\(|\)|\（|\）|-|,|，|—/g,""));
            if (!checkIsNull(oldTableNameList) && oldTableNameList[oldTableNameList.length - 1].trim().toLowerCase() !== "xxx") {
                if ($("#organizationClassifyInput").val().split("/") === "业务要素索引库分类") {
                    if (oldTableNameList.length === 3) {
                        $("#realTableNameInput").val(backFilePojo.tableName + oldTableNameList[oldTableNameList.length - 1].trim());
                    } else {
                        $("#realTableNameInput").val(backFilePojo.tableName + "001");
                    }
                } else {
                    if (oldTableNameList.length === 4) {
                        $("#realTableNameInput").val(backFilePojo.tableName + oldTableNameList[oldTableNameList.length - 1].trim());
                    } else {
                        $("#realTableNameInput").val(backFilePojo.tableName + "001");
                    }
                }
            } else {
                $(this).val(backFilePojo.tableName + "001");
            }
        }
    })
}

// 真实表名不能只用焦点触发事件，当分级分类发生变化时也要修改真实表名
function realTableNameChange() {
    try {
        // 先判断 表名的输入框是不是只读的模式，如果是只读，则不能修改表名

        if (checkIsNull($("#realTableNameInput")[0]) || $("#realTableNameInput")[0].attributes.readOnly !== undefined) {
            return;
        }
        var tableName = $("#realTableNameInput").val();
        var returnFlag = false;
        var returnFlag = checkRealTableNameisOldOrNew(tableName);
        if ($("#objectIdManage").text() == "" || !returnFlag) {
            //根据数据组织 和数据名来回填真实表名
            var oldTableNameList = tableName.split("_");
            fillBackRealName($("#organizationClassifyInput").val(), $("#sourceClassifyInput").val(), $("#dataSourceNameInput").val().replace(/_|\(|\)|\（|\）|-|,|，|—/g,""));
            if (!checkIsNull(oldTableNameList) && oldTableNameList[oldTableNameList.length - 1].trim().toLowerCase() !== "xxx") {
                if ($("#organizationClassifyInput").val().split("/") === "业务要素索引库分类") {
                    if (oldTableNameList.length === 3) {
                        $("#realTableNameInput").val(backFilePojo.tableName + oldTableNameList[oldTableNameList.length - 1].trim());
                    } else {
                        $("#realTableNameInput").val(backFilePojo.tableName + "001");
                    }
                } else {
                    if (oldTableNameList.length === 4) {
                        $("#realTableNameInput").val(backFilePojo.tableName + oldTableNameList[oldTableNameList.length - 1].trim());
                    } else {
                        $("#realTableNameInput").val(backFilePojo.tableName + "001");
                    }
                }
            } else {
                $("#realTableNameInput").val(backFilePojo.tableName + "001");
            }
        }
    } catch (e) {
        toastr.error("表名规范错误" + e)
    }
}

// 数据名的输入框进行修改之后，真实表名也要进行修改
function dataSourceNameFocus() {
    // var reg1 = new RegExp("[^a-zA-Z0-9\_\u4e00-\u9fa5]");
    $("#dataSourceNameInput").bind('input porpertychange', function () {
        // var inTel = $("#dataSourceNameInput").val();
        // if (inTel !== null && reg1.test(inTel)) {
        //     var inTelNew = inTel.substring(0, inTel.length - 1);
        //     $("#dataSourceNameInput").val(inTelNew.trim());
        //     toastr.error("数据名中存在特殊字符，只能用中文/数字/字母");
        // } else if (inTel.trim() !== "") {
        //     realTableNameChange();
        // }
        realTableNameChange();
        // if(inTel !==null &&  inTel.indexOf("_") !== -1) {
        //   toastr.error("数据名中不能包含 下划线 ");
        //   var inTelNew = inTel.replace("_", "");
        //   $("#dataSourceNameInput").val(inTelNew);
        // }else if(inTel !==null &&  inTel.indexOf("——") !== -1) {
        //   toastr.error("数据名中不能包含 中文横线 ");
        //   var inTelNew = inTel.replace("——", "");
        //   $("#dataSourceNameInput").val(inTelNew);
        // }else

    })
}


// 审批弹框关闭
function approvalCallbackFunc(e) {
    if (e.data) {
        if (e.data.pageTag == 'approval') {
            $('#importApprovalInfoModal').modal('hide');
            if (e.data.action == 'submit') {
                toastr.info("操作成功");
                setTimeout(function () {
                    closeApproval();
                }, 1000);
            } else if (e.data.action == 'turn') {
                setTimeout(function () {
                    toastr.info("操作成功");
                    window.location.href = GLOBAL.turnIndex;
                }, 1000);
            }
        }
    }
}

function jumpPage() {
    var executeMethod = getQueryParam("executeMethod");//从哪里跳转
    // 获取第三方的模块名 @20200917 新增这个内容
    var moduleName = getQueryParam("moduleName");
    if(!checkIsNull(moduleName)){
        // 本地行政编码的6位号代码
        var local_administrative_code = getQueryParam("localAdministrativeCode");
        if(checkIsNull(local_administrative_code) ||
            !checkIsNumber(local_administrative_code)||
            local_administrative_code.length !== 6){
            toastr.error("参数[localAdministrativeCode]必须都是数字且数字长度为6");
            return;
        }
        // 编辑按钮的点击事件
        editResourceManageTable('click');
        editPage();
        // 隐藏 来源关系 这个tab页面
        $(".oneSourceRelationShipTab").css("display","none")
        return;
    }

    if (checkIsNull(executeMethod)) return;
    if(executeMethod.toLowerCase() === "edit"){
        editResourceManageTable('click')
    }else if (executeMethod.toLowerCase() === "dataresource") {
        //  表示是从数据仓库跳转过来的 之后读取session里面的值，
        //  从里面获取传过来的参数
        var dataId = getQueryParam("dataId"); //数据仓库传过来的id数据
        var tableId = getQueryParam("tableId"); //数据仓库传过来的id数据
        var centerIdPage = getQueryParam("centerID");  // 数据仓库的id
        var jumpType = getQueryParam("jumpType");  // 数据仓库过来的类型 新建标准：add   查看已建标准:query
        // 添加输出项目的相关信息
        var dataStandardTableId = getQueryParam("dataStandardTableId");
        var dataStandardTableName = getQueryParam("dataStandardTableName");
        if (checkIsNull(dataId)) {
            toastr.error("参数dataId不能为空");
            return;
        }
        if (checkIsNull(tableId)) {
            toastr.error("参数tableName不能为空");
            return;
        }
        if (checkIsNull(jumpType)) {
            toastr.error("参数jumpType不能为空");
            return;
        }
        if (checkIsNull(centerIdPage)) {
            toastr.error("参数centerId不能为空");
            return;
        }
        if (jumpType.toLowerCase() === "query") {
            var treeNode = new Object();
            treeNode.id = dataStandardTableId;
            treeNode.name = dataStandardTableName;
            treeNode.tableNodeFlag = true;
            zTreeOnClick(null, null, treeNode)
            return;
        }
        var dataObject = null;
        $.ajax({
            type: "get",
            data: {
                "dataId": dataId,
                "tableName": tableId
            },
            url: "getDataResourceInformation",
            calculable: false,
            async: false,
            success: function (result) {
                if (result.status === 1) {
                    dataObject = result.data;
                } else {
                    toastr.error("获取数据仓库中指定数据信息报错，回填页面失败");
                    return;
                }
            },
            error: function (result) {
                toastr.error("后端程序未启动");
            }
        });
        if (dataObject != null) {
            console.log(dataObject);
            // 将从数据仓库的信息写入到全局变量中
            var sourceTableName = dataObject.sourceTableName; // 数据仓库那边的表名
            $("#sourceId").text(dataObject.sourceId);  // 源表ID的数据信息
            $("#dataSourceName").text(dataObject.dataSourceName);//数据名
            $("#sourceFirm").text("全部");     //协议厂商默认为全部
            $("#objectName").val(dataObject.sourceProtocolCode);  //所属应用系统
            $("#objectName").text(dataObject.sourceProtocolCh);
            // $("#tableId").text(dataObject.sourceId);     //  -- >表ID
            $("#objectMemo").text("");     //  -- >表ID
            $("#objectIdManage").text(dataObject.objectId);     //  objectId的值
            // $("#editResource").click();//点击编辑按钮
            $("#realTableName").text(dataObject.tableNameObject);
            // 20200518 从数据仓库跳转，如果有来源分类，则需要回填相关信息
            if(!checkIsNull(dataObject.dataOrganizationStr)){
                $("#organizationClassify").text(dataObject.dataOrganizationStr);
            }
            if(!checkIsNull(dataObject.dataResourceOrigin)){
                $("#sourceClassify").text(dataObject.dataResourceOrigin);
            }
            // 查询来源关系
            $("#sourceRelationShip").bootstrapTable('removeAll');
            sourceRelationShipDataGet(dataObject.sourceId);
            editResourceManageTable('change');
            // 当自动选择分类信息之后，真实表名需要自动跳转
            // 来源关系的插入
            //选择数据仓库
            $("#addSourceRelationClick").click();//点击添加来源按钮
            var option = $("#sourceRelationSelectModal").find("option");
            for (var i = 0; i < option.length; i++) {
                if (option[i].value == "database") {
                    $(option[i]).attr("selected", "selected");
                } else {
                    $(option[i]).remove("selected");
                }
            };
            sourceRelationModalChange();
            $("#dataBaseNameMode").val(centerIdPage + "@@" + dataId);
            $("#tableNameMode").empty();
            $("#tableNameMode").append("<option value='" + sourceTableName + "' selected>" + sourceTableName + "</option>");
            //来源协议
            $("#sourceProtocolMode").val(dataObject.sourceId);
            //来源系统
            $("#sourceSystemMode").val(dataObject.sourceProtocolCode);
            // $("#sourceSystemMode").find("option:contains('"+dataObject.sourceProtocolCh+"')").attr("selected",true);
            //来源单位
            $("#sourceFirmMode").val(dataObject.sourceFirmCode);
            // 表中文名
            $("#tableNameEtlCh").val(dataObject.dataSourceName);
            // $("#sourceFirmMode").find("option:contains('"+dataObject.sourceFirmCh+"')").attr("selected",true);
            addSourceRelationPageButton();//在关系表中添加一条记录
            centerId = centerIdPage;
            //$("#saveResource").click();//点击保存按钮
            // 重新查询表字段信息
            $("#resourceManageObjectField").bootstrapTable('removeAll');
            updateObjectField($("#tableId").text());


            //sourceRelationClick();
            // 将字段信息插入到数据库中
            GLOBAL.dwParams.tableName = sourceTableName;
            GLOBAL.dwParams.sourceProtocol = dataObject.sourceId;
            GLOBAL.dwParams.sourceFirm = dataObject.sourceFirmCh;
            GLOBAL.dwParams.sourceSystem = dataObject.sourceProtocolCode;
            GLOBAL.dwTableInfo = JSON.stringify(dataObject.FieldList);
            GLOBAL.tableID = dataObject.sourceId;
            // initSourceFieldTable();
            GLOBAL.dwParams.tableName = "";
            GLOBAL.dwParams.sourceProtocol = "";
            GLOBAL.dwParams.sourceFirm = "";
            GLOBAL.dwParams.sourceSystem = "";
            GLOBAL.dwTableInfo = {};

            // 20200909 还需要查询数据来源的相关信息
            getResourceTableDataUrl(dataId,tableId);
        } else {
            toastr.error("从数据仓库中获取到的信息为空");
        }
    } else if (executeMethod.toLowerCase() === "approvalinfo") {
        //  表示是从审批流程中
        //  从里面获取传过来的参数  如果是审批中，则只需要展示不能修改和编辑  如果是拒绝的，则
        var dataId = getQueryParam("dataId"); //审批流程中的id数据
        var moduleId = getQueryParam("moduleId");     // createTable:表示在数据仓库中建表  standardTable：标准表
        if (checkIsNull(dataId)) {
            toastr.error("参数dataId不能为空");
            return;
        }
        if (checkIsNull(moduleId)) {
            toastr.error("参数moduleId不能为空");
            return;
        }
        GLOBAL.turnIndex = getQueryParam("turnIndex");
        $.ajax({
            type: "get",
            data: {
                "approvalId": dataId
            },
            url: "getResourceManageByApprovalId",
            calculable: false,
            async: false,
            success: function (result) {
                if (result.status === 1) {
                    //  获取数据  然后回填数据
                    var tableData = result.data;
                    if (moduleId.toLowerCase() === "standardtable") {
                        // 标准表的数据信息
                        var objectPojoTable = tableData.objectPojoTable;
                        // 标准表的 所有字段信息
                        var objectFieldList = tableData.objectFieldList;
                        //  标准表的 所有来源关系
                        var sourceRelationShipList = tableData.sourceRelationShipList;
                        // 审批流程id
                        createApprovalId = tableData.approvalId;
                        // 分级分类id
                        organizationClassifyId = tableData.objectPojoTable.classIds;
                        //  数据来源分类的id
                        sourceClassifyId = tableData.objectPojoTable.sourceClassIds;
                        // 状态 是否为 1:审批中;2:退回
                        var status = tableData.status;
                        if (status === "1") {
                            $("#editResource").css("display", "none");
                        } else if (status === "2") {
                            $("#editResource").css("display", "block");
                        }
                        column_table_list = objectFieldList;
                        source_relationship_list = sourceRelationShipList;
                        $("#resourceManageObjectField").bootstrapTable('load', column_table_list);
                        addTotalAndtitle('#resourceManageObjectFieldTab', column_table_list.length);
                        $('#sourceRelationShip').bootstrapTable('load', source_relationship_list);
                        // 回填数据信息
                        $("#sourceId").text(objectPojoTable.sourceId);  // 源表ID的数据信息
                        $("#dataSourceName").text(objectPojoTable.dataSourceName);//数据名
                        $("#sourceFirm").text(objectPojoTable.ownerFactory);     //协议厂商默认为全部
                        $("#objectName").val(objectPojoTable.codeTextTd);  //所属应用系统
                        $("#objectName").text(objectPojoTable.codeTextCh);
                        $("#tableId").text(objectPojoTable.tableId);
                        $("#objectMemo").text(objectPojoTable.objectMemo);
                        $("#organizationClassify").text(objectPojoTable.organizationClassify);
                        $("#sourceClassify").text(objectPojoTable.sourceClassify);
                        $("#storageTableStatus").text(objectPojoTable.storageTableStatus);
                        // $("#storageMode").text(objectPojoTable.storageDataMode);
                        $("#realTableName").text(objectPojoTable.realTablename);
                        // 新增 更新表类型
                        if (objectPojoTable.isActiveTable === "1") {
                            $("#isActiveTable").text("实时表");
                        } else if (result.isActiveTable === "0") {
                            $("#isActiveTable").text("批量表");
                        } else {
                            $("#isActiveTable").text("");
                        }
                        // 新增最新修改人等信息
                        if(checkIsNull(objectPojoTable.createTime)){
                          $("#createTime").text("");
                        }else{
                          $("#createTime").text(objectPojoTable.createTime);
                        }
                        if(checkIsNull(objectPojoTable.updateTime)){
                          $("#updateTime").text("");
                        }else{
                          $("#updateTime").text(objectPojoTable.updateTime);
                        }
                        if(checkIsNull(objectPojoTable.creator)){
                          $("#creator").text("");
                        }else{
                          $("#creator").text(objectPojoTable.creator);
                        }
                        if(checkIsNull(objectPojoTable.updater)){
                          $("#updater").text("");
                        }else{
                          $("#updater").text(objectPojoTable.updater);
                        }
                        // 还需要获取来源关系的相关内容
                        try{
                          updateHtmlData(tableData.publicDataInfo);
                        }catch (e) {
                          console.log("回填数据来源信息报错"+e);
                        }
                    } else if (moduleId.toLowerCase() === "createtable") {
                        $("#editResource").css("display", "none");
                        // 获取 数据中的tableId
                        var tableId = tableData.tableId;
                        // 审批流程id
                        createApprovalId = tableData.approvalId;
                        // creatObjectDetail(tableId, "");
                        // // 查询字段定义的相关信息
                        // $("#resourceManageObjectField").bootstrapTable('removeAll');
                        // updateObjectField(tableId);
                        // // 查询来源关系
                        // $("#sourceRelationShip").bootstrapTable('removeAll');
                        // sourceRelationShipDataGet(tableId);
                        // 20210201 数据来源信息等内容
                        var treeNode = new Object();
                        treeNode.id = tableId;
                        treeNode.name = "";
                        treeNode.tableNodeFlag = true;
                        zTreeOnClick(null, null, treeNode)

                        // 隐藏按钮
                        // $("#addOrUpdateObjectField").css("display","none");
                        // $("#UpdateObjectField").css("display","none");
                        // $("#deleteObjectField").css("display","none");
                        // $("#addColumn").css("display","none");
                        // $("#dropdownMenu1").css("display","none");
                        $("#dropdownMenu1").attr("disabled", "disabled");
                        // $("#createTable").css("float","right");
                        // $("#createTable").css("margin-right","75px");
                        editFlag = false;
                    } else {
                        $("#editResource").css("display", "none");
                        toastr.error("moduleId值为：" + moduleId + "值填写错误")
                    }
                    $(".headerTool").css("display", "none");
                    $(".treeDiv").css("display", "none");
                    $(".TableDiv").css("margin-left", "10px");
                    $(".TableDiv").css("width", "calc( 100% - 20px )");
                } else {
                    $(".headerTool").css("display", "none");
                    $(".treeDiv").css("display", "none");
                    $(".TableDiv").css("margin-left", "10px");
                    $(".TableDiv").css("width", "calc( 100% - 20px )");
                    $("#editResource").css("display", "none");
                    toastr.error("获取审批流程中对应的数据报错");
                    return;
                }
            },
            error: function (result) {
                toastr.error("后端程序未启动");
            }
        });
    }else if (executeMethod.toLowerCase() === "query"){
        // 这个是正常的查询按钮，需要有参数 tableId
      var tableId = getQueryParam("tableId");
      if(checkIsNull(tableId)){
         toastr.error("参数tableId为空，请加上该值");
         return;
      }
      var treeNode = new Object();
      treeNode.id = tableId;
      treeNode.name = "";
      treeNode.tableNodeFlag = true;
      zTreeOnClick(null, null, treeNode)
      return;
    }
}

/**
 *  需要增加获取数据接入的相关信息
 *  接入服务方
 * @param dataId
 * @param tableId
 */
function getResourceTableDataUrl(dataId,tableId) {
// 20200909 还需要查询数据来源的相关信息
    $.ajax({
        type: "get",
        data: {
            "dataId": dataId,
            "tableName": tableId
        },
        url: "getResourceTableDataUrl",
        calculable: false,
        async: false,
        success: function (result) {
            if (result.status === 1) {
                var data = result.result;
                updateHtmlData(data);
            } else {
                // $("#SJZYLYLX").text("");
                // $("#YYYXTMC").text("");
                // $("#YYYXTMM").text("");
                // $("#YYYXTJSGS").text("");
                $("#SOURCEID_SOURCE").text("");
                $("#SJZYSQDWMC").text("");
                $("#SJZYSQDW_SQDWDM").text("");
                $("#SQDWLXR").text("");
                $("#SQDWLXDH").text("");
                $("#SJHQFS").text("");
                $("#SJHQFS").val("");
                $("#SJZYWZ").text("");
                $("#SJZYWZ").val("");
                $("#SJZYCCFZX").text("");
                $("#SJZYGLMC").text("");
                $("#SJZYGLDW_GAJGJGDM").text("");
                /** 20210126 新增数据接入的信息**/
                $("#JRFWF").text("");
                $("#TGJRFS").text("");
                $("#JRFWR").text("");
                $("#JRFWLXDH").text("");
                $("#SJJRSM").text("");
                $("#GSMS").text("");
                $("#YYYXTGLDW").text("");
                $("#SJZYGXZQ").text("");
                $("#avgStore").text("");
                $("#dataStoreScale").text("");
                $("#dataNumberScale").text("");
                $("#updateType").text("");
                $("#updateCycle").text("");
                $("#avgRecord").text("");
                toastr.error("获取数据仓库中数据来源的相关信息报错，回填页面失败");
            }
        },
        error: function (result) {
            toastr.error("标准管理后端程序未启动");
        }
    });
}



/**
 * 数据定义的新页面 当进入这个页面之后，能自己修改相关的数据
 */
function editPage() {
    // 之后可以修改数据来源信息的相关内容
    // 数据来源分类的页面信息  style="width: 100%;"
    // $("#SJZYLYLX").append("<div><div class=\"layui-input-block\" style='margin-left: 0px ; width: 100%;'>\n" +
    //   "<input type=\"text\" name=\"SJZYLYLX_input\"  id=\"SJZYLYLX_input\" class=\"layui-input form-control hiddenBeyond\" " +
    //   " autocomplete=\"off\"   style=\"width: 100%;\"/>\n" +
    //   "</div></div>");
    // 源应用系统名称
    // $("#YYYXTMC").append("<select type=\"text\" name=\"YYYXTMC_select\" class=\"form-control\" style=\"float: left;\" " +
    //   "id=\"YYYXTMC_select\" onchange=\"applicationSystemchange()\" ></select>");
    // 获取源应用系统名称的相关信息

    // 源应用系统编号
    // $("#YYYXTMM").append("<input class=\"form-control\" style=\"width: 100%;\" name=\"YYYXTMM_input\"   id=\"YYYXTMM_input\"  disabled />");

    // 源应用系统建设公司
    // $("#YYYXTJSGS").append("<select class=\"form-control\" name=\"levelOne\"   id=\"YYYXTJSGS_select\" lay-search><option  >全部</option><option >普天</option>\n" +
    //   "<option >汇智</option><option >三所</option><option >烽火</option><option >三汇</option><option >锐安</option>\n" +
    //   "<option >部中心</option><option >部中心mq</option><option >海康</option></select>");

    // 来源协议编码 需要通过规则
    $("#SOURCEID_SOURCE").append("<input type=\"text\" name=\"SOURCEID_SOURCE_input\" class=\"form-control\" style=\"width: 100%; " +
        "float: left;\" id=\"SOURCEID_SOURCE_input\" autocomplete=\"off\"  value='GA_SOURCE_' />");

    // 数据资源事权单位名称
    // $("#SJZYSQDWMC").append("<div><div class=\"layui-input-block\" style='margin-left: 0px'>\n" +
    //   "<input type=\"text\" name=\"SJZYGLMC_input\"  id=\"SJZYSQDWMC_input\" class=\"layui-input form-control hiddenBeyond\" " +
    //   "style=\"width: 100%;float: left;\" autocomplete=\"off\"   />\n" +
    //   "</div></div>");
    $("#SJZYSQDWMC").append("<input type=\"text\" name=\"SJZYSQDWMC_input\" class=\"form-control\" style=\"width: 100%; " +
        "float: left;\" id=\"SJZYSQDWMC_input\" autocomplete=\"off\"  />");

    // 事权单位机构代码  机构代码是通过选择单位名称获取 选择了什么单位名称自动获取单位机构代码
    $("#SJZYSQDW_SQDWDM").append("<input type=\"text\" name=\"SJZYSQDW_SQDWDM_input\" class=\"form-control\" id=\"SJZYSQDW_SQDWDM_input\"" +
        " autocomplete=\"off\"  style=\"width: 100%;float: left;\"  />");

    // 事权单位联系电话
    $("#SQDWLXDH").append("<input type=\"text\" name=\"SQDWLXDH_input\" class=\"form-control\" id=\"SQDWLXDH_input\"" +
        " autocomplete=\"off\"  style=\"width: 100%;float: left;\" />");

    // 数据获取方式
    $("#SJHQFS").append("<select class=\"form-control\"  name=\"SJHQFS_select\" id=\"SJHQFS_select\">\n" +
        "<option value=\"\"></option><option value=\"01\">侦控</option><option value=\"02\">管控</option>" +
        "<option value=\"03\">管理</option>\n" +
        "<option value=\"04\">公开</option></select>");

    // 数据资源位置
    $("#SJZYWZ").append("<select class=\"form-control\"  name=\"SJZYWZ_select\" id=\"SJZYWZ_select\">\n" +
        "<option value=\"\"></option></option><option value=\"1\">部</option><option value=\"2\">省</option>" +
        "<option value=\"3\">市</option>\n" +
        "<option value=\"4\">网站</option></select>");

    // 数据资源存储中心
    $("#SJZYCCFZX").append("<input class=\"form-control\" style=\"width: 100%;\" name=\"SJZYCCFZX_input\"" +
        " id=\"SJZYCCFZX_input\" />");

    // 资源管理单位名称
    // $("#SJZYGLMC").append("<div class=\"layui-input-block\" style='margin-left: 0px'>\n" +
    //   "<input type=\"text\" name=\"SJZYGLMC_input\"  id=\"SJZYGLMC_input\" class=\"layui-input form-control hiddenBeyond\" " +
    //   "style=\"width: 100%;float: left;\" autocomplete=\"off\"   />\n" +
    //   "</div>");
    $("#SJZYGLMC").append("<input class=\"form-control\" style=\"width: 100%;\" name=\"SJZYGLMC_input\"" +
        " id=\"SJZYGLMC_input\" />");

    // 资源管理单位机构代码 机构代码是通过选择单位名称获取 选择了什么单位名称自动获取单位机构代码
    $("#SJZYGLDW_GAJGJGDM").append("<input class=\"form-control\" style=\"width: 100%;\" name=\"SJZYGLDW_GAJGJGDM_input\"" +
        " id=\"SJZYGLDW_GAJGJGDM_input\" />");

    // 事权单位联系人
    $("#SQDWLXR").append("<input class=\"form-control\" style=\"width: 100%;\" name=\"SQDWLXR_input\"" +
        " id=\"SQDWLXR_input\"  />")

    $("#SOURCEID_SOURCE_input").val("GA_SOURCE_XXXX_"+getQueryParam("localAdministrativeCode")+"_80001");

    //  获取源应用系统名称的select数据
    // $.ajax({
    //     type: "get",
    //     url:  "getSysToSelect",
    //     contentType: 'application/json',
    //     dataType: 'json',
    //     calculable: false,
    //     async: false,
    //     success: function (result) {
    //         if (result.status === 1 && result.data != null) {
    //             result.data.forEach(function (oneData) {
    //                 $("#YYYXTMC_select").append("<option value=" + oneData.value + ">" + oneData.label + "</option>");
    //             });
    //         } else {
    //             toastr.error(result.msg);
    //         }
    //         applicationSystemchange();
    //     },
    //     error: function (result) {
    //         toastr.error("从sys表中获取数据报错");
    //     }
    // });
    // 需要初始化数据源来源下拉框
    // $.ajax({
    //     type: "get",
    //     url:  "getAllClassifyLayui",
    //     contentType: 'application/json',
    //     dataType: 'json',
    //     calculable: false,
    //     async: false,
    //     data: {
    //         "mainClassifyCh": "数据来源分类"
    //     },
    //     success: function (result) {
    //         if (result.status === 1) {
    //             layuiInputBuild3("SJZYLYLX_input", result.result, [],"click");
    //         } else {
    //             toastr.error(result.msg);
    //         }
    //     },
    //     error: function (result) {
    //         toastr.error("获取分级分类的信息报错");
    //         return;
    //     }
    // });

    // // 获取管理单位的相关信息
    // // 一共有2个
    // $.ajax({
    //     type: "get",
    //     url:  "getResourceManagementUnitUrl",
    //     contentType: 'application/json',
    //     dataType: 'json',
    //     calculable: false,
    //     async: false,
    //     success: function (result) {
    //         if (result.status === 1) {
    //             layuiInputBuild3("SJZYSQDWMC_input", result.result, [],"click");
    //             layuiInputBuild3("SJZYGLMC_input", result.result, [],"click");
    //         } else {
    //             toastr.error(result.msg);
    //         }
    //     },
    //     error: function (result) {
    //         toastr.error("获取分级分类的信息报错");
    //         return;
    //     }
    // });

}

/**
 * 数据来源系统中的 源应用系统select的选择方法
 */
function applicationSystemchange() {
    // $("#dataBaseNameMode option:selected").val()
    $("#YYYXTMM_input").val($("#YYYXTMC_select option:selected").val());

}

function initTableParams() {
    //  传递过来的协议编码也要回填给 sourceId
    $("#sourceId").text(GLOBAL.dwParams.sourceProtocol);
    $("#tableId").text(GLOBAL.dwParams.sourceProtocol);//来源协议  -- >表ID
    $("#dataSourceName").text(GLOBAL.dwParams.tableName);//数据名
    $("#sourceFirm").text(GLOBAL.dwParams.sourceFirm);//来源单位
    $("#objectName").val(GLOBAL.dwParams.sourceSystem);//系统代码
    $("#objectName").text(GLOBAL.dwParams.belongSystemCode);
    $("#editResource").click();//点击编辑按钮
    // 查询是否原有的来源关系
    // 查询来源关系
    $("#sourceRelationShip").bootstrapTable('removeAll');
    sourceRelationShipDataGet(GLOBAL.dwParams.sourceProtocol);
    // $("#saveResource").click();//点击保存按钮
}

function addRelationShipRecord() {
    $("#addSourceRelationClick").click();//点击添加来源按钮
    addSourceRelationPageButton();//在关系表中添加一条记录
}


//刷新按钮 重新获取
function treeRefresh() {
    $.ajax({
        type: "get",
        url: "treeRefresh",
        calculable: false,
        async: false,
        success: function (result) {
            if (result.status == 0) {
                toastr.error("刷新失败：" + result.msg);
            } else {
                toastr.info("刷新成功：" + result.msg);
            }
            createTree();
        },
        error: function (result) {
        }
    });
}



// 切换tab页面 修改active值，并重新获取tree
function switchTab(tabTypeCode) {
    dataTypeCode = tabTypeCode;
    var index = 1;
    $(".dataTypeTab td").each(function () {
        $(this).removeClass("active");
        if (index == tabTypeCode) {
            $(this).addClass("active")
        }
        ;
        index = index + 1;
    });
    createTree();
    // 20201128 新增查询汇总表信息的参数
    switchTabQuerySummary()
}

/**
 * 创建已建表的相关信息
 */
function createdTableTab() {
    $("#createdTable").bootstrapTable({
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
        resizable:true,
        columns: [
            {field: 'tableBase', title: "平台", valign: "middle", align: "center", colspan: 1, rowspan: 1},
            {field: 'tableProject', title: "项目名", valign: "middle", align: "center", colspan: 1, rowspan: 1},
            {field: 'tableName', title: "表名", valign: "middle", align: "center", colspan: 1, rowspan: 1,formatter:paramsMatter},
            {field: 'importFlag', title: "是否自动入库", valign: "middle", align: "center", colspan: 1, rowspan: 1,
              formatter: function (value, row, index) {
                var  tableBase = row.tableBase;
                var tableNameEn = row.tableNameEn;
                if(1 === value ){
                  return [
                    '<select class=\"form-control  \"  name=\"importFlagSelect\"  onchange=\"importFlagChange(this.value,\''+tableBase+'\',\''+tableNameEn+'\')\">' +
                    '<option value=1 selected>是</option><option value=0>否</option></select>'
                  ].join('');
                }else{
                  return [
                    '<select class=\"form-control \"  name=\"importFlagSelect\"   onchange=\"importFlagChange(this.value,\''+tableBase+'\',\''+tableNameEn+'\')\">' +
                    '<option value=1 >是</option><option value=0 selected >否</option></select>'
                  ].join('');
                }
              }
            },
            {field: 'createTime', title: "创建时间", valign: "middle", align: "center", colspan: 1, rowspan: 1},
            {field: 'createUser', title: "创建人", valign: "middle", align: "center", colspan: 1, rowspan: 1},
            {
                title: "资产详情", valign: "middle", align: "center", colspan: 1, rowspan: 1,
                formatter: function (value, row, index) {
                    //弹出模态框
                    return [
                        '<i class="lookCreatedTable" style="color:var(--main-color);cursor: pointer;font-style: normal" >查看</i>'
                    ].join('');
                },
                events: lookCreatedTable
            },
            {
                title: "字段比对", valign: "middle", align: "center", colspan: 1, rowspan: 1,
                formatter: function (value, row, index) {
                    //弹出模态框
                    return [
                        '<i class="fieldComparison" style="color:var(--main-color);cursor: pointer;font-style: normal" >比对</i>'
                    ].join('');
                },
                events: lookCreatedTable
            }
        ],
        onPageChange: function () {
            addTotalAndtitle('#createdTableTab', created_table_list.length);
        }
    });
    addTotalAndtitle('#createdTableTab', created_table_list.length);
}



// 来源关系的表格
//构造表格
function sourceRelationShipTable() {
    $("#sourceRelationShip").bootstrapTable({
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
        resizable:true,
        onClickRow: function (row, $element, field) {
            // 当来源表点击一行之后，重新刷新字段定义表，获取对应的来源关系
            // @TODO 不想这么做
            refreshColumnTableByRelation(row)
        },
        clickToSelect: true,
        columns: [
            {checkbox: true},
            {field: 'dataSourceName', title: "来源数据名", valign: "middle", align: "center", colspan: 1, rowspan: 1},
            {field: 'realTableName', title: "真实表名", valign: "middle", align: "center", colspan: 1, rowspan: 1, formatter:paramsMatter},
            {field: 'sourceSystem', title: "来源数据协议", valign: "middle", align: "center", colspan: 1, rowspan: 1, formatter:paramsMatter},
            {field: 'sourceProtocol', title: "来源系统代码", valign: "middle", align: "center", colspan: 1, rowspan: 1},
            {field: 'sourceProtocolCh', title: "来源系统名", valign: "middle", align: "center", colspan: 1, rowspan: 1},
            {field: 'sourceFirm', title: "来源厂商", valign: "middle", align: "center", colspan: 1, rowspan: 1},
            {field: 'storageTableStatus', title: "存储表状态", valign: "middle", align: "center", colspan: 1, rowspan: 1},
            {
                title: "导入", valign: "middle", align: "center", colspan: 1, rowspan: 1,
                formatter: function (value, row, index) {
                    //弹出模态框

                    return [
                        "<a href='javascript:void(0);'>" +
                        '<i class="glyphicon glyphicon-import import"></i>' +
                        '</a>'
                    ].join('');
                },
                events: showImportModel
            }
        ],
        onPageChange: function () {
            addTotalAndtitle('#sourceRelationShipTab', source_relationship_list.length);
        }
    });
    addTotalAndtitle('#sourceRelationShipTab', source_relationship_list.length);
};

var lookCreatedTable = {
    'click .lookCreatedTable': function (event, value, row, index) {
        // 获取到资产详情页面的url，然后再跳转到该页面
        $.ajax({
            type: "get",
            url: "getOrganizationDetailUrl",
            data: row,
            contentType: 'application/json',
            dataType: 'json',
            calculable: false,
            async: false,
            success: function (result) {
                if(result.status === 1){
                    window.open(result.data);
                }else{
                    toastr.error(result.msg);
                }
            },
            error: function (result) {
                toastr.error("获取跳转到资产详情页面的url报错");
            }
        });
    },
    'click .fieldComparison':function (event, value, row, index) {
        // 字段比对的点击事件
        // 查询出这个表在大平台上的字段顺序
        if(editFlag){
            toastr.error("请先保存整个标准信息");
            return;
        }
        addMask();
        try{
            // 目前 datahub 不能进行新增字段的操作
            if("datahub" === row.tableBase.toLowerCase()
                || "hbase" === row.tableBase.toLowerCase() ){
                $.unblockUI();
                toastr.info(row.tableBase+"类型的表不能新增字段");
                return;
            }
            var columnDataList = getColumnTypeByBase(row.tableBase);
            leftStandardTableInit(columnDataList);
            rightCreateTableInit();
            getDateComparision(row);
        }catch (errMsg) {
            $.unblockUI();
            toastr.error(errMsg);
        }
    },
    'click .deleteTable':function (event, value, row, index) {
        // 删除表的相关事件
        console.log(row);

    }
}

/**
 * 导入功能
 */
var showImportModel = {

    'click .import': function (event, value, row, index) {
        if (!editFlag) {
            $("#statusCheckModal").modal("show");
            return;
        }
        //显示模态框
        $("#TableDiv").removeClass("tabScrollDiv");
        $('#importSrcFieldModal').modal('show');
        console.log(row);

        if (!GLOBAL.isFlow) {//如果不是流程中来,需要设置一下
            //获取参数
            GLOBAL.dwParams.tableName = row.realTableName;
            GLOBAL.dwParams.sourceProtocol = row.sourceSystem;
            GLOBAL.dwParams.sourceFirm = row.sourceFirm;
            GLOBAL.dwParams.sourceSystem = row.sourceProtocol;
            GLOBAL.dwTableInfo = {};

        }
        GLOBAL.tableID = row.sourceSystem;
        initSourceFieldTable();

    }
};

function closeSrcFieldModal() {
  $("#TableDiv").addClass("tabScrollDiv");
  $('#importSrcFieldModal').modal('hide');

}

function initSourceFieldTable() {
    var dataLeng = 0;
    $("#sourceFieldTable").bootstrapTable("destroy").bootstrapTable({
        type: 'POST',
        url: "initSourceFieldTable",
        queryParams: {
            "sourceProtocol": GLOBAL.dwParams.sourceProtocol,
            "tableName": GLOBAL.dwParams.tableName,
            "sourceFirm": GLOBAL.dwParams.sourceFirm,
            "sourceSystem": GLOBAL.dwParams.sourceSystem,
            "dwTableInfo": GLOBAL.dwTableInfo,
            "tableID": GLOBAL.tableID
        },
        contentType: "application/json,charset=utf-8",
        sidePagination: "client", // 分页方式：client客户端分页，server服务端分页
        pagination: true,// 启动分页
        paginationPreText: '<',
        paginationNextText: '>',
        pageSize: 10,  //每页显示的记录条数
        pageNumber: 1, //当前显示第几页
        pageList: [10, 20, 30, 50],
        paginationShowPageGo: true,
        showJumpto: true,
        smartDisplay: false,
        paginationLoop: false,
        search: false,// 是否启用查询,
        showRefresh: false, // 是否显示刷新按钮
        showColumns: false, // 是否显示所有的列
        minimumCountColumns: 2, // 最少允许的列数
        clickToSelect: true,
        uniqueId: "id",
        height: caculateTableHeight() + 260,
        onCheckAll: function(rowsAfter){
          var allData = $('#sourceFieldTable').bootstrapTable('getData');
          if(rowsAfter !== undefined && rowsAfter.length !== allData.length){
            $("#sourceFieldTable").bootstrapTable('togglePagination').bootstrapTable('checkAll').bootstrapTable('togglePagination');
          }
        },
        onUncheckAll: function(rowsAfter){
          if(rowsAfter !== undefined && rowsAfter.length !== 0){
            $("#sourceFieldTable").bootstrapTable('togglePagination').bootstrapTable('uncheckAll').bootstrapTable('togglePagination');
          }
        },
        //singleSelect : true,
        columns: [
            {checkbox: true},
            {field: 'fieldName', title: "源字段名", valign: "middle", align: "center", colspan: 1, rowspan: 1},
            {field: 'fieldType', title: "字段类型", valign: "middle", align: "center", colspan: 1, rowspan: 1},
            {field: 'fieldLength', title: "字段长度", valign: "middle", align: "center", colspan: 1, rowspan: 1},
            {field: 'isPrimarykey', title: "是否是索引", valign: "middle", align: "center", colspan: 1, rowspan: 1},
            {field: 'isNonnull', title: "是否为必填", valign: "middle", align: "center", colspan: 1, rowspan: 1},
            {field: 'fieldDescription', title: "字段描述信息", valign: "middle", align: "center", colspan: 1, rowspan: 1},
            {field: 'fieldCode', title: "元素编码", valign: "middle", align: "center", colspan: 1, rowspan: 1},
        ],
        onLoadSuccess: function (data) {
            dataLeng = data.length;
            addTotalAndtitle('#importSrcFieldModal', dataLeng);
           // var scrollbar = new PerfectScrollbar("#importSrcFieldModal .fixed-table-body", {});

        },
        onPageChange: function () {
            addTotalAndtitle('#importSrcFieldModal', dataLeng);
        }
    });

}


function copySQLContent() {
    var cp = new Clipboard("#copyBtn");
    cp.on('success', function (e) {
        bootbox.alert("复制成功！");
    });
    cp.on('error', function (e) {
        bootbox.alert("复制失败！");
    });
}

//建表
function createTable() {
    if (editFlag) {
        toastr.error("只有点击保存之后才能建表");
        return;
    }
    var realTableName = $("#realTableName").text();
    var tableId = $("#tableId").text();
    var objectId = $("#objectIdManage").text();
    var isActiveTable = $("#isActiveTable").val();
    var dataSourceName = $("#dataSourceName").text();
    var appendOption = "";
    columnInfo = column_table_list;
    if (columnInfo.length == 0) {
        toastr.error("无字段信息");
        return;
    }
    if (realTableName == null || realTableName === "" || tableId == null || tableId === "") {
        toastr.error("真实表名或表协议未填");
        return;
    }
    if(dataSourceName==null||dataSourceName==""){
        toastr.error("数据名未填");
        return;
    }
    if(isActiveTable == null || isActiveTable ==""){
        toastr.error("更新表类型未填");
        return;
    }
    if (objectId == null || objectId == "") {
        toastr.error("请先保存该表信息");
        return;
    }
    $("#tableName").val(realTableName);
    $("#topicName").val(realTableName);
    $.ajax({
        type: "get",
        url: "getTableInfo",
        calculable: false,
        async: false,
        success: function (result) {
            if (result == null || result.length == 0) {
                toastr.error("未获取到数据源信息");
                return;
            }
            createTableObj = JSON.parse(result);
            $("#dataCentName").empty();
            for (var i = 0; i < createTableObj.length; i++) {
                if (createTableObj[i].iconSkin === "newcenter") {
                    appendOption += "<option value='" + createTableObj[i].id + "'";
                    if (createTableObj[i].id === GLOBAL.dwParams.centerId) {
                        appendOption += "selected = 'selected'";
                    }
                    var value = createTableObj[i].name.split("(")[0];
                    appendOption += ">" + value + "</option>";
                }
            }
            // $("#dataCentName").append("<option value='"+createTableObj[i].id+"'>"+createTableObj[i].name+"</option>")
            $("#dataCentName").append(appendOption);
            $("#odpsPartition").val("DT");
            $('#createTableContent').modal('show');
            $('.selectpicker').selectpicker('refresh');
            $('.selectpicker').selectpicker('render');
        },
        error: function (result) {
            toastr.error("数据源信息获取失败");
            return;
        }
    });

    // // 获取到已经创建的表信息
    // $.ajax({
    //     type: "get",
    //     data: {
    //         "tableName": realTableName
    //     },
    //     url: "dbManager/getAllStandardTableCreatedList",
    //     calculable: false,
    //     async: false,
    //     success: function (result) {
    //         console.log(result);
    //         if (result.status === 1) {
    //             var data = result.data;
    //             var message = "";
    //             data.forEach(function (obj) {
    //                 message = message + obj.tableBase + "  " + obj.tableProject + "." + obj.tableName + "\n"
    //             });
    //             if(message === ""){
    //                 $("#createdTable").text("未建表");
    //             }else{
    //                 $("#createdTable").text(message);
    //             }
    //         } else {
    //             toastr.error(result.msg);
    //         }
    //
    //     },
    //     error: function (result) {
    //         toastr.error("获取已经创建表信息报错");
    //     }
    // });

    // 获取大数据平台的类型
    $.ajax({
        type: "get",
        url: "dbManager/getDataPlatFormType",
        calculable: false,
        async: false,
        success: function (result) {
            console.log(result);
            $("#dataResourceType").empty();
            if (result.status === 1) {
                $("#dataResourceType").append(result.data);
                loadDataResourceName();
                $('.selectpicker').selectpicker('refresh');
                $('.selectpicker').selectpicker('render');
                loadProjectName();
                $('.selectpicker').selectpicker('refresh');
                $('.selectpicker').selectpicker('render');
            }else{
                toastr.error(result.msg);
            }
        },
        error: function (result) {
            toastr.error("获取配置文件中的大数据类型报错");
        }
    });

}

function loadDataResourceName() {
    var dataCenterNum = 0;
    var dataCentNameVal = $("#dataCentName option:selected").val();
    var dataResourceTypeVal = $("#dataResourceType option:selected").val();
    if (dataResourceTypeVal === "ODPS" || dataResourceTypeVal === "ADS" || dataResourceTypeVal === "HIVE") {
        // var appendOption = "<option selected=\"selected\"  value=\"selected\">请选择</option>";
        var appendOption = "";
        var getSelectRows = $('#resourceManageObjectField').bootstrapTable('getSelections');
        $("#partitionFirst").empty();
        $("#partitionSecond").empty();
        $("#partitionFirstNum").empty();
        $("#partitionSecondNum").empty();
        $("#partitionFirst").attr("readonly", true);
        $("#partitionSecond").attr("readonly", true);
        $("#partitionFirstNum").attr("readonly", true);
        $("#partitionSecondNum").attr("readonly", true);
        // $("#partitionFirstNumColor").css("color","grey");
        if (dataCentNameVal != "selected" && dataResourceTypeVal != "selected") {
            if (dataResourceTypeVal == "ODPS") {
                for (var i = 0; i < createTableObj.length; i++) {
                    if (createTableObj[i].pId === dataCentNameVal && createTableObj[i].name.split('(')[0].toUpperCase() === "ODPS") {
                        for (var j = 0; j < createTableObj.length; j++) {
                            if (createTableObj[j].pId === createTableObj[i].id) {
                                appendOption += "<option value='" + createTableObj[j].id + "'>" + createTableObj[j].name + "</option>";
                                dataCenterNum++;
                            }
                        }
                        break;
                    }
                }
                ;
                $(".odpsCreateTableInfo").css("display", "block");
                $(".adsCreateTableInfo").css("display", "none");
                // var list = new Array();
                // for (var i = 0; i < columnInfo.length; i++) {
                //     if (columnInfo[i].odpsPattition) {
                //         var obj = new Object();
                //         obj.labelCode = columnInfo[i].columnName;
                //         obj.labelName = columnInfo[i].columnName;
                //         list.push(obj);
                //     }
                // }
                // getmultipleSelect("odpsPartition", list)
            } else if (dataResourceTypeVal == "ADS") {
                for (var i = 0; i < createTableObj.length; i++) {
                    if (createTableObj[i].pId === dataCentNameVal && createTableObj[i].name.split('(')[0].toUpperCase() === "ADS") {
                        for (var j = 0; j < createTableObj.length; j++) {
                            if (createTableObj[j].pId === createTableObj[i].id) {
                                appendOption += "<option value='" + createTableObj[j].id + "'>" + createTableObj[j].name + "</option>";
                                dataCenterNum++;
                            }
                        }
                        break;
                    }
                }
                ;
                $(".odpsCreateTableInfo").css("display", "none");
                $(".adsCreateTableInfo").css("display", "block");
                $("#partitionFirst").attr("readonly", false);
                $("#partitionFirst").append("<option selected=\"selected\"  value=\"selected\">请选择</option>");
                for (var i = 0; i < columnInfo.length; i++) {
                    if (columnInfo[i].partitionRecno === 1 && columnInfo[i].pkRecno >= 1) {
                        $("#partitionFirst").append("<option value='" + columnInfo[i].columnName + "'>" + columnInfo[i].columnName + "</option>");
                    }
                }
            } else if (dataResourceTypeVal === "HIVE") {
                // HIVE 默认为 dt
                for (var i = 0; i < createTableObj.length; i++) {
                    if (createTableObj[i].pId === dataCentNameVal && createTableObj[i].name.split('(')[0].toUpperCase() === "HIVE") {
                        for (var j = 0; j < createTableObj.length; j++) {
                            if (createTableObj[j].pId === createTableObj[i].id) {
                                appendOption += "<option value='" + createTableObj[j].id + "'>" + createTableObj[j].name + "</option>";
                                dataCenterNum++;
                            }
                        }
                        break;
                    }
                }
                ;
                $("#partitionFirst").append("<option selected=\"selected\"  value=\"dt\">dt</option>")
            }
        }
        $("#dataResourceName").empty();
        $("#projectName").empty();
        $("#dataResourceName").append(appendOption);
        // $("#projectName").append(appendOption);
        $(".aliDataBase").css("display", "block");
        $(".datahub").css("display", "none");
        $(".prestoDataBase").css("display", "none");
        $(".tableName").css("display", "block");
        $('.selectpicker').selectpicker('refresh');
        $('.selectpicker').selectpicker('render');
        loadProjectName();
        $('.selectpicker').selectpicker('refresh');
        $('.selectpicker').selectpicker('render');
    } else if (dataResourceTypeVal === "HBASE") {


        // HBASE 数据库的类型
        // var appendOption = "<option selected=\"selected\"  value=\"selected\">请选择</option>";
        var appendOption = "";
        if (dataCentNameVal != "selected" && dataResourceTypeVal != "selected") {
            for (var i = 0; i < createTableObj.length; i++) {
                // @TODO 将ORACLE 改成 HBASE 20191226
                if (createTableObj[i].pId === dataCentNameVal && createTableObj[i].name.split('(')[0].toUpperCase() === "HBASE") {
                    for (var j = 0; j < createTableObj.length; j++) {
                        if (createTableObj[j].pId === createTableObj[i].id) {
                            appendOption += "<option value='" + createTableObj[j].id + "'>" + createTableObj[j].name + "</option>";
                            dataCenterNum++;
                        }
                    }
                    break;
                }
            };
        }
        $("#dataResourceName").empty();
        $("#projectName").empty();
        $("#dataResourceName").append(appendOption);
        // $("#projectName").append(appendOption);
        // $("#projectName").append("<option selected=\"selected\"  value=\"selected\">请选择</option>");
        $(".aliDataBase").css("display", "none");
        $(".datahub").css("display", "none");
        $(".prestoDataBase").css("display", "block");
        $(".tableName").css("display", "block");
        $("#lifecycle").removeAttr("readOnly");
        $("#regionType").val(3);
        regionTypeChange();
        $("#esSplitType").val(3);
        esSplitTypeChange();
        $("#esShards").val(2);
        $("#lifecycle").val("");
        $("#prestoMemo").val("");
        $("#prestoMemo").val($("#dataSourceName").html());
        $('.selectpicker').selectpicker('refresh');
        $('.selectpicker').selectpicker('render');
    }else if(dataResourceTypeVal === "DATAHUB"){
        // 创建datahub创建topic的相关信息
        // var appendOption = "<option selected=\"selected\"  value=\"selected\">请选择</option>";
        var appendOption ="";
        if (dataCentNameVal != "selected" && dataResourceTypeVal != "selected") {
            for (var i = 0; i < createTableObj.length; i++) {
                if (createTableObj[i].pId === dataCentNameVal && createTableObj[i].name.split('(')[0].toUpperCase() === "DATAHUB") {
                    for (var j = 0; j < createTableObj.length; j++) {
                        if (createTableObj[j].pId === createTableObj[i].id) {
                            appendOption += "<option value='" + createTableObj[j].id + "'>" + createTableObj[j].name + "</option>";
                            dataCenterNum++;
                        }
                    }
                    break;
                }
            };
        }
        $("#dataResourceName").empty();
        $("#dataResourceName").append(appendOption);
        $("#projectName").empty();
        $(".aliDataBase").css("display", "none");
        $(".prestoDataBase").css("display", "none");
        $(".tableName").css("display", "none");
        $(".datahub").css("display", "block");
        $("#comment").val($("#dataSourceName").text());
        $('.selectpicker').selectpicker('refresh');
        $('.selectpicker').selectpicker('render');
    }

    loadProjectName();
    $('.selectpicker').selectpicker('refresh');
    $('.selectpicker').selectpicker('render');
    // 当数据源类型的选择项发生变化时，建表字段和建表长度发生变化
    columnInfo.forEach(function (obj) {
        obj.createColumnType = "";
        obj.createColumnLen = 0;
    });
    $("#createSqlTextarea").text("");
    if (dataCenterNum === 0) {
        if (dataResourceTypeVal !== undefined) {
            if (dataResourceTypeVal === "selected") {
                toastr.error("在数据中心【" + $("#dataCentName option:selected").text() + "】请选择具体的数据源类型");
            } else {
                toastr.error("数据中心【" + $("#dataCentName option:selected").text() + "】中没有" + dataResourceTypeVal + "类型的数据源");
            }
        }
    }
}

function loadPartitionSecond() {
    $("#partitionSecond").attr("readonly", false);
    $("#partitionSecond").empty();
    var dataResourceTypeVal = $("#dataResourceType option:selected").val();
    var partitionFirstVal = $("#partitionFirst option:selected").val();
    if (partitionFirstVal !== "selected") {
        $("#partitionFirstNum").attr("readonly", false);
    } else {
        $("#partitionFirstNum").attr("readonly", true);
    }
    if (dataResourceTypeVal === "ADS" && partitionFirstVal != "selected") {
        // $("#partitionSecond").append("<option selected=\"selected\"  value=\"selected\">请选择</option>");
        for (var i = 0; i < columnInfo.length; i++) {
            if (columnInfo[i].partitionRecno === 2 && columnInfo[i].columnName.toUpperCase() === "DT"&&columnInfo[i].pkRecno>=1) {
                $("#partitionSecond").append("<option value='" + columnInfo[i].columnName + "'>" + columnInfo[i].columnName + "</option>");
            }
        }
        activePartitionSecondNum();
    }
}

function activePartitionSecondNum() {
    var partitionSecondVal = $("#partitionSecond option:selected").val();
    if (partitionSecondVal !== "selected" && partitionSecondVal !== undefined) {
        $("#partitionSecondNum").attr("readonly", false);
    }else{
        $("#partitionSecondNum").attr("readonly", true);
    }
}

function loadProjectName() {
    $("#projectName").empty();
    // var appendOption = "<option selected=\"selected\"  value=\"selected\">请选择</option>";
    var appendOption =""
    var dataResourceNameVal = $("#dataResourceName option:selected").val();
    if(checkIsNull(dataResourceNameVal)){
       return;
    }
    var booleanval = false;
    var obj = [];
    //  20200312  更改为获取审批通过的项目名 getSchemaApproved
    //  20200418 获取项目名可以不经过审批
    $.ajax({
        type: "get",
        url: "getSchemaApproved",
        calculable: false,
        data: {dataId: dataResourceNameVal},
        async: false,
        success: function (result) {
            if (result === null || result.length === 0) {
                toastr.error("获取到的通过审批的项目名为空，请去数据仓库页面查看");
            } else {
                for (var i = 0; i < result.length; i++) {
                    appendOption += "<option value='" + result[i] + "'>" + result[i] + "</option>"
                }
                $("#projectName").append(appendOption);
                $('.selectpicker').selectpicker('refresh');
                $('.selectpicker').selectpicker('render');
            }
        },
        error: function (result) {
            toastr.error("数据源信息获取失败");
            return;
        }
    });
}

//生成SQL
function createSQL() {
    var getSelectRows = $('#resourceManageObjectField').bootstrapTable('getSelections');
    if (getSelectRows.length <= 0) {
        bootbox.alert("请先选择一条数据!");
        return;
    } else {
        var sql = "<font color='blue'>SELECT</font>";
        var ds = "";
        var tName = $("#realTableName").text();
        var ed = "<font color='blue'>FROM</font> " + tName;
        var columnName = "";
        var fieldChineeName = "";
        for (var i = 0; i < getSelectRows.length; i++) {
            columnName = getSelectRows[i].columnName;
            fieldChineeName = getSelectRows[i].fieldChineeName;
            ds = ds + columnName + " as '" + fieldChineeName + "'";
            if ((getSelectRows.length != 1) && (i != (getSelectRows.length - 1))) {
                ds = ds + ",</br>";
            }
        }
        sql = sql + "</br>" + ds + "</br>" + ed;
        //alert(sql);
        $("#showSQLContent").html(sql);
        $('#showSQL').modal('show');
    }
}


/**
 * 增加字段点击事件
 * @param action
 */
function addOrUpdateObjectField(action, rowData) {
    if (!editFlag) {
        $("#statusCheckModal").modal("show");
        return;
    }
    if (action === 'add') {
        // var objectId = $("#objectIdManage").text();
        // if(checkIsNull(objectId)){
        //   toastr.error("请选择具体的表");
        //   return ;
        // }
        $("#fieldIdAddModel").removeAttr("readonly");
        $("#fieldNameAddModel").removeAttr("readonly");
        $('#headContent').text("增加字段");
        var suggestFieldIdUrl =  "createAddColumnModel?type=fieldId&condition=";
        var suggestcolumnNameUrl = "createAddColumnModel?type=columnName&condition=";
        $("#fieldIdAddModel").bsSuggest("destroy");
        $("#fieldNameAddModel").bsSuggest("destroy");
        inputBlurFunction();
        twoSuggest("fieldIdAddModel", suggestFieldIdUrl);
        threeSuggest("fieldNameAddModel", suggestcolumnNameUrl);
        $("#fieldIdAddModel").attr("style", "width:220px");
        $("#fieldNameAddModel").attr("style", "width:220px");
        $("#fieldChineNameAddModel").attr("readonly", "true");

        $("#fieldTypeAddModel").attr("disabled", "disabled");
        $("#fieldLenAddModel").attr("readonly", "true");
        $("#fieldChineNameAddModel").attr("readonly", "true");
        $("#defaultValueAddModel").attr("readonly", "true");
        $("#partitionRecnoAddModel").find("option:contains('二级分区列')").attr("disabled", true);
        $("#partitionRecnoAddModel").find("option:contains('一级分区列')").attr("disabled", true);
        $("#odpsPattitionAddModel").selectpicker('val', "");
        // 将全局变量设置为 add
        addUpdateColumn = "add";
    } else if (action === 'update') {
        $('#headContent').text("编辑字段");
        //  如果这个是编辑流程 所有的都可以修改
        var getSelectRows = [];
        if (rowData === null || rowData === undefined) {
            getSelectRows = $('#resourceManageObjectField').bootstrapTable('getSelections');
            if (getSelectRows.length <= 0 || getSelectRows.length > 1) {
                toastr.error("请选择一条数据!");
                return;
            }
        } else {
            getSelectRows.push(rowData);
        }
        console.log(getSelectRows[0]);
        $("#fieldIdAddModel").bsSuggest("destroy");
        $("#fieldNameAddModel").bsSuggest("destroy");
        $("#fieldIdAddModel").unbind();
        $("#fieldNameAddModel").unbind();
        var suggestFieldIdUrl = "createAddColumnModel?type=fieldId&condition=";
        var suggestcolumnNameUrl =  "createAddColumnModel?type=columnName&condition=";
        twoSuggest("fieldIdAddModel", suggestFieldIdUrl);
        fourSuggest("fieldNameAddModel", suggestcolumnNameUrl);
        inputBlurFunctionUpdate();
        // 点击编辑按钮后，相关数据回填到每行数据中，具体的字段信息不能被修改
        if(!checkIsNull(getSelectRows[0].fieldId) && getSelectRows[0].fieldId.indexOf("unknown_") !== -1){
          $("#fieldIdAddModel").val("");
        }else{
          $("#fieldIdAddModel").val(getSelectRows[0].fieldId);
        }
        // $("#fieldIdAddModel").val(getSelectRows[0].fieldId);
        $("#fieldNameAddModel").val(getSelectRows[0].fieldName);

        $("#fieldIdAddModel").attr("style", "width:220px");
        $("#fieldNameAddModel").attr("style", "width:220px");

        // 重新定义编辑流程，所有的都可以修改，除了字段英文描述
        $("#fieldIdAddModel").removeAttr("readonly");
        $("#fieldNameAddModel").removeAttr("readonly");
        $("#fieldChineNameAddModel").removeAttr("readonly");
        $("#fieldTypeAddModel").removeAttr("disabled");
        $("#fieldLenAddModel").removeAttr("readonly");
        $("#fieldDescribeAddModel").removeAttr("readonly");
        $("#defaultValueAddModel").removeAttr("readonly");
        $("#columnNameAddModel").val(getSelectRows[0].columnName);
        $("#fieldChineNameAddModel").val(getSelectRows[0].fieldChineeName);
        var fieldTypeNum = getSelectRows[0].fieldType;
        $("#fieldTypeAddModel").selectpicker('val',fieldTypeNum);
        // if(fieldTypeNum === "0"){
        //   $("#fieldTypeAddModel").val("integer");
        // }else if(fieldTypeNum === "1"){
        //   $("#fieldTypeAddModel").val("float");
        // }else if(fieldTypeNum === "2"){
        //   $("#fieldTypeAddModel").val("string");
        // }else if(fieldTypeNum === "3"){
        //   $("#fieldTypeAddModel").val("date");
        // }else if(fieldTypeNum === "4"){
        //   $("#fieldTypeAddModel").val("datetime");
        // }
        $("#fieldLenAddModel").val(getSelectRows[0].fieldLen);
        $("#fieldDescribeAddModel").val(checkIsNull(getSelectRows[0].fieldDescribe)?getSelectRows[0].memo:getSelectRows[0].fieldDescribe);
        $("#defaultValueAddModel").val(getSelectRows[0].defaultValue);
        $("#tableIndexAddModel").selectpicker('val', getSelectRows[0].tableIndex);
        $("#isIndexAddModel").selectpicker('val',getSelectRows[0].isIndex);
        $("#indexTypeAddModel").selectpicker('val', getSelectRows[0].indexType);

        $("#needValueAddModel").selectpicker('val', getSelectRows[0].needValue);
        $("#isContorlAddModel").selectpicker('val', getSelectRows[0].isContorl);

        $("#isQueryAddModel").selectpicker('val', getSelectRows[0].isQuery);
        $("#memoAddModel").val(getSelectRows[0].memo);
        // 该字段是否  属于原始库
        $("#fieldSourceType").selectpicker('val', getSelectRows[0].fieldSourceType);

        // 参加MD5值计算
        if (checkIsNull(getSelectRows[0].md5Index) && !checkIsNumber(getSelectRows[0].md5Index)) {
            $("#md5IndexAddModel").selectpicker('val', "" + getSelectRows[0].md5IndexStatus);
        } else {
            $("#md5IndexAddModel").selectpicker('val', getSelectRows[0].md5Index >= 1 ? "true" : "false");
        }
        $("#memoAddModel").val(getSelectRows[0].memo);

        $("#isPrivateAddModel").selectpicker('val', getSelectRows[0].isPrivate === null ? "" : getSelectRows[0].isPrivate);
        // 主键
        if (checkIsNull(getSelectRows[0].pkRecno) && !checkIsNumber(getSelectRows[0].pkRecno)) {
            $("#pkRecnoAddModel").selectpicker('val', "" + getSelectRows[0].pkRecnoStatus);
            if(!getSelectRows[0].pkRecnoStatus){
                $("#partitionRecnoAddModel").find("option:contains('一级分区列')").attr("disabled", true);
            }
        } else {
            $("#pkRecnoAddModel").selectpicker('val', getSelectRows[0].pkRecno >= 1 ? "true" : "false");
            if(getSelectRows[0].pkRecno < 1) {
                $("#partitionRecnoAddModel").find("option:contains('一级分区列')").attr("disabled", true);
            }
        }
        $("#partitionRecnoAddModel").selectpicker('val',getSelectRows[0].partitionRecno === 0 ? "" : getSelectRows[0].partitionRecno);
        // 聚集列
        if (checkIsNull(getSelectRows[0].clustRecno) && !checkIsNumber(getSelectRows[0].clustRecno)) {
            $("#clustRecnoAddModel").selectpicker('val',"" + getSelectRows[0].clustRecnoStatus);
        } else {
            $("#clustRecnoAddModel").selectpicker('val', getSelectRows[0].clustRecno >= 1 ? "true" : "false");
        }
        $("#oraShowAddModel").selectpicker('val', getSelectRows[0].oraShow);
        $("#odpsPattitionAddModel").selectpicker('val', getSelectRows[0].odpsPattition === null ? "" : getSelectRows[0].odpsPattition);

        $("#proTypeAddModel").selectpicker('val', getSelectRows[0].proType === null ? "" : getSelectRows[0].proType);
        if (checkIsNull(getSelectRows[0].columnName)) {
            $("#partitionRecnoAddModel").find("option:contains('二级分区列')").attr("disabled", true);
        } else if (getSelectRows[0].columnName.trim().toUpperCase() === "DT") {
            $("#partitionRecnoAddModel").find("option:contains('二级分区列')").attr("disabled", false);
        } else {
            $("#partitionRecnoAddModel").find("option:contains('二级分区列')").attr("disabled", true);
        }
        if(getSelectRows[0].columnName.trim().toUpperCase() === "DT"){
            $("#odpsPattitionAddModel").selectpicker('val', 1);
            $("#partitionRecnoAddModel").find("option:contains('一级分区列')").attr("disabled", true);
        } else {
            if($("#partitionRecnoAddModel").val()=="2"){
                $("#partitionRecnoAddModel").selectpicker('val', "");
            }
            $("#odpsPattitionAddModel").selectpicker('val', "");
        }
        // 20200914 新增字段分类和敏感度信息
        $("#sensitivityCh").text(getSelectRows[0].fieldClassId);
        $("#sensitivityCh").val(getSelectRows[0].fieldClassCh);
        $("#SensitivityLevel").selectpicker('val', getSelectRows[0].sensitivityLevel);
        addUpdateColumn = "update";
    }
    $(".selectpicker").selectpicker("refresh");
    $('#saveObjectField').modal('show');
}

function changePK() {
    var columnText = $("#fieldNameAddModel").val();

    if($("#pkRecnoAddModel").val()==="true"&&columnText.toUpperCase()!=="DT"){
        $("#partitionRecnoAddModel").find("option:contains('一级分区列')").attr("disabled", false);
    } else if($("#pkRecnoAddModel").val()==="false"){
        $("#partitionRecnoAddModel").selectpicker('val', "");
        $("#partitionRecnoAddModel").find("option:contains('一级分区列')").attr("disabled", true);
        $("#partitionRecnoAddModel").find("option:contains('二级分区列')").attr("disabled", true);
    } else if(columnText.toUpperCase()=="DT"){
        if($("#partitionRecnoAddModel")=="1"){
            $("#partitionRecnoAddModel").selectpicker('val', "");
        }
        $("#partitionRecnoAddModel").find("option:contains('一级分区列')").attr("disabled", true);
        $("#partitionRecnoAddModel").find("option:contains('二级分区列')").attr("disabled", false);
    }
    $("#partitionRecnoAddModel").selectpicker("refresh");
}

function inputBlurFunctionUpdate() {
    $("#fieldIdAddModel").blur(function () {
        var fieldIdInput = $("#fieldIdAddModel").val();
        if (checkIsNull(fieldIdInput.trim())) {
            return;
        } else {
            // toastr.info("获取元素编码为："+fieldIdInput+"的相关字段信息");
            // 之后ajax查询相关的字段信息
            getAddColumnByInputUpdate("fieldId", fieldIdInput);
        }
    });
    // $("#fieldNameAddModel").blur(function () {
    //     var columnNameInput = $("#fieldNameAddModel").val();
    //     var columnText = $("#fieldNameAddModel").val();
    //     if($("#pkRecnoAddModel").val()==="true"&&columnText.toUpperCase()!=="DT"){
    //         $("#partitionRecnoAddModel").find("option:contains('一级分区列')").attr("disabled", false);
    //     } else if($("#pkRecnoAddModel").val()==="false"){
    //         $("#partitionRecnoAddModel").val("");
    //         $("#partitionRecnoAddModel").find("option:contains('一级分区列')").attr("disabled", true);
    //         $("#partitionRecnoAddModel").find("option:contains('二级分区列')").attr("disabled", true);
    //     } else if(columnText.toUpperCase()=="DT"){
    //         if($("#partitionRecnoAddModel")=="1"){
    //             $("#partitionRecnoAddModel").val("");
    //         }
    //         $("#partitionRecnoAddModel").find("option:contains('一级分区列')").attr("disabled", true);
    //         $("#partitionRecnoAddModel").find("option:contains('二级分区列')").attr("disabled", false);
    //     }
    //     if (checkIsNull(columnNameInput.trim())) {
    //         return;
    //     } else {
    //         // toastr.info("获取表字段名为："+columnNameInput+"的相关字段信息");
    //         // 之后ajax查询相关的字段信息
    //         getAddColumnByInputUpdate("columnName", columnNameInput);
    //     }
    // });
}


/**
 * 在增加字段的模态框中，如果元素编码 和 表字段名的输入框 失去聚焦，
 * 则表示输入完成，需要查询后台数据来获取具体的字段信息
 */
var isClick = true;

function inputBlurFunction() {
    $("#fieldIdAddModel").blur(function () {
        var fieldIdInput = $("#fieldIdAddModel").val();
        if (checkIsNull(fieldIdInput.trim())) {
            inputSelectClean();
            return;
        } else {
            // toastr.info("获取元素编码为："+fieldIdInput+"的相关字段信息");
            // 之后ajax查询相关的字段信息
            getAddColumnByInput("fieldId", fieldIdInput);
        }
    });
    // $("#fieldNameAddModel").blur(function () {
    //     var columnNameInput = $("#fieldNameAddModel").val();
    //     if (checkIsNull(columnNameInput.trim())) {
    //         inputSelectClean();
    //         return;
    //     } else {
    //         // toastr.info("获取表字段名为："+columnNameInput+"的相关字段信息");
    //         // 之后ajax查询相关的字段信息
    //         getAddColumnByInput("columnName", columnNameInput);
    //     }
    // });

    $("#flowComplete").click(function () {
        if (!isClick) {
            return;
        }
        try {
            isClick = false;
            var message = {}, taskInfo = {};
            var outSourceFirmStr = checkIsNull($("#sourceFirmTdSelect").val()) ? $("#sourceFirm").text() : $("#sourceFirmTdSelect").val()
            taskInfo.standardId = $("#tableId").text();
            message.pageTag = 'standard';
            message.standardPropStr = JSON.stringify(taskInfo);
            message.outSourceSystem = $("#objectName").val();     //所属系统代码（数值）
            message.outTableName = $("#dataSourceName").text();           //协议中文名
            message.outSourceProtocol = $("#tableId").text(); //数据协议
            message.outSourceFirm = outSourceFirmStr;         //数据产商中文名
            message.outSourceSystemName = $("#objectName").val(); //
            message.objectId = $("#objectIdManage").text();   //序号
            message.sysChiName = $("#objectName").text();     //所属系统中文名
            message.sourceFirmCode = getSourceFirmCode(outSourceFirmStr);//输出-产商代码
            message.standardFieldJsonStr = getTableColumnByTableIdFlow();
            if (message.standardFieldJsonStr == "") {
                return;
            }
            window.parent.postMessage(message, "*");
            toastr.info("数据定义已完成！");
        } catch (errMsg) {
            toastr.error(errMsg)
        }
        setTimeout(function () {
            isClick = true;
        }, 1500)
    });

    $("#saveSrcFieldsBut").click(function () {
        //获取sourceFieldTable选中的数据行
        var getSelectRows = $('#sourceFieldTable').bootstrapTable('getSelections');
        if (getSelectRows.length <= 0) {
            toastr.error("请至少选择一条数据进行操作！");
            return;
        }
        // // 在后台拼接出页面上需要的字段信息
        // if (getSelectRows.length >= 8) {
        //     // 如果大于等于8个，则插入所有的数据
        //     getSelectRows = $('#sourceFieldTable').bootstrapTable('getData');
        // }
        $.ajax({
            type: "post",
            data: JSON.stringify({
                'sourceFieldInfo': getSelectRows,
                'objectID': $("#objectIdManage").text(),
                'pageColumnList': column_table_list
            }),
            contentType: 'application/json',
            dataType: 'json',
            url: "getSourceFieldColumnList",
            calculable: false,
            async: false,
            before: function () {
            },
            success: function (result) {
                if (result.status == 1) {//表示成功！
                    var data = result.result;
                    if (!checkIsNull(data)) {
                        data.forEach(function (value) {
                            column_table_list.push(value);
                        });
                        $("#resourceManageObjectField").bootstrapTable('load', column_table_list);
                        addTotalAndtitle('#resourceManageObjectFieldTab', column_table_list.length);
                        //清楚筛选功能
                        resetData();
                    }
                    $("#TableDiv").addClass("tabScrollDiv");
                    $('#importSrcFieldModal').modal('hide');
                } else {
                    toastr.error(result.message);
                }
            },
            error: function (result) {
                toastr.error("从来源关系中添加字段信息报错");
            }
        });
    });

    // 解释页面跳转的click事件
    $(".imgGlyphicon").click(function () {
        window.open("prestoExposition?id=" + this.id, '_blank')
    });
    //  表名填写规则的click事件
    $(".tableNameGlyphicon").click(function () {
        window.open("tableNameExposition", '_blank')
    })
}

function getTableColumnByTableIdFlow() {
    var resultAll = "";
    var tableId = $("#tableId").text();
    if (tableId == null || tableId.trim() === "") {
        resultAll = "";
        toastr.error("tableId不能为空，数据定义失败");
        return resultAll;
    }
    var tableName = $("#realTableName").text();

    if (tableName == null || tableName.trim() === "") {
        resultAll = "";
        toastr.error("目标表名不能为空，数据定义失败");
        return resultAll;
    }
    $.ajax({
        type: "get",
        data: {
            "tableId": tableId
        },
        url: "getAllStandardFieldJson",
        calculable: false,
        async: false,
        success: function (result) {
            console.log(result);
            if (result.status === 1) {
                resultAll = JSON.stringify(result.data);
            } else {
                toastr.error("数据定义失败" + result.msg);
                resultAll = "";
            }

        },
        error: function (result) {
            resultAll = "";
        }
    });
    return resultAll;
}

function getAddColumnByInputUpdate(type, inputValue) {
    $.ajax({
        type: "get",
        data: {
            "type": type,
            "inputValue": inputValue
        },
        url: "getAddColumnByInput",
        calculable: false,
        async: true,
        success: function (result) {
            if (result.status === 1) {
                $("#fieldIdAddModel").val(result.data.fieldid === null ? "" : result.data.fieldid);
                $("#fieldNameAddModel").val(result.data.fieldname === null ? "" : result.data.fieldname);
                var oldLen = $("#fieldLenAddModel").val();
                if(!checkIsNull(result.data.fieldlen) && (checkIsNull(oldLen)||oldLen==='0')){
                    var oldLenInt = parseInt(oldLen);
                    var newLenInt = parseInt( result.data.fieldlen);
                    if((isNaN(oldLenInt)|| oldLenInt < newLenInt)&& !isNaN(newLenInt)){
                        $("#fieldLenAddModel").val(result.data.fieldlen);
                    }
                }
                $("#fieldTypeAddModel").selectpicker('val', result.data.fieldtype);
                var fieldTypeModel = result.data.fieldtypeName === null ? "" : result.data.fieldtypeName;
                // $("#fieldTypeAddModel").find("option:contains('" + fieldTypeModel + "')").attr("selected", true);
                if (checkIsNull(fieldTypeModel) || result.data.fieldtype === -1) {
                    $("#fieldTypeAddModel").selectpicker('val',"-1");
                    $("#fieldTypeAddModel").removeAttr("disabled");
                }
                var memo = result.data.fieldchinesename === null?"":result.data.fieldchinesename;
                if(memo !== "" && $("#fieldChineNameAddModel").val() === ""){
                    $("#fieldChineNameAddModel").val(memo);
                }
            } else {
                console.log(result.msg);
                // 如果建表字段是dt,则可以将其选择为二级分区字段
                var columnText = $("#columnNameAddModel").val();
                $("#partitionRecnoAddModel").selectpicker('val', "");
                if (checkIsNull(columnText)) {
                    $("#partitionRecnoAddModel").find("option:contains('二级分区列')").attr("disabled", true);
                } else if (columnText.trim().toUpperCase() === "DT") {
                    $("#partitionRecnoAddModel").find("option:contains('二级分区列')").attr("disabled", false);
                } else {
                    $("#partitionRecnoAddModel").find("option:contains('二级分区列')").attr("disabled", true);
                }

                if(columnText.trim().toUpperCase() === "DT"){
                    $("#odpsPattitionAddModel").selectpicker('val', 1);
                    if($("#pkRecnoAddModel").val()==="true"){
                        $("#partitionRecnoAddModel").find("option:contains('一级分区列')").attr("disabled", true);
                    }
                } else {
                    $("#odpsPattitionAddModel").selectpicker('val', "");
                }
            }
            $(".selectpicker").selectpicker("refresh");
        },
        error: function (result) {
            toastr.error("查询相关字段信息报错");
        }
    });

}


/**
 * 根据元素编码 和 表字段名的输入框内的值查询
 */
function getAddColumnByInput(type, inputValue) {
    $.ajax({
        type: "get",
        data: {
            "type": type,
            "inputValue": inputValue
        },
        url: "getAddColumnByInput",
        calculable: false,
        async: true,
        success: function (result) {
            if (result.status === 1) {
                $("#fieldIdAddModel").val(result.data.fieldid === null ? "" : result.data.fieldid);
                $("#columnNameAddModel").val(result.data.columnname === null ? "" : result.data.columnname);
                $("#fieldNameAddModel").val(result.data.fieldname === null ? "" : result.data.fieldname);
                $("#fieldChineNameAddModel").val(result.data.fieldchinesename === null ? "" : result.data.fieldchinesename);
                // $("#fieldTypeAddModel option").removeAttr("selected");
                $("#fieldTypeAddModel").selectpicker('val',result.data.fieldtype);
                var fieldTypeModel = result.data.fieldtypeName === null ? "" : result.data.fieldtypeName;
                // $("#fieldTypeAddModel").find("option:contains('" + fieldTypeModel + "')").attr("selected", true);
                if (checkIsNull(fieldTypeModel) || result.data.fieldtype === -1) {
                    $("#fieldTypeAddModel").selectpicker('val',"-1");
                    $("#fieldTypeAddModel").removeAttr("disabled");
                    $("#fieldLenAddModel").removeAttr("readonly");
                } else {
                    $("#fieldTypeAddModel").attr("disabled", "disabled");
                    $("#fieldLenAddModel").attr("readonly", "true");
                }
                $("#fieldLenAddModel").val(result.data.fieldlen === null ? "" : result.data.fieldlen);
                $("#fieldDescribeAddModel").val(result.data.fielddescribe === null ? "" : result.data.fielddescribe);
                $("#defaultValueAddModel").val(result.data.defaultvalue === null ? "" : result.data.defaultvalue);
                // toastr.info("查询相关字段信息结束");
                // 如果建表字段是dt,则可以将其选择为二级分区字段
                var columnText = $("#columnNameAddModel").val();
                $("#partitionRecnoAddModel").selectpicker('val',"");
                if (checkIsNull(columnText)) {
                    $("#partitionRecnoAddModel").find("option:contains('二级分区列')").attr("disabled", true);
                } else if (columnText.trim().toUpperCase() === "DT") {
                    $("#partitionRecnoAddModel").find("option:contains('二级分区列')").attr("disabled", false);
                    $("#partitionRecnoAddModel").find("option:contains('一级分区列')").attr("disabled", true);
                } else {
                    $("#partitionRecnoAddModel").find("option:contains('二级分区列')").attr("disabled", true);
                }
                if(columnText.trim().toUpperCase() === "DT"){
                    // partitionRecno
                    $("#odpsPattitionAddModel").selectpicker('val', 1);
                    $("#partitionRecnoAddModel").find("option:contains('一级分区列')").attr("disabled", true);
                } else {
                    $("#odpsPattitionAddModel").selectpicker('val', "");
                    if($("#pkRecnoAddModel").val()==="true"){
                        $("#partitionRecnoAddModel").find("option:contains('一级分区列')").attr("disabled", false);
                    }
                    $("#partitionRecnoAddModel").find("option:contains('一级分区列')").attr("disabled", true);
                }
                $(".selectpicker").selectpicker("refresh");
            } else {
                console.log(result.msg);
                // toastr.error(result.msg);
                $("#partitionRecnoAddModel").val("");
                $("#partitionRecnoAddModel").find("option:contains('二级分区列')").attr("disabled", true);
                $("#odpsPattitionAddModel").selectpicker('val', "");
                $(".selectpicker").selectpicker("refresh");
            }
        },
        error: function (result) {
            toastr.error("查询相关字段信息报错");
        }
    });

}

function saveTable() {
    var obj = new Object();
    //  获取user_id
    var userId = getUserId();
    var userName = getUserName();
    // 20210127 获取建表描述的信息
    var createTableMemo = $("#createTableMemo").val().trim();
    if(createTableMemo.length > 3000){
      toastr.error("建表描述的长度不能超过3000")
      return;
    }

    // 获取数据源类型
    var dataResourceType = $("#dataResourceType option:selected").val();
    if (dataResourceType === "ODPS" || dataResourceType === "ADS" || dataResourceType === "DATAHUB") {
        //  20200603 新增数据源名称和数据中心名称
        obj.dataCenterName = $("#dataCentName option:selected").text();
        obj.dataResourceName = $("#dataResourceName option:selected").text();
        //
        obj.primaryOrganizationCh = $("#organizationClassify").text().split("/")[0];
        obj.secondaryOrganizationCh = $("#organizationClassify").text().split("/")[1];
        if ($("#organizationClassify").text().split("/").length === 3) {
            obj.threeOrganizationCh = $("#organizationClassify").text().split("/")[2];
        } else {
            obj.threeOrganizationCh = "";
        }
        obj.primaryDatasourceCh = $("#sourceClassify").text().split("/")[0];
        obj.secondaryDatasourceCh = $("#sourceClassify").text().split("/")[1];
        obj.dataId = $("#dataResourceName option:selected").val();
        obj.tableNameCH = $("#dataSourceName").text();
        obj.tableId = $("#tableId").text();
        obj.dsType = $("#dataResourceType option:selected").val();
        obj.schema = $("#projectName option:selected").val();
        obj.tableName = $("#tableName").val();
        obj.objectId = $("#objectIdManage").text();
        var life = $("input[name=life]")[1].checked;
        var lifeDays = $("#lifeDays").val();
        if(dataResourceType==="ADS"){
            obj.isActiveTable = $("#isActiveTable").val();
            obj.partitionFirst = $("#partitionFirst").val() === "selected" ? null : $("#partitionFirst").val();
            obj.partitionFirstNum = $("#partitionFirstNum").val();
            obj.partitionSecond = $("#partitionSecond").val() === "selected" ? null : $("#partitionSecond").val();
            obj.partitionSecondNum = $("#partitionSecondNum").val();
            if (obj.partitionFirst === "selected" || checkIsNull(obj.partitionFirst)) {
                toastr.error("一级分区字段不能为空");
                return;
            }
            if ((!obj.partitionFirst || !obj.partitionFirstNum) || (obj.partitionSecond && !obj.partitionSecondNum)) {
                toastr.error("选择分区字段后，分区数不能为空");
                return;
            }
        }else if(dataResourceType==="ODPS"){
            // obj.partitionColumns = $("#odpsPartitionselect").val();
            if (life && !lifeDays) {
                toastr.error("请填写必填项");
                return;
            }
        }else{
            // datahub 建表时特有的参数
            var checkFlag = checkDatahubMessageIsNull();
            if (checkFlag) {
                return;
            }
            obj.topicName = $("#topicName").val();
            // 通道数
            obj.shardCount = $("#shardCount").val();
            // 生命周期
            lifeDays =  $("#lifeCycle").val();
            // 备注
            obj.comment = $("#comment").val();

        }
        var msg = checkRequired(obj);
        if (!checkIsNull(msg)) {
            toastr.error(msg);
            return;
        }
        // if(obj.dataId==="selected"||obj.objectFields=="selected"||obj.dsType==="selected"||obj.schema==="selected"||obj.tableName===""||obj.partitionFirst==null){
        //   toastr.error("请填写必填项");
        //   return;
        // }
        obj.lifeCycle = lifeDays;
        columnCorrespondClick();
        obj.objectFields = columnInfo;
        obj.approvalId = createApprovalId;
        obj.userId = userId;
        obj.userName = userName;
        obj.createTableMemo = createTableMemo;
        addMask();
        $.ajax({
            type: "post",
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify(obj),
            url:  "dbManager/buildTable",
            calculable: true,
            async: true,
            success: function (result) {
                if (result.status === 1) {
                    if(result.switchFlag){
                        var iframeUrl = result.data;
                        $('#approvalInfoHtml').attr('src', iframeUrl);
                        $("#importApprovalInfoModal").modal('show');
                        closemodal();
                    }else{
                        toastr.info("建表成功");
                        closemodal();
                    }
                } else {
                    toastr.error(result.message);
                }
                // getCreatedTableData();
                $.unblockUI();
            },
            error: function (result) {
                toastr.error(result.message);
                $.unblockUI();
            }
        });
    } else if (dataResourceType === "HBASE" || dataResourceType === "HIVE") {
        obj.dsType = dataResourceType;
        obj.tableName = $("#tableName").val();
        obj.dataId = $("#dataResourceName option:selected").val();
        //  20200603 新增数据源名称和数据中心名称
        obj.dataCenterName = $("#dataCentName option:selected").text();
        obj.dataResourceName = $("#dataResourceName option:selected").text();
        //
        obj.tableId = $("#tableId").text();
        obj.objectId = $("#objectIdManage").text();
        if (dataResourceType === "HBASE") {
            var checkFlag = checkPrestoTableMessageIsNull(true);
            if (checkFlag) {
                return;
            }
            obj = getPrestoTableMessage();
            obj.dsType = dataResourceType;
            obj.tableName = $("#tableName").val();
            obj.dataId = $("#dataResourceName option:selected").val();
            obj.tableId = $("#tableId").text();
            obj.objectId = $("#objectIdManage").text();
            obj.tableNameCH = $("#prestoMemo").val();
            //  20200603 新增数据源名称和数据中心名称
            obj.dataCenterName = $("#dataCentName option:selected").text();
            obj.dataResourceName = $("#dataResourceName option:selected").text();
            obj.primaryOrganizationCh = $("#organizationClassify").text().split("/")[0];
            obj.secondaryOrganizationCh = $("#organizationClassify").text().split("/")[1];
            obj.primaryDatasourceCh = $("#sourceClassify").text().split("/")[0];
            obj.secondaryDatasourceCh = $("#sourceClassify").text().split("/")[1];
            if ($("#organizationClassify").text().split("/").length === 3) {
                obj.threeOrganizationCh = $("#organizationClassify").text().split("/")[2];
            } else {
                obj.threeOrganizationCh = "";
            }
        } else {
            obj.primaryOrganizationCh = $("#organizationClassify").text().split("/")[0];
            obj.secondaryOrganizationCh = $("#organizationClassify").text().split("/")[1];
            if ($("#organizationClassify").text().split("/").length === 3) {
                obj.threeOrganizationCh = $("#organizationClassify").text().split("/")[2];
            } else {
                obj.threeOrganizationCh = "";
            }
            obj.primaryDatasourceCh = $("#sourceClassify").text().split("/")[0];
            obj.secondaryDatasourceCh = $("#sourceClassify").text().split("/")[1];
            obj.tableNameCH = $("#dataSourceName").text();
            obj.schema = $("#projectName option:selected").val();
            obj.projectName = $("#projectName option:selected").val();
            obj.partitionFirst = $("#partitionFirst").val() === "selected" ? null : $("#partitionFirst").val();
            var msg = checkRequired(obj);
            if (!checkIsNull(msg)) {
                toastr.error(msg);
                return;
            }
            // if(obj.dataId==="selected"||obj.objectFields=="selected"||obj.dsType==="selected"||obj.schema==="selected"||obj.tableName===""||obj.partitionFirst==null){
            //   toastr.error("请填写必填项");
            //   return;
            // }
        }
        // 一键匹配
        columnCorrespondClick();
        obj.columnData = columnInfo;
        obj.approvalId = createApprovalId;
        obj.userId = userId;
        obj.userName = userName;
        obj.createTableMemo = createTableMemo;
        addMask();
        $.ajax({
            type: "post",
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify(obj),
            url:  "dbManager/createHuaWeiTable",
            calculable: true,
            async: true,
            success: function (result) {
                if (result.status === 1) {
                    if(result.data.approvalInfo === "true"){
                        var iframeUrl = result.data.message;
                        $('#approvalInfoHtml').attr('src', iframeUrl);
                        $("#importApprovalInfoModal").modal('show');
                        closemodal();
                    }else{
                        toastr.info("建表： "+result.data.message);
                        closemodal();
                    }
                    // getCreatedTableData();
                } else {
                    toastr.error(result.msg);
                }
                $.unblockUI();
            },
            error: function (result) {
                toastr.error(result.msg);
                $.unblockUI();
            }
        });
    } else {
        toastr.error("请选择具体的数据源类型");
        return;
    }
}

function saveObjectField() {
    var obj = new Object();
    obj.objectId = $("#objectIdManage").text();
    obj.fieldId = $("#fieldIdAddModel").val();
    obj.columnName = $("#columnNameAddModel").val();
    obj.fieldName = $("#fieldNameAddModel").val();
    obj.fieldChineeName = $("#fieldChineNameAddModel").val();
    obj.fieldDescribe = $("#fieldDescribeAddModel").val();
    obj.fieldType = $("#fieldTypeAddModel").val();
    // obj.fieldType = $("#fieldTypeAddModel option:selected").text();
    obj.fieldLen = $("#fieldLenAddModel").val();
    obj.defaultValue = $("#defaultValueAddModel").val();
    // 是否创建索引
    obj.indexType = $("#tableIndexAddModel option:selected").val();
    // 标准中是否存在索引
    obj.isIndex = $("#isIndexAddModel option:selected").val();
    //是否必填
    obj.needValue = $("#needValueAddModel option:selected").val();
    // 是否为布控条件
    obj.isContorl = $("#isContorlAddModel option:selected").val();
    // 是否可查询
    obj.isQuery = $("#isQueryAddModel option:selected").val();
    // 备注
    obj.memo = $("#memoAddModel").val();
    // 表字段是否可用
    obj.columnNameState = $("#columnNameStateAddModel option:selected").val();
    // 是否参加MD5值计算
    obj.md5IndexStatus = $("#md5IndexAddModel option:selected").val();
    // 是否标准协议
    obj.isPrivate = $("#isPrivateAddModel option:selected").val();
    // 是否为主键
    obj.pkRecnoStatus = $("#pkRecnoAddModel option:selected").val();
    // 分区列标示
    obj.partitionRecno = $("#partitionRecnoAddModel option:selected").val();
    // 是否为聚集列
    obj.clustRecnoStatus = $("#clustRecnoAddModel option:selected").val();
    // 是否近线显示
    obj.oraShow = $("#oraShowAddModel option:selected").val();
    // ODPS的分区列
    obj.odpsPattition = $("#odpsPattitionAddModel option:selected").val();
    // 属性类型
    obj.proType = $("#proTypeAddModel option:selected").val();
    // 创建索引
    obj.tableIndex = $("#tableIndexAddModel option:selected").val();
    //　字段是否属于原始库数据
    obj.fieldSourceType = parseInt($("#fieldSourceType option:selected").val());

    // 20200914 新增字段分类和敏感分类的数据
    obj.fieldClassId = $("#sensitivityCh").text();
    obj.fieldClassCh = $("#sensitivityCh").val();
    obj.sensitivityLevel = $("#SensitivityLevel option:selected").val();
    obj.sensitivityLevelCh = $("#SensitivityLevel option:selected").text();
    // if(checkIsNull(obj.objectId)||checkIsNull(obj.fieldId)||checkIsNull(obj.columnName)||checkIsNull(obj.fieldName)
    //   ||checkIsNull(obj.fieldChineeName)||checkIsNull(obj.fieldType)||checkIsNull(obj.fieldLen)){
    //     toastr.error("请完成必填值的输入");
    //     return;
    // }
    if (checkIsNull(obj.columnName)) {
        toastr.error("请完成必填值的输入");
        return;
    }
    if(checkIsNull(obj.fieldId)){
        toastr.error("元素编码不能为空");
        return;
    }
    if(checkIsNull(obj.fieldName)){
        toastr.error("标准列名不能为空");
        return;
    }
    if (checkIsChinese(obj.fieldId)) {
        toastr.error("元素编码不能存在中文");
        return;
    }
    if (checkIsChinese(obj.fieldName)) {
        toastr.error("标准列名不能存在中文");
        return;
    }
    // 在华为云平台中 没有 分区列标示 ，如果 ODPS的分区列的数据 为 1 则  partitionRecno 为1
    if(obj.odpsPattition === "1" && obj.partitionRecno === "" && dataBaseType === "huaweiyun"){
       obj.partitionRecno = "1"
    }
    //判断一级分区列是否只有一个
    if(obj.partitionRecno==="1") {
        for (var i = 0; i < column_table_list.length; i++) {
            if((column_table_list[i].partitionRecno === '1'||column_table_list[i].partitionRecno === 1)
                &&!checkIsNull(column_table_list[i].partitionRecno)&&
                column_table_list[i].columnName.toLowerCase() !== obj.columnName.toLowerCase()){
                toastr.error("ADS的一级分区列只能存在一个");
                return;
            }
        }
    }
    var flag = false;
    // 需要判断fieldId和 fieldName是不是标准字段信息  2020511
    $.ajax({
        type: "get",
        url:  "getIsExitsFiledMessage",
        contentType: 'application/json',
        dataType: 'json',
        data:{
            "fieldId":obj.fieldId,
            "fieldName":obj.fieldName
        },
        calculable: false,
        async: false,
        success: function (result) {
            if (result.status === 1) {
                flag = false;
            } else {
                toastr.error(result.msg);
                flag = true;
            }
        },
        error: function (result) {
            toastr.error("判断是否为标准列名报错");
            return;
        }
    });
    if(flag){
        return;
    }

    // TODO
    //  @add  20200217 新增数据不直接插入到数据库中，先插入到前端页面，之后再统一保存
    //  所有后端判断的逻辑都需要前端进行判断
    //  对于新增，一个表中只能有一种建表字段(元素编码)，建表字段(元素编码)不能相同
    //  对于编辑，则只能修改对应的对应建表字段(元素编码)的数据
    // 新增字段  0：没有变化   1：新增数据  2：修改数据
    if (addUpdateColumn === "add") {
        //建表字段和 fieldId不能相同
        for (var i = 0; i < column_table_list.length; i++) {
            if(column_table_list[i].columnName.toLowerCase()
                      === obj.columnName.toLowerCase()){
                toastr.error("建表字段已经存在，不能保存");
                return;
            }else if(!checkIsNull(column_table_list[i].fieldId)
                && column_table_list[i].fieldId.toLowerCase()
                       ===obj.fieldId.toLowerCase()){
                toastr.error("元素编码已经存在，不能保存");
                return;
            }else if(!checkIsNull(column_table_list[i].fieldName) &&
                   column_table_list[i].fieldName.toLowerCase()
                          ===obj.fieldName.toLowerCase()){
                toastr.error("标准列名已经存在，不能保存");
                return;
            }
        }
        obj.updateStatus = 1;
        var recno = 0;
        column_table_list.forEach(function (value) {
            if (value.recno >= recno) {
                recno = value.recno + 1;
            }
        });
        obj.recno = recno;
        column_table_list.push(obj);
    } else {
        for (var i = 0; i < column_table_list.length; i++) {
            if(column_table_list[i].columnName !== obj.columnName && column_table_list[i].fieldId ===obj.fieldId){
                toastr.error("元素编码已经存在，不能保存");
                return;
            }else if(column_table_list[i].columnName !== obj.columnName && column_table_list[i].fieldName ===obj.fieldName){
                toastr.error("标准列名已经存在，不能保存");
                return;
            }
        }
        //  如果是新增的数据，还没有插入到数据库，再进行编辑之后，也只是新增数据
        if (obj.updateStatus !== 1) {
            obj.updateStatus = 2;
        }
        // 编辑之后如何修改数据
        for (var i = 0; i < column_table_list.length; i++) {
            var value = column_table_list[i];
            if (value.columnName.toLowerCase() === obj.columnName.toLowerCase()) {
                obj.recno = value.recno;
                obj.fieldType = $("#fieldTypeAddModel").val();
                column_table_list[i] = obj;
            }
        }
    }
    $("#resourceManageObjectField").bootstrapTable('load', column_table_list);
    addTotalAndtitle('#resourceManageObjectFieldTab', column_table_list.length);
    downUpClick();
    closeModal();
    // 刷新筛选
    resetData();
    // var data = JSON.stringify(obj);
    // $.ajax({
    //   type:"post",
    //   dataType: "json",
    //   contentType: "application/json",
    //   data:data,
    //   url:path+"/saveObjectField",
    //   calculable : false,
    //   async:true,
    //   success:function(result){
    //     if(result.status === 1){
    //       toastr.info("保存字段成功"+result.msg);
    //       // 查询字段定义的相关信息
    //       $("#resourceManageObjectField").bootstrapTable('removeAll');
    //       updateObjectField($("#tableId").text().trim());
    //       initOtherSelect();
    //       downUpClick();
    //       closeModal();
    //       // 向标准化管理提交修改后的内容 pushMetaInfo
    //       pushMetaInfoController();
    //     }else{
    //       toastr.error("保存字段失败"+result.msg);
    //     }
    //   },
    //   error:function(result){
    //     toastr.error("增加字段报错")
    //   }
    // });
}

function deleteObjectField() {
    if (!editFlag) {
        $("#statusCheckModal").modal("show");
        return;
    }
    var getSelectRows = $('#resourceManageObjectField').bootstrapTable('getSelections');
    if (getSelectRows.length <= 0) {
        toastr.error("请先选择一条数据!");
        return;
    } else {
        var message = "";
        if(getSelectRows.length <= 5){
          message = "需要删除的字段信息为："
          for(var i=0 ;i < getSelectRows.length; i++){
            message = message+getSelectRows[i].columnName+" ";
          }
        }else{
          message = "总共需要删除"+getSelectRows.length+"个字段";
        }
        bootbox.confirm({
            size: "small",
            backdrop: true,
            title:"<span style='font-size: 16px'>提示</span>",
            message: "<img src='img/myImg/warn.png' style='margin: -5px 10px 0 0;'><span>是否要删除所选数据？</span>" +
              "</br></br><span style='margin-left: 10px'>"+message+"</span>",
            buttons: {
                cancel: {
                    label: '取消',
                    className: 'my-btn-cancle'
                },
                confirm: {
                    label: '确定',
                    className: 'my-btn-confirm'
                }
            },
            callback: function (result) {
                if (result) {
                    for (var i = 0; i < getSelectRows.length; i++) {
                        // recno 序号
                        var recno = getSelectRows[i].recno;
                        // var objectId = getSelectRows[i].objectId;
                        // 改成这个是唯一的
                        var columnName = getSelectRows[i].columnName.toLowerCase();
                        //  删除前端全局变量中的值
                        var deleteI = null;
                        for (var j = 0; j < column_table_list.length; j++) {
                            var value = column_table_list[j];
                            if (value.recno === recno && value.columnName.toLowerCase() === columnName) {
                                deleteI = j;
                                break;
                            }
                        }
                        if (deleteI === null) {
                            toastr.error("没有找到要删除的数据，删除失败");
                        } else {
                            column_table_list.splice(deleteI, 1);
                        }

                        //  删除
                        // $.ajax({
                        //   type:"post",
                        //   url:path+"/deleteObjectField",
                        //   data:{
                        //     "objectId":objectId,
                        //     "fieldName":fieldName
                        //   },
                        //   calculable : false,
                        //   async:false,
                        //   success:function(result){
                        //     if(result.status == 1){
                        //       toastr.info(result.msg);
                        //     }else{
                        //       toastr.error(result.msg);
                        //     }
                        //     //刷新表格数据
                        //     updateObjectField($("#tableId").text());
                        //   },
                        //   error:function (result) {
                        //     toastr.error("删除失败!!!!!!");
                        //     return;
                        //   }
                        // });
                    }
                    //删除之后重新把recno排序
                    for (var j = 0; j < column_table_list.length; j++) {
                        column_table_list[j].recno = j;
                        if(column_table_list[j].updateStatus === 0){
                            column_table_list[j].updateStatus = 2;
                        }
                    }
                    $("#resourceManageObjectField").bootstrapTable('load', column_table_list);
                    addTotalAndtitle('#resourceManageObjectFieldTab', column_table_list.length);
                    //清楚筛选功能
                    resetData();
                }
            }
        });
    }
}

function showObjectField(row) {
    var tableId = $("#tableIdTd").text().trim();
    var fieldId = row.fieldId;
    $.ajax({
        type: "post",
        data: {
            "tableId": tableId,
            "fieldId": fieldId
        },
        url: "showObjectField",
        calculable: false,
        async: true,
        success: function (result) {
            var objectId = result.objectId;
            if (objectId != null && objectId != "" && objectId != "null") {
                $("#objectId").text(result.objectId);
            }
            var recno = result.recno;
            if (recno != null && recno != "" && recno != "null") {
                $("#recno").text(recno);
            }
            var fieldId = result.fieldId;
            if (fieldId != null && fieldId != "" && fieldId != "null") {
                $("#fieldId").text(fieldId);
            }
            var columnName = result.columnName;
            if (columnName != null && columnName != "" && columnName != "null") {
                $("#column_name").text(columnName);
            }
            var columnName = result.columnName;
            if (columnName != null && columnName != "" && columnName != "null") {
                $("#columnName").text(columnName);
            }
            var fieldChineeName = result.fieldChineeName;
            if (fieldChineeName != null && fieldChineeName != "" && fieldChineeName != "null") {
                $("#fieldChineeName").text(fieldChineeName);
            }
            var fieldDescribe = result.fieldDescribe;
            if (fieldDescribe != null && fieldDescribe != "" && fieldDescribe != "null") {
                var fd = "";
                var ft = "";
                var value = "";
                for (var count = 22; count < 1000; count += 22) {
                    if (count == 22) {
                        fd = fieldDescribe.substring(0, count);
                    } else {
                        ft = fieldDescribe.substring(count - 22, count);
                        value = value + ft;
                        if (ft == "" || ft == null) {
                            break;
                        } else {
                            value = value + "</br>";
                        }
                    }

                }
                if (value == "") {
                    fieldDescribe = fd;
                } else {
                    fieldDescribe = fd + "</br>" + value;
                }
                $("#fieldDescribe").html(fieldDescribe);
            }
            var fieldType = result.fieldType + "";
            if (fieldType != null && fieldType != "") {
                if (fieldType == "0") {
                    $("#fieldType").text("整型");
                } else if (fieldType == "1") {
                    $("#fieldType").text("浮点型");
                } else if (fieldType == "2") {
                    $("#fieldType").text("字符串类型");
                } else if (fieldType == "3") {
                    $("#fieldType").text("日期型，格式为yyyyMMdd");
                } else if (fieldType == "4") {
                    $("#fieldType").text("日期型，格式为yyyyMMddHH:mm:ss[.ffff]");
                } else {
                    $("#fieldType").text("");
                }
            }
            var fieldLen = result.fieldLen;
            if (fieldLen != null && fieldLen != "" && fieldLen != "null") {
                $("#fieldLen").text(fieldLen);
            }
            var defaultValue = result.defaultValue;
            if (defaultValue != null && defaultValue != "" && defaultValue != "null") {
                $("#defaultValue").text(defaultValue);
            }
            var tableIndex = result.tableIndex + "";
            if (tableIndex != null && tableIndex != "") {
                if (tableIndex == "0") {
                    $("#tableIndex").text("不建立");
                } else if (tableIndex == "1") {
                    $("#tableIndex").text("建立可以通过HBASE查询");
                } else if (tableIndex == "2") {
                    $("#tableIndex").text("建立索引，但是因为没有数据，不建议查询");
                }
            }

            var isIndex = result.isIndex + "";
            if (isIndex != null && isIndex != "") {
                if (isIndex == "0") {
                    $("#isIndex").text("否");
                } else if (isIndex == "1") {
                    $("#isIndex").text("是");
                }
            }
            var indexType = result.indexType + "";
            if (indexType != null && indexType != "") {
                if (indexType == "0") {
                    $("#indexType").text("不建立索引");
                } else if (indexType == "1") {
                    $("#indexType").text("建立分词索引");
                } else if (indexType == "2") {
                    $("#indexType").text("建立非分词索引");
                } else if (indexType == "3") {
                    $("#indexType").text("位图索引");
                }
            }
            var needValue = result.needValue + "";
            if (needValue != null && needValue != "") {
                if (needValue == "0") {
                    $("#needValue").text("否");
                } else if (needValue == "1") {
                    $("#needValue").text("是");
                }
            }
            var isContorl = result.isContorl + "";
            if (isContorl != null && isContorl != "") {
                if (isContorl == "0") {
                    $("#isContorl").text("否");
                } else if (isContorl == "1") {
                    $("#isContorl").text("是");
                }
            }
            var isQuery = result.isQuery + "";
            if (isQuery != null && isQuery != "") {
                if (isQuery == "0") {
                    $("#isQuery").text("否");
                } else if (isQuery == "1") {
                    $("#isQuery").text("是");
                }
            }
            var memo = result.memo;
            if (memo != null && memo != "" && memo != "null") {
                $("#memo").text(memo);
            }
            var columnNameState = result.columnNameState + "";
            if (columnNameState != null && columnNameState != "") {
                if (columnNameState == "0") {
                    $("#columnNameState").text("不可用");
                } else if (columnNameState == "1") {
                    $("#columnNameState").text("可用");
                } else if (columnNameState == "2") {
                    $("#columnNameState").text("临时可用");
                } else if (columnNameState == "3") {
                    $("#columnNameState").text("ads可用，odps不可用");
                }
            }
            var secretLevel = result.secretLevel + "";
            if (secretLevel != null && secretLevel != "") {
                if (secretLevel == "1") {
                    $("#secretLevel").text("一级");
                } else if (secretLevel == "2") {
                    $("#secretLevel").text("二级");
                } else if (secretLevel == "3") {
                    $("#secretLevel").text("三级");
                } else if (secretLevel == "4") {
                    $("#secretLevel").text("四级");
                } else if (secretLevel == "5") {
                    $("#secretLevel").text("五级");
                } else if (secretLevel == "6") {
                    $("#secretLevel").text("六级");
                } else if (secretLevel == "7") {
                    $("#secretLevel").text("七级");
                } else if (secretLevel == "8") {
                    $("#secretLevel").text("八级");
                } else if (secretLevel == "9") {
                    $("#secretLevel").text("九级");
                }
            }
            $('#showObjectField').modal('show');
        },
        error: function (result) {
        }
    });
}


function updateObjectField(tableId) {
    addMask();
    $.ajax({
        type: "post",
        data: {
            "tableId": tableId
        },
        url: "resourceManageObjectField",
        calculable: false,
        async: true,
        success: function (result) {
            try {
                $("#resourceManageObjectField").bootstrapTable('load', result);
                addTotalAndtitle('#resourceManageObjectFieldTab', column_table_list.length);

                columnInfo = result;
                columnInfo.forEach(function (row) {
                    row.indexs = (row.isIndex === 1 ? true : false);
                });
                column_table_list = columnInfo;
                $('#resourceManageObjectFieldTab .total-num').html('共' + column_table_list.length + '条');
                // addTotalAndtitle('#resourceManageObjectFieldTab',column_table_list.length);

            } catch (e) {
                console.log("获取字段信息失败，没有字段信息" + e);
                // toastr.error("获取字段信息失败，没有字段信息");
                column_table_list = [];
            }
            $.unblockUI();
        },
        error: function (result) {
            $.unblockUI();
        }
    });
}


//创建bootstrap表格(元素代码集)
function resourceManageObjectField1() {
    //构造表格
    $("#resourceManageObjectDetailDiv").bootstrapTable({
        sidePagination: "client", // 分页方式：client客户端分页，server服务端分页
        pagination: false,// 启动分页
        pageSize: 15,// 每页显示的记录条数
        pageNumber: 1,// 当前显示第几页
        pageList: [5],
        search: false,// 是否启用查询,
        showRefresh: false, // 是否显示刷新按钮
        showColumns: false, // 是否显示所有的列
        minimumCountColumns: 2, // 最少允许的列数
        columns: [
            {field: 'recno', title: "表ID", valign: "middle", align: "center", colspan: 1, rowspan: 1},
            {field: 'recno1', title: "对象名", valign: "middle", align: "center", colspan: 1, rowspan: 2},
            {field: 'recno2', title: "序号", valign: "middle", align: "center", colspan: 1, rowspan: 3},
            {field: 'recno3', title: "数据保存天数", valign: "middle", align: "center", colspan: 1, rowspan: 4},
            {field: 'recno4', title: "表描述", valign: "middle", align: "center", colspan: 1, rowspan: 5},
            {field: 'recno5', title: "真实表名", valign: "middle", align: "center", colspan: 2, rowspan: 1},
            {field: 'recno6', title: "表状态", valign: "middle", align: "center", colspan: 2, rowspan: 2},
            {field: 'recno7', title: "表类型", valign: "middle", align: "center", colspan: 2, rowspan: 3},
            {field: 'recno8', title: "对应的数据协议", valign: "middle", align: "center", colspan: 2, rowspan: 4},
            {field: 'recno9', title: "Hive下的表名", valign: "middle", align: "center", colspan: 3, rowspan: 1},
            {field: 'recno10', title: "存储位置", valign: "middle", align: "center", colspan: 3, rowspan: 2},
            {field: 'recno11', title: "数据来源名", valign: "middle", align: "center", colspan: 3, rowspan: 3}
        ],
    });
};

/**
 * 计算获得表的高度
 * @returns {number}
 */
function caculateTableHeight() {
    var tableHeight = $("#sourceRelationShipTab").height();
    console.log(tableHeight);
    return tableHeight;
}


/**
 * 省略超长的字符串显示
 * @param value
 * @param row
 * @param index
 * @returns {string}
 */
function paramsMatter(value,row,index){
    if(value==undefined){
        return "";
    }
    var html;
    value=value.replace(/\s+/g,'&nbsp;');
    html = "<span title="+value+">"+value+"</span>";
    return html;
}
//创建bootstrap表格(对象字段表)
function resourceManageObjectField() {
    //构造表格
    $("#resourceManageObjectField").bootstrapTable({
        sidePagination: "client", // 分页方式：client客户端分页，server服务端分页
        pagination: true,// 启动分页
        pageSize: 10,// 每页显示的记录条数
        pageNumber: 1,// 当前显示第几页
        pageList: [10, 20, 30, 50],
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
        // height: caculateTableHeight(),
        onDblClickRow: function (row, $element) {  //双击效果
            // showObjectField(row);
            if (editFlag) {
                addOrUpdateObjectField("update", row)
            } else {
                $("#statusCheckModal").modal("show");
            }
        },
        clickToSelect: true,
        columns: [
            {checkbox: true},
            {field: 'recno', title: "序号", valign: "middle", align: "center", colspan: 1, rowspan: 1,titleTooltip:"序号"},
            {field: 'columnName', title: "建表字段 ", valign: "middle", align: "center", colspan: 1, titleTooltip:"建表字段",rowspan: 1, formatter:paramsMatter},
            {field: 'fieldChineeName', title: "字段中文名", valign: "middle", align: "center", colspan: 1, rowspan: 1
                ,titleTooltip:"字段中文名", formatter:paramsMatter},
            {field: 'fieldId', title: "元素编码", valign: "middle", align: "center", colspan: 1, titleTooltip:"元素编码",rowspan: 1},
            {field: 'fieldClassCh', title: "字段分类", valign: "middle", align: "center", colspan: 1, titleTooltip:"字段分类", rowspan: 1},
            {field: 'sameWordType', title: "语义类型", valign: "middle", align: "center", colspan: 1, titleTooltip:"语义类型", rowspan: 1},
            {field: 'sensitivityLevelCh', title: "敏感级别", valign: "middle", align: "center", colspan: 1,titleTooltip:"敏感级别", rowspan: 1},
            {
                field: 'fieldType', title: "字段类型", valign: "middle", align: "center", colspan: 1,titleTooltip:"字段类型", rowspan: 1,
                formatter: function (value, row, index) {
                    var value = "";
                    if (row.fieldType == "0") {
                        value = "整形";
                    } else if (row.fieldType == "1") {
                        value = "浮点型";
                    } else if (row.fieldType == "2") {
                        value = "字符串类型";
                    } else if (row.fieldType == "3") {
                        value = "日期型，格式为yyyyMMdd";
                    } else if (row.fieldType == "4") {
                        value = "日期型，格式为yyyyMMddHH:mm:ss";
                    } else if (row.fieldType == "6") {
                        value = "Long类型";
                    } else if (row.fieldType == "7") {
                        value = "double类型";
                    }
                    return value;
                }
            },
            {field: 'fieldLen', title: "字段长度", valign: "middle", align: "center", colspan: 1,titleTooltip:"字段长度", rowspan: 1},
            {
                field: 'isIndex', title: "<div id='isIndexFilterDiv'>索引<span id='isIndexFilter' class='glyphicon glyphicon-menu-down dropdown-toggle' onclick='tableFilterField(this,event)'" +
                    "style='cursor: pointer;margin-left: 2px;display: inline-block' ></span></div>", valign: "middle", align: "center", colspan: 1, rowspan: 1,
                formatter: function (value, row, index) {
                    var value = "";
                    if (row.isIndex == "0") {
                        value = "否";
                    } else if (row.isIndex == "1") {
                        value = "是";
                    }
                    return value;
                }
            },
            {
                field: 'needValue',titleTooltip:"该字段是否必填", title: "<div id='needValueFilterDiv'>必填<span id='needValueFilter' class='glyphicon glyphicon-menu-down dropdown-toggle' onclick='tableFilterField(this,event)' " +
                    "style='cursor: pointer;margin-left: 2px;display: inline-block' ></span></div>", valign: "middle", align: "center", colspan: 1, rowspan: 1,
                formatter: function (value, row, index) {
                    var value = "";
                    if (row.needValue == "0") {
                        value = "否";
                    } else if (row.needValue == "1") {
                        value = "是";
                    }
                    return value;
                }
            },
            {
                field: 'codeText', title: "代码中文名", valign: "middle", align: "center", colspan: 1, rowspan: 1,
                titleTooltip:"代码中文名",
                events: operateEvents, formatter: function (value, row, index) {
                    if (value != null) {
                        return ['<a class="mod">' + value + '</a>'].join("");
                    }
                }
            },
            {field: 'fieldName', title: "标准列名", titleTooltip:"标准列名",valign: "middle", align: "center", colspan: 1, rowspan: 1, formatter:paramsMatter},
            {
                field: 'codeid',
                title: "codeId",
                valign: "middle",
                align: "center",
                colspan: 1,
                rowspan: 1,
                visible: false
            },
            {
                field: 'fieldSourceType', titleTooltip:"该字段是否属于原始库",title: "<div id='fieldSourceTypeFilterDiv'>属于原始库<span id='fieldSourceTypeFilter' class='glyphicon glyphicon-menu-down dropdown-toggle' onclick='tableFilterField(this,event)' " +
                    "style='cursor: pointer;margin-left: 2px;display: inline-block' ></span></div>", valign: "middle", align: "center", colspan: 1, rowspan: 1,
                formatter: function (value, row, index) {
                    var value = "";
                    if (row.fieldSourceType === 0 || row.fieldSourceType === '0') {
                        value = "否";
                    } else if (row.fieldSourceType === 1 || row.fieldSourceType === '1') {
                        value = "是";
                    }
                    return value;
                }
            },
            {
                field: 'partitionRecno', titleTooltip:"是否为分区字段",title: "<div id='partitionRecnoFilterDiv'>分区标识<span id='partitionRecnoFilter' class='glyphicon glyphicon-menu-down dropdown-toggle' onclick='tableFilterField(this,event)' " +
                    "style='cursor: pointer;margin-left: 2px;display: inline-block' ></span></div>", valign: "middle", align: "center", colspan: 1, rowspan: 1,
                formatter: function (value, row, index) {
                    var value = "";
                    if (row.partitionRecno === 1 || row.partitionRecno === '1' ) {
                        value = "一级分区列";
                    } else if (row.partitionRecno === 2 || row.partitionRecno === '2'
                        || row.odpsPattition === 1 || row.odpsPattition === '1' ) {
                        value = "二级分区列";
                    }else{
                        value = "非分区列";
                    }
                    return value;
                }
            },
            {field: 'memo', title: "备注", titleTooltip:"备注", valign: "middle", align: "center", colspan: 1, rowspan: 1, formatter:paramsMatter}
        ],
        onPageChange: function () {
            addTotalAndtitle('#resourceManageObjectFieldTab', column_table_list.length);
        }
    });
    addTotalAndtitle('#resourceManageObjectFieldTab', column_table_list.length);
    //var scrollbar = new PerfectScrollbar("#resourceManageObjectFieldTab .fixed-table-body",{});
};

// 表头筛选之后的事件 目前只能单选排序
function tableFilterField(data,e) {
    // 如果没有字段信息，不展示以下内容
    if(column_table_list === null ||column_table_list.length <1){
        return;
    }
    var clickTop = $("#"+data.id+'Div').offset().top +35;
    var clickLeft = $("#"+data.id+'Div').offset().left;
    $(".table-filter").css("top", clickTop+"px");
    $(".table-filter").css("left", clickLeft+"px");
    // 如果是已经展示了，然后判断 如果id值相同 则隐藏 如果id值不同 则先隐藏 然后再显示
    if($(".table-filter")[0].style.display === "block"){
        $(".table-filter").css("display", "none");
        e.stopPropagation();
        if($("#table-filter-id").val() === data.id){

            return;
        }else{
            resetData()
        }
    }
    if($("#table-filter-id").val() === data.id){
        $(".table-filter").css("display", "block");
        e.stopPropagation();
        return;
    }else{
        resetData()
    }
    $("#table-filter-id").val(data.id);

    var checkLabel = [];
    if(data.id === 'isIndexFilter'){
        checkLabel = [{id:'0',value:"否"},{id:'1',value:"是"}]
    }else if (data.id ==='fieldSourceTypeFilter'){
        checkLabel = [{id:'0',value:"否"},{id:'1',value:"是"}]
    }else if (data.id ==='needValueFilter'){
        checkLabel = [{id:'0',value:"否"},{id:'1',value:"是"}]
    }else if(data.id === 'partitionRecnoFilter'){
        checkLabel = [{id:'1',value:"一级分区列"},{id:'2',value:"二级分区列"},{id:null,value:"非分区列"}]
    }else{
        toastr.error("没有配置筛选内容");
    }
    var tableFilterUlHtml = ""
    checkLabel.forEach(function (label) {
        var oneHtml = "<li class=\"checkbox\">\n" +
            "                <span class=\"table-filter-checkbox\"><input name='filter-data' type=\"checkbox\" value=\""+label.id+"\"></span>\n" +
            "                <span class=\"table-filter-span\">"+label.value+"</span>\n" +
            "            </li>";
        tableFilterUlHtml = tableFilterUlHtml+oneHtml;
    });
    $("#table-filter-ul").empty();
    $("#table-filter-ul").html(tableFilterUlHtml);
    $(".table-filter").css("display", "block");
    e.stopPropagation();
}




// 筛选数据
function filterDataClick() {
    var columnName = $("#table-filter-id").val();
    var checkedIds = [];
    $("#table-filter-ul input[name='filter-data']:checked").each(function () {
        checkedIds.push($(this).val());
    });
    if(checkIsNull(columnName)){
        toastr.error("字段信息为空，程序内部错误");
    }
    if(checkedIds.length >0){
        var serachColumnList = [];
        column_table_list.forEach(function (data) {

            if(checkedIds.indexOf(''+data[columnName.replace("Filter","")])!== -1){
                serachColumnList.push(data)
            }else{
                //  非分区列存在两种情况 '0' 或 '' 都可以表示为非分区列
                if(checkedIds.indexOf("null") !== -1 && (''+data[columnName.replace("Filter","")] === '0'
                  ||''+data[columnName.replace("Filter","")] === '' )){
                    serachColumnList.push(data)
                }
            }

        });
        $("#resourceManageObjectField").bootstrapTable('load', serachColumnList);
        addTotalAndtitle('#resourceManageObjectFieldTab', serachColumnList.length);
        // $("#table-filter-id").val("");
        $(".table-filter").css("display", "none");
        // 筛选之后字体颜色变化
        $("#"+columnName+"Div").css("color","var(--main-color)");
    }else{
        resetData()
    }
}


function resetData() {
    var columnName = $("#table-filter-id").val();
    $("#resourceManageObjectField").bootstrapTable('load', column_table_list);
    addTotalAndtitle('#resourceManageObjectFieldTab', column_table_list.length);
    $("#table-filter-id").val("");
    $(".table-filter").css("display", "none");
    $("#"+columnName+"Div").css("color","#131313");
    $("#"+columnName).css("display","inline-block")
}


function creatObjectDetail(tableId, valText) {
    $.ajax({
        type: "post",
        data: {
            "tableId": tableId
        },
        url:  "resourceManageObjectDetail",
        calculable: false,
        async: false,
        success: function (result) {
            if (result.tableId != "" && result.tableId != null) {
                $("#tableId").text(result.tableId);
            } else {
                $("#tableId").text("");
            }
            if (result.objectId != "" && result.objectId != null) {
                $("#objectIdManage").text(result.objectId);
            } else {
                $("#objectIdManage").text("");
            }
            // sourceId的相关信息 20191118的修改
            if (result.sourceId != "" && result.sourceId != null) {
                $("#sourceId").text(result.sourceId);
            } else {
                $("#sourceId").text("");
            }
            // objectMemo的相关信息 20191118的修改
            if (result.objectMemo != "" && result.objectMemo != null) {
                $("#objectMemo").text(result.objectMemo);
            } else {
                $("#objectMemo").text("");
            }

            if (result.dataSourceName != "" && result.dataSourceName != null) {
                $("#dataSourceName").text(result.dataSourceName);
            } else {
                $("#dataSourceName").text("");
            }
            if (result.realTablename != "" && result.realTablename != null) {
                $("#realTableName").text(result.realTablename);
            } else {
                $("#realTableName").text("");
            }
            if (result.storageTableStatus != "" && result.storageTableStatus != null) {
                $("#storageTableStatus").text(result.storageTableStatus);
            } else {
                $("#storageTableStatus").text("");
            }
            // if(result.storageDataMode!=""&&result.storageDataMode!=null){
            //   $("#storageMode").text(result.storageDataMode);
            // }else{
            //   $("#storageMode").text("");
            // }
            if (result.ownerFactory != "" && result.ownerFactory != null) {
                $("#sourceFirm").text(result.ownerFactory);
            } else {
                $("#sourceFirm").text("");
            }
            if (result.codeTextTd != "" && result.codeTextTd != null) {
                $("#objectName").val(result.codeTextTd);
            } else {
                $("#objectName").val("");
            }
            if (result.codeTextCh != "" && result.codeTextCh != null) {
                $("#objectName").text(result.codeTextCh);
            } else {
                $("#objectName").text("");
            }
            if (result.organizationClassify != "" && result.organizationClassify != null) {
                $("#organizationClassify").text(result.organizationClassify);
            } else {
                $("#organizationClassify").text("");
            }
            if (result.sourceClassify != "" && result.sourceClassify != null) {
                $("#sourceClassify").text(result.sourceClassify);
            } else {
                $("#sourceClassify").text("");
            }
            if (result.isActiveTable != "" && result.isActiveTable != null) {
                $("#isActiveTable").val(result.isActiveTable);
                if (result.isActiveTable === "1") {
                    $("#isActiveTable").text("实时表");
                } else if (result.isActiveTable === "0") {
                    $("#isActiveTable").text("批量表");
                } else {
                    $("#isActiveTable").text("");
                }
            } else {
                $("#isActiveTable").val("");
                $("#isActiveTable").text("");
            }
            // 20201010 创建时间的相关数据的回填
            if(checkIsNull(result.createTime)){
                $("#createTime").text("");
            }else{
                $("#createTime").text(result.createTime);
            }
            if(checkIsNull(result.updateTime)){
                $("#updateTime").text("");
            }else{
                $("#updateTime").text(result.updateTime);
            }
            if(checkIsNull(result.creator)){
                $("#creator").text("");
            }else{
                $("#creator").text(result.creator);
            }
            if(checkIsNull(result.updater)){
                $("#updater").text("");
            }else{
                $("#updater").text(result.updater);
            }

            // 6类标签赋值
            // if (result.bodyTag1Val != "") {
            //     $("#bodyTag1").val(result.bodyTag1Val);
            //     $("#bodyTag1").text(result.bodyTag1Text);
            // } else {
            //     $("#bodyTag1").text("");
            //     $("#bodyTag1").val("");
            // }
            // if (result.elementTag2Val != "") {
            //     $("#elementTag2").val(result.elementTag2Val);
            //     $("#elementTag2").text(result.elementTag2Text);
            // } else {
            //     $("#elementTag2").text("");
            //     $("#elementTag2").val("");
            // }
            // if (result.objectDescTag3Val != "") {
            //     $("#objectDescTag3").val(result.objectDescTag3Val);
            //     $("#objectDescTag3").text(result.objectDescTag3Text);
            // } else {
            //     $("#objectDescTag3").text("");
            //     $("#objectDescTag3").val("");
            // }
            // if (result.behaviorTag4Val != "") {
            //     $("#behaviorTag4").text(result.behaviorTag4Text);
            //     $("#behaviorTag4").val(result.behaviorTag4Val);
            // } else {
            //     $("#behaviorTag4").text("");
            //     $("#behaviorTag4").val("");
            // }
            // if (result.relationShipTag5Val != "") {
            //     $("#relationShipTag5").text(result.relationShipTag5Text);
            //     $("#relationShipTag5").val(result.relationShipTag5Val);
            // } else {
            //     $("#relationShipTag5").text("");
            //     $("#relationShipTag5").val("");
            // }
            // if (result.locationTag6Val != "") {
            //     $("#locationTag6").text(result.locationTag6Text);
            //     $("#locationTag6").val(result.locationTag6Val);
            // } else {
            //     $("#locationTag6").text("");
            //     $("#locationTag6").val("");
            // }
        },
        error: function (result) {
            //$("#tableIdTd").value("1111")
        }
    });
}

//创建bootstrap表格(元素代码集值)
function codeValTableModal(codeId) {
    $("#fieldCodeValModalTable").bootstrapTable({
        method: "post",
        contentType: "application/x-www-form-urlencoded",
        dataType: "json",
        url:  "codeValTable",
        sidePagination: "server", // 分页方式：client客户端分页，server服务端分页
        queryParams: function (params) {
            return {
                pageSize: params.limit,   //页面大小
                pageIndex: params.offset / params.limit + 1,  //页码
                valValue: $.trim($('#valValueInput').val()),
                valText: $.trim($('#valTextInput').val()),
                codeId: codeIdModal
            };
        },
        pagination: true,// 启动分页
        pageSize: 10,// 每页显示的记录条数
        pageNumber: 1,// 当前显示第几页
        pageList: [10, 20, 30, 50],
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
        clickToSelect: true,
        uniqueId: "id",
        columns: [
            {field: 'id', title: "主键id", valign: "middle", align: "center", colspan: 1, rowspan: 1, visible: false},
            {field: 'valText', title: "代码集名", valign: "middle", align: "center", colspan: 1, rowspan: 1},
            {field: 'valValue', title: "代码集值", valign: "middle", align: "center", colspan: 1, rowspan: 1},
            {field: 'sortIndex', title: "代码值顺序", valign: "middle", align: "center", colspan: 1, rowspan: 1},
            {field: 'memo', title: "备注", valign: "middle", align: "center", colspan: 1, rowspan: 1},
            {field: 'valTextTitle', title: "英文简写", valign: "middle", align: "center", colspan: 1, rowspan: 1},
            {field: 'codeId', title: "外键", valign: "middle", align: "center", colspan: 1, rowspan: 1}
        ],
        onLoadSuccess: function (data) {
            addTotalAndtitle('#fieldCodeValModal', data.length);
        }
    });
};

/**
 *查询按钮
 */
function selectDataModal() {
    $("#fieldCodeValModalTable").bootstrapTable('refresh');
}



/**
 * 节点点击事件
 * @param event
 * @param treeId
 * @param treeNode
 */
function zTreeOnClick(event, treeId, treeNode) {
    //如果是父节点就跳过
    if (treeNode.tableNodeFlag === false) {
        treeObj.expandNode(treeNode);
    } else {
        if (editFlag) {
            $("#saveModelObject").modal("show");
            $("#nodeIdModel").val(treeId);
            $("#nodeNameModel").val(treeNode);
            return;
        }
        // 判断
        clickTree(event, treeId, treeNode);
    }
}

function clickTree(event, treeId, treeNode) {
    // 判断
    approvalId = "";
    createApprovalId = "";
    $("#editResource").attr("style", "display:inline");
    $("#saveResource").attr("style", "display:none");
    removeAddButtonDisabled("save");
    sourceClassifyId = "";
    organizationClassifyId = "";
    clean6TagValue();
    tableAddRemoveBorder("table-object", "add");
    backFilePojo.mianClassfical = "";
    backFilePojo.subClassfical = "";
    backFilePojo.dataName = "";
    backFilePojo.tableName = "";
    // 如果是子节点就展示详情
    // 然后查询指定id的相关信息
    if (checkIsNull(treeNode.id)) {
        toastr.info("表" + treeNode.name + "的tableId为空");
    } else {
        // toastr.info("查询表" + treeNode.id + "的相关信息");
    }
    // 将select框默认选择成全列  输入框置空
    $("#needValueSelect").val("all");
    $("#searchInputContext").val("");
    // 查询数据信息
    creatObjectDetail(treeNode.id, "");
    // 查询字段定义的相关信息
    $("#resourceManageObjectField").bootstrapTable('removeAll');
    updateObjectField(treeNode.id);
    // 查询来源关系
    $("#sourceRelationShip").bootstrapTable('removeAll');
    sourceRelationShipDataGet(treeNode.id);
    editFlag = false;
    // 获取该tableId对应的已建表信息
    getCreatedTableData(treeNode.id);
    // 20200907  新增新的数据来源信息页面查询的接口
    getSourceRelationData(treeNode.id);

}


/**
 * 获取来源关系的信息
 */
function sourceRelationShipDataGet(tableId) {
    $.ajax({
        type: "post",
        data: {
            "tableId": tableId
        },
        url:  "sourceRelationShipDataGet",
        calculable: false,
        async: false,
        success: function (result) {
            $("#sourceRelationShip").bootstrapTable('load', result);
            // 20200217  新增全局数据
            if (checkIsNull(result)) {
                source_relationship_list = [];
            } else {
                source_relationship_list = result;
                console.log(source_relationship_list)
            }
            addTotalAndtitle('#sourceRelationShipTab', source_relationship_list.length);
        },
        error: function (result) {
            source_relationship_list = [];
        }
    });
}

/**
 * 来源关系的增加按钮
 */
function addSourceRelationClick() {
    if (!editFlag) {
        $("#statusCheckModal").modal("show");
        return;
    }
    // $("#tableNameMode").empty();
    $("#tableNameMode").selectpicker('val','');
    $("#sourceProtocolMode").val("");
    $("#sourceSystemMode").empty();
    $("#tableNameEtlCh").empty();
    $("#sourceSystemModeOrganizational").empty();
    getAllTableNameByDataBaseId();
    $.ajax({
        type: "get",
        url:  "getSysToSelect",
        contentType: 'application/json',
        dataType: 'json',
        calculable: false,
        async: false,
        success: function (result) {
            if (result.status === 1 && result.data != null) {
                result.data.forEach(function (oneData) {
                    $("#sourceSystemMode").append("<option value=" + oneData.value + ">" + oneData.label + "</option>");
                    $("#sourceSystemModeOrganizational").append("<option value=" + oneData.value + ">" + oneData.label + "</option>");

                });
              $("#sourceSystemMode").selectpicker('refresh');
              $("#sourceSystemModeOrganizational").selectpicker('refresh');
            } else {
                toastr.error(result.msg);
            }
        },
        error: function (result) {
            toastr.error("从sys表中获取数据报错");
            return;
        }
    });
    var objectId = $("#objectIdManage").text();
    // 20200226 开始 可以不选择具体的表
    // if(objectId.trim() === ""){
    //   toastr.error("请选择具体的表");
    // }else{
    $('#addSourceRelationModal').modal('show');
    // }
    // 获取所有的来源系统的代码和中文名称

    //  二级分类使用 layuiInput ，因为 当一级分类为
    mainClassChange("1");

    //---------------------------------------------------------------------------------------------------------
    if (GLOBAL.isFlow) {//如果是流程进来的,则回填
        //选择数据仓库
        var option = $("#sourceRelationSelectModal").find("option");
        for (var i = 0; i < option.length; i++) {
            if (option[i].value == "database") {
                $(option[i]).attr("selected", "selected");
            } else {
                $(option[i]).remove("selected");
            }
        }
        $("#sourceRelationSelectModal").attr("disabled", "disabled");
        sourceRelationModalChange();

        $("#dataBaseNameDiv").css("display", "none");
        $("#tableNameMode").empty();
        $("#tableNameMode").append("<option value='" + GLOBAL.dwParams.tableName + "' selected>" + GLOBAL.dwParams.tableName + "</option>");
        $("#tableNameMode").attr("disabled", "disabled");
        // $("#tableNameMode").val(GLOBAL.dwParams.tableName);//表名
        // $("#tableNameMode").attr("readonly","readonly");

        $("#sourceProtocolMode").val(GLOBAL.dwParams.sourceProtocol);//来源协议
        $("#sourceProtocolMode").attr("readonly", "readonly");

        // $("#sourceSystemMode").val(GLOBAL.dwParams.sourceSystem);//来源系统
        $("#sourceSystemMode").val(GLOBAL.dwParams.sourceSystem);//来源系统
        $("#sourceSystemMode").attr("disabled", "disabled");

        // $("#sourceFirmMode").text(GLOBAL.dwParams.sourceFirm);//来源单位
        $("#sourceFirmMode").find("option:contains('" + GLOBAL.dwParams.sourceFirm + "')").attr("selected", true);
        $("#sourceFirmMode").attr("disabled", "disabled");
    }
}

//--------------------------------  以下为增加来源关系的模态框 -----------------------------------
//增加页面中的select事件，选择不同的增加方式
function sourceRelationModalChange() {
    var addType = $("#sourceRelationSelectModal").val();
    // 根据选择的数据不同展示不同的选择框
    if (addType === "organizational") {
        $('.database').css('display', 'none');
        $('.organizational').css('display', 'block');
        mainClassChange("1");
    } else {
        $('.organizational').css('display', 'none');
        $('.database').css('display', 'block');
        // 获取所有的仓库名
        getAllDataBaseByUrl();
    }
}

//  大类的select改变的函数,根据选择的大类获取一级分类的信息
function mainClassChange(mainValue) {
    // var mainValue = $("#mainClassMode option:selected").val();
    $.ajax({
        type: "get",
        data: {
            "mainValue": mainValue
        },
        url:  "getFirstClassModeByMain",
        calculable: false,
        async: true,
        success: function (result) {
            $("#firstClassMode").empty();
            for (var i = 0; i < result.length; i++) {
                $("#firstClassMode").append("<option value=" + result[i].value + ">" + result[i].label + "</option>");
            }
            $("#firstClassMode").selectpicker('refresh');
            if (result != null && result.length > 0) {
                // 获取二级分类的相关信息
                firstClassChangeNew();
            } else {
                $("#secondaryClass").empty();
            }
        },
        error: function (result) {
            $("#firstClassMode").empty();
            $("#secondaryClass").empty();
            // createNewSuggest();
            toastr.error("在增加来源关系的模态框中获取一级分类信息报错")
        }
    });
}

// 获取二级分类的所有数据(当一级分类发生变化)
function firstClassChange() {
    var mainValue = $("#mainClassMode option:selected").val();
    var firstClassValue = $("#firstClassMode option:selected").val();
    $.ajax({
        type: "get",
        data: {
            "mainValue": mainValue,
            "firstClassValue": firstClassValue
        },
        url:  "getSecondaryClassModeByFirst",
        calculable: false,
        async: true,
        success: function (result) {
            $("#secondaryClass").empty();
            if (result != null) {
                for (var i = 0; i < result.length; i++) {
                    if (result[i] != null && result[i] != "") {
                        $("#secondaryClass").append("<option value=" + result[i].value + ">" + result[i].label + "</option>");
                    }
                }
            }
            createNewSuggest();
        },
        error: function (result) {
            $("#secondaryClass").empty();
            $("addTableMode").bsSuggest("destroy");
            suggest("addTableMode", "");
            toastr.error("在增加来源关系的模态框中获取二级分类信息报错")
        }
    });
}

/**
 * 创建搜索提示框
 */
function createNewSuggest() {
    document.getElementById("addTableMode").value = '';
    var mainValue = $("#mainClassMode option:selected").val();
    var firstValue = $("#firstClassMode option:selected").val();
    var secondaryValue = $("#secondaryClass option:selected").val();
    mainValue = mainValue == undefined ? "1" : mainValue;
    firstValue = firstValue == undefined ? "" : firstValue;
    secondaryValue = secondaryValue == undefined ? "" : secondaryValue;
    var url = "createNewSuggest?mainValue=" + mainValue + "&firstValue=" + firstValue
        + "&secondaryValue=" + secondaryValue + "&condition=";
    $("#addTableMode").bsSuggest("destroy");
    suggest("addTableMode", url);
    $("#addTableMode").css("width", "220px");
    $("#addTableMode").css("height", "32px");
}

/**
 * 获取所有的一级仓库名和二级仓库名
 */
function getAllDataBaseByUrl() {
    $.ajax({
        type: "get",
        url:  "getAllDataBaseByUrl",
        calculable: false,
        async: false,
        success: function (result) {
            $("#dataBaseNameMode").empty();
            for (var i = 0; i < result.length; i++) {
                $("#dataBaseNameMode").append("<option value=" + result[i].value + ">" + result[i].label + "</option>");
            }
            $("#dataBaseNameMode").selectpicker('refresh');
            if (result != null && result.length > 0) {
                // 获取数据仓库下所有的表信息
                getAllTableNameByDataBaseId();
            } else {
                $("#dataBaseNameMode").empty();
                $("#tableNameMode").empty();
            }
        },
        error: function (result) {
            $("#dataBaseNameMode").empty();
            $("#tableNameMode").empty();
            toastr.error("从数据仓库中获取所有信息报错")
        }
    });
}

/**
 *
 */
function getAllTableNameByDataBaseId() {
    try {
        var dataId = $("#dataBaseNameMode option:selected").val().split("@@")[1];
        $("#tableNameMode").empty();
        $("#sourceProtocolMode").val("");
        //  20200312 更改 需要获取审批通过后表名
        $.ajax({
            type: "get",
            url:  "getTableNameApprovaed",
            data: {
                "dataId": dataId,
                "schema": ""
            },
            calculable: false,
            async: false,
            success: function (result) {
                if (GLOBAL.isFlow) {

                } else {
                    $("#tableNameMode").selectpicker({liveSearch: true, size: 10, noneResultsText: "没有找到对应的记录{0}"});
                    if (result == null || result.length == 0) {
                        var executeMethod = getQueryParam("executeMethod");//从哪里跳转
                        if (!checkIsNull(executeMethod) && executeMethod.toLowerCase() === "dataresource") {
                            console.log("获取的表名信息为空");
                        } else {
                            toastr.error("获取的表名信息为空");
                        }
                    }else{
                        for (var i = 0; i < result.length; i++) {
                            $("#tableNameMode").append("<option value=" + result[i].value + ">" + result[i].label + "</option>");
                        }
                    }
                    $("#tableNameMode").selectpicker('refresh');
                }
            },
            error: function (result) {
                $("#tableNameMode").empty();
                toastr.error("从数据仓库中获取所有信息报错")
            }
        });
    } catch (e) {
        console.log(e);
    }
}

/**
 * 表名更改 获取来源协议 来源系统 来源单位
 */
function etlTableNameChange() {
    try{
        var dataBaseName =  $("#dataBaseNameMode option:selected").val();
        if(checkIsNull(dataBaseName)){
            return;
        }
        var dataId = dataBaseName.split("@@")[1];
        var projectTableName = $("#tableNameMode option:selected").val();
        console.log(dataId);
        console.log(projectTableName);
        if (checkIsNull(projectTableName) || checkIsNull(dataId)) {

        } else {
            $.ajax({
                type: "get",
                url:  "getTableInfoForStandard",
                data: {
                    "dataId": dataId,
                    "tableName": projectTableName
                },
                calculable: false,
                async: true,
                success: function (result) {
                    if (result == null) {
                        toastr.error("获取表【" + projectTableName + "】的来源信息报错");
                        return;
                    }
                    // 20201020 使用了  selectpicker   回填数据的方式发生变化
                    if (checkIsNull(result.resourceId)) {
                        $("#sourceProtocolMode").val("")
                    } else {
                        $("#sourceProtocolMode").val(result.resourceId);
                    }
                    if (checkIsNull(result.system)) {
                        // $("#sourceSystemMode").val("");//来源系统
                        $("#sourceSystemMode").selectpicker('val','');
                    } else {
                        // $("#sourceSystemMode").val(result.system);//来源系统
                        $("#sourceSystemMode").selectpicker('val',result.system);

                    }
                    if (checkIsNull(result.fromUnit)) {
                        // $("#sourceFirmMode").val('0');
                        $("#sourceFirmMode").selectpicker('val','0');
                        // $("#sourceFirmMode").find("option:contains('')").attr("selected",true);
                    } else {
                        // $("#sourceFirmMode").find("option[text='"+result.fromUnit+"']").attr("selected",true);
                        var code = getSourceFirmCode(result.fromUnit);
                        // $("#sourceFirmMode").val(code);
                        $("#sourceFirmMode").selectpicker('val',code);
                    }
                    // 表中文名
                    if (checkIsNull(result.tableName)) {
                        $("#tableNameEtlCh").val('');
                    } else {
                        $("#tableNameEtlCh").val(result.tableName);
                    }
                },
                error: function (result) {
                    toastr.error("获取表的来源关系报错");
                }
            });
        }
    }catch(e){
        console.log(e);
    }

}


/**
 *   模态框添加新的来源关系
 */
function addSourceRelationButton() {
    var addType = $("#sourceRelationSelectModal option:selected").val();
    // 获取输出协议对应的表协议
    var outputTableId = $("#tableId").text();

    if (addType === "organizational") {
        // 从组织资产中添加相关的数据 这个是标准里面的数据通过输入的表名来获取要插入的数据
        // 输入框中的表名
        var addTableName = $("#addTableMode").val().toUpperCase();
        //输入框改成select
        var addSourceSystem = $("#sourceSystemModeOrganizational option:selected").val();
        var addSourceFirm = $("#sourceFirmModeOrganizational option:selected").val();
        if (addTableName.trim() === "") {
            toastr.error("需要添加的表名不能为空");
            return;
        }
        if (!checkIsNumber(addSourceSystem) || addSourceSystem.trim() === "") {
            toastr.error("来源系统代码必须为数字");
            return;
        }
        $.ajax({
            type: "get",
            url: "addSourceRelationByTableName",
            data: {
                "tableName": addTableName,
                "addType": addType,
                "outputTableId": outputTableId,
                "sourceSystem": addSourceSystem,
                "sourceFirm": addSourceFirm
            },
            calculable: false,
            async: true,
            success: function (result) {
                if (result.status == 1) {
                    toastr.info(result.msg);
                    //隐藏添加框
                    // 查询来源关系
                    $("#sourceRelationShip").bootstrapTable('removeAll');
                    sourceRelationShipDataGet(outputTableId);
                    $('#addSourceRelationModal').modal('hide');
                    $("#addTableMode").val("");
                    // $("#sourceSystemModeOrganizational").val("");
                } else {
                    toastr.error(result.msg);
                }
            },
            error: function (result) {
                toastr.error("添加新的来源关系报错");
            }
        });
    } else {//数据仓库中添加
        // 保存后判断来源协议和来源系统 来源单位是否为空，为空不能保存
        var sourceProtocolMode = $("#sourceProtocolMode").val();
        var sourceSystemMode = $("#sourceSystemMode option:selected").val();
        var sourceFirmMode = $("#sourceFirmMode option:selected").text();
        var tableName = $("#tableNameMode option:selected").val();
        if (!checkIsNumber(sourceSystemMode)) {
            toastr.error("来源系统代码应该为数字");
            return;
        }
        if (checkIsNull(sourceProtocolMode) || checkIsNull(sourceSystemMode)
            || checkIsNull(sourceFirmMode) || checkIsNull(tableName)) {
            toastr.error("来源数据协议/来源系统代码/来源厂商/表名/目标表协议存在空值，请填写该值");
            return;
        }
        // 来源单位是中文
        $.ajax({
            type: "get",
            url:  "addSourceRelationByEtlMessage",
            data: {
                "sourceProtocol": sourceProtocolMode.toUpperCase(),
                "sourceSystem": sourceSystemMode,
                "sourceFirm": sourceFirmMode,
                "tableName": tableName.toUpperCase(),
                "tableId": outputTableId
            },
            calculable: false,
            async: true,
            success: function (result) {
                if (GLOBAL.isFlow) {
                    $("#sourceRelationShip").bootstrapTable('removeAll');
                    sourceRelationShipDataGet(outputTableId);
                    $('#addSourceRelationModal').modal('hide');
                } else {
                    if (result.status == 1) {
                        toastr.info(result.msg);
                        //隐藏添加框
                        // 查询来源关系
                        $("#sourceRelationShip").bootstrapTable('removeAll');
                        sourceRelationShipDataGet(outputTableId);
                        $('#addSourceRelationModal').modal('hide');
                        //清空输入框
                        $("#sourceProtocolMode").val("");
                        // $("#sourceSystemMode").val("");
                        // $("#sourceFirmMode").val("");
                    } else {
                        toastr.error(result.msg);
                    }
                }

            },
            error: function (result) {
                toastr.error("添加新的来源关系报错");
            }
        });

        if (!GLOBAL.isFlow) {
            // 如果是从数据仓库添加，则将从数据仓库中添加表字段信息
            // 数据仓库Id
            var dataCenterId = $("#dataBaseNameMode option:selected").val().split("@@")[1];
            // 包括项目名的表名
            $.ajax({
                type: "get",
                url:  "addTableColumnByEtl",
                data: {
                    "sourceProtocol": sourceProtocolMode.toUpperCase(),
                    "sourceSystem": sourceSystemMode,
                    "sourceFirm": sourceFirmMode,
                    "tableName": tableName.toUpperCase(),
                    "tableId": outputTableId,
                    "dataCenterId": dataCenterId
                },
                calculable: false,
                async: true,
                success: function (result) {
                    if (result.status === 1) {

                    } else {
                        toastr.error(result.msg)
                    }
                },
                error: function (result) {
                    toastr.error("将表字段信息存储在数据库中报错");
                }
            });
        }
    }
}

/**
 * 将来源关系插入到页面中
 */
function addSourceRelationPageButton() {
    var addType = $("#sourceRelationSelectModal option:selected").val();
    // 获取输出协议对应的表协议
    var outputTableId = $("#tableIdInput").val();

    if (addType === "organizational") {
        // 从组织资产中添加相关的数据 这个是标准里面的数据通过输入的表名来获取要插入的数据
        // 输入框中的表名
        var addTableName = $("#addTableMode").val().toUpperCase();
        //输入框改成select
        var addSourceSystem = $("#sourceSystemModeOrganizational option:selected").val();
        var sourceSystemModeCh = $("#sourceSystemModeOrganizational option:selected").text();
        var addSourceFirm = $("#sourceFirmModeOrganizational option:selected").val();
        var addSourceFirmCh = $("#sourceFirmModeOrganizational option:selected").text();
        if (addTableName.trim() === "") {
            toastr.error("需要添加的表名不能为空");
            return;
        }
        if (!checkIsNumber(addSourceSystem) || addSourceSystem.trim() === "") {
            toastr.error("来源系统代码必须为数字");
            return;
        }
        //判断　一级／二级分类是否
        var objectAll = getOrganizationRelation(addTableName);
        if (objectAll === null || checkIsNull(objectAll.sourceId)) {
            toastr.error("表" + addTableName + "不是标准表");
            return;
        }
        //  拼接来源关系的数据
        var object = new Object();
        object.dataSourceName = objectAll.dataSourceName;
        object.realTableName = addTableName;
        // 来源数据协议
        object.sourceSystem = objectAll.sourceId;
        object.sourceProtocol = addSourceSystem;
        object.sourceProtocolCh = sourceSystemModeCh;
        object.sourceFirm = addSourceFirmCh;
        object.storageTableStatus = "正式使用";
        object.addType = "organizational";
        // 需要从后台获取其它信息
        var date = new Date();
        object.insertDate = date;
        //  判断该数据来源是否已经存在
        var isExist = false;
        source_relationship_list.forEach(function (value) {
            if (object.sourceSystem.toLowerCase() === value.sourceSystem.toLowerCase() &&
                object.sourceProtocol === value.sourceProtocol) {
                isExist = true;
            }
        });
        if (isExist) {
            toastr.info("该来源协议已经插入，不能重新插入");
        } else {
            source_relationship_list.push(object);
            $('#addSourceRelationModal').modal('hide');
            //清空输入框
            // $("#addTableMode").val("");
            $('#sourceRelationShip').bootstrapTable('load', source_relationship_list);
            addTotalAndtitle('#sourceRelationShipTab', source_relationship_list.length);
        }
    } else {
        // 保存后判断来源协议和来源系统 来源单位是否为空，为空不能保存
        var sourceProtocolMode = $("#sourceProtocolMode").val();
        var sourceSystemMode = $("#sourceSystemMode option:selected").val();
        var sourceSystemModeCh = $("#sourceSystemMode option:selected").text();
        var sourceFirmMode = $("#sourceFirmMode option:selected").text();
        var tableName = $("#tableNameMode option:selected").val();
        var tableNameCh = $("#tableNameEtlCh").val();
        if (!checkIsNumber(sourceSystemMode)) {
            toastr.error("来源系统代码应该为数字");
            return;
        }
        if (checkIsNull(sourceProtocolMode) || checkIsNull(sourceSystemMode)
            || checkIsNull(sourceFirmMode)) {
            toastr.error("来源数据协议/来源系统代码/来源厂商 存在空值，请填写该值");
            return;
        }
        if(checkIsNull(tableNameCh)){
            toastr.error("表中文名不能为空，请填写表中文名");
            return;
        }
        // TODO 20200519 tableId 和 tableName 最后保存的时候获取
        //  拼接来源关系的数据
        var object = new Object();
        //
        // if (tableName.split(".").length === 2) {
        //     object.dataSourceName = tableName.split(".")[1].toUpperCase();
        // } else {
        //     object.dataSourceName = tableName.split(".")[0].toUpperCase();
        // }
        object.dataSourceName = tableNameCh;
        object.realTableName = tableName.toUpperCase();
        // 来源数据协议
        object.sourceSystem = sourceProtocolMode.toUpperCase();
        object.sourceProtocol = sourceSystemMode;
        object.sourceProtocolCh = sourceSystemModeCh;
        object.sourceFirm = sourceFirmMode;
        object.storageTableStatus = "正式使用";
        object.addType = "database";
        object.tableId = tableName;
        var date = new Date();
        object.insertDate = date;
        var ids = $("#dataBaseNameMode option:selected").val().split("@@");
        console.log(ids);
        object.dataId = ids[1];
        if (checkIsNull(centerId.trim())) {
            object.centerId = ids[0];
        } else {
            object.centerId = centerId;
        }
        console.log(object.centerId);
        //  将从数据仓库中获取到的信息插入到全局变量中
        //  判断该数据来源是否已经存在
        var isExist = false;
        source_relationship_list.forEach(function (value) {
            if (object.sourceSystem.toLowerCase() === value.sourceSystem.toLowerCase() &&
                object.sourceProtocol === value.sourceProtocol) {
                isExist = true;
            }
        });
        if (isExist) {
            toastr.info("该来源协议已经插入，不能重新插入");
        } else {
            source_relationship_list.push(object);
            $('#addSourceRelationModal').modal('hide');
            //清空输入框
            $("#sourceProtocolMode").val("");
            $('#sourceRelationShip').bootstrapTable('load', source_relationship_list);
            addTotalAndtitle('#sourceRelationShipTab', source_relationship_list.length);

            // 20200911 当新增这个之后 需要查询数据仓库的接口来获取对应的 数据来源信息 回填到对应的页面
            if(!checkIsNull(object.dataId) && !checkIsNull(object.tableId)){
                getResourceTableDataUrl(object.dataId,object.tableId);
            }
        }
        centerId = "";
        // 来源单位是中文
        if (!GLOBAL.isFlow) {
            // 如果是从数据仓库添加，则将从数据仓库中添加表字段信息
            // 数据仓库Id
            var dataCenterId = $("#dataBaseNameMode option:selected").val().split("@@")[1];
            // 包括项目名的表名
            $.ajax({
                type: "get",
                url:  "addTableColumnByEtl",
                data: {
                    "sourceProtocol": sourceProtocolMode.toUpperCase(),
                    "sourceSystem": sourceSystemMode,
                    "sourceFirm": sourceFirmMode,
                    "tableName": tableName,
                    "tableId": sourceProtocolMode.toUpperCase(),
                    "dataCenterId": dataCenterId
                },
                calculable: false,
                async: true,
                success: function (result) {
                    if (result.status === 1) {

                    } else {
                        toastr.error(result.msg)
                    }
                },
                error: function (result) {
                    toastr.error("将表字段信息存储在数据库中报错");
                }
            });
        }
        originalTableIdChange()
    }
}

/**
 * 删除对应的来源关系
 */
function deleteSourceRelation() {
    if (!editFlag) {
        $("#statusCheckModal").modal("show");
        return;
    }
    var getSelectRows = $('#sourceRelationShip').bootstrapTable('getSelections');
    var outputTableId = $("#tableId").text();
    if (getSelectRows.length <= 0) {
        toastr.error("请先选择一条数据!");
        return;
    } else {
        bootbox.confirm({
            size: "small",
            backdrop: true,
            title:"<span style='font-size: 16px'>提示</span>",
            message: "<img src='img/myImg/warn.png' style='margin: -5px 10px 0 0;'><span>是否要删除所选数据？</span>",
            buttons: {
                cancel: {
                    label: '取消',
                    className: 'my-btn-cancle'
                },
                confirm: {
                    label: '确定',
                    className: 'my-btn-confirm'
                }
            },
            callback: function (result) {
                if (result) {
                    var deleteId = 0;
                    for (var i = 0; i < source_relationship_list.length; i++) {
                        var value = source_relationship_list[i];
                        if (getSelectRows[0].sourceSystem.toLowerCase() === value.sourceSystem.toLowerCase() &&
                            getSelectRows[0].sourceProtocol === value.sourceProtocol) {
                            deleteId = i;
                        }
                    }
                    source_relationship_list.splice(deleteId, 1);
                    $('#sourceRelationShip').bootstrapTable('load', source_relationship_list);
                    addTotalAndtitle('#sourceRelationShipTab', source_relationship_list.length)
                    $('#addSourceRelationModal').modal('hide');
                    // $.ajax({
                    //   type:"post",
                    //   url:path+"/deleteSourceRelation",
                    //   contentType:'application/json',
                    //   dataType:'json',
                    //   data: JSON.stringify({
                    //     'delSourceRelation':getSelectRows,
                    //     'outputDataId':outputTableId
                    //   }),
                    //   calculable : false,
                    //   async:false,
                    //   success:function(result){
                    //     if(result.status == 1){
                    //       toastr.info(result.msg);
                    //       sourceRelationShipDataGet(outputTableId);
                    //       $('#addSourceRelationModal').modal('hide');
                    //     }else{
                    //       toastr.error(result.msg)
                    //     }
                    //   },
                    //   error:function (result) {
                    //     toastr.error("删除来源关系失败");
                    //     return;
                    //   }
                    // });
                }
            }
        });
    }
}

// 清空 input里面的值 和select
function inputSelectClean() {
    // $(".addColumnInput").val();
    $("#saveObjectField input[type=text]").val("");
    // $("#fieldTypeAddModel option").removeAttr("selected");
    // $("#SensitivityLevel option").removeAttr("selected");
    // $("#fieldTypeAddModel ").val("-1");
    $("#SensitivityLevel").selectpicker('val',"");
    $("#fieldTypeAddModel").selectpicker('val',"-1");
}

// 关闭模态框
function closeModal() {
    inputSelectClean();
    initOtherSelect();
    downUpClick();
    // 搜索提示框会存在历史数据 但是 我这边不需要这个
    $("#fieldNameAddModelUL").html("");
    $('#saveObjectField').modal('hide');

}

function closemodal() {
    $("#createTableContent input[type=text]").val("");
    $("#dataResourceType").val("selected");
    $("#partitionFirst").empty();
    $("#partitionSecond").empty();
    $('#createTableContent').modal('hide');
    // 关闭之后需要清空 高级里面的表格和建表的SQL
    $("#updateColumnCreateTable").bootstrapTable("destroy");
    $("#createSqlTextarea").text("");
    createTablePreviousStep();
    // $("#createdTable").text("");
    // 关闭之后要将
    $(".aliDataBase").css("display", "none");
    $(".prestoDataBase").css("display", "none");
    $("#createTableMemo").val("");
}

/**
 *  刷新字段定义表 主要用于获取来源关系的内容
 */
function refreshColumnTableByRelation() {

}

/**
 *  给数据信息的表格移除边框
 */
function tableAddRemoveBorder(tableId, flag) {
    if (flag === "remove") {
        $("#" + tableId + " td").each(function () {
            $(this).css("border", "0px");
            if (checkIsNull($(this)[0].id)) {
                // if ($(this)[0].innerText === "描述") {
                //     $(this).css("transform", "translateY(35%)");
                // } else {
                //     $(this).css("transform", "translateY(20%)");
                // }
            } else {
            }
        });
        $("#" + tableId).css("border", "0px")

    } else {
        $("#" + tableId + " td").each(function () {
             $(this).css("border", "1px solid #f5f5f5");
            $(this).css("transform", "");
        });
         $("#" + tableId).css("border", "1px solid #f5f5f5")
    }
}

/**
 *  删除 button 的 disabled 属性
 */
function removeAddButtonDisabled(type) {
    if (type === "edit") {
        $("#addOrUpdateObjectField").attr("disabled", false);
        $("#UpdateObjectField").attr("disabled", false);
        $("#deleteObjectField").attr("disabled", false);
        $("#createTable").attr("disabled", "disabled");
        $("#addColumn").attr("disabled", false);
        $("#dropdownMenu1").attr("disabled", "disabled");
        // $("#deleteSourceRelationClick").attr("disabled", false);
        // $("#addSourceRelationClick").attr("disabled", false);
        $("#tableRegister").attr("disabled", "disabled");
    } else {
        /*$("#addOrUpdateObjectField").attr("disabled", "disabled");
        $("#UpdateObjectField").attr("disabled", "disabled");
        $("#deleteObjectField").attr("disabled", "disabled");
        $("#createTable").attr("disabled", false);
        $("#addColumn").attr("disabled", "disabled");*/

        $("#addOrUpdateObjectField").attr("disabled", false);
        $("#UpdateObjectField").attr("disabled", false);
        $("#deleteObjectField").attr("disabled", false);
        $("#createTable").attr("disabled", false);
        $("#addColumn").attr("disabled", false);

        $("#dropdownMenu1").attr("disabled", false);
        $("#tableRegister").attr("disabled", false);
        // $("#deleteSourceRelationClick").attr("disabled", "disabled");
        // $("#addSourceRelationClick").attr("disabled", "disabled");
    }

}

/**
 * 数据信息表中的修改代码
 * 如果是新增的即objectId为空时，除了objectId，其它都可以修改
 * 如果是编辑状态 除了 objectId和 tableId，其它都可以修改
 */
function editResourceManageTable(triggerType) {
    editFlag = true;
    removeAddButtonDisabled("edit")
    tableAddRemoveBorder("table-object", "remove");
    // 将编辑按钮隐藏 将保存按钮显示出来
    $("#editResource").attr("style", "display:none");
    $("#saveResource").attr("style", "display:inline");
    $("#cancelResource").attr("style", "display:inline");
    sourceClassifyId = "";
    organizationClassifyId = "";
    backFilePojo.mianClassfical = "";
    backFilePojo.subClassfical = "";
    backFilePojo.dataName = "";
    var objectId = $("#objectIdManage").text();
    var tableId = $("#tableId").text();
    $("#tableId").text("");
    if (GLOBAL.isFlow) {
        $("#tableId").append("<input id=\"tableIdInput\"  type=\"text\" class=\"form-control\" autocomplete=\"off\">");
        $("#tableIdInput").val(tableId.trim());
    }
    // 系统代码需要使用中文和数字，先从后台获取数据
    getSysChiNum();
    var sourceId = $("#sourceId").text();
    $("#sourceId").text("");
    $("#sourceId").append("<input id=\"sourceIdInput\"  type=\"text\" style='width:100%;height: 32px;' class=\"form-control\" autocomplete=\"off\" >");
    $("#sourceIdInput").val(sourceId);
    var objectMemo = $("#objectMemo").text();
    $("#objectMemo").text("");
    $("#objectMemo").append("<textarea id=\"objectMemoInput\"  type=\"text\" style='width:100%;' class=\"form-control\" autocomplete=\"off\" rows='3'>");
    $("#objectMemoInput").val(objectMemo.trim());
    // 数据名 真实表名 系统代码 可以直接修改
    var dataSourceName = $("#dataSourceName").text();
    $("#dataSourceName").text("");
    $("#dataSourceName").append("<input id=\"dataSourceNameInput\"  type=\"text\" style='width:100%;height: 32px;' class=\"form-control\" autocomplete=\"off\" >");
    $("#dataSourceNameInput").val(dataSourceName.trim());
    var realTableName = $("#realTableName").text();

    // 协议厂商 存储表状态  存储方式使用select
    // 以下为协议厂商
    var sourceFirmStr = $("#sourceFirm").text();
    $("#sourceFirm").text("");
    $("#sourceFirm").append("<select id=\"sourceFirmTdSelect\" class=\"form-control selectpicker\"></select>");
    allSourceFirmList.forEach(function (value) {
        if (value === sourceFirmStr) {
            $("#sourceFirmTdSelect").append("<option value=" + value + " selected>" + value + "</option>");
        } else {
            $("#sourceFirmTdSelect").append("<option value=" + value + ">" + value + "</option>");
        }
    });
    if (objectId === "") {
      $("#tableId").append("<input id=\"tableIdInput\"  type=\"text\" style='width:100%;height: 32px;' class=\"form-control\" autocomplete=\"off\">");
      $("#tableIdInput").val(tableId.trim());
      $("#sourceFirmTdSelect").removeAttr("disabled");
      $("#objectNameSelect").removeAttr("disabled")

    } else {
      $("#tableId").append("<input id=\"tableIdInput\"  type=\"text\" style='width:100%;height: 32px;' class=\"form-control\" " +
        "autocomplete=\"off\" title='"+tableId.trim()+"' readonly>");
      $("#tableIdInput").val(tableId.trim());
      $("#sourceFirmTdSelect").attr("disabled","disabled");
      $("#objectNameSelect").attr("disabled","disabled");

    }
    // 以下为存储表状态
    var storageTableStatus = $("#storageTableStatus").text();
    $("#storageTableStatus").text("");
    $("#storageTableStatus").append("<select id=\"storageTableStatusTdSelect\" class=\"form-control selectpicker\" style='width: 361px'></select>");
    storageTableStatusList.forEach(function (value) {
        if (value === storageTableStatus) {
            $("#storageTableStatusTdSelect").append("<option value=" + value + " selected>" + value + "</option>");
        } else {
            $("#storageTableStatusTdSelect").append("<option value=" + value + ">" + value + "</option>");
        }
        $('.selectpicker').selectpicker('refresh');
        $('.selectpicker').selectpicker('render');
    });
    // 以下为更新表类型
    var isActiveTable = $("#isActiveTable").text();
    $("#isActiveTable").text("");
    $("#isActiveTable").append("<select id=\"isActiveTableTdSelect\" class=\"form-control selectpicker\"></select>");
    isActiveTableList.forEach(function (obj) {
        if (obj.label === isActiveTable) {
            $("#isActiveTableTdSelect").append("<option value=" + obj.value + " selected>" + obj.label + "</option>");
        } else {
            $("#isActiveTableTdSelect").append("<option value=" + obj.value + ">" + obj.label + "</option>");
        }
        $('.selectpicker').selectpicker('refresh');
        $('.selectpicker').selectpicker('render');
    });
    // 20201010 修改 定义创建时间和修改人 时间
    // 更新人获取 url 中的 userName
    var userName = getUserName();
    var creator = $("#creator").text();
    $("#creator").text("");
    $("#creator").append("<input id=\"creatorInput\"  type=\"text\" style='width:100%;height: 32px;' class=\"form-control\" autocomplete=\"off\" readonly>");
    if(checkIsNull($("#objectIdManage").text())){
        $("#creatorInput").val(userName);
    }else{
        $("#creatorInput").val(creator);
    }
    var createTime = $("#createTime").text();
    $("#createTime").text("");
    $("#createTime").append("<input id=\"createTimeInput\"  type=\"text\" style='width:100%;height: 32px;' class=\"form-control\" autocomplete=\"off\" readonly>");
    if(checkIsNull(createTime)){
        $("#createTimeInput").val(new Date().Format("yyyy-MM-dd hh:mm:ss"));
    }else{
        $("#createTimeInput").val(createTime);
    }

    // var updater = $("#updater").text();
    $("#updater").text("");
    $("#updater").append("<input id=\"updaterInput\"  type=\"text\" style='width:100%;height: 32px;' class=\"form-control\" autocomplete=\"off\" readonly>");
    $("#updaterInput").val(userName);
    var updateTime = new Date().Format("yyyy-MM-dd hh:mm:ss");
    $("#updateTime").text("");
    $("#updateTime").append("<input id=\"updateTimeInput\"  type=\"text\" style='width:100%;height: 32px;' class=\"form-control\" autocomplete=\"off\" readonly>");
    $("#updateTimeInput").val(updateTime);

    // 以下为存储方式
    // var storageMode = $("#storageMode").text();
    // $("#storageMode").text("");
    // $("#storageMode").append("<select id=\"storageModeTdSelect\" class=\"form-control\"></select>");
    // storageModeList.forEach(function (value) {
    //   if(value === storageMode){
    //     $("#storageModeTdSelect").append("<option value="+value+" selected>"+value+"</option>");
    //   }else{
    //     $("#storageModeTdSelect").append("<option value="+value+">"+value+"</option>");
    //   }
    // });
    // 6类标签的编辑页面修改 20200318
    // 6类标签不再需要
    tagSelectPageEdit();

    // -------------- select结束----------------------------
    //  数据来源的级联选择器 将级联选择器打开
    // 　20200224　分类方式只有数据组织分类　所以去除掉数据来源分类
    var oldOrganizationClassify = $("#organizationClassify").text();
    var oldSourceClassify = $("#sourceClassify").text();
    var organizationClassifyList = null;
    var sourceClassifyList = null;

    $("#organizationClassify").text("");
    $("#sourceClassify").text("");
    $("#organizationClassify").append("<div><div class=\"layui-input-block\" style='margin-left: 0px'>\n" +
        "\t\t\t\t\t\t\t<input type=\"text\" id=\"organizationClassifyInput\" class=\"layui-input\" style='height: 32px;border-radius: 4px' readonly=\"readonly\">\n" +
        "\t\t\t\t\t\t</div></div>");
    $("#sourceClassify").append("<div><div class=\"layui-input-block\" style='margin-left: 0px'>\n" +
        "\t\t\t\t\t\t\t<input type=\"text\" id=\"sourceClassifyInput\" class=\"layui-input\" style='height: 32px;border-radius: 4px' readonly=\"readonly\">\n" +
        "\t\t\t\t\t\t\t</div></div>");
    if (oldOrganizationClassify.trim() === "" || oldOrganizationClassify === "未知/未知") {
        organizationClassifyList = [];
        // $("#organizationClassifyInput").text(oldOrganizationClassify);
    } else {
        organizationClassifyList = oldOrganizationClassify.split("/");
    }
    if (oldSourceClassify.trim() === "" || oldSourceClassify.trim() === "/" || oldOrganizationClassify === "未知/未知") {
        sourceClassifyList = [];
    } else {
        sourceClassifyList = oldSourceClassify.split("/");
    }


    //  查找分级分类的信息　　
    //  20200224分级分类的方式发生变化　不再从dubbo中获取，直接从数据库中获取
    $.ajax({
        type: "get",
        url: "getAllClassifyLayui",
        contentType: 'application/json',
        dataType: 'json',
        calculable: false,
        async: false,
        data: {
            "mainClassifyCh": "数据组织分类"
        },
        success: function (result) {
            if (result.status === 1) {
                layuiInputBuild("organizationClassifyInput", result.result, organizationClassifyList,triggerType);
            } else {
                toastr.error(result.msg);
            }
        },
        error: function (result) {
            toastr.error("获取分级分类的信息报错");
            return;
        }
    });
    $.ajax({
        type: "get",
        url:  "getAllClassifyLayui",
        contentType: 'application/json',
        dataType: 'json',
        calculable: false,
        async: false,
        data: {
            "mainClassifyCh": "数据来源分类"
        },
        success: function (result) {
            if (result.status === 1) {
                layuiInputBuild2("sourceClassifyInput", result.result, sourceClassifyList,triggerType);
            } else {
                toastr.error(result.msg);
            }
        },
        error: function (result) {
            toastr.error("获取分级分类的信息报错");
            return;
        }
    });
    $("#realTableName").text("");
    $("#realTableName").append("<input id=\"realTableNameInput\"  type=\"text\" style='width:100%;height: 32px;' class=\"form-control\" autocomplete=\"off\" >");
    $("#realTableNameInput").val(realTableName.trim());
    // 查询该表是否已经创建，如果已经创建，则该表名不能修改
    queryTableIsExist(realTableName, "realTableNameInput");
    $('.selectpicker').selectpicker('refresh');
    $('.selectpicker').selectpicker('render');
}

/**
 * 获取系统的代号和中文名称
 */
function getSysChiNum() {
    var objectName = $("#objectName").val();
    $("#objectName").text("");
    $("#objectName").val("");
    $("#objectName").append("<select id=\"objectNameSelect\" class=\"form-control selectpicker\"></select>");
    $.ajax({
        type: "get",
        url:  "getSysToSelect",
        contentType: 'application/json',
        dataType: 'json',
        calculable: false,
        async: false,
        success: function (result) {
            if (result.status === 1 && result.data != null) {
                result.data.forEach(function (oneData) {
                    if (oneData.value === objectName) {
                        $("#objectNameSelect").append("<option value=" + oneData.value + " selected>" + oneData.label + "</option>");
                    } else {
                        $("#objectNameSelect").append("<option value=" + oneData.value + ">" + oneData.label + "</option>");
                    }
                });
            } else {
                toastr.error(result.msg);
            }
        },
        error: function (result) {
            toastr.error("从sys表中获取数据报错");
            return;
        }
    });
    $('.selectpicker').selectpicker('refresh');
    $('.selectpicker').selectpicker('render');
}


/**
 * 添加新的一种数据
 */
function addResourceManageObject() {
    // location.parent.reload()
    // console.log(path+"/resourceManage");
    // var url = encodeURIComponent(path+"/resourceManage") + '&title=标准管理';
    // var url = "src=http%3A%2F%2F176.101.200.16%3A8123%2FdataStandardManager%2FresourceManage";
    // location.replace(location);
    if (editFlag) {
        $("#saveModelObjectTwo").modal("show");
        return;
    }
    if(!checkIsNull(getQueryParam("moduleName"))){
        location.reload();
    }else{
        sessionStorage.setItem('refreshPageCook', true);
        window.parent.location = pageUrl;
    }



}

function edit() {
    toastr.info("开始添加新的标准信息");
    editResourceManageTable('click');
}

/**
 * 对于要修改的数据信息 保存对应的结果
 * @20200225 修改需求 点击保存之后  保存 数据信息/字段定义/来源关系页面上信息
 *    数据组织的分级分类直接修改数据库中表 tableorg，这个是与资源服务平台共用的
 *    @20200911 新增了数据来源信息的页面数据  数据存储在 PUBLIC_DATA_INFO
 *    20210126 数据来源信息和数据接入信息可能会存在旧数据 所以在保存前先判断
 *       更新时间是否是最新的，不是的话需要重新刷新
 */
function saveResourceManageTable() {
    addMask();
    var flag = false;
    try{
        //点击保存之时重新获取表id  TODO
        getNewSourceId();
        saveOriginalTableIdChange();
        var obj = getAllStandardObjectManage();
        if (obj === null) {
            $.unblockUI();
            return false;
        }
        // 获取 数据来源信息
        getDataSourceInformationPage(obj);
        var data = JSON.stringify(obj);
        $.ajax({
            type: "post",
            url:  "saveResourceManageTable",
            contentType: 'application/json',
            dataType: 'json',
            data: data,
            calculable: false,
            async: false,
            success: function (result) {
                if (result.status === 1) {
                    //  如果是新增的标准，则需要跳出弹出框，显示审批流程的一个页面
                    if (checkIsNull(obj.objectId) && !GLOBAL.isFlow && result.data.approvalInfo === "true") {
                        var iframeUrl = result.data.message;
                        $('#approvalInfoHtml').attr('src', iframeUrl);
                        $("#importApprovalInfoModal").modal('show');
                        $.unblockUI();
                        return false;
                    }
                    treeRefresh();
                    // 将输入框去除 重新显示真实页面
                    $("#editResource").attr("style", "display:inline");
                    $("#saveResource").attr("style", "display:none");
                    $("#cancelResource").attr("style", "display:none");

                    var dataSourceName = $("#dataSourceNameInput").val();
                    $("#dataSourceName").text("");
                    $("#dataSourceName").text(dataSourceName);

                    var realTableName = $("#realTableNameInput").val();
                    $("#realTableName").text("");
                    $("#realTableName").text(realTableName);

                    var tableId = $("#tableIdInput").val();
                    $("#tableId").text("");
                    $("#tableId").text(tableId);

                    var sourceId = $("#sourceIdInput").val();
                    $("#sourceId").text("");
                    $("#sourceId").text(sourceId);

                    var objectMemo = $("#objectMemoInput").val();
                    $("#objectMemo").text("");
                    $("#objectMemo").text(objectMemo);
                    // 6类标签的保存
                    saveRemoveSelect();
                    // $("#objectName").attr("contenteditable","false");
                    var tableId = $("#tableId").text();
                    sourceClassifyId = "";
                    organizationClassifyId = "";
                    if (checkIsNull(tableId)) {
                        location.reload();
                    } else {
                        creatObjectDetail(tableId, "");
                    }
                    // 保存之后重新获取 数据信息
                    // creatObjectDetail($("#tableId").text() , "");
                    // 修改之后重新获取来源关系
                    sourceRelationClick();
                    // 重新查询字段信息
                    $("#resourceManageObjectField").bootstrapTable('removeAll');
                    updateObjectField($("#tableId").text());
                    editFlag = false;
                    if (GLOBAL.isFlow) {
                        flowCompleteClick();
                    }
                    tableAddRemoveBorder("table-object", "add");
                    removeAddButtonDisabled("save");
                    flag = true;
                    getSourceRelationData($("#tableId").text());
                } else {
                    toastr.error(result.msg);
                    $.unblockUI();
                    flag = false;
                    // $("#editResource").attr("style","display:inline");
                    // $("#saveResource").attr("style","display:none");
                    // $("#dataSourceName").attr("contenteditable","false");
                    // $("#realTableName").attr("contenteditable","false");
                    // $("#objectName").attr("contenteditable","false");
                    // $("#tableId").attr("contenteditable","false");
                    // var tableId = $("#tableId").text();
                    // sourceClassifyId = "";
                    // organizationClassifyId = "";
                    // if(checkIsNull(tableId)){
                    //   location.reload();
                    // }else{
                    //   creatObjectDetail(tableId , "");
                    // }
                }
                $.unblockUI();
            },
            error: function (result) {
                toastr.error("保存数据信息报错");
                $.unblockUI();
                flag = false;
            }
        });
    }catch(e){
        toastr.error("保存数据信息报错"+e);
        $.unblockUI();
        flag = false;
    }
    return flag;
}

/**
 * 取消数据定义的编辑状态
 */
function cancelEditModel() {
    bootbox.confirm({
        size: "small",
        backdrop: true,
        title:"<span style='font-size: 16px;'>提示</span>",
        message: "<img src='img/myImg/warn.png' style='margin: -5px 10px 0 0;float: left'><span>取消后数据将丢失，是否取消？</span>",
        buttons : {
            confirm : {
                label : '是',
                className : 'my-btn-confirm'
            },
            cancel : {
                label : '否',
                className : 'my-btn-cancle'
            }
        },
        callback : function(result) {
            if (result) {

                // treeRefresh();
                // 将输入框去除 重新显示真实页面
                $("#editResource").attr("style", "display:inline");
                $("#saveResource").attr("style", "display:none");
                $("#cancelResource").attr("style", "display:none");

                var dataSourceName = $("#dataSourceNameInput").val();
                $("#dataSourceName").text("");
                $("#dataSourceName").text(dataSourceName);

                var realTableName = $("#realTableNameInput").val();
                $("#realTableName").text("");
                $("#realTableName").text(realTableName);

                var tableId = $("#tableIdInput").val();
                $("#tableId").text("");
                $("#tableId").text(tableId);

                var sourceId = $("#sourceIdInput").val();
                $("#sourceId").text("");
                $("#sourceId").text(sourceId);

                var objectMemo = $("#objectMemoInput").val();
                $("#objectMemo").text("");
                $("#objectMemo").text(objectMemo);
                // 6类标签的保存
                saveRemoveSelect();
                // $("#objectName").attr("contenteditable","false");
                var tableId = $("#tableId").text();
                sourceClassifyId = "";
                organizationClassifyId = "";
                if (checkIsNull(tableId)) {
                    location.reload();
                } else {
                    creatObjectDetail(tableId, "");
                }
                // 保存之后重新获取 数据信息
                // creatObjectDetail($("#tableId").text() , "");
                // 修改之后重新获取来源关系
                sourceRelationClick();
                // 重新查询字段信息
                $("#resourceManageObjectField").bootstrapTable('removeAll');
                updateObjectField($("#tableId").text());
                editFlag = false;
                if (GLOBAL.isFlow) {
                    flowCompleteClick();
                }
                tableAddRemoveBorder("table-object", "add");
                removeAddButtonDisabled("save");
                flag = true;
                getSourceRelationData($("#tableId").text());
            }
        }
    });
}


/**
 *
 * @param inputId  input的id值
 * @param data     data类型的数据
 * @param selectValueList   ["aa","aa"]
 */
function layuiInputBuild(inputId, data, selectValueList,triggerType) {
    layui.config({
        base: "layui/lay/mymodules/"
    }).use(['form', "jquery", "cascader", "form"], function () {
        var $ = layui.jquery;
        var cascader1 = layui.cascader;

        var cas1 = cascader1({
            elem: "#" + inputId,
            data: data,
            value: selectValueList,
            triggerType:triggerType,
            success: function (valData, labelData) {
                console.log(valData, labelData);
                if (valData != null && valData.length >= 1) {
                    organizationClassifyId = valData;
                    //20201130 当是原始库的时候，才显示那个必填按钮
                    editRequiredAttributes(labelData);
                    // 当修改分级分类信息时，表名也要进行规范变化 20200412
                    realTableNameChange();
                    // 当修改分级分类时，如果是数据组织分类，选择的值是 原始库 则 tableId 需要按照规则回填
                    //   规则为GA_RESOURCE_5位数据来源代码_6位行政区划_5位流水号
                    originalTableIdChange();
                } else {
                    toastr.error("回填的classid有问题，回填值为" + valData)
                }
            }
        });

    })

}


/**
 * 当选择的是原始库之后，数据来源信息里面的几个值为必填
 * @param labelData  ["原始库","互联网数据"]
 */
function editRequiredAttributes(labelData) {
  try{
    if(checkIsNull(labelData)){
      $("#sourceIdSpan").html("");
      $("#sourceIdSpan").html("");
      $("#sourceIdSpan").html("");
      $("#tableIdInput").attr("placeholder","")
      return;
    }
    if(labelData[0].indexOf("原始库")  !== -1){
      $("#sourceIdSpan").html("*");
      $("#sourceIdSpan").html("*");
      $("#sourceIdSpan").html("*");
      $("#tableIdInput").attr("placeholder","保存后自动生成")
    }else{
      $("#sourceIdSpan").html("");
      $("#sourceIdSpan").html("");
      $("#sourceIdSpan").html("");
      $("#tableIdInput").attr("placeholder","")
    }
  }catch (e) {
    $("#sourceIdSpan").html("");
    $("#sourceIdSpan").html("");
    $("#sourceIdSpan").html("");
    $("#tableIdInput").attr("placeholder","")
    console.log(e);
  }
}




/**
 * 获取 数据来源分类中最新的那条主要根据sourceId 来获取6位行政区划
 *
 * GA_RESOURCE_5位数据来源代码_6位行政区划_5位流水号。
 */
function getNewTableRelationCode() {
    // 如果是保存的时候是 editPageFlag = true
    if(!checkIsNull(getQueryParam("moduleName"))){
        var sourceId = $("#SOURCEID_SOURCE_input").val();
        if(sourceId.split("_").length === 5){
            return sourceId.split("_")[3];
        }else{
            return null;
        }
    }
    if(source_relationship_list!== null && source_relationship_list.length >0){
        var relationList = [];
        for(var i = 0; i<source_relationship_list.length;i++){
            if(source_relationship_list[i].addType === "database"){
                relationList.push(source_relationship_list[i])
            }
        }
        relationList.sort(function (a,b) {
            return  b.insertDate - a.insertDate;
        });
        if(relationList.length === 0){
          throw new DOMException("  数据组织分类选择的是【原始库】，来源关系的的获取方式请选择【数据仓库】");
        }
        var sourceId = relationList[0].sourceSystem;

        if(sourceId.split("_").length === 5){
            return sourceId.split("_")[3];
        }else{
            return null;
        }
    }else{
        return null;
    }


}
// 保存之后重新获取表tableId
function saveOriginalTableIdChange(){
    // 只能是新增里面使用这个 编辑中表tableId不能修改
    var objectId =  $("#objectIdManage").text();
    if(!checkIsNull(objectId)){
        return;
    }
    var organizationClassifyStr = $("#organizationClassifyInput").val();
    var dataSourceClassifyStr = $("#sourceClassifyInput").val();
    if(organizationClassifyStr.indexOf("原始库") !== -1 && dataSourceClassifyStr !== ""){
        // 如果选择的是来源关系 代码值不是最新标准的代码 则不
        // 数据组织选择了原始库
        // GA_SOURCE_5位数据来源代码_6位行政区划_5位流水号
        var code = getNewTableRelationCode();
        // 先从来源关系中获取到最新的一条记录 根据 源协议id获取  6位行政区划
        if(checkIsNull(code) ){
            toastr.error("来源协议关系代码值的规则为  GA_SOURCE_5位数据来源代码_6位行政区划_5位流水号 ，请确定填写是否正确");
            return;
        }
        if(code.length !== 6){
            toastr.error("最新的一条来源关系数据中来源数据协议的行政区划不是6位，请重新确定来源关系");
            return;
        }
        $.ajax({
            type: "get",
            url:  "getOrganizationTableId",
            contentType: 'application/json',
            dataType: 'json',
            data:{
                "dataSourceClassify": dataSourceClassifyStr,
                "code":code
            },
            calculable: false,
            async: false,
            success: function (result) {
                if (result.status === 1 && result.data != null) {
                    $("#tableIdInput").val(result.data);
                    toastr.info("最新的tableId的值为"+result.data);
                } else {
                    toastr.error(result.msg);
                }
            },
            error: function (result) {
                toastr.error("获取源数据库的tableId信息报错");
                return;
            }
        });
    }
}


// 当数据组织分类选择 原始库时 tableId发生的变化
function originalTableIdChange() {
    // 只能是新增里面使用这个 编辑中表tableId不能修改
    var objectId =  $("#objectIdManage").text();
    if(!checkIsNull(objectId)){
        return;
    }
    var organizationClassifyStr = $("#organizationClassifyInput").val();
    var dataSourceClassifyStr = $("#sourceClassifyInput").val();
    if(organizationClassifyStr.indexOf("原始库") !== -1 && dataSourceClassifyStr !== ""){
        // 如果选择的是来源关系 代码值不是最新标准的代码 则不
        // 数据组织选择了原始库
        $("#tableIdInput").attr("readonly", "readonly");
        // GA_SOURCE_5位数据来源代码_6位行政区划_5位流水号
        var code = getNewTableRelationCode();
        // 先从来源关系中获取到最新的一条记录 根据 源协议id获取  6位行政区划
        if(checkIsNull(code) ){
            toastr.error("来源协议关系代码值的规则为  GA_SOURCE_5位数据来源代码_6位行政区划_5位流水号 ，请确定填写是否正确");
            return;
        }
        if(code.length !== 6){
            toastr.error("最新的一条来源关系数据中来源数据协议的行政区划不是6位，请重新确定来源关系");
            return;
        }
        $.ajax({
            type: "get",
            url:  "getOrganizationTableId",
            contentType: 'application/json',
            dataType: 'json',
            data:{
                "dataSourceClassify": dataSourceClassifyStr,
                "code":code
            },
            calculable: false,
            async: false,
            success: function (result) {
                if (result.status === 1 && result.data != null) {
                    $("#tableIdInput").val(result.data);
                } else {
                    toastr.error(result.msg);
                }
            },
            error: function (result) {
                toastr.error("获取源数据库的tableId信息报错");
                return;
            }
        });
    }else{
        // 数据组织没有选择原始库
        if($("#tableIdInput")[0].attributes.readOnly !== undefined){
            $("#tableIdInput").val("");
        }
        $("#tableIdInput").removeAttr("readonly");
    }

}

/**
 *
 * @param inputId
 * @param data
 * @param selectValueList
 * @param triggerType   如果是从数据仓库调转过来 则使用 change 否则  click
 */
function layuiInputBuild2(inputId, data, selectValueList,triggerType) {
    layui.config({
        base: "layui/lay/mymodules/"
    }).use(['form', "jquery", "cascader", "form"], function () {
        var $ = layui.jquery;
        var cascader2 = layui.cascader;

        var cas2 = cascader2({
            elem: "#" + inputId,
            data: data,
            value: selectValueList,
            triggerType:triggerType,
            success: function (valData, labelData) {
                console.log(valData, labelData);
                // 在这里回填全局变量
                if (valData != null && valData.length >= 1) {
                    sourceClassifyId = valData;
                    // 当修改分级分类信息时，表名也要进行规范变化 20200412
                    // 当分级分类发生变化时，其来源协议编码对应的改变
                    // var codeName = getCodeNameByClassifyId(valData.join(","))
                    // getSourceIdByCodeName(codeName,$("#SOURCEID_SOURCE_input").val());
                    getNewSourceId();
                    realTableNameChange();
                    originalTableIdChange();
                } else {
                    toastr.error("数据来源回填的classid有问题，回填值为" + valData)
                }
            }
        });
        if(selectValueList.size > 0 ){
            cas2.reload() ; // 重载
        }

    })

}


/**
 *
 * @param inputId
 * @param data
 * @param selectValueList
 * @param triggerType   如果是从数据仓库调转过来 则使用 change 否则  click
 */
function layuiInputBuild3(inputId, data, selectValueList,triggerType) {
    layui.config({
        base: "layui/lay/mymodules/"
    }).use(['form', "jquery", "cascader", "form"], function () {
        var $ = layui.jquery;
        var cascader3 = layui.cascader;

        var cas3 = cascader3({
            elem: "#" + inputId,
            data: data,
            value: selectValueList,
            triggerType:triggerType,
            success: function (valData, labelData) {
                // 在这里回填全局变量
                if (valData != null && valData.length >= 1) {
                    console.log(valData)
                    if(inputId === "SJZYLYLX_input"){
                        // 数据来源分类
                        $("#SJZYLYLX").val(valData.join(","));
                        // 当分级分类发生变化时，其来源协议编码对应的改变
                        var codeName = getCodeNameByClassifyId(valData.join(","))
                        getSourceIdByCodeName(codeName,$("#SOURCEID_SOURCE_input").val());
                    }else if(inputId === "SJZYSQDWMC_input"){
                        //
                        $("#SJZYSQDW_SQDWDM_input").val(valData[valData.length - 1]);
                    }else if(inputId === "SJZYGLMC_input"){
                        $("#SJZYGLDW_GAJGJGDM_input").val(valData[valData.length - 1]);
                    }else{
                        toastr.error("数据来源信息中"+inputId+"下拉框报错")
                    }
                } else {
                    toastr.error("数据来源信息中"+inputId+"下拉框报错")
                }
            }
        });
        // if(selectValueList.size > 0 ){
        //     cas3.reload() ; // 重载
        // }
    })

}

/**
 * 字段定义的查询按钮
 */
function objectFieldQuery() {
    var needValueSelectList = $("#needValueSelect").val();
    if(needValueSelectList ===""
      || needValueSelectList === null || needValueSelectList === undefined){
      toastr.error("请选择具体的字段信息");
      return;
    }
    var searchInput = $("#searchInputContext").val().trim().toLowerCase();
    var serachColumnList = [];
    if (searchInput !== "") {
        //清楚筛选功能
        resetData();
        column_table_list.forEach(function (data) {
            for(var num =0;num <needValueSelectList.length;num++){
              var column = needValueSelectList[num];
              var rowData = data[column]=== null ? "" :data[column].trim().toLowerCase();
              if(rowData.indexOf(searchInput) !== -1){
                serachColumnList.push(data);
                break;
              }
            }

            // // 查询数据
            // var fieldId = data.fieldId === null ? "" : data.fieldId.toLowerCase();
            // var columnName = data.columnName === null ? "" : data.columnName.toLowerCase();
            // var fieldName = data.fieldName === null ? "" : data.fieldName.toLowerCase();
            // var fieldChineeName = data.fieldChineeName === null ? "" : data.fieldChineeName;
            // if (needValueSelectList.indexOf("fieldId") !== -1 &&
            //      fieldId.indexOf(searchInput) !== -1 ) {
            //     serachColumnList.push(data);
            // } else if (needValueSelectList.indexOf("columnName") !== -1 &&
            //   columnName.indexOf(searchInput) !== -1) {
            //     serachColumnList.push(data);
            // } else if (needValueSelectList.indexOf("fieldName")  !== -1 &&
            //   fieldName.indexOf(searchInput) !== -1) {
            //     serachColumnList.push(data);
            // } else if (needValueSelectList.indexOf("fieldChineeName") !== -1 &&
            //   fieldChineeName.indexOf(searchInput) !== -1) {
            //     serachColumnList.push(data);
            // }
        });
    } else {
        serachColumnList = column_table_list;
    }
    $("#resourceManageObjectField").bootstrapTable('load', serachColumnList);
    // column_table_list = serachColumnList;
    addTotalAndtitle('#resourceManageObjectFieldTab', serachColumnList.length)
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
            '</div><div style="font-size: 13px;color: #565656;">正在加载中，请稍后...</div></div>',
        css: {
            marginLeft: '10%',
            marginTop: '10px',
            boxShadow: '0 1px 5px #CBCCCD',
            border: 'none',
            '-webkit-border-radius': '10px',
            width: '180px'
        },
        overlayCSS: {
            opacity: '0.3'
        },
        baseZ: 2000
    });
}

// 显示其它信息的页面
function downArrowClick() {
    $(".glyphicon-menu-down").css("display", "none");
    $(".glyphicon-menu-up").css("display", "block");
    $(".downUpArrow").css("display", "block")
}

// 隐藏其它信息的页面
function downUpClick() {
    $(".glyphicon-menu-down").css("display", "inline-block");
    $(".glyphicon-menu-up").css("display", "none");
    $(".downUpArrow").css("display", "none")
}

// 关闭和保存之后清空其它信息里面的选择框
function initOtherSelect() {
    $("#tableIndexAddModel").selectpicker('val',0);
    $("#isIndexAddModel").selectpicker('val',0);
    $("#indexTypeAddModel").selectpicker('val',0);
    $("#needValueAddModel").selectpicker('val', 0);
    $("#isContorlAddModel").selectpicker('val',0);
    $("#isQueryAddModel").selectpicker('val', 0);
    $("#memoAddModel").val("");
    $("#columnNameStateAddModel option").removeAttr("selected");
    $("#columnNameStateAddModel").selectpicker('val', 1);
    $("#md5IndexAddModel option").removeAttr("selected");
    $("#md5IndexAddModel").selectpicker('val','false');
    $("#isPrivateAddModel option").removeAttr("selected");
    $("#isPrivateAddModel").selectpicker('val', '');
    $("#pkRecnoAddModel option").removeAttr("selected");
    $("#pkRecnoAddModel").selectpicker('val', 'false');
    $("#partitionRecnoAddModel option").removeAttr("selected");
    $("#partitionRecnoAddModel").selectpicker('val', '');
    $("#clustRecnoAddModel option").removeAttr("selected");
    $("#clustRecnoAddModel").selectpicker('val','false');
    $("#oraShowAddModel option").removeAttr("selected");
    $("#oraShowAddModel").selectpicker('val', '0');
    $("#odpsPattitionAddModel option").removeAttr("selected");
    $("#odpsPattitionAddModel").selectpicker('val', '');
    $("#proTypeAddModel option").removeAttr("selected");
    $("#proTypeAddModel").selectpicker('val', '');
    // 字段分类和敏感分类 置空
    $("#SensitivityLevel").selectpicker('val','');
    $("#sensitivityCh").val("");

}

function ColumnCreateTable(data,columnDataList,prestoColumn) {
    $("#updateColumnCreateTable").bootstrapTable("destroy").bootstrapTable({
        data :data ,
        contentType : "application/json,charset=utf-8",
        sidePagination : "client", // 分页方式：client客户端分页，server服务端分页
        pagination : true,// 启动分页
        paginationPreText: '<',
        paginationNextText: '>',
        pageSize : 10,  //每页显示的记录条数
        pageNumber : 1, //当前显示第几页
        pageList : [10,20,30,50],
        paginationShowPageGo: true,
        showJumpto: true,
        smartDisplay: false,
        paginationLoop: false,
        search : false,// 是否启用查询,
        showRefresh : false, // 是否显示刷新按钮
        showColumns : false, // 是否显示所有的列
        minimumCountColumns : 2, // 最少允许的列数
        clickToSelect : true,
        height: caculateTableHeight() * 0.5,
        // fixedColumns:true,
        // fixedNumber:2,
        // height:"400",
        //singleSelect : true,
        columns : [
            // {checkbox : true},
            {field: 'fieldChineeName',title: "字段中文名",valign:"middle",align:"center",width:100},
            {field: 'columnName',title: "建表字段",valign:"middle",align:"center",width:80},
            {field: 'fieldtypes',title: "源字段类型",valign:"middle",align:"center",width:100},
            {field: 'fieldType',title: "标准字段类型",valign:"middle",align:"center",width:120,
                formatter:function(value,row,index){
                    var value = "";
                    if(row.fieldType=="0"){
                        value = "integer("+row.fieldLen+")";
                    }else if(row.fieldType=="1"){
                        value = "float("+row.fieldLen+")";
                    }else if(row.fieldType=="2"){
                        value = "string("+row.fieldLen+")";
                    }else if(row.fieldType=="3"){
                        value = "date("+row.fieldLen+")";
                    }else if(row.fieldType=="4"){
                        value = "datetime("+row.fieldLen+")";
                    }else if(row.fieldType=="6"){
                        value = "long("+row.fieldLen+")";
                    }else if(row.fieldType=="7"){
                        value = "double("+row.fieldLen+")";
                    }
                    return value;
                }
            },
            {field: 'createColumnType',title: "建表字段类型",valign:"middle",align:"center",width:120,
                editable:{
                    type:'select',
                    title:'建表字段类型',
                    source:columnDataList,
                    placement:'bottom'
                }
            },
            {field: 'createColumnLen',title: "建表字段长度",valign:"middle",align:"center",width:120,
                editable:{
                    type:'text',
                    title:'建表字段长度',
                    placement:'bottom',
                    validate:function (v) {
                        if(isNaN(v)) return '必须为数字';
                        if(v<0) return '请输入正数';
                    }
                }
            },
            {field: 'isRowkey',title: "rowKey",valign:"middle",align:"center",width:80,visible:prestoColumn,
                events:operateEvents,
                formatter:function (value,row,index) {
                    if(row.isRowkey === 1){
                        return [
                            '<input type="checkbox" class="isRowkeyCheckbox" checked>',
                        ].join("")
                    }else{
                        return [
                            '<input type="checkbox" class="isRowkeyCheckbox">',
                        ].join("")
                    }

                }
            },
            {
                field: 'indexs', title: "建立索引", valign: "middle", align: "center", width: 80, visible: prestoColumn,
                events: operateEvents,
                formatter: function (value, row, index) {
                    if (row.indexs === true) {
                        return [
                            '<input type="checkbox" class="isIndexsCheckbox" checked>',
                        ].join("")
                    } else {
                        return [
                            '<input type="checkbox" class="isIndexsCheckbox">',
                        ].join("")
                    }
                }
            },
            {
                field: 'isSource',
                title: "存source",
                valign: "middle",
                align: "center",
                width: 80,
                visible: prestoColumn,
                events: operateEvents,
                formatter: function (value, row, index) {
                    if (row.isSource === 1) {
                        return [
                            '<input type="checkbox"  class="isSourceCheckbox" checked>',
                        ].join("")
                    } else {
                        return [
                            '<input type="checkbox"  class="isSourceCheckbox">',
                        ].join("")
                    }
                }
            },
            {
                field: 'isStore', title: "存store", valign: "middle", align: "center", width: 80, visible: prestoColumn,
                events: operateEvents,
                formatter: function (value, row, index) {
                    if (row.isStore === 1) {
                        return [
                            '<input type="checkbox" class="isStoreCheckbox" checked>',
                        ].join("")
                    } else {
                        return [
                            '<input type="checkbox" class="isStoreCheckbox">',
                        ].join("")
                    }
                }
            },
            {
                field: 'isDocval',
                title: "存docvalue",
                valign: "middle",
                align: "center",
                width: 100,
                visible: prestoColumn,
                events: operateEvents,
                formatter: function (value, row, index) {
                    if (row.isDocval === 1) {
                        return [
                            '<input type="checkbox" class="isDocvalCheckbox" checked>',
                        ].join("")
                    } else {
                        return [
                            '<input type="checkbox" class="isDocvalCheckbox">',
                        ].join("")
                    }

                }
            },
            {
                field: 'isFilter', title: "过滤列", valign: "middle", align: "center", width: 80, visible: prestoColumn,
                events: operateEvents,
                formatter: function (value, row, index) {
                    if (row.isFilter === 1) {
                        return [
                            '<input type="checkbox" class="isFilterCheckbox" checked>',
                        ].join("")
                    } else {
                        return [
                            '<input type="checkbox" class="isFilterCheckbox">',
                        ].join("")
                    }

                }
            }
        ],
        onPageChange: function () {
            addTotalAndtitle('#addTableContent', column_table_list.length)
        }
    });
    addTotalAndtitle('#addTableContent', column_table_list.length)
}

// 在创建表的模态框中下一步按钮 显示出字段的对应内容以及建表按钮
function createTableNextStep() {
    // 点击下一步之后，判断必填的几个是否点击了必填
    var dataBasedType = $("#dataResourceType option:selected").val();
    if (dataBasedType === "selected" || !dataBasedType) {
        toastr.error("请选择具体的数据源类型");
        return;
    }
    var prestoFlag = false;
    var dataId = $("#dataResourceName option:selected").val();
    var schema = $("#projectName option:selected").val();
    var tableName = $("#tableName").val();
    if (dataBasedType === "ODPS" || dataBasedType === "ADS" || dataBasedType === "HIVE") {
        var tableNameCH = $("#dataSourceName").text();
        var objectFields = columnInfo;
        var tableId = $("#tableId").text();
        var objectId = $("#objectIdManage").text();
        var partitionFirst = $("#partitionFirst").val() === "selected" ? null : $("#partitionFirst").val();
        var partitionFirstNum = $("#partitionFirstNum").val();
        var partitionSecond = $("#partitionSecond").val() === "selected" ? null : $("#partitionSecond").val();
        var partitionSecondNum = $("#partitionSecondNum").val();
        // var odpsPartitions = $("#odpsPartitionselect").val();
        var life = $("input[name=life]")[1].checked;
        var lifeDays = $("#lifeDays").val();
        if (dataId === "selected" || objectFields == "selected" || schema === "selected" ||
               tableName === "" || checkIsNull(schema) || checkIsNull(dataId)) {
            toastr.error("请填写必填项");
            return;
        }
        if (dataBasedType === "ADS" && ((!partitionFirst || !partitionFirstNum) || (partitionSecond && !partitionSecondNum))) {
            toastr.error("请填写必填项");
            return;
        }
        if (dataBasedType === "ODPS" &&  (life && !lifeDays)) {
            toastr.error("请填写必填项");
            return;
        }
    } else if (dataBasedType === "HBASE") {
        var checkFlag = checkPrestoTableMessageIsNull(false);
        if (checkFlag) {
            return;
        }
        prestoFlag = true;
    }else if(dataBasedType === "DATAHUB"){
        var checkFlag = checkDatahubMessageIsNull();
        if (checkFlag) {
            return;
        }
    }
    $("#createTableNextStep").css("display", "none");
    $("#createTableConfig").css("display", "none");
    $("#createTablePreviousStep").css("display", "inline");
    $("#createTableColumn").css("display", "inline");
    $("#saveTable").css("display", "none");
    $("#closePage").css("display", "none");
    $("#afterPage").css("display", "inline");

    // 建表字段的对应关系 ，获取 odps ads的所有字段信息
    var columnDataList = getColumnTypeByBase(dataBasedType);
    ColumnCreateTable(columnInfo, columnDataList, prestoFlag);
}

// 在创建表的模态框中上一步按钮
function createTablePreviousStep() {
    $("#createTableNextStep").css("display", "inline");
    $("#createTableConfig").css("display", "inline");
    $("#createTablePreviousStep").css("display", "none");
    $("#createTableColumn").css("display", "none");
    $("#saveTable").css("display", "inline");
    $("#afterPage").css("display", "none");
    $("#closePage").css("display", "inline");
}


/**
 * 获取 odps/ads 的所有字段类型
 */
function getColumnTypeByBase(dataBaseType) {
    var dataColumn = [];
    $.ajax({
        type: "get",
        url:  "dbManager/getColumnType",
        calculable: false,
        async: false,
        data: {
            "dataBaseType": dataBaseType
        },
        success: function (result) {
            dataColumn = result;
            addTotalAndtitle('#addTableContent', column_table_list.length)
        },
        error: function (result) {
            toastr.error("获取字段所有类型报错");
        }
    });
    return dataColumn;
}

// 一键对应 修改所有的字段类型和字段长度
function columnCorrespondClick() {
    var dataBaseType = $("#dataResourceType option:selected").val();
    var flag = false;
    if (dataBaseType === "HBASE") {
        flag = true;
    }
    $.ajax({
        type: "post",
        contentType: 'application/json',
        dataType: 'json',
        url:  "dbManager/columnCorrespondClick",
        calculable: false,
        async: false,
        data: JSON.stringify({
            "dataBaseType": dataBaseType,
            "columnData": columnInfo
        }),
        success: function (result) {
            if (result.status === 1) {
                toastr.info("字段一键对应结束");
                columnInfo = result.data;
                var columnDataList = getColumnTypeByBase($("#dataResourceType option:selected").val());
                ColumnCreateTable(columnInfo, columnDataList, flag);
            } else {
                toastr.error(result.msg)
            }
        },
        error: function (result) {
            toastr.error("一键对应所有的建表字段信息报错");
        }
    });
}

/**
 * 生成建表的SQL
 */
function showCreateTableSql() {
    addMask();
    var obj = new Object();
    var dataBaseType = $("#dataResourceType option:selected").val() === "selected" ? null : $("#dataResourceType option:selected").val();
    if (dataBaseType === "HBASE") {
        obj = getPrestoTableMessage();
        obj.dataBaseType = dataBaseType;
        obj.columnData = columnInfo;
        obj.tableName = $("#tableName").val();
    } else if (dataBaseType === "ODPS" || dataBaseType === "ADS" || dataBaseType === "HIVE") {
        obj.dataBaseType = dataBaseType;
        obj.tableName = $("#tableName").val();
        obj.projectName = $("#projectName option:selected").val() === "selected" ? null : $("#projectName option:selected").val();
        obj.columnData = columnInfo;
        obj.tableNameCH = $("#dataSourceName").text();
        if(dataBaseType === "ODPS"){
            // obj.odpsPartition = $("#odpsPartitionselect").val();
            obj.lifeDays = $("#lifeDays").val();
        }
        if(dataBaseType === "ADS"){
            obj.partitionFirst = $("#partitionFirst").val() === "selected" ? null : $("#partitionFirst").val();
            obj.partitionFirstNum = $("#partitionFirstNum").val();
            obj.partitionSecond = $("#partitionSecond").val() === "selected" ? null : $("#partitionSecond").val();
            obj.partitionSecondNum = $("#partitionSecondNum").val();
        }
    } else if(dataBaseType === "DATAHUB"){
        toastr.info( dataBaseType + "不能使用SQL来创建topic");
        $.unblockUI();
        return;
    } else {
        toastr.error("数据库类型" + dataBaseType + "没有编写生成建表sql方法");
        $.unblockUI();
        return;
    }
    $.ajax({
        type: "post",
        contentType: 'application/json',
        dataType: 'json',
        url:  "dbManager/showCreateTableSql",
        calculable: false,
        async: true,
        data: JSON.stringify(obj),
        success: function (result) {
            if (result.status === 1) {
                toastr.info("生成建表SQL结束");
                $("#createSqlTextarea").text(result.data);
            } else {
                toastr.error(result.msg)
            }
            $.unblockUI();
        },
        error: function (result) {
            $.unblockUI();
            toastr.error("生成建表SQL报错");
        }
    });
}

// 添加公共字段
function addCommonColumn() {
    // var objectId = $("#objectIdManage").text();
    // var tableId = $("#tableId").text().trim();
    // if(objectId == null || objectId === ""){
    //    toastr.error("objectId为空，不能添加公共字段,请先保存");
    //    return;
    // }
    if (!editFlag) {
        $("#statusCheckModal").modal("show");
        return;
    }
    addMask();
    $.ajax({
        type: "post",
        contentType: 'application/json',
        dataType: 'json',
        url:  "dbManager/getCommonColumn",
        calculable: false,
        async: true,
        data: JSON.stringify(column_table_list),
        success: function (result) {
            if (result.status === 1) {
                //  获取需要添加的公共字段信息
                var needColumnList = result.data;
                if (needColumnList !== null && needColumnList.length > 0) {
                    needColumnList.forEach(function (value) {
                        column_table_list.push(value);
                    });
                    $("#resourceManageObjectField").bootstrapTable('load', column_table_list);
                    addTotalAndtitle('#resourceManageObjectFieldTab', column_table_list.length)
                    toastr.info("获取公共字段信息成功");
                    //清楚筛选功能
                    resetData();
                } else {
                    toastr.info("公共字段已经存在");
                }

                // toastr.info(result.data);
                // 查询字段定义的相关信息
                // $("#resourceManageObjectField").bootstrapTable('removeAll');
                // updateObjectField(tableId);
                // 向标准化管理提交修改后的内容 pushMetaInfo
                // pushMetaInfoController();
            } else {
                toastr.error(result.msg)
            }
            $.unblockUI();
        },
        error: function (result) {
            $.unblockUI();
            toastr.error("添加公共字段报错");
        }
    });
    // $.ajax({
    //   type:"get",
    //   contentType:'application/json',
    //   dataType:'json',
    //   url:path+"/dbManager/addCommonColumn",
    //   calculable: false,
    //   async:true,
    //   data:{
    //     "objectId":objectId
    //   },
    //   success:function(result){
    //     if(result.status === 1){
    //       toastr.info(result.data);
    //       // 查询字段定义的相关信息
    //       $("#resourceManageObjectField").bootstrapTable('removeAll');
    //       updateObjectField(tableId);
    //       // 向标准化管理提交修改后的内容 pushMetaInfo
    //       pushMetaInfoController();
    //     }else{
    //       toastr.error(result.msg)
    //     }
    //     $.unblockUI();
    //   },
    //   error:function(result){
    //     $.unblockUI();
    //     toastr.error("添加公共字段报错");
    //   }
    // });
}

function sourceRelationClick() {
    $("#sourceRelationShip").bootstrapTable('removeAll');
    sourceRelationShipDataGet($("#tableId").text());
}

/**
 * 页面跳转
 * 1:质量检测 2:数据处理 3:数据接入
 */
function jumpOrthePage(pageNum) {
    if (editFlag) {
        toastr.error("请先保存该数据");
        return;
    }
    var objectId = $("#objectIdManage").text();
    if (checkIsNull(objectId)) {
        toastr.error("请选择具体的标准表")
        return;
    }
    // 获取页面上的url
    $.ajax({
        type: "get",
        data: {
            "pageNum": pageNum
        },
        contentType: 'application/json',
        dataType: 'json',
        url:  "getOrtherPageUrl",
        calculable: false,
        async: true,
        success: function (result) {
            if (result.status === 1) {
                var title = result.data.title;
                var dataGovernaceWeb = result.data.dataGovernaceUrl;
                var judmpUrl = "";
                if (pageNum === 3) {
                    // 数据接入需要传递两个参数
                    var outTableName = $("#tableId").text();
                    var outSysChiName = $("#objectName").text();
                    var outSourceSystem = $("#objectName").val();
                    GLOBAL.dataGovernaceWeb = dataGovernaceWeb;
                    GLOBAL.pageUrl = result.data.pageUrl;
                    // 输出协议的相关信息
                    var sourceRelationList = $("#sourceRelationShip").bootstrapTable("getData");
                    var flageNum = 0;
                    if (sourceRelationList.length > 1) {
                        $("#relationSelectEtl").empty();
                        sourceRelationList.forEach(function (value) {
                            if (!checkIsNull(value.dataId)) {
                                flageNum = flageNum + 1;
                                $("#relationSelectEtl").append("<option value=" + value.sourceSystem + '|' + value.sourceProtocol + " selected  style='width: 220px' >" + value.dataSourceName + "</option>");
                            }
                        });
                        if (flageNum > 0) {
                            toastr.error("存在多个来源关系，请选择具体的来源信息");
                            $('#jumpRelationModalEtl').modal('show');
                        } else {
                            toastr.error("该表的所有来源关系中没有一种关系来自数据仓库,来源协议为空");
                            judmpUrl = result.data.pageUrl + "?tableId=&dataId=&centerId=&inTableName=&inSourceProtocol=&inSourceFirmCode=&outTableName=" + outTableName + "&outSysChiName=" + outSysChiName
                                + "&outSourceSystem=" + outSourceSystem;
                            window.open(dataGovernaceWeb + '?src=' +
                                encodeURIComponent(judmpUrl) + '&title=' + title, '_blank');
                        }
                    } else if (sourceRelationList.length === 1) {
                        var dataId = sourceRelationList[0].dataId;
                        var tableId = sourceRelationList[0].tableId;
                        var centerId = sourceRelationList[0].centerId;
                        if (!checkIsNull(dataId)) {
                            judmpUrl = result.data.pageUrl + "?tableId=" + tableId + "&dataId=" + dataId + "&centerId=" + centerId
                                + "&inTableName=" + sourceRelationList[0].sourceSystem + "&inSourceProtocol=" + sourceRelationList[0].sourceProtocol
                                + "&inSourceFirmCode=" + sourceRelationList[0].sourceFirm + "&outTableName=" + outTableName + "&outSysChiName=" + outSysChiName
                                + "&outSourceSystem=" + outSourceSystem;
                        } else {
                            toastr.error("该表的所有来源关系中没有一种关系来自数据仓库,来源协议为空");
                            judmpUrl = result.data.pageUrl + "?tableId=&dataId=&centerId=&inTableName=&inSourceProtocol=&inSourceFirmCode=&outTableName=" + outTableName + "&outSysChiName=" + outSysChiName
                                + "&outSourceSystem=" + outSourceSystem;
                        }
                        // console.log(judmpUrl);
                        // window.open(judmpUrl + '&title='+title,'_blank');
                        window.open(dataGovernaceWeb + '?src=' +
                            encodeURIComponent(judmpUrl) + '&title=' + title, '_blank');
                    } else {
                        toastr.error("该表的所有来源关系中没有一种关系来自数据仓库,来源协议为空");
                        judmpUrl = result.data.pageUrl + "?tableId=&dataId=&inTableName=&centerId=&inSourceProtocol=&inSourceFirmCode=&outTableName=" + outTableName + "&outSysChiName=" + outSysChiName
                            + "&outSourceSystem=" + outSourceSystem;
                        window.open(dataGovernaceWeb + '?src=' +
                            encodeURIComponent(judmpUrl) + '&title=' + title, '_blank');
                    }
                } else if (pageNum === 2) {
                    // 如果是跳转到
                    //  如果只有一个 直接跳转 如果含有多个，则需要选择具体的来源关系
                    // 如果没有 则跳出提示 没有具体的对应关系
                    // 因为数据来源可能有多个，所以需要先选择具体的数据来源
                    // 目标表协议信息
                    var tableId = $("#tableId").html();
                    // 来源表协议信息
                    var sourceId = '';
                    var sourceRelationList = $("#sourceRelationShip").bootstrapTable("getData");
                    if (sourceRelationList.length > 1) {
                        $("#relationSelect").empty();
                        sourceRelationList.forEach(function (value) {
                            $("#relationSelect").append("<option value=" + value.sourceSystem + " selected>" + value.dataSourceName + "</option>");
                        });
                        $('#jumpRelationModal').modal('show');
                        toastr.error("存在多个来源关系，请选择具体的数据来源");
                    } else if (sourceRelationList.length === 1) {
                        sourceId = sourceRelationList[0].sourceSystem;
                        jumpToProcessingPage(tableId, sourceId);
                        // window.open(path+'/requestDataHandle?tableId='+tableId+'&sourceId='+sourceId,'_blank');
                    } else {
                        jumpToProcessingPage(tableId, "");
                        // window.open(path+'/requestDataHandle?tableId='+tableId+'&sourceId=','_blank');
                    }
                }
            } else {
                toastr.error(result.msg)
            }
        },
        error: function (result) {
            toastr.error("跳转页面报错");
        }
    });
}


// 建表过程中 HBASE分区类型 发生变化
function regionTypeChange() {
    var regionTypeValue = $("#regionType").val();
    $("#regionCount").removeAttr("readOnly");
    $("#regionCount").val("");
    $("#regionCountLabel").text("");
    $("#lifecycle").removeAttr("readOnly");
    $("#lifecycle").val("");
    // 如果为 -1 表示 是无效分区 则 label要改成 无效分区 值设置为-1 且不可以修改
    if (regionTypeValue === "-1") {
        $("#regionCountLabel").append("<font style=\"color: red\">*</font>无效分区  <span id=\"regionCountSpan\" class=\"glyphicon glyphicon-info-sign imgGlyphicon\" aria-hidden=\"true\"></span>");
        $("#regionCount").val("-1");
        $("#regionCount").attr("readOnly", "true");
    } else if (regionTypeValue === "3") {
        // 多天一个分区
        $("#regionCountLabel").append("<font style=\"color: red\">*</font>HBASE分区天数  <span id=\"regionCountSpan\" class=\"glyphicon glyphicon-info-sign imgGlyphicon\" aria-hidden=\"true\"></span>");
    } else if (regionTypeValue === "1") {
        // 固定分区
        $("#regionCountLabel").append("<font style=\"color: red\">*</font>HBASE总分区个数  <span id=\"regionCountSpan\" class=\"glyphicon glyphicon-info-sign imgGlyphicon\" aria-hidden=\"true\"></span>");
        // 当选择固定分区时，生命周期要设置为0
        $("#lifecycle").val("0");
        $("#lifecycle").attr("readOnly", "true");
    } else if (regionTypeValue === "2") {
        // 一天多个分区
        $("#regionCountLabel").append("<font style=\"color: red\">*</font>HBASE分区个数  <span id=\"regionCountSpan\" class=\"glyphicon glyphicon-info-sign imgGlyphicon\" aria-hidden=\"true\"></span>");
    } else if (regionTypeValue === "0") {
        // 每天一个分区
        $("#regionCountLabel").append("<font style=\"color: red\">*</font> 每天一个分区  <span id=\"regionCountSpan\" class=\"glyphicon glyphicon-info-sign imgGlyphicon\" aria-hidden=\"true\"></span>");
        $("#regionCount").val("1");
        $("#regionCount").attr("readOnly", "true");
    } else {
        toastr.error("没有对应的分区类型的选择事件");
    }
    $(".imgGlyphicon").off();
    $(".imgGlyphicon").click(function () {
        window.open( "prestoExposition?id=" + this.id, '_blank')
    });
}

// ES索引类型改变之后标签的变化
function esSplitTypeChange() {
    var esSplitTypeValue = $("#esSplitType").val();
    $("#esSplitCount").removeAttr("readOnly");
    $("#esSplitCount").val("");
    if (esSplitTypeValue === "0" || esSplitTypeValue === "1") {
        $("#esSplitCount").attr("readOnly", "true");
        $("#esSplitCount").val("1");
    } else if (esSplitTypeValue === "-1") {
        $("#esSplitCount").attr("readOnly", "true");
        $("#esSplitCount").val("-1");
    }
    // 对于这个值变化  ES单索引分片数
    if(esSplitTypeValue === "-1"){
        $("#esShards").val(0)
    }else if(esSplitTypeValue === "3"){
        $("#esShards").val(2)
    }else{
        $("#esShards").val("")
    }
}

/**
 * 获取 HBASE 表的相关配置信息
 */
function getPrestoTableMessage() {
    var obj = new Object();
    var projectName = $("#projectName option:selected").val() === "selected" ? null : $("#projectName option:selected").val();
    var regionType = $("#regionType option:selected").val();
    var regionCount = $("#regionCount").val();
    var esSplitType = $("#esSplitType").val();
    var esSplitCount = $("#esSplitCount").val();
    var esShards = $("#esShards").val();
    var lifecycle = $("#lifecycle").val();
    var prestoMemo = $("#prestoMemo").val();
    var geoRedundant = $("#geoRedundant option:selected").val();
    obj.schemaName = projectName;
    obj.schema = projectName;
    obj.regionType = regionType;
    obj.regionCount = regionCount;
    obj.esSplitType = esSplitType;
    obj.esSplitCount = esSplitCount;
    obj.esShards = esShards;
    obj.lifecycle = lifecycle;
    obj.prestoMemo = prestoMemo;
    obj.geoRedundant = geoRedundant;
    return obj;
}

window.operateEvents = {
    // 在presto表中 rowKey 有且只能有一个，如果选中了，则必须先清除其它所有的
    'click .isRowkeyCheckbox': function (e, value, row, index) {
        // 判断是否为选中状态
        var checkFlag = $(this).is(':checked');
        if (checkFlag) {
            columnInfo.forEach(function (row2) {
                row2.isRowkey = 0;
            });
            // 先取消所有的选中状态，然后再把这个设置为选中状态
            $(".isRowkeyCheckbox").each(function (indexRow, valueRow) {
                this.checked = false;

            });
            row.isRowkey = 1;
            this.checked = true;
        } else {
            row.isRowkey = 0;
        }
    },
    'click .isIndexsCheckbox': function (e, value, row, index) {
        // 判断是否为选中状态
        var checkFlag = $(this).is(':checked');
        if (checkFlag) {
            row.isIndexs = true;
        } else {
            row.isIndexs = false;
        }
    },
    'click .isSourceCheckbox': function (e, value, row, index) {
        // 判断是否为选中状态
        var checkFlag = $(this).is(':checked');
        if (checkFlag) {
            row.isSource = 1;
        } else {
            row.isSource = 0;
        }
    },
    'click .isStoreCheckbox': function (e, value, row, index) {
        // 判断是否为选中状态
        var checkFlag = $(this).is(':checked');
        if (checkFlag) {
            row.isStore = 1;
        } else {
            row.isStore = 0;
        }
    },
    'click .isDocvalCheckbox': function (e, value, row, index) {
        // 判断是否为选中状态
        var checkFlag = $(this).is(':checked');
        if (checkFlag) {
            row.isDocval = 1;
        } else {
            row.isDocval = 0;
        }
    },
    'click .isFilterCheckbox': function (e, value, row, index) {
        // 判断是否为选中状态
        var checkFlag = $(this).is(':checked');
        if (checkFlag) {
            row.isFilter = 1;
        } else {
            row.isFilter = 0;
        }
    }
};

/**
 * 判断 HBASE建表时候必填参数是否为null 数字的取值范围 以及 rowkey 有且只有一个
 */
function checkPrestoTableMessageIsNull(buildTableFlag) {
    var dataId = $("#dataResourceName option:selected").val();
    var schema = $("#projectName option:selected").val();
    var tableName = $("#tableName").val();
    var regionCount = $("#regionCount").val();
    var esSplitCount = $("#esSplitCount").val();
    var esShards = $("#esShards").val();
    var lifecycle = $("#lifecycle").val();
    // // 判断为空的情况
    // if(dataId==="selected"||schema==="selected"||checkIsNull(tableName)||checkIsNull(regionCount)||
    //   checkIsNull(esSplitCount)||checkIsNull(esShards)||checkIsNull(lifecycle)){
    //   toastr.error("请填写必填项");
    //   return true;
    // }
    if (dataId === "selected" || checkIsNull(dataId)) {
        toastr.error("数据源名称不能为空");
        return true;
    }
    if (schema === "selected" || checkIsNull(schema)) {
        toastr.error("项目名不能为空");
        return true;
    }
    if (tableName === "selected" || checkIsNull(tableName)) {
        toastr.error("表名不能为空");
        return true;
    }
    // 以下检查输入的数据是否都为数据
    if ((!checkIsNumber(regionCount) && regionCount !== "-1") || checkIsNull(regionCount)) {
        var labelName = $("#regionCountLabel").html();
        toastr.error("【" + labelName + "】的值应该为数字");
        return true;
    }
    if (!checkIsNumber(esSplitCount) && esSplitCount !== "-1" || checkIsNull(esSplitCount)) {
        toastr.error("【ES单索引天数】的值应该为数字");
        return true;
    }
    if (!checkIsNumber(esShards) || checkIsNull(esShards)) {
        toastr.error("【ES单索引分片数】的值应该为数字");
        return true;
    }
    if (!checkIsNumber(lifecycle) || checkIsNull(lifecycle)) {
        toastr.error("【生命周期】的值应该为数字");
        return true;
    }
    // 填入的值取值范围最大 32767 最小为 -1
    if (parseInt(regionCount) < -1 || parseInt(regionCount) > 32767) {
        var labelName = $("#regionCountLabel").html();
        toastr.error("【" + labelName + "】的取值范围为[-1,32767]");
        return true;
    }
    if (parseInt(esSplitCount) < -1 || parseInt(esSplitCount) > 32767) {
        toastr.error("【ES单索引天数】的取值范围为[-1,32767]");
        return true;
    }
    if (parseInt(esShards) < 0 || parseInt(esShards) > 32767) {
        toastr.error("【ES单索引分片数】的取值范围为[0,32767]");
        return true;
    }
    if (parseInt(lifecycle) < 0 || parseInt(lifecycle) > 32767) {
        toastr.error("【生命周期】的取值范围为[0,32767]");
        return true;
    }
    if (buildTableFlag) {
        // 判断 rowKey 是否 有且只有一个
        var count = 0;
        columnInfo.forEach(function (value) {
            if (value.isRowkey === 1) {
                count = count + 1;
            }
        });
        if (count != 1) {
            toastr.error("列rowKey有且只能有一个，请在高级配置页面指定rowkey");
            return true;
        }
    }
    return false;
}

/**
 * 当有多个数据来源时 选择具体的来源关系
 */
function addRelationJump() {
    var tableId = $("#tableId").html();
    var sourceId = $("#relationSelect").val();
    jumpToProcessingPage(tableId, sourceId);
    // window.open(path+'/requestDataHandle?tableId='+tableId+'&sourceId='+sourceId,'_blank');
}

function addRelationJumpEtl() {
    var sourceRelationList = $("#sourceRelationShip").bootstrapTable("getData");
    var outTableName = $("#tableId").text();
    var outSysChiName = $("#objectName").text();
    var outSourceSystem = $("#objectName").val();
    var sourceId = $("#relationSelectEtl").val();
    var flagNum = 0;
    sourceRelationList.forEach(function (value) {
        if (value.sourceSystem + '|' + value.sourceProtocol === sourceId) {
            var dataId = value.dataId;
            var tableId = value.tableId;
            var centerId = value.centerId;
            var judmpUrl = GLOBAL.pageUrl + "?tableId=" + tableId + "&dataId=" + dataId + "&centerId=" + centerId
                + "&inTableName=" + value.sourceSystem + "&inSourceProtocol=" + value.sourceProtocol
                + "&inSourceFirmCode=" + value.sourceFirm + "&outTableName=" + outTableName + "&outSysChiName=" + outSysChiName
                + "&outSourceSystem=" + outSourceSystem;
            window.open(GLOBAL.dataGovernaceWeb + '?src=' +
                encodeURIComponent(judmpUrl) + '&title=新增任务', '_blank');
            flagNum = 1;
        }
    });
    if (flagNum === 0) {
        toastr.error("未能获取到来源关系，请刷新页面");
    }
    $('#jumpRelationModalEtl').modal('hide');
}

/**
 *  这个是输出协议的发送
 *  每次修改标准表相关数据之后，都要调用标准化平台的修改接口
 */
function pushMetaInfoController() {
    var tableId = $("#tableId").text().trim();
    $.ajax({
        type: "get",
        url: "pushMetaInfoController",
        calculable: false,
        async: true,
        data: {
            "tableId": tableId
        },
        success: function (result) {
            if (result.status === 1) {
                toastr.info(result.data);
            } else {
                toastr.error(result.msg)
            }
        },
        error: function (result) {
            toastr.error("调用标准化平台的修改接口报错");
        }
    });
}

/**
 *  创建 二级分类的 多级 select
 */
function secondaryClassLayui(mainClassify, primaryClassCh) {
    $.ajax({
        type: "get",
        url: "getSecondaryClassLayui",
        contentType: 'application/json',
        dataType: 'json',
        calculable: false,
        data: {
            "mainClassify": mainClassify,
            "primaryClassifyCh": primaryClassCh
        },
        async: false,
        success: function (result) {
            if (result.status === 1) {
                // 使用select框 不要使用
                $("#secondaryClass").empty();
                if (result.result.length > 0) {
                    for (var i = 0; i < result.result.length; i++) {
                        if (result.result[i] != null && result.result[i] != "") {
                            $("#secondaryClass").append("<option value=" + result.result[i].value + ">" + result.result[i].label + "</option>");
                        }
                    }
                }
                $("#secondaryClass").selectpicker('refresh');
                // layuiInputBuildAddModel("secondaryClassLayui" ,result.result ,[]);
            } else {
                toastr.error(result.msg);
            }
        },
        error: function (result) {
            toastr.error("获取二级分类信息报错");
            return;
        }
    });
}

// 获取二级分类的所有数据(当一级分类发生变化)
function firstClassChangeNew() {
    var mainValue = "数据组织分类";
    var firstClassValue = checkIsNull($("#firstClassMode option:selected").val())?"":$("#firstClassMode option:selected").val();
    secondaryClassLayui(mainValue, firstClassValue);
    createObjectTableSuggest();
    // $.ajax({
    //   type:"get",
    //   data:{
    //     "mainValue": mainValue ,
    //     "firstClassValue": firstClassValue
    //   },
    //   url:path+"/getSecondaryClassModeByFirst",
    //   calculable : false,
    //   async:true,
    //   success:function(result){
    //     $("#secondaryClass").empty();
    //     if(result != null){
    //       for(var i=0;i<result.length;i++){
    //         if(result[i] != null && result[i] !="" ){
    //           $("#secondaryClass").append("<option value="+result[i].value+">"+result[i].label+"</option>");
    //         }
    //       }
    //     }
    //     createNewSuggest();
    //   },
    //   error:function(result){
    //     $("#secondaryClass").empty();
    //     $("addTableMode").bsSuggest("destroy");
    //     suggest("addTableMode","");
    //     toastr.error("在增加来源关系的模态框中获取二级分类信息报错")
    //   }
    // });
}


// function layuiInputBuildAddModel(inputId , data ,selectValueList) {
//   layui.config({
//     base: "/dataStandardManager/layui/lay/mymodules/"
//   }).use(['form',"jquery","cascader","form"], function() {
//     var $ = layui.jquery;
//     var cascader = layui.cascader;
//     var cas3 = cascader({
//       elem: "#"+inputId,
//       data: data,
//       value: selectValueList,
//       changeOnSelect: true,
//       success: function (valData, labelData) {
//         // 选择之后创建表名的搜索查询框
//         if(valData != null &&valData.length >1){
//            createObjectTableSuggest();
//         }else if(valData != null &&valData.length  == 1) {
//            createObjectTableSuggest();
//         }else{
//           toastr.error("数据来源回填的classid有问题，回填值为"+valData.toString())
//         }
//       }
//     });
//     cas3.reload({data:data,value:selectValueList})  // 重载
//   })
// }

/**
 * 增加来源关系里面的
 */
function createObjectTableSuggest() {
    document.getElementById("addTableMode").value = '';
    var mainValue = "数据组织分类";
    var firstValue = $("#firstClassMode option:selected").val();
    // 其中二级分类存在　　其他数据／其他　　这种情况
    var secondaryValue = $("#secondaryClass option:selected").val();
    // mainValue = (mainValue === undefined ? "" : mainValue);
    firstValue = firstValue === undefined ? "" : firstValue;
    secondaryValue = secondaryValue === undefined ? "" : secondaryValue;
    var url = "createObjectTableSuggest?mainValue=" + mainValue + "&firstValue=" + firstValue
        + "&secondaryValue=" + secondaryValue + "&condition=";
    $("#addTableMode").bsSuggest("destroy");
    suggest("addTableMode", url);
    $("#addTableMode").css("width", "220px");
    $("#addTableMode").css("height", "32px");
}

/**
 *  在添加来源关系时，根据相关信息获取全部的数据(数据组织资产需要这个)
 */
function getOrganizationRelation(addTableName) {
    var objectMessage = null;
    $.ajax({
        type: "get",
        url: "getOrganizationRelationByTableName",
        data: {
            "addTableName": addTableName
        },
        calculable: false,
        async: false,
        success: function (result) {
            if (result.status === 1) {
                objectMessage = result.result
            } else {
                toastr.error(result.msg)
            }
        },
        error: function (result) {
            toastr.error("根据报错");
        }
    });
    return objectMessage;
}

/**
 *  获取 单个标准表所有的信息 拼接成一个 对象
 *  20200318 数据都在输入框里面
 */
function getAllStandardObjectManage() {
    var object = new Object();
    object.objectId = $("#objectIdManage").text();
    object.tableId = $("#tableIdInput").val();
    var obj = new Object();
    obj.codeTextTd = $("#objectNameSelect option:selected").val();
    obj.codeTextCh = $("#objectNameSelect option:selected").text();
    var realTableName = $("#realTableNameInput").val().toUpperCase();
    var information = checkTableRealName(realTableName, $("#organizationClassifyInput").val(), $("#sourceClassifyInput").val());
    if (information != '') {
        toastr.error(information);
        return null;
    }
    // if( realTableName ==="NB_TAB_"){
    //   toastr.error("表名不能为NB_TAB_，请修改表名");
    //   return null;
    // }
    if (!checkIsNumber(obj.codeTextTd)) {
        toastr.error("系统代码输入的值必须为数字");
        return null;
    }
    obj.objectId = $("#objectIdManage").text();
    obj.dataSourceName = $("#dataSourceNameInput").val();
    obj.realTablename = $("#realTableNameInput").val().toUpperCase();
    obj.ownerFactory = $("#sourceFirmTdSelect option:selected").val();
    obj.tableId = $("#tableIdInput").val().toUpperCase();
    obj.storageTableStatus = $("#storageTableStatusTdSelect option:selected").val().trim();
    obj.storageDataMode = 'ads';
    obj.organizationClassify = $("#organizationClassifyInput").val();
    obj.sourceClassify = $("#sourceClassifyInput").val();
    obj.sourceId = $("#sourceIdInput").val();
    obj.objectMemo = $("#objectMemoInput").val();
    obj.isActiveTable = $("#isActiveTableTdSelect").val();
    if (!checkIsNull(obj.sourceId)) {
        obj.sourceId = $("#sourceIdInput").val().toUpperCase();
    }

    var arry = [];
    if (!checkIsNull(organizationClassifyId)) {
        arry.push(organizationClassifyId);
    }
    var sourceClassifyArry = [];
    if (!checkIsNull(sourceClassifyId)) {
        sourceClassifyArry.push(sourceClassifyId)
    }
    obj.classIds = arry.join(",");
    // 数据来源分类信息
    obj.sourceClassIds = sourceClassifyArry.join(",");
    obj.flow = GLOBAL.isFlow;
    // 20201010 新增更新人和更新时间等内容
    obj.createTime = $("#createTimeInput").val();
    obj.updateTime = $("#updateTimeInput").val();
    obj.creator = $("#creatorInput").val();
    obj.updater = checkIsNull($("#updaterInput").val())?" ":$("#updaterInput").val();


    // 保存6类标签值
    // var labelsList = [];
    // if (!checkIsNull($("#bodyTag1select").val())) {
    //     labelsList.push($("#bodyTag1select").val().join(","));
    //     obj.bodyTag1Val = $("#bodyTag1select").val().join(",");
    //     obj.bodyTag1Text = getTagText("bodyTag1select");
    // }
    // if (!checkIsNull($("#elementTag2select").val())) {
    //     labelsList.push($("#elementTag2select").val().join(","));
    //     obj.elementTag2Val = $("#elementTag2select").val().join(",");
    //     obj.elementTag2Text = getTagText("elementTag2select");
    // }
    // if (!checkIsNull($("#objectDescTag3select").val())) {
    //     labelsList.push($("#objectDescTag3select").val().join(","));
    //     obj.objectDescTag3Val = $("#objectDescTag3select").val().join(",");
    //     obj.objectDescTag3Text = getTagText("objectDescTag3select");
    // }
    // if (!checkIsNull($("#behaviorTag4select").val())) {
    //     labelsList.push($("#behaviorTag4select").val().join(","));
    //     obj.behaviorTag4Val = $("#behaviorTag4select").val().join(",");
    //     obj.behaviorTag4Text = getTagText("behaviorTag4select");
    // }
    // if (!checkIsNull($("#relationShipTag5select").val())) {
    //     labelsList.push($("#relationShipTag5select").val().join(","));
    //     obj.relationShipTag5Val = $("#relationShipTag5select").val().join(",");
    //     obj.relationShipTag5Text = getTagText("relationShipTag5select");
    // }
    // if (!checkIsNull($("#locationTag6select").val())) {
    //     labelsList.push($("#locationTag6select").val().join(","));
    //     obj.locationTag6Val = $("#locationTag6select").val().join(",");
    //     obj.locationTag6Text = getTagText("locationTag6select");
    // }
    // obj.labels = labelsList.join(",");
    object.objectPojoTable = obj;
    object.objectFieldList = column_table_list;
    // 20200519 输出表和输出协议没有填
    // if(source_relationship_list.length >0){
    //     source_relationship_list.forEach(function (value) {
    //         value.tableId =
    //     })
    // }
    object.sourceRelationShipList = source_relationship_list;
    object.flow = GLOBAL.isFlow;
    object.approvalId = approvalId;

    //  20200917 新增第三方模块的名称
    object.moduleName = getQueryParam("moduleName");

    //
    if (checkIsNull(obj.objectId) && object.objectFieldList.length === 0) {
        toastr.error("字段信息为空，请配置对应的字段信息");
        return null;
    }
    // 如果 数据名为空
    if (checkIsNull(obj.dataSourceName)) {
        toastr.error("数据名为空，请填写数据名");
        return null;
    }
    // 判断 表协议
    if (checkIsNull(obj.tableId)) {
        toastr.error("表协议为空，请填写表协议数据");
        return null;
    }
    // 数据组织分类不能为空
    if (checkIsNull(obj.organizationClassify)) {
        toastr.error("数据组织分类为空，请选择数据组织分类");
        return null;
    }
    if (checkIsNull(obj.sourceClassify)) {
        toastr.error("数据来源分类为空，请选择数据来源分类");
        return null;
    }
    var reg1 = new RegExp(/^\w*\W+\w*/);
    if (reg1.test(obj.tableId)) {
        toastr.error("表协议只能由英文字母/数字/下划线组成，存在非法字符");
        return null;
    }
    //  描述的长度
    if(!checkIsNull(obj.objectMemo)&& obj.objectMemo.length > 256){
        toastr.error("【描述】的字段长度不能超过256");
        return null;
    }
    // try{
    //     var organizationClassifyStr = $("#organizationClassifyInput").val();
    //     var dataSourceClassifyStr = $("#sourceClassifyInput").val();
    //     if(organizationClassifyStr.indexOf("原始库") !== -1 && dataSourceClassifyStr !== ""){
    //        var code = getNewTableRelationCode();
    //        object.dataSourceClassifyStr = dataSourceClassifyStr;
    //        object.code = code;
    //     }
    // }catch (e){
    //     console.log(e)
    // }
    return object;
}


function getTagText(id) {
    var tagList = $("#"+id).find('option:selected');
    var tagContextList = [];
    for(var i = 0; i<tagList.length;i++){
        tagContextList.push(tagList[i].textContent)
    }
    return tagContextList.join(",")
}

function closeApproval() {
    var objectId = $("#objectIdManage").text();
    if (checkIsNull(objectId)) {
        if(!checkIsNull(getQueryParam("moduleName"))){
            location.reload();
        }else{
            window.parent.location = pageUrl;
        }
    }
}

function checkTableRealName(realTableName, organizationClassify, sourceClassify) {
    var errorInformation = '';
    var errorInformation1 = '';
    if (organizationClassify == null || realTableName == '') {
        return errorInformation1 = '表名不能为空';
    }
    /***
     * 对表名规范进行校验
     * 对于新增的表需要校验，
     * 对于编辑的表，如果是新增符合规则的进行校验，
     * 如果是原来的老表就不做校验，直接给他保存
     *** ***/
    var objectId = $("#objectIdManage").text();
    if (checkRealTableNameisOldOrNew(realTableName) && !checkIsNull(objectId)) {
        return errorInformation;
    }
    var tableNameArray = realTableName.split("_");
    var organizationArray = organizationClassify.split("/");
    var sourceClassifyArray = sourceClassify.split("/");

    if (tableNameArray.length < 4) {
        return errorInformation = "真实表名填写至少需要四个元素"
    }
    // 如果表名最后是以 _xxx结尾 则禁止保存
    var reg = new RegExp(/_[x]*$/);
    var flag = reg.test('_' + tableNameArray[3].toLowerCase());
    if (flag) {
        return "表名不能以_xx结尾";
    }
    var reg1 = new RegExp(/^\w*\W+\w*/);
    if (reg1.test(realTableName)) {
        return "表名只能由英文字母/数字/下划线组成，存在非法字符";
    }
    var dataName = $("#dataSourceNameInput").val().replace(/_|\(|\)|\（|\）|-|,|，|—/g,"");
    if (checkIsNull(dataName.trim())) {
        return "数据名不能为空";
    }
    if ("原始库" == organizationArray[0]) {
        if ("ysk" != tableNameArray[0].toLowerCase()) {
            errorInformation1 = "表命名不符合规则,原始库的数据层次为ysk \n" + errorInformation;
            return errorInformation1;
        }
    }
    if ("资源库" == organizationArray[0]) {
        if ("zyk" != (tableNameArray[0].toLowerCase())) {
            errorInformation1 = "表命名不符合规则,资源库的数据层次为zyk \n" + errorInformation;
            return errorInformation1;
        }
    }
    if ("主题库" == organizationArray[0]) {
        if ("ztk" != tableNameArray[0].toLowerCase()) {
            errorInformation1 = "表命名不符合规则,主题库的数据层次为ztk \n" + errorInformation;
            return errorInformation1;
        }
    }
    if ("知识库" == organizationArray[0]) {
        if ("zsk" != tableNameArray[0].toLowerCase()) {
            errorInformation1 = "表命名不符合规则,知识库的数据层次为zsk \n" + errorInformation;
            return errorInformation1;
        }
    }
    if ("业务库" == organizationArray[0]) {
        if ("ywk" != tableNameArray[0].toLowerCase()) {
            errorInformation1 = "表命名不符合规则,业务库的数据层次为ywk \n" + errorInformation;
            return errorInformation1;
        }
    }
    if ("业务要素索引库" == organizationArray[0]) {
        if ("yssy" != tableNameArray[0].toLowerCase()) {
            errorInformation1 = "表命名不符合规则,业务要素索引库数据层次为yssy \n" + errorInformation;
            return errorInformation1;
        } else {
            return '';
        }
    }

    /**
     * 根据中文的数据组织去检测表的命名规则
     * ***/
    var flag = false;
    if ("原始库" === organizationArray[0]) {
        flag = true;
    }
    $.ajax({
        type: "get",
        url: "getEnFlagByChnType",
        contentType: 'application/json',
        dataType: 'json',
        calculable: false,
        data: {
            "organizationClassifys": organizationClassify,
            "sourceClassifys": sourceClassify,
            "dataSourceName": dataName,
            "flag": flag
        },
        async: false,
        success: function (result) {
            try {
                if (result.status === 1) {
                    var dataCheckName = result.result;
                    tableNameArray.pop();
                    if (tableNameArray.join("_").toLowerCase() + "_" !== dataCheckName.toLowerCase()) {
                        errorInformation1 = "表名不正确，不符合规则";
                    }
                    // if(result.result[0].toLowerCase()!=tableNameArray[1].toLowerCase()){
                    // errorInformation1 = tableNameArray[1].toLowerCase()+"与"+organizationArray[1]+(organizationArray[2]==undefined?"":"/"+organizationArray[2])+"不匹配，正确的应为"+result.result[0].toLowerCase()+"\n"+ errorInformation1;
                    // }
                } else {
                    toastr.error(result.msg);
                    errorInformation1 = result.msg;
                }
            } catch (e) {
                errorInformation1 = "";
                // toastr.error("判断表名准确性报错");
            }

        },
        error: function (result) {
            toastr.error("检查表命名规范查询报错");
            return;
        }
    });
    if (errorInformation1 != '') {
        return errorInformation1;
    }
    if ($("#dataSourceNameInput").val() == null || $("#dataSourceNameInput").val().replace(/_|\(|\)|\（|\）|-|,|，|—/g,"") == "") {
        return errorInformation1 = "数据名不可为空"
    }
    /**
     *检测数据描述是否为数据名的拼音手写！
     **/
    // if (tableNameArray[2].toLowerCase() != Pinyin.GetJP($("#dataSourceNameInput").val().replace(/_|\(|\)|\（|\）|-|,|，|—/g,""))) {
    //     return errorInformation1 = "表命名不规范" + tableNameArray[2] + "表名描述应该为 " + (Pinyin.GetJP($("#dataSourceNameInput").val().replace(/_|\(|\)|\（|\）|-|,|，|—/g,"")));
    // }
    //检测表名是否存在，如果存在，提示，表名已经存在了,不给保存
    $.ajax({
        type: "get",
        url: "checTableNamekIsExit",
        contentType: 'application/json',
        dataType: 'json',
        calculable: false,
        data: {
            "realTableName": realTableName,
            "objectId": $("#objectIdManage").text()
        },
        async: false,
        success: function (result) {
            if (result.status === 1) {
                if (result.result == "1") {
                    errorInformation1 = "该表名已经存在，请修改真实表名";
                }
            } else {
                toastr.error(result.msg);
            }
        },
        error: function (result) {
            toastr.error("检测表是否已经存在查询出错");
            return;
        }
    });
    return errorInformation1;
}

/**
 * 表名回填机制
 * @param organizationClassifys   数据组织分类选择框的 ids
 * @param sourceClassifys        数据来源分类选择框的 ids
 * @param dataSourceName        数据名
 */
function fillBackRealName(organizationClassifys, sourceClassifys, dataSourceName) {
    if (!checkIsNull(organizationClassifys)) {
        var flag = false;
        var organizationArray = organizationClassifys.split("/");
        if ("原始库" === organizationArray[0]) {
            flag = true;
        }
        $.ajax({
            type: "get",
            url:  "getEnFlagByChnType",
            contentType: 'application/json',
            dataType: 'json',
            calculable: false,
            data: {
                "organizationClassifys": organizationClassifys,
                "sourceClassifys": sourceClassifys,
                "dataSourceName": dataSourceName,
                "flag": flag
            },
            async: false,
            success: function (result) {
                if (result.status === 1) {
                    backFilePojo.tableName = result.result.toLowerCase();
                } else {
                    toastr.error(result.msg);
                }
            },
            error: function (result) {
                toastr.error("检查表命名规范查询报错");
                return;
            }
        });
    }
}

function flowCompleteClick() {
    try {
        isClick = false;
        var message = {}, taskInfo = {};
        var outSourceFirmStr = checkIsNull($("#sourceFirmTdSelect").val()) ? $("#sourceFirm").text() : $("#sourceFirmTdSelect").val()
        taskInfo.standardId = $("#tableId").text();
        message.pageTag = 'standard';
        message.standardPropStr = JSON.stringify(taskInfo);
        message.outSourceSystem = $("#objectName").val();     //所属系统代码（数值）
        message.outTableName = $("#dataSourceName").text();           //协议中文名
        message.outSourceProtocol = $("#tableId").text(); //数据协议
        message.outSourceFirm = outSourceFirmStr;         //数据产商中文名
        message.outSourceSystemName = $("#objectName").val(); //
        message.objectId = $("#objectIdManage").text();   //序号
        message.sysChiName = $("#objectName").text();     //所属系统中文名
        message.sourceFirmCode = getSourceFirmCode(outSourceFirmStr);//输出-产商代码
        message.standardFieldJsonStr = getTableColumnByTableIdFlow();
        if (message.standardFieldJsonStr == "") {
            return;
        }
        window.parent.postMessage(message, "*");
        toastr.info("数据定义已完成！");
    } catch (errMsg) {
        toastr.error(errMsg)
    }
}


function checkRealTableNameisOldOrNew(realTableName) {
    var mainClassArray = ['ysk', 'zyk', 'ztk', 'zsk', 'ywk', 'yssy'];
    var realNameArray = realTableName.split("_");
    if ($("#realTableNameInput")[0].attributes.readOnly !== undefined) {
        return true;
    }
    ;
    if (mainClassArray.indexOf(realNameArray[0].toLowerCase()) != -1) {
        return false;
    } else {
        return true;
    }
}


/**
 *  编辑时多选框创造对应的select的html值
 */
function getmultipleSelect(id, data, normalList) {
    $("#" + id).text("");
    var selectHtml = "<select id='" + id + "select' class = \"selectpicker form-control\" " +
        "data-live-search='true'  style=\"width: 100%;height: 34px\" title='请选择标签值' multiple ></select>";
    $("#" + id).append(selectHtml);
    if (data.length > 0) {
        data.forEach(function (value) {
            $("#" + id + "select").append("<option value=" + value.labelCode + ">" + value.labelName + "</option>");
        })
    }
    $("#" + id + "select").selectpicker({liveSearch: true, size: 10, noneResultsText: "没有找到对应的记录{0}"});
    $("#" + id + "select").selectpicker('val', normalList);
}

/**
 * 编辑之后6类标签值的修改
 * 20200902 去除掉
 */
function tagSelectPageEdit() {
    //  先获取所有标签值的码表
    //  标签1 的修改
    // var bodyTag1List = $("#bodyTag1").val().split(",");
    // var elementTag2List = $("#elementTag2").val().split(",");
    // var objectDescTag3List = $("#objectDescTag3").val().split(",");
    // var behaviorTag4List = $("#behaviorTag4").val().split(",");
    // var relationShipTag5List = $("#relationShipTag5").val().split(",");
    // var locationTag6List = $("#locationTag6").val().split(",");
    // $.ajax({
    //     type: "get",
    //     url: "getTagAllDataMap",
    //     contentType: 'application/json',
    //     dataType: 'json',
    //     data: {
    //         "bodyTag": $("#bodyTag1").val()
    //     },
    //     calculable: true,
    //     async: true,
    //     success: function (result) {
    //         if (result.status === 1) {
    //             getmultipleSelect("bodyTag1", result.result.bodyTag1, bodyTag1List);
    //             getmultipleSelect("elementTag2", result.result.elementTag2, elementTag2List);
    //             getmultipleSelect("objectDescTag3", result.result.objectDescTag3, objectDescTag3List);
    //             getmultipleSelect("behaviorTag4", result.result.behaviorTag4, behaviorTag4List);
    //             getmultipleSelect("relationShipTag5", result.result.relationShipTag5, relationShipTag5List);
    //             getmultipleSelect("locationTag6", result.result.locationTag6, locationTag6List);
    //             aaa();
    //         } else {
    //             toastr.error(result.msg);
    //         }
    //     },
    //     error: function (result) {
    //         toastr.error("获取所有标签表码表信息报错");
    //         return;
    //     }
    // });

}

/**
 *  保存成功之后移除多选框  6个标签值填写
 */
function saveRemoveSelect() {
    // try {
    //     //
    //     var bodyTag1 = $("#bodyTag1select").find("option:selected");
    //     $("#bodyTag1").text("");
    //     if (bodyTag1.length > 0) {
    //         var valueList = [];
    //         var valList = [];
    //         for (var i = 0; i < bodyTag1.length; i++) {
    //             valueList.push(bodyTag1[i].textContent);
    //             valList.push(bodyTag1[i].value)
    //         }
    //         $("#bodyTag1").text(valueList.join(","));
    //         $("#bodyTag1").val(valList.join(","));
    //     }
    //     var elementTag2 = $("#elementTag2select").find("option:selected");
    //     $("#elementTag2").text("");
    //     if (elementTag2.length > 0) {
    //         var valueList = [];
    //         var valList = [];
    //         for (var i = 0; i < elementTag2.length; i++) {
    //             valueList.push(elementTag2[i].textContent);
    //             valList.push(elementTag2[i].value)
    //         }
    //         $("#elementTag2").text(valueList.join(","));
    //         $("#elementTag2").val(valList.join(","));
    //     }
    //     var objectDescTag3 = $("#objectDescTag3select").find("option:selected");
    //     $("#objectDescTag3").text("");
    //     if (objectDescTag3.length > 0) {
    //         var valueList = [];
    //         var valList = [];
    //         for (var i = 0; i < objectDescTag3.length; i++) {
    //             valueList.push(objectDescTag3[i].textContent);
    //             valList.push(objectDescTag3[i].value)
    //         }
    //         $("#objectDescTag3").text(valueList.join(","));
    //         $("#objectDescTag3").val(valList.join(","));
    //     }
    //     var behaviorTag4 = $("#behaviorTag4select").find("option:selected");
    //     $("#behaviorTag4").text("");
    //     if (behaviorTag4.length > 0) {
    //         var valueList = [];
    //         var valList = [];
    //         for (var i = 0; i < behaviorTag4.length; i++) {
    //             valueList.push(behaviorTag4[i].textContent);
    //             valList.push(behaviorTag4[i].value)
    //         }
    //         $("#behaviorTag4").text(valueList.join(","));
    //         $("#behaviorTag4").val(valList.join(","));
    //     }
    //     var relationShipTag5 = $("#relationShipTag5select").find("option:selected");
    //     $("#relationShipTag5").text("");
    //     if (relationShipTag5.length > 0) {
    //         var valueList = [];
    //         var valList = [];
    //         for (var i = 0; i < relationShipTag5.length; i++) {
    //             valueList.push(relationShipTag5[i].textContent);
    //             valList.push(relationShipTag5[i].value)
    //         }
    //         $("#relationShipTag5").text(valueList.join(","));
    //         $("#relationShipTag5").val(valList.join(","));
    //     }
    //     var locationTag6 = $("#locationTag6select").find("option:selected");
    //     $("#locationTag6").text("");
    //     if (locationTag6.length > 0) {
    //         var valueList = [];
    //         var valList = [];
    //         for (var i = 0; i < locationTag6.length; i++) {
    //             valueList.push(locationTag6[i].textContent);
    //             valList.push(locationTag6[i].value)
    //         }
    //         $("#locationTag6").text(valueList.join(","));
    //         $("#locationTag6").val(valList.join(","));
    //     }
    // } catch (e) {
    //     toastr.error("保存6类标签报错");
    // }

}

// 清除6类标签的数据
function clean6TagValue() {
    // $("#bodyTag1").val("");
    // $("#bodyTag1").text("");
    // $("#elementTag2").val("");
    // $("#elementTag2").text("");
    // $("#objectDescTag3").val("");
    // $("#objectDescTag3").text("");
    // $("#behaviorTag4").val("");
    // $("#behaviorTag4").text("");
    // $("#relationShipTag5").val("");
    // $("#relationShipTag5").text("");
    // $("#locationTag6").val("");
    // $("#locationTag6").text("");
}

/**
 * 查询表名是否存在 如果存在，则将表名输入框设置为只读，禁止修改
 */
function queryTableIsExist(tableName, tableInputId) {
    $.ajax({
        type: "get",
        url:  "queryTableIsExist",
        contentType: 'application/json',
        dataType: 'json',
        calculable: false,
        async: false,
        data: {
            "tableName": tableName
        },
        success: function (result) {
            if (result.status === 1) {
                if (result.result) {
                    toastr.info("表名" + tableName + "已经在数据平台中建表，表名禁止修改");
                    $("#" + tableInputId).attr("readOnly", "true");
                } else {
                    realTableFocus();
                    dataSourceNameFocus();
                }
            } else {
                toastr.error(result.msg);
            }
        },
        error: function (result) {
            toastr.error("获取表是否已经创建报错");
            return;
        }
    });

}

/**
 * 建表时必填项的确认 基本的几项
 */
function checkRequired(obj) {
    if (obj.dataId === "selected" || checkIsNull(obj.dataId)) {
        return "数据源名称不能为空";
    }
    // if(obj.objectFields==="selected" || checkIsNull(obj.objectFields)){
    //   return "建表字段不能为空";
    // }
    if (obj.dsType === "selected" || checkIsNull(obj.dsType)) {
        return "数据源类型不能为空";
    }
    // @TODO
    if (obj.schema === "selected" || checkIsNull(obj.schema)) {
        return "项目名不能为空";
    }
    if (obj.tableName === "selected" || checkIsNull(obj.tableName)) {
        return "表名不能为空";
    }
    return "";
}


/**
 * 当主体标签发生变化之后，其它5类标签需要对应的变化
 */

function createOtherLabelByOne(value) {
    var elementTag2List = $("#elementTag2").val().split(",");
    var objectDescTag3List = $("#objectDescTag3").val().split(",");
    var behaviorTag4List = $("#behaviorTag4").val().split(",");
    var relationShipTag5List = $("#relationShipTag5").val().split(",");
    var locationTag6List = $("#locationTag6").val().split(",");
    $.ajax({
        type: "get",
        url: "getTagAllDataMap",
        contentType: 'application/json',
        dataType: 'json',
        data: {
            "bodyTag": value
        },
        calculable: true,
        async: true,
        success: function (result) {
            if (result.status === 1) {
                getmultipleSelect("elementTag2", result.result.elementTag2, elementTag2List);
                getmultipleSelect("objectDescTag3", result.result.objectDescTag3, objectDescTag3List);
                getmultipleSelect("behaviorTag4", result.result.behaviorTag4, behaviorTag4List);
                getmultipleSelect("relationShipTag5", result.result.relationShipTag5, relationShipTag5List);
                getmultipleSelect("locationTag6", result.result.locationTag6, locationTag6List);
            } else {
                toastr.error(result.msg);
            }
        },
        error: function (result) {
            toastr.error("获取所有标签表码表信息报错");
            return;
        }
    });
}

function aaa() {
    // $('#bodyTag1select').on('changed.bs.select', function (e, clickedIndex, isSelected, previousValue) {
    //     var value = "";
    //     if (checkIsNull($(this).val())) {
    //         value = "";
    //     } else {
    //         value = $(this).val().join(",");
    //     }
    //     createOtherLabelByOne(value);
    // });
}


function getPageUrl() {
    $.ajax({
        type: "get",
        url: "getPageUrl",
        contentType: 'application/json',
        dataType: 'json',
        calculable: true,
        async: true,
        success: function (result) {
            pageUrl = result[0] + "?src=" + encodeURIComponent(result[1]);
        },
        error: function (result) {
            pageUrl = "";
            return;
        }
    });
}


function jumpToProcessingPage(tableId, sourceId) {
    $.ajax({
        type: "get",
        url: "requestDataHandle",
        contentType: 'application/json',
        dataType: 'json',
        calculable: false,
        async: false,
        data: {
            "tableId": tableId,
            "sourceId": sourceId
        },
        success: function (result) {
            var dataGovernanceUrl = result[0];
            var url = result[1];
            console.log(dataGovernanceUrl + "?src=" + url);
            // window.open(url+ '&title=数据处理规则配置','_blank');
            window.open(dataGovernanceUrl + "?src=" + encodeURIComponent(url) + '&title=数据处理规则配置', '_blank');
        },
        error: function (result) {
            toastr.error("跳转失败");
            return;
        }
    });
}
function cancelSaveModelObjectTwo() {
    if(!checkIsNull(getQueryParam("moduleName"))){
        location.reload();
    }else{
        sessionStorage.setItem('refreshPageCook', true);
        window.parent.location = pageUrl;
    }

}

function saveModelObject() {
    // 去除取消按钮显示
    $("#cancelResource").attr("style", "display:none")

    var flag = saveResourceManageTable();
    if (flag) {
        clickTree(null, $("#nodeIdModel").val(), $("#nodeNameModel").val());
        $("#saveModelObject").modal("hide");
        $("#nodeIdModel").val("");
        $("#nodeNameModel").val("");
    } else {
        // 如果保存数据报错，则显示取消按钮
        $("#cancelResource").attr("style", "display:inline")

        toastr.error("保存数据报错");
        $("#nodeIdModel").val("");
        $("#nodeNameModel").val("");
        $("#saveModelObject").modal("hide");
    }
}
// 这个是 新增按钮 跳转到新增标准的页面
function saveModelTwoObject() {
    var flag = saveResourceManageTable();
    if (flag) {
        if(!checkIsNull(getQueryParam("moduleName"))){
            location.reload();
        }else{
            sessionStorage.setItem('refreshPageCook', true);
            window.parent.location = pageUrl;
        }

    } else {
        $("#nodeIdModel").val("");
        $("#nodeNameModel").val("");
        $("#saveModelObjectTwo").modal("hide");
    }
}

function cancelSaveModelObject() {
    // 去除取消按钮显示
    $("#cancelResource").attr("style", "display:none")

    clickTree(null, $("#nodeIdModel").val(), $("#nodeNameModel").val());
    $("#nodeIdModel").val("");
    $("#nodeNameModel").val("");
    $("#saveModelObject").modal("hide");
    removeAddButtonDisabled("save");
}

function addTotalAndtitle(id, num) {
    $(id + " .page-list ul li").append('<span>条/页</span>');
    $(id + " .page-list button span.page-size").append('<span>条/页</span>');
    $(id + ' .fixed-table-pagination').prepend('<div class="total-num">共 ' + num + ' 条</div>');
}

function showLifeNum() {
    var life = $("input[name='life']");
    if (life[0].checked) {
        $("#lifeDays").val("");
        $("#lifeDays").attr('readonly', true);
    } else {
        $("#lifeDays").attr('readonly', false);
    }

}

/**
 *
 */
function tableRegisterModel() {
    //先从后台获取已建表
    // var  realTableName = $("#realTableName").text();
    var realTableName = $("#tableId").text()
    if(checkIsNull(realTableName)){
        toastr.error("表名为空，不能进行表登记");
        return;
    }
    $.ajax({
        type: "get",
        data: {
            "tableId": realTableName
        },
        url:  "dbManager/getAllStandardTableCreatedList",
        calculable: false,
        async: false,
        success: function (result) {
            console.log(result);
            if (result.status === 1) {
                $("#tableRegisterModalSelect").empty();
                var data = result.data;
                if(data === null ||data.length === 0){
                    toastr.error("该数据没有通过标准管理工具创建表，不能进行表登记");
                    return;
                }
                data.forEach(function (obj) {
                    $("#tableRegisterModalSelect").append("<option value=" + obj.tableBase+'|'+obj.tableProject+"."+obj.tableName + " selected  style='width: 220px' >" + obj.tableProject + "." + obj.tableName + "</option>");
                });
                $("#tableRegisterModal").modal("show");
            } else {
                toastr.error(result.msg);
            }
        },
        error: function (result) {
            toastr.error("获取已经创建表信息报错，请检查");
        }
    });
}

/**
 * 因为是已经保存的数据，根据传入的tableId  objectId 查询 需要传递过去的信息，然后生成对应的url
 */
function tableRegister() {
    var objectId = $("#objectIdManage").text();
    // 格式为 tableBase|tableProject.tableName
    var tableName = $("#tableRegisterModalSelect option:selected").val();
    var tableId = $("#tableId").text();
    if(checkIsNull(objectId)||checkIsNull(tableId)){
        toastr.error("数据的tableId为空，不能进行表登记");
        return;
    }
    $.ajax({
        type: "get",
        data: {
            "tableName": tableName,
            "tableId":tableId,
            "objectId":objectId
        },
        url: "getTableRegisterUrl",
        calculable: false,
        async: false,
        success: function (result) {
            console.log(result);
            if (result.status === 1) {
                window.open(result.result);
                $("#tableRegisterModal").modal("hide");
            } else {
                toastr.error(result.message);
            }
        },
        error: function (result) {
            toastr.error("获取已经创建表信息报错，请检查");
        }
    });
}


$(document).click(function(e){
    var target = $(e.target);
    if( $(".table-filter")[0].style.display === 'block'){
        if(target.closest(".table-filter").length === 0 ){
            $(".table-filter").css("display", "none");
        }
    }
});
// 监听滚动条和缩放 如果有，则隐藏掉
$(window).bind('resize',function(){
    $(".table-filter").css("display", "none");
});


// 20200605 fieldName 是重复的  所以需要用另一种方式来获取数据
// 提示框中提示两列数据
function threeSuggest(domID,url) {
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
            'padding-top': 0, 'max-height': '300px', 'max-width': '220px',"min-width":"220px",
            'overflow': 'auto', 'width': '220px',
            'transition': '0.3s', '-webkit-transition': '0.3s', '-moz-transition': '0.3s', '-o-transition': '0.3s',

        },                              //列表的样式控制
        autoMinWidth: true,
        listAlign: 'left'             //提示列表对齐位置，left/right/auto
    }).on('onDataRequestSuccess', function(e, result){

    }).on('onSetSelectValue', function(e, keyword, data){
        // 选择的数据为
        console.log('onSetSelectValue: ', keyword, data);
        fieldNameCheckAdd(data.fieldId);
    }).on('onUnsetSelectValue', function(){
        console.log("onUnsetSelectValue");
    });
}


function fourSuggest(domID,url){
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
            'padding-top': 0, 'max-height': '300px', 'max-width': '220px',"min-width":"220px",
            'overflow': 'auto', 'width': '220px',
            'transition': '0.3s', '-webkit-transition': '0.3s', '-moz-transition': '0.3s', '-o-transition': '0.3s',

        },                              //列表的样式控制
        autoMinWidth: true,
        listAlign: 'left'             //提示列表对齐位置，left/right/auto
    }).on('onDataRequestSuccess', function(e, result){

    }).on('onSetSelectValue', function(e, keyword, data){
        // 选择的数据为
        console.log('onSetSelectValue: ', keyword, data);
        fieldNameCheckUpdate(data.fieldId);
    }).on('onUnsetSelectValue', function(){
        console.log("onUnsetSelectValue");
    });
}



function fieldNameCheckUpdate(fieldId) {
    var columnNameInput = $("#fieldNameAddModel").val();
    var columnText = $("#fieldNameAddModel").val();
    if($("#pkRecnoAddModel").val()==="true"&&columnText.toUpperCase()!=="DT"){
        $("#partitionRecnoAddModel").find("option:contains('一级分区列')").attr("disabled", false);
    } else if($("#pkRecnoAddModel").val()==="false"){
        $("#partitionRecnoAddModel").selectpicker('val', "");
        $("#partitionRecnoAddModel").find("option:contains('一级分区列')").attr("disabled", true);
        $("#partitionRecnoAddModel").find("option:contains('二级分区列')").attr("disabled", true);
    } else if(columnText.toUpperCase()=="DT"){
        if($("#partitionRecnoAddModel")=="1"){
            $("#partitionRecnoAddModel").selectpicker('val', "");
        }
        $("#partitionRecnoAddModel").find("option:contains('一级分区列')").attr("disabled", true);
        $("#partitionRecnoAddModel").find("option:contains('二级分区列')").attr("disabled", false);
    }
    $(".selectpicker").selectpicker("refresh");
    if (checkIsNull(columnNameInput.trim())) {
        return;
    } else {
        // toastr.info("获取表字段名为："+columnNameInput+"的相关字段信息");
        // 之后ajax查询相关的字段信息
        getAddColumnByInputUpdate("columnName", fieldId);
    }
}

function fieldNameCheckAdd(fieldId){
    var columnNameInput = $("#fieldNameAddModel").val();
    if (checkIsNull(columnNameInput.trim())) {
        inputSelectClean();
        return;
    } else {
        // toastr.info("获取表字段名为："+columnNameInput+"的相关字段信息");
        // 之后ajax查询相关的字段信息
        getAddColumnByInput("columnName", fieldId);
    }
}


function getUserId() {
    var userId ;
    try{
        userId = document.referrer.match(/userId=(\d+)&/)[1];
    }catch (e){
        userId = "";
    }
    return userId;
}

function getCreatedTableData(tableId) {
// 获取到已经创建的表信息
    $.ajax({
        type: "get",
        data: {
            "tableId": tableId
        },
        url: "dbManager/getAllStandardTableCreatedList",
        calculable: false,
        async: false,
        success: function (result) {
            if (result.status === 1) {
                $("#createdTable").bootstrapTable('load', result.data);
                if (checkIsNull(result.data)) {
                    created_table_list = [];
                } else {
                    created_table_list = result.data;
                }
                addTotalAndtitle('#createdTableTab', created_table_list.length);

            } else {
                $("#createdTable").bootstrapTable('load', []);
                created_table_list = [];
                addTotalAndtitle('#createdTableTab', created_table_list.length);
                toastr.error(result.msg);
            }
        },
        error: function (result) {
            toastr.error("获取已经创建表信息报错");
        }
    });
}

/**
 * 20200907 出现新的数据来源信息 数据从表 PUBLIC_DATA_INFO 中查询
 * @param tableId  表协议ID
 */
function getSourceRelationData(tableId) {
    $.ajax({
        type: "get",
        data: {
            "tableId": tableId
        },
        url: "getSourceRelationData",
        calculable: false,
        async: false,
        success: function (result) {
            if (result.status === 1) {
                var data = result.result;
                updateHtmlData(data);
            } else {
                $("#SOURCEID_SOURCE").text("");
                $("#SJZYSQDWMC").text("");
                $("#SJZYSQDW_SQDWDM").text("");
                $("#SQDWLXR").text("");
                $("#SQDWLXDH").text("");
                $("#SJHQFS").text("");
                $("#SJHQFS").val("");
                $("#SJZYWZ").text("");
                $("#SJZYWZ").val("");
                $("#SJZYCCFZX").text("");
                $("#SJZYGLMC").text("");
                $("#SJZYGLDW_GAJGJGDM").text("");
                $("#JRFWF").text("");
                $("#TGJRFS").text("");
                $("#JRFWR").text("");
                $("#JRFWLXDH").text("");
                $("#SJJRSM").text("");
                $("#YYYXTGLDW").text("");
                $("#SJZYGXZQ").text("");
                $("#GSMS").text("");
                // toastr.info(result.message);
            }
        },
        error: function (result) {
            toastr.error("获取新的数据来源信息报错");
        }
    });

}

/**
 * @20200911 获取页面上数据来源信息的数据 有2种保存可能  一种是从数据仓库获取的(不能修改)
 * 第2种是可以修改的 其中 来源协议编码 的值为空
 * @param obj
 */
function getDataSourceInformationPage(obj) {
    // try {
    var publicDataInfoObject = new Object();
    var sourceIdInput = $("#SOURCEID_SOURCE_input").val();
    // 这个表示数据仓库那边的 sourceId为空 是自己新增的数据
    if (!checkIsNull(getQueryParam("moduleName")) && sourceIdInput !== undefined ) {
        // 这个表示是 从自己修改的页面填写然后获取到的数据
        // 数据来源分类的相关信息
        // 20200921 数据来源分类使用数据信息里面的来源分类信息　来源分类全部使用中文　
        // var sjzylylx = $("#SJZYLYLX").val();
        var sjzylylx =　$("#sourceClassifyInput").val();
        // 源应用系统名称
        //var yyyxtmc = $("#YYYXTMC_select option:selected").text();
        var yyyxtmc = $("#objectNameSelect option:selected").text()
        // 源应用系统编号  YYYXTMM
        // var yyyxtmm = $("#YYYXTMM_input").val();
        var yyyxtmm = $("#objectNameSelect option:selected").val();
        // 源应用系统建设公司
        // var yyyxtjsgs = $("#YYYXTJSGS_select option:selected").text();
        var yyyxtjsgs = $("#sourceFirmTdSelect option:selected").text();
        // 数据资源事权单位名称
        var sjzysqdwmc = $("#SJZYSQDWMC_input").val();
        // 事权单位机构代码
        var sjzysqdwSqdwdm = $("#SJZYSQDW_SQDWDM_input").val();
        // 来源协议编码
        var sourceidSource = $("#SOURCEID_SOURCE_input").val();
        // 事权单位联系人
        var sqdwlxr = $("#SQDWLXR_input").val();
        // 事权单位联系电话
        var sqdwlxdh = $("#SQDWLXDH_input").val();
        // 数据获取方式
        var sjhqfs = $("#SJHQFS_select option:selected").val();
        // 数据资源位置
        var sjzywz = $("#SJZYWZ_select option:selected").val();
        // 数据资源存储中心
        var sjzyccfzx = $("#SJZYCCFZX_input").val();
        // 资源管理单位名称
        var sjzyglmc = $("#SJZYGLMC_input").val();
        //  资源管理单位机构代码
        var sjzygldwGajgjgdm = $("#SJZYGLDW_GAJGJGDM_input").val();
        var classIds = sjzylylx.split("/");
        // TODO  如果组织分类一级分类是  原始库 则这些是必填 否则可以不是必填

        /** 20210126 数据接入的相关信息**/
        var jrfwf = $("#JRFWF").text();
        var tgjrfs = $("#TGJRFS").text();
        var jrfwr = $("#JRFWR").text();
        var jrfwlxdh = $("#JRFWLXDH").text();
        var sjjrsm = $("#SJJRSM").text();
        var gsms = $("#GSMS").text();

      /** 源应用系统管理单位 **/
      var yyyxtgldw = $("#YYYXTGLDW").text();
      var sjzygxzq = $("#SJZYGXZQ").text();

      if(classIds.length >1){
            publicDataInfoObject.SJZYLYLX = classIds[1];
        }else if(classIds.length  === 1){
            publicDataInfoObject.SJZYLYLX = sjzylylx;
        }else{
            publicDataInfoObject.SJZYLYLX = "";
        }
        publicDataInfoObject.YYYXTMC = yyyxtmc;
        publicDataInfoObject.YYYXTMM = yyyxtmm;
        publicDataInfoObject.YYYXTJSGS = yyyxtjsgs;
        publicDataInfoObject.SJZYSQDWMC = sjzysqdwmc;
        publicDataInfoObject.SJZYSQDW_SQDWDM = sjzysqdwSqdwdm;
        publicDataInfoObject.SOURCEID = sourceidSource;
        publicDataInfoObject.SQDWLXR = sqdwlxr;
        publicDataInfoObject.SQDWLXDH = sqdwlxdh;
        publicDataInfoObject.SJHQFS = sjhqfs;
        publicDataInfoObject.SJZYWZ = sjzywz;
        publicDataInfoObject.SJZYCCFZX = sjzyccfzx;
        publicDataInfoObject.SJZYGLMC = sjzyglmc;
        publicDataInfoObject.SJZYGLDW_GAJGJGDM = sjzygldwGajgjgdm;

        publicDataInfoObject.JRFWF = jrfwf;
        publicDataInfoObject.TGJRFS = tgjrfs;
        publicDataInfoObject.JRFWR = jrfwr;
        publicDataInfoObject.JRFWLXDH = jrfwlxdh;
        publicDataInfoObject.SJJRSM = sjjrsm;
        publicDataInfoObject.GSMS = gsms;

        publicDataInfoObject.YYYXTGLDW = yyyxtgldw;
        publicDataInfoObject.SJZYGXZQ = sjzygxzq;
        //  需要验证输入的数据格式是否正确
        // if(checkIsNull(sjzysqdwmc)){
        //     toastr.error("数据资源事权单位名称不能为空");
        //     throw  DOMException("数据资源事权单位名称不能为空");
        // }
        // if(checkIsNull(sjzysqdwSqdwdm)){
        //     toastr.error("事权单位机构代码不能为空");
        //     throw  DOMException("事权单位机构代码不能为空");
        // }
        if(checkIsChinese(sjzysqdwSqdwdm)){
            toastr.error("事权单位机构代码不能存在中文");
            throw  DOMException("事权单位机构代码不能存在中文");
        }

    } else {
        // 数据来源分类 的代码值 数据资源来源类型 这个只存储二级分类
        // 数据来源一级分类存储在 数据组织二级分类
        // "JZCODEGASJZZFL010101,JZCODEGASJZZFL010101001"
        // 变成中文
        // var sjzylylx = $("#SJZYLYLX").val();
        var sjzylylx =　$("#sourceClassifyInput").val();
        // 源应用系统名称
        // var yyyxtmc = $("#YYYXTMC").text();
        var yyyxtmc = $("#objectNameSelect option:selected").text()
        // 源应用系统编号  YYYXTMM
        // var yyyxtmm = $("#YYYXTMM").text();
        var yyyxtmm = $("#objectNameSelect option:selected").val();
        // 源应用系统建设公司
        // var yyyxtjsgs = $("#YYYXTJSGS").text();
        var yyyxtjsgs = $("#sourceFirmTdSelect option:selected").text();
        // 数据资源事权单位名称
        var sjzysqdwmc = $("#SJZYSQDWMC").text();
        // 事权单位机构代码
        var sjzysqdwSqdwdm = $("#SJZYSQDW_SQDWDM").text();
        // 来源协议编码
        var sourceidSource = $("#SOURCEID_SOURCE").text();
        // 事权单位联系人
        var sqdwlxr = $("#SQDWLXR").text();
        // 事权单位联系电话
        var sqdwlxdh = $("#SQDWLXDH").text();
        // 数据获取方式
        var sjhqfs = $("#SJHQFS").val();
        // 数据资源位置
        var sjzywz = $("#SJZYWZ").val();
        // 数据资源存储中心
        var sjzyccfzx = $("#SJZYCCFZX").text();
        // 资源管理单位名称
        var sjzyglmc = $("#SJZYGLMC").text();
        //  资源管理单位机构代码
        var sjzygldwGajgjgdm = $("#SJZYGLDW_GAJGJGDM").text();

        // 数据来源分类  只保存第2级分类
        var classIds = sjzylylx.split("/");
        if(classIds.length >1){
            publicDataInfoObject.SJZYLYLX = classIds[1];
        }else if(classIds.length  === 1){
            publicDataInfoObject.SJZYLYLX = sjzylylx;
        }else{
            publicDataInfoObject.SJZYLYLX = "";
        }
        publicDataInfoObject.YYYXTMC = yyyxtmc;
        publicDataInfoObject.YYYXTMM = yyyxtmm;
        publicDataInfoObject.YYYXTJSGS = yyyxtjsgs;
        publicDataInfoObject.SJZYSQDWMC = sjzysqdwmc;
        publicDataInfoObject.SJZYSQDW_SQDWDM = sjzysqdwSqdwdm;
        publicDataInfoObject.SOURCEID = sourceidSource;
        publicDataInfoObject.SQDWLXR = sqdwlxr;
        publicDataInfoObject.SQDWLXDH = sqdwlxdh;
        publicDataInfoObject.SJHQFS = sjhqfs;
        publicDataInfoObject.SJZYWZ = sjzywz;
        publicDataInfoObject.SJZYCCFZX = sjzyccfzx;
        publicDataInfoObject.SJZYGLMC = sjzyglmc;
        publicDataInfoObject.SJZYGLDW_GAJGJGDM = sjzygldwGajgjgdm;

        /** 20210126 数据接入的相关信息**/
        var jrfwf = $("#JRFWF").text();
        var tgjrfs = $("#TGJRFS").text();
        var jrfwr = $("#JRFWR").text();
        var jrfwlxdh = $("#JRFWLXDH").text();
        var sjjrsm = $("#SJJRSM").text();
        var gsms = $("#GSMS").text();
        publicDataInfoObject.JRFWF = jrfwf;
        publicDataInfoObject.TGJRFS = tgjrfs;
        publicDataInfoObject.JRFWR = jrfwr;
        publicDataInfoObject.JRFWLXDH = jrfwlxdh;
        publicDataInfoObject.SJJRSM = sjjrsm;
        publicDataInfoObject.GSMS = gsms;

        /** 源应用系统管理单位 **/
        var yyyxtgldw = $("#YYYXTGLDW").text();
        publicDataInfoObject.YYYXTGLDW = yyyxtgldw;

        var sjzygxzq = $("#SJZYGXZQ").text();
        publicDataInfoObject.SJZYGXZQ = sjzygxzq;
    }
  /** 20210510 新增 数据来源规模探查信息**/
  publicDataInfoObject.dataNumberScale = $("#dataNumberScale").text();
  publicDataInfoObject.dataStoreScale = $("#dataStoreScale").text();
  publicDataInfoObject.updateType = $("#updateType").text();
  publicDataInfoObject.avgStore = $("#avgStore").text();
  publicDataInfoObject.avgRecord = $("#avgRecord").text();
  obj.publicDataInfo = publicDataInfoObject;
    // } catch (e1) {
    //     console.log("保存数据来源信息报错"+e1);
    // }
}

/**
 *  页面初始化加载时 字段敏感的选择框获取
 */
function getSensitivityLevelSelect() {
    $.ajax({
        type: "get",
        url:  "getSensitivityLevelSelect",
        contentType: 'application/json',
        dataType: 'json',
        calculable: false,
        async: false,
        success: function (result) {
            if(result.status === 1){
                var data = result.result;
                $("#SensitivityLevel").empty();
               // $("#SensitivityLevel").append("<option value=''></option>");
                data.forEach(function (obj) {
                    // $("#SensitivityLevel").append("<option value="+ obj.value+" title= "+obj.memo+">" + obj.label+"</option>");
                    $("#SensitivityLevel").append("<option value="+ obj.value +">" + obj.label+"</option>");
                });
                $('.selectpicker').selectpicker('refresh');
                $('.selectpicker').selectpicker('render');
            }else{
                toastr.error(result.message);
            }
        },
        error: function (result) {
            toastr.error("获取数据来源的相关信息报错");
        }
    });

}


function createTableTab() {
    var tableId = "";
    if(editFlag){
        tableId = "";
    }else{
        tableId = $("#tableId").text();
    }
    if(tableId !== ""){
        getCreatedTableData(tableId);
    }
}

/**
 *  获取用户登录名称
 * @returns {string}
 */
function getUserName() {
    var userName ;
    try{
        userName = decodeURI(document.referrer.match(/userName=(.*?)&/)[1]) ;
    }catch (e){
        userName = "";
    }
    return userName;
}

function downTemplateFile() {
  location.href ="downloadSummaryTableExcel";
}


function resourceManageObjectFieldTabClick() {
  $("#resourceManageObjectField").bootstrapTable("refreshOptions",{})
}

function oneSourceRelationShipTabClick() {
  $("#sourceRelationShip").bootstrapTable("refreshOptions",{})
}


/**
 * 是否需要跳转到数据处理的
 */
function getJumpOrthePage() {
  $.ajax({
    type: "get",
    url:  "getJumpOrthePage",
    contentType: 'application/json',
    dataType: 'json',
    calculable: false,
    async: false,
    success: function (result) {
      if(result === false){
        $("ul #jumpOrthePageTwo").remove();
      }
    },
    error: function (result) {
      console.log(result);
    }
  });

}

/**
 * 当是否入库更改之后
 * @param data
 */
var flagData = null;
function importFlagChange(data,tableBase,tableNameEn) {
    if(editFlag){
      toastr.error("请先保存整个标准信息");
      return;
    }
    var dataAll = $("#createdTable").bootstrapTable("getData");
    for(var i =0; i< dataAll.length;i++){
      if(dataAll[i].tableBase === tableBase && tableNameEn === dataAll[i].tableNameEn){
        flagData = dataAll[i];
        flagData.importFlag = data;
        break;
      }
    }
    $("#importFlagModalTable").html(tableBase+"."+tableNameEn);
    $("#importFlagChangeModal").modal("show")
}

function importFlagSave() {
  var data = new Object();
  data.tableId = $("#tableId").text();
  data.createdTableData = flagData;
  data.userId = getUserId();
  // 选择修改之后，则修改
  $.ajax({
    type: "post",
    contentType: 'application/json',
    dataType: 'json',
    url: "editImportFlagSave",
    calculable: false,
    async: false,
    data: JSON.stringify(data),
    success: function (result) {
      if (result.status !== 1) {
          toastr.error(result.msg);
      }else{
          toastr.info(result.data);
      }
      importFlagCancle();
    },
    error: function (result) {
      toastr.error("修改报错");
      flagData = null;
    }
  });

}

function importFlagCancle() {
  $("#importFlagChangeModal").modal("hide");
  flagData = null;
  getCreatedTableData($("#tableId").text());
}

/**
 * 刷新已建表的相关信息
 */
function refreshCreateTable(){
  if(editFlag){
    toastr.error("请先保存整个标准信息");
    return;
  }
  addMask();
  var data = new Object();
  data.tableName = $("#realTableName").text();
  data.tableId = $("#tableId").text();
  data.userName = getUserName();
  data.userId = getUserId();
  data.objectName = $("#dataSourceName").text();
  $.ajax({
    type: "post",
    url: "refreshCreateTable",
    calculable: false,
    contentType: 'application/json',
    dataType: 'json',
    async: true,
    data:JSON.stringify(data),
    success: function (result) {
      if (result.status !== 1) {
        toastr.error(result.msg);
      }else{
        toastr.info(result.data);
      }
      getCreatedTableData($("#tableId").text());
      $.unblockUI();
    },
    error: function (result) {
      toastr.error("刷新已建表报错");
      $.unblockUI();
    }
  });
}

function updateHtmlData(data) {
  $("#SOURCEID_SOURCE").text(data.SOURCEID);
  $("#SJZYSQDWMC").text(data.SJZYSQDWMC);
  $("#SJZYSQDW_SQDWDM").text(data.SJZYSQDW_SQDWDM);
  $("#SQDWLXR").text(data.SQDWLXR);
  $("#SQDWLXDH").text(data.SQDWLXDH);
  $("#SJHQFS").text(data.SJHQFS_CH);
  $("#SJHQFS").val(data.SJHQFS);
  $("#SJZYWZ").text(data.SJZYWZ_CH);
  $("#SJZYWZ").val(data.SJZYWZ);
  $("#SJZYCCFZX").text(data.SJZYCCFZX);

  $("#SJZYGLMC").text(data.SJZYGLMC);
  $("#SJZYGLDW_GAJGJGDM").text(data.SJZYGLDW_GAJGJGDM);
  /** 20210126 新增数据接入的信息**/
  $("#JRFWF").text(data.JRFWF);
  $("#TGJRFS").text(data.TGJRFS);
  $("#JRFWR").text(data.JRFWR);
  $("#JRFWLXDH").text(data.JRFWLXDH);
  $("#SJJRSM").text(data.SJJRSM);
  $("#GSMS").text(data.GSMS);
  $("#YYYXTGLDW").text(data.YYYXTGLDW);
  $("#SJZYGXZQ").text(data.SJZYGXZQ);
  // 20210510  补充数据来源规模探查信息
  $("#avgStore").text(data.avgStore);
  $("#dataStoreScale").text(data.dataStoreScale);
  $("#dataNumberScale").text(data.dataNumberScale);
  $("#updateType").text(data.updateType);
  $("#updateCycle").text(data.updateCycle);
  $("#avgRecord").text(data.avgRecord);
}


