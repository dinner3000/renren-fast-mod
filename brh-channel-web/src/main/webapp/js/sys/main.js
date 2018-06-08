$(function() {
	$("#jqGrid").jqGrid({
		url : '../sys/main/list',
		datatype : "json",
		colModel : [ {
			label : '客户编号',
			name : 'ciNo',
			index:'CI_NO',
			width : 100,
			key : true
		}, {
			label : '进件编号',
			name : 'incomNo',
			index:'INCOM_NO',
			width : 100,
			key : true,
			formatter : function(value, options, row) {
				if (row.curStep != '0') {
					return value;
				} else {
					return "";
				}
			}
		}, {
			label : '客户名称',
			name : 'ciNm',
			index:'CI_NM',
			width : 100
		}, {
			label : '接收时间',
			name : 'createTime',
			index:'CREATE_TIME',
			width : 100
		}, {
			label : '环节',
			name : 'curStep',
			index:'CUR_STEP',
			width : 100,
			formatter : function(value, options, row) {
                return curStepHtml(value);
			}
		}, {
			label : '状态',
			name : 'status',
			index:'STATUS',
			width : 80 , formatter: function(value, options, row){
                return statusHtml(value);}
		}, ],
		viewrecords : true,
		height : 385,
		rowNum : 10,
		rowList : [ 10, 30, 50 ],
		rownumbers : true,
		rownumWidth : 25,
		autowidth : true,
		multiselect : false,
		pager : "#jqGridPager",
		jsonReader : {
			root : "page.list",
			page : "page.currPage",
			total : "page.totalPage",
			records : "page.totalCount"
		},
		prmNames : {
			page : "page",
			rows : "limit",
			order : "order"
		},
		gridComplete : function() {
			// 隐藏grid底部滚动条
			$("#jqGrid").closest(".ui-jqgrid-bdiv").css({
				"overflow-x" : "hidden"
			});
		}
	});
});

var vm = new Vue({
	el : '#rrapp',
	data : {
		q : {
			tmTyp : null
			
		},
	},
	methods : {
		query : function() {
			$("#jqGrid").jqGrid('setGridParam',{page:1});
			vm.q.tmTyp='';	
			vm.reload();
			$('.btn').eq(2).css({'background':'#f0ad4e',
								'border-color':'#eea236'});
			$('.btn').eq(0).css({'background':'#337ab7',
								'border-color':'#2e6da4'});
			$('.btn').eq(1).css({'background':'#337ab7',
								'border-color':'#2e6da4'});
		},
		queryWeek : function() {
			vm.q.tmTyp='week';
			vm.reload();
			$('.btn').eq(0).css({'background':'#f0ad4e',
								'border-color':'#eea236'});
			$('.btn').eq(1).css({'background':'#337ab7',
								'border-color':'#2e6da4'});
			$('.btn').eq(2).css({'background':'#337ab7',
								'border-color':'#2e6da4'});
		},
		queryMonth : function() {
			vm.q.tmTyp='month';
			vm.reload();
			$('.btn').eq(1).css({'background':'#f0ad4e',
								'border-color':'#eea236'});
			$('.btn').eq(0).css({'background':'#337ab7',
								'border-color':'#2e6da4'});
			$('.btn').eq(2).css({'background':'#337ab7',
								'border-color':'#2e6da4'});
		},
		reload : function(event) {
			var page = $("#jqGrid").jqGrid('getGridParam', 'page');
			$("#jqGrid").jqGrid('setGridParam', {
				postData : {
					'tmTyp' : vm.q.tmTyp
				},
				page : page
			}).trigger("reloadGrid");
		}
	}
});