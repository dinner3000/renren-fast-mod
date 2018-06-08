$(function () {
	 $("#jqGrid").jqGrid({
	        url: '../channel/singleChannelInfo',
	        datatype: "json",
	        colModel: [		
				{ label: '渠道编号', name: 'channel', index: 'CHANNEL', width: 120 , key: true},
            { label: '渠道名称', name: 'channelName', index: 'CHANNELNAME', width: 120 },	
			{ label: '注册数(人)', name: 'registNum', index: 'REGISTNUM', width: 120 }, 		
			{ label: '出借人数', name: 'investUserTotal', index: 'INVESTUSERTOTAL', width: 120 },
			{ label: '出借笔数(笔)', name: 'timesTotal', index: 'TIMESTOTAL', width: 120 },
			{ label: '出借金额额(元)', name: 'amountTotal', index: 'AMOUNTTOTAL', width: 120 }
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
	        postData:{startDay:$("#startDay").text(),endDay:$("#endDay").text()},
	        gridComplete:function(){
	        	//隐藏grid底部滚动条
	        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
	        }
	    });

	bindDate();//绑定时间控件
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		q:{
			key: null,
			startDay : ' ',
			endDay : ' '
		},
		showList: true,
		title: null,
		channelStatistics: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.channelStatistics = {};
		},
		update: function (event) {
			var investsum = getSelectedRow();
			if(investsum == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(investsum)
		},
		saveOrUpdate: function (event) {
			var url = vm.channelStatistics.investsum == null ? "../channelstatistics/save" : "../channelstatistics/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.channelStatistics),
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
			var investsums = getSelectedRows();
			if(investsums == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: "../channelstatistics/delete",
				    data: JSON.stringify(investsums),
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
		getInfo: function(channel){
			$.get("../channelstatistics/info/"+investsum, function(r){
                vm.channelStatistics = r.channelStatistics;
            });
		},
		getRegistList: function(){
			var channel = getSelectedRow();
			if(channel == null){
				return ;
			}
			var registurl = "channelregist.html?channel="+channel+"&startDay="+vm.q.startDay+"&endDay="+vm.q.endDay;
			window.location.href=registurl;
		},
		getInvestList: function(){
			var channel = getSelectedRow();
			if(channel == null){
				return ;
			}
			var registurl = "channelinvest.html?channel="+channel+"&startDay="+vm.q.startDay+"&endDay="+vm.q.endDay;
			window.location.href=registurl;
		},
		getInviteeList: function(){
			var channel = getSelectedRow();
			if(channel == null){
				return ;
			}
			var registurl = "channelinvitee.html?channel="+channel+"&startDay="+vm.q.startDay+"&endDay="+vm.q.endDay;
			window.location.href=registurl;
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
				postData:{'key': vm.q.key,
					'startDay' : vm.q.startDay,
					'endDay' : vm.q.endDay},
                page:page
            }).trigger("reloadGrid");
		}
	}
});