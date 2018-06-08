$(function() {
	$("#jqGrid").jqGrid({
		url : '../cust/agencyinfo/list',
		datatype : "json",
		colModel : [ {
			label : '机构编号',
			name : 'agencyId',
			index : 'AGENCY_ID',
			width : 120,
			hidden : true,
			key : true
		}, {
			label : '机构名称',
			name : 'agencyNm',
			index : 'AGENCY_NM',
			width : 150,
			formatter : function(value, options, row) {
				return operateDetail(row.agencyId, row.agencyNm);
			}
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
		// {
		// label : '地址',
		// name : 'address',
		// index : 'ADDRESS',
		// width : 150
		// }, {
		// label : '联系人',
		// name : 'cooPer',
		// index : 'COO_PER',
		// width : 80
		// }, {
		// label : '联系手机',
		// name : 'cooTele',
		// index : 'COO_TELE',
		// width : 130
		// },
		// {
		// label : '联系电话',
		// name : 'cooPhone',
		// index : 'COO_PHONE',
		// width : 120
		// },
		// {
		// label : '邮编',
		// name : 'postCode',
		// index : 'POST_CODE',
		// width : 100
		// }, {
		// label : '传真',
		// name : 'fax',
		// index : 'FAX',
		// width : 120
		// },
		// {
		// label : '邮件',
		// name : 'email',
		// index : 'EMAIL',
		// width : 150
		// }, {
		// label : '网址',
		// name : 'webUrl',
		// index : 'WEB_URL',
		// width : 150
		// },
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
		}, {
			label : '操作',
			name : 'operId',
			index : 'OPER_ID',
			width : 150,
			formatter : function(value, options, row) {
				return operateAgency(row.agencyId, row.agencyNm, row.status);
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

var vm = new Vue(
		{
			el : '#rrapp',
			data : {
				q : {
					key : null,
					cooTyp : ' ',
					status : ' '
				},
				// showList : 'true',
				showList : true,
				showEdit : false,
				showDetail : false,
				title : null,
				agencyInfo : {}
			},
			methods : {
				reload : function() {
					vm.reload();
				},
				query : function() {
					$("#jqGrid").jqGrid('setGridParam', {
						page : 1
					});
					vm.reload();
				},
				add : function() {
					this.$http.get('../sys/config/token').then(function(r) {
						vm.showList = false;
						vm.showEdit = true;
						vm.title = "新增";
						vm.agencyInfo = {};
						$("#licenceId").attr("disabled", false);
						$("#taxNoId").attr("disabled", false);
						$("#organCodeId").attr("disabled", false);
						$("#orgCodeId").attr("disabled", false);
					});
				},
				update : function(event) {
					var agencyId = getSelectedRow();
					if (agencyId == null) {
						return;
					}
					vm.showList = false;
					vm.showEdit = true;
					vm.title = "修改";
					vm.getInfo(agencyId)
				},
				saveOrUpdate : function(event) {

					if (!$('#rrapp').Validform()) {
						return false;
					}

					var url = vm.agencyInfo.agencyId == null ? "../cust/agencyinfo/save"
							: "../cust/agencyinfo/update";
					var index = parent.layer.load(1);
					this.$http({
						method : "POST",
						url : url,
						body : JSON.stringify(vm.agencyInfo)
					}).then(function(r) {
						parent.layer.close(index);
						if (r.body.code === 0) {
							alert('操作成功', function(index) {
								vm.reload();
							});
						} else {
							alert(r.body.msg);
						}
					});
				},
				del : function(event) {
					var agencyIds = getSelectedRows();
					if (agencyIds == null) {
						return;
					}
					var ts = this;
					confirm('确定要删除选中的记录？', function() {
						var index = parent.layer.load(1);
						parent.layer.close(index - 1);
						ts.$http({
							method : "POST",
							url : "../cust/agencyinfo/delete",
							body : JSON.stringify(agencyIds)
						}).then(function(r) {
							parent.layer.close(index);
							if (r.body.code == 0) {
								alert('操作成功', function(index) {
									$("#jqGrid").trigger("reloadGrid");
								});
							} else {
								alert(r.body.msg);
							}
						});
					});
				},
				getInfo : function(agencyId) {
					this.$http.get("../cust/agencyinfo/info/" + agencyId).then(
							function(r) {
								filterHtmlResult(r);// 过滤并转义返回对象中的所有非法字符
								vm.agencyInfo = r.body.agencyInfo;
								if(r.body.agencyInfo.orgCode!=null){
									$("#licenceId").attr("disabled", true);
									$("#taxNoId").attr("disabled", true);
									$("#organCodeId").attr("disabled", true);
									$("#orgCodeId").attr("disabled", false);
								}else if(r.body.agencyInfo.licence!=null||r.body.agencyInfo.taxNo!=null||r.body.agencyInfo.organCode!=null){
									$("#licenceId").attr("disabled", false);
									$("#taxNoId").attr("disabled", false);
									$("#organCodeId").attr("disabled", false);
									$("#orgCodeId").attr("disabled", true);
								}else{
									$("#licenceId").attr("disabled", false);
									$("#taxNoId").attr("disabled", false);
									$("#organCodeId").attr("disabled", false);
									$("#orgCodeId").attr("disabled", false);
								}
							});
				},
				detail : function(agencyId) {
					if (agencyId == null) {
						return;
					}
					vm.showList = false;
					vm.showEdit = false;
					vm.showDetail = true;
					vm.title = "详情";
					vm.getInfo(agencyId)
				},
				reload : function(event) {
					vm.showList = true;
					removeMessage($errorInput);
					vm.showEdit = false;
					vm.showDetail = false;
					var page = $("#jqGrid").jqGrid('getGridParam', 'page');
					$("#jqGrid").jqGrid('setGridParam', {
						postData : {
							'agencyId' : vm.q.agencyId,
							'agencyNm' : vm.q.agencyNm,
							'cooTyp' : vm.q.cooTyp,
							'status' : vm.q.status
						},
						page : page
					}).trigger("reloadGrid");
				},
				endOrActive : function(agencyId, agencyNm, status, oper) {
					if (agencyId == null) {
						return;
					}
					vm.agencyInfo.agencyId = agencyId;
					vm.agencyInfo.status = status;
					var ts = this;
					confirm('确定' + oper + '与"' + agencyNm + '"的合作？',
							function() {
								vm.agencyInfo.agencyNm = '';
								var index = parent.layer.load(1);
								parent.layer.close(index - 1);
								ts.$http({
									method : "POST",
									url : "../cust/agencyinfo/update",
									body : JSON.stringify(vm.agencyInfo)
								}).then(function(r) {
									parent.layer.close(index);
									if (r.body.code == 0) {
										alert('操作成功', function(index) {
											$("#jqGrid").trigger("reloadGrid");
										});
									} else {
										alert(r.body.msg);
									}
								});
							});
				},
				// 统一社会信用代码用的处理事件
				upperCase : function(id) {
					// 小写转为大写
					var y = document.getElementById(id).value;
					// 如果内容不为空，将老的三证信息至为只读
					if (y == '' || y == null) {
						$("#licenceId").attr("disabled", false);
						$("#taxNoId").attr("disabled", false);
						$("#organCodeId").attr("disabled", false);
					} else {
						$("#licenceId").attr("disabled", true);
						$("#taxNoId").attr("disabled", true);
						$("#organCodeId").attr("disabled", true);
					}
					vm.agencyInfo.orgCode = y.toUpperCase();
				},
				// 老企业三证使用的onchange处理事件
				upperCaseOld : function(id) {
					// 小写转为大写
					var y = document.getElementById(id).value;
					// 如果内容不为空，将老的三证信息至为只读
					if (y == '' || y == null) {
						$("#orgCodeId").attr("disabled", false);
					} else {
						$("#orgCodeId").attr("disabled", true);
					}
					if (id == "organCodeId") {
						vm.agencyInfo.organCode = y.toUpperCase();
					}
				}

			}
		});