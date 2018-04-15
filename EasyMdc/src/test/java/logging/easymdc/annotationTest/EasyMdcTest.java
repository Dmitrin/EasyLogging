package logging.easymdc.annotationTest;

import logging.easymdc.testcases.DoSomething;
import logging.easymdc.testconfig.TestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
@Slf4j
public class EasyMdcTest {

    @Autowired
    private DoSomething doSomething;

    @Test
    public void doSomethingTest() {

        doSomething.doSomething();

    }

    @Test
    public void doSomethingTenTimesWithBean() {

        for (int i = 1; i < 10; i++) {
            log.debug("Try number: {}", i);
            doSomething.doSomething();
        }

    }

//    @Test
//    public void doSomethingTest() {
//
//        applicationContext.getBean(DoSomething.class).doSomething();
//
//        Assert.assertEquals(MDC.get(easyMdcProperties.getMdcTheOnlyOneStageName()), null);
//    }
//
//    @Test
//    public void doSomethingTenTimesWithApplicationContextTest() {
//
//        for (int i = 0; i < 10; i++) {
//            System.out.println("Try number: " + i);
//            applicationContext.getBean(DoSomething.class).doSomething();
//        }
//
//        Assert.assertEquals(MDC.get(easyMdcProperties.getMdcTheOnlyOneStageName()), null);
//    }
//
//    @Test
//    public void doSomethingTenTimesWithBean() {
//
//        for (int i = 1; i < 10; i++) {
//            log.debug("Try number: {}", i);
//            doSomething.doSomething();
//        }
//
//        log.info("Benchmarked methods:");
//        for (String methodName : timeFactory.getAllMethodsNames()) {
//            log.debug("--> MethodName: {}", methodName);
//
//            for (Long time : timeFactory.getAllMethodBenchmarks(methodName)) {
//                log.debug("----> Time (ms): {}", time);
//            }
//
//            log.info("Average time for method (ns): {} is: {}", methodName, timeFactory.getMethodBenchmarkResult(methodName));
//        }
//
////        timeFactory.getAllData();
//
//        Assert.assertEquals(MDC.get(easyMdcProperties.getMdcTheOnlyOneStageName()), null);
//    }
}