$(function () {
    $("#jqGrid").jqGrid({
        url: '../cust/attachfileinfo/list',
        postData:{fileCode:$("#fileCode",parent.document).val(),
        	incomNo:$("#incomNo",parent.document).val()},
        datatype: "json",
        colModel: [			
			{ label: 'idNo', name: 'idNo', index: 'ID_NO', width: 50, key: true,hidden:true},
			{ label: '文件名', name: 'fileNm', index: 'FILE_NM', width: 80 }, 			
			{ label: '文件类型', name: 'fileTyp', index: 'FILE_TYP', width: 80 },			
			{ label: '进件编号', name: 'incomNo', index: 'INCOM_NO', width: 80 }, 			
			{ label: '客户号', name: 'ciNo', index: 'CI_NO', width: 80 },			
			{ label: '上传时间', name: 'createTm', index: 'CREATE_TM', width: 80 }			
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        shrinkToFit:false,    
        autoScroll: true,  
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
            order: "order",
            fileCode:"fileCode",
            incomNo:"incomNo"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
//    new AjaxUpload('#upload', {
//        action: '../cust/attachfileinfo/upload',
//        name: 'file',
//        data:{
//        	fileCode: $("#fileCode",parent.document).val(),
//        	incomNo:$("#incomNo",parent.document).val()
//        },
//        autoSubmit:true,
//        responseType:"json",
//        onSubmit:function(file, extension){
//        	// && /^(jpg|jpeg|png|gif)$/.test(extension.toLowerCase())
//            if (!(extension )){
//                alert('只支持jpg、png、gif格式的图片！');
//                return false;
//            }
//        },
//        onComplete : function(file, r){
//            if(r.code == 0){
//                alert("上传成功");
//                vm.reload();
//            }else{
//                alert(r.msg);
//            }
//        }
//    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		q:{
			key: null
		},
		showList: true,
		title: null,
		attachFileInfo: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		del: function (event) {
			var idNos = getSelectedRows();
			if(idNos == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: "../cust/attachfileinfo/delete",
				    data: JSON.stringify(idNos),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								$("#jqGrid").trigger("reloadGrid");
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		//此方法暂时不用
		deleteFile: function (event) {
			//debugger;
			var treeNode = window.top.treeNode ;
			var treeId = window.top.treeId ;
			confirm('确定要删除选中的记录？', function(){

			});
			
//			var idNo = $("#idNo",parent.document).val();
//			if(idNo == null){
//				return ;
//			}
//			
//			confirm('确定要删除选中的记录？', function(){
//				$.ajax({
//					type: "POST",
//				    url: "../cust/attachfileinfo/deleteFile",
//				    data: JSON.stringify(idNo),
//				    success: function(r){
//						if(r.code == 0){
//							alert('操作成功', function(index){
//								$("#jqGrid").trigger("reloadGrid");
//							});
//						}else{
//							alert(r.msg);
//						}
//					}
//				});
//			});
		},
		getInfo: function(idNo){
			$.get("../cust/attachfileinfo/info/"+idNo, function(r){
                vm.attachFileInfo = r.attachFileInfo;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
				postData:{'key': vm.q.key},
                page:page
            }).trigger("reloadGrid");
		}
	}
});