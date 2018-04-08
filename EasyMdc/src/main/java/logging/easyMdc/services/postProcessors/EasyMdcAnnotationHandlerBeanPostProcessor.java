package logging.easyMdc.services.postProcessors;

import logging.easyMdc.annotations.EasyMdc;
import logging.easyMdc.services.queueMaker.EasyMdcFactory;
import logging.easyMdc.services.queueMaker.EasyMdcTimeFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class EasyMdcAnnotationHandlerBeanPostProcessor implements BeanPostProcessor {

    private Map<String, Class> annotatedBeans = new HashMap<>();

    private EasyMdcFactory easyMdcFactory = new EasyMdcFactory();

    private EasyMdcTimeFactory easyMdcTimeFactory = new EasyMdcTimeFactory();

    @Override

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        Class<?> beanClass = bean.getClass();

         if (beanClass.isAnnotationPresent(EasyMdc.class)) {
            annotatedBeans.put(beanName, beanClass);
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        Class beanClass = annotatedBeans.get(beanName);

        if (beanClass != null) {
            return Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                    easyMdcFactory.logEasyMdcStartingInvoke(method.getName());

                    easyMdcFactory.putStageNameInStack(method.getName());


                    long before = System.nanoTime();

                    Object result = method.invoke(bean, args);

                    long after = System.nanoTime();

                    easyMdcTimeFactory.saveMethodExecutionTime(method, after-before);

                    easyMdcTimeFactory.logMethodBenchmarkResult(method);


                    easyMdcFactory.removeStageNameFromStack();

                    easyMdcFactory.logEasyMdcFinishedInvoke(method.getName());

                    return result;
                }
            });
        }

        return bean;
    }
}
