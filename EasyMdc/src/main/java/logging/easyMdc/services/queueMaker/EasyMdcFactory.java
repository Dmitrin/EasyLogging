package logging.easyMdc.services.queueMaker;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import java.util.LinkedList;

import static logging.easyMdc.config.Constants.*;

@Slf4j
public class EasyMdcFactory {

    // todo: concurrent?
    private static LinkedList<String> stageNames = new LinkedList<>();

    private static LinkedList<String> stageNamesWithPrefix = new LinkedList<>();


    /**
     *
     */
    public void putStageNameInStack(String stageName) {
        String stageNameWithPrefix = generatePrefixForNewStageName() + stageName;

        stageNames.add(stageName);
        stageNamesWithPrefix.add(stageNameWithPrefix);

        MDC.put(MDC_THE_ONLY_ONE_STAGE_NAME, stageNameWithPrefix);

        logAddingNewStage(stageName, stageNameWithPrefix);
    }

    private void logAddingNewStage(String stageName, String stageNameWithPrefix) {
        if (ENABLE_ADVANCED_LOGGING) {
            log.debug("+++ New stageName: {}", stageName);
            log.debug("+++ New stageNameWithPrefix: {}", stageNameWithPrefix);

            log.debug("+++ stages now: {}", stageNames);
            log.debug("+++ stages WithPrefix now: {}", stageNamesWithPrefix);
        }
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
        removeLastStageNameAndLog();

        // Если ещё остались stages
        if (stageNames.size() != 0) {
            // The last stage is the King now! God bless the stage!
            MDC.put(MDC_THE_ONLY_ONE_STAGE_NAME, stageNames.getLast());
        }
    }

    private void removeLastStageNameAndLog() {
        if (ENABLE_ADVANCED_LOGGING) {
            log.debug("--- REMOVING from stageNames: {}", stageNames.removeLast());
            log.debug("--- REMOVING from stageNamesWithPrefix: {}", stageNamesWithPrefix.removeLast());

            log.debug("--- stages now: {}", stageNames);
            log.debug("--- stages WithPrefix now: {}", stageNamesWithPrefix);
        } else {
            stageNames.removeLast();
            stageNamesWithPrefix.removeLast();
        }
    }


    /**
     *
     */
    public void logEasyMdcStartingInvoke(String methodName) {
        if (ENABLE_ADVANCED_LOGGING) {
            log.debug("-------- Starting Invoke for method: {} --------", methodName);
        }
    }

    /**
     *
     */
    public void logEasyMdcFinishedInvoke(String methodName) {
        if (ENABLE_ADVANCED_LOGGING) {
            log.debug("-------- Finished Invoke for method: {} --------", methodName);
        }
    }
}
