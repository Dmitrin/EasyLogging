package logging.easyMdc.services.queueMaker;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.concurrent.ConcurrentHashMap;

import static logging.easyMdc.config.Constants.ENABLE_ADVANCED_LOGGING;

@Slf4j
public class EasyMdcTimeFactory {

    // todo: создать очищение или перезапись значений через какое-либо время
    private Map<String, List<Long>> allMethodsExecutionsTime = new ConcurrentHashMap<>();


    /**
     *
     */
    public void saveMethodExecutionTime(Method method, Long methodExecutionTime) {

        List<Long> singleMethodExecutionsTimeList = allMethodsExecutionsTime.computeIfAbsent(getMethodName(method), k -> new LinkedList<>());

        singleMethodExecutionsTimeList.add(methodExecutionTime);

        logSavingExecutionTime(method.getName(), methodExecutionTime);

    }

    private String getMethodName(Method method) {
        return method.getClass().getName() + "." + method.getName();
    }

    private void logSavingExecutionTime(String methodName, Long methodExecutionTime) {
        if (ENABLE_ADVANCED_LOGGING) {
            log.debug("Method {} execution time: {}", methodName, methodExecutionTime);
        }
    }

    /**
     *
     */
    public void logMethodBenchmarkResult(Method method) {
        List<Long> singleMethodCompetitions = allMethodsExecutionsTime.get(getMethodName(method));

        OptionalDouble averageMethodTime = singleMethodCompetitions.stream()
                .mapToLong(e -> e)
                .average();

        if (averageMethodTime.isPresent()) {
            log.debug("Method {} benchmark (ms): {}", method.getName(), String.valueOf((averageMethodTime.getAsDouble()) / 1000000));
        } else {
            log.debug("Method {} benchmark (ms): NOT CALCULATED!");
        }
    }

}
