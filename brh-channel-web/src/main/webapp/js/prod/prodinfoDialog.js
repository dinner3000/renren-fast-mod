$(function () {
    $("#jqGrid").jqGrid({
        url: '../prod/prodinfo/list',
        datatype: "json",
        postData:{
            'prodSts': "1"
        },
        colModel: [			
			{ label: '产品序号', name: 'prodId', index: 'PROD_ID', width: 50, key: true, hidden: true },
			{ label: '产品编号', name: 'prodNo', index: 'PROD_NO', width: 80 }, 			
			{ label: '产品名称', name: 'prodNm', index: 'PROD_NM', width: 80 }, 			
			{ label: '产品类型', name: 'prodTyp', index: 'PROD_TYP', width: 80 }, 			
			{ label: '产品利率', name: 'prodRate', index: 'PROD_RATE', width: 80, formatter: function(value, options, row){
                if(value){
                    return value + "%"
                } else {
                    return "";
                }
            }},
			{ label: '发行方', name: 'issuer', index: 'ISSUER', width: 80 },
			{ label: '结构类型', name: 'structTyp', index: 'STRUCT_TYP', width: 80 ,
				formatter : function(value, options, row) {
					return structTypHtml(value);
				}
			},
//			{ label: '还款方式', name: 'repayWay', index: 'REPAY_WAY', width: 80 }, 			
//			{ label: '还款周期单位', name: 'repayCycle', index: 'REPAY_CYCLE', width: 80 }, 			
//			{ label: '具体还款时间 ', name: 'repayTime', index: 'REPAY_TIME', width: 80 }, 	
			{ label: '货款周期 ', name: 'paymentCycle', index: 'PAYMENTCYCLE', width: 80 },
			{ label: '最低贷款额（万元）', name: 'loadMin', index: 'LOAD_MIN', width: 140 },
			{ label: '最高贷款额（万元）', name: 'loadMax', index: 'LOAD_MAX', width: 140 },
//          { label: '状态 ', name: 'prodSts', index: 'PROD_STS', width: 50 },
//			{ label: '操作员ID', name: 'operId', index: 'OPER_ID', width: 80 } 			
//			{ label: '创建时间', name: 'createTm', index: 'CREATE_TM', width: 80 }, 			
//			{ label: '时间戳', name: 'timestamp', index: 'TIMESTAMP', width: 80 }			
        ],
		viewrecords: true,
        height: 255,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        beforeSelectRow: beforeSelectRow,
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
            prodNo: null,
            prodNm: null,
            prodSts: '1'
		},
		showList: true,
		title: null,
		prodInfo: {

		}
	},
	methods: {
		query: function () {
			vm.reload();
		},
        reset: function () {
            this.q = {
                prodNo: null,
                prodNm: null,
                prodSts: '1'
            }
        },
        acceptClick: function (event) {
            var prodId = getDialogSelectedRow("#jqGrid");

            if(prodId == null){
                return null;
            }
            return prodId;
        },
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
				postData:{
					'prodNo': vm.q.prodNo,
					'prodNm': vm.q.prodNm,
                    'prodSts': vm.q.prodSts
				},
                page:page
            }).trigger("reloadGrid");
		}
	}
});