﻿var validatorRegs = {
    // 客户名称： 汉字，60字
    username :{
// reg:/^[a-zA-Z0-9\u4e00-\u9fa5]{1,60}$/,
    	reg:/^.{1,60}$/,
        msg:'请输入正确客户名称',
        required: true
    },
    // 合作机构名称： 汉字，60字
    agencyname :{
// reg:/^[a-zA-Z0-9\u4e00-\u9fa5]{1,60}$/,
    	reg:/^.{1,60}$/,
        msg:'请输入正确合作机构名称',
        required: true
    },
    // 姓名：汉字、字母、中间点“•”、下方点“.”，其他字符禁止录入。18字符
    fullname :{
        reg:/^([a-zA-Z0-9\u4e00-\u9fa5][·\.a-zA-Z0-9\u4e00-\u9fa5]{1,17})$/,
        msg:'请输入正确的姓名,最长不超过18位',
        required: true
    },

    // 联系人：汉字、字母、中间点“•”、下方点“.”，其他字符禁止录入。18字符
    contacts : {
        reg: /^([a-zA-Z0-9\u4e00-\u9fa5][·\.a-zA-Z0-9\u4e00-\u9fa5]{1,17})$/,
        msg:'请输入正确的联系人,最长不超过18位',
        required: true
    },

    // 证件号：长度18、结尾可以是数字xX
    IDCard : {
        //reg: /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/,// 证件号
        reg: /^.{0,20}$/,
        msg:'请输入20位以内的证件号',
        required: true
    },

    // 纳税人识别号：长度18
    TaxNo : {
// reg: /(^\d{15}$)|(^\d{18}$)/,
    	reg: /(^.{15}$)|(^.{18}$)/,
        msg:'请输入正确的18位纳税人识别号',
        required: false
    },
    OrgCode : {
    //  reg: /^.{0,20}$/,
      reg:/^[0-9a-zA-Z]{18}$/,
      msg: '请输入正确的18位的统一社会信用代码',
      required: false
  },
  OrganCode : {
     // reg: /^.{0,20}$/,
	  reg:/^([A-Za-z0-9]{8}-[A-Za-z0-9]$)/,
      msg: '请输入正确的10位组织机构代码',
      required: false
  },
    // 营业执照号：字母，数字，其他字符禁止录入。长度须为15或18位数字
    BSCard : {
        reg: /(^[a-zA-Z0-9]{15}$)|(^[a-zA-Z0-9]{18}$)/,
        msg:'请输入15或18位数字或字母',
        required: false
    },


    // 手机：11位数字
    tel : {
        reg: /^1[34578]\d{9}$/,
        msg:'请输入11位手机号码',
        required: true
    },

    // 传真号 ：数字11位 、开头可以含有+
    fax : {
        reg: /^((\d{3,4}-)?\d{7,8})$|^(1[0-9][0-9]\d{8})$/,
        // reg: /^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/,
        msg:'请输入正确的传真号码',
        required: false
    },

    // 固话：0开头、可以是010-88888888、0476-8670229、04768670229 格式
    phone: {
        reg: /^0\d{2,3}-?\d{7,8}$/,
        msg: '请输入正确的固定电话号码'
    },

    // 联系地址：汉字、英文、数字 - 长度不超过60
    address : {
// reg: /^[a-zA-Z0-9\u4e00-\u9fa5/-]{1,60}$/,
    	reg: /^.{1,60}$/,
        msg: '请输入正确的联系地址',
        required: true
    },

    // 邮编:6位数字、首位不为0
    postcode : {
        reg: /^[1-9]\d{5}$/,
        msg: '请输入6位开头不为0的邮编号码',
        required: true
    },

    // 申请金额：15-28 0000.00万（最大）
    applymoney : {
        reg: /(^[0-9]\d{0,3}\.[0-9]\d{0,1})$|((^[0-9]\d{0,3})$)/,
        msg: '最大金额为四位数',
        required: true
    },

    // 保险金额、信贷金额：0000.00万（最大)
    insurancemoney : {
        reg: /(^[0-9]\d{0,3}\.[0-9]\d{0,1})$|((^[0-9]\d{0,3})$)/,
        msg: '最大金额为四位数',
        required: true
    },

    // 保险金额、信贷金额：00000000.00元（最大)
    insurancemoneyByOnesPlace : {
        reg: /(^[0-9]\d{0,7}\.[0-9]\d{0,1})$|((^[0-9]\d{0,7})$)/,
        msg: '最大金额为八位数，小数点后保留两位',
        required: true
    },
    // 合同编号：数字、英文 长度不超过30
    contractcode : {
// reg: /^[0-9a-zA-Z\-]{1,30}$/,
// reg: /^[^\u4e00-\u9fa5]{0,36}$/,
    	reg: /^.{0,36}$/,
        msg: '合同编号为长度不超过36位的数字或字母或特殊符号',
        required: true
    },

    // 保单号：数字字母，最大25位
    policycode : {
        reg: /^[0-9a-zA-Z]{1,25}$/,
        msg: '保单号为长度不超过25位的数字或字母',
        required: true
    },

    // 借款合同：数字，最大25位
    moneycontract : {
        reg: /^[0-9]\d{0,24}$/,
        msg: '借款合同为长度不超过25位的数字',
        required: true
    },

    // 贷款期限；10位字符
    moneytime : {
        reg: /^[0-9]\d{0,9}$/,
        msg: '贷款期限最多为10位数字',
        required: true
    },

    // 资产方名称：20位字符
    propertyname : {
// reg: /^[a-zA-Z0-9\u4e00-\u9fa5]\d{0,19}$/,
    	reg: /^.{0,19}$/,
        msg: '资产方名称最多为20位',
        required: true
    },

    // 单纯的不为空
    notNull : {
        reg: /^[\s\S]+$/,
        msg: '不能为空！',
        required: true
    },
    // 产品名称
    prodname : {
// reg:/^[a-zA-Z0-9\u4e00-\u9fa5]{1,60}$/,
    	reg:/^.{1,60}$/,
        msg: '产品名称为长度不超过60位的汉字、英文、或数字',
        required: true
    },
   	// 产品编号
     prodnum : {
        reg: /^[0-9a-zA-Z]{1,30}$/,
        msg: '产品编号为长度不超过30位的数字或字母',
        required: true
    },
    // 利率
    interest : {
        reg:/^(0|([1-9]\d*))(\.\d{1,2})?$/,
        msg: '利率请保留到小数点后两位',
        required: true
    },
    // 部门名称20汉字
    deptName : {
// reg:/^([a-zA-Z0-9\u4e00-\u9fa5][\·\.a-zA-Z0-9\u4e00-\u9fa5]{1,21})$/,
    	reg:/^.{1,21}$/,
        msg:'请输入正确的部门名称,最长不超过20位',
        required: true
    },
  // 意见备注： 汉字，200字符
    commentStr :{
// reg:/^[a-zA-Z0-9\u4e00-\u9fa5]{1,201}$/,
    	reg:/^.{1,201}$/,
        msg:'请输入备注',
        required: true
    },
    // 用户名:字母或数字 长度20
     UserName: {
        reg: /(^[a-zA-Z0-9_]{15}$)|(^[a-zA-Z0-9_]{1,20}$)/,
        msg:'请输入长度不超过20的数字或字母',
        required: true
    },
    // 用户中文名字：字母、数字、汉字 长度50
     CName : {
        reg: /^[a-zA-Z0-9\u4e00-\u9fa5]{1,50}$/,
        msg: '请输入长度不超过50的数字、字母或汉字',
        required: true
    },
    // 密码 数字或字母 长度100
     PassWord: {
        reg: /^[0-9a-zA-Z]{6,100}$/,
        msg: '请输入长度至少6位的数字或字母'
// required: true
    },
    // 邮箱：^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$
	// 只允许英文字母、数字、下划线、英文句号、以及中划线组成
	Email: {
        reg: /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/,
        msg: '请输入正确的邮箱',
        required: true
    },
    // 角色名称：字母、数字、汉字 长度100
     RoleName : {
// reg: /^[a-zA-Z0-9\u4e00-\u9fa5]{1,100}$/,
    	reg: /^.{1,100}$/,
        msg: '请输入长度不超过100的数字、字母或汉字',
        required: true
    },
    // 排序号：5位以内数字
     Sort : {
        reg:/\d{1,5}/,
        msg: '请输入5位以内数字',
        required: true
    },
    // 菜单名称：字母、数字、汉字 长度50
     menuName : {
        reg: /^[a-zA-Z0-9\u4e00-\u9fa5]{1,50}$/,
        msg: '请输入长度不超过50的数字、字母或汉字',
        required: true
    },
    // 菜单URL:数字、字母 冒号 “/” 长度100
	  menuUrlName : {
	  reg: /^[a-zA-Z0-9:\/\.]{1,100}$/,
	  msg: '请输入长度不超过100的菜单URL',
	  required: true
	},
     // 授权标志:字母 冒号 “,” 长度500
     Mark : {
        reg: /^[a-zA-Z\d:,]{1,500}$/,
        msg: '多个用逗号分隔，如：user:list,user:create',
        required: true
    },
    // 参数名：字母、数字、汉字 长度50
     ParaMeter : {
        reg: /^[a-zA-Z0-9\u4e00-\u9fa5]{1,50}$/,
        msg: '请输入长度不超过50的数字、字母或汉字',
        required: true
    },  // 合作机构名称： 汉字，60字
    accountManager :{
// reg:/^[a-zA-Z0-9\u4e00-\u9fa5]{1,60}$/, 客户经理 汉字50
      reg:/^[\u4e00-\u9fa5]{1,50}$/,
      msg:'请输入50位以内汉字',
      required: true
   },  
   // 投保人贷款卡号：数字字母，最大20位
   loanCardNo : {
       reg: /^[0-9a-zA-Z]{1,20}$/,
       msg: '投保人贷款卡号不超过20位的数字或字母',
       required: true
   },
   // 保险年费率 绝对免赔率
   insureFeeRate : {
       reg: /^(0|([1-9]\d{0,10}))(\.\d{0,6})?$/,
       msg: '请输入正确费率',
       required: true
   },
   // 出单机构
   outAgencyId :{
 	reg:/^.{1,60}$/,
     msg:'请输入60位以内出单机构',
     required: true
   },
   // 特别约定
   specialAgr :{
	   reg:/^.{1,2000}$/,
	   msg:'请输入正确特别约定',
	   required: true
   	},
   	//核保人
   	underwriteUser :{
   			reg:/^.{1,20}$/,
   			msg:'请输入20位以核保人',
   			required: true
   		}
};

/**
 * 添加离开焦点事件
 */
$.fn.setBlurEvent = function () {
    var form = $(this);
    $(this).find("[isvalid=yes]").each(function () {
        $(this).blur(function(e){
            $(form).Validform();
        });
    });
};

/**
 * 去掉离开焦点事件
 */
$.fn.unBlurEvent = function () {
    $(this).find("[isvalid=yes]").each(function () {
        $(this).unbind("blur");
    });
};

/**
 * 数据验证完整性
 */
$.fn.Validform = function () {
    var Validatemsg = "";
    var Validateflag = true;
    $(this).find("[isvalid=yes]").each(function () {
    	if($(this).is(":disabled")||!$(this).is(":visible")||$(this).is(":hidden") ){
            return true;
        }
        var checkexpession = $(this).attr("checkexpession");
        var inputname = $(this).attr("placeholder");

        if (checkexpession != undefined) {
            var value = $(this).val();
            if ($(this).hasClass('ui-select')) {
                value = $(this).attr('data-value');
            }

            var regs = validatorRegs[checkexpession];
            if(!regs){
                return true;
            }

            if (regs.required == true && (!value || !value.trim())){
                if (inputname == undefined) {
                    inputname = "";
                }
                Validatemsg = inputname + "不能为空！";
                Validateflag = false;
                ValidationMessage($(this), Validatemsg);
                return false;
            }
            if(value){
                if(!regs.reg.test(value.trim())){
                    Validatemsg = regs.msg;
                    Validateflag = false;
                    ValidationMessage($(this), Validatemsg);
                    return false;
                }
            }
        }
    });

    if ($(this).find("[fieldexist=yes]").length > 0) {
        return false;
    }
    return Validateflag;
};

/**
 * 数据验证完整性
 */
$.fn.Validform2 = function () {
    var Validatemsg = "";
    var Validateflag = true;
    $(this).find("[isvalid=yes]").each(function () {
    	if($(this).is(":disabled")){
            return true;
        }
        var checkexpession = $(this).attr("checkexpession");
        var inputname = $(this).attr("placeholder");

        if (checkexpession != undefined) {
            var value = $(this).val();
            if ($(this).hasClass('ui-select')) {
                value = $(this).attr('data-value');
            }

            var regs = validatorRegs[checkexpession];
            if(!regs){
                return true;
            }

            if (regs.required == true && (!value || !value.trim())){
                if (inputname == undefined) {
                    inputname = "";
                }
                Validatemsg = inputname + "不能为空！";
                Validateflag = false;
                ValidationMessage($(this), Validatemsg);
                return false;
            }
            if(value){
                if(!regs.reg.test(value.trim())){
                    Validatemsg = regs.msg;
                    Validateflag = false;
                    ValidationMessage($(this), Validatemsg);
                    return false;
                }
            }
        }
    });

    if ($(this).find("[fieldexist=yes]").length > 0) {
        return false;
    }
    return Validateflag;
};
// 提示信息
function ValidationMessage(obj, Validatemsg) {
    // console.log(obj.is(":visible") + "|" + obj.css("display") + "|" +
	// obj.is(":hidden"));
    // Validatemsg = Validatemsg + new Date();

    try {
        removeMessage(obj);
        if(!obj.is(":visible") || obj.is(":hidden") || obj.is(":disabled")){
            alert(Validatemsg);
            return;
        }
        $errorInput = obj;
        // obj.focus();
        var $poptip_error = $('<div class="poptip"><span class="poptip-arrow poptip-arrow-top"><em>◆</em></span>' + Validatemsg + '</div>').css("left", obj.offset().left + 'px').css("top", obj.offset().top + obj.parent().height() + 5 + 'px')
        $('body').append($poptip_error);
        if (obj.hasClass('form-control') || obj.hasClass('ui-select')) {
            obj.parent().addClass('has-error');
        }
        if (obj.hasClass('ui-select')) {
            $('.input-error').remove();
        }

        obj.bind("keypress", function () {
            removeMessage(obj);
        });

        obj.change(function () {
            removeMessage(obj);
        });

        if (obj.hasClass('ui-select')) {
            $(document).click(function (e) {
                if (obj.attr('data-value')) {
                    removeMessage(obj);
                }
                e.stopPropagation();
            });
        }
        return false;  
    } catch (e) {
        alert(e)
    }
}
// 移除提示
function removeMessage(obj) {
    /*
	 * if(obj) { obj.parent().removeClass('has-error'); }
	 */
    // $('#rrapp').find("[isvalid=yes]").parent().removeClass('has-error')
    $("*").removeClass('has-error');

    $('.poptip').remove();
    $('.input-error').remove();
}

// 添加表单输入框离开焦点事件
$(function () {
   $('#rrapp').setBlurEvent();
});