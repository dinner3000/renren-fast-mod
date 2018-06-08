$(function () {
    $("#jqGrid").jqGrid({
        url: '../sys/flow/list',
        datatype: "json",
        colModel: [			
			{ label: '部署ID', name: 'deploymentId', width: 100, key: true },
			{ label: '部署名称', name: 'deploymentName', width: 150 }, 			
			{ label: '流程ID', name: 'processDefinitionId', width: 150 }, 			
			{ label: '流程标识', name: 'processDefinitionKey', width: 150 }, 	
			{ label: '流程名称', name: 'processDefinitionName', width: 150 },
			{ label: '流程描述', name: 'processDefinitionDes', width: 100 }, 
			{ label: '流程文件', name: 'processDefinitionRes', width: 150 },
			{ label: '流程图片', name: 'processDefinitionPng', width: 150 },
			{ label: '流程版本', name: 'processDefinitionVersion', width: 80 },
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: false,
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
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "auto" }); 
        }
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		q:{
			key: null
		},
	},
	methods: {
		query: function () {
			$("#jqGrid").jqGrid('setGridParam',{page:1});
			vm.reload();
		},
		reload: function (event) {
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
				postData:{'key': vm.q.key},
                page:page
            }).trigger("reloadGrid");
		}
	}
});