package logging.easymdc.testcases;

import logging.easymdc.annotations.EasyMdc;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogSomethingMore implements DoSomethingMore{

    @EasyMdc
    public void DoSomethingMore() {
        System.out.println("I'm doing 2nd job!");
    }
}
