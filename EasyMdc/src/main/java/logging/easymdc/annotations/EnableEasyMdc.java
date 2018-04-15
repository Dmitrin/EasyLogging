package logging.easymdc.annotations;

import logging.easymdc.config.EasyMdcConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Import(EasyMdcConfiguration.class)
@EnableAspectJAutoProxy
public @interface EnableEasyMdc {
}
