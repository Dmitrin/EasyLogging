package logging.easyMdc.services.doSomething;

import logging.easyMdc.annotations.EasyMdc;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@EasyMdc
public class LogSomething implements DoSomething {

//    @Autowired
//    private LogSomethingMore logSomethingMore;

    public void doSomething() {
        System.out.println("Diagnostic MDC content : " + MDC.getCopyOfContextMap());
        System.out.println("I'm doing something!");
//        logSomethingMore.doSomethingMore();
    }
}
