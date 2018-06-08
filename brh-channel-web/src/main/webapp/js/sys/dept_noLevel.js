$(function() {
	$("#jqGrid").jqGrid({
		url : '../sys/dept/list',
		datatype : "json",
		colModel : [ {
			label : '部门ID',
			name : 'deptId',
			index : 'DEPT_ID',
			width : 50,
			key : true
		},{
			label : '部门名称',
			name : 'name',
			index : 'NAME',
			width : 80
		}, {
			label : '上级部门',
			name : 'parentName',
			index : 'PARENT_ID',
			width : 80
		},  {
			label : '合作机构ID',
			name : 'agencyId',
			index : 'agencyId',
			width : 80
		},{
			label : '合作机构名称',
			name : 'agencyNm',
			index : 'agencyNm',
			width : 80
		}, {
			label : '排序号',
			name : 'orderNum',
			index : 'ORDER_NUM',
			width : 80
		} ],
		viewrecords : true,
		height : 385,
		rowNum : 10,
		rowList : [ 10, 30, 50 ],
		rownumbers : true,
		rownumWidth : 25,
		autowidth : true,
//		shrinkToFit:false,
//		autoScroll: true,
		multiselect : true,
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
//			$("#jqGrid").closest(".ui-jqgrid-bdiv").css({
//				"overflow-x" : "hidden"
//			});
		}
	});
});

var setting = {
	data : {
		simpleData : {
			enable : true,
			idKey : "deptId",
			pIdKey : "parentId",
			rootPId : -1
		},
		key : {
			url : "nourl"
		}
	}
};
var ztree;

var vm = new Vue({
	el : '#rrapp',
	data : {
		showList : true,
		title : null,
		dept : {
			parentName : null,
			parentId : 0,
			// type:1,
			orderNum : 0,
			agencyInfo : {}
		}
	},
	methods : {
		getMenu : function(menuId) {
			// 加载菜单树
			$.get("../sys/dept/select", function(r) {
				ztree = $.fn.zTree.init($("#menuTree"), setting, r.deptList);
				var node = ztree.getNodeByParam("deptId", vm.dept.parentId);
				ztree.selectNode(node);

				vm.dept.parentName = node.name;
			})
		},
		query: function () {
			$("#jqGrid").jqGrid('setGridParam',{page:1});
			vm.reload();
		},
		add : function() {
			vm.showList = false;
			vm.title = "新增";
			vm.dept = {
				parentName : null,
				parentId : 0,
				orderNum : 0,
				agencyInfo : {},
				agencyNm : null,
				agencyId : null
			};
			vm.getMenu();
		},
		update : function(event) {
			var deptIds = getSelectedRow();
			if (deptIds == null) {
				return;
			}
			
			$.get("../sys/dept/info/" + deptIds, function(r) {
				vm.showList = false;
				vm.title = "修改";
				vm.dept = r.sysDept;
				// 获取部门树
				vm.getMenu();
				// 新增部门时 agencyId 和 agencyNm可以为空
				console.log(r.sysDept.agencyInfo);
				if (r.sysDept.agencyInfo != null) {
					vm.dept.agencyInfo = r.sysDept.agencyInfo;
					// 获取合作机构信息
					var agencyId = vm.dept.agencyInfo.agencyId;
					if (null != agencyId) {
						vm.getAgencyInfo(vm.dept.agencyInfo.agencyId);
					}
				}
			});

		},
		del : function(event) {
			var deptIds = getSelectedRows();
			if (deptIds == null) {
				return;
			}
			confirm('确定要删除选中的记录？', function() {
				$.ajax({
					type : "POST",
					url : "../sys/dept/delete",
					data : JSON.stringify(deptIds),
					success : function(r) {
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
		},
		saveOrUpdate : function(event) {
			var url = vm.dept.deptId == null ? "../sys/dept/save"
					: "../sys/dept/update";

			if (!$('#rrapp').Validform()) {
				return false;
			}
			var index = parent.layer.load(1);

			$.ajax({
				type : "POST",
				url : url,
				data : JSON.stringify(vm.dept),
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
		menuTree : function() {
			layer.open({
				type : 1,
				offset : '50px',
				skin : 'layui-layer-molv',
				title : "选择部门",
				area : [ '300px', '450px' ],
				shade : 0,
				shadeClose : false,
				content : jQuery("#menuLayer"),
				btn : [ '确定', '取消' ],
				btn1 : function(index) {
					var node = ztree.getSelectedNodes();
					// 选择上级菜单
					vm.dept.parentId = node[0].deptId;
					vm.dept.parentName = node[0].name;

					layer.close(index);
				}
			});
		},
		getAgencyInfo : function(agencyId) {
			$.get("../cust/agencyinfo/info/" + agencyId, function(r) {
				filterHtmlResult(r);//过滤并转义返回对象中的所有非法字符
				vm.dept.agencyInfo = r.agencyInfo;
				if (null != r.agencyInfo) {
					vm.dept.agencyInfo.agencyNm = r.agencyInfo.agencyNm;
					vm.dept.agencyNm = r.agencyInfo.agencyNm;
				}
			});
		},
		getAgencyDialog : function() {
			dialogOpen({
				title : '选择合作机构',
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
			vm.showList = true;
			removeMessage($('#rrapp'));
			var page = $("#jqGrid").jqGrid('getGridParam', 'page');
			$("#jqGrid").jqGrid('setGridParam', {
				postData:{'name': vm.dept.name},
				page : page
			}).trigger("reloadGrid");
		},
		resetAgencyNm : function(){
			vm.dept.agencyId = "";
			vm.dept.agencyNm = "";   //清空下拉框内容
			vm.dept.agencyInfo.agencyNm = ""; //清空上送内容
			vm.dept.agencyInfo.agencyId = "";
		}
	}
});