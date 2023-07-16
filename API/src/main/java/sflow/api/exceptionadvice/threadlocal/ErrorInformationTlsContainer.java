package sflow.api.exceptionadvice.threadlocal;

import org.springframework.stereotype.Component;
import sflow.api.dto.ErrorInformationDto;

@Component
public class ErrorInformationTlsContainer {

    private ThreadLocal<ErrorInformationDto> threadLocal = ThreadLocal.withInitial(
        () -> new ErrorInformationDto("none", "none")
    );

    public ThreadLocal<ErrorInformationDto> getThreadLocal() {
        return threadLocal;
    }

    public void removeThreadLocal() {
        threadLocal.remove();
    }
}