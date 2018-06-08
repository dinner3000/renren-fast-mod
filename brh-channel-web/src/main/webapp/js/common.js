var $errorInput;

//jqGrid的配置信息
$.jgrid.defaults.width = 1000;
$.jgrid.defaults.responsive = true;
$.jgrid.defaults.styleUI = 'Bootstrap';

//工具集合Tools
window.T = {};

// 获取请求参数
// 使用示例
// location.href = http://localhost:8080/index.html?id=123
// T.p('id') --> 123;
var url = function(name) {
	var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if(r!=null)return  unescape(r[2]); return null;
};
T.p = url;

//全局配置
$.ajaxSetup({
	dataType: "json",
	contentType: "application/json",
	cache: false,
    complete:function(XMLHttpRequest,textStatus) {
        if (textStatus == "parsererror") {
            console.log("登陆超时！请重新登陆！");
        } else if (textStatus == "error") {
            console.log("请求超时！请稍后再试！");
        }
    }
});

//重写alert
window.alert = function(msg, callback){
	parent.layer.alert(msg, function(index){
		parent.layer.close(index);
		if(typeof(callback) === "function"){
			callback("ok");
		}
	});
}

//重写confirm式样框
window.confirmOld = window.confirm;
//重写confirm式样框
window.confirm = function(msg, callback){
	parent.layer.confirm(msg, {btn: ['确定','取消']},
	function(){//确定事件
		if(typeof(callback) === "function"){
			callback("ok");
		}
	});
}

//选择一条记录
function getSelectedRow() {
    var grid = $("#jqGrid");
    var rowKey = grid.getGridParam("selrow");
    if(!rowKey){
    	alert("请选择一条记录");
    	return ;
    }
    
    var selectedIDs = grid.getGridParam("selarrrow");
    if(selectedIDs.length > 1){
    	alert("只能选择一条记录");
    	return ;
    }
    
    return selectedIDs[0];
}
checkedRow = function (id) {
    var isOK = true;
    if (id == undefined || id == "" || id == 'null' || id == 'undefined') {
        isOK = false;
        dialogMsg('您没有选中任何数据项！');
    } else if (id.length > 1) {
        isOK = false;
        dialogMsg('您只能选择一条数据项！');
    }
    return isOK;
}
// 只能选择一条记录
function beforeSelectRow() {
    $("#jqGrid").jqGrid('resetSelection');
    return(true);
};

//选择一条记录,传grid id
function getDialogSelectedRow(gridId) {
    var grid = $(gridId);
    var rowKey = grid.getGridParam("selrow");
    if(!rowKey){
        alert("请选择一条记录");
        return ;
    }

    var selectedIDs = grid.getGridParam("selarrrow");
    if(selectedIDs.length > 1){
        alert("只能选择一条记录");
        return ;
    }

    return selectedIDs[0];
};

//选择多条记录
function getSelectedRows() {
    var grid = $("#jqGrid");
    var rowKey = grid.getGridParam("selrow");
    if(!rowKey){
    	alert("请选择一条记录");
    	return ;
    }
    
    return grid.getGridParam("selarrrow");
}

dialogOpen = function(opt){
    var defaults = {
        id : 'layerForm',
        title : '',
        width: '',
        height: '',
        url : null,
        scroll : false,
        maxmin : false,
        defaultMax : false,
        resize: true,
        data : {},
        btn: ['确定', '取消'],
        success: function(){},
        yes: function(){}
    }
    var option = $.extend({}, defaults, opt), content = null;
    if(option.scroll){
        content = [option.url]
    }else{
        content = [option.url, 'no']
    }
    var index = parent.layer.open({
        type : 2,
        id : option.id,
        title : option.title,
        closeBtn : 1,
        anim: -1,
        isOutAnim: false,
        shadeClose : false,
        shade : 0.3,
        maxmin:option.maxmin,
        resize: option.resize,
        area : [option.width, option.height],
        content : content,
        btn: option.btn,
        success: function(){
            var length = parent.frames.length;
            option.success(option.id, length);
        },
        yes: function(index, layero){
            option.yes(option.id, index);
        }
    });

    if(option.defaultMax == true) {
        parent.layer.full(index);
    }
}

$.currentIframe = function () {
    return $(window.parent.document).contents().find('#main')[0].contentWindow;;
}

function incomNoHtml(value, row){
    if(row.curStep != '0') {
        return value;
    } else {
        return "";
    }
}

// 当前环节 0:贷款审核 1:一次进件 2:二次进件
function curStepHtml(value){
    if (value == 0) {
        return '<span class="label label-success">贷款审核</span>';
    }
    if (value == 1) {
        return '<span class="label label-primary">一次进件</span>';
    }
    if (value == 2) {
        return '<span class="label label-warning">二次进件</span>';
    }
}

// 状态 00:放款   01:录入  02:待审核  03 审核通过  04:审核不通过  05:放弃 06:拒绝
function statusHtml(value){
    if(value == '00') {
        return '放款';
    } else if (value == '01'){
        return "录入";
    } else if (value == '02'){
        return "待审核";
    } else if (value == '03'){
        return "审核通过";
    }else if (value == '04'){
        return "审核不通过";
    }else if (value == '05'){
        return "放弃";
    }else if (value == '06'){
        return "拒绝";
    }else {
        return "";
    }
}

// 保费缴费状态 00:未缴费 01:已缴费
function payStsHtml(value){
    if(value == '00') {
        return '未缴费';
    } else if (value == '01'){
        return "已缴费";
    } else {
        return "";
    }
}

// 出单状态 00:未出单 01:已出单 02:出单处理中 03:出单失败
function issueStsHtml(value){
    if(value == '00') {
        return '未出单';
    } else if (value == '01'){
        return "已出单";
    } else if (value == '02'){
        return "出单处理中";
    } else if (value == '03'){
        return "出单失败";
    } else {
        return "";
    }
}

//担保方式
function guaranteeTypHtml(value){
    if(value == '01') {
        return '质押担保';
    } else if (value == '02'){
        return "抵押担保";
    } else if (value == '03'){
        return "个人担保";
    } else if (value == '04'){
        return "法人担保";
    }else if (value == '05'){
        return "担保公司担保";
    }else if (value == '06'){
        return "分级产品";
    }else if (value == '07'){
        return "其他";
    }else {
        return "";
    }
}

//结构类型
function structTypHtml(value){
    if(value == '01') {
        return '直接融资';
    } else if (value == '02'){
        return "信托计划";
    } else if (value == '03'){
        return "债权转让";
    } else if (value == '04'){
        return "定向资管";
    }else if (value == '05'){
        return "委托贷款";
    }else if (value == '06'){
        return "其他";
    }else {
        return "";
    }
}

//证件类型
function idTypHtml(value){
	
    if(value == '00') {
        return '身份证';
    } else if (value == '01'){
        return "护照";
    } else if (value == '02'){
        return "军官证";
    } else if (value == '03'){
        return "港澳居民往来内地通行证";
    }else if (value == '04'){
        return "台胞证";
    }else if (value == '05'){
        return "其他";
    }else {
        return "";
    }
}

Vue.http.interceptors.push(function (request, next){
    var token = localStorage.getItem('token');
    request.headers.set('__token__', token);

    next(function (response){
        var stoken = response.headers.get('__token__');
        localStorage.setItem('token',stoken);
        return response
    })
})

// 获取时间公共方法
function getCurrentDate(month) {
    var today = new Date();
    if(!month) {
        month = 0;
    }
    var year=today.getFullYear();
    var mon = today.getMonth() + 1 + month;
    if(mon<0){
    	year=year-1;
    	mon=12+mon;
    }
    if(mon<10){
        mon = '0'+ mon;  //补齐0
    }

    var dd = today.getDate();
    if(dd<10){
        dd = '0'+ dd;  //补齐0
    }

    return year + "年" + mon + "月" + dd + "日";
}
function getCurrentTime(flag){ 
    var now = new Date();
   
    var year = now.getFullYear();       //年
    var month = now.getMonth() + 1;     //月
    var day = now.getDate();            //日
   
    var hh = now.getHours();            //时
    var mm = now.getMinutes();          //分
    var ss = now.getSeconds();
    var clock = year + "/";
   
    if(month < 10)
        clock += "0";
   
    clock += month + "/";
   
    if(day < 10)
        clock += "0";
       
    clock += day + " ";
   if(flag==0)
	   clock +="00:00:00";
   else
	   clock +="23:59:59";
//    if(hh < 10)
//        clock += "0";
//       
//    clock += hh + ":";
//    if (mm < 10) clock += '0'; 
//    clock += mm+ ":"; 
//    if (ss < 10) clock += '0'; 
//    clock += ss; 
    return(clock); 
} 
function getCurrentTime1(flag){ 
    var now = new Date();
   
    var year = now.getFullYear();       //年
    var month = now.getMonth() + 1;     //月
    var day = now.getDate();            //日
   
    var hh = now.getHours();            //时
    var mm = now.getMinutes();          //分
    var ss = now.getSeconds();
    var clock = year + "/";
   
    if(month < 10)
        clock += "0";
   
    clock += month + "/";
   
    if(day < 10)
        clock += "0";
       
    clock += day + " ";
   if(flag==0)
	   clock +="00:00:00";
   else
	   clock +="23:59:59";
//    if(hh < 10)
//        clock += "0";
//       
//    clock += hh + ":";
//    if (mm < 10) clock += '0'; 
//    clock += mm+ ":"; 
//    if (ss < 10) clock += '0'; 
//    clock += ss; 
    return(clock); 
} 
// 时间控件公共方法
function bindLaydate(elemId, vmElem, vmElemId) {
    laydate.render({
        elem: elemId
        , trigger: 'click'
        , format: 'yyyy年MM月dd日'
        , done: function (value, date, endDate) {
            vm.$set(vmElem, vmElemId, value.replace("年", "").replace("月", "").replace("日", ""));
            removeMessage($errorInput);
        }
    });
}

// 时间控件公共方法  给定初始值
function bindLaydateWithValue(elemId, vmElem, vmElemId, value) {
    laydate.render({
        elem: elemId
        , trigger: 'click'
        , format: 'yyyy/MM/dd HH:mm:ss'
        , value: value
        , done: function (val, date, endDate) {
            vm.$set(vmElem, vmElemId, val.replace("年", "").replace("月", "").replace("日", ""));
            removeMessage($errorInput);
        }
    });
}

//递归转译ajax返回值中存在非法字符的情况   例如英文的()
function filterHtmlResult(obj){
	if(null == obj || "" == obj){
		return false;
	}
	for ( var p in obj) {

		if (typeof(obj[p]) == 'object') {
			if(obj[p]!= null){
				filterHtmlResult(obj[p]);
			}else{
				continue;
			}
			
		}else if (typeof(obj[p]) == 'string') {

			if(obj[p]!= 'null'){
				obj[p]=filter(obj[p]);
			}
			
		}else{
			continue;
		}
	}
	
	return obj;
	
}

function filterHtml(obj){
	for ( var p in obj) {
		if (typeof(obj[p]) == 'string') {
			if(obj[p]!= 'null'){
				obj[p]=filter(obj[p]);
			}
		}
	}
	return obj;
}
function filter(html) {
	return html.replace(html ? /&(?!#?\w+;)/g : /&/g, '&amp;').replace(
			/&lt;/g, "<").replace(/&gt;/g, ">").replace(/&#40;/g, "(")
			.replace(/&#41;/g, ")").replace(/&quot;/g, "\"").replace(
					/&#39;/g, "\'");
}

//bootstrap-treegrid
checkedArray = function (id) {
    var isOK = true;
    if (id == undefined || id == "" || id == 'null' || id == 'undefined') {
        isOK = false;
        dialogMsg('您没有选中任何数据项！');
    }
    return isOK;
}
//bootstrap-treegrid
checkedRow = function (id) {
    var isOK = true;
    if (id == undefined || id == "" || id == 'null' || id == 'undefined') {
        isOK = false;
        dialogMsg('您没有选中任何数据项！');
    } else if (id.length > 1) {
        isOK = false;
        dialogMsg('您只能选择一条数据项！');
    }
    return isOK;
}
function requestParam(paras) {
    var url = location.search;
    var paraString = url.substring(url.indexOf("?") + 1, url.length).split("&");
    var paraObj = {};
    for (i = 0; j = paraString[i]; i++) {
        paraObj[j.substring(0, j.indexOf("=")).toLowerCase()] = j.substring(j.indexOf("=") + 1, j.length);
    }
    var returnValue = paraObj[paras.toLowerCase()];
    if (typeof(returnValue) == "undefined") {
        return "";
    } else {
        return returnValue;
    }
}
function bindDate() {
    // 绑定时间控件
    var threeMonth = getCurrentTime(0);
    vm.q.startDay = threeMonth.replace("年", "").replace("月", "").replace("日", "");
    bindLaydateWithValue("#startDate", vm.q, 'startDay', threeMonth);

    var now = getCurrentTime(1);
    vm.q.endDay = now.replace("年", "").replace("月", "").replace("日", "");

    bindLaydateWithValue("#endDate", vm.q, 'endDay', now);
}
function bindDateTmp(startDay,endDay) {
    // 绑定时间控件
//    var threeMonth = getCurrentTime(0);
	var tempStart = startDay.replace("%20", " ");
    vm.q.startDay = tempStart.replace("%20", " ").replace("月", "").replace("日", "");
    bindLaydateWithValue("#startDate", vm.q, 'startDay', tempStart);
    var tempEnd = endDay.replace("%20", " ");
//    var now = getCurrentTime(1);
    vm.q.endDay = tempEnd.replace("%20", " ").replace("月", "").replace("日", "");

    bindLaydateWithValue("#endDate", vm.q, 'endDay', tempEnd);
}