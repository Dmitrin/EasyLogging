package logging.easymdc.services.factories;

import logging.easymdc.config.EasyMdcProperties;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.Signature;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class EasyMdcTimeFactory implements TimeFactory {

    // todo: создать очищение или перезапись значений через какое-либо время
    private Map<String, List<Long>> allMethodsExecutionsTime = new ConcurrentHashMap<>();

    private EasyMdcProperties easyMdcProperties;

    public EasyMdcTimeFactory(EasyMdcProperties easyMdcProperties) {
        this.easyMdcProperties = easyMdcProperties;
    }

    /**
     *
     */
    public Set<String> getAllMethodsNames() {
        return allMethodsExecutionsTime.keySet();
    }


    /**
     *
     */
    public void saveMethodExecutionTimeAspect(Signature signature, Long methodExecutionTime) {

        List<Long> methodTimeList = allMethodsExecutionsTime.computeIfAbsent(createUniqueMethodNameAspect(signature),
                k -> new LinkedList<>());

        methodTimeList.add(methodExecutionTime);

        logSavingExecutionTimeAspect(signature.getName(), methodExecutionTime);

    }

    private String createUniqueMethodNameAspect(Signature signature) {
        return signature.getDeclaringType().getName() + "." + signature.getName();
    }

    private void logSavingExecutionTimeAspect(String methodName, Long methodExecutionTime) {

        if (easyMdcProperties.isEnableAdvancedLogging()) {
            log.debug("Method {} execution time: {}", methodName, methodExecutionTime);
        }

    }

    /**
     *
     */
    public void saveMethodExecutionTime(Method method, Long methodExecutionTime) {

        List<Long> methodTimeList = allMethodsExecutionsTime.computeIfAbsent(createUniqueMethodName(method), k -> new LinkedList<>());

        methodTimeList.add(methodExecutionTime);

        logSavingExecutionTime(method.getName(), methodExecutionTime);

    }

    private String createUniqueMethodName(Method method) {
        return method.getClass().getName() + "." + method.getName();
    }

    private void logSavingExecutionTime(String methodName, Long methodExecutionTime) {
        if (easyMdcProperties.isEnableAdvancedLogging()) {
            log.debug("Method {} execution time: {}", methodName, methodExecutionTime);
        }
    }


    /**
     *
     */
    public void printMethodBenchmarkResultInLog(String method) {
        List<Long> singleMethodCompetitions = allMethodsExecutionsTime.get(method);

        OptionalDouble averageMethodTime = singleMethodCompetitions.stream()
                .mapToLong(e -> e)
                .average();

        if (averageMethodTime.isPresent()) {
            log.debug("Method {} benchmark (ms): {}", method, String.valueOf((averageMethodTime.getAsDouble()) / 1000000));
        } else {
            log.debug("Method {} benchmark (ms): NOT CALCULATED!");
        }
    }


    /**
     *
     */
    public Double getMethodBenchmarkResult(String method) {
        List<Long> singleMethodCompetitions = allMethodsExecutionsTime.get(method);

        OptionalDouble averageMethodTime = singleMethodCompetitions.stream()
                .mapToLong(e -> e)
                .average();

        if (averageMethodTime.isPresent()) {
            return averageMethodTime.getAsDouble() / 1000000;
        } else {
            return null;
        }
    }


    /**
     *
     */
    public List<Long> getAllMethodBenchmarks(String method) {
        return allMethodsExecutionsTime.get(method);
    }


    /**
     *
     */
    public Map<String, List<Long>> getAllData() {
        return allMethodsExecutionsTime;
    }
}
