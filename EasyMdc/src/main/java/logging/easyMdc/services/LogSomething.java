package logging.easyMdc.services;

import logging.easyMdc.annotations.EasyMdc;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

@Slf4j
@EasyMdc
@Service
public class LogSomething implements DoSomething {

    public void doSomething() {
        log.debug("1");

        System.out.println("Diagnostic MDC content : " + MDC.get("it works"));

        System.out.println("Did something!");

        log.debug("2");
    }
}
