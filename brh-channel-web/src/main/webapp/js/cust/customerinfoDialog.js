$(function () {
    $("#jqGrid").jqGrid({
        url: '../cust/customerinfo/list',
        datatype: "json",
        postData:{
           'status': "00"
        },
        colModel: [			
			{ label: '客户编号', name: 'ciNo', index: 'CI_NO', width: 160, key: true },
			{ label: '客户名称', name: 'ciNm', index: 'CI_NM', width: 120 }, 			
//			{ label: '客户类别', name: 'ciTyp', index: 'CI_TYP', width: 80 }, 			
			{ label: '证件类型', name: 'idTyp', index: 'ID_TYP', width: 80 , formatter: function(value, options, row){
                return idTypHtml(value);
            }},
			{ label: '证件号码 ', name: 'idNo', index: 'ID_NO', width: 160 },
//			{ label: '法人姓名', name: 'legalNm', index: 'LEGAL_NM', width: 80 }, 			
//			{ label: '法人电话', name: 'legalTele', index: 'LEGAL_TELE', width: 80 }, 			
//			{ label: '营业执照', name: 'licence', index: 'LICENCE', width: 80 }, 			
//			{ label: '组织机构代码', name: 'orgCode', index: 'ORG_CODE', width: 80 }, 			
//			{ label: '联系地址', name: 'contactAds', index: 'CONTACT_ADS', width: 80 }, 			
//			{ label: '联系人', name: 'contactPer', index: 'CONTACT_PER', width: 80 }, 			
			{ label: '手机', name: 'contactTele', index: 'CONTACT_TELE', width: 120 },
			// { label: '联系电话', name: 'contactPhone', index: 'CONTACT_PHONE', width: 60 },
//			{ label: '邮编', name: 'postCode', index: 'POST_CODE', width: 80 }, 			
//			{ label: '企业地址', name: 'address', index: 'ADDRESS', width: 80 }, 			
//			{ label: '传真', name: 'fax', index: 'FAX', width: 80 }, 			
//			{ label: '邮件地址', name: 'email', index: 'EMAIL', width: 80 }, 			
//			{ label: '企业网站地址', name: 'webUrl', index: 'WEB_URL', width: 80 }, 			
			{ label: '客户状态', name: 'status', index: 'STATUS', width: 80 }
//			{ label: '操作员ID', name: 'operId', index: 'OPER_ID', width: 80 }, 			
// 			{ label: '创建时间', name: 'createTm', index: 'CREATE_TM', width: 80 },
//			{ label: '时间戳', name: 'timeStamp', index: 'TIME_STAMP', width: 80 }			
        ],
		viewrecords: true,
        height: 255,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        multiboxonly:true,
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
            ciNm: null,
            idNo: null,
            status: "00"
		},
		showList: true,
		title: null,
		customerInfo: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
        reset: function () {
            this.q = {
                ciNm: null,
                idNo: null,
                status: "00"
            }
        },
        acceptClick: function (event) {
            var ciNo = getDialogSelectedRow("#jqGrid");

            if(ciNo == null){
                return null;
            }
            return ciNo;
        },
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
				postData:{
					'ciNm': vm.q.ciNm,
					'idNo': vm.q.idNo,
					'status': vm.q.status
				},
                page:page
            }).trigger("reloadGrid");
		}
	}
});