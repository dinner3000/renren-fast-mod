var reg = {
    //1-14
    username : /^[a-zA-Z0-9\u4e00-\u9fa5]{1,60}$/, //客户名称
    //汉字，60字符
    //请输入中文客户名称
    fullname : /^([a-zA-Z0-9\u4e00-\u9fa5][\·\.a-zA-Z0-9\u4e00-\u9fa5]{1,17})$/,//姓名
    //汉字、字母、中间点“•”、下方点“.”，其他字符禁止录入。18字符
    //请输入正确的姓名,最长不超过18位
    contacts : /^([a-zA-Z0-9\u4e00-\u9fa5][\·\.a-zA-Z0-9\u4e00-\u9fa5]{1,17})$/,//联系人
    //汉字、字母、中间点“•”、下方点“.”，其他字符禁止录入。18字符
    //请输入正确的联系人,最长不超过18个字
    IDCard : /^[1-9]\d{16}[\dxX]$/,//证件号
    //长度18、结尾可以是数字xX
    //请输入正确的证件号
    BSCard : /^[a-zA-Z0-9]{15}$/,//营业执照号
    //字母，数字，其他字符禁止录入。长度须为15位数字
    //请输入15位数字或字母
    tel : /^1[34578]\d{9}$/,//手机
    //11位数字
    //请输入11位手机号码
    fax : /^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/,//传真
    //传真号 ：数字11位 、开头可以含有+
    //请输入11位传真号码
    phone: /^0\d{2,3}-?\d{7,8}$/, //固话
    //固话：0开头、可以是010-88888888、0476-8670229、04768670229 格式
    //请输入正确的固定电话号码
    address : /^[a-zA-Z0-9\u4e00-\u9fa5/-]{1,60}$/,//联系地址
    //汉字、英文、数字 -  长度不超过60
    //请输入正确的联系地址
    postcode : /^[1-9]\d{5}$/,//邮编
    //6位数字、首位不为0
    ////请输入6位开头不为0的邮编号码

    //15-28
    applymoney : /^[0-9]\d{0,3}\.[0-9]\d{0,1}$/,//申请金额
    //0000.00万（最大）
    //最大金额为四位数
    insurancemoney : /^[0-9]\d{0,3}\.[0-9]\d{0,1}$/,//保险金额
    //0000.00万（最大)
    //最大金额为四位数
    creditmoney : /^[0-9]\d{0,3}\.[0-9]\d{0,1}$/,//信贷金额
    //0000.00万（最大)
    //最大金额为四位数
    contractcode : /^[0-9a-zA-Z]{1,30}$/,//合同编号
    //数字、英文  长度不超过30
    //合同编号为长度不超过30位的数组或字母
    policycode : /^[0-9a-zA-Z]{1,25}$/, //保单号
    //数字字母，最大25位
    //保单号为长度不超过25位的数字或字母
    moneycontract : /^[0-9]\d{0,24}$/,//借款合同
    //数字，最大25位
    //借款合同为长度不超过25位的数字
    moneytime : /^[0-9]\d{0,9}$/, //贷款期限
    //10位字符
    //贷款期限最多为10位数字
    propertyname : /^[a-zA-Z0-9\u4e00-\u9fa5]\d{0,19}$/, //资产方名称
    //20位字符
    //资产方名称最多为20位
};
/*
    .getElementsByName() 通过name属性获取元素
    .focus() 使元素获得焦点
    .blur() 使元素失去焦点

 */
var arrObj = [
    $('input[name=username]'),
    $('input[name=fullname]'),
    $('input[name=contacts]'),
    $('input[name=IDCard]'),
    $('input[name=BSCard]'),
    $('input[name=tel]'),
    $('input[name=fax]'),
    $('input[name=phone]'),
    $('input[name=address]'),
    $('input[name=postcode]'),
    $('input[name=applymoney]'),
    $('input[name=insurancemoney]'),
    $('input[name=creditmoney]'),
    $('input[name=contractcode]'),
    $('input[name=policycode]'),
    $('input[name=moneycontract]'),
    $('input[name=moneytime]'),
    $('input[name=propertyname]')
];
/*
$.each(arrObj,function(){
    $(this).blur(function(e){
        if (!$(this).val())return;
        //if ($(this).val().length == 0)return;
        if (!reg[$(this).attr('name')].test($(this).val())) {
            $(this).focus();
            //$(this).css('border','1px solid red');
            $(this).next().css('display','block');
        }else{
            $(this).next().css('display','none');
        }
    })
});*/


