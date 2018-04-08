package logging.easyMdc.services.queueMaker;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class EasyMdcFactory {

    private static final String MDC_THE_ONLY_ONE_STAGE_NAME = "GlobalMdcStageName";

    private static final int LOG_PREFIX_LENGTH = 2;

    // todo: concurrent?
    private static LinkedList<String> stageNames = new LinkedList<>();

    private static LinkedList<String> stageNamesWithPrefix = new LinkedList<>();

    private Map<String, List<Long>> methodsCompetitions = new ConcurrentHashMap<>();

    /**
     *
     */
    public void saveMethodCompetitionTime(Method method, Long competitionTime) {

        List<Long> singleMethodCompetitions = methodsCompetitions.get(getMethodName(method));

        if (singleMethodCompetitions == null) {
            singleMethodCompetitions = new LinkedList<>();
            methodsCompetitions.put(getMethodName(method), singleMethodCompetitions);
        }

        if (competitionTime != 0) {
            singleMethodCompetitions.add(competitionTime);
        }

        log.debug("Current Method competition benchmark: {}", competitionTime);
    }

    private String getMethodName(Method method) {
        return method.getClass().getName() + "." + method.getName();
    }

    /**
     *
     */
    public OptionalDouble getMethodBenchmarkResult(Method method) {
        List<Long> singleMethodCompetitions = methodsCompetitions.get(getMethodName(method));

        OptionalDouble averageTime = singleMethodCompetitions.stream()
                                       .mapToLong(e -> e)
                                       .average();

        log.debug(String.valueOf(averageTime.getAsDouble()));

        return averageTime;
    }



    /**
     *
     */
    public void putStageNameInStack(String stageName) {
        // Add Stage without Prefix
        if (stageNames.size() == 0) {

            addNewStageNameToStackAndPushMDC(stageName, stageName);

        // Add Stage with Prefix
        } else {
            String stageNameWithPrefix = generatePrefixForNewStageName() + stageName;

            addNewStageNameToStackAndPushMDC(stageName, stageNameWithPrefix);
        }
    }

    private void addNewStageNameToStackAndPushMDC(String stageName, String stageNameWithPrefix) {
        stageNames.add(stageName);
        stageNamesWithPrefix.add(stageNameWithPrefix);

        logAddingNewStage(stageName, stageNameWithPrefix);

        MDC.put(MDC_THE_ONLY_ONE_STAGE_NAME, stageName);
    }

    private void logAddingNewStage(String stageName, String stageNameWithPrefix) {
        log.debug("+++ New StageName: {}", stageName);
        log.debug("+++ New StageName with prefix: {}", stageNameWithPrefix);

        log.debug("+++ New StackStages: {}", stageNames);
        log.debug("+++ New StackPrefixes: {}", stageNamesWithPrefix);
    }

    private String generatePrefixForNewStageName() {
        String prefix = "";

        for (String stageName : stageNames) {
            prefix = addPrefixToString(prefix, stageName);
        }

        return prefix;
    }

    private String addPrefixToString(String prefix, String stageName) {
        if (LOG_PREFIX_LENGTH >= 0 && stageName.length() < LOG_PREFIX_LENGTH) {
            prefix = prefix + stageName + ".";
        } else {
            prefix = prefix + stageName.substring(0, LOG_PREFIX_LENGTH) + ".";
        }

        return prefix;
    }



    /**
     *
     */
    public void removeStageNameFromStack() {
        if (stageNames.size() <= 1) {
            removeLastStageNameAndLog();

            // Полное удаление, если не осталось элементов
            MDC.remove(MDC_THE_ONLY_ONE_STAGE_NAME);
        } else {
            removeLastStageNameAndLog();

            // Last element in Stack is the King now!!! God bless!!!
            MDC.put(MDC_THE_ONLY_ONE_STAGE_NAME, stageNames.getLast());
        }
    }

    private void removeLastStageNameAndLog() {
        log.debug("--- REMOVING from StackStages: {}", stageNames.removeLast());
        log.debug("--- REMOVING from StackStages: {}", stageNamesWithPrefix.removeLast());

        log.debug("--- New StackStages: {}", stageNames);
        log.debug("--- New StackPrefixes: {}", stageNamesWithPrefix);
    }


    /**
     *
     */
    public void logEasyMdcStartingInvoke() {
        log.debug("-------- Starting Invoke --------");
    }

    /**
     *
     */
    public void logEasyMdcFinishedInvoke() {
        log.debug("-------- Finished Invoke --------");
    }
}
