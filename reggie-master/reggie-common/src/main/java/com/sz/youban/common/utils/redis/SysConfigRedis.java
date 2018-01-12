package com.sz.youban.common.utils.redis;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



/**
 * 系统配置Redis
 *
 * @author theodo
 * @email 36780272@qq.com
 * @date 2017/7/18 21:08
 */
@Component
public class SysConfigRedis {
    @Autowired
    private RedisUtils redisUtils;

    public void saveOrUpdate(String key,Object config) {
        if(config == null){
            return ;
        }
        redisUtils.set(key, config);
    }

    public void delete(String configKey) {
        String key = RedisKeys.getSysConfigKey(configKey);
        redisUtils.delete(key);
    }

    public Object get(String configKey,Class<?> clas){
        String key = RedisKeys.getSysConfigKey(configKey);
        return redisUtils.get(key, clas);
    }
}
