document.write("<script type='text/javascript' src='bootstrap/js/bootstrap-suggest.js\'></script>");//引入suggest方法

// 提示框中提示两列数据
function twoSuggest(domID,url) {
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
      console.log('onDataRequestSuccess', result);
    }).on('onSetSelectValue', function(e, keyword, data){
      // $("#" + domID).blur();
      console.log('onSetSelectValue: ', keyword, data);
    }).on('onUnsetSelectValue', function(){
      console.log("onUnsetSelectValue");
    });
}

// 搜索查询插件绑定事件
function suggest(domID,url) {
    console.log("进入搜索框");
    $("#" + domID).bsSuggest({
        url:url,
        indexId : 0, // data.value 的第几个数据，作为input输入框的显示的内容
        indexKey: 0,
        getDataMethod:"url", //数据从ajax中来
        ignorecase: true,
        clearable: true, //可清除已输入内容
        showHeader: false,
        showBtn: false,     //不显示下拉按钮
        delayUntilKeyup:true,  //为url时不起作用
        // jsonp: 'cb',                      //如果从url获取数据，并且需要跨域，则该参数必须设置
        listStyle: {
            'padding-top': 0, 'max-height': '300px', 'max-width': '220px',"min-width":"220px",
            'overflow': 'auto', 'width': '220px',
            'transition': '0.3s', '-webkit-transition': '0.3s', '-moz-transition': '0.3s', '-o-transition': '0.3s',

        },                              //列表的样式控制
        autoMinWidth: true,
        listAlign: 'left' ,            //提示列表对齐位置，left/right/auto
        processData: function (json){    // url 获取数据时，对数据的处理，作为fnGetData 的回调函数
            var index, len, data = {value: []};
            if (!json  || json.length === 0){
                return false;
            }
            len = json.length;
            for (index = 0; index < len; index++){
                data.value.push({
                    word: json[index]
                });
            }
            data.defaults = 'synway';
            //字符串转化为 js 对象
            return data;
        }
    }).on('onDataRequestSuccess', function(e, result){
        console.log('onDataRequestSuccess', result);
    }).on('onSetSelectValue', function(e, keyword, data){
        // $("#" + domID).blur();
        console.log('onSetSelectValue: ', keyword, data);
    }).on('onUnsetSelectValue', function(){
        console.log("onUnsetSelectValue");
    });
}
//获取日期函数：如果输入的日期为空，则获取当前日期，否则，将输入的日期转换格式
//示例:20180809;2018-08-09;2018/08/09
function getDay(date,splitStr){
    var newDate;
    date==""?newDate=new Date():newDate=new Date(date);
    var year = newDate.getFullYear();
    var month= (newDate.getMonth() + 1) < 10?"0"+(newDate.getMonth() + 1):newDate.getMonth()+1;
    var day=newDate.getDate()< 10?"0"+newDate.getDate():newDate.getDate();
    return year+splitStr+month+splitStr+day;
}

//日期选择器,参数：日期的name属性
function datetimepicker(name){
    $("input[name='"+name+"']").datetimepicker({
        language: 'zh-CN',//显示中文
        format: 'yyyy-mm-dd',//显示格式
        minView: "month",//设置只显示到月份
        initialDate: new Date(),
        autoclose: true,//选中自动关闭
        todayBtn: true,//显示今日按钮
        locale: moment.locale('zh-cn')
    });
}

//判断是否为空
function checkIsNull(value){
    if(value=='undefine' || value==null || value=='NaN' || value==''){
        return true;
    }
    return false;
}

//判断字符串是否为中文(中文为true)
function checkIsChinese(temp){
    var re=/.*[\u4e00-\u9fa5]+.*/;
    if(re.test(temp))return true;
    return false;
}


//从url中获取参数
function getQueryParam(variable){
    var query = window.location.search.substring(1);
    var vars=query.split("&");
    for (var i=0;i<vars.length;i++){
        var pair = vars[i].split("=");
        if(pair[0] == variable){
            if(!checkIsNull(pair[1])){
                return decodeURIComponent(pair[1]);
            }
        }
    }
    return "";
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

function checkIsNumber(temp) {
  var re=new RegExp("^[0-9]*$");
  if(re.test(temp))return true;
  return false;
}

/**
 * 获取本地的ip地址信息
 */
function findIp(callback) {
   var myPeerConnection = window.RTCPeerConnection || window.mozRTCPeerConnection || window.webkitRTCPeerConnection;
   var pc = new myPeerConnection({ iceServers :[]}),
     noop = function () {},
     localIPs = {},
     ipRegex = /([0-9]{1,3}(\.[0-9]{1,3}){3}|[a-f0-9]{1,4}(:[a-f0-9]{1,4}){7})/g,
     key;
   function ipIterate(ip) {
     if( !localIPs[ip]) callback(ip);
     localIPs[ip] = true;
   }
   pc.createDataChannel("");
   pc.createOffer().then(function (sdp) {
     sdp.sdp.split('\n').forEach(function(line) {
       if (line.indexOf('candidate') < 0) return;
       line.match(ipRegex).forEach(ipIterate);
     });
     pc.setLocalDescription(sdp, noop, noop);
   })
   pc.onicecandidate = function(ice) {
     if (!ice || !ice.candidate || !ice.candidate.candidate || !ice.candidate.candidate.match(ipRegex)) return;
     ice.candidate.candidate.match(ipRegex).forEach(ipIterate);
   };
}

function getLocalIp(){
  var localIp = ""
  try{
      findIp(function (ip) {
      console.log('get ip: ', ip);
      localIp = ip;
    })
  }catch (e) {
    console.log(e);
    return "";
  }
  return localIp;
}




Date.prototype.Format = function (fmt) {
  var o = {
    "M+" : this.getMonth() + 1,
    "d+": this.getDate(),
    "h+": this.getHours(),
    "m+": this.getMinutes(),
    "s+": this.getSeconds(),
  };
  if(/(y+)/.test(fmt))
    fmt = fmt.replace(RegExp.$1,(this.getFullYear()+"").substr(4 -RegExp.$1.length));

  for(var k in o){
    var n = o[k];
    if(new RegExp("("+k+")").test(fmt)){
      fmt = fmt.replace(RegExp.$1, RegExp.$1.length === 1?n:("00" +n).substr((""+n).length))
    }
  }
  return fmt;
}