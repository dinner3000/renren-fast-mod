$(function () {
    $("#jqGrid").jqGrid({
// url: '../loadincomord/list',
    	url: '../load/income/insureFeeBook',
        datatype: "json",
        colModel: [			
            { label: '进件编号', name: 'incomNo', index: 'INCOM_NO', width: 150},
			{ label: '贷款合同号', name: 'contractNo', index: 'CONTRACT_NO', width: 125, key: true},
			{ label: '资金方名称', name: 'agencyNm', index: 'INCOM_NO', width: 100 }, 				
			{ label: '借款客户名称', name: 'ciNm', index: 'INCOM_NO', width: 100 }, 			
			{ label: '借款客户证件号码', name: 'licence', index: 'INCOM_NO', width: 130 }, 					
			{ label: '贷款金额(元)', name: 'loadAmt', index: 'CI_NO', width: 100 }, 			
			{ label: '贷款期限', name: 'loadTm1', index: 'PROD_NO', width: 80 }, 			
			{ label: '保险金额(元)', name: 'insurAmt', index: 'LOAD_CYCLE1', width: 100 }, 			
			{ label: '保险期限', name: 'loadTm2', index: 'LOAD_CYCLE2', width: 80 }, 	
			{ label: '保险开始日期', name: 'insureBeginTm', index: 'INSURE_BEGIN_TM', width:120 }, 			
			{ label: '保险终止日期', name: 'insureEndTm', index: 'INSURE_END_TM', width: 120 }, 	
			{ label: '保险年费率(%)', name: 'insureFeeRate', index: 'INSURE_FEE_RATE', width: 120 }, 			
			{ label: '绝对免赔率(%)', name: 'absExcess', index: 'ABS_EXCESS', width: 120 }, 			
			{ label: '保费(元)', name: 'insurFee', index: 'AGENCY_ID', width: 100 }, 	
			{ label: '保单号', name: 'insurNo', index: 'CI_TYP', width: 80 }, 
			{ label: '核保日期', name: 'underwriteTm', index: 'UNDERWRITE_TM', width: 120 },
			{ label: '保费收取日期', name: 'payInsureTm', index: 'PAY_INSURE_TM', width: 120 },
			{ label: '状态', name: 'paySts', index: 'PAY_STS', width: 80 }, 		
			{ label: '备注', name: 'remark', index: 'REMARK', width: 500 }, 		
        ],
		viewrecords: true,
        height: 385,
        rowNum: 50,
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
        	// 隐藏grid底部滚动条
  // $("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
        }
    });
    bindDate();//绑定时间控件
    bindUnderDate();
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		loadIncomOrd: {},
		q:{
			incomNo:'',
            ciNm: '',
            licence:'',
			contractNo: '',
			insurePayTyp: '00',
			paySts:'',
			payStartDay:'',
			payEndDay: '',
			underStartDay:'',
			underEndDay: ''
		},
		showList: true,
		showCheck:false,
		showSave:false,//控制新增保险专员保存按钮
		title: null,
        loadCheckOrd: {customerInfo:{},agencyInfo:{},contractInfo:{}},
        guarantees : {}
	},
	methods: {
		query: function () {
			$("#jqGrid").jqGrid('setGridParam',{page:1});
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.loadIncomOrd = {};
		},
		update: function (event) {
			var incomNo = getSelectedRow();
			if(incomNo == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(incomNo)
		},
		saveOrUpdate: function (event) {
			var url = vm.loadIncomOrd.incomNo == null ? "../loadincomord/save" : "../loadincomord/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.loadIncomOrd),
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
		},
		del: function (event) {
			var incomNos = getSelectedRows();
			if(incomNos == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: "../loadincomord/delete",
				    data: JSON.stringify(incomNos),
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
		getInfo: function(incomNo){
	         vm.needLoadSplb = true;
			 vm.loadCheckOrd = {incomNo:incomNo,customerInfo:{},contractInfo:{}};
			 $.get("../load/checkord/info/"+incomNo, function(r){
				 filterHtmlResult(r);//过滤并转义返回对象中的所有非法字符
	             vm.loadCheckOrd = r.loadCheckOrd;
                 if (!vm.loadCheckOrd.contractInfo) {
                     vm.loadCheckOrd.contractInfo = {contractNo:null};
                 }
	         });
		},
		reload: function (event) {
			vm.showList = true;
//			vm.reset();
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
				postData : {
					 'incomNo':vm.q.incomNo,
					 'ciNm': vm.q.ciNm,
					 'licence':vm.q.licence,
					 'contractNo':vm.q.contractNo,
					 'insurePayTyp': vm.q.insurePayTyp,
					 'paySts':vm.q.paySts,
					 'payStartDay':vm.q.payStartDay,
					 'payEndDay':vm.q.payEndDay,
					 'underStartDay':vm.q.underStartDay,
					 'underEndDay':vm.q.underEndDay
				},
                page:page
            }).trigger("reloadGrid");
		},
		reset : function() {
			loadCheckOrd = '';
			bindDate();
			 this.q = {
						ciNm: '',
						licence:'',
	    				contractNo: '',
	    				insurePayTyp: '',
	    				payStartDay:'',
	    				pauEndDay: '',
	    				paySts:'',
	    				underStartDay:'',
	    				underEndDay:''
		            }
		},
//		getPledgeInfo: function(agencyId){  
//			 $.get("../cust/attachfileinfo/pledgeList/" + incomNo, function(r) {
//				// debugger;
//					vm.loadCheckOrd.agencyInfo = r.agencyInfo;
//			 });
//	    },
		//弹出质押信息列表页面cust/attachfileinfo
	pledgeOpen: function(incomNo){
		dialogOpen({
			title : '押品信息查询',
            url : 'load/pledgeDialog.html?incomNo='+incomNo,
			width : '650px',
			height : '400px',
			btn: ['取消'],
			scroll : true,
			success : function(iframeId, length) {
				vm.length = length;
			},
			yes : function(iframeId, index) {
				//var incomNo = parent.frames[vm.length - 1].vm.acceptClick();	
//					vm.getPledgeInfo(incomNo);
				parent.layer.close(index);	
			}
		});
	},
		getGuarantees: function(incomNo){
            $.get("../cust/attachfileinfo/guarantees?incomNo="+incomNo, function(r){
                vm.guarantees = r.guarantees;
            });
        },
        submit:function(event){
        	
        },
        exportExcel:function(event){
        	var index = parent.layer.load(1);
    		$.ajax({
    			   type: 'POST',
    			   url:'../load/income/exportInsureFeeBook', 
    			   timeout:1000, // 超时时间设置，单位毫秒
    			   // beforeSend:ajaxLoading(),//发送请求前打开进度条
    			   contentType: "application/json", // 必须有
    	           dataType: "json", // 表示返回值类型，不必须
    	           data: JSON.stringify({'status': '00', 'ciNm': vm.q.ciNm,'licence':vm.q.licence,'contractNo':vm.q.contractNo,'agencyNm': vm.q.agencyNm,'startDay':vm.q.startDay,
	    				'endDay':vm.q.endDay}),
    			   success: function(r){
    			    	if (r.code == 0){
    			    		 	location.href = "../load/income/downLoadExcelBook/?filePath="+r.filePath+"&fileName=保费代收台账";
    			   	 	} else {
    			   	 	 parent.layer.close(index);
    			   	 	 		alert(r.msg);
    			    	}  			    	
    			   },
    			   complete:function(r){
	    				  　if(status=='timeout'){// 超时,status还有success,error等值的情况
	    				  		  parent.layer.close(index);
	    				　　　　　  alert("超时");
	    				　　　　}
    				  // ajaxLoadEnd();//发送请求后关闭进度条
    				   parent.layer.close(index);
    			   } 
    		});	
        },
        getAgencyDialog:function(){
        	// 通过userId 查询合作机构
        	// cooTyp 0 资金方 1资产方 2担保机构
        	var cooTyp = '0';
        	var agencyId='';
        	$.ajax({ 
               type: "post", 
               url: "../cust/agencyinfo/infoByUserId", 
               async:false, 
// dataType: ($.browser.msie) ? "text" : "xml",
               success: function(r){ 
            	   if(r.code == 0){
            		   if(null!= r.agencyId){
            			   agencyId = r.agencyId;
            		   }
            		   
				   }else{
						alert(r.msg);
				   }
               } 

        });
        	
        	dialogOpen({
				title : '选择合作机构',
				url : 'cust/agencyinfoDialog.html?cooTyp=' + cooTyp + '&'+'agencyId=' + agencyId,   
				// 跳转agencyinfoDialog.js 最终还要走这个url url :
				// '../cust/agencyinfo/list',
				
				width : '800px',
				height : '500px',
				scroll : true,
				success : function(iframeId, length) {
					vm.length = length;
				},
				yes : function(iframeId, index) {
					var agencyId = parent.frames[vm.length - 1].vm.acceptClick();
					if (agencyId) {
						vm.getAgencyInfo(agencyId);
						parent.layer.close(index);
					}
				}
			});
        },
		getAgencyInfo: function(agencyId){  
			vm.loadIncomOrd = {};
			// 这里的逻辑要进行判断 用userId 去查agencyId 能查出来则展示一条 查不出来则展示全部！！！！！
			 $.get("../cust/agencyinfo/info/" + agencyId, function(r) {
				// debugger;
					vm.loadCheckOrd.agencyInfo = r.agencyInfo;
					//vm.loadCheckOrd.agencyInfo.agencyNm = r.agencyInfo.agencyNm;
					// $("#agencyNm").val(vm.loadCheckOrd.agencyInfo.agencyNm );
					vm.q.agencyNm = vm.loadCheckOrd.agencyInfo.agencyNm ;
			 });
	    },
	    genApproval : function() {
            previewApproval(vm.loadCheckOrd.incomNo);
        }
	}
});

function bindDate() {
    // 绑定时间控件
    var threeMonth = getCurrentDate(-3);
    vm.q.startDay = threeMonth.replace("年", "").replace("月", "").replace("日", "");
    bindLaydateWithValue("#payStartDate", vm.q, 'payStartDay', threeMonth);

    var now = getCurrentDate(0);
    vm.q.endDay = now.replace("年", "").replace("月", "").replace("日", "");

    bindLaydateWithValue("#payEndDate", vm.q, 'payEndDay', now);
}
function bindUnderDate() {
    // 绑定时间控件
    var threeMonth = getCurrentDate(-3);
    vm.q.startDay = threeMonth.replace("年", "").replace("月", "").replace("日", "");
    bindLaydateWithValue("#underStartDate", vm.q, 'underStartDay', threeMonth);

    var now = getCurrentDate(0);
    vm.q.endDay = now.replace("年", "").replace("月", "").replace("日", "");

    bindLaydateWithValue("#underEndDate", vm.q, 'underEndDay', now);
}