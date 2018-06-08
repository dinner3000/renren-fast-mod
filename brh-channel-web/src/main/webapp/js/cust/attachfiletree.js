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
		enable: true,
		drag: {
			isCopy: false,
			isMove: false
		},
	
		//删除按钮配置
		showRemoveBtn: setRemoveBtn,
		removeTitle:"删除节点", 

		
		//编辑文件名配置
		showRenameBtn: setRenameBtn,
		renameTitle: setRenameTitle,

		
	},
	check: {
		enable: false
	},
	view : {
//		addDiyDom: addDiyDom,
		addHoverDom: addHoverDom,
		removeHoverDom: removeHoverDom
	},
	callback : {
		onClick : onClick,
		// 用于捕获节点被删除之前的事件回调函数，并且根据返回值确定是否允许删除操作 即执行onRemove
		beforeRemove: beforeRemove,      
		onRemove: onRemove,
		
		beforeEditName: zTreeBeforeEditName,
		//节点进入编辑名称状态，并且修改节点名称后触发此回调函数。并且根据返回值确定是否允许删除操作 即执行onRename
		beforeRename: zTreeBeforeRename,
		onRename: zTreeOnRename,
		
		//拖拽功能设置
		beforeDrag: false
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
			// 加载菜单树
			$.get("../cust/attachfiletree/select/" + flag, function(r) {
				vm.menu.parentId = r.menuList[0].fileCode;
				ztree = $.fn.zTree.init($("#menuTree"), setting, r.menuList);
				ztree.expandAll(true);
				var node = ztree.getNodeByParam("fileCode", vm.menu.parentId);
				ztree.selectNode(node);
				vm.menu.parentName = node.fileNm;
			})
		},
		getMenuAndFiles : function(flag, ciNo, incomNo) {
			var str={'flag':flag,'ciNo':ciNo,'incomNo':incomNo};
			// 加载菜单树
			   $.ajax({
                   type: "POST",
                   url: "../cust/attachfiletree/selectTreeAndFiles1",
                   data: JSON.stringify(str),
                   success: function(r){
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
	
	if (flag == 'previewFlag') {
		
		vm.getMenuAndFiles(flag, ciNo, incomNo);
	} else {
//		vm.getMenu(flag);// 加载树
		vm.getMenuAndFiles(flag, ciNo, incomNo);
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


//function addDiyDom(treeId, treeNode) {
//	
//};


var newCount = 0;

//鼠标悬浮展示新增、删除、编辑按钮
function addHoverDom(treeId, treeNode) {
//	debugger;
	
	//treeNode.fileLvl 的值
	//为null  文件夹或文件
	//为"Y"   末级菜单
	if("N" == treeNode.fileLvl){  //非末级菜单 非文件夹及文件   不添加新增插件 
		return false;
	}else if("1" == treeNode.fileCls){  //文件 也不添加新增插件
		return false;
	}
	var aObj = $("#" + treeNode.tId + "_a");
	
	//控制重复出现新增等按钮
	if ($("#diyBtn_"+treeNode.fileCode).length>0) return;
	if ($("#diyBtn2_"+treeNode.fileCode).length>0) return;
	
	var editStr = "<span id='diyBtn_space_" +treeNode.fileCode+ "' > </span>"
		+ "<button type='button' class='diyBtn1' style='height:20px;width:25px' id='diyBtn_" + treeNode.fileCode
		+ "' title='"+treeNode.fileNm+"'  onfocus='this.blur();'>+</button>"
//		+ "<button type='button' class='diyBtn1' style='height:20px;width:25px' id='diyBtn2_" + treeNode.fileCode
//		+ "' title='"+treeNode.fileNm+"'  onfocus='this.blur();'>-</button>"
		;
	aObj.append(editStr);
	
	//新增文件夹
	
	var addBtn = $("#diyBtn_"+treeNode.fileCode);
	if (addBtn) addBtn.bind("click", function(){  
		var folderLvl = "0";  //默认都是一级文件夹
		var fileId = "";
		//判断当前新增的文件夹级别
		if(undefined == treeNode.part || null == treeNode.part){
			 folderLvl = "1";  //0是根级文件夹    1是其他级文件夹
			 fileId = treeNode.fileId;
		}
		
		newCount++;  
//		debugger;
		var info = {
				"fileCode":treeNode.fileCode,
				"flag":$("#flag").val(),
				"ciNo":$("#ciNo").val(),
				"incomNo":$("#incomNo").val(),
				"fileNm" : "新建文件夹" + newCount,
				"parentId":treeNode.tId,  
				"folderLvl":folderLvl,
				"fileId":fileId
		};
     
        $.ajax({  
            type : "POST",    
            async : false,    
            url : "../cust/attachfileinfo/addFolder",    
            data: JSON.stringify(info),
		    dataType:"json",
            success:function(result){  
                if("" != result ){  
                    var zTree = $.fn.zTree.getZTreeObj("menuTree");  
                    
                    zTree.addNodes(treeNode, 
            			{	
            				id:result.entity.idNo,              //每个文件夹或文件的主键
                    		fileId:result.entity.idNo,
            				parentId:treeNode.tId, 
            				fileNm:"新建文件夹" + (newCount),
            				fileCode:treeNode.fileCode,   //父节点的fileCode
            			    folderLvl:folderLvl,
            			    fileCls:result.entity.fileCls,
            			    upFileNo:result.entity.upFileNo
            			}
            		);  
                    return false;  
                }else{  
                    alert("无法添加新文件夹，请联系管理员！");  
                }  
            }  
        });   
        return false;             
    });  
	
};
//失去焦点后 新增、删除、编辑按钮消失
function removeHoverDom(treeId, treeNode) {
	$("#diyBtn_"+treeNode.fileCode).unbind().remove();
	$("#diyBtn2_"+treeNode.fileCode).unbind().remove();
	$("#diyBtn_space_" +treeNode.fileCode).unbind().remove();
};

var log, className = "dark";  //这里还不知道做什么用的！！！

//删除操作
function beforeRemove(treeId, treeNode) { 
	
    className = (className === "dark" ? "":"dark");  
    var zTree = $.fn.zTree.getZTreeObj("menuTree");  
    zTree.selectNode(treeNode);  
//    debugger;
    var r=confirmOld("确定要删除选中的记录？")
    if (r){
    	 var info = {
 				"idNo": treeNode.fileId   
    	 };
 	            $.ajax({  
 	            type : "POST",    
 	            async : false,    
 	            data: JSON.stringify(info),
 			    dataType:"json",
 	            url : "../cust/attachfileinfo/deleteFolderAndFiles",    
 	            success:function(result){  
// 	            	debugger;
 	                if (result.isDelete) {  
 	                	return true;
 	                } else {  
 	                    alert("删除失败");  
 	                    return false;
 	                }  
 	            }  
 	        });  
 	    
      }
    else
      {
    	return false;
      }
}  

function onRemove(event, treeId, treeNode) {
//	 debugger;
	alert("删除成功！");
	
}

//设置所有的父节点不显示删除按钮
function setRemoveBtn(treeId, treeNode) {
	//查询树的过程中 要在菜单节点中  加入isParent=true的属性  传回前台
	return !checkNodeType(treeNode);
}

//设置父节点删除按钮的 Title 辅助信息为: "删除父节点"
function setRemoveTitle(treeId, treeNode) {
	return checkNodeType(treeNode) ? "删除父节点":"删除叶子节点";
}

//设置父节点编辑名称按钮的 Title 辅助信息为: "编辑父节点名称"
function setRenameTitle(treeId, treeNode) {
	return checkNodeType(treeNode) ? "编辑父节点名称":"编辑叶子节点名称";
}

//设置所有的父节点不显示编辑名称按钮
function setRenameBtn(treeId, treeNode) {
	return !checkNodeType(treeNode);
}

//禁止修改父节点的名称
function zTreeBeforeEditName(treeId, treeNode) {
	return !checkNodeType(treeNode);
}

//禁止修改的名称的长度小于 5
function zTreeBeforeRename(treeId, treeNode, newName, isCancel) {
	
//	if(newName.length < 5){
//		alert("修改名称长度不小于5");
//		return false;
//	}
	 className = (className === "dark" ? "":"dark");  
	 var zTree = $.fn.zTree.getZTreeObj("menuTree");  
	 zTree.selectNode(treeNode);  
		 var info = {
					"idNo": treeNode.fileId,   
					"fileNm" : newName
		 };
		 if (newName.length == 0) {  
		        alert("节点名称不能为空,请修改!");  
		        var zTree = $.fn.zTree.getZTreeObj("menuTree");  
		        setTimeout(function(){zTree.editName(treeNode)}, 10);  
		        return false;  
		 }else{  
		        $.ajax({  
		            type : "POST",    
		            async : false,    
		            url : "../cust/attachfileinfo/editRename",   
		            data: JSON.stringify(info),
				    dataType:"json",
		            success:function(result){  
		            	if (result.isEdit) {  
		            		return true;
		            		
		                } else {  
		                    alert("编辑失败");  
		                    return false;
		                }   
		            }  
		        });               
		}  
		 
}

function zTreeOnRename(event, treeId, treeNode, isCancel) {
	alert("编辑成功！");
	
}

//查看当前节点是菜单还是文件夹或文件  是菜单返回true
function checkNodeType(treeNode){
	if(null == treeNode.fileCls){
		return true;  //菜单
	}else{
		return false;   //文件夹或文件
	}
}