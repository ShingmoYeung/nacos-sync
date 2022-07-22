package com.alibaba.nacossync.nacos;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA 2022.
 * FileName:  InstanceService.java
 *
 * @Author: Yang Chengwu
 * @Date: 2022/7/22 10:56
 * @Version: 1.0
 * To change this template use File Or Preferences | Settings | Editor | File and Code Templates.
 * File Description:
 */
@Slf4j
@Component
public class InstanceService implements ApplicationRunner {
    /**
     * Nacos服务地址
     */
    @Value(value = "${spring.cloud.nacos.server-addr:http://180.76.189.174:18849}")
    private String nacosServerAddr;
    /**
     * Nacos命名空间
     */
    @Value(value = "${spring.cloud.nacos.discovery.namespace:nacos-sync-prod}")
    private String nacosNamespace;
    /**
     * Nacos注册应用名称
     */
    @Value(value = "${spring.application.name:nacos-sync-server}")
    private String serviceName;
    /**
     * Nacos注册应用IP地址
     */
    @Value(value = "${server.ip-address:127.0.0.1}")
    private String ip;
    /**
     * Nacos注册应用端口号
     */
    @Value(value = "${server.port:8083}")
    private int port;

    /**
     * 启动加载服务
     *
     * @param args args
     * @throws Exception Exception
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            Properties properties = new Properties();
            properties.setProperty("serverAddr", this.nacosServerAddr);
            properties.setProperty("namespace", this.nacosNamespace);
            NamingService namingService = NamingFactory.createNamingService(properties);
            Instance instance = new Instance();
            instance.setServiceName(this.serviceName);
            instance.setIp(this.ip);
            instance.setPort(this.port);
            instance.setWeight(1.0);
            // 元数据
            Map<String, String> instanceMeta = Maps.newHashMap();
            instanceMeta.put("preserved.register.source", "NACOS_SYNC");
            instance.setMetadata(instanceMeta);
            namingService.registerInstance(this.serviceName, instance);
            log.info("Data Open service registration Nacos succeeded!");
        } catch (NacosException e) {
            log.error("The data Open service failed to register Nacos! {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}