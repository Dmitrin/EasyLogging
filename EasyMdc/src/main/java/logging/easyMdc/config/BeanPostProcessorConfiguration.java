package logging.easyMdc.config;

import logging.easyMdc.services.factories.EasyMdcStageFactory;
import logging.easyMdc.services.factories.EasyMdcTimeFactory;
import logging.easyMdc.services.factories.StageFactory;
import logging.easyMdc.services.factories.TimeFactory;
import logging.easyMdc.services.postProcessors.EasyMdcAnnotationHandlerBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanPostProcessorConfiguration {

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
