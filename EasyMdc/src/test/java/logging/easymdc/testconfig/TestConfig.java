package logging.easymdc.testconfig;

import logging.easymdc.annotations.EnableEasyMdc;
import logging.easymdc.testcases.LogSomething;
import logging.easymdc.testcases.LogSomethingMore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableEasyMdc
@ComponentScan("logging.easymdc")
public class TestConfig {

    @Bean
    public LogSomething logSomething(LogSomethingMore logSomethingMore) {
        return new LogSomething(logSomethingMore);
    }

    @Bean
    public LogSomethingMore logSomethingMore() {
        return new LogSomethingMore();
    }

}
