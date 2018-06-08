var p_ciNo = T.p('ciNo');
$(function () {
    if(p_ciNo == 'undefined'){
        alert('客户编号未空！');
    }
    vm.getInfo(p_ciNo);
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
        reset: function () {
            this.q = {
                ciNm: null,
                idNo: null,
                status: "00"
            }
        },
        saveOrUpdate: function (event) {
			
            if (!$('#rrapp').Validform()) {
                return false;
            }
			
			var url = vm.customerInfo.ciNo == null ? "../cust/customerinfo/save" : "../cust/customerinfo/updateInvoice";
			this.$http({
				url: url,
				method: 'POST',
			    body: JSON.stringify(vm.customerInfo)
			}).then(function(r){
				if(r.body.code === 0){
					alert('操作成功', function(index){
						vm.getInfo(vm.customerInfo.ciNo);
					});
				}else{
					alert(r.body.msg);
				}
			});
		},
        getInfo: function(ciNo){
			var index = parent.layer.load(1);
			this.$http.get('../cust/customerinfo/info/'+ciNo).then(function(r){
				filterHtmlResult(r);//过滤并转义返回对象中的所有非法字符
                if(r.body.customerInfo.taxNo==null||r.body.customerInfo.taxNo==""){
                	r.body.customerInfo.taxNo=r.body.customerInfo.orgCode;
                }
                vm.customerInfo = r.body.customerInfo;
                parent.layer.close(index);
            });
			
		}
	}
});