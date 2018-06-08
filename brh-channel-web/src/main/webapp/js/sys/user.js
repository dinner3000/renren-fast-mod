$(function () {
    $("#jqGrid").jqGrid({
        url: '../sys/user/list',
        datatype: "json",
        colModel: [			
			{ label: '用户ID', name: 'userId', index: "user_id", width: 45, key: true },
			{ label: '用户名', name: 'username', width: 75 },
			{ label: '用户中文名', name: 'chUsername', width: 75 },
			{ label: '邮箱', name: 'email', width: 90 },
			{ label: '手机号', name: 'mobile', width: 100 },
			{ label: '状态', name: 'status', width: 80, formatter: function(value, options, row){
				return value === 0 ? 
					'<span class="label label-danger">禁用</span>' : 
					'<span class="label label-success">正常</span>';
			}},
			{ label: '创建时间', name: 'createTime', index: "create_time", width: 80}
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
//        shrinkToFit:false,    
//        autoScroll: true,  
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});
var setting = {
		data: {
			simpleData: {
				enable: true,
				idKey: "deptId",
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
		q:{
			username: null
		},
		showList: true,
		title:null,
		roleList:{},
		user:{
			status:1,
			roleIdList:[],
			parentName:null,
			parentId:0,
//			type:1,
			orderNum:0,
			deptId:0
		},
		menu :{status:1,roleIdList:[],parentName:null,parentId:0,orderNum:0,deptId:0}
	},
	methods: {
		getMenu: function(menuId){
			//加载菜单树
			$.get("../sys/dept/select", function(r){
				ztree = $.fn.zTree.init($("#menuTree"), setting, r.deptList);
//				var node = ztree.getNodeByParam("deptId", vm.menu.parentId);
				var node = ztree.getNodeByParam("deptId", vm.menu.deptId);
//				var node = ztree.getNodeByParam("deptId", vm.user.deptId);
				ztree.selectNode(node);
				vm.user.parentName = node.name;
				
				vm.user.deptId = vm.menu.deptId;
//				debugger;
			})
			
		},
		 
		query: function () {
			$("#jqGrid").jqGrid('setGridParam',{page:1});
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.roleList = {};
			vm.user = {status:1,roleIdList:[],parentName:null,parentId:0,orderNum:0,deptId:0};
			vm.menu = {status:1,roleIdList:[],parentName:null,parentId:0,orderNum:0,deptId:0};
			//获取角色信息
			this.getRoleList();
			//获取菜单树信息
			vm.getMenu();
		},
		update: function (event) {
			var userId = getSelectedRow();
			if(userId == null){
				return ;
			}
			
			vm.showList = false;
            vm.title = "修改";
			
			vm.getUser(userId);
			//获取角色信息
			this.getRoleList();
			
			$.get("../sys/dept/deptInfoByUserId/"+userId, function(r){
				filterHtmlResult(r);//过滤并转义返回对象中的所有非法字符
				vm.showList = false;
                vm.title = "修改";
                vm.menu = r.sysDept;
                
                //新增用户时 可以不选部门  所以这里要判空   
                //不选择部门时  deptId赋值为0 回显时即根节点

                if(null != r.sysDept){
//                    vm.user = r.sysDept;
                    vm.menu.deptId = r.sysDept.deptId;
                }else{
                	vm.menu = {status:1,roleIdList:[],parentName:null,parentId:0,orderNum:0,deptId:0};
                }

                vm.getMenu();
            });
		},
		del: function () {
			var userIds = getSelectedRows();
			if(userIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: "../sys/user/delete",
				    data: JSON.stringify(userIds),
				    success: function(r){
						if(r.code == 0){
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
			var url = vm.user.userId == null ? "../sys/user/save" : "../sys/user/update";
			
			if (!$('#rrapp').Validform()) {
	                return false;
	        }
			 
			if($("input[type='checkbox']:checked").length < 1){
				alert("请选择角色！");
				return false;
			}
			
			//保存时对密码判空校验
			if("../sys/user/save" == url){
				if("" == $.trim($("#password").val()) || null == $.trim($("#password").val())){
					alert("密码不能为空！");
					return false;
				}
			}
			
			var index = parent.layer.load(1);
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.user),
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
		getUser: function(userId){
			$.get("../sys/user/info/"+userId, function(r){
				vm.user = r.user;
			});
		},
		getRoleList: function(){
			$.get("../sys/role/select", function(r){
				vm.roleList = r.list;
			});
		},
		menuTree: function(){
			layer.open({
				type: 1,
				offset: '50px',
				skin: 'layui-layer-molv',
				title: "选择部门",
				area: ['300px', '450px'],
				shade: 0,
				shadeClose: false,
				content: jQuery("#menuLayer"),
				btn: ['确定', '取消'],
				btn1: function (index) {
					var node = ztree.getSelectedNodes();
					//选择上级菜单
					vm.user.parentId = node[0].deptId;
					vm.user.parentName = node[0].name;
					vm.user.deptId = node[0].deptId;
					vm.user.deptName = node[0].name; 
					layer.close(index);
	            }
			});
		},
		reload: function (event) {
			vm.showList = true;
			removeMessage($errorInput);
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                postData:{'username': vm.q.username},
                page:page
            }).trigger("reloadGrid");
		}
	}
});