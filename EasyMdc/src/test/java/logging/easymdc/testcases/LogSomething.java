package logging.easymdc.testcases;

import logging.easymdc.annotations.EasyMdc;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class LogSomething implements DoSomething {

    private LogSomethingMore logSomethingMore;

    public LogSomething(LogSomethingMore logSomethingMore) {
        this.logSomethingMore = logSomethingMore;
    }

    @EasyMdc
    public void doSomething() {
        log.debug("I'm doing 1st job!");

        logSomethingMore.doSomethingMore();
    }

    @Override
    @EasyMdc
    public void doSomethingWithException() throws IOException {
        log.debug("I'm doing 1st job with Exception!");

        throw new IOException();
    }
}
