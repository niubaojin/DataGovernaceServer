$(document).ready(function() {
    createTree();

});
var treeObj;
var checkTreeObj;

function createTree() {
    // 树状图初始化
    var setting = ztreeSetting();
    treeObj = $.fn.zTree.init($("#dszTree"), setting);
    var checkSetting = checkztreeSetting();
    checkTreeObj = $.fn.zTree.init($("#checkdszTree"), checkSetting);

}

function ztreeSetting() {
    var setting = {
        async: {
            enable: true, //是否异步加载
            url: "./data/tree.json",
            type: "get",
            otherParam: {}
        },
        view: {
            expandSpeed: "",
            selectedMulti: false
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
            onAsyncSuccess: function(event, treeId, treeNode, msg) {}
        }
    };
    return setting;
}


function checkztreeSetting() {
    var setting = {
        async: {
            enable: true, //是否异步加载
            url: "./data/tree.json",
            type: "get",
            otherParam: {}
        },
        view: {
            expandSpeed: "",
            selectedMulti: false
        },
        check: {
            enable: true,
            checkStyle: "checkbox",
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
            onAsyncSuccess: function(event, treeId, treeNode, msg) {}
        }
    };
    return setting;
}