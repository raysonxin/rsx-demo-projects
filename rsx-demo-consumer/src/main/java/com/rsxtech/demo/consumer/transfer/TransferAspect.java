package com.rsxtech.demo.consumer.transfer;

import com.rsxtech.demo.consumer.controller.dto.command.HelloRequestCmd;
import com.rsxtech.demo.consumer.transfer.gray.PercentGrayModel;
import com.rsxtech.demo.consumer.transfer.gray.TransferGray;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 切面实现
 *
 * @author raysonxin
 * @since 2021/1/19
 */
@Component
@Slf4j
@Aspect
public class TransferAspect {

    @Pointcut("@within(com.rsxtech.demo.consumer.transfer.EnableTransfer)||@annotation(com.rsxtech.demo.consumer.transfer.EnableTransfer)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 请求类名
        String className = joinPoint.getTarget().getClass().getName();
        // 请求方法名
        String methodName = joinPoint.getSignature().getName();
        // 返回值类型
        Class<?> returnType = ((MethodSignature) joinPoint.getSignature()).getMethod().getReturnType();
        // 方法入参
        Object[] args = joinPoint.getArgs();

        try {
            if (this.needTransfer(args)) {
                return this.invokeProxy(className, methodName, args, returnType);
            }
        } catch (Exception ex) {
            log.error("TransferAspect execute {}#{} transfer failed", className, methodName, ex);
        }
        // 如果无灰度或者遇到异常了，还是使用旧的处理方式，新接口不行，还得保证系统可用。
        return joinPoint.proceed();
    }

    /**
     * 灰度判断逻辑
     *
     * @return true-命中灰度，执行转发；false-不转发
     */
    private boolean needTransfer(Object[] args) {
        // 这里先简单处理，实际情况下需要根据参数特点，编写一个方法解析灰度参数。
        // 比如我们的老系统，所有接口都包含一个用户请求对象，就可以找个找个参数，然后执行灰度判断。
        HelloRequestCmd cmd = (HelloRequestCmd) args[0];
        PercentGrayModel percentGrayModel = TransferGray.grayConfig;
        return percentGrayModel.hitGray(cmd.getUserId());
    }

    private Object invokeProxy(String className, String methodName, Object[] args, Class<?> returnType) throws Exception {

        Object bean = RegistryUtils.getBeanObject(className);
        if (null == bean) {
            throw new NoSuchMethodException("no bean found");
        }

        Method targetMethod = null;
        Method[] methods = bean.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                targetMethod = method;
                break;
            }
        }
        if (null == targetMethod) {
            throw new NoSuchMethodException("no matched method found");
        }

        Object result = null;
        Class<?>[] targetParamTypes = targetMethod.getParameterTypes();
        if (targetParamTypes.length == 0) {
            result = targetMethod.invoke(bean);
        } else if (targetParamTypes.length == args.length) {
            Object[] targetParams = new Object[args.length];
            for (int i = 0; i < args.length; i++) {
                // 在这里进行参数转换
                targetParams[i] = ObjectConverter.convert(args[i], targetParamTypes[i]);
            }

            result = targetMethod.invoke(bean, targetParams);
        } else {
            throw new NoSuchMethodException("no matched method found");
        }

        // 返回前，需要根据returnType进行类型转换
        return ObjectConverter.convert(result, returnType);
    }
}
