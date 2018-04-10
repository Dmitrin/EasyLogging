package logging.easyMdc.services.factories;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface TimeFactory {

    Set<String> getAllMethodsNames();

    void saveMethodExecutionTime(Method method, Long methodExecutionTime);

    void printMethodBenchmarkResultInLog(String method);

    Double getMethodBenchmarkResult(String method);

    List<Long> getAllMethodBenchmarks(String method);

    Map<String, List<Long>> getAllData();
}