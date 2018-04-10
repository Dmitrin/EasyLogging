package logging.easyMdc.services.annotationTest.doSomething;

import logging.easyMdc.annotations.EasyMdc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@EasyMdc
public class LogSomething implements DoSomething {

    @Autowired
    private DoSomethingMore logSomethingMore;

    public void doSomething() {
        System.out.println("I'm doing 1st job!");

        logSomethingMore.secondJob();
    }
}
