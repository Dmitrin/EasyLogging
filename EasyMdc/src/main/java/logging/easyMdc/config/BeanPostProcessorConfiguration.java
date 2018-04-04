package logging.easyMdc.config;

import logging.easyMdc.services.EasyMdcAnnotationHandlerBeanPostProcessor;
import logging.easyMdc.services.LogSomething;
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
    public EasyMdcAnnotationHandlerBeanPostProcessor easyMdcAnnotationHandlerBeanPostProcessor() {
        return new EasyMdcAnnotationHandlerBeanPostProcessor();
    }

}
