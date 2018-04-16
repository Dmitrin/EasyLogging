package logging.easymdc.services.aspect;

import logging.easymdc.annotations.EasyMdc;
import logging.easymdc.services.factories.StageFactory;
import logging.easymdc.services.factories.TimeFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class EasyMdcAspect {

    private StageFactory easyMdcStageFactory;

    private TimeFactory easyMdcTimeFactory;

    public EasyMdcAspect(StageFactory easyMdcStageFactory,
                         TimeFactory easyMdcTimeFactory) {
        this.easyMdcStageFactory = easyMdcStageFactory;
        this.easyMdcTimeFactory = easyMdcTimeFactory;
    }

    @Around("@annotation(easyMdc)")
    public Object around(ProceedingJoinPoint pjp, EasyMdc easyMdc) throws Throwable {

        beginEasyMdcLogic(pjp);

        long before = System.nanoTime();

        Object result;

        long after;

        try {
            result = pjp.proceed();
        } finally {
            after = System.nanoTime();

            finishEasyMdcLogic(pjp, before, after);
        }

        return result;
    }

    private void beginEasyMdcLogic(ProceedingJoinPoint pjp) {
        easyMdcStageFactory.logEasyMdcStartingInvoke(pjp.getSignature().getName());

        easyMdcStageFactory.putStage(pjp.getSignature().getName());
    }

    private void finishEasyMdcLogic(ProceedingJoinPoint pjp, long before, long after) {
        easyMdcTimeFactory.saveMethodExecutionTimeAspect(pjp.getSignature(), after - before);


        easyMdcStageFactory.removeStage();

        easyMdcStageFactory.logEasyMdcFinishedInvoke(pjp.getSignature().getName());
    }
}
