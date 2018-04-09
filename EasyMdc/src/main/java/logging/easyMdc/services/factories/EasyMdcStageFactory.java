package logging.easyMdc.services.factories;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import java.util.LinkedList;

import static logging.easyMdc.config.Constants.*;

@Slf4j
public class EasyMdcStageFactory implements StageFactory {

    // todo: concurrent?
    private static LinkedList<String> stageNames = new LinkedList<>();

    private static LinkedList<String> stageNamesWithPrefix = new LinkedList<>();


    /**
     *
     */
    public void putStage(String stageName) {
        String stageNameWithPrefix = generatePrefix() + stageName;

        stageNames.add(stageName);

        stageNamesWithPrefix.add(stageNameWithPrefix);

        MDC.put(MDC_THE_ONLY_ONE_STAGE_NAME, stageNameWithPrefix);

        logPutStage(stageName, stageNameWithPrefix);
    }

    private String generatePrefix() {
        StringBuilder prefix = new StringBuilder();

        for (String stageName : stageNames) {
            if (stageName.length() < PREFIX_LENGTH) {
                 prefix.append(stageName)
                       .append(".");
            } else {
                prefix.append(stageName, 0, PREFIX_LENGTH)
                      .append(".");
            }
        }

        return prefix.toString();
    }

    private void logPutStage(String stageName, String stageNameWithPrefix) {
        if (ENABLE_ADVANCED_LOGGING) {
            log.debug("+++ ADDED stageName: {}", stageName);
            log.debug("+++ ADDED stageNameWithPrefix: {}", stageNameWithPrefix);

            log.debug("+++ stages now: {}", stageNames);
            log.debug("+++ stages WithPrefix now: {}", stageNamesWithPrefix);
        }
    }


    /**
     *
     */
    public void removeStage() {
        // Проверка корректности записи и очищения очереди - Такой ситуации не должно произойти - проверка на неё
        if (stageNames.size() == 0 || stageNamesWithPrefix.size() == 0) {
            // todo: Обработать эту ошибку!
            log.error("MDC stage remove try from stageNames! But stageNames is Empty!");
            throw new RuntimeException();
        }

        String removedStage = stageNames.removeLast();
        String removedStageWithPrefix = stageNamesWithPrefix.removeLast();

        // Если ещё остались stages
        if (stageNames.size() != 0) {
            // The last stage is the King now! God bless the stage!
            MDC.put(MDC_THE_ONLY_ONE_STAGE_NAME, stageNames.getLast());
        } else {
            MDC.remove(MDC_THE_ONLY_ONE_STAGE_NAME);
        }

        logRemoveStage(removedStage, removedStageWithPrefix);
    }

    private void logRemoveStage(String removedStage, String removedStageWithPrefix) {
        if (ENABLE_ADVANCED_LOGGING) {
            log.debug("--- REMOVED from stageNames: {}", removedStage);
            log.debug("--- REMOVED from stageNamesWithPrefix: {}", removedStageWithPrefix);

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
            log.debug("~~~~~~~~ Starting Invoke for method: {} ~~~~~~~~", methodName);
        }
    }


    /**
     *
     */
    public void logEasyMdcFinishedInvoke(String methodName) {
        if (ENABLE_ADVANCED_LOGGING) {
            log.debug("~~~~~~~~ Finished Invoke for method: {} ~~~~~~~~", methodName);
        }
    }
}
