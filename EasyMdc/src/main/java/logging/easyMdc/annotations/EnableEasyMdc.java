package logging.easyMdc.annotations;

import logging.easyMdc.config.EasyMdcConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Import(EasyMdcConfiguration.class)
public @interface EnableEasyMdc {
}
