package logging.easyMdc.services.annotationTest.doSomething;

import logging.easyMdc.annotations.EasyMdc;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EasyMdc
public class LogSomethingMore implements DoSomethingMore {

    public void secondJob() {
        System.out.println("I'm doing 2nd job!");
    }

}
