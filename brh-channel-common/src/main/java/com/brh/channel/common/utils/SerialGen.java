package com.brh.channel.common.utils;

import com.brh.channel.common.dao.SeqGenDao;
import com.brh.channel.common.enums.CustomerType;
import com.brh.channel.common.enums.ProductType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service("serialGen")
public class SerialGen{
	@Autowired
	private SeqGenDao seqGenDao;
	private static Logger logger = LoggerFactory
			.getLogger(SerialGen.class);
	/**
	 * 根据序列获取Id
	 */
	public String getSeqId(String sequenceNm) {
		return String.valueOf(seqGenDao.querySeq(sequenceNm));
	}
	/**
	 * 获取客户编号
	 * @return
	 */
	public  String getCiNo(String str){
		StringBuffer bf=new StringBuffer();
		bf.append(str);
		
		bf.append(DateUtils.getTimestamp().substring(0, 8));
		
		StringBuffer sequence = new StringBuffer(getSeqId(CommonConstant.CUSTOMER_SEQ));

		String sequenceSub ="";
		
		//sequence不够六位左补0  
		if(sequence.length() <= 6){
			 for(int j = 0; 0 != 6 -sequence.length();j++){
				 StringBuffer zero = new StringBuffer("0");
				 sequence = zero.append(sequence);
			 }
			 bf.append(sequence);
		}else{   //超过六位
			 sequenceSub = sequence.substring(sequence.length() - 6 , sequence.length());
			 bf.append(sequenceSub);
		}
		
		logger.debug("--------------create ciNo=  "+bf);
		
		return bf.toString();
	}

	/**
	 * 获取客户编号
	 * @return
	 */
	public  String getCiNo(CustomerType customerType){
		if (customerType == null) {
			return getCiNo("");
		}
		return getCiNo(customerType.getType());
	}

	/**
	 * 生成进件编号 无前缀
	 * @return
	 */
	public  String getIncomeNo(){
		StringBuffer bf = new StringBuffer();
		bf.append(DateUtils.getTimestamp());
		bf.append(getSeqId(CommonConstant.INCOME_SEQ));
		return bf.toString();
	}

	/**
	 * 生成进件编号
	 * @return
	 */
	public  String getIncomeNo(String str){
		StringBuffer bf = new StringBuffer();
		bf.append(str);
		bf.append(DateUtils.getTimestamp());
		bf.append(getSeqId(CommonConstant.INCOME_SEQ));
		return bf.toString();
	}

	/**
	 * 根据产品类型生成进件编号
	 * @return
	 */
	public  String getIncomeNo(ProductType productType){
		if (productType == null) {
			return  getIncomeNo("");
		}
		return getIncomeNo(productType.getType());
	}

	/**
	 * 生成信贷项目其他编号
	 * @return
	 */
	public  String getCreditNo(){
		return getSeqId(CommonConstant.CREDIT_SEQ);
	}
	/**
	 * 生成信贷系统序号
	 * @return
	 */
	public  String getSysNo(){
		return getSeqId(CommonConstant.SECQUENCE_SEQ);
	}
}
