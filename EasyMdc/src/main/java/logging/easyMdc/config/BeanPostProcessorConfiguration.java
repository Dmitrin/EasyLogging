package logging.easyMdc.config;

import logging.easyMdc.services.doSomething.LogSomething;
import logging.easyMdc.services.doSomething.LogSomethingMore;
import logging.easyMdc.services.factories.EasyMdcStageFactory;
import logging.easyMdc.services.factories.EasyMdcTimeFactory;
import logging.easyMdc.services.factories.StageFactory;
import logging.easyMdc.services.factories.TimeFactory;
import logging.easyMdc.services.postProcessors.EasyMdcAnnotationHandlerBeanPostProcessor;
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
    public StageFactory stageFactory() {
        return new EasyMdcStageFactory();
    }

    @Bean
    public TimeFactory timeFactory() {
        return new EasyMdcTimeFactory();
    }

    @Bean
    public EasyMdcAnnotationHandlerBeanPostProcessor easyMdcAnnotationHandlerBeanPostProcessor() {
        return new EasyMdcAnnotationHandlerBeanPostProcessor();
    }
}
