package com.brh.channel.api.dao;

import com.brh.channel.api.entity.TokenEntity;
import com.brh.channel.common.dao.BaseDao;

/**
 * 用户Token
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-23 15:22:07
 */
public interface TokenDao extends BaseDao<TokenEntity> {
    
    TokenEntity queryTokenByUserId(Long userId);

    TokenEntity queryByUserToken(String token);
	
}
