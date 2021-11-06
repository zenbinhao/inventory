package com.inventory.nike.config;/*
 * @Author: zeng
 * @Data: 2021/10/27 18:35
 * @Description: TODO
 */

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

@Configuration
public class UploadFileConfig {

    @Value("${file.uploadFolder}")
    private String uploadFolder;

    @Value("${file.maxSize}")
    private String maxSize;

    @Value("${file.countSize}")
    private String countSize;

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setLocation(uploadFolder);
        //文件最大
        factory.setMaxFileSize(DataSize.parse(maxSize));
        // 设置总上传数据总大小
        factory.setMaxRequestSize(DataSize.parse(countSize));
        return factory.createMultipartConfig();
    }
}
