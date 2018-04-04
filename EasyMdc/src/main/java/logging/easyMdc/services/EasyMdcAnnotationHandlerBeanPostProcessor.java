package logging.easyMdc.services;

import logging.easyMdc.annotations.EasyMdc;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class EasyMdcAnnotationHandlerBeanPostProcessor implements BeanPostProcessor {

    private Map<String, Class> map = new HashMap<>();

    @Override

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        Class<?> beanClass = bean.getClass();

        if (beanClass.isAnnotationPresent(EasyMdc.class)) {
            map.put(beanName, beanClass);
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        Class beanClass = map.get(beanName);

        if (beanClass != null) {
            return Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                    System.out.println("===> Started!");

                    MDC.put("it works", "yea!");
                    long before = System.nanoTime();

                    Object result = method.invoke(bean, args);

                    long after = System.nanoTime();
                    System.out.println("Method work time: " + String.valueOf(after-before));
                    MDC.remove("it works");

                    System.out.println("<=== Finished!");

                    return result;
                }
            });
        }

        return bean;
    }
}
