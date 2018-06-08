$(function() {
	$("#jqGrid").jqGrid({
		url : '../prod/prodinfo/list',
		datatype : "json",
		colModel : [ {
			label : '产品序号',
			name : 'prodId',
			index : 'PROD_ID',
			width : 80,
			key : true
		}, {
			label : '产品编号',
			name : 'prodNo',
			index : 'PROD_NO',
			width : 80,
			formatter : function(value, options, row) {
				return operateDetail(row.prodId, row.prodNo);
			}
		}, {
			label : '产品名称',
			name : 'prodNm',
			index : 'PROD_NM',
			width : 80
		}, {
			label : '产品类型',
			name : 'prodTyp',
			index : 'PROD_TYP',
			width : 80
		},
		// { label: '产品利率', name: 'prodRate', index: 'PROD_RATE', width: 80 },
		{
			label : '产品发行方',
			name : 'issuer',
			index : 'ISSUER',
			width : 80
		}, {
			label : '产品结构类型',
			name : 'structTyp',
			index : 'STRUCT_TYP',
			width : 80,
			formatter : function(value, options, row) {
				return structTypHtml(value);
			}
		},
		// { label: '还款方式', name: 'repayWay', index: 'REPAY_WAY', width: 80 },
		// { label: '还款周期单位', name: 'repayCycle', index: 'REPAY_CYCLE', width:
		// 80 },
		// { label: '具体还款时间 ', name: 'repayTime', index: 'REPAY_TIME', width: 80
		// },
		{
			label : '担保方式',
			name : 'guaranteeTyp',
			index : 'GUARANTEE_TYP',
			width : 80,
			formatter : function(value, options, row) {
				return guaranteeTypHtml(value);
			}
		}, {
			label : '货款周期 ',
			name : 'paymentCycle',
			index : 'REPAY_CYCLE',
			width : 80
		},
		// {
		// label : '最低贷款金额',
		// name : 'loadMin',
		// index : 'LOAD_MIN',
		// width : 80
		// }, {
		// label : '最高贷款金额',
		// name : 'loadMax',
		// index : 'LOAD_MAX',
		// width : 80
		// },
		{
			label : '状态 ',
			name : 'prodSts',
			index : 'PROD_STS',
			width : 80
		}, {
			label : '操作',
			name : 'operId',
			index : 'OPER_ID',
			width : 150,
			formatter : function(value, options, row) {
				return operateProd(row.prodId, row.prodNm, row.prodSts);
			}
		}
		// { label: '操作员ID', name: 'operId', index: 'OPER_ID', width: 80 }
		// { label: '创建时间', name: 'createTm', index: 'CREATE_TM', width: 80 },
		// { label: '时间戳', name: 'timestamp', index: 'TIMESTAMP', width: 80 }
		],
		viewrecords : true,
		height : 385,
		rowNum : 10,
		rowList : [ 10, 30, 50 ],
		rownumbers : true,
		rownumWidth : 25,
		autowidth : true,
		multiselect : true,
		// shrinkToFit:false,
		// autoScroll: true,
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
			// $("#jqGrid").closest(".ui-jqgrid-bdiv").css({
			// "overflow-x" : "hidden"
			// });
		}
	});
});

var vm = new Vue({
	el : '#rrapp',
	data : {
		q : {
			prodNo : null,
			prodNm : null
		},
		showList : true,
		title : null,
		prodInfo : {
			agencyInfo : {}
		}
	// issuer:{}

	},
	methods : {
		query : function() {
			$("#jqGrid").jqGrid('setGridParam', {
				page : 1
			});
			vm.reload();
		},
		add : function() {
			vm.showList = false;
			$("#saveOrUpdateBtns").css("display", "inline");
			$("input").removeAttr("readonly");
			$("select").removeAttr("disabled");
			vm.title = "新增";
			vm.prodInfo = {
				agencyInfo : {}
			};
		},
		update : function(prodId, prodNm, prodSts) {
			// var prodId = getSelectedRow();
			if (prodId == null) {
				return;
			}
			vm.showList = false;
			$("#saveOrUpdateBtns").css("display", "inline");
			vm.title = "修改";
			vm.getInfo(prodId);
			$("input").removeAttr("readonly");
			$("select").removeAttr("disabled");
			$("#prodNo").attr("readonly", true);
		},
		saveOrUpdate : function(event) {

			if (!$('#rrapp').Validform()) {
				return false;
			}

			var url = vm.prodInfo.prodId == null ? "../prod/prodinfo/save"
					: "../prod/prodinfo/update";
			var index = parent.layer.load(1);
			$.ajax({
				type : "POST",
				url : url,
				data : JSON.stringify(vm.prodInfo),
				success : function(r) {
					parent.layer.close(index);
					if (r.code === 0) {
						alert('操作成功', function(index) {
							vm.reload();
						});
					} else {
						alert(r.msg);
					}
				}
			});
		},
		del : function(event) {
			var prodIds = getSelectedRows();
			if (prodIds == null) {
				return;
			}

			confirm('确定要删除选中的记录？', function() {
				var index = parent.layer.load(1);
				parent.layer.close(index - 1);
				$.ajax({
					type : "POST",
					url : "../prod/prodinfo/delete",
					data : JSON.stringify(prodIds),
					success : function(r) {
						parent.layer.close(index);
						if (r.code == 0) {
							alert('操作成功', function(index) {
								$("#jqGrid").trigger("reloadGrid");
							});
						} else {
							alert(r.msg);
						}
					}
				});
			});
		},
		deleteProd : function(prodId, prodNm, prodSts) {
			// var prodId = getSelectedRows();
			if (prodId == null) {
				return;
			}

			confirm('确定要删除选中的记录？', function() {
				var index = parent.layer.load(1);
				parent.layer.close(index - 1);
				$.ajax({
					type : "POST",
					url : "../prod/prodinfo/deleteProd",
					data : JSON.stringify(prodId),
					success : function(r) {
						parent.layer.close(index);
						if (r.code == 0) {
							alert('操作成功', function(index) {
								$("#jqGrid").trigger("reloadGrid");
							});
						} else {
							alert(r.msg);
						}
					}
				});
			});
		},
		getInfo : function(prodId) {
			$.get("../prod/prodinfo/info/" + prodId, function(r) {
				filterHtmlResult(r);//过滤并转义返回对象中的所有非法字符
				vm.prodInfo = r.prodInfo;
			});
		},
		getAgencyInfo : function(agencyId) {
			$.get("../cust/agencyinfo/info/" + agencyId, function(r) {
				filterHtmlResult(r);
				vm.prodInfo.agencyInfo = r.agencyInfo;
			});
		},
		getAgencyDialog : function() {
			dialogOpen({
				title : '选择发行商',
				// url : 'cust/agencyinfoDialog.html?cooTyp=1&_' + $.now(),
				url : 'cust/agencyinfoDialog.html?_' + $.now(),
				width : '800px',
				height : '500px',
				scroll : true,
				success : function(iframeId, length) {
					vm.length = length;
				},
				yes : function(iframeId, index) {
					var agencyId = parent.frames[vm.length - 1].vm
							.acceptClick();
					if (agencyId) {
						vm.getAgencyInfo(agencyId);
						parent.layer.close(index);
					}
				}
			});
		},
		reload : function(event) {
			$("input").removeAttr("disabled");
			vm.showList = true;
			removeMessage($errorInput);
			var page = $("#jqGrid").jqGrid('getGridParam', 'page');
			$("#jqGrid").jqGrid('setGridParam', {
				postData : {
					'prodNo' : vm.q.prodNo,
					'prodNm' : vm.q.prodNm
				},
				page : page
			}).trigger("reloadGrid");
		},
		detail : function(prodId) {
			if (prodId == null) {
				return;
			}
			vm.showList = false;

			$("#saveOrUpdateBtns").css("display", "none");
			$("input").attr("readonly", true);
			$("select").attr("disabled", "disabled");
			$("input[type='button']").removeAttr("readonly");
			// vm.showEdit = false;
			// vm.showDetail = true;
			vm.title = "详情";
			vm.getInfo(prodId);
		},
		endOrActive : function(prodId, prodNm, prodSts, oper) {
			if (prodId == null) {
				return;
			}
			// 这里要初始化vm.prodInfo对象 清空缓存
			vm.prodInfo = {
				agencyInfo : {}
			};

			var message = "";
			// prodSts 0 改成1 0:草稿
			// prodSts 1 改成 2 1:生效
			// prodSts 2 改成 1 2:失效
			if ("0" == prodSts) {
				message = prodNm + "生效";
				prodSts = "1";
			} else if ("1" == prodSts) {
				message = prodNm + "失效";
				prodSts = "2";
			} else if ("2" == prodSts) {
				message = prodNm + "生效";
				prodSts = "1";
			} else {
				alert("不存在此状态");
				return false;
			}
			vm.prodInfo.prodId = prodId;
			vm.prodInfo.prodSts = prodSts;
			confirm('确定' + message + '操作么？', function() {
				var index = parent.layer.load(1);
				parent.layer.close(index - 1);
				$.ajax({
					type : "POST",
					url : "../prod/prodinfo/updateProdSts",
					data : JSON.stringify(vm.prodInfo),
					success : function(r) {
						parent.layer.close(index);
						if (r.code === 0) {
							alert('操作成功', function(index) {
								vm.reload();
							});
						} else {
							alert(r.msg);
						}
					}
				});
			});

		}
	}
});