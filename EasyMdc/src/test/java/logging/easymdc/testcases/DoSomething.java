package logging.easymdc.testcases;

import java.io.IOException;

public interface DoSomething {

    /**
     * Method for EasyMDC demonstration
     */
    void doSomething();

    void doSomethingWithException() throws IOException;

}
