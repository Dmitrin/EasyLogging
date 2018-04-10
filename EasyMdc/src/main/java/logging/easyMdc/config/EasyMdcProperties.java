package logging.easyMdc.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class EasyMdcProperties {
    // todo: В программе не должно быть MDC с таким же именем
    private String mdcTheOnlyOneStageName = "stage";

    private boolean enableAdvancedLogging = true;

    private int prefixLength = 4;


    public void setPrefixLength(int prefixLength) {

        if (prefixLength > 0) {
            this.prefixLength = prefixLength;
        } else {
            log.warn("PrefixLength is not set! Should be > 0!");
        }

    }
}
