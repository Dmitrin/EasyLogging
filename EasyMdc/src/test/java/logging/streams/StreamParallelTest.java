package logging.streams;

import logging.streams.config.StreamParallelTestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = StreamParallelTestConfig.class)
public class StreamParallelTest {

    @Autowired
    private StreamParallelRunner streamParallelRunner;

    @Test
    public void testStream() {
        streamParallelRunner.runStreamParallel();
    }
}
