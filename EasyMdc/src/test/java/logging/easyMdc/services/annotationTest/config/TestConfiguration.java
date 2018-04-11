package logging.easyMdc.services.annotationTest.config;

import logging.easyMdc.annotations.EnableEasyMdc;
import logging.easyMdc.services.annotationTest.doSomething.LogSomething;
import logging.easyMdc.services.annotationTest.doSomething.LogSomethingMore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableEasyMdc
public class TestConfiguration {

    @Bean
    public LogSomething logSomething() {
        return new LogSomething();
    }

    @Bean
    public LogSomethingMore logSomethingMore() {
        return new LogSomethingMore();
    }

}
