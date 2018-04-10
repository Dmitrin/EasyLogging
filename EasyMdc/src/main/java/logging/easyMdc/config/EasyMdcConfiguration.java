package logging.easyMdc.config;

import logging.easyMdc.services.factories.EasyMdcStageFactory;
import logging.easyMdc.services.factories.EasyMdcTimeFactory;
import logging.easyMdc.services.factories.StageFactory;
import logging.easyMdc.services.factories.TimeFactory;
import logging.easyMdc.services.postProcessors.EasyMdcAnnotationHandlerBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EasyMdcConfiguration {

    @Bean
    public EasyMdcProperties easyMdcProperties() {
        return new EasyMdcProperties();
    }

    @Bean
    public StageFactory stageFactory(EasyMdcProperties easyMdcProperties) {
        return new EasyMdcStageFactory(easyMdcProperties);
    }

    @Bean
    public TimeFactory timeFactory(EasyMdcProperties easyMdcProperties) {
        return new EasyMdcTimeFactory(easyMdcProperties);
    }

    @Bean
    public EasyMdcAnnotationHandlerBeanPostProcessor easyMdcAnnotationHandlerBeanPostProcessor() {
        return new EasyMdcAnnotationHandlerBeanPostProcessor();
    }
}
