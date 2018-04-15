package logging.easymdc.config;

import logging.easymdc.services.aspect.EasyMdcAspect;
import logging.easymdc.services.factories.EasyMdcStageFactory;
import logging.easymdc.services.factories.EasyMdcTimeFactory;
import logging.easymdc.services.factories.StageFactory;
import logging.easymdc.services.factories.TimeFactory;
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
    public EasyMdcAspect easyMdcAspect(StageFactory stageFactory, TimeFactory timeFactory) {
        return new EasyMdcAspect(stageFactory, timeFactory);
    }
}
