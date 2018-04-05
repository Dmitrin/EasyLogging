package logging.easyMdc.services.doSomething;

import logging.easyMdc.annotations.EasyMdc;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

@Slf4j
@EasyMdc
public class LogSomethingMore {

    public void doSomethingMore() {
        System.out.println("Diagnostic MDC content : " + MDC.getCopyOfContextMap());
        System.out.println("I'm doing something more!");
    }
}
