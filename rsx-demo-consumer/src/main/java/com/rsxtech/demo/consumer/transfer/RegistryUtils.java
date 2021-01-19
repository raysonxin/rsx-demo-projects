package com.rsxtech.demo.consumer.transfer;

import com.rsxtech.demo.api.GreetingService;
import com.rsxtech.demo.consumer.controller.GreetingController;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 模拟注册中心及服务对象存储
 *
 * @author raysonxin
 * @since 2021/1/19
 */
@Slf4j
@Component
public class RegistryUtils implements ApplicationContextAware {

    /**
     * 映射
     */
    private static Map<String, ServiceDesc> serviceMap = new HashMap<>(1);


    static {
        // 初始化
        serviceMap.put(GreetingController.class.getName(), new ServiceDesc(GreetingService.class, null));
    }

    /**
     * 根据请求源类名获取目标服务的Bean对象
     *
     * @param sourceClass 源类名
     * @return bean
     */
    public static Object getBeanObject(String sourceClass) {
        if (serviceMap.containsKey(sourceClass)) {
            return serviceMap.get(sourceClass).getServiceBeanObject();
        }
        return null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        for (Map.Entry<String, ServiceDesc> entry : serviceMap.entrySet()) {
            try {
                // 按照类型获取bean对象，并设置到注册中心。
                Object bean = applicationContext.getBean(entry.getValue().getServiceType());
                entry.getValue().setServiceBeanObject(bean);
            } catch (Exception ex) {
                log.error("RegistryUtils getBean for {}->{} failed.", entry.getKey(), entry.getValue().getServiceType().getName());
            }
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ServiceDesc {

        /**
         * 服务类型
         */
        private Class<?> serviceType;

        /**
         * 服务对象实例
         */
        private Object serviceBeanObject;

    }
}
