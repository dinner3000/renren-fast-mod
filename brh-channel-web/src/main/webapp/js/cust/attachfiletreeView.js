//zTree配置
var setting = {
	data : {
		simpleData : {
			enable : true,
			idKey : "fileCode",
			pIdKey : "parentId",
			rootPId : 0
		},
		key : {
			name : "fileNm"
		}
	},
	edit : {
//		showRemoveBtn: true
	},
	view : {
//		addDiyDom: addDiyDom,
//		addHoverDom: addHoverDom,
//		removeHoverDom: removeHoverDom
	},
	callback : {
		onClick : onClick
	}
};

var ztree;

var vm = new Vue({
	el : '#rrapp',
	data : {
		q : {
			key : null
		},
		showList : true,
		title : null,
		attachFileTree : {},
		menu : {
			parentName : null,
			parentId : null
		}
	},
	methods : {

		del : function(event) {
			var fileIds = getSelectedRows();
			if (fileIds == null) {
				return;
			}

			confirm('确定要删除选中的记录？', function() {
				$.ajax({
					type : "POST",
					url : "../cust/attachfiletree/delete",
					data : JSON.stringify(fileIds),
					success : function(r) {
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

		reload : function(event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam', 'page');
			$("#jqGrid").jqGrid('setGridParam', {
				postData : {
					'key' : vm.q.key
				},
				page : page
			}).trigger("reloadGrid");
		},
		getMenu : function(flag) {
			var index = parent.layer.load(1);
			// 加载菜单树
			$.get("../cust/attachfiletree/select/" + flag, function(r) {
				 parent.layer.close(index);
				vm.menu.parentId = r.menuList[0].fileCode;
				ztree = $.fn.zTree.init($("#menuTree"), setting, r.menuList);
				ztree.expandAll(true);
				var node = ztree.getNodeByParam("fileCode", vm.menu.parentId);
				ztree.selectNode(node);
				vm.menu.parentName = node.fileNm;
			})
		},
		openDownLoadBatch :function(incomNo,ciNo){
			vm.showList = false;
			//alert("aaa"+incomNo);
		/*	dialogOpen({
				title: '批量下载',
				url: 'cust/attachDownDialog.html',
				width: '800px',
				height: '500px',
				scroll: true,
				success: function(iframeId,length){
					vm.length = length;
				},
				yes: function (iframeId, index) {

				}
			});*/
			//location.href='../cust/attachDownDialog.html?incomNo='+incomNo+'&ciNo='+ciNo;
			//var pn = $("#gotopagenum").val();//#gotopagenum是文本框的id属性
			//暂时注释 
//			var iWidth=800;                         //弹出窗口的宽度;
//			var iHeight=500;                        //弹出窗口的高度;
//			//window.screen.height获得屏幕的高，window.screen.width获得屏幕的宽
//			var iTop = (window.screen.height-30-iHeight)/2;       //获得窗口的垂直位置;
//			var iLeft = (window.screen.width-10-iWidth)/2;        //获得窗口的水平位置;
//			window.open('../cust/attachDownDialog.html?incomNo='+incomNo+'&ciNo='+ciNo,'流程图','height='+iHeight+',,innerHeight='+iHeight+',width='+iWidth+',innerWidth='+iWidth+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=auto,resizeable=no,location=no,status=no')
		},
		getMenuAndFiles : function(flag, ciNo, incomNo) {
			var str={'flag':flag,'ciNo':ciNo,'incomNo':incomNo};
			var index = parent.layer.load(1);

			// 加载菜单树
			   $.ajax({
                   type: "POST",
                   url: "../cust/attachfiletree/selectTreeAndFiles1",
                   data: JSON.stringify(str),
                   success: function(r){
                	   parent.layer.close(index);
                       if(r.code === 0){
                    	vm.menu.parentId = r.menuList[0].fileCode;
       					ztree = $.fn.zTree
       							.init($("#menuTree"), setting, r.menuList);
       					ztree.expandAll(true);
       					var node = ztree.getNodeByParam("fileCode",
       							vm.menu.parentId);
       					ztree.selectNode(node);
       					vm.menu.parentName = node.fileNm;
                       }else{
                           alert(r.msg);
                       }
                   }
               })
		}
	}
});
$(function() {
	var incomNo = GetQueryString("incomNo");
	var ciNo = GetQueryString("ciNo");
	var flag = GetQueryString("flag");

	// 三个值放入隐藏域
	$("#incomNo").val(incomNo);
	$("#ciNo").val(ciNo);
	$("#flag").val(flag);
	if ('previewFlag' == flag || "applyFlag" == flag) {
		vm.getMenuAndFiles(flag, ciNo, incomNo);
	} else {
		vm.getMenu(flag);// 加载树
	}
});

// 获取url上的参数
function GetQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
}
