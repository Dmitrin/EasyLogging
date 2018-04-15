package logging.easymdc.services.factories;

public interface StageFactory {

    void putStage(String stageName);

    void removeStage();

    void logEasyMdcStartingInvoke(String methodName);

    void logEasyMdcFinishedInvoke(String methodName);

}
