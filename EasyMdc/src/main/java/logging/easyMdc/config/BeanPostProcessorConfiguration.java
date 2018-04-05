package logging.easyMdc.config;

import logging.easyMdc.services.doSomething.LogSomethingMore;
import logging.easyMdc.services.postProcessors.EasyMdcAnnotationHandlerBeanPostProcessor;
import logging.easyMdc.services.doSomething.LogSomething;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class BeanPostProcessorConfiguration {

    @Bean
    public LogSomething logSomething() {
        return new LogSomething();
    }

    @Bean
    public LogSomethingMore logSomethingMore() {
        return new LogSomethingMore();
    }

    @Bean
    public EasyMdcAnnotationHandlerBeanPostProcessor easyMdcAnnotationHandlerBeanPostProcessor() {
        return new EasyMdcAnnotationHandlerBeanPostProcessor();
    }

}
