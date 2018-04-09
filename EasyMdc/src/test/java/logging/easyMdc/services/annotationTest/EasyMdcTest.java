package logging.easyMdc.services.annotationTest;

import logging.easyMdc.config.BeanPostProcessorConfiguration;
import logging.easyMdc.services.doSomething.DoSomething;
import logging.easyMdc.services.factories.TimeFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static logging.easyMdc.config.Constants.MDC_THE_ONLY_ONE_STAGE_NAME;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {BeanPostProcessorConfiguration.class})
@Slf4j
public class EasyMdcTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private DoSomething doSomething;

    @Autowired
    private TimeFactory timeFactory;

    @Test
    public void doSomethingTest() {

        applicationContext.getBean(DoSomething.class).doSomething();

        Assert.assertEquals(MDC.get(MDC_THE_ONLY_ONE_STAGE_NAME), null);
    }

    @Test
    public void doSomethingTenTimesWithApplicationContextTest() {

        for (int i = 0; i < 10; i++) {
            System.out.println("Try number: " + i);
            applicationContext.getBean(DoSomething.class).doSomething();
        }

        Assert.assertEquals(MDC.get(MDC_THE_ONLY_ONE_STAGE_NAME), null);
    }

    @Test
    public void doSomethingTenTimesWithBean() {

        for (int i = 1; i < 10; i++) {
            log.debug("Try number: {}", i);
            doSomething.doSomething();
        }

        log.info("Benchmarked methods:");
        for (String methodName : timeFactory.getAllMethodsNames()) {
            log.debug("--> MethodName: {}", methodName);

            for (Long time : timeFactory.getAllMethodBenchmarks(methodName)) {
                log.debug("----> Time (ms): {}", time);
            }

            log.info("Average time for method (ns): {} is: {}", methodName, timeFactory.getMethodBenchmarkResult(methodName));
        }

//        timeFactory.getAllData();

        Assert.assertEquals(MDC.get(MDC_THE_ONLY_ONE_STAGE_NAME), null);
    }
}