package logging.easyMdc.services.stream;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Component
public class StreamParallelRunner {

    public void runStreamParallel() {
        MDC.put("some", "some string");

        log.error("Start logging!");

        Arrays.asList(1,2,3,4,5).stream()
                .map(integer -> {
                    log.debug("stream-map: {}",integer);
                    log.warn(Thread.currentThread().toString());
                    return integer;
                }).forEach(integer -> {
            log.debug("stream-foreach: {}", integer);
            log.warn(Thread.currentThread().toString());
        });

        Arrays.asList(1,2,3,4,5).parallelStream().
                map(integer -> {
                    log.debug("parallel stream - map: {}", integer);
                    log.warn("Thread: {}", Thread.currentThread().toString());
                    return integer;
                }).forEach(integer -> {
            log.debug("parallel stream - foreach: {}", integer);
            log.warn("Thread: {}", Thread.currentThread().toString());
        });
    }

}
