package logging.easymdc.testcases;

import logging.easymdc.annotations.EasyMdc;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogSomething implements DoSomething {

    private LogSomethingMore logSomethingMore;

    public LogSomething(LogSomethingMore logSomethingMore) {
        this.logSomethingMore = logSomethingMore;
    }

    @EasyMdc
    public void doSomething() {
        System.out.println("I'm doing 1st job!");

        logSomethingMore.doSomethingMore();
    }
}
