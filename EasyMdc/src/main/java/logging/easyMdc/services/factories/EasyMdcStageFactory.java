package logging.easyMdc.services.factories;

import logging.easyMdc.config.EasyMdcProperties;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedList;

@Slf4j
public class EasyMdcStageFactory implements StageFactory {

    // todo: concurrent?
    private static LinkedList<String> stageNames = new LinkedList<>();

    private static LinkedList<String> stageNamesWithPrefix = new LinkedList<>();

    private EasyMdcProperties easyMdcProperties;


    @Autowired
    public EasyMdcStageFactory(EasyMdcProperties easyMdcProperties) {
        this.easyMdcProperties = easyMdcProperties;
    }

    /**
     *
     */
    public void putStage(String stageName) {
        String stageNameWithPrefix = generatePrefix() + stageName;

        stageNames.add(stageName);

        stageNamesWithPrefix.add(stageNameWithPrefix);

        MDC.put(easyMdcProperties.getMdcTheOnlyOneStageName(), stageNameWithPrefix);

        logPutStage(stageName, stageNameWithPrefix);
    }

    private String generatePrefix() {
        StringBuilder prefix = new StringBuilder();

        for (String stageName : stageNames) {
            if (stageName.length() < easyMdcProperties.getPrefixLength()) {
                 prefix.append(stageName)
                       .append(".");
            } else {
                prefix.append(stageName, 0, easyMdcProperties.getPrefixLength())
                      .append(".");
            }
        }

        return prefix.toString();
    }

    private void logPutStage(String stageName, String stageNameWithPrefix) {
        if (easyMdcProperties.isEnableAdvancedLogging()) {
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
            MDC.put(easyMdcProperties.getMdcTheOnlyOneStageName(), stageNames.getLast());
        } else {
            MDC.remove(easyMdcProperties.getMdcTheOnlyOneStageName());
        }

        logRemoveStage(removedStage, removedStageWithPrefix);
    }

    private void logRemoveStage(String removedStage, String removedStageWithPrefix) {
        if (easyMdcProperties.isEnableAdvancedLogging()) {
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
        if (easyMdcProperties.isEnableAdvancedLogging()) {
            log.debug("~~~~~~~~ Starting Invoke for method: {} ~~~~~~~~", methodName);
        }
    }


    /**
     *
     */
    public void logEasyMdcFinishedInvoke(String methodName) {
        if (easyMdcProperties.isEnableAdvancedLogging()) {
            log.debug("~~~~~~~~ Finished Invoke for method: {} ~~~~~~~~", methodName);
        }
    }
}
