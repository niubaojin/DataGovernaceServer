$(document).ready(function() {
    initDatePickerSelect();
})

/**
 * 初始化时间选择器
 */
function initDatePickerSelect() {
    $("#startTimeText").datetimepicker({
        language: 'zh-CN',
        format: 'yyyy-mm-dd',
        initialDate: new Date(),
        autoclose: true,
        todayBtn: true,
        pickerPosition: 'bottom-right',
        todayHighlight: true,
        startView: 'month',
        minView: 2
    });
    $("#endTimeText").datetimepicker({
        language: 'zh-CN',
        format: 'yyyy-mm-dd',
        initialDate: new Date(),
        autoclose: true,
        todayBtn: true,
        pickerPosition: 'bottom-right',
        todayHighlight: true,
        startView: 'month',
        minView: 2
    });

    $("#timeText").datetimepicker({
        language: 'zh-CN',
        format: 'yyyy-mm-dd hh:ii:ss',
        initialDate: new Date(),
        autoclose: true,
        todayBtn: true,
        pickerPosition: 'bottom-right',
        todayHighlight: true,
        startView: 'month',
        minView: 2
    });
}