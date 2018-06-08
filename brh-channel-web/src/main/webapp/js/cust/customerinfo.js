$(function () {
    $("#jqGrid").jqGrid({
        url: '../cust/customerinfo/list',
        datatype: "json",
        colModel: [			
			{ label: '客户编号', name: 'ciNo', index: 'CI_NO', width: 150, key: true ,
				formatter : function(value, options, row) {
					return operateDetail(row.ciNo);
				}
			},
			{ label: '企业名称', name: 'ciNm', index: 'CI_NM', width: 80 }, 			
			{ label: '统一社会信用代码', name: 'orgCode', index: 'ORG_CODE', width: 180 }, 
//			{ label: '客户类别', name: 'ciTyp', index: 'CI_TYP', width: 80 }, 			
//			{ label: '证件类型', name: 'idTyp', index: 'ID_TYP', width: 80 }, 			
//			{ label: '证件号码 ', name: 'idNo', index: 'ID_NO', width: 80 }, 	
			{ label: '营业执照', name: 'licence', index: 'LICENCE', width: 180 }, 		
			{ label: '法人姓名', name: 'legalNm', index: 'LEGAL_NM', width: 80 }, 			
			{ label: '法人手机', name: 'legalTele', index: 'LEGAL_TELE', width: 100 }, 
//			{ label: '组织机构代码', name: 'orgCode', index: 'ORG_CODE', width: 80 }, 			
//			{ label: '联系地址', name: 'contactAds', index: 'CONTACT_ADS', width: 80 }, 			
//			{ label: '联系人', name: 'contactPer', index: 'CONTACT_PER', width: 80 }, 			
//			{ label: '联系手机', name: 'contactTele', index: 'CONTACT_TELE', width: 100 }, 			
//			{ label: '联系电话', name: 'contactPhone', index: 'CONTACT_PHONE', width: 80 }, 			
//			{ label: '邮编', name: 'postCode', index: 'POST_CODE', width: 80 }, 			
//			{ label: '企业地址', name: 'address', index: 'ADDRESS', width: 80 }, 			
//			{ label: '传真', name: 'fax', index: 'FAX', width: 80 }, 			
//			{ label: '邮件地址', name: 'email', index: 'EMAIL', width: 80 }, 			
//			{ label: '企业网站地址', name: 'webUrl', index: 'WEB_URL', width: 80 }, 
			{ label: '申请时间', name: 'createTm', index: 'CREATE_TM', width: 180 }, 	
			{ label: '客户状态', name: 'status', index: 'STATUS', width: 80 }, 			
//			{ label: '操作员ID', name: 'operId', index: 'OPER_ID', width: 80 }, 			
//			{ label: '时间戳', name: 'timeStamp', index: 'TIME_STAMP', width: 80 },
			{ label: '客户风险', name: 'riskLvl', index: 'RISK_LVL', width: 80 , formatter: function(value, options, row){
                return riskLvlHtml(value);
            }
			},
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
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
//        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
        
    });
    
    //进件记录添加点击事件 加载进件记录列表
    getIncomordRecord(1);
    //押品清单添加点击事件  加载押品清点列表
    getCollateralList(1);
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		q:{
			key: null,
			status:' ',
			riskLvl:' '
		},
		showList: true,
		showDetailList: true,
		showUpdateList:true,
		title: null,
		customerInfo: {}
	},
	methods: {
		query: function () {
			$("#jqGrid").jqGrid('setGridParam',{page:1});
			vm.reload();
		},
//		add: function(){
//			vm.showList = false;
//			vm.title = "新增";
//			vm.customerInfo = {};
//		},
		update: function (event) {
			var ciNo = getSelectedRow();
			if(ciNo == null){
				return ;
			}
			vm.showList = false;
			vm.showDetailList = true;
			vm.showUpdateList = false;
            vm.title = "修改";
            $("#saveOrUpdateBtn").css("display","inline");
            vm.getInfoToUpdate(ciNo);
            $("#riskLvl").css("color","#000000");   
            
//            $("#tel").attr("readonly",false);
////            $("#tel").removeAttr("readonly");
//            $("#postCode").removeAttr("readonly");
//            $("#address").removeAttr("readonly");
//            $("#riskLvlUpdate").removeAttr("disabled");
//            $("#remark").removeAttr("readonly");
//            $("#contactPer").removeAttr("readonly");
//            $("#contactTele").removeAttr("readonly");
//            $("#fax").removeAttr("readonly");
//            $("#postCode").removeAttr("readonly");
            
//			$("#ciNo").attr("readonly", "readonly");
		},
		saveOrUpdate: function (event) {
			
            if (!$('#rrapp').Validform()) {
                return false;
            }
			
			var url = vm.customerInfo.ciNo == null ? "../cust/customerinfo/save" : "../cust/customerinfo/update";
			this.$http({
				url: url,
				method: 'POST',
			    body: JSON.stringify(vm.customerInfo)
			}).then(function(r){
				if(r.body.code === 0){
					alert('操作成功', function(index){
						vm.reload();
					});
				}else{
					alert(r.body.msg);
				}
			});
		},
		del: function (event) {
			var ciNos = getSelectedRows();
			if(ciNos == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: "../cust/customerinfo/delete",
				    data: JSON.stringify(ciNos),
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
		getInfo: function(ciNo){
			var index = parent.layer.load(1);
			this.$http.get('../cust/customerinfo/info/'+ciNo).then(function(r){
				filterHtmlResult(r);//过滤并转义返回对象中的所有非法字符
                vm.customerInfo = r.body.customerInfo;
                
                //追加改变高危下拉框颜色的事件 
                if(null != r.body.customerInfo){
                	var riskLvl = vm.customerInfo.riskLvl;
                	if('0' == riskLvl){
                		$("#riskLvl").css("color","#000000");
                	}else if('1' == riskLvl){
                		$("#riskLvl").css("color","#FF0000");
                	}else{
                		$("#riskLvl").css("color","#000000");
                	}
                }
                parent.layer.close(index);
            });
			
		},
		getInfoToUpdate: function(ciNo){
			
			var index = parent.layer.load(1);
			this.$http.get('../cust/customerinfo/info/'+ciNo).then(function(r){
				filterHtmlResult(r);//过滤并转义返回对象中的所有非法字符
                vm.customerInfo = r.body.customerInfo;
                vm.descInput();
                parent.layer.close(index);
            });
			
		},
		reload: function (event) {
			vm.showList = true;
			vm.showDetailList = true;
			vm.showUpdateList = true;
			removeMessage($errorInput);
			
			$("#queryCustomerNm").removeAttr("disabled");
			$("#queryIdNo").removeAttr("disabled");
			$("#queryStatus").removeAttr("disabled");
			
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
				postData:{
					'ciNm': vm.q.ciNm,
					'idNo': vm.q.idNo,
					'status': vm.q.status,
					'riskLvl': vm.q.riskLvl
				},
                page:page
            }).trigger("reloadGrid");
		},
		detail: function(customerInfo){
			var ciNo = customerInfo.id;
			if (ciNo == null) {
				return;
			}
			vm.showList = false;
			vm.showUpdateList = true;
			vm.showDetailList = false;
			
			//初始化详情页面
			initDetail();
			
//			$("input").attr("disabled","disabled");
//			$("#input").attr("readonly",true);
//			$("select").attr("readonly",true);
//			$("input[type='button']").removeAttr("readonly");
//			$("#remark").attr("disabled","disabled");
			$("#queryCollateralByIncomNo").removeAttr("readonly");
			$("#queryRecordByIncomNo").removeAttr("readonly");
			vm.title = "详情";
			vm.getInfo(ciNo);
			
		},
		descInput: function(){
            if(vm.customerInfo.remark) {
                var txtVal = this.customerInfo.remark.length;
                this.remarkLength = 400 - txtVal;
            } else {
                this.remarkLength = 400;
            }
        },
        queryToIncomordRecord : function(){
        	vm.showList = false;
			vm.showDetailList = false;
			vm.showUpdateList = true;
			removeMessage($errorInput);
			
			$("#queryCustomerNm").removeAttr("disabled");
			$("#queryIdNo").removeAttr("disabled");
			$("#queryStatus").removeAttr("disabled");
			
			var page = $("#splbGridIncomord").jqGrid('getGridParam','page');
			$("#splbGridIncomord").jqGrid('setGridParam',{ 
				postData:{
					'ciNo': vm.customerInfo.ciNo,
					'incomNo': vm.q.incomNo
				},
                page:page
            }).trigger("reloadGrid");
        },
        queryToCollateral : function(){
        	vm.showList = false;
			vm.showDetailList = false;
			vm.showUpdateList = true;
			removeMessage($errorInput);
			
			$("#queryCustomerNm").removeAttr("disabled");
			$("#queryIdNo").removeAttr("disabled");
			$("#queryStatus").removeAttr("disabled");
			
			var page = $("#splbGridCollateral").jqGrid('getGridParam','page');
			$("#splbGridCollateral").jqGrid('setGridParam',{ 
				postData:{
					'ciNo': vm.customerInfo.ciNo,
					'incomNo': vm.q.incomNo
				},
                page:page
            }).trigger("reloadGrid");
        },
        downloadFile : function(idNo){
    		if(idNo==null){
    			alert("请选择要查看的一个文件");
    			return;
    		}
    		//跳转到后端控制器
    		location.href = "../cust/attachfileinfo/download.do?idNo="
				+ idNo;
        },
		incomordDetail: function(incomNo){
			window.location.href = "../load/loadincomord.html?customerInfoToIncomNo=" + incomNo;
		}
	}
});