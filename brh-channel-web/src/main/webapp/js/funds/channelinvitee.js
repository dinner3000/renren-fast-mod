$(function () {
    $("#jqGrid").jqGrid({
        url: '../channel/inviteeList',
        datatype: "json",
        colModel: [	
			{ label: '用户名称', name: 'realName', index: 'REALNAME', width: 80 }, 			
			{ label: '出借时间', name: 'inputTime', index: 'INPUTTIME', width: 180 },
			{ label: '手机号码', name: 'phoneTel', index: 'PHONETEL', width: 130 },
			{ label: '出借金额(元）', name: 'amount', index: 'AMOUNT', width: 100 }, 			
			{ label: '出借项目', name: 'projectName', index: 'PROJECTNAME', width: 240 }, 			
			{ label: '渠道编号', name: 'channel', index: 'CHANNEL', width: 120 }			
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
        postData:{channel:$("#channel").text(),startDay:$("#startDay").text(),endDay:$("#endDay").text()},
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
//    bindDate();//绑定时间控件
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		q:{
			key: null
		},
		showList: true,
		title: null,
		channelinvest: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.channelinvest = {};
		},
		update: function (event) {
			var phonetel = getSelectedRow();
			if(phonetel == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(phonetel)
		},
		saveOrUpdate: function (event) {
			var url = vm.channelinvest.phonetel == null ? "../channelinvest/save" : "../channelinvest/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.channelinvest),
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
			var phonetels = getSelectedRows();
			if(phonetels == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: "../channelinvest/delete",
				    data: JSON.stringify(phonetels),
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
		getInfo: function(phonetel){
			$.get("../channelinvest/info/"+phonetel, function(r){
                vm.channelinvest = r.channelinvest;
            });
		},
		exportExcel:function(event){
        	var index = parent.layer.load(1);
    		$.ajax({
    			   type: 'POST',
    			   url:'../channel/genInviteeData', 
    			   timeout:100000, // 超时时间设置，单位毫秒
    			   // beforeSend:ajaxLoading(),//发送请求前打开进度条
    			   contentType: "application/json", // 必须有
    	           dataType: "json", // 表示返回值类型，不必须
    	           data: JSON.stringify({'channel':$("#channel").text(),'startDay' : vm.q.startDay,
   					'endDay' : vm.q.endDay}),
    			   success: function(r){
    				   location.href = "../channel/downloadInviteeData?filePath="+r.filePath+"&fileName=注册数据统计";
//    			    	if (r.code == 0){
//    			    		 	location.href = "../channel/downloadRegistData?filePath="+r.filePath+"&fileName=注册数据统计";
//    			   	 	} else {
//    			   	 	 parent.layer.close(index);
//    			   	 	 		alert(r.msg);
//    			    	}  			    	
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
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
				postData:{'channel':$("#channel").text(),'startDay' : vm.q.startDay,
					'endDay' : vm.q.endDay},
                page:page
            }).trigger("reloadGrid");
		}
	}
});