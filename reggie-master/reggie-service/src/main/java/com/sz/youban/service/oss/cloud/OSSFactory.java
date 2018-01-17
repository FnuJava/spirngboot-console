package com.sz.youban.service.oss.cloud;

import com.sz.youban.common.bean.constant.ConfigConstant;
import com.sz.youban.common.bean.constant.Constant;
import com.sz.youban.common.utils.spring.SpringContextUtils;
import com.sz.youban.myinterface.sys.CloudStorageConfig;
import com.sz.youban.myinterface.sys.SysConfigService;

/**
 * 文件上传Factory
 * @author theodo
 * @email 36780272@qq.com
 * @date 2017-03-26 10:18
 */
public final class OSSFactory {
    private static SysConfigService sysConfigService;

    static {
        OSSFactory.sysConfigService = (SysConfigService) SpringContextUtils.getBean("sysConfigService");
    }

    public static CloudStorageService build(){
        //获取云存储配置信息
        CloudStorageConfig config = sysConfigService.getConfigObject(ConfigConstant.CLOUD_STORAGE_CONFIG_KEY, CloudStorageConfig.class);

        if(config.getType() == Constant.CloudService.QINIU.getValue()){
            return new QiniuCloudStorageService(config);
        }else if(config.getType() == Constant.CloudService.ALIYUN.getValue()){
            return new AliyunCloudStorageService(config);
        }else if(config.getType() == Constant.CloudService.QCLOUD.getValue()){
            return new QcloudCloudStorageService(config);
        }else if(config.getType() == Constant.CloudService.NATIVE.getValue()){
        	return new NativeStorageService();
        }
        
        return null;
    }

}
