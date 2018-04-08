package logging.easyMdc.services.annotationTest;

import logging.easyMdc.config.BeanPostProcessorConfiguration;
import logging.easyMdc.services.doSomething.DoSomething;
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
public class EasyMdcTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void doSomethingTest() {

        applicationContext.getBean(DoSomething.class).doSomething();

        // Проверка корректности удаления
        System.out.println("Diagnostic MDC content CLEAR: " + MDC.get("GlobalMdcStageName"));

        Assert.assertEquals(MDC.get("GlobalMdcStageName"), null);
    }

    @Test
    public void doSomethingTenTimesTest() {

        for (int i = 0; i<10; i++) {
            System.out.println("Try number: " + i);
            applicationContext.getBean(DoSomething.class).doSomething();
        }

        // Проверка корректности удаления
        System.out.println("Diagnostic MDC content CLEAR: " + MDC.get("GlobalMdcStageName"));

        Assert.assertEquals(MDC.get("GlobalMdcStageName"), null);
    }
}