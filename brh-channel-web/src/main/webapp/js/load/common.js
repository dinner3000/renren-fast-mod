//<!-- tab js -->
var aTabLi = document.querySelectorAll('#rrapp .content-title ul li');
var aBanner = document.querySelectorAll('#rrapp .content-wrap ul li');
var length = aTabLi.length;
var bindex = 0;
for (var i=0;i<length;i++) {
    aTabLi[i].i = i;
    aTabLi[i].onclick = function(e){
        aTabLi[bindex].className = '';
        aBanner[bindex].className = '';
        bindex = this.i;
        this.className = 'on';
        aBanner[bindex].className = 'on';

        removeMessage($errorInput);

        if($(this).attr("id") == 'commentList'){
            if (vm.loadCheckOrd.incomNo) {
                var page = $("#splbGrid").jqGrid('getGridParam','page');
                $("#splbGrid").jqGrid('setGridParam',{
                    postData:{'incomNo': vm.loadCheckOrd.incomNo},
                    page:page
                }).trigger("reloadGrid");
            }

            $("#splbGrid").resize();
        }
        //判断需要加载的表格  通过$(this).attr("id")
        forOnloadTable( $(this).attr("id") );
    }
};

// 详情页面每次进来后都需要初始化
function initDetail() {
    aTabLi[bindex].className = '';
    aBanner[bindex].className = '';

    bindex = 0;
    aTabLi[bindex].className = 'on';
    aBanner[bindex].className = 'on';

    //清空审批列表内容
    $("#splbGrid").jqGrid("clearGridData");
    $("#splbGridCollateral").jqGrid("clearGridData");
    $("#splbGridIncomord").jqGrid("clearGridData");
};

function forOnloadTable(id){
	if("collateralList" == id){
		if (vm.customerInfo.ciNo) {
            var page = $("#splbGridCollateral").jqGrid('getGridParam','page');
            $("#splbGridCollateral").jqGrid('setGridParam',{
            	postData:{
            		'ciNo' : vm.customerInfo.ciNo
            	},
                page:page
            }).trigger("reloadGrid");
        }

        $("#splbGridCollateral").resize();
	}else if("incomordRecord" == id){
		if (vm.customerInfo.ciNo) {
            var page = $("#splbGridIncomord").jqGrid('getGridParam','page');
            $("#splbGridIncomord").jqGrid('setGridParam',{
//                postData:{'incomNo': vm.loadCheckOrd.incomNo},
            	postData:{
            		'ciNo' : vm.customerInfo.ciNo
            	},
                page:page
            }).trigger("reloadGrid");
        }

        $("#splbGridIncomord").resize();
		
	}else{
		return false;
	}
}
//审批列表添加点击事件
function getCommentList (index) {
    $("#splbGrid").jqGrid({
        url: '../sys/approval/list',
        datatype: "json",
        colModel: [
            {label: '办理环节', name: 'curStep', width: 145, formatter: function(value, options, row){
                return curStepHtml(value);
            }},
            {label: '节点', name: 'curNodeNm', width: 145},
            {label: '接收时间', name: 'arriveTm', width: 145},
            {label: '完成时间', name: 'completeTm', width: 145},
            {label: '部门', name: 'dept', width: 160},
            {label: '办理人', name: 'operator', width: 130},
            {label: '批注', name: 'commentStr', width: 170}
        ],
        viewrecords: true,
        height: 280,
        rowNum: 500,
        rowList: [30,50,100,500],
        rownumbers: true,
        rownumWidth: 50,
        autowidth: true,
        //multiselect: true,
        shrinkToFit: false,
        autoScroll: true,
        pager: "#splbGridPager",
        jsonReader: {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order"
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#splbGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "auto"});
        }
    });

    $('#preview').on('click', function (e) {
    	var url = "cust/attachfiletreeview.html";
//    	var flag = "previewFlag";
    	var flag = $("#preview").attr("name");
        prepareData(url,flag,'资料预览');
    });
    $('#upload').on('click', function (e) {
    	var url = "cust/attachfiletree.html";
    	var flag = $("#upload").attr("name");
    	prepareData(url,flag,'资料上传');
    });
    
    function prepareData(url,flag, title){
//    	paymentFlag  货款审核跳转 资料上传标志
//    	incomFlag    进件管理跳转 资料上传标志     
//    	applyFlag    申请录入 跳转 资料预览
//    	previewFlag  一次进件 二次进件  跳转  资料预览  
    	var incomNo = vm.loadCheckOrd.incomNo;
    	var ciNo = vm.loadCheckOrd.customerInfo.ciNo;
    	if(!incomNo) {
    	    return false;
        }

        dialogOpen({
            title: title,
            url: url +  '?incomNo=' + incomNo + '&ciNo=' + ciNo + "&flag=" + flag + '&_' + $.now(),
            width: '800px',
            height: '500px',
            scroll: true,
            maxmin: true,
            defaultMax : true,
            btn: ['取消'],
            success: function(iframeId,length){
                vm.length = length;
            },
            yes: function (iframeId, index) {
                parent.layer.close(index);
            }
        });
    }
};

//进件记录添加点击事件 加载进件记录列表
function getIncomordRecord(index){   //进入页面后先加载全部的数据  选择具体详情后 再加载具体客户的记录
	$("#splbGridIncomord").jqGrid({
        url: '../load/checkord/list',
        datatype: "json",
        colModel: [
            { label: '进件编号', name: 'incomNo', index: 'INCOM_NO', width: 160, key: true,
            	formatter : function(value, options, row) {
					return incomNoDetailHtmlHref(value, row);
				}
            },
            { label: '合同编号', name: 'lendNo', index: 'LEND_NO', width: 200 },
            { label: '借款人', name: 'ciNm', index:'CI_NM',width: 80 },
            { label: '产品名称', name: 'prodNm', index: 'PROD_NM', width: 80},
            { label: '贷款期限', name: 'loadTm1', index: 'LOAD_TM1', width: 80 ,
				formatter : function(value, options, row) {
					return operateNodeTm(row.loadTm1,row.loadCycle1);
				}
			},
            { label: '货款利率', name: 'loadRate', index: 'LOAD_RATE', width: 80, formatter: function(value, options, row){
                if(!value){
                    return "";
                }
                return value + "%";
			}},
            { label: '还款方式', name: 'repayWay', index: 'REPAY_WAY', width: 140,
				formatter : function(value, options, row) {
					return repayWayHtml(value);
				}
			},
            { label: '贷款金额（万元）', name: 'loadAmt', index: 'LOAD_AMT', width: 130, formatter: function(value, options, row){
                if(!value){
                    return "";
                }
                return value/10000;
				}
			}, 	
            { label: '状态', name: 'status', index: 'STATUS', width: 80, formatter: function(value, options, row){
                return statusHtml(value);
            }}
        ],
        viewrecords: true,
        height: 280,
        rowNum: 500,
        rowList: [30,50,100,500],
        rownumbers: true,
        rownumWidth: 50,
        autowidth: true,
        //multiselect: true,
        shrinkToFit: false,
        autoScroll: true,
        pager: "#splbGridPagerIncomord",
        jsonReader: {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order"
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
//            $("#splbGridIncomord").closest(".ui-jqgrid-bdiv").css({"overflow-x": "auto"});
        }
    });
}

//押品清单添加点击事件 加载押品清单列表
function getCollateralList(index){
	$("#splbGridCollateral").jqGrid({
        url: '../cust/attachfileinfo/pledgeDetailList',
        datatype: "json",
        colModel: [
            { label: '文件序列号', name: 'idNo', index: 'ID_NO', hidden : true, width: 170 },
            { label: '进件编号', name: 'incomNo', index: 'INCOM_NO', width: 160, key: true
//            	,formatter : function(value, options, row) {
//					return incomNoDetailHtmlHref(value, row);
//				}
            },
            { label: '押品资料目录', name: 'pledgenFolder', index:'pledgenFolder',width: 180 },
            { label: '资料名称', name: 'fileFullNm', index: 'fileFullNm', width: 280
            	, formatter: function(value, options, row){
                return downloadFiles(row.incomNo, row, row.idNo);
            	}
            },
            { label: '上传时间', name: 'upTm',index:'upTm', width: 180 },
        ],
        viewrecords: true,
        height: 280,
        rowNum: 500,
        rowList: [30,50,100,500],
        rownumbers: true,
        rownumWidth: 50,
        autowidth: true,
        //multiselect: true,
        shrinkToFit: false,
        autoScroll: true,
        pager: "#splbGridPagerCollateral",
        jsonReader: {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order"
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#splbGridCollateral").closest(".ui-jqgrid-bdiv").css({"overflow-x": "auto"});
        }
    });
}

//跳转进件信息详情页面
function incomNoDetailHtmlHref(value, row){
    if(row.curStep != '0') {
    	return '<a href="#" onclick="vm.incomordDetail(\''+value+'\')">'+value+'</a>';
    } else {
        return "";
    }
}

//还款方式
function repayWayHtml(value){
    if(value == '0') {
        return '等额本金';
    } else if (value == '1'){
        return "等额本息";
    } else if (value == '2'){
        return "一次性还本付息";
    } else if (value == '3'){
        return "月度结息，到期还本";
    }else if (value == '4'){
        return "季度结息，到期还本";
    }else if (value == '5'){
        return "其他";
    }else {
        return "";
    }
}

function operateNodeTm (loadTm,loadCycle1) {
	if(!loadTm){
	  return '';
	}
	var tmStr='';
  	if(loadCycle1=='D'){
  		tmStr='天';
  	}else if(loadCycle1=='M'){
  		tmStr='月';
  	}else if(loadCycle1=='Y'){
  	    tmStr='年';
  	}
 	return loadTm+tmStr; 
}

function downloadFiles(incomNo, row, idNo) {
	var fileFullNm = row.fileFullNm;

	var downloadFileHtml = '<a href="#" onclick="vm.downloadFile(\''+idNo+'\')">' + fileFullNm + '</a>';
    return downloadFileHtml ;
};

//时间格式转换
function getDateStr (d) {
    if(d){
        return d.toString().substr(0,4) + '年' + d.substr(4,2) + '月' + d.substr(6,2) + '日';
    }
};

// 预览审批单公共方法
function previewApproval(incomNo){
    if(!incomNo){
        alert('无法预览审批单信息！');
        return false;
    }
    dialogOpen({
        title : '预览审批单',
        url : 'load/approvalForm.html?incomNo=' + incomNo + '&disabled=true&_' + $.now(),
        width : '900px',
        height : '500px',
        scroll : true,
        maxmin: true,
        btn: ['下载审批单','取消'],
        success : function(iframeId, length) {
            vm.length = length;
            parent.frames[vm.length - 1].vm.getInfo(null, incomNo);
        },
        yes : function(iframeId, index) {
            //parent.layer.close(index);
            var url = "load/approvalinfo/genApproval/?incomNo="+incomNo;
            window.open(url);
        }
    });
}