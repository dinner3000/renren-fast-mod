package com.brh.channel.common.dao;

import org.apache.ibatis.annotations.Param;

/**
 * 获取指定sequence
 * @author lcl
 *
 */
public interface  SeqGenDao {
	long querySeq(@Param("key") String key);
}
