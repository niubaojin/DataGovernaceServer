$(document).ready(function() {
    windowResizeEvent();
    initTable();

});

function initTable() {
    var dataLen = 0;
    var obj = $("#myTableId").bootstrapTable("destroy").bootstrapTable({
        url: './data/table.json',
        method: 'get',
        striped: true,
        cache: false,
        pagination: true,
        queryParams: function(params) {
            var temp = {
                pageNum: (params.offset / params.limit) + 1, //页码
                pageSize: params.limit, //页面大小
                sortName: params.sort, //排序字段 createTime
                sortOrder: params.order, //排序方式
            };
            return temp;
        },
        sortable: true,
        sortOrder: "desc",
        silentSort: true, //默认true，设置为 false 将在点击分页按钮时，自动记住排序项。仅在 sidePagination设置为 server时生效。
        sortName: 'createTime',
        sidePagination: "client",
        paginationPreText: '<',
        paginationNextText: '>',
        pageNumber: 1,
        pageSize: 20,
        pageList: [10, 20, 30, 50],
        paginationShowPageGo: true,
        showJumpto: true,
        paginationLoop: false,
        search: false,
        strictSearch: false,
        showColumns: false,
        showRefresh: false,
        minimumCountColumns: 1,
        clickToSelect: true,
        height: caculateTableHeight(),
        uniqueId: "id",
        showToggle: false,
        cardView: false,
        detailView: false,
        undefinedText: '-', //当数据为 undefined 时显示的字符,默认'-'。
        paginationDetailHAlign: 'left',
        smartDisplay: false,
        columns: [{
                checkbox: true,
            }, {
                title: '序号',
                align: 'center',
                formatter: function(value, row, index) {
                    return index + 1;
                }
            }, {
                field: 'date',
                title: '日期',
                align: 'center',

            }, {
                field: 'name',
                title: '姓名',
                align: 'center',

            }, {
                field: 'province',
                title: '省份',
                align: 'center',
                sortable: true,

            }, {
                field: 'city',
                title: '市区',
                align: 'center',
                sortable: true,

            }, {
                field: 'address',
                title: '地址',
                align: 'center',
                sortable: true,
                cellStyle: formatTableUnit,
                formatter: paramsMatter

            }, {
                field: 'zip',
                title: '邮编',
                align: 'center',
                sortable: true,

            },
            {
                field: 'detail',
                title: '详情',
                align: 'center',
                events: "detailEvents",
                formatter: detailFormatter
            }, {
                field: 'operator',
                title: '操作',
                align: 'center',
                events: "operateEvents",
                formatter: operateFormatter
            }
        ],
        onLoadSuccess: function(data) {
            dataLen = data.length;
            addTotalAndtitle('#myTableDiv', dataLen);
        },
        /**
         * 若分页为client 的时候需要，server 不需要
         * 
         */
        onPageChange: function() {
            addTotalAndtitle('#myTableDiv', dataLen);
        }
    });
    return obj;

}

/**
 * 分页样式修改及加总条数
 */
function addTotalAndtitle(id, num) {
    $(id + " .page-list ul li").append('<span>条/页</span>');
    $(id + " .page-list button span.page-size").append('<span>条/页</span>');
    $(id + ' .fixed-table-pagination').prepend('<div class="total-num">共 ' + num + ' 条</div>');
    $('[data-toggle="tooltip"]').tooltip();
}
/**
 * 计算获得表的高度
 * @returns {number}
 */
function caculateTableHeight() {
    var tableHeight = $(".my-table-div").height();
    return tableHeight;
}
/**
 * 窗口大小改变事件
 */
function windowResizeEvent() {
    $(window).resize(function() {
        var tableHeight = $(".my-table-div").height();
        $('#myTableId').bootstrapTable('resetView', {
            height: tableHeight
        });
    });
}
/**
 * 表格内操作
 * @param value
 * @param row
 * @param index
 * @returns {string}
 */
function operateFormatter(value, row, index) {
    return ['<span type="button"  id="editData"  style="cursor: pointer;"><img src="./img/bianji.png" style="width:18px" /></span>  &nbsp; ' +
        '<span id="deleteData" type="button" style="cursor: pointer;" ><img src="./img/shanchu.svg" style="width:16px" /></span> &nbsp'
    ].join('');

}
/**
 * 表格内跳转详情
 * @param value
 * @param row
 * @param index
 * @returns {string}
 */
function detailFormatter(value, row, index) {
    return '<span type="button"  id="deteleData"  style="cursor: pointer;"><img src="./img/check.png" style="width:18px" /></span> ';
}

/**
 * 文字过长显示省略号，鼠标悬浮显示全部
 * @param value
 * @param row
 * @param index
 * @returns {string}
 */
function paramsMatter(value, row, index) {
    var span = document.createElement('span');
    span.setAttribute('class', 'td-span');
    span.setAttribute('title', value);
    span.setAttribute('data-toggle', "tooltip");
    span.innerHTML = value;
    return span.outerHTML;
}

function formatTableUnit(value, row, index) {
    return {
        css: {
            "max-width": "300px",
            "white-space": 'nowrap',
            "text-overflow": 'ellipsis',
            "overflow": 'hidden'
        }
    }
}