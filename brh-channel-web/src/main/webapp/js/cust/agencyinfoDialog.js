var p_cooTyp = T.p('cooTyp');
var p_agencyId = T.p('agencyId');
$(function() {
	$("#jqGrid").jqGrid({
		url : '../cust/agencyinfo/list',
		datatype : "json",
		postData:{
			'status': "0",
            'cooTyp': p_cooTyp,
            'agencyId': p_agencyId,
		},
		colModel : [ {
			label : '机构编号',
			name : 'agencyId',
			index : 'AGENCY_ID',
			width : 120,
			hidden : true,
			key : true
		}, {
			label : '名称',
			name : 'agencyNm',
			index : 'AGENCY_NM',
			width : 150
//			,
//			formatter : function(value, options, row) {
//				return operateDetail(row.agencyId, row.agencyNm);
//			}
		}, {
			label : '合作角色',
			name : 'cooTyp',
			index : 'COO_TYP',
			width : 120,
			formatter : function(value, options, row) {
				var val = row.cooTyp;
				if (val == '0') {
					return '资金合作机构';
				} else if (val == '1') {
					return "资产合作机构";
				} else if (val == '2') {
					return "担保机构";
				} else {
					return "";
				}
			}
		},
		 {
		 label : '地址',
		 name : 'address',
		 index : 'ADDRESS',
		 width : 150
		 }, {
		 label : '联系人',
		 name : 'cooPer',
		 index : 'COO_PER',
		 width : 80
		 }, {
		 label : '联系手机',
		 name : 'cooTele',
		 index : 'COO_TELE',
		 width : 130
		 },
//		 {
//		 label : '联系电话',
//		 name : 'cooPhone',
//		 index : 'COO_PHONE',
//		 width : 120
//		 },
//		 {
//		 label : '邮编',
//		 name : 'postCode',
//		 index : 'POST_CODE',
//		 width : 100
//		 }, {
//		 label : '传真',
//		 name : 'fax',
//		 index : 'FAX',
//		 width : 120
//		 },
//		 {
//		 label : '邮件',
//		 name : 'email',
//		 index : 'EMAIL',
//		 width : 150
//		 }, {
//		 label : '网址',
//		 name : 'webUrl',
//		 index : 'WEB_URL',
//		 width : 150
//		 },
		{
			label : '状态',
			name : 'status',
			index : 'STATUS',
			width : 100,
			formatter : function(value, options, row) {
				var val = row.status;
				if (val == '0') {
					return '有效';
				} else if (val == '1') {
					return "终止";
				} else {
					return "";
				}
			}
		}

		// {
		// label : '操作员',
		// name : 'operId',
		// index : 'OPER_ID',
		// width : 80
		// }, {
		// label : '创建时间',
		// name : 'createTm',
		// index : 'CREATE_TM',
		// width : 120
		// }, {
		// label : '时间戳',
		// name : 'timeStamp',
		// index : 'TIME_STAMP',
		// width : 120
		// }
		],
		viewrecords : true,
		height : 385,
		rowNum : 10,
		rowList : [ 10, 30, 50 ],
		rownumbers : true,
		rownumWidth : 25,
		autowidth : true,
		multiselect : true,
        beforeSelectRow: beforeSelectRow,
		shrinkToFit : false,
		autoScroll : true,
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
				"overflow-x" : "auto"
			});

		}

	});
});

var vm = new Vue({
	el: '#rrapp',
	data: {
		q: {
			agencyNm: null,
			cooTyp: p_cooTyp,
			status: 0
		},
		//showList : 'true',
		showList: true,
		showEdit: false,
		showDetail: false,
		title: null,
		agencyInfo: {}
	},
	methods: {
		reload: function () {
			vm.reload();
		},
		query: function () {
			vm.reload();
		},
		acceptClick: function (event) {
			var agencyId = getDialogSelectedRow("#jqGrid");

			if (agencyId == null) {
				return null;
			}
			return agencyId;
		},
		reload: function (event) {
			vm.showList = true;
			vm.showEdit = false;
			vm.showDetail = false;
			var page = $("#jqGrid").jqGrid('getGridParam', 'page');
			$("#jqGrid").jqGrid('setGridParam', {
				postData: {
					'agencyId': vm.q.agencyId,
					'agencyNm': vm.q.agencyNm,
					'cooTyp': vm.q.cooTyp,
					'status': vm.q.status
				},
				page: page
			}).trigger("reloadGrid");
		}
	}
});