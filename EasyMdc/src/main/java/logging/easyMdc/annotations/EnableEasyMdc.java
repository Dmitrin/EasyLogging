package logging.easyMdc.annotations;

import logging.easyMdc.config.BeanPostProcessorConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Import(BeanPostProcessorConfiguration.class)
public @interface EnableEasyMdc {
}
