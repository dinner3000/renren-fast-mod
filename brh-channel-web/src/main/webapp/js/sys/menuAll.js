/**
 * 系统菜单js
 */

$(function () {
	initialPage();
	getGrid();
});

function initialPage() {
	$(window).resize(function() {
		TreeGrid.table.resetHeight({height: $(window).height()-100});
	});
}

function getGrid() {
	var colunms = TreeGrid.initColumn();
    var table = new TreeTable(TreeGrid.id, '../sys/menu/listAll?_' + $.now(), colunms);
    table.setExpandColumn(2);
    table.setIdField("menuId");
    table.setCodeField("menuId");
    table.setParentCodeField("parentId");
    table.setExpandAll(false);
    table.setHeight($(window).height()-100);
    table.init();
    TreeGrid.table = table;
}

var TreeGrid = {
    id: "dataGrid",
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
TreeGrid.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: '编号', field: 'menuId', visible: false, align: 'center', valign: 'middle', width: '50px'},
        {title: '名称', field: 'name', align: 'center', valign: 'middle', width: '180px'},
        {title: '上级菜单', field: 'parentName', align: 'center', valign: 'middle', width: '100px'},
        {title: '图标', field: 'icon', align: 'center', valign: 'middle', width: '50px', formatter: function(item, index){
            return item.icon == null ? '' : '<i class="'+item.icon+' fa-lg"></i>';
        }},
        {title: '类型', field: 'type', align: 'center', valign: 'middle', width: '60px', formatter: function(item, index){
            if(item.type === 0){
                return '<span class="label label-primary">目录</span>';
            }
            if(item.type === 1){
                return '<span class="label label-success">菜单</span>';
            }
            if(item.type === 2){
                return '<span class="label label-warning">按钮</span>';
            }
        }},
        {title: '排序', field: 'orderNum', align: 'center', valign: 'middle', width: '50px'},
        {title: '菜单URL', field: 'url', align: 'center', valign: 'middle', width: '200px'},
        {title: '授权标识', field: 'perms', align: 'center', valign: 'middle'}]
    return columns;
};



var setting = {
	data: {
		simpleData: {
			enable: true,
			idKey: "menuId",
			pIdKey: "parentId",
			rootPId: -1
		},
		key: {
			url:"nourl"
		}
	}
};
var ztree;

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		menu:{
			parentName:null,
			parentId:0,
			type:1,
			orderNum:0
		}
	},
	methods: {
		getMenu: function(menuId){
			//加载菜单树
			$.get("../sys/menu/select", function(r){
				ztree = $.fn.zTree.init($("#menuTree"), setting, r.menuList);
				var node = ztree.getNodeByParam("menuId", vm.menu.parentId);
				ztree.selectNode(node);
				
				vm.menu.parentName = node.name;
			})
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.menu = {parentName:null,parentId:0,type:1,orderNum:0};
			vm.getMenu();
		},
		update: function (event) {
			var ck = TreeGrid.table.getSelectedRow();
			//var menuId = getSelectedRow();
			var menuId=null;
			if(checkedRow(ck)){
				menuId=ck[0].id;	
			}
			if(menuId == null){
				return ;
			}
			$.get("../sys/menu/info/"+menuId, function(r){
				vm.showList = false;
                vm.title = "修改";
                vm.menu = r.menu;
                
                vm.getMenu();
            });
		},
		del: function (event) {
			//var menuIds = getSelectedRows();
			var ck = TreeGrid.table.getSelectedRow(), menuIds = [];
			if(checkedArray(ck)){
				$.each(ck, function(idx, item){
					menuIds[idx] = item.id;
				});
			}
			if(menuIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: "../sys/menu/delete",
				    data: JSON.stringify(menuIds),
				    success: function(r){
				    	if(r.code === 0){
							alert('操作成功', function(index){
								vm.reload();
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		saveOrUpdate: function (event) {
			var url = vm.menu.menuId == null ? "../sys/menu/save" : "../sys/menu/update";
			
			 if (!$('#rrapp').Validform()) {
	                return false;
	         }
			 
			var index = parent.layer.load(1);
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.menu),
			    success: function(r){
			    	parent.layer.close(index);
			    	if(r.code === 0){
						alert('操作成功', function(index){
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
		menuTree: function(){
			layer.open({
				type: 1,
				offset: '50px',
				skin: 'layui-layer-molv',
				title: "选择菜单",
				area: ['300px', '450px'],
				shade: 0,
				shadeClose: false,
				content: jQuery("#menuLayer"),
				btn: ['确定', '取消'],
				btn1: function (index) {
					var node = ztree.getSelectedNodes();
					//选择上级菜单
					vm.menu.parentId = node[0].menuId;
					vm.menu.parentName = node[0].name;
					
					layer.close(index);
	            }
			});
		},
		query: function () {
			$("#jqGrid").jqGrid('setGridParam',{page:1});
			vm.reload();
		},

		reload: function (event) {
			vm.showList = true;
			removeMessage($errorInput);
			//var page = $("#jqGrid").jqGrid('getGridParam','page');
//			$("#jqGrid").jqGrid('setGridParam',{ 
//				postData:{'name': vm.menu.name},
//                page:page
//            }).trigger("reloadGrid");
			TreeGrid.table.refresh();
		}
	}
});