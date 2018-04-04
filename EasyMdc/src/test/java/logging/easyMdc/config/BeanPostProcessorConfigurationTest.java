package logging.easyMdc.config;

import logging.easyMdc.services.DoSomething;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {BeanPostProcessorConfiguration.class})
public class BeanPostProcessorConfigurationTest {

    @Autowired
    private ApplicationContext applicationContext;


    @Test
    public void logSomething() {
    }

    @Test
    public void easyMdcAnnotationHandlerBeanPostProcessor() {

        applicationContext.getBean(DoSomething.class).doSomething();

        // Проверка корректности удаления
        System.out.println("Diagnostic MDC content CLEAR: " + MDC.get("it works"));

        Assert.assertEquals(MDC.get("it works"), null);
    }

    @Test
    public void AddAndRemoveMdcTenTimes() {

        for (int i = 0; i<10; i++) {
            System.out.println("Try number: " + i);
            applicationContext.getBean(DoSomething.class).doSomething();
        }

        // Проверка корректности удаления
        System.out.println("Diagnostic MDC content CLEAR: " + MDC.get("it works"));

        Assert.assertEquals(MDC.get("it works"), null);
    }
}